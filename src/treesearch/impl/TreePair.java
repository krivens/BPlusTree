package treesearch.impl;

public class TreePair {
	protected double key;
	protected Object value;
	
	public TreePair(double key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}

	public double getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}
}
