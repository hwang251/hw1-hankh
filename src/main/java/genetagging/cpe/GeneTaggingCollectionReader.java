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
import org.apache.uima.util.Level;
import org.apache.uima.util.Progress;


public class GeneTaggingCollectionReader extends CollectionReader_ImplBase {

  public static final String PARAM_INPUTFILE = "InputFile";
  
  private File mInputFile;
  
  private boolean processFile = false; 
  
  public void initialize() throws ResourceInitializationException {
    mInputFile = new File(((String) getConfigParameterValue(PARAM_INPUTFILE)).trim());
    
    // if input file does not exist or is not a file, throw exception
    if (!mInputFile.exists() || !mInputFile.isFile()) {
      throw new ResourceInitializationException("file_not_found",
              new Object[]{ PARAM_INPUTFILE, this.getMetaData().getName(), mInputFile.getPath()});
    }
    System.out.println("test");
  }
  
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
    
    Scanner scanner = new Scanner(mInputFile);
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      int splitLoc = line.indexOf(" ");
      Input input = new Input(jcas);
      input.setId(line.substring(0, splitLoc).trim());
      input.setText(line.substring(splitLoc).trim());
      input.addToIndexes();
    }
    
    processFile = true;
    System.out.println("test2");
  }

  @Override
  public void close() throws IOException {
    // TODO Auto-generated method stub

  }

  @Override
  public Progress[] getProgress() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean hasNext() throws IOException, CollectionException {
    return !processFile;
  }

}
