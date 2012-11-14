package com.example.stocktest2.test;
import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.After;
import org.junit.Before;

//import junit.framework.*;

//import org.junit.Test;
import android.test.*;

import com.example.stocktest2.ShareSet;
import com.example.stocktest2.YahooFinanceAPI;

public class TestYahoo extends AndroidTestCase
{
	ShareSet testStock;
	
	@Before
	public void setUp() 
	{
		testStock = new ShareSet("BP", 192, "BP");
	}
	
	

	//@AndroidTest
	public void test_FetchCurrentPrice() 
	{
		double delta = 1;
		try 
		{
			YahooFinanceAPI.getInstance().fetchAndParseShare(testStock);	//Get current price for stock
		} 
		catch (MalformedURLException e) { } 
		catch (IOException e) { }
		assertEquals(/*Literal for ever-changing stock value*/4.2430, testStock.getCurrentPrice(), delta);
	}
	
	//@Test
	public void test_HistoricalPrice() 
	{
		double delta = 1;
		try 
		{
			YahooFinanceAPI.getInstance().fetchAndParseHistoryObject(testStock);	//Get historical (last friday) price for stock
		} 
		catch (MalformedURLException e) { } 
		catch (IOException e) { }
		assertEquals(/*Literal for last friday's actual stock price*/4.2430, testStock.getPreviousFridayPrice(), delta);
	}
	
	//@Test
	public void test_LastFridayDate() 
	{
		String fetchedDate = YahooFinanceAPI.getInstance().getLastFriday();	//Get Last Friday's date
		assertEquals(/*Literal for last friday's date*/"09/11/12", fetchedDate);

	}
	
	@After
	public void tearDown() 
	{
		testStock = null;
		System.gc();
	}
	
	
}
