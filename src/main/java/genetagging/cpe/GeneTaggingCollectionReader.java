package genetagging.cpe;
import genetagging.Input;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.FileUtils;
import org.apache.uima.util.Progress;

/**
 * Collection Reader made specifically for genetagging collection processing engine. 
 * The Collection Reader retrieves the input file and parses the input file into sentence-id
 * and text
 * @author Hank
 *
 */
public class GeneTaggingCollectionReader extends CollectionReader_ImplBase {

  private static final String PARAM_INPUTFILE = "InputFile";
  
  private File mInputFile;
  
  /**
   * variable to determine if the input file has been processed
   */
  private boolean processFile = false; 
  
  /**
   * Retrieves a file defined by the parameter settings, and if the file does not exists, 
   * throw an exception
   */
  public void initialize() throws ResourceInitializationException {
    mInputFile = new File(((String) getConfigParameterValue(PARAM_INPUTFILE)).trim());
    
    // if input file does not exist or is not a file, throw exception
    if (!mInputFile.exists() || !mInputFile.isFile()) {
      throw new ResourceInitializationException("file_not_found",
              new Object[]{ PARAM_INPUTFILE, this.getMetaData().getName(), mInputFile.getPath()});
    }
  }
  
  /**
   * Takes the input file and stores the text in the CAS. The file is then parsed and
   * the sentence id and text are stored in {@link Input} annotations.
   */
  @Override
  public void getNext(CAS aCAS) throws IOException, CollectionException {
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new CollectionException(e);
    }

    // put document in cas
    String text = FileUtils.file2String(mInputFile);
    jcas.setDocumentText(text);
    
    // scan each line of input file and parse each line 
    // into sentence id and text
    Scanner scanner = new Scanner(mInputFile);
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      int splitLoc = line.indexOf(" ");
      
      // store into input annotations
      Input input = new Input(jcas);
      input.setId(line.substring(0, splitLoc).trim());
      input.setText(line.substring(splitLoc).trim());
      input.addToIndexes();
    }
    
    processFile = true;
  }

  @Override
  public void close() throws IOException {
    // TODO Auto-generated method stub

  }

  @Override
  public Progress[] getProgress() {
    return null;
  }

  @Override
  public boolean hasNext() throws IOException, CollectionException {
    return !processFile;
  }

}
