package org.adligo.mockito_ext;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import org.mockito.stubbing.Stubber;

/**
 * This is a rather strange interface that uses the 
 * default method syntax in modern Java to allow an alternate
 * path to Mockito's static imports.
 * @author scott
 *
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
