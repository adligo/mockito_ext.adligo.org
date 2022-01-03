package org.adligo.mockito_ext;

import java.util.Arrays;

/**
 * This was mostly added to get Object [] to 
 * equals, hashCode.
 * 
 * @author scott
 *
 */
public class ObjParams {
  private final Object [] delegate_;
  
  public ObjParams(Object [] delegate) {
    if (delegate == null) {
      throw new NullPointerException();
    }
    delegate_ = delegate;
  }
  
  public Object[] toArray() {
    return delegate_;
  }
  
  @Override
  public String toString() {
    return "ObjParams [delegate_=" + Arrays.toString(delegate_) + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    for (int i = 0; i < delegate_.length; i++) {
      Object o = delegate_[i];
      if (o != null) {
        result = prime * result + o.hashCode();
      } else {
        result = prime * result + 1;
      }
    }
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ObjParams other = (ObjParams) obj;
    if (other.delegate_.length != delegate_.length) {
      return false;
    }
    for (int i = 0; i < delegate_.length; i++) {
      Object o = delegate_[i];
      if (o == null && other.delegate_[i] != null) {
        return false;
      } else if (!o.equals(other.delegate_[i])) {
        return false;
      }
    }
    return true;
  }
}
