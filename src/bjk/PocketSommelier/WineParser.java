package bjk.PocketSommelier;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import org.xml.sax.InputSource;
import java.io.StringReader;

import java.util.Vector;

public class WineParser {
	private Vector<Restaurant> m_Restaurants;
	private String m_result;
	
	public WineParser(String xmlData)
	{
		m_result = "Didnt Start";
		m_Restaurants = new Vector<Restaurant>();
		
		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            SaxHandler handler = new SaxHandler();
            parser.parse(new InputSource(new StringReader(xmlData)), handler);
            m_Restaurants = handler.GetAllRestaurants();
            m_result = "Done";
		}
		catch (Exception ex)
		{
			m_result = ex.getMessage();
		}
	}
	
	public String GetResult()
	{
		return m_result;
	}
	
	public Vector<Restaurant> GetRestaurants()
	{
		return m_Restaurants;
	}
	
	private static final class SaxHandler extends DefaultHandler {
		private Vector<Restaurant> AllRestaurants;
		private Vector<Wine> AllWines;
		
		private Restaurant CurrentRestaurant;
		private Wine CurrentWine;
		
		private boolean AtID = false, AtName = false, AtAddress = false, AtZip = false, AtType = false, AtURL = false;
		private boolean AtWineID = false, AtWineName = false, AtWineType = false, AtPrice = false, AtRegion = false;
		
		public void startDocument() throws SAXException {
			AllRestaurants = new Vector<Restaurant>();
			AllWines = new Vector<Wine>();
			CurrentRestaurant = new Restaurant();
			CurrentWine = new Wine();
        }
		
		public void endDocument() throws SAXException {
			//Do Nothing
        }
		
		public void characters(char ch[], int start, int length) throws SAXException {
			if (AtID)
			{
				CurrentRestaurant.SetID(Integer.parseInt(new String(ch, start, length))); 
				AtID = false;
			}
			else if(AtName)
			{
				CurrentRestaurant.SetName(new String(ch, start, length));
				AtName = false;
			}
			else if(AtAddress)
			{
				CurrentRestaurant.SetAddress(new String(ch, start, length));
				AtAddress = false;
			}
			else if(AtZip)
			{
				CurrentRestaurant.SetZIP(Integer.parseInt(new String(ch, start, length)));
				AtZip = false;
			}
			else if(AtType)
			{
				int FoundType = Integer.parseInt(new String(ch, start, length));
				RestaurantType SelectedType;
				
				if(FoundType == 1)
					SelectedType = RestaurantType.STEAKHOUSE;
				else if(FoundType == 2)
					SelectedType = RestaurantType.AMERICAN;
				else if(FoundType == 3)
					SelectedType = RestaurantType.ITALIAN;
				else
					SelectedType = RestaurantType.FRENCH;
				
				CurrentRestaurant.SetType(SelectedType);
				AtType = false;
			}
			else if(AtURL)
			{
				CurrentRestaurant.SetURL(new String(ch, start, length));
				AtURL = false;
				AllRestaurants.add(CurrentRestaurant);
			}
			else if(AtWineID)
			{
				CurrentWine.SetID(Integer.parseInt(new String(ch, start, length)));
				AtWineID = false;
			}
			else if(AtWineName)
			{
				CurrentWine.SetName(new String(ch, start, length));
				AtWineName = false;
			}
			else if(AtWineType)
			{
				int FoundType = Integer.parseInt(new String(ch, start, length));
				WineType SelectedType;
				
				if(FoundType == 80)
					SelectedType = WineType.RED;
				else if(FoundType == 81)
					SelectedType = WineType.WHITE;
				else
					SelectedType = WineType.ROSE;
				
				CurrentWine.SetType(SelectedType);
				AtWineType = false;
			}
			else if(AtPrice)
			{
				CurrentWine.SetPrice(Double.parseDouble(new String(ch, start, length)));
				AtPrice = false;
			}
			else if(AtRegion)
			{
				int FoundRegion = Integer.parseInt(new String(ch, start, length));
				Region SelectedRegion;

				if(FoundRegion == 22)
					SelectedRegion = Region.FRANCE;
				else if(FoundRegion == 24)
					SelectedRegion = Region.ITALY;
				else if(FoundRegion == 26)
					SelectedRegion = Region.SPAIN;
				else if(FoundRegion == 28)
					SelectedRegion = Region.USA;
				else if(FoundRegion == 30)
					SelectedRegion = Region.CHILE;
				else if(FoundRegion == 32)
					SelectedRegion = Region.AUSTRAILIA;
				else if(FoundRegion == 34)
					SelectedRegion = Region.GERMANY;
				else if(FoundRegion == 36)
					SelectedRegion = Region.NEW_ZEALAND;
				else
					SelectedRegion = Region.ARGENTINA;
				
				CurrentWine.SetWineRegion(SelectedRegion);
				AllWines.add(CurrentWine);
				AtRegion = false;
			}
		}
		
		public void startElement(String uri, String localName, 
                String qName, Attributes attrs) throws SAXException {

			if (localName.equals("Restaurant"))
			{
				if(AllWines.size() > 0)
				{
					CurrentRestaurant.SetWineList(AllWines);
				}
				
				ResetValues();
            	CurrentRestaurant = new Restaurant();
            	AllWines = new Vector<Wine>();
            }
            else if (localName.equals("RestaurantID"))
            {
            	ResetValues();
            	AtID = true;
            }
			else if (localName.equals("RestaurantName"))
            {
            	ResetValues();
            	AtName = true;
            }
			else if (localName.equals("RestaurantAddress"))
            {
            	ResetValues();
            	AtAddress = true;
            }
			else if (localName.equals("RestaurantZip"))
            {
            	ResetValues();
            	AtZip = true;
            }
			else if (localName.equals("RestaurantType"))
            {
            	ResetValues();
            	AtType = true;
            }
			else if (localName.equals("URL"))
            {
            	ResetValues();
            	AtURL = true;
            }
			else if (localName.equals("WineList"))
            {
            	ResetValues();
            }
			else if (localName.equals("Wine"))
            {
				ResetValues();
            	CurrentWine = new Wine();
            }
			else if (localName.equals("WineID"))
            {
            	ResetValues();
            	AtWineID = true;
            }
			else if (localName.equals("WineName"))
            {
            	ResetValues();
            	AtWineName = true;
            }
			else if (localName.equals("WineType"))
            {
            	ResetValues();
            	AtWineType = true;
            }
			else if (localName.equals("Price"))
            {
            	ResetValues();
            	AtPrice = true;
            }
			else if (localName.equals("Region"))
            {
            	ResetValues();
            	AtRegion = true;
            }
			else if (localName.equals(""))
            {
            	ResetValues();
            }
        }
		
		public void endElement(String uri, String localName, String qName) throws SAXException {
            // do nothing
        }
		
		public Vector<Restaurant> GetAllRestaurants()
		{
			return AllRestaurants;
		}
		
		private void ResetValues()
		{
			//Restaurant Section
			AtID = false;
			AtName = false;
        	AtAddress = false;
        	AtZip = false;
        	AtType = false;
        	AtURL = false;
        	
        	//Wine Section
        	AtWineID = false;
        	AtWineName = false;
        	AtWineType = false;
        	AtPrice = false;
        	AtRegion = false;
		}
	}
}