

/* First created by JCasGen Sat Oct 13 22:53:46 EDT 2012 */
package genetagging;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sat Oct 13 23:55:49 EDT 2012
 * XML source: C:/Users/Hank/workspace/hw1-hankh/src/main/resources/descriptors/collection_processing_engine/GeneTaggingCasConsumer.xml
 * @generated */
public class FoundGene extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(FoundGene.class);
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
  protected FoundGene() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public FoundGene(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public FoundGene(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public FoundGene(JCas jcas, int begin, int end) {
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
    if (FoundGene_Type.featOkTst && ((FoundGene_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "genetagging.FoundGene");
    return jcasType.ll_cas.ll_getStringValue(addr, ((FoundGene_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated */
  public void setId(String v) {
    if (FoundGene_Type.featOkTst && ((FoundGene_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "genetagging.FoundGene");
    jcasType.ll_cas.ll_setStringValue(addr, ((FoundGene_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: gene

  /** getter for gene - gets 
   * @generated */
  public String getGene() {
    if (FoundGene_Type.featOkTst && ((FoundGene_Type)jcasType).casFeat_gene == null)
      jcasType.jcas.throwFeatMissing("gene", "genetagging.FoundGene");
    return jcasType.ll_cas.ll_getStringValue(addr, ((FoundGene_Type)jcasType).casFeatCode_gene);}
    
  /** setter for gene - sets  
   * @generated */
  public void setGene(String v) {
    if (FoundGene_Type.featOkTst && ((FoundGene_Type)jcasType).casFeat_gene == null)
      jcasType.jcas.throwFeatMissing("gene", "genetagging.FoundGene");
    jcasType.ll_cas.ll_setStringValue(addr, ((FoundGene_Type)jcasType).casFeatCode_gene, v);}    
  }

    