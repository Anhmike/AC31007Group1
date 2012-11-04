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
import android.view.View;
import android.widget.*;



@TargetApi(7)
public class MainActivity extends Activity 
{
	//YahooFinanceAPI financeAPI;
	private TextView txtOutput;
	private Button btnFetch;
	
	private TextView txtHistory1Output;
	private Button btnHistory1Fetch;
	
	private TextView txtHistory2Output;
	private Button btnHistory2Fetch;
	
	private TextView txtTotal;
	private Button btnTotal;
	
	private ProgressDialog dialog;
	
	private ShareSetsModel portfolio = new ShareSetsModel();
	
		
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);	//Set layout from xml file
        
        //UI stuff.
        txtOutput = (TextView)findViewById(R.id.textOutput);
        btnFetch = (Button)findViewById(R.id.getQuoteButton);
        txtHistory1Output = (TextView)findViewById(R.id.textHistory);
        btnHistory1Fetch = (Button)findViewById(R.id.getHistoryText);
        txtHistory2Output = (TextView)findViewById(R.id.YQLHistory);
        btnHistory2Fetch = (Button)findViewById(R.id.getHistoryYQL);
        txtTotal = (TextView)findViewById(R.id.textTotal);
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
				
				txtHistory1Output.setText(YahooFinanceAPI.getInstance().fetchAndParseHistory("TSCO"));
				
			}
			
		});
        
        btnHistory2Fetch.setOnClickListener(new View.OnClickListener()
        {
			
			public void onClick(View v)
			{
				if (!checkInternetConnection())
					Toast.makeText(MainActivity.this, "Internet Access Required...", Toast.LENGTH_SHORT).show();
				
				txtHistory2Output.setText(YahooFinanceAPI.getInstance().fetchAndParseYQLHistory("TSCO"));
				
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
		            	txtTotal.setText(result);
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

