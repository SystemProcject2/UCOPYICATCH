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
		List<Matrix> vector = inform.getReduceMatrix().getColumnList();
		int originalDocSentence = inform.getSentenceIndex().get(0).size();
		int currentCompareDoc = 1;
		
		for(int i=0 ; i < originalDocSentence ; i++) {
			int count=1;
			for(int j=originalDocSentence ; j < vector.size() ; i++) {
				double rate = vector.get(i).cosineSimilarityTo(vector.get(j), true);
				//표절의심문서에 저장
				SuspendPlagiarismResultItemData data = new SuspendPlagiarismResultItemData(i,currentCompareDoc,count,rate);
				lsaValue.getResult().add(data);
				
				if(count>inform.getSentenceIndex().get(currentCompareDoc).size()) {
					currentCompareDoc++;
					count=1;
				}
				else {
					count++;
				}
			}
		}
	}
	
	public SuspendPlagiarismResultData getLSAvalue() {
		return lsaValue;
	}
}
