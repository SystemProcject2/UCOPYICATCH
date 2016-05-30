package kumoh.d445.ucopyicatch.daumdataminig.book;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.ujmp.core.Matrix;

import kumoh.d445.ucopyicatch.bookreport.BookReportData;
import kumoh.d445.ucopyicatch.bookreport.BookReportItemData;
import kumoh.d445.ucopyicatch.bookreport.Word;
import kumoh.d445.ucopyicatch.check.SuspendPlagiarismResultData;

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
	
	public static String readTxtFile(String fileName) {
		 String txt = "";
	      try {        
	         BufferedReader br  =  new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
	         
	         String line = null;
	       //  line = br.readLine();//test
	         String match = "[^.!?:/\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
	         
	         while((line = br.readLine()) != null) {
	        	 txt += line;
	         }
	         txt = txt.replaceAll(match, "");
	         br.close();
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }
	      
	      return txt;
	   }
	
	public static BookReportItemData loadReport(String fileName) {
		BookReportItemData data = new BookReportItemData();
		
		try {
			String[] str = fileName.split("_");
			File readFile = new File("C:/Users/user/Documents/GitHub/UCOPYICATCH/UCOPYICATCH/report/"+fileName+".txt");
			FileReader fileReader = new FileReader(readFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line = null;
			StringBuffer content=new StringBuffer();
			String match = "[^.!?:/\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
			while((line = bufferedReader.readLine()) != null) {
				content.append(line);
			}
			data.setBookcode(Integer.parseInt(str[0]));
			data.setTitle(str[1]);
			data.setLink("noLink");
			String text = content.toString();
			text = text.replaceAll(match, "");
			text = text.replaceAll("\\s{2,}"," ");
			data.setContent(text);
			bufferedReader.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return data;
	}
	
	public static void writeResult(SuspendPlagiarismResultData data, String fileName) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			for(int i = 0 ; i < data.getResult().size() ; i++) {
				writer.write(data.getResult().get(i).toString());
				writer.newLine();
			}
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeResult2(SuspendPlagiarismResultData data, String fileName) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			for(int i = 0 ; i < data.getResult().size() ; i++) {
				//if(data.getResult().get(i).getEndResult() >= 0.7) {
					writer.write(data.getResult().get(i).toString());
					writer.newLine();
				//}
			}
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeReport(BookReportData data, String fileName) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			for(int i = 0 ; i < data.getItems().size() ; i++) {
				for(int j = 0 ; j < data.getItems().get(i).getContents().size() ; j++) {
					for(int k = 0 ; k < data.getItems().get(i).getContents().get(j).size() ; k++) {
						writer.write(data.getItems().get(i).getContents().get(j).get(k).toString());
						writer.newLine();
					}
					writer.write("---------------------------");
					writer.newLine();
				}
				writer.write("===========================================");
				writer.newLine();
			}
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//테스트용 결과값 출력
	public static void writeMatrix(Matrix m, String fileName, ArrayList<Word> termIndex) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			for(int i = 0 ; i < m.getRowCount() ; i++) {
				StringBuffer str = new StringBuffer();
				for(int j = 0 ; j < m.getColumnCount() ; j++) {
					str.append(m.getAsDouble(i,j)+"\t");
				}
				writer.write(str.toString());
				writer.newLine();
			}
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		BookReportItemData data = BookFile.loadReport("10001_백범일지_20101008_이재승.txt");
		System.out.print(data);
	}
}
