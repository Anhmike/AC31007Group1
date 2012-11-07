package com.example.stocktest2;

import java.util.Calendar;
import java.util.Date;

public class ShareSet 
{
	private String companyTicker;
	private String companyName;//
	private int sharesOwned;//
	private long sharesVolume;//
	private double currentPrice;//
	private double previousClosePrice;//
	private long previousCloseSharesVolume;//
	private double weeklyHigh;//
	private double weeklyLow;//
	private double total;//
	private double previousFridayTotal;
	private double previousFridayPrice;
	private long previousFridayVolume;
	private String priceChange;
	private String LTtime;
	
	
    // Constructor for creating a new object
	public ShareSet(String company, int owned, String ticker) 
	{
		companyTicker = ticker;
		companyName = company;
		sharesOwned = owned;
		sharesVolume = 0;
		currentPrice = 0;
		previousClosePrice = 0;
		previousCloseSharesVolume = 0;
		weeklyHigh = 0;
		weeklyLow = 1000;
		total = 0;
		previousFridayTotal = 0;
		previousFridayPrice = 0;
		previousFridayVolume = 0;
		priceChange = "";
	}

	//Method to set the new Information about the share set at each fetch
	public void setShareSet(String time, double curPrice, double dailyHigh, double dailyLow, long Vol, double prevPrice, String change)
	{
		LTtime = time;
		currentPrice = curPrice;
		previousClosePrice = prevPrice;
		priceChange = change;
		sharesVolume = Vol;
		calculateTotal();
		setWeeklyHigh(dailyHigh);
		setWeeklyLow(dailyLow);
	}
	
	public void setShareHistory (double fridayPrice, long fridayVolume)
	{
		previousFridayPrice = fridayPrice;
		previousFridayVolume = fridayVolume;
		setPreviousWeekTotal();
	}
	
	/*
	 * Set of get methods for the attributed of this class
	 */
	public double getCurrentPrice()
	{
		return currentPrice;
	}
	
	public String getTime()
	{
		return LTtime;
	}
	
	public double getPreviousPrice()
	{
		return previousClosePrice;
	}
	
	public double getTotal()
	{
		return total;
	}
	
	public int getShares()
	{
		return sharesOwned;
	}
	
	public long getCurrentVolume()
	{
		return sharesVolume;
	}
	
	public String getName()
	{
		return companyName;
	}
	
	public long getPreviousVolume()
	{
		return previousCloseSharesVolume;
	}

	public double getWeeklyHigh()
	{
		return weeklyHigh;
	}
	
	public double getWeeklyLow()
	{
		return weeklyLow;
	}
	
	public double getPreviousTotal()
	{
		return previousFridayTotal;
	}
	
	public String getChange()
    {
    	return priceChange;
    }
	
	public String getTicker()
	{
		return companyTicker;
	}

	/*
	 * Set of methods to perform the background calculations needed to help out the features of the app
	 */
 	public void calculateTotal()
	{
		total = sharesOwned * currentPrice;
	}
	
	public void setWeeklyHigh(double dailyHigh)
	{
		if (weeklyHigh < dailyHigh) weeklyHigh = dailyHigh;
	}
	
	public void setWeeklyLow(double dailyLow)
	{
		if (weeklyLow > dailyLow) weeklyLow = dailyLow;
	}
	
	public double getRun()
	{
		double run = sharesVolume/previousCloseSharesVolume;
		if(run >= 10)
		{
			return run;
		}
		else return 0; // Insufficient change for alert
	}
	
	public void setPreviousWeekTotal()
	{
		   previousFridayTotal = sharesOwned * previousFridayPrice;
	}

    public double getPlummetRocket()
    {
    	//Take the percent off the end
    	String change = priceChange.substring(0, priceChange.length() -1 );
    	
    	Double result;
    	
    	//Plummets
    	if((change.charAt(0))=='-')
    	{
    		//Some code in case double does not perform well with - sign or easier for us
    		//change = change.substring(1,change.length());
    		//result = Double.parseDouble(change);
    		//if(result>=20)
    		//{
			//return (result*-1);
			//}
    		result = Double.parseDouble(change);
    		if(result<= -20)
    		{
    			return (result);
    		}
    		else return 0; // Insufficient change for alert
    	}
    	//Rockets
    	else
    	{
    		result = Double.parseDouble(change);
    		if(result>=10)
    		{
    			return result;
    		}
    		else return 0; // Insufficient change for alert
    	}
    }
    
    //Returns a Stock as a specially formatted string
    public String toString()
    {
    	return (companyName + "\t" + sharesOwned + "\t" + total);
    	
    }
}
