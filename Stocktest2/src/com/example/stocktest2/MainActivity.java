package com.example.stocktest2;

import java.io.IOException;
import java.net.MalformedURLException;
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
//**********************************************************************************************************************//
/* Class MainActivity(1)  extends Activity class
 * Brief: The mainActivity thread of any android project. This class displays the main menu to the user
 * and determines their options selected bringing them to the main screen.
 * 
 * Methods Involved:
 * 1.0 onCreate - Sets up the UI on startup for user interaction, calls the UI elements an instantiates  
 * 1.1 btnTotal.onClickListener - overrides behavior in the case that the user presses the portfolio total button on the application.
 * 1.2 onClick - If behavior to be displayed to the user should they press the "Portfolio total" button.
 * 1.3 onProgressUpdate - the loading toast displayed to the user while loading the Friday total in the background of the application.
 * 1.4 doInBackground - fetches the Friday total from the portfolio object.
 * 1.5 onPostExecute - bridges the connection between the main menu activity and the portfolio total activity.
 *   
 * 1.6 btnShare.onClickListener - overrides behavior in the case that the user presses the current share values button.
 * 1.7 onClick - If behavior to be displayed to the user should they press the "Share values" button.
 * 1.8 onProgressUpdate - the loading toast displayed to the user while loading the arrayList of share values in the background of the application.
 * 1.9 doInBackground - fetches the Friday total from the portfolio object.
 * 1.10 onPostExecute - bridges the connection between the main menu activity and the portfolio total activity.
 *  
 * 1.11 btnLostGained.onClickListener - overrides behavior in the case that the user presses the loss/gained button on the application.
 * 1.12 onClick - If behavior to be displayed to the user should they press the "Loss/gained" button.
 * 1.13 onProgressUpdate - the loading toast displayed to the user while loading the total lost/gained on the portfolio value in the background of the application.
 * 1.14 doInBackground - fetches the Friday total from the portfolio object.
 * 1.15 onPostExecute - bridges the connection between the main menu activity and the portfolio total activity.
 * 
 * 1.16 checkInternetConnection - determines if there is a valid connection to the internet in order to retrieve data.
 */
public class MainActivity extends Activity 
{	
	
	//button variables for the main menu.
	private Button btnTotal;
	
	private Button btnShares;
	
	private Button btnLostGained;
	
	// progress toast after a button has been pressed.
	private ProgressDialog dialog;
	
	//instantiated class to hold the portfolio details gathered from the Yahoo API
	private ShareSetsModel portfolio = new ShareSetsModel();
	
		
    @Override
    //**********************************************Start of onCreate 1.0***********************************************************//
    /*Class onCreate
     *Param - Bundle savedInstanceState - the saved state of the app, since no data has been passed to the app since the last startup
     *									this value will be null.
	 *Return - none.
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    public void onCreate(Bundle savedInstanceState) 
    {
    	//create an instance state to save data to (this remains null).
        super.onCreate(savedInstanceState);
        //call the main menu layout view from it's xml file.
        setContentView(R.layout.activity_main);	
        //force the screen to be locked into a portrait view.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        //Assign UI ID's to there respective buttons.
        btnTotal = (Button)findViewById(R.id.getTotal);
        btnShares = (Button)findViewById(R.id.getShares );
        btnLostGained = (Button)findViewById(R.id.getLostGained);
        
        
       //***************************************start of setOnClickListener for Friday portfolio total 1.1 ********************************************//
       /* btnTotal.setOnClickListener- overrides the behavior of the Portfolio total button to the behavior we specify below.
        * @param - new View.onClickListener: new instance of OnClickListener, listens for the user to interact and begin
        * 									 desired behavior ie. display a screen for the portfolio total.
        * @return - none.
        */
        btnTotal.setOnClickListener(new View.OnClickListener()
        {	
        	//***********************************Start of Onclick Method 1.2******************************************************************//
			/* onClick(view v) - if the button is pressed then begin the behavior below in response to user interaction.
			 * @param - View v: The listener responsible for waiting for user interaction on their mobile device.
			 * 
			 * @return - none.
			 * (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
        	public void onClick(View v)
			{	
        		
        		//if no connection is avaliable display this message in a toast.
        		if (!checkInternetConnection())
				{
					Toast.makeText(MainActivity.this, "No Feed Available...", Toast.LENGTH_SHORT).show();
					return;
				}//endif
        		
        		
				//Set up "Loading" dialog
				dialog = ProgressDialog.show(MainActivity.this, "Please Wait...",
                        "Fetching Stock Market Data...", true);

				//Fetch share data on another thread capable of updating the UI.
				new AsyncTask<String, Integer, String>()
				{

		            @Override
		            //provide a loading bar on the button press.(1.3)
		            protected void onProgressUpdate(Integer... progress)
		            {
		                super.onProgressUpdate(progress);
		            }
		            
		            //**************************************Start of doInBackground Method 1.4********************************************************//
		            @Override
		            /*doInBackground -calculate the friday total from the stored portfolio data.
		             * @param - (String... arg0): Calls arguements.
		             * 
		             * @return portfolio.calculateFridayPortfolio() : the friday total for user's the portfolio.
		             * 												: if connection has failed provide no data.
		             * (non-Javadoc)
		             * @see android.os.AsyncTask#doInBackground(Params[])
		             */
		            protected String doInBackground(String... arg0)
		            {
		            	
		                try 
		                {
							return portfolio.calculateFridayPortfolio();
						} 
		                catch (MalformedURLException e) {}
		                catch (IOException e) {}
		                
		                return null;

		            }
		            
		            //**********************************end of doInBackground Method***************************************************//
		            
		            

		            @Override
		            //***********************************************Start of onPostExecute Method 1.5***************************************//
		            /* onPostExecute - display this message in the case that there is no feed data avaliable for friday's total.
		             * @param String result - the friday total taken from the portfolio object.
		             * 
		             * @return -none.
		             * (non-Javadoc)
		             * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		             */
		            protected void onPostExecute(String result)
		            {
		            	dialog.dismiss();
				        
		            	//show this message if nothing is taken from the portfolio object.
		            	if (result == null)
		            	{
		            		Toast.makeText(MainActivity.this, "No Feed Available...", Toast.LENGTH_SHORT).show();
		            		return;
		            	}//endif
		            	
		            	//Intents act as a bridge between activities (the main menu and display friday total in this case)
		            	//The two activities to be linked.
		            	Intent intent = new Intent(MainActivity.this, TextViewActivity.class);
		            	//Variables sent to the new activity.
		            	intent.putExtra("TextOutput", result);
		            	//start the activity.
		            	startActivity(intent);
		            }
		          //***********************************end of OnPostExecute Method***************************************************//
		            
		            
				}.execute("");
				
				
				//***********************************End of Onclick Method******************************************************************//
				
				//Disable the button
				btnTotal.setEnabled(false);

				//Enable the button again
				btnTotal.setEnabled(true);
			}
			
		});
        
        //***************************************End of onClickListener for Friday Total*********************************************//
        
        //***************************************Start of onClickListener for share totals 1.6*********************************************//
        /*
         * btnShare.setOnClickListener - the main menu behavior for the share Total button.
         * @param new View.OnClickListener - onClickListener being over-riden to perform the behavior for getting the portfolio total.
         * @return none.
         */
        btnShares.setOnClickListener(new View.OnClickListener()
        {
        /*	onClick - behavior to be preformed if the "share total" button is pressed.
         * 	@param View v - the activity view to be changed.
         * 
		 * 	@return - if no internet connection display the mssage "No Feed avaliable".
		 * 			  else change activity to the share total list.
         * (non-Javadoc)
         * @see android.view.View.OnClickListener#onClick(android.view.View)
         */
        	
        //************************start of onClick 1.7*************************************************************************//
        	public void onClick(View v)
			{
				if (!checkInternetConnection())
				{
					Toast.makeText(MainActivity.this, "No Feed Available...", Toast.LENGTH_SHORT).show();
					return;
				}//endif

				//Set up "Loading" dialog
				dialog = ProgressDialog.show(MainActivity.this, "Please Wait...",
                        "Fetching Stock Market Data...", true);

				//Fetch share data on another thread capable of updating the UI.
				new AsyncTask<String, Integer, ArrayList<String>>()
				{
		            @Override
		            
		            //display a loading bar to the user while background operations are being performed.(1.8)
		            protected void onProgressUpdate(Integer... progress)
		            {
		                super.onProgressUpdate(progress);
		            }

		          //***************************************Start of doInBackground Method 1.9*********************************************//
		            @Override
		            /*
		             * doInBackground - calculate the share totals for each share, load this up to send to the share total activity screen.
		             * @param - arguments to be taken (currently none)
		             * @return - the portfolio information for share totals
		             * 			 null if an error occurs.
		             * (non-Javadoc)
		             * @see android.os.AsyncTask#doInBackground(Params[])
		             */
		            protected ArrayList<String> doInBackground(String... arg0)
		            {
		                try 
		                {
							return portfolio.calculateShareTotals();
						} 
		                catch (MalformedURLException e) {}
		                catch (IOException e) {}
		                
		                return null;

		            }
		          //***************************************End of doInBackground Method*********************************************//
		            
		            
		          //***************************************Start of onPostExecute 1.10***************************************************//
		          /*
		           * onPostExecute - Preforms the actions of providing a bridge to the share total activities, or displaying a message if
		           * 				 no feed information is avaliable.
		           * @param - An arrayList of String objects holding the current value for each share.
		           * @return - none if no connection is avaliable (share value is returned as null).
		           * 		   Display the activity displaying all share values in list form if the information is avaliable.
		           * (non-Javadoc)
		           * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		           */
		            @Override
		            protected void onPostExecute(ArrayList<String> result)
		            {
		            	dialog.dismiss();
		        
		            	if (result == null)
		            	{
		            		Toast.makeText(MainActivity.this, "No Feed Available...", Toast.LENGTH_SHORT).show();
		            		return;
		            	}
		            	
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
        	//***************************************End of onPostExecute Method*********************************************//
		});
        //*************************************End of onClick*********************************************************************//
      //***************************************End of onClickListener for share totals*********************************************//
        
        
        
        
        
      //***************************************Start of onClickListener for Total Lost/Gained button 1.11*********************************************//
        
       
       /*btnLostGained.setOnClickListner - provides behavior for the total lost/gained on the portfolio.
        * @param - View.OnClickListener - onClickListener being over-ridden to perform the behavior for getting the amount lost/gained on the portfolio.
        * @return - "No Feed Available" message if there is no Internet connection
        * 			The activity layout for the total lost/gained on the portfolio if there is a connection and data stored.
        */
        btnLostGained.setOnClickListener(new View.OnClickListener()
        {
        	//***********************************Start of Onclick Method 1.12******************************************************************//
			/*onClick -  the button behavior for "Lost/Gained".
			 * @param - the activity to be changed.
			 * @ return - "No Feed Available" message if there is no Internet connection
			 * 			   The activity layout for the total lost/gained on the portfolio if there is a connection and data stored.
			 * (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			public void onClick(View v)
			{
				//if no internet connection display the following message to the user.
				if (!checkInternetConnection())
				{
					Toast.makeText(MainActivity.this, "No Feed Available...", Toast.LENGTH_SHORT).show();
					return;
				}

				//Set up "Loading" dialog
				dialog = ProgressDialog.show(MainActivity.this, "Please Wait...",
                        "Fetching Stock Market Data...", true);

				//Fetch share data on another thread capable of updating the UI.
				new AsyncTask<String, Integer, String>()
				{
					
		            @Override
		            //display a loading bar to the user while background operations are being performed.(1.13)
		            protected void onProgressUpdate(Integer... progress)
		            {
		                super.onProgressUpdate(progress);
		            }

		            
		            //***************************************Start of doInBackground Method 1.14*********************************************//
		            @Override
		            /*
		             * doInBackground - calculate the total loss/gain in value over all shares, load this up to send to the loss/gain activity screen.
		             * @param - arguments to be taken (currently none)
		             * @return - the portfolio information for share totals
		             * 			 null if an error occurs.
		             * (non-Javadoc)
		             */
		            protected String doInBackground(String... arg0)
		            {
		                try 
		                {
							return portfolio.calculateLossGain();
						} 
		                catch (MalformedURLException e) {} 
		                catch (IOException e) {}
		                
		                return null;
		            }
		          //***************************************End of doInBackground Method*********************************************//
		            
		            
		            
		            
		          //***************************************Start of onPostExecute 1.15***************************************************//
			          /*
			           * onPostExecute - Performs the actions of providing a bridge to the share total activities, or displaying a message if
			           * 				 no feed information is available.
			           * @param - An arrayList of String objects holding the current value for each share.
			           * @return - none if no connection is available (share value is returned as null).
			           * 		   Display the activity displaying the loss/gain of all shares.
			           * (non-Javadoc)
			           * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
			           */
		            protected void onPostExecute(String result)
		            {
		            	dialog.dismiss();
		            	
		            	if (result == null)
		            	{
		            		Toast.makeText(MainActivity.this, "No Feed Available...", Toast.LENGTH_SHORT).show();
		            		return;
		            	}
		            	
		            	Intent intent = new Intent(MainActivity.this, TextViewActivity.class);
		            	intent.putExtra("TextOutput", result);
		            	startActivity(intent);
		            }
		          //***************************************End of onPostExecute***************************************************//
		            
		            
				}.execute("");
				//***********************************End of Onclick Method******************************************************************//
				//Disable the button
				btnLostGained.setEnabled(false);
				
				
				
				
				//Enable the button again
				btnLostGained.setEnabled(true);
			}
			
		});
    
       
    }
    //*********************************************start of method checkInternetConnection 1.16**********************************************//   
    /*boolean checkInternetConnection - checks to determine that the mobile device is actually connected to the internet.
     * @param - none.
     * @return false if there is no connection available.
     * 		   true if there is an available Internet connection.
     */
    public boolean checkInternetConnection()
    {
        
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        
        if (cm.getActiveNetworkInfo().isConnectedOrConnecting() == false)
        {
        	return false;
        }	
        return true;
    }
    //******************************************end of method checkInternetConnection****************************************************//

}//***************************************************end of Class MainActivity***********************************************************//

