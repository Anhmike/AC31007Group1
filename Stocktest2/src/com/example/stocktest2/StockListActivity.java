package com.example.stocktest2;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

/**
 * stockListActivity - sets up the xml for the layout of the display screens for the user and populates them with relevant information.
 * 
 * 
 * Methods Used - 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class StockListActivity extends Activity
{
	
    private ArrayList<String> list;
    private ProgressDialog dialog;
    

  //********************************************Start of onCreate***********************************************************//
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            //Get the Extras from MainActivity
        list = getIntent().getStringArrayListExtra("Values");
        
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
                
                    //Set up a UI List
                TableLayout table = (TableLayout) findViewById(R.id.table);
                for(int i=0;i<list.size();i+=3)
                {
	                    TableRow row= new TableRow(StockListActivity.this);
	                    
	                    TextView col1  = new TextView(StockListActivity.this);
	                    col1.setText(list.get(i));
	                    col1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_text_view));
	                    col1.setPadding(10,5,5,5);
	                    col1.setTextSize(18);
	                    col1.setGravity(Gravity.CENTER_VERTICAL);
	                    col1.setTextColor(Color.parseColor("#f3bf7f"));
	                    col1.setTypeface(Typeface.SANS_SERIF , Typeface.BOLD);
	                    LayoutParams lp = new LayoutParams((int)(LayoutParams.MATCH_PARENT * 0.4),LayoutParams.FILL_PARENT);
	                    col1.setLayoutParams(lp);
	                   
	                    TextView col2  = new TextView(StockListActivity.this);
	                    col2.setText(list.get(i+1));
	                    col2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_text_view));
	                    col2.setGravity(Gravity.CENTER);
	                    col2.setPadding(5,5,5,5);
	                    col2.setTextSize(18);
	                    col2.setTextColor(Color.parseColor("#f3bf7f"));
	                    col2.setTypeface(Typeface.SANS_SERIF , Typeface.BOLD);
	                    lp = new LayoutParams((int)(LayoutParams.MATCH_PARENT * 0.27),LayoutParams.FILL_PARENT);
	                    col2.setLayoutParams(lp);
	                    
	                    TextView col3  = new TextView(StockListActivity.this);
	                    col3.setText("\u00A3" + list.get(i+2));
	                    col3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_text_view));
	                    col3.setGravity(Gravity.CENTER);
	                    col3.setTextColor(Color.parseColor("#f3bf7f"));
	                    col3.setTextSize(18);
	                    col3.setTypeface(Typeface.SANS_SERIF , Typeface.BOLD);
	                    col3.setPadding(5,5,5,5);
	                    lp = new LayoutParams((int)(LayoutParams.MATCH_PARENT * 0.33),LayoutParams.FILL_PARENT);
	                    col3.setLayoutParams(lp);
	                    
	                    row.addView(col1);
	                    row.addView(col2);
	                    row.addView(col3);
	                    table.addView(row);
                }
            }

        }.execute("");



    }
  //********************************************End of onCreate***********************************************************//

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
