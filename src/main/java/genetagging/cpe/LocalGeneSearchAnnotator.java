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

/**
 * Annotator part of the GeneTagging Analysis Engine pipeline. The annotator uses a local gene
 * set to search through named entities to identify any genes. 
 * 
 * @author Hank
 *
 */
public class LocalGeneSearchAnnotator extends JCasAnnotator_ImplBase {

  private Set<String> mGeneSet;

  /**
   * Retrieves the gene set from {@link GeneSetResource}, if the gene set can not be retrieved,
   * throw an exception.
   */
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);

    try {
      mGeneSet = ((GeneSetResource) getContext().getResourceObject("GeneSet")).getGeneSet();
    } catch (ResourceAccessException e) {
      throw new ResourceInitializationException(e);
    }
  }

  /**
   * Takes the {@link PosTagNamedEntity} annotations and searches through the named entity
   * using the gene set from {@link GeneSetResource} to determine if any genes from the gene
   * set appear in the named entity. If a gene is found, the gene is stored in a {@link FoundGene}
   * annotation, and if not, the named entity is stored in a {@link UnfoundNamedEntity} annotation.
   */
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

          // store gene into FoundGene annotation
          // the begin and end offsets are adjusted to the previous offsets
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
   * Search NamedEntity for specific string found from local gene set
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
