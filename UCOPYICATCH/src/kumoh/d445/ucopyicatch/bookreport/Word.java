package kumoh.d445.ucopyicatch.bookreport;

public class Word {
	private String name;
	private float tfidf;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getTfidf() {
		return tfidf;
	}
	public void setTfidf(float tfidf) {
		this.tfidf = tfidf;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(tfidf);
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
		Word other = (Word) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Float.floatToIntBits(tfidf) != Float.floatToIntBits(other.tfidf))
			return false;
		return true;
	}


}
