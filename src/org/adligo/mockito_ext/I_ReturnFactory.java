package org.adligo.mockito_ext;

/**
 * This interface is a factory for return values
 * from MockMethods.
 * @see MockMethod
 * @see ArgMap
 * @author scott
 *
 * @param <T>
 */
public interface I_ReturnFactory<T> {
  public T create(Object [] keys);
}
