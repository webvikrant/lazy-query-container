package in.mindbug.lqc;

public class Attribute {
	private final String colName;
	private final Class<?> dataType;
	private Object value;
	private final String caption;

	public Attribute(String colName, Class<?> dataType, Object value, String caption) {
		super();
		this.colName = colName;
		this.dataType = dataType;
		this.value = value;
		this.caption = caption;
	}

	public String getColName() {
		return colName;
	}

	public Class<?> getDataType() {
		return dataType;
	}

	void setValue(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public String getCaption() {
		return caption;
	}

}
