package genetagging;


/* First created by JCasGen Wed Oct 10 11:29:55 EDT 2012 */

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sat Oct 13 18:16:32 EDT 2012
 * XML source: C:/Users/Hank/workspace/hw1-hankh/src/main/resources/descriptors/collection_processing_engine/GeneTaggingTAE.xml
 * @generated */
public class Input extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Input.class);
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
  protected Input() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Input(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Input(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Input(JCas jcas, int begin, int end) {
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

  /** getter for id - gets Sentence Identifier
   * @generated */
  public String getId() {
    if (Input_Type.featOkTst && ((Input_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "genetagging.Input");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Input_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets Sentence Identifier 
   * @generated */
  public void setId(String v) {
    if (Input_Type.featOkTst && ((Input_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "genetagging.Input");
    jcasType.ll_cas.ll_setStringValue(addr, ((Input_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: text

  /** getter for text - gets Text of the sentence
   * @generated */
  public String getText() {
    if (Input_Type.featOkTst && ((Input_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "genetagging.Input");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Input_Type)jcasType).casFeatCode_text);}
    
  /** setter for text - sets Text of the sentence 
   * @generated */
  public void setText(String v) {
    if (Input_Type.featOkTst && ((Input_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "genetagging.Input");
    jcasType.ll_cas.ll_setStringValue(addr, ((Input_Type)jcasType).casFeatCode_text, v);}    
  }

    