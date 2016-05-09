package kumoh.d445.ucopyicatch.check;

import java.util.ArrayList;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;
import org.ujmp.core.calculation.Calculation;
import org.ujmp.core.doublematrix.calculation.general.decomposition.SVD;
import org.ujmp.core.doublematrix.calculation.general.decomposition.SVD.SVDMatrix;

import kumoh.d445.ucopyicatch.bookreport.BookReportData;
import kumoh.d445.ucopyicatch.bookreport.BookReportItemData;
import kumoh.d445.ucopyicatch.bookreport.Word;
import kumoh.d445.ucopyicatch.daumdataminig.book.BookFile;

public class TermSentence {
	private Matrix matrix;
	private Matrix reduceMatrix;
	private ArrayList<Word> termIndex;
	private ArrayList<ArrayList<String>> sentenceIndex;
	private ArrayList<String> linkIndex;
	
	public TermSentence(BookReportData report, int select) {
		termIndex = new ArrayList<Word>();	//단어 또는 어절이 저장
		sentenceIndex = new ArrayList<ArrayList<String>>();
		linkIndex = new ArrayList<String>();
		
		//독후감 정보를 받아서 메트릭스에 정보를 저장하여 기본적인 정보를 생성
		//1. 디비에서 읽어들인 엔티티 정보를 통해서 메트릭스 생성 값 생성
		//2. 0번 인덱스의 독후감 정보가 검사 대상 독후감 나머지 독후감은 수집 독후감
		if(select==1) {	//tfidf
			for(int i = 0 ; i < report.getItems().size() ; i++) {
				BookReportItemData bri = report.getItems().get(i);
				for(int j = 0 ; j < bri.getContents().size() ; j++ ) {
					//System.out.println(bri.getSentence().get(j));
					ArrayList<Word> brw = bri.getContents().get(j);
					for(int k = 0 ; k < brw.size() ; k++) {
						//문장 순서로 단어 리스트를 생성
						//중복 체크
						if(!termIndex.contains(brw.get(k))) {
							termIndex.add(brw.get(k));
						}
					}
				}
				sentenceIndex.add(bri.getSentence());
				linkIndex.add(bri.getLink());
			}
			
			//배열의 인덱스 생성(행 : 단어, 열 : 문장)
			int row = termIndex.size();
			int col = 0;
			for(int i = 0 ; i < sentenceIndex.size() ; i++) {
				col += sentenceIndex.get(i).size();
			}
			System.out.println("row,col : "+row+","+col);
			//메트릭스 생성
			matrix = SparseMatrix.Factory.zeros(row,col);
			Fillmatrix(report);
			System.out.println("매트릭스 끝");
			calcSVD(100);
			System.out.println("svd 끝");
		}
		else if(select==2) {	//어절
			for(int i = 0 ; i < report.getItems().size() ; i++) {
				BookReportItemData bri = report.getItems().get(i);
				//termIndex에 bookreportitemdata 어절 저장
				for(int j = 0 ; j < bri.getContents().size() ; j++ ) {
					//System.out.println(bri.getSentence().get(j));
					ArrayList<Word> brw = bri.getContents().get(j);
					for(int k = 0 ; k < brw.size() ; k++) {
						//문장 순서로 단어 리스트를 생성
						//중복 체크
						if(!termIndex.contains(brw.get(k))) {
							termIndex.add(brw.get(k));
						}
					}
				}
				sentenceIndex.add(bri.getSentence());
				linkIndex.add(bri.getLink());
			}
			
			//매트릭스 채우기
			int sentenceCount=0;
			for(int i = 0 ; i < report.getItems().size() ; i++) {
				BookReportItemData bri = report.getItems().get(i);
				//System.out.println(i+"번째 독후감의 문장 사이즈 : " + bri.getContents().size());
				for(int j=0 ; j < bri.getContents().size() ; j++) {
					ArrayList<String> separateWord = bri.getSeparateWord().get(j);
					//System.out.println("sentece index : "+sentenceCount);
					//(value : 단어의 tf-idf값, row : 해당 단어의 인덱스, col : 문장 인덱스)
					//System.out.println(j+"번째 ArrayList Word Size : " + sentenceWord.size());
					for(int k=0 ; k < separateWord.size() ; k++) {
						int index = termIndex.indexOf(separateWord.get(k));
						//System.out.println("row index : " + index + ", 명사 : " + sentenceWord.get(k).getName());
						if(!separateWord.isEmpty()) {
							matrix.setAsDouble(1,index,sentenceCount);
						}
					}
					sentenceCount++;
				}
			}
		}
	}
	
	//매트릭스 값 채워 넣기
	private void Fillmatrix(BookReportData report) {
		int sentenceCount=0;
		for(int i = 0 ; i < report.getItems().size() ; i++) {
			BookReportItemData bri = report.getItems().get(i);
			//System.out.println(i+"번째 독후감의 문장 사이즈 : " + bri.getContents().size());
			for(int j=0 ; j < bri.getContents().size() ; j++) {
				ArrayList<Word> sentenceWord = bri.getContents().get(j);
				//System.out.println("sentece index : "+sentenceCount);
				//(value : 단어의 tf-idf값, row : 해당 단어의 인덱스, col : 문장 인덱스)
				//System.out.println(j+"번째 ArrayList Word Size : " + sentenceWord.size());
				for(int k=0 ; k < sentenceWord.size() ; k++) {
					int index = termIndex.indexOf(sentenceWord.get(k));
					//System.out.println("row index : " + index + ", 명사 : " + sentenceWord.get(k).getName());
					if(!sentenceWord.isEmpty()) {
						matrix.setAsDouble(sentenceWord.get(k).getTfidf(),index,sentenceCount);
					}
				}
				sentenceCount++;
			}
		}
	}
	
	public void calcSVD(int rank) {
		//인덱스순으로 0 : U, 1 : S, 2 : V
		//Matrix[] svd = matrix.svd();
		SVD.SVDMatrix svd = new SVDMatrix(matrix);
		if(svd.getU().getColumnCount() < 100) {
			rank = (int)svd.getU().getColumnCount();
		}
		Matrix s = copyMatrix(svd.getS(),rank,rank);
		Matrix v = copyMatrix(svd.getV(),(int)svd.getV().getColumnCount(),rank);
		
		reduceMatrix = s.mtimes(v.transpose());
		//BookFile.writeMatrix(reduceMatrix, "축소 메트릭스.txt",termIndex);
		//BookFile.writeMatrix(s, "S 메트릭스.txt",termIndex);
		//BookFile.writeMatrix(v, "V 전치 메트릭스.txt",termIndex);
	}
	
	private Matrix copyMatrix(Matrix m, int row, int col) {
		Matrix matrix = SparseMatrix.Factory.zeros(row,col);
		for(int i = 0 ; i < row ; i++) {
			for(int j = 0 ; j < col ; j++) {
				matrix.setAsDouble(m.getAsDouble(i,j),i,j);
			}
		}
		return matrix;
	}
	
	public Matrix getMatrix() {
		return matrix;
	}
	
	public Matrix getReduceMatrix() {
		return reduceMatrix;
	}
	
	public ArrayList<Word> getTermIndex() {
		return termIndex;
	}
	
	public ArrayList<ArrayList<String>> getSentenceIndex() {
		return sentenceIndex;
	}
	
	public ArrayList<String> getLinkIndex() {
		return linkIndex;
	}
}
