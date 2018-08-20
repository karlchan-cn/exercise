/**
 * 
 */
package cn.com.karl.exercise.lamda;

/**
 * @author Administrator
 *
 */
public class Person {
	private int age;

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	public static int compareByAge(Person a, Person b) {
		return Integer.compare(a.getAge(), b.getAge());
	}
}
