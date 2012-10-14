

/* First created by JCasGen Thu Oct 11 14:40:45 EDT 2012 */
package genetagging;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sun Oct 14 11:50:08 EDT 2012
 * XML source: C:/Users/Hank/workspace/hw1-hankh/src/main/resources/descriptors/PosTagNamedEntityTypeSystem.xml
 * @generated */
public class PosTagNamedEntity extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(PosTagNamedEntity.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected PosTagNamedEntity() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public PosTagNamedEntity(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public PosTagNamedEntity(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public PosTagNamedEntity(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: id

  /** getter for id - gets 
   * @generated */
  public String getId() {
    if (PosTagNamedEntity_Type.featOkTst && ((PosTagNamedEntity_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "genetagging.PosTagNamedEntity");
    return jcasType.ll_cas.ll_getStringValue(addr, ((PosTagNamedEntity_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated */
  public void setId(String v) {
    if (PosTagNamedEntity_Type.featOkTst && ((PosTagNamedEntity_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "genetagging.PosTagNamedEntity");
    jcasType.ll_cas.ll_setStringValue(addr, ((PosTagNamedEntity_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: namedEntity

  /** getter for namedEntity - gets 
   * @generated */
  public String getNamedEntity() {
    if (PosTagNamedEntity_Type.featOkTst && ((PosTagNamedEntity_Type)jcasType).casFeat_namedEntity == null)
      jcasType.jcas.throwFeatMissing("namedEntity", "genetagging.PosTagNamedEntity");
    return jcasType.ll_cas.ll_getStringValue(addr, ((PosTagNamedEntity_Type)jcasType).casFeatCode_namedEntity);}
    
  /** setter for namedEntity - sets  
   * @generated */
  public void setNamedEntity(String v) {
    if (PosTagNamedEntity_Type.featOkTst && ((PosTagNamedEntity_Type)jcasType).casFeat_namedEntity == null)
      jcasType.jcas.throwFeatMissing("namedEntity", "genetagging.PosTagNamedEntity");
    jcasType.ll_cas.ll_setStringValue(addr, ((PosTagNamedEntity_Type)jcasType).casFeatCode_namedEntity, v);}    
  }

    