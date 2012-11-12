package com.example.stocktest2;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.*;
import org.apache.http.util.*;


/** Class YahooFinanceAPI
* Brief: Singleton boundary class representing the remote Yahoo! Finance API
* Requests for stock data are routed through this class, parsed and returned in order
* to create ShareSet objects for each Share in a portfolio
* 
* Methods Involved:
* 1.0 getInstance - Returns the single static instance of this object.  Ensures only one instance is ever created.  
* 1.1 fetchAndShare(ShareSet) - Queries the Yahoo Finance API for current stock data on the passed Stock object,
* 								parses it and populates the stock object.
* 1.2 fetchAndShareHistory(ShareSet) - Gets the historical data of the stock (i.e. last friday's price) and populates the passed stock.
* 1.3 getLastFriday() - Returns last friday's market close date.
* 1.4 timeParsing - Returns the time in the desired format.
*/
public class YahooFinanceAPI 
{

	//Static instance of this object (Singleton) 
	private static YahooFinanceAPI instance;
	
	
	private YahooFinanceAPI() 
	{
		//Private constructor.  Singleton.
	}
	
	//Implements Singleton Design Pattern, by returning only one shared instance of itself at a time.
	public static YahooFinanceAPI getInstance()
	{
		
		if (instance == null)
			return new YahooFinanceAPI();
		else
			return instance;
	}
		
	/**
	 * Queries the Yahoo Finance API for current stock data on the passed Stock object,
	 * parses it and populates the stock object.
	 * 
	 * @param ShareSet stock
	 * @return true if successful
	 * @throws IOException
	 * @throws MalformedURLException
	 * Throws exceptions to notify UI to display "No Feed Available"
	 */
	public boolean fetchAndParseShare(ShareSet stock) throws IOException, MalformedURLException
	{
		URL url;	//URL object to access Yahoo! Finance
		/* 
		 * t1 = Last Trade Time
		 * l1 = Last Trade Price
		 * g = Daily High
		 * h = Daily Low
		 * v = Current Volume
		 * p = Previous Close Price
		 * p2 = Change from Previous Close in Percent
		 */
		url = new URL("http://finance.yahoo.com/d/quotes.csv?s=" + stock.getTicker() + ".L&f=t1l1ghvpp2"); 
		
		//connect to Yahoo! finance.
		URLConnection urlConnection = url.openConnection();
		
		//Buffer the CSV file return from Yahoo!
		BufferedInputStream csvBuffer = new BufferedInputStream(urlConnection.getInputStream());			
		ByteArrayBuffer byteArray = new ByteArrayBuffer(100);

		//Append to byteArray until there is no more data (-1)
		int current = 0;
		while( (current = csvBuffer.read()) != -1)
		{
			byteArray.append((byte) current);
		}

		//Stores CVS into an unparsed string
		String stockCSV = new String(byteArray.toByteArray());
		
		//Split unparsed string into tokens at commas
		String[] rawTokens = (stockCSV.split(","));
		
		//Tidy up corresponding fields
		String stockTime = timeParsing(rawTokens[0].substring(1, rawTokens[0].length() - 3));	//Stock trade time (1st element) with removed "" quotation marks and 'Periods' (am/pm)
		Double stockPrice = Double.parseDouble(rawTokens[1]) / 100;	//Divide by 100 to get in pounds £
		Double dailyHigh = Double.parseDouble(rawTokens[2]) / 100;  // Parse the daily high as Double
		Double dailyLow = Double.parseDouble(rawTokens[3]) / 100;  // Parse the daily low as Double
		Long volume = Long.parseLong(rawTokens[4]);
		Double prevPrice = Double.parseDouble(rawTokens[5]) / 100;
		String change = rawTokens[6].substring(1, rawTokens[6].length() - 1);
		
		stock.setShareSet(stockTime, stockPrice, dailyHigh, dailyLow, volume, prevPrice, change);
		
		//Return true flag for proper execution
		return true;
		
		
	}
	
	/**
	 * Returns the time in the desired format as a String.
	 */
	public String timeParsing(String theTime)
	{
		//Compensate stock time for US time-zone
		String[] sub = theTime.split(":");
		int first = Integer.parseInt(sub[0]) + 5;
		return (Integer.toString(first) + ":" + sub[1]);
	}

	/**
	 * Returns the date of last Friday's stock market close, in the appropriate date format.
	 */
	public String getLastFriday()
	{
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

        return  sdf.format(cal.getTime());    
    }
	
	
	/****************************
	 * Method to get the historical data using ichart URL - returns the content of the ichart query ,
	 * thus the data needed in a string, which could be parsed
	 * 
	 * e.g. ichart query: 
	 * http://ichart.finance.yahoo.com/table.csv?s=TSCO.L&a=09&b=26&c=2012&d=09&e=26&f=2012&g=d&ignore=.csv
	 * 
	 * Check Out This here:
	 * http://code.google.com/p/yahoo-finance-managed/wiki/csvHistQuotesDownload
	 * 
	 * @param Stock object
	 * @return true if successful
	 */
	public boolean fetchAndParseHistoryObject(ShareSet stock) throws IOException, MalformedURLException

	{

		String[] dateTokens = (getLastFriday().split("/"));
		int month = ( Integer.parseInt(dateTokens[1]) - 1);
		
		String url_text ="http://ichart.finance.yahoo.com/table.csv?s=" + stock.getTicker() + 
				".L&a="+ month +"&b="+ dateTokens[0]+"&c=20"+dateTokens[2]+"&d="+ month +"&e="+dateTokens[0]+"&f=20"+dateTokens[2]+"&g=d&ignore=.csv"; 
		
		 HttpClient client = new DefaultHttpClient();
		 HttpGet request = new HttpGet(url_text);
         // Get the response
         ResponseHandler<String> responseHandler = new BasicResponseHandler();
         String response_str = client.execute(request, responseHandler);
         
            /*
			 * Date = 0
			 * Open = 1
			 * High = 2
			 * Low = 3
			 * Close = 4
			 * Volume = 5
			 * Adj Close = 6
			 */

         String [] lines = response_str.split("\n");
         
         String[] data = (lines[1].split(","));
		
		//Tidy up corresponding fields
		Double price = Double.parseDouble(data[4]) / 100;	//Divide by 100 to get in pounds 
		Long volume = Long.parseLong(data[5]);
		
		stock.setShareHistory(price, volume);
         
		return true;
		
	}
}


