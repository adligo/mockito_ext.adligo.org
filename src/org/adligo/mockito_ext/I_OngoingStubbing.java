package org.adligo.mockito_ext;

import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;

/**
 * This interface simply provided a wrapper for method chaining.
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
public interface I_OngoingStubbing<T> extends OngoingStubbing<T> {

  /**
   * ex an abbreviation execute and alias for {@link OngoingStubbing#then(Answer)}
   */
  I_OngoingStubbing<T> ex(Answer<?> answer);

  /**
   * ret an abbreviation return and alias for
   * {@link OngoingStubbing#thenReturn(T)}
   */
  I_OngoingStubbing<T> ret(T value);

  /**
   * ret an abbreviation return and alias for
   * {@link OngoingStubbing#thenReturn(T, T)}
   */
  I_OngoingStubbing<T> ret(T value, T... values);

  /**
   * th an abbreviation throw and alias for {@link OngoingStubbing#thenThrow()}
   */
  I_OngoingStubbing<T> th(Class<? extends Throwable> throwableType);

  /**
   * th an abbreviation throw and alias for {@link OngoingStubbing#thenThrow()}
   */
  I_OngoingStubbing<T> th(Class<? extends Throwable>... toBeThrown);

  /**
   * th an abbreviation throw and alias for {@link OngoingStubbing#thenThrow()}
   */
  I_OngoingStubbing<T> th(Throwable... throwables);
}
