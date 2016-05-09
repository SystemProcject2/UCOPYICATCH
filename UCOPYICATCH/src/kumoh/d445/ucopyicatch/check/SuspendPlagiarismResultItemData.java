package kumoh.d445.ucopyicatch.check;

public class SuspendPlagiarismResultItemData {
	private int partOfOriginalDocIndex;	//검사할 독후감의 문장 인덱스
	private String partOfOriginalDocSentence; //검사할 독후감의 문장
	private int compareDoc;	//비교 대상 독후감 인덱스
	private int compareDocSentenceIndex;	//비교 대상 독후감의 문장 인덱스
	private String compareDocSentence;
	private String compareDocLink;
	private double plagiarismRate;	//해당 문장의 검사 결과값
	
	public SuspendPlagiarismResultItemData(int partOfOriginalDocIndex,int compareDoc,int compareDocSentenceIndex,double plagiarismRate) {
		this.partOfOriginalDocIndex = partOfOriginalDocIndex;
		this.compareDoc = compareDoc;
		this.compareDocSentenceIndex = compareDocSentenceIndex;
		this.plagiarismRate = plagiarismRate;
	}

	public int getPartOfOriginalDocIndex() {
		return partOfOriginalDocIndex;
	}

	public void setPartOfOriginalDocIndex(int partOfOriginalDocIndex) {
		this.partOfOriginalDocIndex = partOfOriginalDocIndex;
	}

	public String getPartOfOriginalDocSentence() {
		return partOfOriginalDocSentence;
	}

	public void setPartOfOriginalDocSentence(String partOfOriginalDocSentence) {
		this.partOfOriginalDocSentence = partOfOriginalDocSentence;
	}

	public int getCompareDoc() {
		return compareDoc;
	}

	public void setCompareDoc(int compareDoc) {
		this.compareDoc = compareDoc;
	}

	public int getCompareDocSentenceIndex() {
		return compareDocSentenceIndex;
	}

	public void setCompareDocSentenceIndex(int compareDocSentenceIndex) {
		this.compareDocSentenceIndex = compareDocSentenceIndex;
	}

	public String getCompareDocSentence() {
		return compareDocSentence;
	}

	public void setCompareDocSentence(String compareDocSentence) {
		this.compareDocSentence = compareDocSentence;
	}

	public double getPlagiarismRate() {
		return plagiarismRate;
	}

	public void setPlagiarismRate(double plagiarismRate) {
		this.plagiarismRate = plagiarismRate;
	}
	
	public String getCompareDocLink() {
		return compareDocLink;
	}

	public void setCompareDocLink(String compareDocLink) {
		this.compareDocLink = compareDocLink;
	}

	@Override
	public String toString() {
		return "Data [검사문장번호=" + partOfOriginalDocIndex
				+ ", 검사문장=" + partOfOriginalDocSentence + ", 비교문서번호=" + compareDoc
				+ ", 비교문서 문장번호=" + compareDocSentenceIndex + ", 비교문서 문장=" + compareDocSentence
				+ ", 비교문서 주소=" + compareDocLink + ", Rate=" + plagiarismRate + "]";
	}
}
