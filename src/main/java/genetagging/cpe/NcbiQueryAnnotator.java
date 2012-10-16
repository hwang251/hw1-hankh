package genetagging.cpe;

import genetagging.GeneSetResource;
import genetagging.NcbiResults;
import genetagging.UnfoundNamedEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
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
import org.apache.uima.util.Level;

/**
 * Annotator part of the GeneTagging Analysis Engine pipeline. The annotator uses the named entity
 * as a search query to retrieve a list of gene names. The list of gene names are then used to 
 * search through named entities to identify any genes.
 * 
 * @author Hank
 *
 */
public class NcbiQueryAnnotator extends JCasAnnotator_ImplBase {

  private HttpClient mClient = new DefaultHttpClient();
  private HttpGet mGet = new HttpGet();
  
  private GeneSetResource mGeneSetResource;
  
  /**
   * Retrieves the gene set from {@link GeneSetResource}, if the gene set can not be retrieved,
   * throw an exception.
   */
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);

    try {
      mGeneSetResource = (GeneSetResource) getContext().getResourceObject("NcbiGeneSet");
    } catch (ResourceAccessException e) {
      throw new ResourceInitializationException(e);
    }
  }
  
  /**
   * Takes the {@link UnfoundNamedEntity} annotations and sends a query to the NCBI gene database
   * using the named entity as the query string. A list of possible gene names are then returned
   * and used to search through the named entity to determine any matching genes. If a gene is 
   * found, the gene is stored in a {@link NcbiResults} annotation. The list of gene names are
   * then saved to the gene set.
   */
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    Iterator uneIter = aJCas.getAnnotationIndex(UnfoundNamedEntity.type).iterator();
    while (uneIter.hasNext()) {
      UnfoundNamedEntity une = (UnfoundNamedEntity) uneIter.next();
      String namedEntity = une.getNamedEntity();
      
      try {
        // send NCBI query using named entity
        List<String> idList = searchQuery(namedEntity);
        if (idList.size() > 0) {
          // retrieve summaries of ids found by search query 
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
          
          // add gene list to gene set
          mGeneSetResource.addAll(nameList);
        }
      } catch (Exception e) {
        // log exception 
        getContext().getLogger().log(Level.FINEST, String.format("Exception %s for %s",
                e.toString(), namedEntity));
      } 
    }
    
    // save gene set
    mGeneSetResource.save();
  }
  
  /**
   * Search NamedEntity for specific string found from NCBI gene database
   * 
   * @param namedEntity the named entity subject of search
   * @param name the specific gene to search for in the named entity
   * @return an array containing the begin and end offsets of where the name is 
   * found in the named entity
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
   * Queries NCBI gene database for search of specific query string
   * 
   * @param query the query string to search for on NCBI gene database
   * @return  list of ids 
   * @throws AnalysisEngineProcessException - if an error occurs while searching or parsing
   */
  private List<String> searchQuery(String query) throws AnalysisEngineProcessException {
    try {
      // create uri
      List<NameValuePair> formParams = new ArrayList<NameValuePair>();
      formParams.add(new BasicNameValuePair("db", "gene"));
      formParams.add(new BasicNameValuePair("term", query));
      URI uri = URIUtils.createURI("http", "eutils.ncbi.nlm.nih.gov", -1,
              "/entrez/eutils/esearch.fcgi", URLEncodedUtils.format(formParams, "UTF-8"), null);

      // send query
      mGet.setURI(uri);
      HttpEntity result = mClient.execute(mGet).getEntity();

      // parse results and return id list
      SearchXmlParser searchParser = new SearchXmlParser();
      searchParser.parseDocument(result.getContent());
      return searchParser.getIdList();
    } catch (Exception e) {
      throw new AnalysisEngineProcessException(e);
    } 
  }

  /**
   * Queries NCBI database for summaries of specified ids
   * 
   * @param idList list of ids to retrieve summaries
   * @return list of gene names
   * @throws AnalysisEngineProcessException - if an error occurs while searching or parsing 
   */
  private List<String> summaryQuery(List<String> idList) throws AnalysisEngineProcessException {
    try {
      // combine ids into one string
      StringBuilder ids = new StringBuilder();
      for (String id : idList) {
        ids.append(id);
        ids.append(' ');
      }

      // create uri
      List<NameValuePair> formParams = new ArrayList<NameValuePair>();
      formParams.add(new BasicNameValuePair("db", "gene"));
      formParams.add(new BasicNameValuePair("id", ids.toString()));
      URI uri = URIUtils.createURI("http", "eutils.ncbi.nlm.nih.gov", -1,
              "/entrez/eutils/esummary.fcgi", URLEncodedUtils.format(formParams, "UTF-8"), null);

      // send summary request
      mGet.setURI(uri);
      HttpEntity result = mClient.execute(mGet).getEntity();

      // parse results and return gene list
      SummaryXmlParser summaryParser = new SummaryXmlParser();
      summaryParser.parseDocument(result.getContent());
      return summaryParser.getNameList();
    } catch (Exception e) {
      throw new AnalysisEngineProcessException(e);
    }
  }
  
}
