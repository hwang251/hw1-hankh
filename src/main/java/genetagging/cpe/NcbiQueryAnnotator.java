package genetagging.cpe;

import genetagging.GeneSetResource;
import genetagging.NcbiResults;
import genetagging.PosTagNamedEntity;
import genetagging.UnfoundNamedEntity;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceAccessException;
import org.apache.uima.resource.ResourceInitializationException;
import org.xml.sax.SAXException;

public class NcbiQueryAnnotator extends JCasAnnotator_ImplBase {

  private HttpClient mClient = new DefaultHttpClient();
  private HttpGet mGet = new HttpGet();
  
  private GeneSetResource mGeneSetResource;
  
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);

    try {
      mGeneSetResource = (GeneSetResource) getContext().getResourceObject("NcbiGeneSet");
    } catch (ResourceAccessException e) {
      throw new ResourceInitializationException(e);
    }
  }
  
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    Iterator uneIter = aJCas.getAnnotationIndex(UnfoundNamedEntity.type).iterator();
    while (uneIter.hasNext()) {
      UnfoundNamedEntity une = (UnfoundNamedEntity) uneIter.next();
      String namedEntity = une.getNamedEntity();
      
      try {
        List<String> idList = searchQuery(namedEntity);
        if (idList.size() > 0) {
          List<String> nameList = summaryQuery(idList);
          
          for (String name : nameList) {
            int[] result = searchNamedEntity(namedEntity, name);
            if (result != null) {
              int begin = result[0];
              int end = result[1];
              
              NcbiResults ncbiResults = new NcbiResults(aJCas);
              ncbiResults.setBegin(une.getBegin() + begin);
              ncbiResults.setEnd(une.getBegin() + end);
              ncbiResults.setId(une.getId());
              ncbiResults.setResult(namedEntity.substring(begin, end).trim());
              ncbiResults.addToIndexes();
            }
          }
          
          mGeneSetResource.addAll(nameList);
        }
      } catch (Exception e) {
        // log error
      } 
    }
    mGeneSetResource.save();
  }
  
  /**
   * Search NamedEntity for specific string found from NCBI
   * 
   * @param namedEntity
   * @param name
   * @return
   */
  private int[] searchNamedEntity(String namedEntity, String name) {
    int[] result = null;
    if (namedEntity.toLowerCase().contains(name.toLowerCase()) && name.length() != 0) {
      result = new int[2];
      int begin = namedEntity.indexOf(name.toLowerCase());
      int end = begin + name.length();

      result[0] = begin;
      result[1] = end;
      
      // Check to make sure results are valid
      if (begin < 0 || end < 0 || end < begin) {
        return null;
      }
    }
    
    return result;
  }
  
  
  /**
   * Queries NCBI database for search of specific query string
   * 
   * @param query
   * @return
   * @throws URISyntaxException
   * @throws IllegalStateException
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   */
  private List<String> searchQuery(String query) throws URISyntaxException, IllegalStateException, ParserConfigurationException, SAXException, IOException {
    // Initial Query 
    List<NameValuePair> formParams = new ArrayList<NameValuePair>();
    formParams.add(new BasicNameValuePair("db","gene"));
    formParams.add(new BasicNameValuePair("term", query));
    URI uri = URIUtils.createURI("http", "eutils.ncbi.nlm.nih.gov", -1, 
            "/entrez/eutils/esearch.fcgi", URLEncodedUtils.format(formParams, "UTF-8"), null);
    //System.out.println(uri.toString()); // log this
    
    mGet.setURI(uri);
    HttpEntity result = mClient.execute(mGet).getEntity();
    
    SearchXmlParser searchParser = new SearchXmlParser();
    searchParser.parseDocument(result.getContent());
    return searchParser.getIdList();
  }

  /**
   * Queries NCBI database for summaries of specified ids
   * @param idList
   * @return
   * @throws URISyntaxException
   * @throws IOException
   * @throws IllegalStateException
   * @throws ParserConfigurationException
   * @throws SAXException
   */
  private List<String> summaryQuery(List<String> idList) throws URISyntaxException, IOException, IllegalStateException, ParserConfigurationException, SAXException {
    StringBuilder ids = new StringBuilder();
    for (String id : idList) { 
      ids.append(id);
      ids.append(' ');
    }
    
    List<NameValuePair> formParams = new ArrayList<NameValuePair>();
    formParams.add(new BasicNameValuePair("db","gene"));
    formParams.add(new BasicNameValuePair("id", ids.toString()));
    URI uri = URIUtils.createURI("http", "eutils.ncbi.nlm.nih.gov", -1, 
            "/entrez/eutils/esummary.fcgi", URLEncodedUtils.format(formParams, "UTF-8"), null);
    
    mGet.setURI(uri);
    HttpEntity result = mClient.execute(mGet).getEntity();
    
    SummaryXmlParser summaryParser = new SummaryXmlParser();
    summaryParser.parseDocument(result.getContent());
    return summaryParser.getNameList();
  }
  
}
