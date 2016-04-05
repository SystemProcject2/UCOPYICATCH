package kumoh.d445.ucopyicatch.daumdataminig;

import kumoh.d445.ucopyicatch.daumdataminig.bookreport.BookData;
import kumoh.d445.ucopyicatch.daumdataminig.bookreport.BookFile;
import kumoh.d445.ucopyicatch.daumdataminig.parse.BlogParser;
import kumoh.d445.ucopyicatch.daumdataminig.response.BlogData;
import kumoh.d445.ucopyicatch.daumdataminig.xml.XMLSaver;

public class TestMain {

	public static void main(String[] args) throws Exception{
		BookData bData = BookFile.readBookFile("C:/Users/user/Documents/GitHub/UCOPYICATCH/UCOPYICATCH/src/kumoh/d445/ucopyicatch/daumdataminig/bookreport/booklist.csv");
		for(int i=0 ; i < bData.getBookList().size() ; i++) {
			String query = bData.getBookList().get(i).getTitle()+" 독후감";
			// TODO Auto-generated method stub
			BlogData data = OpenApiProvider.requestBlogApi("a0cc69977252a3d205c2f0f268aa9839", query, 20, 1, "accu", "xml");
			BlogParser bp = new BlogParser();
			XMLSaver  xs = new XMLSaver();
			int count = 1;
			try {
				for(int j=0; j < data.getItems().size() ; j++){
					String address = data.getItems().get(j).getLink();
					String content = bp.getText(address);
					if(content.length() == 0){
						continue;
					}
					xs.saveXML(content, address, bData.getBookList().get(j).getBookCode(), count++);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				Thread.sleep(1000);
			}
		}
	}
}
