package genetagging.cpe;

import genetagging.Input;
import genetagging.PosTagNamedEntity;
import genetagging.PosTagNamedEntityRecognizer;

import java.util.Iterator;
import java.util.Map;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

/**
 * Annotator part of the GeneTagging Analysis Engine pipeline. The annotator uses Stanford's CoreNLP
 * to search for named entities in the input texts.
 * 
 * @author Hank
 *
 */
public class PosTagNamedEntityAnnotator extends JCasAnnotator_ImplBase {

  /**
   * Takes the {@link Input} annotations and passes it to the {@link PosTagNamedEntityRecognizer}.
   * The {@link PosTagNamedEntityRecognizer} returns begin-end offset pairs of named entities. The
   * begin-end offset are used to find named entities from the text strings. The begin-end offsets
   * and named entities are stored in {@link PosTagNamedEntity} annotations.
   */
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // create PosTagNamedEntityRecognizer
    PosTagNamedEntityRecognizer ptner;
    try {
      ptner = new PosTagNamedEntityRecognizer();
    } catch (ResourceInitializationException e) {
      throw new AnalysisEngineProcessException(e);
    }
    
    Iterator inputIter = aJCas.getAnnotationIndex(Input.type).iterator();
    while (inputIter.hasNext()) {
      Input input = (Input) inputIter.next();
      String text = input.getText();
      
      Map<Integer, Integer> neMap = ptner.getGeneSpans(text);
      Iterator neMapKeyIter = neMap.keySet().iterator();
      while (neMapKeyIter.hasNext()) {
        // get next begin-end pair
        Integer neMapKey = (Integer) neMapKeyIter.next();
        int begin = neMapKey.intValue();
        int end = neMap.get(neMapKey).intValue();
        
        PosTagNamedEntity ptne = new PosTagNamedEntity(aJCas);
        ptne.setBegin(begin);
        ptne.setEnd(end);
        ptne.setId(input.getId());
        ptne.setNamedEntity(text.substring(begin, end));
        ptne.addToIndexes();
      }
    }

  }

}
