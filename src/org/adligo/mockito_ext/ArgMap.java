package org.adligo.mockito_ext;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class maps arguments/parameters to return values of type T.  There are
 * a variety of ways to do this; <br/>
 * A) With a default return value. <br/>
 * B) With a factory for return values. <br/>
 * C) With the backing delegate Map&lt;ObjParams,T&gt;. <br/>
 * <br/>
 * In all cases the value returned, should be 
 * the same instance for subsequent invocations 
 * of get for the same parameters/arguments so 
 * that the arg map can be used for assertions.<br/>
 * <br/>
 * <pre>
 * {@code
 * MockMethod<String> mm = new MockMethod<String>(new ArgMap<String>(
 *    new I_ReturnFactory<String> {
 *      public String create(Object [] params) {
 *          return "11" + params[0];
 *      }
 * ));
 * List&lt;String&gt; mockList = mock(List.class);
 * when(mockList.get(anyInt()).then(mm);
 * assertEquals("111", mockList.get(1));
 * assertEquals("112", mockList.get(2));
 * assertEquals("1131", mockList.get(31));
 * </pre>
 * @author scott
 *
 * Note this was a implementation of Map, but to bridge jdk versions it no longer implements Map.
 * @param <T>
 */
public class ArgMap<T> {
  public static final String OBJECT_ARRAY_CLASS_NAME = new Object[] {}.getClass().getName();
  
  private final Map<ObjParams, T> delegate_ = new HashMap<ObjParams, T>();
  private final T defaultValue_;
  private final I_ReturnFactory<T> factory_;
  private final boolean cacheResults_;
  
  public ArgMap() {
    defaultValue_ = null;
    factory_ = null;
    cacheResults_ = true;
  }
  
  public ArgMap(T defaultValue) {
    defaultValue_ = defaultValue;
    factory_ = null;
    cacheResults_ = true;
  }
  /**
   * this can be useful for storing instances,
   * to make sure they get passed to other methods later.
   * @param factory
   */
  public ArgMap(I_ReturnFactory<T> factory) {
    defaultValue_ = null;
    factory_ = factory;
    cacheResults_ = true;
  }
  
  public ArgMap(I_ReturnFactory<T> factory, boolean cacheResults) {
    defaultValue_ = null;
    factory_ = factory;
    cacheResults_ = cacheResults;
  }
  
  public ArgMap(T defaultValue, I_ReturnFactory<T> factory) {
    defaultValue_ = defaultValue;
    factory_ = factory;
    cacheResults_ = true;
  }
  
  public ArgMap(T defaultValue, I_ReturnFactory<T> factory, boolean cacheResults) {
    defaultValue_ = defaultValue;
    factory_ = factory;
    cacheResults_ = cacheResults;
  }
  
  public int size() {
    return delegate_.size();
  }

  public boolean isEmpty() {
    return delegate_.isEmpty();
  }

  public boolean containsKey(Object key) {
    return delegate_.containsKey(key);
  }

  public boolean containsValue(Object value) {
    return delegate_.containsValue(value);
  }

  public T get(Object key) {
    if (key != null) {
      if (OBJECT_ARRAY_CLASS_NAME.equals(key.getClass().getName())) {
        return get(new ObjParams((Object []) key));
      }
    }
    return delegate_.get(key);
  }

  public T get(ObjParams key) {
    
    T toRet = delegate_.get(key);
    
    if (toRet == null) {
      if (factory_ != null) {
        toRet = factory_.create(key.toArray());
        if (cacheResults_) {
          delegate_.put(key, toRet);
        }
      }
    }
    if (toRet == null) {
      return defaultValue_;
    }
    return toRet;
  }
  
  public T get(Object [] key) {
    return get(new ObjParams(key));
  }
  /**
   * note it's getVar to distinguish 
   * between get(1)' and getVar(1);
   * @param key
   * @return
   */
  public T getVar(Object ... key) {
    return get(new ObjParams(key));
  }
  
  public T put(ObjParams key, T value) {
    return delegate_.put(key, value);
  }
  
  public T put(Object[] key, T value) {
    return delegate_.put(new ObjParams(key), value);
  }

  /**
   * Note: This method is backward from the regular
   * put methods so it can have the ... for variable 
   * arguments.
   * @param value
   * @param key
   * @return
   */
  public T putVal(T value, Object ... key ) {
    return delegate_.put(new ObjParams(key), value);
  }
  
  public T remove(Object key) {
    return delegate_.remove(key);
  }

  public void putAll(Map<? extends ObjParams, ? extends T> m) {
    delegate_.putAll(m);
  }

  public void clear() {
    delegate_.clear();
  }

  public Set<ObjParams> keySet() {
    return delegate_.keySet();
  }

  public Collection<T> values() {
    return delegate_.values();
  }

  public Set<java.util.Map.Entry<ObjParams, T>> entrySet() {
    return delegate_.entrySet();
  }

  public boolean equals(Object o) {
    return delegate_.equals(o);
  }

  public int hashCode() {
    return delegate_.hashCode();
  }

  public boolean isCacheResults() {
    return cacheResults_;
  }

  
}
