/**
 * 
 */
package com.headfirst.charpter1.service.impl;

import com.headfirst.charpter1.service.FlyBehavior;

/**
 * @author karl
 *
 */
public class FlyNoWay implements FlyBehavior {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.headfirst.charpter1.service.FlyBehavior#fly()
	 */
	public void fly() {
		System.out.println("i just can't fly");
	}

}
