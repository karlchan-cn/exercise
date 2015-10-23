package com.headfirst.charpter1;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MallardDuckTest {

	@Test
	public void display() {

	}

	@Test
	public void testMallardDuckPerform() {
		Duck mallardDuck = new MallardDuck();
		mallardDuck.performFly();
		mallardDuck.performQuack();
		Assert.assertTrue(Boolean.TRUE);
	}
}
