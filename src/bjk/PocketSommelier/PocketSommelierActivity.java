package bjk.PocketSommelier;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class PocketSommelierActivity extends Activity {
	AutoCompleteTextView actvName;
	Button btnSearch;
	EditText txtMinPrice;
	EditText txtMaxPrice;
	Spinner ddlWineType;
	Spinner ddlWineRegion;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        SetControls();
        
        ArrayAdapter<CharSequence> typeadapt = ArrayAdapter.createFromResource(
                this, R.array.types_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> regionadapt = ArrayAdapter.createFromResource(
                this, R.array.regions_array, android.R.layout.simple_spinner_item);
        
        Resources res = getResources();
        String[] restaurants = res.getStringArray(R.array.restaurants_array);
        actvName.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, restaurants));
        
        typeadapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddlWineType.setAdapter(typeadapt);
        
        regionadapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddlWineRegion.setAdapter(regionadapt);

        //Sets listeners
        btnSearch.setOnClickListener(new View.OnClickListener()
        {
        	@Override
			public void onClick(View v)
        	{
        		GoToSearchResults();
        	}
		});
    }
	
	private void SetControls()
	{
		actvName = (AutoCompleteTextView)findViewById(R.id.actvName);
		btnSearch = (Button)findViewById(R.id.btnSearch);
		txtMinPrice = (EditText)findViewById(R.id.txtMinPrice);
        txtMaxPrice = (EditText)findViewById(R.id.txtMaxPrice);
        ddlWineType = (Spinner)findViewById(R.id.ddlWineType);
        ddlWineRegion = (Spinner)findViewById(R.id.ddlWineRegion);
	}
	private void GoToSearchResults()
	{
		double EnteredMinPrice = GetMinPrice();
		double EnteredMaxPrice = GetMaxPrice();
		
		if(EnteredMinPrice > EnteredMaxPrice)
		{
			double Temp = EnteredMinPrice;
			EnteredMinPrice = EnteredMaxPrice;
			EnteredMaxPrice = Temp;
		}
		
		final Intent goToSearch = new Intent();
		goToSearch.setClass(this, SearchResults.class);
		goToSearch.putExtra("MinPrice", EnteredMinPrice);
		goToSearch.putExtra("MaxPrice", EnteredMaxPrice);
		goToSearch.putExtra("WineType", ddlWineType.getSelectedItemPosition());
		goToSearch.putExtra("WineRegion", ddlWineRegion.getSelectedItemPosition());
		startActivity(goToSearch);
	}
	private double GetMinPrice()
	{
		double ReturnValue = 0.0;
		
		if(txtMinPrice.getText().toString().length() > 0)
			ReturnValue = Double.parseDouble(txtMinPrice.getText().toString());
		
		return ReturnValue;
	}
	private double GetMaxPrice()
	{
		double ReturnValue = 0.0;
		
		if(txtMaxPrice.getText().toString().length() > 0)
			ReturnValue = Double.parseDouble(txtMaxPrice.getText().toString());
		
		return ReturnValue;
	}
}