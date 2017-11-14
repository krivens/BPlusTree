package treesearch.io;

public class OpObject {
	public static enum Operation {INSERT, SEARCH, SEARCHRANGE};
	
	private Operation operation;
	private double key;
	private double key2;
	private String value;
	
	OpObject(Operation operation, double key, String value) {
		this.operation = operation;
		this.key = key;
		this.value = value;
	}

	OpObject(Operation operation, double key, double key2) {
		this.operation = operation;
		this.key = key;
		this.key2 = key2;
	}
	
	public Operation getOperation() {
		return operation;
	}

	public double getKey() {
		return key;
	}
	
	public double getKey2() {
		return key2;
	}

	public String getValue() {
		return value;
	}

}
