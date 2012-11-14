package com.example.stocktest2.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;

import com.example.stocktest2.ShareSetsModel;
import com.example.stocktest2.YahooFinanceAPI;

import android.test.*;

public class TestPortfolio extends AndroidTestCase
{
	private ShareSetsModel portfolioRight;
	private ShareSetsModel portfolioWrong;
	
	@Before
	public void setUp() 
	{
		portfolioRight = new ShareSetsModel("TESCO", 122, "TSCO");
		portfolioWrong = new ShareSetsModel("AAAA", 152, "AAAA");
	}
	
	
	
	public void testWrongFridayPortfolio()
	{
		String result = "";
		try 
		{ 
			YahooFinanceAPI.getInstance().fetchAndParseShare(portfolioWrong.SHARES[0]);	//Get current price for stock
			result = portfolioWrong.calculateFridayPortfolio();
		} 
		catch (MalformedURLException e) { } 
		catch (IOException e) { }
		assertEquals(result, "");
	}
	
	public void testRightFridayPortfolio()
	{
		String result = "";
		try 
		{ 
			YahooFinanceAPI.getInstance().fetchAndParseShare(portfolioRight.SHARES[0]);	//Get current price for stock
			result = portfolioRight.calculateFridayPortfolio();
		} 
		catch (MalformedURLException e) { } 
		catch (IOException e) { }
		assertFalse(result == "");
	}
	
	public void testWrongLossGain()
	{
		String result = "";
		try 
		{ 
			YahooFinanceAPI.getInstance().fetchAndParseShare(portfolioWrong.SHARES[0]);	//Get current price for stock
			result = portfolioWrong.calculateLossGain();
		} 
		catch (MalformedURLException e) { } 
		catch (IOException e) { }
		assertEquals(result, "");
	}
	
	public void testRightLossGain()
	{
		String result = "";
		try 
		{ 
			YahooFinanceAPI.getInstance().fetchAndParseShare(portfolioRight.SHARES[0]);	//Get current price for stock
			result = portfolioRight.calculateLossGain();
		} 
		catch (MalformedURLException e) { } 
		catch (IOException e) { }
		assertFalse(result == "");
	}
	
	public void testWrongShareTotals()
	{
		ArrayList<String> result = new ArrayList<String>();
		try 
		{ 
			YahooFinanceAPI.getInstance().fetchAndParseShare(portfolioWrong.SHARES[0]);	//Get current price for stock
			result = portfolioWrong.calculateShareTotals();
		} 
		catch (MalformedURLException e) { } 
		catch (IOException e) { }
		assertEquals(result.size(), 0);
	}
	
	public void testRightShareTotals()
	{
		ArrayList<String> result = new ArrayList<String>();
		try 
		{ 
			YahooFinanceAPI.getInstance().fetchAndParseShare(portfolioRight.SHARES[0]);	//Get current price for stock
			result = portfolioRight.calculateShareTotals();
		} 
		catch (MalformedURLException e) { } 
		catch (IOException e) { }
		assertFalse(result.get(2) == "N/A");
	}
	
	public void testRounding()
	{
		double delta = 0.01;
		double innerResult = ShareSetsModel.roundDouble(2.35649);
		double result = 2.36;
		assertEquals(result, innerResult, delta);
	}
	
	
	
	@After
	public void tearDown() 
	{
		portfolioRight = null;
		portfolioWrong = null;
		System.gc();
	}
}

