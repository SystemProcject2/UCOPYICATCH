package kumoh.d445.ucopyicatch.daumdataminig.xml;

import java.io.File;
import java.io.FileOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLSaver {
	public void saveXML(String content, String address, String bookCode, int count)
	{
        String fileName = "xml\\2" + bookCode + "_" + count + ".xml";
     
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		
		try{
			db = dbf.newDocumentBuilder();
		}catch(ParserConfigurationException e){
			e.printStackTrace();
		}
		Document d = db.newDocument();
		Element bookreport = d.createElement("BOOKREPORT");
		d.appendChild(bookreport);
		
		Element link = d.createElement("LINK");
		Element text = d.createElement("CONTENT");
		
		link.setTextContent(address);
		text.setTextContent(content);
		
		bookreport.appendChild(link);
		bookreport.appendChild(text);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	
		DOMSource source = new DOMSource(d);
		StreamResult result;
		try {
			result = new StreamResult(new FileOutputStream(new File(fileName)));
			transformer.transform(source, result);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("File saved!");   
	}
}
