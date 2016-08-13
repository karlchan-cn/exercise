/**
 * 
 */
package cn.com.karl.exercise.cocurrent;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import com.google.common.base.Objects;

/**
 * @author karl
 *
 */
public class MockHashMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {
	// MUST be a power of two.
	static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

	static final int MAXIMUM_CAPACITY = 1 << 30;

	static final float DEFAULT_LOAD_FACTOR = 0.75f;

	static final int TREEIFY_THRESHOLD = 8;

	static final int UNTREEIFY_THRESHOLD = 6;

	static final int MIN_TREEIFY_CAPACITY = 64;

	static class Node<K, V> implements Map.Entry<K, V> {
		final int hash;
		final K key;
		V value;
		Node<K, V> next;

		Node(int hash, K key, V value, Node<K, V> next) {
			this.hash = hash;
			this.key = key;
			this.value = value;
			this.next = next;

		}

		public final K getKey() {
			return key;
		}

		public final V getValue() {
			return value;
		}

		public final String toString() {
			return key + "=" + value;
		}

		@Override
		public V setValue(V newValue) {
			V oldValue = value;
			value = newValue;
			return oldValue;
		}

		public final boolean equals(Object o) {
			if (o == this)
				return true;
			if (o instanceof Map.Entry) {
				Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
				if (Objects.equal(key, e.getKey()) && Objects.equal(value, e.getValue()))
					return true;
			}
			return false;
		}

	}
	/*---------Static utilities ----------*/

	/**
	 * 
	 * @param key
	 * @return
	 */
	static final int hash(Object key) {
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}

	static Class<?> comparableClassFor(Object x) {
		if (x instanceof Comparable) {
			Class<?> c;
			Type[] ts, as;
			Type t;
			ParameterizedType p;
			if ((c = x.getClass()) == String.class)
				return c;
			if ((ts = c.getGenericInterfaces()) != null) {
				for (int i = 0; i < ts.length; ++i) {
					if (((t = ts[i]) instanceof ParameterizedType)
							&& ((p = (ParameterizedType) t).getRawType() == Comparable.class)
							&& ((as = p.getActualTypeArguments()) != null) && as.length == 1 && as[0] == c)
						return c;
				}
			}
		}
		return null;
	}

	/**
	 * Returns k.compareTo(x) if x matches kc (k's screened comparable class),
	 * else 0.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" }) // for cast to Comparable
	static int compareComparables(Class<?> kc, Object k, Object x) {
		return (x == null || x.getClass() != kc ? 0 : ((Comparable) k).compareTo(x));
	}

	/**
	 * Returns a power of two size for the given target capacity.
	 */
	static final int tableSizeFor(int cap) {
		int n = cap - 1;
		n |= n >>> 1;
		n |= n >>> 2;
		n |= n >>> 4;
		n |= n >>> 8;
		n |= n >>> 16;
		return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
	}

	/* ---------------- Fields -------------- */
	/**
	 * The table, initialized on first use, and resized as necessary. When
	 * allocated, length is always a power of two. (We also tolerate length zero
	 * in some operations to allow bootstrapping mechanics that are currently
	 * not needed.)
	 */
	transient Node<K, V>[] table;
	/**
	 * Holds cached entrySet(). Note that AbstractMap fields are used for
	 * keySet() and values().
	 */
	transient Set<Map.Entry<K, V>> entrySet;
	transient int size;
	transient int modCount;

	int threshold;

	final float loadFactor;

	/* ---------------- Public operations -------------- */
	public MockHashMap(int initialCapacity, float loadFactor) {
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illeagal initial capacity:" + initialCapacity);
		if (initialCapacity > MAXIMUM_CAPACITY)
			initialCapacity = MAXIMUM_CAPACITY;
		if (loadFactor <= 0 || Float.isNaN(loadFactor))
			throw new IllegalArgumentException("Illeagal load factor:" + initialCapacity);
		this.loadFactor = loadFactor;
		this.threshold = tableSizeFor(initialCapacity);
	}

	public MockHashMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR);
	}

	public static void main(String[] args) {
		MockHashMap.tableSizeFor(2147483647);
	}

	public MockHashMap() {
		this.loadFactor = DEFAULT_LOAD_FACTOR;
	}

	public MockHashMap(Map<? extends K, ? extends V> m) {
		this.loadFactor = DEFAULT_LOAD_FACTOR;
		putMapEntries(m, false);
	}

	/**
	 * Implements Map.putAll and Map constructor
	 *
	 * @param m
	 *            the map
	 * @param evict
	 *            false when initially constructing this map, else true (relayed
	 *            to method afterNodeInsertion).
	 */
	final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
		int s = m.size();
		if (s > 0) {
			if (table == null) { // pre-size
				float ft = ((float) s / loadFactor) + 1.0F;
				int t = ((ft < (float) MAXIMUM_CAPACITY) ? (int) ft : MAXIMUM_CAPACITY);
				if (t > threshold)
					threshold = tableSizeFor(t);
			} else if (s > threshold)
				resize();
			for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
				K key = e.getKey();
				V value = e.getValue();
				putVal(hash(key), key, value, false, evict);
			}
		}
	}

	/**
	 * Implements Map.put and related methods
	 *
	 * @param hash
	 *            hash for key
	 * @param key
	 *            the key
	 * @param value
	 *            the value to put
	 * @param onlyIfAbsent
	 *            if true, don't change existing value
	 * @param evict
	 *            if false, the table is in creation mode.
	 * @return previous value, or null if none
	 */
	final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
//		Node<K, V>[] tab;
//		Node<K, V> p;
//		int n, i;
//		if ((tab = table) == null || (n = tab.length) == 0)
//			n = (tab = resize()).length;
//		if ((p = tab[i = (n - 1) & hash]) == null)
//			tab[i] = newNode(hash, key, value, null);
//		else {
//			Node<K, V> e;
//			K k;
//			if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
//				e = p;
//			else if (p instanceof TreeNode)
//				e = ((TreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value);
//			else {
//				for (int binCount = 0;; ++binCount) {
//					if ((e = p.next) == null) {
//						p.next = newNode(hash, key, value, null);
//						if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
//							treeifyBin(tab, hash);
//						break;
//					}
//					if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
//						break;
//					p = e;
//				}
//			}
//			if (e != null) { // existing mapping for key
//				V oldValue = e.value;
//				if (!onlyIfAbsent || oldValue == null)
//					e.value = value;
//				afterNodeAccess(e);
//				return oldValue;
//			}
//		}
//		++modCount;
//		if (++size > threshold)
//			resize();
//		afterNodeInsertion(evict);
		return null;
	}

	/**
	 * Initializes or doubles table size. If null, allocates in accord with
	 * initial capacity target held in field threshold. Otherwise, because we
	 * are using power-of-two expansion, the elements from each bin must either
	 * stay at same index, or move with a power of two offset in the new table.
	 *
	 * @return the table
	 */
	final Node<K, V>[] resize() {
		Node<K, V>[] oldTab = table;
		int oldCap = (oldTab == null) ? 0 : oldTab.length;
		int oldThr = threshold;
		int newCap, newThr = 0;
		if (oldCap > 0) {
			if (oldCap >= MAXIMUM_CAPACITY) {
				threshold = Integer.MAX_VALUE;
				return oldTab;
			} else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY && oldCap >= DEFAULT_INITIAL_CAPACITY)
				newThr = oldThr << 1; // double threshold
		} else if (oldThr > 0) // initial capacity was placed in threshold
			newCap = oldThr;
		else { // zero initial threshold signifies using defaults
			newCap = DEFAULT_INITIAL_CAPACITY;
			newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
		}
		if (newThr == 0) {
			float ft = (float) newCap *  loadFactor;
			newThr = (newCap < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY) ? (int) ft : Integer.MAX_VALUE;
		}
		threshold = newThr;
		@SuppressWarnings({"rawtypes", "unchecked"})
		Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];
		table = newTab;
		if (oldTab != null) {
			for (int j = 0; j < oldCap; ++j) {
				Node<K, V> e;
				if ((e = oldTab[j]) != null) {
					oldTab[j] = null;
					if (e.next == null)
						newTab[e.hash & (newCap - 1)] = e;
					else if (e instanceof TreeNode)
						//((TreeNode<K, V>) e).split(this, newTab, j, oldCap);
						System.out.println("");
					else { // preserve order
						Node<K, V> loHead = null, loTail = null;
						Node<K, V> hiHead = null, hiTail = null;
						Node<K, V> next;
						do {
							next = e.next;
							if ((e.hash & oldCap) == 0) {
								if (loTail == null)
									loHead = e;
								else
									loTail.next = e;
								loTail = e;
							} else {
								if (hiTail == null)
									hiHead = e;
								else
									hiTail.next = e;
								hiTail = e;
							}
						} while ((e = next) != null);
						if (loTail != null) {
							loTail.next = null;
							newTab[j] = loHead;
						}
						if (hiTail != null) {
							hiTail.next = null;
							newTab[j + oldCap] = hiHead;
						}
					}
				}
			}
		}
		return newTab;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V put(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
