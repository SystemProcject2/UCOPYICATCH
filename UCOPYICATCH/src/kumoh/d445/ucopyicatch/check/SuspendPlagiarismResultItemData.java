package kumoh.d445.ucopyicatch.check;

public class SuspendPlagiarismResultItemData {
	private int partOfOriginalDocIndex;	//검사할 독후감의 문장 인덱스
	private int compareDoc;	//비교 대상 독후감 인덱스
	private int compareDocSentence;	//비교 대상 독후감의 문장 인덱스
	private double plagiarismRate;	//해당 문장의 검사 결과값
	
	public SuspendPlagiarismResultItemData(int partOfOriginalDocIndex,int compareDoc,int compareDocSentence,double plagiarismRate) {
		this.partOfOriginalDocIndex = partOfOriginalDocIndex;
		this.compareDoc = compareDoc;
		this.compareDocSentence = compareDocSentence;
		this.plagiarismRate = plagiarismRate;
	}
	
	public int getPartOfOriginalDocIndex() {
		return partOfOriginalDocIndex;
	}
	
	public int getCompareDoc() {
		return compareDoc;
	}
	
	public int getCompareDocSentence() {
		return compareDocSentence;
	}
	
	public double getPlagiarismRate() {
		return plagiarismRate;
	}
}
