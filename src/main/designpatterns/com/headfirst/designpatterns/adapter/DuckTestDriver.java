/**
 * 
 */
package com.headfirst.designpatterns.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author karl
 * @date Jan 15, 2017 5:44:50 PM
 */
public class DuckTestDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Duck duck = new MallardDuck();
		Turkey turkey = new WildTurkey();
		Duck turkeyAdapter = new TurkeyAdapter(turkey);
		System.out.println("the turkey says");
		turkey.gobble();
		turkey.fly();
		System.out.println("the duck says");
		testDuck(duck);
		System.out.println("the turkey adapter says");
		testDuck(turkeyAdapter);
		List  list = new ArrayList();
	}

	static void testDuck(Duck duck) {
		duck.quack();
		duck.fly();
	}

}
