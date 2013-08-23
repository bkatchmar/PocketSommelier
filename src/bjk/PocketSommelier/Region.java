package bjk.PocketSommelier;

public enum Region {
	FRANCE(22),
	ITALY(24),
	SPAIN(26),
	USA(28),
	CHILE(30),
	AUSTRAILIA(32),
	GERMANY(34),
	NEW_ZEALAND(36),
	ARGENTINA(38);
	
	private int code;
	
	private Region(int c) {
		code = c;
	}

	public int getCode() {
		return code;
	}
}
