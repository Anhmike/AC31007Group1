package com.example.stocktest2;

public class ShareSetsModel 
{
	//Map<String, ShareSet> SHARES = new HashMap<String, ShareSet>();
	ShareSet [] SHARES = new ShareSet[5];
	
	public ShareSetsModel() 
	{
		//Try with Array
		SHARES[0] = new ShareSet("BP plc", 192, "BP");
		SHARES[1] = new ShareSet("HSBC Holdings plc", 343, "HSBA");
		SHARES[2] = new ShareSet("Experian Ordinary", 258,"EXPN");
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
	public String calculateCurrentPortfolio()
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
	
	public String calculateFridayPortfolio()
	{
		String text = "";
		
		double totalPortfolio = 0;
		
		for (int i=0; i<SHARES.length; i++)
		{
			YahooFinanceAPI.getInstance().fetchAndParseHistoryObject(SHARES[i]);
			totalPortfolio += SHARES[i].getPreviousTotal();
		}
		
		text = text + "\nThe total worth of your portfolio was \u00A3" + Math.round(totalPortfolio) + " at the close of the stock market on " + YahooFinanceAPI.getInstance().getLastFriday() + ".";
		
		return text;
	}
	
	public String calculateLossGain()
	{
		String text = "";
		
		double totalPortfolio = 0;
		double prevTotal = 0;
		String appTime = "";
		
		for (int i=0; i<SHARES.length; i++)
		{
			YahooFinanceAPI.getInstance().fetchAndParseShare(SHARES[i]);
			text = text + SHARES[i].getName() + " - \u00A3" + SHARES[i].getTotal() + " - " + SHARES[i].getTime() + "h.\n";
			totalPortfolio += SHARES[i].getTotal();
			prevTotal += SHARES[i].getPreviousTotal();
		}
		
		appTime = SHARES[0].getTime();
		
		//Detect Loss or Gain
		double diff = prevTotal - totalPortfolio;
		if(diff > 0)
		{
			text += "\n Current Total: \u00A3" + totalPortfolio + "\n Previous Total: \u00A3"+ prevTotal + "\n You have lost \u00A3" +diff;
		}
		else text += "\n Current Total: \u00A3" + totalPortfolio + "\n Previous Total: \u00A3"+ prevTotal + "\n You have gained \u00A3" + (diff*-1);
		
		return text;
	}

	public String calculateShareTotals()
	{
		String text = "";
		
		for (int i=0; i<SHARES.length; i++)
		{
			YahooFinanceAPI.getInstance().fetchAndParseShare(SHARES[i]);
			text += SHARES[i].toString() + ";";
		}
		
		return text;
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
}
