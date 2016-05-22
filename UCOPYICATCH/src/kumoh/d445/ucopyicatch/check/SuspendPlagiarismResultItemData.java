package kumoh.d445.ucopyicatch.check;

public class SuspendPlagiarismResultItemData {
	private int partOfOriginalDocIndex;	//검사할 독후감의 문장 인덱스
	private String partOfOriginalDocSentence; //검사할 독후감의 문장
	private int compareDoc;	//비교 대상 독후감 인덱스
	private int compareDocSentenceIndex;	//비교 대상 독후감의 문장 인덱스
	private String compareDocSentence;
	private String compareDocLink;
	private double plagiarismRate;	//해당 문장의 검사 결과값
	private double edRate;	//문장의 편집거리 비율 공식 = (전체 문자 - 편집거리) / (전체문자)
	private double tagRate;
	private double endResult;	//최종 결과
	
	public SuspendPlagiarismResultItemData(int partOfOriginalDocIndex,String partOfOriginalDocSentence,int compareDoc,int compareDocSentenceIndex,String compareDocSentence,String compareDocLink,double plagiarismRate,double tagRate) {
		this.partOfOriginalDocIndex = partOfOriginalDocIndex;
		this.partOfOriginalDocSentence = partOfOriginalDocSentence;
		this.compareDoc = compareDoc;
		this.compareDocSentenceIndex = compareDocSentenceIndex;
		this.compareDocSentence = compareDocSentence;
		this.compareDocLink = compareDocLink;
		this.plagiarismRate = plagiarismRate;
		this.tagRate = tagRate;
		edRate = CopyCheck.calcEditDistance(partOfOriginalDocSentence, compareDocSentence);
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
	
	

	public double getEdRate() {
		return edRate;
	}

	public void setEdRate(double edRate) {
		this.edRate = edRate;
	}

	public double getEndResult() {
		return endResult;
	}

	public void setEndResult(double cosineRating, double editRating) {
		this.endResult = plagiarismRate*cosineRating + edRate*editRating;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + partOfOriginalDocIndex;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SuspendPlagiarismResultItemData other = (SuspendPlagiarismResultItemData) obj;
		if (partOfOriginalDocIndex != other.partOfOriginalDocIndex)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Data [검사문장번호=" + partOfOriginalDocIndex
				+ ", 검사문장=" + partOfOriginalDocSentence + ", 비교문서번호=" + compareDoc
				+ ", 비교문서 문장번호=" + compareDocSentenceIndex + ", 비교문서 문장=" + compareDocSentence
				+ ", 비교문서 주소=" + compareDocLink + ", CosineRate=" + plagiarismRate
				+ ", tagRate=" + tagRate + ", editRate=" + edRate + ", endResult=" + endResult + "]";
	}
}
