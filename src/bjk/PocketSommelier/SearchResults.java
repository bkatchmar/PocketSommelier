package bjk.PocketSommelier;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.BasicResponseHandler;

import java.util.Vector;

public class SearchResults extends Activity {
	TextView lblStatus;
	ListView lstWineResults;
	Vector<Wine> Wines;
	
	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			try
			{
				if(IsOnline())
				{
					Vector<Restaurant> Filtered = new Vector<Restaurant>();
					Vector<Wine> FilteredWines = new Vector<Wine>();
					
					WineParser me = new WineParser(GetMainRestaurantXML());
					Filtered = me.GetRestaurants();
					
					double MinPrice = getIntent().getExtras().getDouble("MinPrice");
					double MaxPrice = getIntent().getExtras().getDouble("MaxPrice");
					int WineType = getIntent().getExtras().getInt("WineType");
					int WineRegion = getIntent().getExtras().getInt("WineRegion");
					
					for(int i = 0; i < Filtered.size(); i++)
					{
						Restaurant Current = new Restaurant();
						Current.SetID(Filtered.elementAt(i).GetID());
						Current.SetName(Filtered.elementAt(i).GetName());
						Current.SetAddress(Filtered.elementAt(i).GetAddress());
						Current.SetType(Filtered.elementAt(i).GetType());
						Current.SetURL(Filtered.elementAt(i).GetURL());
						Current.SetZIP(Filtered.elementAt(i).GetZip());
						
						for(int j = 0; j < Filtered.elementAt(i).GetList().size(); j++)
						{
							Wine CurrentWine = Filtered.elementAt(i).GetList().elementAt(j);
							CurrentWine.SetRestaurant(Current);
							
							if(CurrentWine.GetPrice() >= MinPrice && 
									CurrentWine.GetPrice() <= MaxPrice &&
									(CurrentWine.GetType() == BuildType(WineType) || WineType == 0) &&
									(CurrentWine.GetWineRegion() == BuildRegion(WineRegion) || WineRegion == 0))
							{
								FilteredWines.add(CurrentWine);
							}
						}
					}
					
					lblStatus.setText("Network Connectivity Has Been Found");
					Wines = FilteredWines;
					
					if(Wines.size() == 0)
					{
						lblStatus.setText("No Results Found");
					}
					else
					{
						lblStatus.setVisibility(8);
						TimeToSetAdapter();
					}
				}
				else
					lblStatus.setText("No Network Connectivity Found");
			}
			catch(Exception e)
			{
				lblStatus.setText(e.getMessage());
			}
		}
	};
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        
        SetControls();
    }
	
	public void onStart()
    {
    	super.onStart();

    	Thread bg = new Thread(new Runnable()
    	{
    		public void run(){
    			try
    			{
    				lblStatus.setText("Loading..");
    				handler.sendMessage(handler.obtainMessage());
    			}
    			catch(Exception e)
    			{
    			}
    		}
    	});
    	
    	bg.start();
    }
	public void onStop()
    {
    	super.onStop();
    }
	
	private void SetControls()
	{
		lblStatus = (TextView)findViewById(R.id.lblStatus);
		lstWineResults = (ListView)findViewById(R.id.lstWineResults);
	}
	public boolean IsOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    	return cm.getActiveNetworkInfo().isConnected();
	}
	//Private Methods
    private String GetMainRestaurantXML()
    {
    	String Url = "http://videoxpg.webs.com/Restaurants.xml";
    	String ResponseTxt = "";
    	
    	// Create an instance of HttpClient.
        HttpClient client = new DefaultHttpClient();

        // Create a method instance.
        HttpGet method = new HttpGet(Url);
        
        try
        {
        	ResponseHandler<String> responseHandler = new BasicResponseHandler();
        	ResponseTxt = client.execute(method, responseHandler);
        }
        catch(Exception e)
        {
        	ResponseTxt = e.getMessage();
        }
    	
    	return ResponseTxt;
    }
    private WineType BuildType(int index)
    {
    	WineType FoundType = WineType.RED;
    	
    	if(index == 1)
    		FoundType = WineType.RED;
		else if(index == 2)
			FoundType = WineType.WHITE;
		else
			FoundType = WineType.ROSE;
    	
    	return FoundType;
    }
    private Region BuildRegion(int index)
    {
    	Region FoundType = Region.ARGENTINA;
    	
    	if(index == 22)
    		FoundType = Region.FRANCE;
		else if(index == 24)
			FoundType = Region.ITALY;
		else if(index == 26)
			FoundType = Region.SPAIN;
		else if(index == 28)
			FoundType = Region.USA;
		else if(index == 30)
			FoundType = Region.CHILE;
		else if(index == 32)
			FoundType = Region.AUSTRAILIA;
		else if(index == 34)
			FoundType = Region.GERMANY;
		else if(index == 36)
			FoundType = Region.NEW_ZEALAND;
		else
			FoundType = Region.ARGENTINA;
    	
    	return FoundType;
    }
    private void TimeToSetAdapter()
    {
    	lstWineResults.setAdapter(new RestaurantAdapter(this));
    }
    
    class RestaurantAdapter extends ArrayAdapter<Wine>
    {
    	Activity m_context;
    	
    	RestaurantAdapter(Activity context)
    	{
    		super(context, R.layout.winelisting, Wines);
    		this.m_context = context;
    	}
    	
    	public View getView(int position, View convertView, ViewGroup parent)
    	{
    		View Row = convertView;
    		Button btnMapIt;
    		TextView lblLocation, lblNameAndPrice;
    		
    		if(Row == null)
    		{
    			LayoutInflater inflater = m_context.getLayoutInflater();
    			Row = inflater.inflate(R.layout.winelisting, null);
    		}
    		
    		lblLocation = (TextView)Row.findViewById(R.id.lblLocation);
    		lblNameAndPrice = (TextView)Row.findViewById(R.id.lblNameAndPrice);
    		btnMapIt = (Button)Row.findViewById(R.id.btnMapIt);
    		
    		lblLocation.setText(Wines.elementAt(position).GetName());
    		lblNameAndPrice.setText("@ " + Wines.elementAt(position).GetRestaurant().GetName());
    		final int myPos = position;
    		
    		btnMapIt.setOnClickListener(new View.OnClickListener()
            {
            	@Override
    			public void onClick(View v)
            	{
            		String source = "geo:0,0?q=" + Wines.elementAt(myPos).GetRestaurant().GetAddress().replace(" ", "+") + "+" + 
            		"+," + Wines.elementAt(myPos).GetRestaurant().GetZip();
            		
            		try
            		{
            			Uri uri = Uri.parse(source);
            			startActivity(new Intent(Intent.ACTION_VIEW, uri));
            		}
            		catch(Exception e)
            		{
            		}
            	}
    		});
    		
    		return Row;
    	}
    }
}