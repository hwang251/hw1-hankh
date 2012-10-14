
/* First created by JCasGen Sat Oct 13 17:27:44 EDT 2012 */
package genetagging;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Sat Oct 13 18:16:32 EDT 2012
 * @generated */
public class NcbiResults_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (NcbiResults_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = NcbiResults_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new NcbiResults(addr, NcbiResults_Type.this);
  			   NcbiResults_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new NcbiResults(addr, NcbiResults_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = NcbiResults.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("genetagging.NcbiResults");



  /** @generated */
  final Feature casFeat_id;
  /** @generated */
  final int     casFeatCode_id;
  /** @generated */ 
  public String getId(int addr) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "genetagging.NcbiResults");
    return ll_cas.ll_getStringValue(addr, casFeatCode_id);
  }
  /** @generated */    
  public void setId(int addr, String v) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "genetagging.NcbiResults");
    ll_cas.ll_setStringValue(addr, casFeatCode_id, v);}
    
  
 
  /** @generated */
  final Feature casFeat_result;
  /** @generated */
  final int     casFeatCode_result;
  /** @generated */ 
  public String getResult(int addr) {
        if (featOkTst && casFeat_result == null)
      jcas.throwFeatMissing("result", "genetagging.NcbiResults");
    return ll_cas.ll_getStringValue(addr, casFeatCode_result);
  }
  /** @generated */    
  public void setResult(int addr, String v) {
        if (featOkTst && casFeat_result == null)
      jcas.throwFeatMissing("result", "genetagging.NcbiResults");
    ll_cas.ll_setStringValue(addr, casFeatCode_result, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public NcbiResults_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_id = jcas.getRequiredFeatureDE(casType, "id", "uima.cas.String", featOkTst);
    casFeatCode_id  = (null == casFeat_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_id).getCode();

 
    casFeat_result = jcas.getRequiredFeatureDE(casType, "result", "uima.cas.String", featOkTst);
    casFeatCode_result  = (null == casFeat_result) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_result).getCode();

  }
}



    