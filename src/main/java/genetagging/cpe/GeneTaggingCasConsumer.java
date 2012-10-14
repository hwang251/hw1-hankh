package genetagging.cpe;
import genetagging.Input;
import genetagging.NcbiResults;
import genetagging.PosTagNamedEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;


public class GeneTaggingCasConsumer extends CasConsumer_ImplBase {

  public static final String PARAM_OUTPUTFILE = "OutputFile";
  
  private File mOutputFile;
  
  public void initialize() throws ResourceInitializationException {
    mOutputFile = new File(((String) getConfigParameterValue(PARAM_OUTPUTFILE)).trim());
    if (!mOutputFile.exists()) {
      try {
        mOutputFile.createNewFile();
      } catch (IOException e) {
        new ResourceInitializationException(e);// fix later
      }
    }
  }
  
  @Override
  public void processCas(CAS aCAS) throws ResourceProcessException {
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new ResourceProcessException(e);
    }

    try {
      FileOutputStream fos = new FileOutputStream(mOutputFile);
      
      // retrieve annotations
      Iterator it = jcas.getAnnotationIndex(NcbiResults.type).iterator();
      while (it.hasNext()) {
        NcbiResults ncbiResults = (NcbiResults) it.next();
        String output = String.format("%s|%d %d|%s\n", 
                ncbiResults.getId(), ncbiResults.getBegin(), ncbiResults.getEnd(), ncbiResults.getResult());
        fos.write(output.getBytes());
      }
      fos.close();
    } catch (IOException e) {
      throw new ResourceProcessException(e);
    }
  }

}
