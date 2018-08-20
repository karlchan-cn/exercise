package cn.com.karl.exercise.lamda;

import java.util.Arrays;

public class LamdaFunc {
	public static void main(String[] args) {
		Person[] persons = new Person[] {};
		Arrays.sort(persons, (a, b) -> Integer.compare(a.getAge(), b.getAge()));
	}
}
