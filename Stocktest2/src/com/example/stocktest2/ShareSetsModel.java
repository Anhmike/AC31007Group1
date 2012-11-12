package com.example.stocktest2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/*ShareSetsModel - Creates a new Array of ShareSet objects, populates their entries with hard coded information ie
 * 				   current number of stocks in a company, the company's share ticker, and company name.
 * 
 * 				   Uses the information of the ShareSet objects to calculate and display the relevant information back to the user.
 * Methods Used:
 * 				   3.0 calculateCurrentPortfolio - Determines the total value of the current portfolio.
 * 				   3.1 calculateFridayPortfolio - Retrieves the previous stock close value of last friday @ 5pm.
 * 				   3.2 calculateLossGain - Determines the total lost/gained over the entire portfolio by comparing current value against stock close of previous week's value.
 * 				   3.3 calculateShareTotals - Calculates the total value of each share and inserts this new value into a list.
 * 				   3.4 detectPlummetRocket - detects is a paticular stock is on a run or fall which is defined as a share increasing in 10% value over a week.
 *  						  				 Or if a stock value has decreased by 5% over a week.
 *  			   3.5 roundDouble - rounds the value gathered from the Yahoo API to 2 decimal places.
 */
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
	
	//********************************************Start of calculateCurrentPortfolio 3.0***********************************************************************//
	/*
	 * calculateCurrentPortfolio - Determines the total value of the current portfolio by adding multiplying no of shares x current value of shares and summing them
	 * 							   all together.
	 * @param -none.
	 * @return - A message displaying the current rounded total to the user.
	 */
	public String calculateCurrentPortfolio() throws IOException, MalformedURLException

	{
		//local variables to and their initalised values.
		String text = "";
		double totalPortfolio = 0;
		String appTime = "";
		
		//get the share value for each company then sum them up.
		for (int i=0; i<SHARES.length; i++)
		{
			YahooFinanceAPI.getInstance().fetchAndParseShare(SHARES[i]);
			totalPortfolio += SHARES[i].getTotal();
		}//endfor
		
		appTime = SHARES[0].getTime();
		
		//Text to be displayed to the user.
		text = text + "\nThe total worth of your portfolio is \u00A3" + Math.round(totalPortfolio) + " at approximately " + appTime + "h.";
		
		return text;
	}

	
	//********************************************End of calculateCurrentPortfolio 3.0***********************************************************************//


	
	//********************************************Start of calculateFridayPortfolio 3.1***********************************************************************//
	/*
	 * calculateFridayPortfolio - retrieves the portfolio sum value as of the share price at stock close of the previous week's friday @ 5.00PM.
	 * 
	 * @param none
	 * @return Message with the previous friday's total.
	 */
	public String calculateFridayPortfolio() throws IOException, MalformedURLException

	{
		double totalPortfolio = 0;
		
		//get the previous friday total for each share, and sum them.
		for (int i=0; i<SHARES.length; i++)
		{
			YahooFinanceAPI.getInstance().fetchAndParseHistoryObject(SHARES[i]);
			totalPortfolio += SHARES[i].getPreviousTotal();
		}//endfor
		
		return ("\nThe total worth of your portfolio is<b> \u00A3" + (int)(Math.round(totalPortfolio))+ " </b> as of <b>" + YahooFinanceAPI.getInstance().getLastFriday() + "</b>.");
	}
	

	
	//********************************************End of calculateFridayPortfolio 3.1***********************************************************************//
	
	

	
	//********************************************Start of calculateLossGain 3.2***********************************************************************//
	/*
	 * calculateLossGain - Determines the value added or lost on the current portfolio by comparing against the previous week stock close values.
	 * 
	 * @param - none.
	 * @return - Message to the user on how much the have lost/gained comparing against the previous week's close value.
	 */
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
		
		//Detect Loss or Gain
		double diff = prevTotal - totalPortfolio;
		if(diff > 0)
		{
			text += " As of <b>" + YahooFinanceAPI.getInstance().getLastFriday() + "</b> <br> you have <b>LOST <font COLOR=\"#FF0000\">\u00A3" + (int)Math.round(diff) + "</font></b>.";
		}//endif
		else text += " As of <b>" + YahooFinanceAPI.getInstance().getLastFriday() + "</b> <br> you have <b>GAINED <font COLOR=\"#00FF00\">\u00A3" + (int)Math.round(diff*-1) + "</font></b>.";
		//endelse
		
		return text;
	}

	//********************************************End of calculateCurrentPortfolio 3.2***********************************************************************//
	
	
	
	
	
	

	
	//********************************************Start of calculateShareTotals 3.3***********************************************************************//
	/*
	 * calculateShareTotals - Calculates the total value of each share and inserts this new value into a list.
	 * @param - none.
	 * @return - the value for each share in the list.
	 */
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
		}//endfor
		
		return values;
	}
	
	//********************************************End of calculateCurrentPortfolio 3.3***********************************************************************//
	
	
	
	//********************************************Start of detectPlummentRocket 3.4***********************************************************************//
	/*
	 * detectPlummentRocket - detects is a paticular stock is on a run or fall which is defined as a share increasing in 10% value over a week.
	 * 						  Or if a stock value has decreased by 5% over a week.
	 * @param - none.
	 * @return - a message for a plummet,rocket or there has not been sufficient change for either occurance.
	 */
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
			}//endif
			else if (change == 0 )
			{
				text = text + SHARES[i].getName() + "'s Share Price has not change sufficiently.\n";
			}//endelseif
			else text = text + SHARES[i].getName() + "'s Share Price plummets - \u00A3" + change  + ".\n";
			//end if
		}
		
		return text;
	}
	//********************************************End of detectPlummetRocket 3.4***********************************************************************//
	
	//********************************************Start of roundDouble 3.5*****************************************************************************//
	/*
	 * roundDouble - rounds values to 2 decimal places, ie pence after a pound.
	 * @param double d - the value to be rounded.
	 * @return - the rounded value.
	 */
	public double roundDouble(double d) 
	{
	    DecimalFormat twoDForm = new DecimalFormat("#.##");
	    return Double.valueOf(twoDForm.format(d));
	}
	
	//********************************************End of roundDouble 3.5***********************************************************************//
}
