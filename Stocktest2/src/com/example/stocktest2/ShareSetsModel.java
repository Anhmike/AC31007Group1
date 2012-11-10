package com.example.stocktest2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ShareSetsModel 
{
	//Map<String, ShareSet> SHARES = new HashMap<String, ShareSet>();
	ShareSet [] SHARES = new ShareSet[5];
	
	public ShareSetsModel() 
	{
		//Try with Array
		SHARES[0] = new ShareSet("BP plc", 192, "BP");
		SHARES[1] = new ShareSet("Experian Ordinary", 258,"EXPN");
		SHARES[2] = new ShareSet("HSBC Holdings plc", 343, "HSBA");
		SHARES[3] = new ShareSet("Marks & Spencer", 485, "MKS");
		SHARES[4] = new ShareSet("Smith & Nephew", 1219, "SN");
	}
	
	
	/*
	 * Date = 0
	 * Open = 1
	 * High = 2
	 * Low = 3
	 * Close = 4
	 * Volume = 5
	 * Adj Close = 6
	 * 
	 */
	public String calculateCurrentPortfolio() throws IOException, MalformedURLException

	{
		String text = "";
		
		double totalPortfolio = 0;
		String appTime = "";
		
		for (int i=0; i<SHARES.length; i++)
		{
			YahooFinanceAPI.getInstance().fetchAndParseShare(SHARES[i]);
			totalPortfolio += SHARES[i].getTotal();
		}
		
		appTime = SHARES[0].getTime();
		
		text = text + "\nThe total worth of your portfolio is \u00A3" + Math.round(totalPortfolio) + " at approximately " + appTime + "h.";
		
		return text;
	}
	
	public String calculateFridayPortfolio() throws IOException, MalformedURLException

	{
		double totalPortfolio = 0;
		
		for (int i=0; i<SHARES.length; i++)
		{
			YahooFinanceAPI.getInstance().fetchAndParseHistoryObject(SHARES[i]);
			totalPortfolio += SHARES[i].getPreviousTotal();
		}
		
		return ("\nThe total worth of your portfolio is<b> \u00A3" + (int)(Math.round(totalPortfolio))+ " </b> as of <b>" + YahooFinanceAPI.getInstance().getLastFriday() + "</b>.");
	}
	
	public String calculateLossGain() throws IOException, MalformedURLException

	{
		String text = "";
		
		double totalPortfolio = 0;
		double prevTotal = 0;
		
		for (int i=0; i<SHARES.length; i++)
		{
			YahooFinanceAPI.getInstance().fetchAndParseShare(SHARES[i]);
			YahooFinanceAPI.getInstance().fetchAndParseHistoryObject(SHARES[i]);
			totalPortfolio += SHARES[i].getTotal();
			prevTotal += SHARES[i].getPreviousTotal();
		}
		
		//String appTime = SHARES[0].getTime();
		//"Current: \u00A3"+(int)Math.round(totalPortfolio) + " at " + appTime + " \n Previous: \u00A3" + (int)Math.round(prevTotal) + 
		
		//Detect Loss or Gain
		double diff = prevTotal - totalPortfolio;
		if(diff > 0)
		{
			text += " As of <b>" + YahooFinanceAPI.getInstance().getLastFriday() + "</b> <br> you have <b>LOST <font COLOR=\"#FF0000\">\u00A3" + (int)Math.round(diff) + "</font></b>.";
		}
		else text += " As of <b>" + YahooFinanceAPI.getInstance().getLastFriday() + "</b> <br> you have <b>GAINED <font COLOR=\"#00FF00\">\u00A3" + (int)Math.round(diff*-1) + "</font></b>.";
		
		return text;
	}

	public ArrayList<String> calculateShareTotals() throws IOException, MalformedURLException

	{
		ArrayList <String> values = new ArrayList<String>();
		for (int i=0; i<SHARES.length; i++)
		{
			YahooFinanceAPI.getInstance().fetchAndParseShare(SHARES[i]);
			values.add(SHARES[i].getName());
			values.add(String.valueOf(SHARES[i].getShares()));
			values.add(String.valueOf(roundDouble(SHARES[i].getTotal())));
			//list.add(SHARES[i].toString());
		}
		
		return values;
	}
	
	public String detectPlummetRocket()
	{		
		String text = "";
		
		//Plummet or rocket of prices
		for (int i=0; i<SHARES.length; i++)
		{
			double  change = SHARES[i].getPlummetRocket();
			if(change > 0)
			{
				text = text + SHARES[i].getName() + "'s Shares rockets - \u00A3" + change  + ".\n";
			}
			else if (change == 0 )
			{
				text = text + SHARES[i].getName() + "'s Share Price has not change sufficiently.\n";
			}
			else text = text + SHARES[i].getName() + "'s Share Price plummets - \u00A3" + change  + ".\n";
			
		}
		
		return text;
	}
	
	public double roundDouble(double d) 
	{
	    DecimalFormat twoDForm = new DecimalFormat("#.##");
	    return Double.valueOf(twoDForm.format(d));
	}
}
