/**
 * 
 */
package com.headfirst.charpter3;

/**
 * @author karl
 *
 */
public abstract class Beverage {
	private String description = "unknown beverage";

	public String getDescption() {
		return this.description;
	}

	public abstract double cost();
}
