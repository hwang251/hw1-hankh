

/* First created by JCasGen Sat Oct 13 17:27:44 EDT 2012 */
package genetagging;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sat Oct 13 18:16:32 EDT 2012
 * XML source: C:/Users/Hank/workspace/hw1-hankh/src/main/resources/descriptors/collection_processing_engine/GeneTaggingTAE.xml
 * @generated */
public class NcbiResults extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(NcbiResults.class);
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
  protected NcbiResults() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public NcbiResults(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public NcbiResults(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public NcbiResults(JCas jcas, int begin, int end) {
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
    if (NcbiResults_Type.featOkTst && ((NcbiResults_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "genetagging.NcbiResults");
    return jcasType.ll_cas.ll_getStringValue(addr, ((NcbiResults_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated */
  public void setId(String v) {
    if (NcbiResults_Type.featOkTst && ((NcbiResults_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "genetagging.NcbiResults");
    jcasType.ll_cas.ll_setStringValue(addr, ((NcbiResults_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: result

  /** getter for result - gets 
   * @generated */
  public String getResult() {
    if (NcbiResults_Type.featOkTst && ((NcbiResults_Type)jcasType).casFeat_result == null)
      jcasType.jcas.throwFeatMissing("result", "genetagging.NcbiResults");
    return jcasType.ll_cas.ll_getStringValue(addr, ((NcbiResults_Type)jcasType).casFeatCode_result);}
    
  /** setter for result - sets  
   * @generated */
  public void setResult(String v) {
    if (NcbiResults_Type.featOkTst && ((NcbiResults_Type)jcasType).casFeat_result == null)
      jcasType.jcas.throwFeatMissing("result", "genetagging.NcbiResults");
    jcasType.ll_cas.ll_setStringValue(addr, ((NcbiResults_Type)jcasType).casFeatCode_result, v);}    
  }

    