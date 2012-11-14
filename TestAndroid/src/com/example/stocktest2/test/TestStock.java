package com.example.stocktest2.test;

import android.test.*;

import com.example.stocktest2.ShareSet;

public class TestStock extends AndroidTestCase
{

	ShareSet set = new ShareSet("TESCO", 122, "TSCO");
	
	public TestStock()
	{
		//**************Time, Current, High, Low, Volume, Previous CLose, Change***************
		set.setShareSet("10:15", 24.56, 27.8, 23.3, 2550, 22.2, "5.3%");
		set.setShareHistory(23.35, 2678);
	}
	
	//@Test
	public void test_SharePrice()
	{
		double difference = 0;
		assertEquals(24.56, set.getCurrentPrice(), difference);
	}
	
	//@Test
	public void test_ShareTotal()
	{
		double difference = 0.01;
		double result = 2996.32;
		set.calculateTotal();
		double innerResult = set.getTotal();
		assertEquals(result, innerResult , difference);
	}
	
	//@Test
	public void test_SharePreviousTotal()
	{
		double difference = 0.01;
		double result = 2848.7;
		set.setPreviousWeekTotal();
		double innerResult = set.getPreviousTotal();
		assertEquals(result, innerResult , difference);
	}

	//27.8
	//@Test
	public void test_SmallHigh()
	{
		double smallHigh = 19.86;
		set.setWeeklyHigh(smallHigh);
		double weeklyHigh = set.getWeeklyHigh();
		assertTrue(weeklyHigh != smallHigh);
	}
	
	//27.8
	//@Test
	public void test_BigHigh()
	{
		double bigHigh = 29.92;
		set.setWeeklyHigh(bigHigh);
		double weeklyHigh = set.getWeeklyHigh();
		assertTrue(weeklyHigh == bigHigh);
	}
	
	//23.3
	//@Test
	public void test_SmallLow()
	{
		double smallLow = 21.93;
		set.setWeeklyLow(smallLow);
		double weeklyLow = set.getWeeklyLow();
		assertTrue(weeklyLow == smallLow);
	}
	
	//23.3
	//@Test
	public void test_BigLow()
	{
		double bigLow = 25.37;
		set.setWeeklyLow(bigLow);
		double weeklyLow = set.getWeeklyLow();
		assertTrue(weeklyLow != bigLow);
	}
	
	//@Test
	public void test_InsufficientChange()
	{
		double difference = 0.01;
		double result = 0;
		double innerResult = set.getPlummetRocket();
		assertEquals(result, innerResult , difference);
	}
	
	//@Test
	public void test_Plummet()
	{
		set.setChange("-23.4%");
		double difference = 0.01;
		double result = -23.4;
		double innerResult = set.getPlummetRocket();
		assertEquals(result, innerResult , difference);
	}
	
	//@Test
	public void test_Rocket()
	{
		set.setChange("15.6%");
		double difference = 0.01;
		double result = 15.6;
		double innerResult = set.getPlummetRocket();
		assertEquals(result, innerResult , difference);
	}
}
