package com.example.stocktest2;


/*Class ShareSet - Holds the data for each share set, as detailed below with instantiated variables.
 * 
 * Methods used -
 * 					2.0 ShareSet - constructor for the shareset class, initialises the values of the variables held.
 * 					2.1 setShareSet - assigns the values gathered from the yahoo API to the variables of the share set list.
 * 					2.2 Individual mutators - get and set methods for each variable in the share set list.
 * 					2.3 toString - changes the gathered yahoo API values into a string sentence.
 */
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
	private String priceChange;
	private String LTtime;
	
	
	//********************************************Start of shareset constructor 2.0****************************************************//
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
		priceChange = "";
	}
	//********************************************End of shareset constructor 2.0****************************************************//
	
	//********************************************Start of setShareSet 2.1***************************************************************//
	/*
	 * setShareSet - Assigns the values gathered by the yahoo API to the appropriate variables.
	 * @params - time : the current timestamp of the data gathered.
	 * 			 curPrice: the current price of the share.
	 * 			 dailyHigh: the highest price achieved by a stock in a single trade day.
	 * 			 dailyLow : the lowest price achieved by a stock in a single trade day.
	 * 			 Vol : the current volume of shares made avaliable for trading by that company.
	 * 			 prevPrice : the previous close price of the stock.
	 * 			 change : the change in price from the current stock value and the previous trade price.
	 * 
	 * @return - none.
	 */
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
	
	//********************************************End of setShareSet 2.1*****************************************************************//
	
	
	//********************************************Start of Individual mutators 2.2***********************************************************//
	/*
	 * setShareHistory - sets the previous friday close stock price.
	 * getCurrentPrice - returns the current price of a share.
	 * getTime - returns the current trade Timestamp.
	 * getPreviousPrice - returns previous Price.
	 * getTotal - total value of the share set ie. no of share multiplied by share worth. 
	 * getShares -  the quantity of shares owned in that set.
	 * getName - name of the company for that share set.
	 * getPreviousTotal - the previous quantity of shares available from the london stock exchange on the previous Friday.
	 * getWeeklyHigh - weekly high value for a stock set.
	 * getWeeklyLow - weekly low value for a stock set.
	 * getPreviousTotal - previous total worth of the previous Friday.
	 * getPriceChange - change in price of current price vs. previous Friday.
	 * getTicker - the stock ticker symbol for a company.
	 * calculateTotal - calculate total
	 * setWeeklyHigh - set the weekly high for a particular stock.
	 * setWeeklyLow - set the weekly low for a particular stock.
	 * setPreviousWeekTotal - the total portfolio value for the previous week.
	 * getPlummetRocket - determine if there is a rocket or plummet occuring in the the value of a stock.
	 */
	public void setShareHistory (double fridayPrice, long fridayVolume)
	{
		previousFridayPrice = fridayPrice;
		setPreviousWeekTotal();
	}
	
	public void setChange(String theChange)
	{
		priceChange = theChange;
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
	
	public String getPriceChange()
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
		if(weeklyHigh < dailyHigh) weeklyHigh = dailyHigh;
	}
	
	public void setWeeklyLow(double dailyLow)
	{
		if(weeklyLow > dailyLow) weeklyLow = dailyLow;
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
  //********************************************End of Individual Mutators 2.2***********************************************************//
    
    
    
  //********************************************Start of toString Class 2.3**************************************************************//
    /*toString - converts the stock information to an appropriate string.
     * 
     * @param - none
     * @return companyName, sharesOwned, total as a complete string.
     * 
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
    	return (companyName + " - " + sharesOwned + " - " + total);
    	
    }
  //********************************************End of toString Class 2.3***********************************************************//
}
