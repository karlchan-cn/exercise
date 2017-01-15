/**
 * 
 */
package com.headfirst.designpatterns.adapter;

/**
 * @author karl
 * @date Jan 15, 2017 5:35:31 PM
 */
public class MallardDuck implements Duck {

	@Override
	public void quack() {
		System.out.println("i'm a mallard duck");

	}

	@Override
	public void fly() {
		System.out.println("i'm flying in middle distance");

	}

}
