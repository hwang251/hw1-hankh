package genetagging.cpe;

import genetagging.FoundGene;
import genetagging.GeneSetResource;
import genetagging.PosTagNamedEntity;
import genetagging.UnfoundNamedEntity;

import java.util.Iterator;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceAccessException;
import org.apache.uima.resource.ResourceInitializationException;

public class LocalGeneSearchAnnotator extends JCasAnnotator_ImplBase {

  private Set<String> mGeneSet;

  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);

    try {
      mGeneSet = ((GeneSetResource) getContext().getResourceObject("GeneSet")).getGeneSet();
    } catch (ResourceAccessException e) {
      throw new ResourceInitializationException(e);
    }
  }

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    Iterator ptneIter = aJCas.getAnnotationIndex(PosTagNamedEntity.type).iterator();
    while (ptneIter.hasNext()) {
      PosTagNamedEntity ptne = (PosTagNamedEntity) ptneIter.next();
      String namedEntity = ptne.getNamedEntity();

      boolean found = false;
      for (String gene : mGeneSet) {
        int[] result = searchNamedEntity(namedEntity, gene);
        if (result != null) {
          int begin = result[0];
          int end = result[1];

          FoundGene foundGene = new FoundGene(aJCas);
          foundGene.setBegin(ptne.getBegin() + begin);
          foundGene.setEnd(ptne.getBegin() + end);
          foundGene.setId(ptne.getId());
          foundGene.setGene(namedEntity.substring(begin, end).trim());
          foundGene.addToIndexes();
          found = true;
        }
      }
      
      // Add unfound named entity
      if (!found) {
        UnfoundNamedEntity une = new UnfoundNamedEntity(aJCas);
        une.setBegin(ptne.getBegin());
        une.setEnd(ptne.getEnd());
        une.setId(ptne.getId());
        une.setNamedEntity(ptne.getNamedEntity());
        une.addToIndexes();
      }
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
    if (namedEntity.toLowerCase().contains(name.toLowerCase()) && name.length() != 0) {
      result = new int[2];
      int begin = namedEntity.indexOf(name);
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

}
