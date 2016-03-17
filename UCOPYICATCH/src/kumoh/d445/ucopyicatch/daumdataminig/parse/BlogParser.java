package kumoh.d445.ucopyicatch.daumdataminig.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class BlogParser {
	public  String  getText(String address)
	  {
		  System.out.println("content link : " + address);
		  
			 Document doc;
			 Elements elems, html_content;
			 Element elem;
			 String src;
			 String content = "";
			
			try {
				doc = Jsoup.connect(address).get();
				if(doc.select("frame[src]").toString().equals("")) return ""; 
				elems = doc.select("frame[src]");
				elem = elems.get(0); // id,
				src = elem.attr("abs:src");
				
				doc = Jsoup.connect(src).get(); 
				html_content = doc.select(".post-view.pcol2");
				if(html_content.isEmpty())
				{
					System.out.println("내용 없음");
					return ""; // 블로그 내용이 없는 경우
				}
				content = html_content.get(0).text();
				System.out.println("content : " + content);
			}
			catch (Exception e) {
				System.out.println("실패");
	    	}
			return content;
	  }
}
