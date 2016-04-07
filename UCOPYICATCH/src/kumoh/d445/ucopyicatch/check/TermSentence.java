package kumoh.d445.ucopyicatch.check;

import java.util.ArrayList;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;
import org.ujmp.core.doublematrix.calculation.general.decomposition.SVD;

import kumoh.d445.ucopyicatch.bookreport.BookReportData;
import kumoh.d445.ucopyicatch.bookreport.BookReportItemData;
import kumoh.d445.ucopyicatch.bookreport.Word;

public class TermSentence {
	private Matrix matrix;
	private Matrix reduceMatrix;
	private ArrayList<Word> termIndex;
	private ArrayList<ArrayList<String>> sentenceIndex;
	
	public TermSentence(BookReportData report) {
		termIndex = new ArrayList<Word>();
		sentenceIndex = new ArrayList<ArrayList<String>>();
		
		//독후감 정보를 받아서 메트릭스에 정보를 저장하여 기본적인 정보를 생성
		//1. 디비에서 읽어들인 엔티티 정보를 통해서 메트릭스 생성 값 생성
		//2. 0번 인덱스의 독후감 정보가 검사 대상 독후감 나머지 독후감은 수집 독후감
		for(int i = 0 ; i < report.getItems().size() ; i++) {
			BookReportItemData bri = report.getItems().get(i);
			for(int j = 0 ; j < bri.getContents().size() ; j++ ) {
				ArrayList<Word> brw = bri.getContents().get(j);
				for(int k = 0 ; k > brw.size() ; k++) {
					//문장 순서로 단어 리스트를 생성
					//중복 체크
					if(!brw.contains(brw.get(k))) {
						termIndex.add(brw.get(k));
					}
				}
				sentenceIndex.add(bri.getSentence());
			}
		}
		//배열의 인덱스 생성(행 : 단어, 열 : 문장)
		int row = termIndex.size();
		int col = 0;
		for(int i = 0 ; i < sentenceIndex.size() ; i++) {
			col += sentenceIndex.get(i).size();
		}
		//메트릭스 생성
		matrix = SparseMatrix.Factory.zeros(row,col);
		Fillmatrix(report);
		calcSVD();
	}
	
	//매트릭스 값 채워 넣기
	private void Fillmatrix(BookReportData report) {
		for(int i = 0 ; i < report.getItems().size() ; i++) {
			BookReportItemData bri = report.getItems().get(i);
			for(int j=0 ; j < bri.getContents().size() ; j++) {
				ArrayList<Word> sentenceWord = bri.getContents().get(j);
				//(value : 단어의 tf-idf값, row : 문장 인덱스, col : 해당 단어의 인덱스)
				matrix.setAsDouble(sentenceWord.get(j).getTfidf(),termIndex.indexOf(sentenceWord.get(j)),i);
			}
		}
	}
	
	public void calcTF() {
		
	}
	
	public void calcDF() {
		
	}
	
	public void calcTFIDF() {
		
	}
	
	public void calcSVD() {
		//인덱스순으로 0 : U, 1 : V, 2 : S
		Matrix svd[] = matrix.svd();
		reduceMatrix = svd[1].mtimes(svd[2].transpose());
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
}
