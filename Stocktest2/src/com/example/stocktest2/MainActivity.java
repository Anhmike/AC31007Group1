package com.example.stocktest2;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.http.util.ByteArrayBuffer;

import android.os.Bundle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.view.View;
import android.widget.*;
import java.lang.Math.*;



@TargetApi(9)
public class MainActivity extends Activity {
	
	TextView symbolOut;
	TextView priceOut;
	Button getQuote;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        
        //UI stuff.
        symbolOut = (TextView)findViewById(R.id.stockSymbol);
        priceOut = (TextView)findViewById(R.id.priceView);
        
        //UI button behaviour.
        getQuote = (Button)findViewById(R.id.getQuoteButton);
        //Overriding OnClick behaviour with my own method.
        getQuote.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View v){
				
				//variables I need to override and add the functionality
				//I want to the button.
				String stockText; //holds the data from the CVS file.
				String[] tokens;  //array holding each "word" in the CVS file after being delimited.
				String tempstockSymbol; //holds the stock symbol but with "" quotation marks.
				String stockPrice; //holds the stock price doesnt need to be delimited here.
				String stockSymbol; //stock symbol after the quotation marks are removed.
				String tempstocktime;
				String stocktime;
				String stocktimewithoutmidians;
				int current;
				
				
				
				//url object to hold yahoo finance url.
				URL url;
				//assign the address to the url variable
					
					try {
						//s = stock symbol, l1 = last trade price.
						url = new URL ("http://finance.yahoo.com/d/quotes.csv?s=TSCO.L&f=st1l1");
						
						
						
						//connect to yahoo finance.
						URLConnection urlconnect = url.openConnection();
						//Create a stream to hold the CVS file data from yahoo.
						InputStream stream = new BufferedInputStream(urlconnect.getInputStream());
				
						//Buffer it up.
						BufferedInputStream stuff = new BufferedInputStream(stream);
						
						
						ByteArrayBuffer mybytearray = new ByteArrayBuffer(50);
						
						//check to see where the stream of data ends in the byte array. -1 if
						//there is no more data.
						
						current = 0;
						
						while((current = stuff.read())!= -1)
						{
							mybytearray.append((byte) current);
							
						}
						
						
						
						stockText = new String(mybytearray.toByteArray());
						
						tokens = (stockText.split(","));
						
						tempstockSymbol = tokens[0];
						stockPrice = tokens[2];
						tempstocktime = tokens[1];
						
						stockSymbol = tempstockSymbol.substring(1, tempstockSymbol.length() -1);
						stocktime = tempstocktime.substring(1, tempstocktime.length() -1 );
						stocktimewithoutmidians = stocktime.substring(0, stocktime.length() -2);
						
						
						long rounded = Math.round(Double.parseDouble(stockPrice));
						
						String [] sub = stocktimewithoutmidians.split(":");
						
						int first = Integer.parseInt(sub[0]) + 5;
						
						stocktimewithoutmidians = Integer.toString(first) + ":" + sub[1];
						
						
						//send these "words" to the UI text fields.
						
						symbolOut.setText("\nThe stock price for Tesco is Åí"+ stockPrice +" (rounded Åí"+rounded+") at "+stocktimewithoutmidians+"h.");
						
						//priceOut.setText(stockPrice);
						
						
						
						
						
						
						
				
						
						
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
}});
        
}}
