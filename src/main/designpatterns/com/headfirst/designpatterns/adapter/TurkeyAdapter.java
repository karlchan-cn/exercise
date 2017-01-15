/**
 * 
 */
package com.headfirst.designpatterns.adapter;

/**
 * @author karl
 * @date Jan 15, 2017 5:41:07 PM
 */
public class TurkeyAdapter implements Duck {

	public TurkeyAdapter(Turkey turkey) {
		this.turkey = turkey;
	}

	@Override
	public void quack() {
		turkey.gobble();
	}

	@Override
	public void fly() {
		for (int i = 0; i < 5; i++)
			turkey.fly();
	}

	private Turkey turkey;

	public Turkey getTurkey() {
		return turkey;
	}

	public void setTurkey(Turkey turkey) {
		this.turkey = turkey;
	}
}
