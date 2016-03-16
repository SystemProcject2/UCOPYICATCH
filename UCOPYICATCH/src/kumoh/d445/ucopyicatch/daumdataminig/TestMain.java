package kumoh.d445.ucopyicatch.daumdataminig;

import kumoh.d445.ucopyicatch.daumdataminig.parse.BlogParser;
import kumoh.d445.ucopyicatch.daumdataminig.response.BlogData;
import kumoh.d445.ucopyicatch.daumdataminig.xml.XMLSaver;

public class TestMain {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		BlogData data = OpenApiProvider.requestBlogApi("a0cc69977252a3d205c2f0f268aa9839", "백범일지독후감", 10, 1, "accu", "xml");
		BlogParser bp = new BlogParser();
		XMLSaver  xs = new XMLSaver();
		int count = 1;
		
		for(int i=0; i < data.getItems().size() ; i++){
			String address = data.getItems().get(i).getLink();
			System.out.println("Link : "+address);
			String content = bp.getText(address);
			if(content.length() == 0){
            	continue;
			}
			xs.saveXML(content, address, bookCode, count);
		}
	}
}
