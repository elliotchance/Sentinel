package org.sentinel.test.cases;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Test;

public abstract class HashMapCase<K, V>
{
    
    Map<K, V> map = null;
    
    public HashMapCase(Map<K, V> map)
    {
        this.map = map;
    }
    
    protected abstract K getTestKey();
    
    protected abstract V getTestValue();
    
    protected Map<K, V> getMap()
    {
        return map;
    }

    @Test
    public void testSize()
    {
        assertEquals(0, map.size());
    }

    @Test
    public void testIsEmpty()
    {
        K key = getTestKey();
        V value = getTestValue();
        
        assertTrue(map.isEmpty());
        map.put(key, value);
        assertFalse(map.isEmpty());
        map.clear();
        assertTrue(map.isEmpty());
    }

    @Test
    public void testContainsKey()
    {
        assertFalse(map.containsKey(getTestKey()));
    }

    @Test
    public void testContainsValue()
    {
        assertFalse(map.containsValue(getTestValue()));
    }

    @Test
    public void testGet()
    {
        K key = getTestKey();
        V value = getTestValue();
        
        assertNull(map.get(key));
        
        map.put(key, value);
        assertEquals(value, map.get(key));
        
        map.remove(key);
    }

    @Test
    public void testPut()
    {
        K key = getTestKey();
        V value = getTestValue();
        
        assertFalse(map.containsKey(key));
        assertFalse(map.containsValue(value));
        
        map.put(key, value);
        assertTrue(map.containsKey(key));
        assertTrue(map.containsValue(value));
        
        map.remove(key);
        assertFalse(map.containsKey(key));
        assertFalse(map.containsValue(value));
    }

    @Test
    public void testRemove()
    {
        K key = getTestKey();
        V value = getTestValue();
        
        assertEquals(0, map.size());
        map.put(key, value);
        assertEquals(1, map.size());
        map.remove(key);
        assertEquals(0, map.size());
    }

    @Test
    public void testPutAll()
    {
        K key = getTestKey();
        V value = getTestValue();
        
        Map<K, V> map2 = new HashMap<K, V>();
        map2.put(key, value);
        assertTrue(map2.size() > 0);
        
        assertEquals(0, map.size());
        map.putAll(map2);
        assertEquals(map2.size(), map.size());
        map.clear();
        assertEquals(0, map.size());
    }

    @Test
    public void testClear()
    {
        K key = getTestKey();
        V value = getTestValue();
        
        assertEquals(0, map.size());
        map.put(key, value);
        assertEquals(1, map.size());
        map.clear();
        assertEquals(0, map.size());
    }

    @Test
    public void testKeySet()
    {
        K key = getTestKey();
        V value = getTestValue();
        
        assertEquals(0, map.size());
        map.put(key, value);
        
        Set<K> set = map.keySet();
        assertEquals(1, set.size());
        assertEquals(map.size(), set.size());
        
        map.clear();
        assertEquals(0, map.size());
    }

    @Test
    public void testValues()
    {
        K key = getTestKey();
        V value = getTestValue();
        
        assertEquals(0, map.size());
        map.put(key, value);
        
        Collection<V> values = map.values();
        assertEquals(1, values.size());
        assertEquals(map.size(), values.size());
        
        map.clear();
        assertEquals(0, map.size());
    }

    @Test
    public void testEntrySet()
    {
        K key = getTestKey();
        V value = getTestValue();
        
        assertEquals(0, map.size());
        map.put(key, value);
        
        Set<Map.Entry<K, V>> values = map.entrySet();
        assertEquals(1, values.size());
        assertEquals(map.size(), values.size());
        
        map.clear();
        assertEquals(0, map.size());
    }
    
}
