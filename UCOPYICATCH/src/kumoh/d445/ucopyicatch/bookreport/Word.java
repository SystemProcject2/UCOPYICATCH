package kumoh.d445.ucopyicatch.bookreport;

public class Word {
	private String name;
	private double tfidf;
	private int tf;
	private int df;
	private int total;
	
	public Word() {
		
	}
	
	public Word(String name) {
		this.name = name;
	}
	
	public Word(String name, int tf) {
		this.name = name;
		this.tf = tf;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setTfidf(double tfidf) {
		this.tfidf = tfidf;
	}
	
	public double getTfidf() {
		return tfidf;
	}
	
	public int getTF() {
		return tf;
	}
	
	public void setTF(int tf) {
		this.tf = tf;
	}
	
	public int getDF() {
		return df;
	}
	
	public void setDF(int df) {
		this.df = df;
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		return true;
	}

	@Override
	public String toString() {
		return "Word [name=" + name + ", tfidf=" + tfidf + ", tf=" + tf + ", df=" + df + ", total=" + total + "]";
	}
	
}
