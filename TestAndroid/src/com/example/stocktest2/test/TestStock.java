package com.example.stocktest2.test;

import org.junit.After;
import org.junit.Before;

import android.test.*;

import com.example.stocktest2.ShareSet;

public class TestStock extends AndroidTestCase
{

	ShareSet testStock;
	
	@Before
	public void setUp() 
	{
		testStock = new ShareSet("TESCO", 122, "TSCO");
		//**************Time, Current, High, Low, Volume, Previous CLose, Change***************
		testStock.setShareSet("10:15", 24.56, 27.8, 23.3, 2550, 22.2, "5.3%");
		testStock.setShareHistory(23.35, 2678);
	}
	
	
	
	
	//@Test
	public void test_SharePrice()
	{
		double difference = 0;
		assertEquals(24.56, testStock.getCurrentPrice(), difference);
	}
	
	//@Test
	public void test_ShareTotal()
	{
		double difference = 0.01;
		double result = 2996.32;
		testStock.calculateTotal();
		double innerResult = testStock.getTotal();
		assertEquals(result, innerResult , difference);
	}
	
	//@Test
	public void test_SharePreviousTotal()
	{
		double difference = 0.01;
		double result = 2848.7;
		testStock.setPreviousWeekTotal();
		double innerResult = testStock.getPreviousTotal();
		assertEquals(result, innerResult , difference);
	}

	//27.8
	//@Test
	public void test_SmallHigh()
	{
		double smallHigh = 19.86;
		testStock.setWeeklyHigh(smallHigh);
		double weeklyHigh = testStock.getWeeklyHigh();
		assertTrue(weeklyHigh != smallHigh);
	}
	
	//27.8
	//@Test
	public void test_BigHigh()
	{
		double bigHigh = 29.92;
		testStock.setWeeklyHigh(bigHigh);
		double weeklyHigh = testStock.getWeeklyHigh();
		assertTrue(weeklyHigh == bigHigh);
	}
	
	//23.3
	//@Test
	public void test_SmallLow()
	{
		double smallLow = 21.93;
		testStock.setWeeklyLow(smallLow);
		double weeklyLow = testStock.getWeeklyLow();
		assertTrue(weeklyLow == smallLow);
	}
	
	//23.3
	//@Test
	public void test_BigLow()
	{
		double bigLow = 25.37;
		testStock.setWeeklyLow(bigLow);
		double weeklyLow = testStock.getWeeklyLow();
		assertTrue(weeklyLow != bigLow);
	}
	
	//@Test
	public void test_InsufficientChange()
	{
		double difference = 0.01;
		double result = 0;
		double innerResult = testStock.getPlummetRocket();
		assertEquals(result, innerResult , difference);
	}
	
	//@Test
	public void test_Plummet()
	{
		testStock.setChange("-23.4%");
		double difference = 0.01;
		double result = -23.4;
		double innerResult = testStock.getPlummetRocket();
		assertEquals(result, innerResult , difference);
	}
	
	//@Test
	public void test_Rocket()
	{
		testStock.setChange("15.6%");
		double difference = 0.01;
		double result = 15.6;
		double innerResult = testStock.getPlummetRocket();
		assertEquals(result, innerResult , difference);
	}
	
	@After
	public void tearDown() 
	{
		testStock = null;
		System.gc();
	}
}
