package genetagging.cpe;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SummaryXmlParser extends DefaultHandler{
  
  private static final String TAG_DOCSUM = "DocSum";
  private static final String TAG_ITEM = "item";
  
  private static final String ATTR_NAME = "Name";
  private static final String ATTR_NAME_VAL_DESC = "Description";
  private static final String ATTR_NAME_VAL_OTHER_DESG = "OtherDesignations";
  private static final String ATTR_NAME_VAL_GENE_SRC = "GeneticSource";
  
  // Temporary variables
  private String mTempVal;
//  private List<String> mTempStrList;
  private Set<String> mTempStrSet;
  private boolean mHasGeneticSource;
  
  private String currentAttrNameVal;
  
  private Set<String> mNameList = new HashSet<String>();

  public void parseDocument(InputStream input) throws ParserConfigurationException, SAXException, IOException {
    SAXParserFactory spf = SAXParserFactory.newInstance();
    SAXParser parser = spf.newSAXParser();
    parser.parse(input, this);
  }
  
  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    if (qName.equalsIgnoreCase(TAG_DOCSUM)) {
//      mTempStrList = new ArrayList<String>();
      mTempStrSet = new HashSet<String>();
    }
    else if (qName.equalsIgnoreCase(TAG_ITEM)) {
      currentAttrNameVal = attributes.getValue(ATTR_NAME);
    }
  }
  
  @Override
  public void characters(char[] ch, int start, int length) {
    mTempVal = new String(ch, start, length);
  }
  
  @Override
  public void endElement(String uri, String localName, String qName) {
    if (qName.equalsIgnoreCase(TAG_ITEM)) {
      if (currentAttrNameVal.equals(ATTR_NAME_VAL_DESC)) {
//        mTempStrList.add(mTempVal);
        mTempStrSet.add(mTempVal);
      }
      else if (currentAttrNameVal.equals(ATTR_NAME_VAL_OTHER_DESG)) {
//        mTempStrList.addAll(Arrays.asList(mTempVal.split("\\|")));
        mTempStrSet.addAll(Arrays.asList(mTempVal.split("\\|")));
      }
      else if (currentAttrNameVal.equals(ATTR_NAME_VAL_GENE_SRC)) {
        mHasGeneticSource = mTempVal.length() > 0;
      }
    }
    else if (qName.equalsIgnoreCase(TAG_DOCSUM) && mHasGeneticSource) {
//      mNameList.addAll(mTempStrList);
      mNameList.addAll(mTempStrSet);
    }
    mTempVal = "";
  }

  public List<String> getNameList() {
    return new ArrayList<String>(mNameList);
  }
}
