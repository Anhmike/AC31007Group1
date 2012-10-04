package com.example.stocktest2;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;


//Singleton boundary class representing the remote Yahoo! Finance API
//Requests for stock data are routed through this class, parsed and returned in order
//to create ShareSet objects for each Share in a portfolio
public class YahooFinanceAPI 
{

	private static YahooFinanceAPI instance;
	
	
	private YahooFinanceAPI() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public static YahooFinanceAPI getInstance()
	{
		
		if (instance == null)
			return new YahooFinanceAPI();
		else
			return instance;
	}
	
	public String[] fetchAndParse(String companyTicker)
	{
		URL url;	//URL object to access Yahoo! Finance
		try 
		{
			//s = Stock Symbol (+ ".L" = LON), l1 = Last Trade Price.
			url = new URL("http://finance.yahoo.com/d/quotes.csv?s=" + companyTicker + "&f=st1l1");
			
			//connect to Yahoo! finance.
			URLConnection urlConnection = url.openConnection();
			
			//Buffer the CVS file return from Yahoo!
			BufferedInputStream cvsBuffer = new BufferedInputStream(urlConnection.getInputStream());			
			ByteArrayBuffer byteArray = new ByteArrayBuffer(50);

			//Append to byteArray until there is no more data (-1)
			int current = 0;
			while( (current = cvsBuffer.read()) != -1)
			{
				byteArray.append((byte) current);
			}

			//Stores CVS into an unparsed string
			String stockCVS = new String(byteArray.toByteArray());
			
			//Split unparsed string into tokens at commas
			String[] rawTokens = (stockCVS.split(","));
			
			//Tidy up corresponding fields
			String stockSymbol = rawTokens[0].substring(1, rawTokens[0].length() - 1);	//Stock symbol (1st element) with removed "" quotation marks
			String stockTime = rawTokens[1].substring(1, rawTokens[1].length() - 3);	//Stock Time (2nd element) with removed "" quotation marks and 'Periods' (am/pm)
			String stockPrice = rawTokens[2];
			
			//Compensate stock time for US time-zone
			String[] sub = stockTime.split(":");
			int first = Integer.parseInt(sub[0]) + 5;
			String compensatedStockTime = Integer.toString(first) + ":" + sub[1];
			
			//Return tidied up token array (separated CVS fields)
			return (new String[] {stockSymbol, compensatedStockTime, stockPrice});
			
			
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		//Exception will have been called at this point
		//Return null, so client can take appropriate action
		return null;
	}
	

}
