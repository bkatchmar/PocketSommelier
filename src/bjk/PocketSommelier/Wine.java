package bjk.PocketSommelier;

public class Wine {
	//Private Variables
	private int m_id;
	private String m_wineName;
	private WineType m_type;
	private double m_price;
	private Region m_region;
	private Restaurant m_restaurant;
	
	public Wine()
	{
		m_id = 0;
		m_wineName = "";
		m_type = WineType.RED;
		m_price = 0.0;
		m_region = Region.FRANCE;
		m_restaurant = new Restaurant();
	}
	
	//Mutators
	public void SetID(int val)
	{
		m_id = val;
	}
	public void SetName(String val)
	{
		m_wineName = val;
	}
	public void SetType(WineType val)
	{
		m_type = val;
	}
	public void SetPrice(double val)
	{
		m_price = val;
	}
	public void SetWineRegion(Region val)
	{
		m_region = val;
	}
	public void SetRestaurant(Restaurant val)
	{
		m_restaurant = val;
	}
	
	//Access
	public int GetID()
	{
		return m_id;
	}
	public String GetName()
	{
		return m_wineName;
	}
	public WineType GetType()
	{
		return m_type;
	}
	public double GetPrice()
	{
		return m_price;
	}
	public Region GetWineRegion()
	{
		return m_region;
	}
	public Restaurant GetRestaurant()
	{
		return m_restaurant;
	}
}