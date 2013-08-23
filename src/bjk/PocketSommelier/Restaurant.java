package bjk.PocketSommelier;

import java.util.Vector;

public class Restaurant {
	//Private Variables
	private int m_id;
	private String m_name;
	private String m_address;
	private int m_zip;
	private RestaurantType m_type;
	private String m_url;
	private Vector<Wine> m_wineList;
	
	public Restaurant()
	{
		m_id = 0;
		m_name = "";
		m_address = "";
		m_zip = 0;
		m_type = RestaurantType.STEAKHOUSE;
		m_url = "";
		m_wineList = new Vector<Wine>();
	}
	
	//Mutators
	public void SetID(int val)
	{
		m_id = val;
	}
	public void SetName(String val)
	{
		m_name = val;
	}
	public void SetAddress(String val)
	{
		m_address = val;
	}
	public void SetZIP(int val)
	{
		m_zip = val;
	}
	public void SetType(RestaurantType val)
	{
		m_type = val;
	}
	public void SetURL(String val)
	{
		m_url = val;
	}
	public void SetWineList(Vector<Wine> val)
	{
		m_wineList = val;
	}
	
	//Access
	public int GetID()
	{
		return m_id;
	}
	public String GetName()
	{
		return m_name;
	}
	public String GetAddress()
	{
		return m_address;
	}
	public int GetZip()
	{
		return m_zip;
	}
	public RestaurantType GetType()
	{
		return m_type;
	}
	public String GetURL()
	{
		return m_url;
	}
	public Vector<Wine> GetList()
	{
		return m_wineList;
	}
}