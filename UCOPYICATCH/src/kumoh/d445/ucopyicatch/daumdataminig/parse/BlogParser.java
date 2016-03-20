package kumoh.d445.ucopyicatch.daumdataminig.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class BlogParser {
	public  String  getText(String address)
	  {
		  System.out.println("content link : " + address);
		  
			 Elements elems;
			 Element elem;
			 String src;
			 String content = "";
			
			try {
				Document doc = Jsoup.connect(address).get();
				if(doc.select("frame[src]").toString().equals("")) return ""; 
				elems = doc.select("frame[src]");
				elem = elems.get(0); // id,
				src = elem.attr("abs:src");

				doc = Jsoup.connect(src).get();
				Elements html_content = doc.select(".cContentBody iframe[src]");
				src = html_content.attr("abs:src");
				doc = Jsoup.connect(src).get();
				Elements iframe_content = doc.select("#cContent");
				
				System.out.println(html_content.size());
				if(html_content.isEmpty())
				{
					System.out.println("내용 없음");
					return ""; // 블로그 내용이 없는 경우
				}
				//content = html_content.get(0).text();
				content = iframe_content.get(0).text();
				System.out.println("content : " + content);
			}
			catch (Exception e) {
				System.out.println("실패");
	    	}
			return content;
	  }
	public static void main(String[] args) {
		BlogParser bp = new BlogParser();
		bp.getText("http://blog.daum.net/like68982/10");
		//bp.getText("http://blog.daum.net/ld1107/9042150");
	}
}
