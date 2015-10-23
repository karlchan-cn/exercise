/**
 * 
 */
package com.headfirst.charpter1;

import com.headfirst.charpter1.service.impl.FlyWithWings;
import com.headfirst.charpter1.service.impl.Quack;

/**
 * @author karl
 *
 */
public class MallardDuck extends Duck {

	public MallardDuck() {
		quackBehavior = new Quack();
		flyBehavior = new FlyWithWings();
	}

	@Override
	void display() {
		System.out.println(" Im a mallard duck");
	}

}
