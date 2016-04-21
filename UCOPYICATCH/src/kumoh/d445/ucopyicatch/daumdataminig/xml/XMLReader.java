package kumoh.d445.ucopyicatch.daumdataminig.xml;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import kumoh.d445.ucopyicatch.bookreport.BookReportItemData;

public class XMLReader {

	public ArrayList<BookReportItemData> readXML(int bookcode, String bookName)
	{
		//BookReportItemData
		ArrayList<BookReportItemData> brid = new ArrayList<BookReportItemData>();
		String link = "";
		try{
			int i = 1;
			while(true)
			{
				try
				{
			//	link = "F:/UCopyICatch/xml/1" + bookcode + "_" + i +".xml";
				link = "C:/Users/user/Downloads/xml/1" + bookcode + "_" + i +".xml";
				File fXmlFile = new File(link);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				doc.getDocumentElement().normalize();
				 
		//		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
				NodeList nList = doc.getElementsByTagName("BOOKREPORT");
		//		System.out.println("-----------------------");
				 
			   Node nNode = nList.item(0);
			   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			 
			      Element eElement = (Element) nNode;
			 
			     String blogLink =  getTagValue("LINK", eElement);
			     String content = getTagValue("CONTENT", eElement);
			     BookReportItemData btd = new BookReportItemData(bookcode,bookName,blogLink,content);
		//	     System.out.println("LINK : " + blogLink);
			//     System.out.println("CONTENT : " + content);
			     brid.add(btd);
			   }
			   i++;
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
					System.out.println(i-1+ "번째 글");
					break;
				}
			}
		}
		catch (Exception e) {
			  e.printStackTrace();
		}
		
		return brid;
	}
	 
	  private static String getTagValue(String sTag, Element eElement) {
	 NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	 
	 Node nValue = (Node) nlList.item(0);
	 
	 return nValue.getNodeValue();
	 }
}
