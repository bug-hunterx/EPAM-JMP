package com.epam;

public interface Cache<K,V> {
	int size();
	long getDefaultExpire();
	void put(K key, V value, long expire);
	V get(K key);
	int eliminate();
	boolean isFull();
	void removeAll();
	int getSize();
	boolean isEmpty();

}
