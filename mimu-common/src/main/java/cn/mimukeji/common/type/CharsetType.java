package cn.mimukeji.common.type;

public enum CharsetType {

    UTF_8("UTF-8", "UTF-8"),
    UTF_16("UTF-16", "UTF-16"),
    ISO_8859_1("ISO-8859-1", "ISO-8859-1"),
    GB2312("GB2312", "GB2312"),
    GBK("GBK", "GBK"),
    GB18030("GB18030", "GB18030");

	private final String value;
	private final String text;

	private CharsetType(String value, String text) {
		this.value = value;
		this.text = text;
	}

	public String toValue() {
		return value;
	}

	public String toText() {
		return text;
	}

	public static CharsetType fromValue(int value) {
		for (CharsetType s : CharsetType.values()) {
			if (s.toValue().equals(value))
				return s;
		}
		return null;
	}

}
