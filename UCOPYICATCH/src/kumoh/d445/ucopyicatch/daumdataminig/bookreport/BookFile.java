package kumoh.d445.ucopyicatch.daumdataminig.bookreport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class BookFile {
	public static BookData readBookFile(String fileName) {
		BookData data = new BookData();
		try {
			File readFile = new File(fileName);
			FileReader fileReader = new FileReader(readFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line = null;
			
			while((line = bufferedReader.readLine()) != null) {
				String[] result = line.split(",");
				BookItemData item = new BookItemData(result[0],result[1]);
				data.getBookList().add(item);
			}
			
			bufferedReader.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static void main(String[] args) {
		BookData data = BookFile.readBookFile("C:/Users/user/Documents/GitHub/UCOPYICATCH/UCOPYICATCH/src/kumoh/d445/ucopyicatch/daumdataminig/bookreport/booklist.csv");
		System.out.print(data);
	}
}
