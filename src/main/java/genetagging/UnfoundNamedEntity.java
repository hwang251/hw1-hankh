

/* First created by JCasGen Sat Oct 13 23:45:23 EDT 2012 */
package genetagging;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sat Oct 13 23:55:50 EDT 2012
 * XML source: C:/Users/Hank/workspace/hw1-hankh/src/main/resources/descriptors/collection_processing_engine/GeneTaggingCasConsumer.xml
 * @generated */
public class UnfoundNamedEntity extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(UnfoundNamedEntity.class);
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
  protected UnfoundNamedEntity() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public UnfoundNamedEntity(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public UnfoundNamedEntity(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public UnfoundNamedEntity(JCas jcas, int begin, int end) {
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
    if (UnfoundNamedEntity_Type.featOkTst && ((UnfoundNamedEntity_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "genetagging.UnfoundNamedEntity");
    return jcasType.ll_cas.ll_getStringValue(addr, ((UnfoundNamedEntity_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated */
  public void setId(String v) {
    if (UnfoundNamedEntity_Type.featOkTst && ((UnfoundNamedEntity_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "genetagging.UnfoundNamedEntity");
    jcasType.ll_cas.ll_setStringValue(addr, ((UnfoundNamedEntity_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: namedEntity

  /** getter for namedEntity - gets 
   * @generated */
  public String getNamedEntity() {
    if (UnfoundNamedEntity_Type.featOkTst && ((UnfoundNamedEntity_Type)jcasType).casFeat_namedEntity == null)
      jcasType.jcas.throwFeatMissing("namedEntity", "genetagging.UnfoundNamedEntity");
    return jcasType.ll_cas.ll_getStringValue(addr, ((UnfoundNamedEntity_Type)jcasType).casFeatCode_namedEntity);}
    
  /** setter for namedEntity - sets  
   * @generated */
  public void setNamedEntity(String v) {
    if (UnfoundNamedEntity_Type.featOkTst && ((UnfoundNamedEntity_Type)jcasType).casFeat_namedEntity == null)
      jcasType.jcas.throwFeatMissing("namedEntity", "genetagging.UnfoundNamedEntity");
    jcasType.ll_cas.ll_setStringValue(addr, ((UnfoundNamedEntity_Type)jcasType).casFeatCode_namedEntity, v);}    
  }

    