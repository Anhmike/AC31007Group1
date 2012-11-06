package com.example.stocktest2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.*;



@TargetApi(7)
public class MainActivity extends Activity 
{
	//YahooFinanceAPI financeAPI;
	private TextView txtOutput;
	private Button btnFetch;
	
	private Button btnHistory1Fetch;
	
	private Button btnHistory2Fetch;
	
	private Button btnTotal;
	
	private ProgressDialog dialog;
	
	private ShareSetsModel portfolio = new ShareSetsModel();
	
		
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);	//Set layout from xml file
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);	//Forces portrait orientation
        
        //UI stuff.
        txtOutput = (TextView)findViewById(R.id.textOutput);
        btnFetch = (Button)findViewById(R.id.getQuoteButton);
        btnHistory1Fetch = (Button)findViewById(R.id.getHistoryText);
        btnHistory2Fetch = (Button)findViewById(R.id.getHistoryYQL);
        btnTotal = (Button)findViewById(R.id.getTotal);
        
        //Overriding OnClick behaviour with my own method.
        btnFetch.setOnClickListener(new View.OnClickListener()
        {
			
			public void onClick(View v)
			{
				if (!checkInternetConnection())
					Toast.makeText(MainActivity.this, "Internet Access Required...", Toast.LENGTH_SHORT).show();
				
				//Asks the Yahoo! Finance API to fetch the data for the passed company ticker.
				String[] cvsFields = YahooFinanceAPI.getInstance().fetchAndParse("TSCO");					
				
				//Rename CVS fields from token array
				String stockSymbol = cvsFields[0];
				String stockTime = cvsFields[1];
				String stockPrice = cvsFields[2];
				
				//Round stock price (.49 down; .50 up)
				long roundedStockTime = Math.round(Double.parseDouble(stockPrice));

				//send these "words" to the UI text fields.
				txtOutput.setText("\nThe stock price for Tesco (\"" + stockSymbol + "\") is \u00A3" + stockPrice + " (rounded \u00A3" + roundedStockTime + ") at " + stockTime + ".");

			}
			
		});
    
        btnHistory1Fetch.setOnClickListener(new View.OnClickListener()
        {
			
			public void onClick(View v)
			{
				if (!checkInternetConnection())
					Toast.makeText(MainActivity.this, "Internet Access Required...", Toast.LENGTH_SHORT).show();
				
				/*
				 * Date = 0
				 * Open = 1
				 * High = 2
				 * Low = 3
				 * Close = 3
				 * Volume = 4
				 * Adj Close = 5
				 * 
				 */
				
				
				txtOutput.setText(YahooFinanceAPI.getInstance().fetchAndParseHistory("TSCO"));
				
			}
			
		});
        
        btnHistory2Fetch.setOnClickListener(new View.OnClickListener()
        {
			
			public void onClick(View v)
			{
				if (!checkInternetConnection())
					Toast.makeText(MainActivity.this, "Internet Access Required...", Toast.LENGTH_SHORT).show();
				
				txtOutput.setText(YahooFinanceAPI.getInstance().fetchAndParseYQLHistory("TSCO"));
				
			}
			
		});
        
       //Overriding OnClick behaviour with my own method.
        btnTotal.setOnClickListener(new View.OnClickListener()
        {
			
			public void onClick(View v)
			{
				if (!checkInternetConnection())
					Toast.makeText(MainActivity.this, "Internet Access Required...", Toast.LENGTH_SHORT).show();

				//Set up "Loading" dialog
				dialog = ProgressDialog.show(MainActivity.this, "Please Wait...",
                        "Fetching Stock Market Data...", true);

				//Fetch share data on another thread capable of updating the UI.
				new AsyncTask<String, Integer, String>()
				{

		            @Override
		            protected void onProgressUpdate(Integer... progress)
		            {
		                super.onProgressUpdate(progress);
		            }


		            @Override
		            protected String doInBackground(String... arg0)
		            {
		                return portfolio.calculatePortfolio();

		            }

		            @Override
		            protected void onPostExecute(String result)
		            {
		            	dialog.dismiss();
		            	txtOutput.setText(result);
		            }

				}.execute("");
				
				//Disable the button
				btnTotal.setEnabled(false);
				
				
				
				
				//Enable the button again
				btnTotal.setEnabled(true);
			}
			
		});
    }
        
        
    public boolean checkInternetConnection()
    {
        //Check for internet access
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo().isConnectedOrConnecting() == false)
        {
        	return false;
        }	
        return true;
    }
    
    
    
}

