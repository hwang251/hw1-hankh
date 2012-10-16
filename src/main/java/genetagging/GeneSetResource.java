package genetagging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.apache.uima.UIMAFramework;
import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;
import org.apache.uima.util.Level;

public class GeneSetResource implements SharedResourceObject {

  private Set<String> mGeneSet = new HashSet<String>();
  private String resourcePath;
  
  /**
   * Loads the resource file to create gene set and stores path of resource file
   */
  @Override
  public void load(DataResource aData) throws ResourceInitializationException {
    // save location of resource file
    resourcePath = aData.getUri().getPath();
    
    try {
      // reads resource file to create gene set
      InputStream is = aData.getInputStream();
      Scanner fileScanner = new Scanner(is);
      while(fileScanner.hasNextLine()) {
        mGeneSet.add(fileScanner.nextLine());
      }
      is.close();
    } catch (IOException e) {
      throw new ResourceInitializationException(e);
    } 
  }

  /**
   * 
   * @return gene set
   */
  public Set<String> getGeneSet() {
    return mGeneSet;
  }
  
  /**
   * Add collection of gene names to set
   * @param c collection of gene names
   */
  public void addAll(Collection<String> c) {
    mGeneSet.addAll(c);
  }
  
  /**
   * Save the Gene Set to local file 
   * @return if the operation succeeded or not
   */
  public boolean save() {
    try {
      File file = new File(resourcePath);
      OutputStream os = new FileOutputStream(file);
      for (String gene : mGeneSet) {
        os.write(String.format("%s\n", gene).getBytes());
      }
      os.close();
      return true;
    } catch (FileNotFoundException e) {
      UIMAFramework.getLogger(this.getClass()).log(Level.FINEST, "Unable to find file");
    } catch (IOException e) {
      UIMAFramework.getLogger(this.getClass()).log(Level.FINEST, "Unable to wrtie file");
    }
    return false;
  }
}
