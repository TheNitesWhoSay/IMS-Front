package com.revature.business;

import java.util.List;

import org.junit.Test;

import com.revature.controllers.BusinessDelegate;

public class BusinessDelegateTest {

	@Test
	public void testStuff() throws Exception {
		BusinessDelegate bd = new BusinessDelegate();
		double val = bd.getTotalUnaccountedInventoryValue();
		System.out.println("Val: " + val);
		
		List<Double> dailyValues = bd.getDailyInventoryValues();
		for ( Double value : dailyValues ) {
			System.out.println("Value: " + value);
		}
		
		bd.destroy();
	}
}
