package com.dnfeitosa.codegraph.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapResultHandler<K, V> implements ResultHandler<K, V> {

	private final Map<K, V> map = new ConcurrentHashMap<K, V>();

	@Override
	public void handle(K key, V value) {
		map.put(key, value);
	}

	public Map<K, V> getMap() {
		return new HashMap<>(map);
	}
}