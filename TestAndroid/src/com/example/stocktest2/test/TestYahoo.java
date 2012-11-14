package com.example.stocktest2.test;
import java.io.IOException;
import java.net.MalformedURLException;

//import junit.framework.*;

//import org.junit.Test;
import android.test.*;

import com.example.stocktest2.ShareSet;
import com.example.stocktest2.YahooFinanceAPI;

public class TestYahoo extends AndroidTestCase
{
ShareSet stock = new ShareSet("BP", 192, "BP");
	
	//@AndroidTest
	public void test_FetchCurrentPrice() 
	{
		double delta = 1;
		try 
		{
			YahooFinanceAPI.getInstance().fetchAndParseShare(stock);	//Get current price for stock
		} 
		catch (MalformedURLException e) { } 
		catch (IOException e) { }
		assertEquals(/*Literal for ever-changing stock value*/4.2430, stock.getCurrentPrice(), delta);
	}
	
	//@Test
	public void test_HistoricalPrice() 
	{
		double delta = 1;
		try 
		{
			YahooFinanceAPI.getInstance().fetchAndParseHistoryObject(stock);	//Get historical (last friday) price for stock
		} 
		catch (MalformedURLException e) { } 
		catch (IOException e) { }
		assertEquals(/*Literal for last friday's actual stock price*/4.2430, stock.getPreviousFridayPrice(), delta);
	}
	
	//@Test
	public void test_LastFridayDate() 
	{
		String fetchedDate = YahooFinanceAPI.getInstance().getLastFriday();	//Get Last Friday's date
		assertEquals(/*Literal for last friday's date*/"09/11/12", fetchedDate);

	}
	
	
}
