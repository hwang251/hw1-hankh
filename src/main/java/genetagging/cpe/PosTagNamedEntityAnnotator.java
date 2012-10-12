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

public class PosTagNamedEntityAnnotator extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
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
        Integer neMapKey = (Integer) neMapKeyIter.next();
        int begin = neMapKey.intValue();
        int end = neMap.get(neMapKey).intValue();
        
        PosTagNamedEntity ptne = new PosTagNamedEntity(aJCas);
        ptne.setBegin(begin);
        ptne.setEnd(end);
        ptne.setId(input.getId());
        ptne.setNamedEntitiy(text.substring(begin, end));
        ptne.addToIndexes();
      }
    }

  }

}
