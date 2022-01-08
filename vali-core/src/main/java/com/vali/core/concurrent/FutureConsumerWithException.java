

package com.vali.core.concurrent;

import java.util.concurrent.CompletionException;
import java.util.function.Consumer;

/**
 * A checked extension of the {@link Consumer} interface which rethrows
 * exceptions wrapped in a {@link CompletionException}.
 *
 * @param <T> type of the first argument
 * @param <E> type of the thrown exception
 */
public interface FutureConsumerWithException<T, E extends Throwable> extends Consumer<T> {

	void acceptWithException(T value) throws E;

	@Override
	default void accept(T value) {
		try {
			acceptWithException(value);
		} catch (Throwable t) {
			throw new CompletionException(t);
		}
	}
}
