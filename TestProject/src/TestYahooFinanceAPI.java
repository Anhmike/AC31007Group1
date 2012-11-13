import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;
import com.example.stocktest2.*;


public class TestYahooFinanceAPI 
{
	ShareSet stock = new ShareSet("BP", 192, "BP");
	
	@Test
	public void test_FetchCurrentPrice() 
	{
		double delta = 1;
		try 
		{
			YahooFinanceAPI.getInstance().fetchAndParseShare(stock);	//Get current price for stock
		} 
		catch (MalformedURLException e) { } 
		catch (IOException e) { }
		assertEquals(/*Literal for ever-changing stock value*/424.30, stock.getCurrentPrice(), delta);
	}
	
	@Test
	public void test_HistoricalPrice() 
	{
		double delta = 1;
		try 
		{
			YahooFinanceAPI.getInstance().fetchAndParseHistoryObject(stock);	//Get historical (last friday) price for stock
		} 
		catch (MalformedURLException e) { } 
		catch (IOException e) { }
		assertEquals(/*Literal for last friday's actual stock price*/424.30, stock.getPreviousPrice(), delta);
	}
	
	@Test
	public void test_LastFridayDate() 
	{
		String fetchedDate = YahooFinanceAPI.getInstance().getLastFriday();	//Get Last Friday's date
		assertEquals(/*Literal for last friday's date*/"09/11/12", fetchedDate, 0);

	}

}
