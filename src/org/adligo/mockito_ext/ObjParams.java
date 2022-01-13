package org.adligo.mockito_ext;

import java.util.Arrays;

/**
 * This was mostly added to get Object [] to 
 * equals, hashCode.
 * 
 * @author scott
 * 
 * <pre><code>
 *         ---------------- Apache ICENSE-2.0 --------------------------
 *
 *         Copyright 2022 Adligo Inc
 * 
 *         Licensed under the Apache License, Version 2.0 (the "License"); you
 *         may not use this file except in compliance with the License. You may
 *         obtain a copy of the License at
 * 
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 *         Unless required by applicable law or agreed to in writing, software
 *         distributed under the License is distributed on an "AS IS" BASIS,
 *         WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *         implied. See the License for the specific language governing
 *         permissions and limitations under the License.
 *         </code></pre>
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
