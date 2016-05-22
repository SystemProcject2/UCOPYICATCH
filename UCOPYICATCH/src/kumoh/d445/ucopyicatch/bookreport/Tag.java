package kumoh.d445.ucopyicatch.bookreport;

public class Tag {
	private String tagName;
	private int tagCnt;
	
	public Tag() {
		
	}
	
	public Tag(String tagName) {
		this.tagName = tagName;
	}
	
	public Tag(String tagName, int tagCnt) {
		this.tagName = tagName;
		this.tagCnt = tagCnt;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public int getTagCnt() {
		return tagCnt;
	}

	public void setTagCnt(int tagCnt) {
		this.tagCnt = tagCnt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
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
		Tag other = (Tag) obj;
		if (tagName == null) {
			if (other.tagName != null)
				return false;
		} else if (!tagName.equals(other.tagName))
			return false;
		return true;
	}
	
	
}
