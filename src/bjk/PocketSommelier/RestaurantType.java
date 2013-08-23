package bjk.PocketSommelier;

public enum RestaurantType {
	STEAKHOUSE(1),
	AMERICAN(2),
	ITALIAN(3),
	FRENCH(4);
	
	private int code;
	
	private RestaurantType(int c) {
		code = c;
	}

	public int getCode() {
		return code;
	}
}