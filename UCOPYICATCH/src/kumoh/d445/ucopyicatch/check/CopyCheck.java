package kumoh.d445.ucopyicatch.check;

import java.util.List;
import org.ujmp.core.Matrix;

public class CopyCheck {
	private SuspendPlagiarismResultData lsaValue = new SuspendPlagiarismResultData();	//lsa를 통해서 얻은 결과값이 저장된 변수
	
	public boolean excute(TermSentence inform) {
		try {
			calcLSA(inform);	//lsa계산
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//벡터 문장 유사도 계산
	public void calcLSA(TermSentence inform) {
		List<Matrix> vector = inform.getReduceMatrix().transpose().getRowList();
		int originalDocSentence = inform.getSentenceIndex().get(0).size();
		//System.out.println("백터 크기 : " + vector.size());
		//System.out.println("origin 크기 : " + originalDocSentence);
		//System.out.println("원본 row 크기 : " + inform.getMatrix().getRowCount());
		//System.out.println("Reduce row 크기 : " + inform.getReduceMatrix().getRowCount());
		//System.out.println("Reduce col 크기 : " + inform.getReduceMatrix().getColumnCount());
		for(int i=0 ; i < originalDocSentence ; i++) {
			int count=0;
			int currentCompareDoc = 1;
			for(int j=originalDocSentence ; j < vector.size() ; j++) {
				double rate = vector.get(i).cosineSimilarityTo(vector.get(j), true);
				//표절의심문서에 저장
				if(rate > 0.7) {
					SuspendPlagiarismResultItemData data = new SuspendPlagiarismResultItemData(i,currentCompareDoc,count,rate);
					data.setPartOfOriginalDocSentence(inform.getSentenceIndex().get(0).get(i));
					data.setCompareDocSentence(inform.getSentenceIndex().get(currentCompareDoc).get(count));
					data.setCompareDocLink(inform.getLinkIndex().get(currentCompareDoc));
					lsaValue.getResult().add(data);
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
	
	//테스트용 코사인 유사도만
	public void calcCosine(TermSentence inform) {
		List<Matrix> vector = inform.getMatrix().transpose().getRowList();
		int originalDocSentence = inform.getSentenceIndex().get(0).size();
		for(int i=0 ; i < originalDocSentence ; i++) {
			int count=0;
			int currentCompareDoc = 1;
			for(int j=originalDocSentence ; j < vector.size() ; j++) {
				double rate = vector.get(i).cosineSimilarityTo(vector.get(j), true);
				//표절의심문서에 저장
				if(rate>0.7) {
					SuspendPlagiarismResultItemData data = new SuspendPlagiarismResultItemData(i,currentCompareDoc,count,rate);
					data.setPartOfOriginalDocSentence(inform.getSentenceIndex().get(0).get(i));
					data.setCompareDocSentence(inform.getSentenceIndex().get(currentCompareDoc).get(count));
					data.setCompareDocLink(inform.getLinkIndex().get(currentCompareDoc));
					lsaValue.getResult().add(data);
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
	
	public double calcEditDistance(String s, String t) {
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

		int maxLen = maxLength(s,t);

		//double editDistance = d[m][n] / (double) maxLen; 

		return d[n-1][m-1];
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
}
