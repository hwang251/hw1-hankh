package genetagging.cpe;

import genetagging.NcbiResults;
import genetagging.PosTagNamedEntity;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.xml.sax.SAXException;

public class NcbiQueryAnnotator extends JCasAnnotator_ImplBase {

  private static final String SUFFIX_XES = "xes";
  private static final String SUFFIX_SES = "ses";
  private static final String SUFFIX_OES = "oes";
  private static final String SUFFIX_IES = "ies";
  
  private HttpClient mClient = new DefaultHttpClient();
  private HttpGet mGet = new HttpGet();
  
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    System.out.println("NCBI");
    Iterator ptneIter = aJCas.getAnnotationIndex(PosTagNamedEntity.type).iterator();
    while (ptneIter.hasNext()) {
      PosTagNamedEntity ptne = (PosTagNamedEntity) ptneIter.next();
      String namedEntity = ptne.getNamedEntitiy();
      
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
              ncbiResults.setBegin(ptne.getBegin() + begin);
              ncbiResults.setEnd(ptne.getBegin() + end);
              ncbiResults.setId(ptne.getId());
              ncbiResults.setResult(namedEntity.substring(begin, end));
              ncbiResults.addToIndexes();
            }
          }
        }
      } catch (Exception e) {
        throw new AnalysisEngineProcessException(e);
      } 
    }

  }
  
  private String removePlural(String text) {
    if (text.endsWith(SUFFIX_XES)) {
      text.lastIndexOf(SUFFIX_XES);
      
    }
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
    if (namedEntity.contains(name) && name.length() != 0) {
      result = new int[2];
      int begin = namedEntity.indexOf(name);
      int end = begin + name.length();

      while (end < namedEntity.length()) {
        char nextChar = namedEntity.charAt(end);
        if (nextChar == '\\' || nextChar == ' ' ) {
          break;
        }
        end++;
      }
      result[0] = begin;
      result[1] = end;
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
