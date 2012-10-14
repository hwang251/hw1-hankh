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

import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

public class GeneSetResource implements SharedResourceObject {

  private Set<String> mGeneSet = new HashSet<String>();
  private String resourcePath;
  
  @Override
  public void load(DataResource aData) throws ResourceInitializationException {
    resourcePath = aData.getUri().getPath();
    
    try {
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

  public Set<String> getGeneSet() {
    return mGeneSet;
  }
  
  public void addAll(Collection<String> c) {
    mGeneSet.addAll(c);
  }
  
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
      // log
    } catch (IOException e) {
      // log
    }
    return false;
  }
}
