import static org.junit.Assert.*;

import org.junit.Test;
import com.example.stocktest2.*;

public class TestShareSet 
{
	ShareSet set = new ShareSet("TESCO", 122, "TSCO");
	public TestShareSet()
	{
		set.setShareSet("10:15", 24.56, 27.8, 23.3, 2550, 22.2, "5.3%");
		set.setShareHistory(23.35, 2678);
	}
	
	@Test
	public void test_SharePrice()
	{
		double difference = 0;
		assertEquals(24.56, set.getCurrentPrice(), difference);
	}
	
	@Test
	public void test_ShareTotal()
	{
		double difference = 0.01;
		double result = 2996.32;
		set.calculateTotal();
		double innerResult = set.getTotal();
		assertEquals(result, innerResult , difference);
	}
	
	@Test
	public void test_SharePreviousTotal()
	{
		double difference = 0.01;
		double result = 2848.7;
		set.setPreviousWeekTotal();
		double innerResult = set.getPreviousTotal();
		assertEquals(result, innerResult , difference);
	}

}
