/**
 * 
 */
package com.headfirst.designpatterns.adapter;

import java.util.Enumeration;
import java.util.List;

/**
 * @author karl
 * @date Jan 15, 2017 10:12:57 PM
 */
public class ListAdapter<E> implements Enumeration<E> {
	private List<E> list;

	public ListAdapter(List<E> list) {
		this.list = list;
	}

	public Enumeration<E> elements() {
		return this;
	}

	@Override
	public boolean hasMoreElements() {
		return list.iterator().hasNext();
	}

	@Override
	public E nextElement() {
		return list.iterator().next();
	}

}
