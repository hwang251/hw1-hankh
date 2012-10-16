package genetagging.cpe;
import genetagging.FoundGene;
import genetagging.NcbiResults;

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

/**
 * CAS Consumer made specifically for genetagging collection processing engine. 
 * The CAS consumer retrieves annotations and writes it to a file.
 * @author Hank
 *
 */
public class GeneTaggingCasConsumer extends CasConsumer_ImplBase {

  private static final String PARAM_OUTPUTFILE = "OutputFile";
  
  private File mOutputFile;
  
  /**
   * Retrieves a file defined by the parameter settings, and if the file does not exist,
   * attempt to create the file.
   */
  public void initialize() throws ResourceInitializationException {
    mOutputFile = new File(((String) getConfigParameterValue(PARAM_OUTPUTFILE)).trim());
    if (!mOutputFile.exists()) {
      try {
        mOutputFile.createNewFile();
      } catch (IOException e) {
        new ResourceInitializationException("can_not_create_file", 
                new Object[] {mOutputFile.getPath(), PARAM_OUTPUTFILE, this.getMetaData().getName()});
      }
    }
  }
  
  /**
   * Retrieves the {@link FoundGene} and {@link NcbiResults} annotations 
   * and writes the sentence id, begin-offset, end-offset, and gene to the 
   * output file.
   */
  public void processCas(CAS aCAS) throws ResourceProcessException {
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new ResourceProcessException(e);
    }

    try {
      FileOutputStream fos = new FileOutputStream(mOutputFile);
      
      // retrieve Local Gene Search Results
      Iterator foundGeneIter = jcas.getAnnotationIndex(FoundGene.type).iterator();
      while (foundGeneIter.hasNext()) {
        FoundGene foundGene = (FoundGene) foundGeneIter.next();
        String output = String.format("%s|%d %d|%s\n", 
                foundGene.getId(), foundGene.getBegin(), foundGene.getEnd(), foundGene.getGene());
        fos.write(output.getBytes());
      }
      
      // retrieve NCBI Results Annotations
      Iterator ncbiIter = jcas.getAnnotationIndex(NcbiResults.type).iterator();
      while (ncbiIter.hasNext()) {
        NcbiResults ncbiResults = (NcbiResults) ncbiIter.next();
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
