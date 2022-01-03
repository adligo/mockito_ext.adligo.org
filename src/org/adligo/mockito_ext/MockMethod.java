package org.adligo.mockito_ext;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * This class is the base replacement for verify <br/>
 * which is intentionally missing from the I_MockitoAsserts <br/>
 * because it will never be counted as a assertion, which it <br/>
 * really what it is. This also clearly divides the work between <br/>
 * tests4j and mockito; <br/>
 * tests4j assertions <br/>
 * mockito partial or full mocking only <br/>
 * <br/>
 * For void return types do this; <br/>
 * MockMethod&lt;Void&gt; mm = new MockMethod&lt;Void&gt;(); <br/>
 * PrintStream out = System.out; <br/>
 * doAnswer(mm).when(out).println(any(String.class)); <br/>
 * out.println("hey"); <br/>
 * assertEquals(1, mm.count());<br/>
 * assertEquals("hey", mm.getArg(0));<br/>
 * <br/>
 * For methods that return values (i.e. String) do this;<br/>
 * MockMethod&lt;String&gt; getMock = new MockMethod&lt;String&gt;(); <br/>
 * List&lt;String&gt; mockList = mock(List.class); <br/>
 * getMock.push("abc");<br/>
 * getMock.push("dev");<br/>
 * when(mockList.get(anyInt())).thenAnswer(getMock); <br/>
 * mockList.get(123); <br/>
 * mockList.get(456); <br/>
 * assertEquals(2, getMock.count());<br/>
 * assertEquals(123, getMock.getArg(0));<br/>
 * assertEquals(456, getMock.getArg(1));<br/>
 * <br/>
 * For a map of return values do this;<br/>
 * ArgMap&lt;String&gt; map = new ArgMap&lt;String&gt;();<br/>
 * Note: putVal is backward on purpose see it's javadoc.<br/>
 * map.putVal("hey",111);<br/>
 * map.putVal("hey2",212);<br/>
 * MockMethod&lt;String&gt; getMockTwo = new MockMethod&lt;String&gt;(map);
 * <br/>
 * when(mockList.get(anyInt()).thenAnswer(getMockTwo); <br/>
 * assertEquals("hey", mockList.get(111)); <br/>
 * assertEquals("hey2", mockList.get(212)); <br/>
 * 
 * @author scott
 *
 */
public class MockMethod<T> implements Answer<T> {
  public static final Object[] EMPTY = new Object[] {};

  private int count_;

  private final I_MethodOrderTracker methodOrderTracker_;
  private final I_ReturnFactory<T> factory_;
  private final ArgMap<T> argsToResults_;
  private final List<Object[]> callArgs_ = new ArrayList<Object[]>();
  private final List<Integer> callOrder_ = new ArrayList<Integer>();

  private Stack<T> types_;
  private Stack<Throwable> toThrow_;
  private T defaultType_;
  private Throwable defaultThrowable_;

  public MockMethod() {
    argsToResults_ = null;
    factory_ = null;
    methodOrderTracker_ = null;
  }

  public MockMethod(I_MethodOrderTracker tracker) {
    argsToResults_ = null;
    factory_ = null;
    methodOrderTracker_ = tracker;
  }

  public MockMethod(T... type) {
    argsToResults_ = null;
    factory_ = null;
    methodOrderTracker_ = null;
    types_ = new Stack<T>();
    for (int i = 0; i < type.length; i++) {
      types_.push(type[i]);
    }
  }

  public MockMethod(I_MethodOrderTracker tracker, T... type) {
    methodOrderTracker_ = tracker;
    argsToResults_ = null;
    factory_ = null;
    types_ = new Stack<T>();
    for (int i = 0; i < type.length; i++) {
      types_.push(type[i]);
    }
  }

  public MockMethod(T type, boolean repeat) {
    methodOrderTracker_ = null;
    argsToResults_ = null;
    factory_ = null;
    if (!repeat) {
      types_ = new Stack<T>();
      types_.push(type);
    } else {
      types_ = null;
      defaultType_ = type;
    }
  }

  public MockMethod(boolean repeat, Throwable throwable) {
    methodOrderTracker_ = null;
    argsToResults_ = null;
    factory_ = null;
    if (!repeat) {
      toThrow_ = new Stack<Throwable>();
      toThrow_.push(throwable);
    } else {
      types_ = null;
      defaultThrowable_ = throwable;
    }
  }
  
  public MockMethod(I_MethodOrderTracker tracker, T type, boolean repeat) {
    methodOrderTracker_ = tracker;
    argsToResults_ = null;
    factory_ = null;
    if (!repeat) {
      types_ = new Stack<T>();
      types_.push(type);
    } else {
      types_ = null;
      defaultType_ = type;
    }
  }

  public MockMethod(ArgMap<T> responses) {
    methodOrderTracker_ = null;
    factory_ = null;
    types_ = null;
    argsToResults_ = responses;
  }

  public MockMethod(I_MethodOrderTracker tracker, ArgMap<T> responses) {
    methodOrderTracker_ = tracker;
    factory_ = null;
    types_ = null;
    argsToResults_ = responses;
  }

  public MockMethod(I_ReturnFactory<T> factory) {
    methodOrderTracker_ = null;
    types_ = null;
    factory_ = factory;
    argsToResults_ = new ArgMap<T>(factory);
  }

  public MockMethod(I_MethodOrderTracker tracker, I_ReturnFactory<T> factory) {
    methodOrderTracker_ = tracker;
    types_ = null;
    factory_ = factory;
    argsToResults_ = new ArgMap<T>(factory);
  }

  public MockMethod(I_ReturnFactory<T> factory, boolean cacheResults) {
    methodOrderTracker_ = null;
    types_ = null;
    factory_ = factory;
    argsToResults_ = new ArgMap<T>(factory, cacheResults);
  }

  public MockMethod(I_MethodOrderTracker tracker, I_ReturnFactory<T> factory, boolean cacheResults) {
    methodOrderTracker_ = tracker;
    types_ = null;
    factory_ = factory;
    argsToResults_ = new ArgMap<T>(factory, cacheResults);
  }

  public MockMethod(ArgMap<T> responses, T defaultType) {
    methodOrderTracker_ = null;
    types_ = null;
    factory_ = null;
    argsToResults_ = responses;
    defaultType_ = defaultType;
  }

  public MockMethod(I_MethodOrderTracker tracker, ArgMap<T> responses, T defaultType) {
    methodOrderTracker_ = tracker;
    types_ = null;
    factory_ = null;
    argsToResults_ = responses;
    defaultType_ = defaultType;
  }

  public MockMethod(Collection<T> types) {
    argsToResults_ = null;
    factory_ = null;
    methodOrderTracker_ = null;
    types_ = new Stack<T>();
    for (T t : types) {
      types_.push(t);
    }
  }

  public MockMethod(I_MethodOrderTracker tracker, Collection<T> types) {
    argsToResults_ = null;
    factory_ = null;
    methodOrderTracker_ = tracker;
    types_ = new Stack<T>();
    for (T t : types) {
      types_.push(t);
    }
  }

  public MockMethod<T> push(T type) {
    if (types_ == null) {
      types_ = new Stack<T>();
    }
    types_.push(type);
    return this;
  }

  public MockMethod<T> pushTh(Throwable t) {
    return pushToThrow(t);
  }

  public MockMethod<T> pushToThrow(Throwable t) {
    if (toThrow_ == null) {
      toThrow_ = new Stack<Throwable>();
    }
    toThrow_.push(t);
    return this;
  }

  @SuppressWarnings("boxing")
  @Override
  public T answer(InvocationOnMock invocation) throws Throwable {
    count_++;
    Object[] args = invocation.getArguments();
    callArgs_.add(args);
    if (methodOrderTracker_ != null) {
      int order = methodOrderTracker_.next();
      callOrder_.add(order);
    }
    if (toThrow_ != null) {
      if (toThrow_.size() >= 1) {
        Throwable toThrow = toThrow_.remove(0);
        throw toThrow;
      }
    }
    T toRet = null;
    if (types_ != null) {
      if (types_.size() >= 1) {
        toRet = types_.firstElement();
        // fifo
        types_.remove(0);
      }
    }
    if (toRet == null) {
      ObjParams params = new ObjParams(args);
      if (argsToResults_ != null) {
        toRet = argsToResults_.get(params);
        if (toRet == null) {
          return defaultType_;
        } else {
          return toRet;
        }
      }
    }
    if (toRet == null) {
      if (defaultThrowable_ != null) {
        throw defaultThrowable_;
      }
      if (defaultType_ != null) {
        return defaultType_;
      }
    }
    return toRet;
  }

  /**
   * 
   * @return The number of times the method was called.
   */
  public int count() {
    return count_;
  }

  /**
   * Clears the recorded state, but does NOT clear the output(return values)
   * types.
   */
  public void clear() {
    count_ = 0;
    callArgs_.clear();
    callOrder_.clear();
  }

  public boolean isCacheResults() {
    if (argsToResults_ == null) {
      return true;
    }
    return argsToResults_.isCacheResults();
  }

  /**
   * @param call
   *          which recorded method call to return the arguments/parameters for.
   * @return the arguments/parameters of a recorded method call.
   */
  public Object[] getArgs(int call) {
    return callArgs_.get(call);
  }

  /**
   * @param call
   *          which recorded method call to return the arguments/parameters for.
   * @return the first or single argument of a recorded method call.
   */
  public Object getArg(int call) {
    return callArgs_.get(call)[0];
  }

  public ArgMap<T> getArgMap() {
    return argsToResults_;
  }

  public T getDefaultType() {
    return defaultType_;
  }

  public I_MethodOrderTracker getMethodOrderTracker() {
    return methodOrderTracker_;
  }

  public I_ReturnFactory<T> getReturnFactory() {
    return factory_;
  }

  public List<T> getTypes() {
    if (types_ == null) {
      return Collections.emptyList();
    }
    return new ArrayList<T>(types_);
  }

  /**
   * 
   * @return The number of times the method was called.
   */
  @SuppressWarnings("boxing")
  public int order(int call) {
    if (methodOrderTracker_ == null) {
      return -1;
    }
    return callOrder_.get(call);
  }

}
