package kumoh.d445.ucopyicatch.check;

import java.util.List;
import org.ujmp.core.Matrix;

import kumoh.d445.ucopyicatch.daumdataminig.book.BookFile;

public class CopyCheck {
	private SuspendPlagiarismResultData lsaValue = new SuspendPlagiarismResultData();	//lsa를 통해서 얻은 결과값이 저장된 변수
	private SuspendPlagiarismResultData cosineValue = new SuspendPlagiarismResultData();	//lsa를 통해서 얻은 결과값이 저장된 변수
	
	public boolean excute(TermSentence inform, double tagRating, double cosineRating, double editRating) {
		try {
			calcCosine(inform,tagRating,cosineRating,editRating);
			BookFile.writeResult2(cosineValue, "cosine 최종 유사도 결과값(tag포함).txt");
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean excuteLSA(TermSentence inform, double tagRating, double cosineRating, double editRating) {
		try {
			calcLSA(inform,tagRating,cosineRating,editRating);
			BookFile.writeResult2(lsaValue, "lsa 최종 유사도 결과값(tag포함).txt");
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//LSA 유사도 계산
	public void calcLSA(TermSentence inform, double tagRating, double cosineRating, double editRating) {
		List<Matrix> wordVector = inform.getReduceMatrix().transpose().getRowList();
		List<Matrix> tagVector = inform.getTagMatrix().transpose().getRowList();
		int originalDocSentence = inform.getSentenceIndex().get(0).size();
		for(int i=0 ; i < originalDocSentence ; i++) {
			int count=0;
			int currentCompareDoc = 1;
			for(int j=originalDocSentence ; j < wordVector.size() ; j++) {
				if(inform.getSentenceIndex().get(currentCompareDoc).size() == 0) {
					currentCompareDoc++;
					count=0;
					continue;
				}
				double cosineRate = wordVector.get(i).cosineSimilarityTo(wordVector.get(j), true);
				if(((-1<=cosineRate) && (cosineRate<=1))) {
					double tagRate = tagVector.get(i).cosineSimilarityTo(tagVector.get(j), true);
					//표절의심문서에 저장
					double constraint = cosineRate*0.6 + tagRate*0.4;
					if(constraint >= 0.5) {
						SuspendPlagiarismResultItemData data = new SuspendPlagiarismResultItemData(i,inform.getSentenceIndex().get(0).get(i),currentCompareDoc,count,inform.getSentenceIndex().get(currentCompareDoc).get(count),inform.getLinkIndex().get(currentCompareDoc),constraint,tagRate);
						data.setEndResult(cosineRating,editRating);
						if(data.getEndResult() >= 0.65) {
							if(lsaValue.getResult().contains(data)) {
								int index = lsaValue.getResult().indexOf(data);
								SuspendPlagiarismResultItemData prev = lsaValue.getResult().get(index);
								if(prev.getEndResult() < data.getEndResult()) {
									lsaValue.getResult().remove(index);
									lsaValue.getResult().add(data);
								}
							}
							else {
								lsaValue.getResult().add(data);
							}
						}
					}
				}
				if(count==inform.getSentenceIndex().get(currentCompareDoc).size()-1) {
					currentCompareDoc++;
					count=0;
				}
				else {
					count++;
				}
			}
		}
	}
	
	public void calcCosine(TermSentence inform, double tagRating, double cosineRating, double editRating) {
		List<Matrix> wordVector = inform.getMatrix().transpose().getRowList();
		List<Matrix> tagVector = inform.getTagMatrix().transpose().getRowList();
		int originalDocSentence = inform.getSentenceIndex().get(0).size();
		System.out.println(wordVector.size()+","+originalDocSentence);
		for(int i=0 ; i < originalDocSentence ; i++) {
			int count=0;
			int currentCompareDoc = 1;
			for(int j=originalDocSentence ; j < wordVector.size() ; j++) {
				if(inform.getSentenceIndex().get(currentCompareDoc).size()==0) {
					currentCompareDoc++;
					count=0;
					System.out.println(i+","+j);
					continue;
				}
				double cosineRate = wordVector.get(i).cosineSimilarityTo(wordVector.get(j), true);
				//if(!((-1<=cosineRate) && (cosineRate<=1))) {
				//	continue;
				//}
				double tagRate = tagVector.get(i).cosineSimilarityTo(tagVector.get(j), true);
				//표절의심문서에 저장
				double constraint = cosineRate*0.6 + tagRate*0.4;
				if(constraint >= 0.5) {
					SuspendPlagiarismResultItemData data = new SuspendPlagiarismResultItemData(i,inform.getSentenceIndex().get(0).get(i),currentCompareDoc,count,inform.getSentenceIndex().get(currentCompareDoc).get(count),inform.getLinkIndex().get(currentCompareDoc),constraint,tagRate);
					data.setEndResult(cosineRating,editRating);
					if(data.getEndResult() >= 0.65) {
						if(cosineValue.getResult().contains(data)) {
							int index = cosineValue.getResult().indexOf(data);
							SuspendPlagiarismResultItemData prev = cosineValue.getResult().get(index);
							if(prev.getEndResult() < data.getEndResult()) {
								cosineValue.getResult().remove(index);
								cosineValue.getResult().add(data);
							}
						}
						else {
							cosineValue.getResult().add(data);
						}
					}
				}
				
				if(count==inform.getSentenceIndex().get(currentCompareDoc).size()-1) {
					currentCompareDoc++;
					count=0;
				}
				else {
					count++;
				}
			}
		}
	}
	
	public static double calcEditDistance(String s, String t) {
		int m=s.length();
		int n=t.length();

		int[][]d=new int[m+1][n+1];
		for(int i=0;i<=m;i++){
			d[i][0]=i;
		}
		for(int j=0;j<=n;j++){
			d[0][j]=j;
		}
		for(int j=1;j<=n;j++){
			for(int i=1;i<=m;i++){
				if(s.charAt(i-1)==t.charAt(j-1)){
					d[i][j]=d[i-1][j-1];
				}
				else{
					d[i][j]=min((d[i-1][j]+1),(d[i][j-1]+1),(d[i-1][j-1]+1));
				}
			}
		}
		
		double result = (double)((m+n)-d[m][n])/(m+n);

		return result;
	}

	public static int min(int a,int b,int c){
		return(Math.min(Math.min(a,b),c));
	}

	public static int maxLength(String len1, String len2){ 
		int length = 0;
		int val1 = len1.length();
		int val2 = len2.length();

		if(val1 <= val2)
			length = val2;
		else
			length = val1;

		return length;
	}

	public SuspendPlagiarismResultData getLSAvalue() {
		return lsaValue;
	}
	
	public SuspendPlagiarismResultData getCosinevalue() {
		return cosineValue;
	}
}
