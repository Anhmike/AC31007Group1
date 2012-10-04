package com.example.stocktest2;

import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.View;
import android.widget.*;



@TargetApi(7)
public class MainActivity extends Activity 
{
	YahooFinanceAPI financeAPI;
	TextView txtOutput;
	Button btnFetch;
	
		
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);	//Set layout from xml file
        
        
        //UI stuff.
        txtOutput = (TextView)findViewById(R.id.textOutput);

        
        //UI button behaviour.
        btnFetch = (Button)findViewById(R.id.getQuoteButton);
        
        //Overriding OnClick behaviour with my own method.
        btnFetch.setOnClickListener(new View.OnClickListener()
        {
			
			public void onClick(View v)
			{
				
				//Asks the Yahoo! Finance API to fetch the data for the passed company ticker.
				//TODO: Replace string literal with list of companies in portfolio
				String[] cvsFields = YahooFinanceAPI.getInstance().fetchAndParse("TSCO.L");					
				
				//Rename CVS fields from token array
				String stockSymbol = cvsFields[0];
				String stockTime = cvsFields[1];
				String stockPrice = cvsFields[2];
				
				//Round stock price (.49 down; .50 up)
				long roundedStockTime = Math.round(Double.parseDouble(stockPrice));

				//send these "words" to the UI text fields.
				txtOutput.setText("\nThe stock price for Tesco (\"" + stockSymbol + "\") is £" + stockPrice + " (rounded £" + roundedStockTime + ") at " + stockTime + ".");
				
			}
			
		});
        
    }
    
}

