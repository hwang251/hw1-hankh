
/* First created by JCasGen Sat Oct 13 23:45:23 EDT 2012 */
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
 * Updated by JCasGen Sat Oct 13 23:55:50 EDT 2012
 * @generated */
public class UnfoundNamedEntity_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (UnfoundNamedEntity_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = UnfoundNamedEntity_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new UnfoundNamedEntity(addr, UnfoundNamedEntity_Type.this);
  			   UnfoundNamedEntity_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new UnfoundNamedEntity(addr, UnfoundNamedEntity_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = UnfoundNamedEntity.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("genetagging.UnfoundNamedEntity");
 
  /** @generated */
  final Feature casFeat_id;
  /** @generated */
  final int     casFeatCode_id;
  /** @generated */ 
  public String getId(int addr) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "genetagging.UnfoundNamedEntity");
    return ll_cas.ll_getStringValue(addr, casFeatCode_id);
  }
  /** @generated */    
  public void setId(int addr, String v) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "genetagging.UnfoundNamedEntity");
    ll_cas.ll_setStringValue(addr, casFeatCode_id, v);}
    
  
 
  /** @generated */
  final Feature casFeat_namedEntity;
  /** @generated */
  final int     casFeatCode_namedEntity;
  /** @generated */ 
  public String getNamedEntity(int addr) {
        if (featOkTst && casFeat_namedEntity == null)
      jcas.throwFeatMissing("namedEntity", "genetagging.UnfoundNamedEntity");
    return ll_cas.ll_getStringValue(addr, casFeatCode_namedEntity);
  }
  /** @generated */    
  public void setNamedEntity(int addr, String v) {
        if (featOkTst && casFeat_namedEntity == null)
      jcas.throwFeatMissing("namedEntity", "genetagging.UnfoundNamedEntity");
    ll_cas.ll_setStringValue(addr, casFeatCode_namedEntity, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public UnfoundNamedEntity_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_id = jcas.getRequiredFeatureDE(casType, "id", "uima.cas.String", featOkTst);
    casFeatCode_id  = (null == casFeat_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_id).getCode();

 
    casFeat_namedEntity = jcas.getRequiredFeatureDE(casType, "namedEntity", "uima.cas.String", featOkTst);
    casFeatCode_namedEntity  = (null == casFeat_namedEntity) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_namedEntity).getCode();

  }
}



    