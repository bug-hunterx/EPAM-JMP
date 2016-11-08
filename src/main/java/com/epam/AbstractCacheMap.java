package com.epam;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AbstractCacheMap<K, V> implements Cache<K,V>{

	class CacheObject<K2,V2>{
		final K2 key;
		final V2 cachedObject;
		long lastAccess; 	//last access time
		long accessCount;   //last access counts
		long ttl;           // time to live
		CacheObject(K2 key, V2 value, long ttl){
		    this.key = key;
		    this.cachedObject = value;
		    this.ttl = ttl;
		    this.lastAccess = System.currentTimeMillis();
		    
		}
		
		boolean isExpired(){
			if(ttl==0){
				return false;			
			}
			
		return lastAccess+ttl < System.currentTimeMillis();
		}
		
		V2 getObject(){
			lastAccess = System.currentTimeMillis();
			accessCount++;
			return cachedObject;
		}
		
	}
	
	protected Map<K,CacheObject<K,V>> cacheMap;
	
	private final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
	private final Lock readLock = cacheLock.readLock();
	private final Lock writeLock = cacheLock.writeLock();
	protected int size; //cache size
	protected boolean existCustomExpire; //set default expire time or not
	protected long defaultExpire;//default expire time
	
	
	public AbstractCacheMap(int size,long defaultExpire){
		this.size = size;
		this.defaultExpire = defaultExpire;
	}
	
	@Override
	public int size() {		
		return size;
	}

	@Override
	public long getDefaultExpire() {		
		return defaultExpire;
	}
	
	protected boolean isNeedClearExpiredObject(){
		return defaultExpire > 0 || existCustomExpire ;
	}

	
	public void put(K key,V value){
		put(key,value,defaultExpire);
	}
	
	@Override
	public void put(K key, V value, long expire) {
		writeLock.lock();
		try{
		CacheObject<K,V> co = new CacheObject<K,V>(key,value,expire);
	    if(expire!=0){
	    	existCustomExpire = true;
	    }
	    if(isFull()) {
	    		eliminate();
	    }
	    cacheMap.put(key, co);
		}finally{
			writeLock.unlock();
		}
	}

	@Override
	public V get(K key) {
		// TODO 自动生成的方法存根
		readLock.lock();
		
		try{
			CacheObject<K,V> co = cacheMap.get(key);
			if(co==null){
				return null;
			}
			if(co.isExpired()){
				cacheMap.remove(key);
				return null;
			}
			return co.getObject();
		}finally{
			readLock.unlock();
		}
	}
	
	@Override
	public final int eliminate(){
			writeLock.lock();
			try{
				return eliminateCache();
			}finally{
				writeLock.unlock();
			}
	}
	
	protected abstract int eliminateCache();
	
	@Override
	public boolean isFull() {
		if(size==0){
			return false;
		}
		return cacheMap.size() >= size;		
	}
	
	public void remove(K key){
		writeLock.lock();
		try{
			cacheMap.remove(key);
		}finally{
			writeLock.unlock();
		}
	}
		
	@Override
	public void removeAll() {
		writeLock.lock();
		try{
			cacheMap.clear();
		}finally{
			writeLock.unlock();
		}
	}

	@Override
	public int getSize() {	
		return size;
	}

	@Override
	public boolean isEmpty() {		
		return size() == 0;
	}

}
