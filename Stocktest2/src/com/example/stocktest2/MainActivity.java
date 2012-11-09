package com.example.stocktest2;

import java.util.ArrayList;

import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.*;



@TargetApi(7)
public class MainActivity extends Activity 
{
	//YahooFinanceAPI financeAPI;
	private TextView txtOutput;
	private Button btnTotal;
	
	private Button btnShares;
	
	private Button btnLostGained;
	
	private Button btnBestWorst;
	
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
        btnTotal = (Button)findViewById(R.id.getTotal);
        btnShares = (Button)findViewById(R.id.getShares );
        btnLostGained = (Button)findViewById(R.id.getLostGained);
        btnBestWorst = (Button)findViewById(R.id.getBestWorst);
        
        /*
        btnTotal.setOnClickListener(new View.OnClickListener()
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
        */
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
		                return portfolio.calculateFridayPortfolio();

		            }

		            @Override
		            protected void onPostExecute(String result)
		            {
		            	dialog.dismiss();
		            	//txtOutput.setText(result);
		            	Intent intent = new Intent(MainActivity.this, TextViewActivity.class);
		            	intent.putExtra("TextOutput", result);
		            	startActivity(intent);
		            }

				}.execute("");
				
				//Disable the button
				btnTotal.setEnabled(false);

				//Enable the button again
				btnTotal.setEnabled(true);
			}
			
		});
        
        btnShares.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View v)
			{
				if (!checkInternetConnection())
					Toast.makeText(MainActivity.this, "Internet Access Required...", Toast.LENGTH_SHORT).show();

				//Set up "Loading" dialog
				dialog = ProgressDialog.show(MainActivity.this, "Please Wait...",
                        "Fetching Stock Market Data...", true);

				//Fetch share data on another thread capable of updating the UI.
				new AsyncTask<String, Integer, ArrayList<String>>()
				{
		            @Override
		            protected void onProgressUpdate(Integer... progress)
		            {
		                super.onProgressUpdate(progress);
		            }


		            @Override
		            protected ArrayList<String> doInBackground(String... arg0)
		            {
		                return portfolio.calculateShareTotals();

		            }

		            @Override
		            protected void onPostExecute(ArrayList<String> result)
		            {
		            	dialog.dismiss();
		            	//txtOutput.setText(result);
		            	Intent intent = new Intent(MainActivity.this, StockListActivity.class);
		            	intent.putStringArrayListExtra("Values", result);
		            	startActivity(intent);
		            }

				}.execute("");
				
				//Disable the button
				btnShares.setEnabled(false);

				//Enable the button again
				btnShares.setEnabled(true);
			
			}
		});
        
        
       //Overriding OnClick behaviour with my own method.
        btnLostGained.setOnClickListener(new View.OnClickListener()
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
		                return portfolio.calculateLossGain();

		            }

		            @Override
		            protected void onPostExecute(String result)
		            {
		            	dialog.dismiss();
		            	//txtOutput.setText(result);
		            	Intent intent = new Intent(MainActivity.this, TextViewActivity.class);
		            	intent.putExtra("TextOutput", result);
		            	startActivity(intent);
		            }

				}.execute("");
				
				//Disable the button
				btnLostGained.setEnabled(false);
				
				
				
				
				//Enable the button again
				btnLostGained.setEnabled(true);
			}
			
		});
    
        //Overriding OnClick behaviour with my own method.
        btnBestWorst.setOnClickListener(new View.OnClickListener()
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
		                return portfolio.calculateCurrentPortfolio();
	
		            }
	
		            @Override
		            protected void onPostExecute(String result)
		            {
		            	dialog.dismiss();
		            	txtOutput.setText(result);
		            }
	
				}.execute("");
				
				//Disable the button
				btnBestWorst.setEnabled(false);
				

				//Enable the button again
				btnBestWorst.setEnabled(true);
			}
			
		});
    }
        
        
    public boolean checkInternetConnection()
    {
        //Check for Internet access
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo().isConnectedOrConnecting() == false)
        {
        	return false;
        }	
        return true;
    }

}

