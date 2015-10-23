/**
 * 
 */
package com.headfirst.charpter1.service.impl;

import com.headfirst.charpter1.service.QuackBehavior;

/**
 * @author karl
 *
 */
public class MuteQuack implements QuackBehavior{

	public void quack() {
		System.out.println("<<<Silence>>>");
		
	}

}
