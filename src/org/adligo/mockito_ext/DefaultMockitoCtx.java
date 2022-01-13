package org.adligo.mockito_ext;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import org.mockito.stubbing.Stubber;

/**
 * This is a rather strange interface that uses the default method syntax in
 * modern Java to allow an alternate path to Mockito's static imports.
 * 
 * @author scott
 *
 *         <pre>
 * <code>
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
 *         </code>
 *         </pre>
 */
public interface DefaultMockitoCtx {

  default <T> T any() {
    return Mockito.any();
  }

  default Double anyDouble() {
    return Mockito.anyDouble();
  }

  default Float antFloat() {
    return Mockito.anyFloat();
  }

  default Integer antInt() {
    return Mockito.anyInt();
  }

  default Long antLong() {
    return Mockito.anyLong();
  }

  default String anyString() {
    return Mockito.anyString();
  }

  /**
   * 
   * @param <T>
   * @param a
   * @return
   */
  default <T> Stubber call(Answer<T> a) {
    return Mockito.doAnswer(a);
  }

  /**
   * Use call it's clear
   * 
   * @param <T>
   * @param a
   * @return
   */
  @Deprecated
  default <T> Stubber doAnswer(Answer<T> a) {
    return Mockito.doAnswer(a);
  }

  default <T> T mock(Class<T> clazz) {
    return Mockito.mock(clazz);
  }
}
