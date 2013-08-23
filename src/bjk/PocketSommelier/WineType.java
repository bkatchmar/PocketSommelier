package bjk.PocketSommelier;

public enum WineType {
	RED(80),
	WHITE(81),
	ROSE(82);
	
	private int code;
	
	private WineType(int c) {
		code = c;
	}

	public int getCode() {
		return code;
	}
}
