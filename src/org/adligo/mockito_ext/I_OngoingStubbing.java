package org.adligo.mockito_ext;

import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;

public interface I_OngoingStubbing<T> extends OngoingStubbing<T> {

  /**
   * ex an abbreviation execute and alias for
   * {@link OngoingStubbing#then(Answer)}
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
