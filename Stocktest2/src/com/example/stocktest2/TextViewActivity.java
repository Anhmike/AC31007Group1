package com.example.stocktest2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;


public class TextViewActivity extends Activity
{

	public TextViewActivity() 
	{
		// TODO Auto-generated constructor stub
	}
	
	  	private String textOutput;
	    private ProgressDialog dialog;
	    

	    /** Called when the activity is first created.
	     * @param savedInstanceState
	     */
	    @Override
	    public void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.text_view);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

	            //Get the Extras from MainActivity
	        textOutput = getIntent().getStringExtra("TextOutput");
	        
	        dialog = ProgressDialog.show(this, "Please Wait...",
	                        "Searching Words...", true);
	        
	        //Calculates list items on separate GUI thread.  Shows progress dialog during...
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
	                //TODO Main List Work
	            	return "Finished!";

	            }

	            @Override
	            protected void onPostExecute(String result)
	            {
	                dialog.dismiss();
	                
	                    //Set up a UI TextView
	               LinearLayout layout = (LinearLayout)findViewById(R.id.frame);
	               layout.setGravity(Gravity.CENTER_VERTICAL);
	               TextView totalText = (TextView)findViewById(R.id.output);
	               totalText.setText(Html.fromHtml(textOutput));
	            }

	        }.execute("");



	    }

	    @Override
	    public void onBackPressed()
	    {
	        Intent intent = new Intent(this, MainActivity.class);
	        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        startActivity(intent);
	    }



	    @Override
	    public void onDestroy()
	    {
	        super.onDestroy();
	    }

}
