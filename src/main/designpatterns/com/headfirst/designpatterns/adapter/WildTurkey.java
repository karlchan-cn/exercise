/**
 * 
 */
package com.headfirst.designpatterns.adapter;

/**
 * @author karl
 * @date Jan 15, 2017 5:37:44 PM
 */
public class WildTurkey implements Turkey {

	@Override
	public void gobble() {
		System.out.println("gobble gobble");

	}

	@Override
	public void fly() {
		System.out.println("flying in short distance");

	}

}
