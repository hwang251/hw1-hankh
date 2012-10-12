
/* First created by JCasGen Thu Oct 11 14:40:46 EDT 2012 */
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
 * Updated by JCasGen Fri Oct 12 14:26:11 EDT 2012
 * @generated */
public class PosTagNamedEntity_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (PosTagNamedEntity_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = PosTagNamedEntity_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new PosTagNamedEntity(addr, PosTagNamedEntity_Type.this);
  			   PosTagNamedEntity_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new PosTagNamedEntity(addr, PosTagNamedEntity_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = PosTagNamedEntity.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("genetagging.PosTagNamedEntity");
 
  /** @generated */
  final Feature casFeat_id;
  /** @generated */
  final int     casFeatCode_id;
  /** @generated */ 
  public String getId(int addr) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "genetagging.PosTagNamedEntity");
    return ll_cas.ll_getStringValue(addr, casFeatCode_id);
  }
  /** @generated */    
  public void setId(int addr, String v) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "genetagging.PosTagNamedEntity");
    ll_cas.ll_setStringValue(addr, casFeatCode_id, v);}
    
  
 
  /** @generated */
  final Feature casFeat_namedEntitiy;
  /** @generated */
  final int     casFeatCode_namedEntitiy;
  /** @generated */ 
  public String getNamedEntitiy(int addr) {
        if (featOkTst && casFeat_namedEntitiy == null)
      jcas.throwFeatMissing("namedEntitiy", "genetagging.PosTagNamedEntity");
    return ll_cas.ll_getStringValue(addr, casFeatCode_namedEntitiy);
  }
  /** @generated */    
  public void setNamedEntitiy(int addr, String v) {
        if (featOkTst && casFeat_namedEntitiy == null)
      jcas.throwFeatMissing("namedEntitiy", "genetagging.PosTagNamedEntity");
    ll_cas.ll_setStringValue(addr, casFeatCode_namedEntitiy, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public PosTagNamedEntity_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_id = jcas.getRequiredFeatureDE(casType, "id", "uima.cas.String", featOkTst);
    casFeatCode_id  = (null == casFeat_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_id).getCode();

 
    casFeat_namedEntitiy = jcas.getRequiredFeatureDE(casType, "namedEntitiy", "uima.cas.String", featOkTst);
    casFeatCode_namedEntitiy  = (null == casFeat_namedEntitiy) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_namedEntitiy).getCode();

  }
}



    