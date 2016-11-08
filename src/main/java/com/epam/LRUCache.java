package com.epam;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class LRUCache<K, V> extends AbstractCacheMap<K, V>{
	@SuppressWarnings("serial")
	public LRUCache(int cacheSize, long defaultExpire) {
		super(cacheSize , defaultExpire) ;
		this.cacheMap = new LinkedHashMap<K, CacheObject<K, V>>(cacheSize +1 , 1f,true) {
			@Override
			protected boolean removeEldestEntry(
					Map.Entry<K, CacheObject<K, V>> eldest) {

				return LRUCache.this.removeEldestEntry(eldest);
			}
		};	

		}
	
	private boolean removeEldestEntry(Map.Entry<K, CacheObject<K, V>> eldest) {

		if (size == 0)
			return false;

		return size() > size;
	}


	@Override
	protected int eliminateCache() {

		if(!isNeedClearExpiredObject()){ return 0 ;}
		
		Iterator<CacheObject<K, V>> iterator = cacheMap.values().iterator();
		int count  = 0 ;
		while(iterator.hasNext()){
			CacheObject<K, V> cacheObject = iterator.next();
			
			if(cacheObject.isExpired() ){
				iterator.remove(); 
				count++ ;
			}
		}
		
		return count;
	}

	
	

}
