

package com.kitsrc.watt.utils.function;

import java.util.function.Supplier;

/**
 * Similar to {@link Supplier} but can throw {@link Exception}.
 */
@FunctionalInterface
public interface CheckedSupplier<R> extends SupplierWithException<R, Exception> {

	static <R> Supplier<R> unchecked(CheckedSupplier<R> checkedSupplier) {
		return () -> {
			try {
				return checkedSupplier.get();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	static <R> CheckedSupplier<R> checked(Supplier<R> supplier) {
		return () -> {
			try {
				return supplier.get();
			}
			catch (RuntimeException e) {
				throw  e;
			}
		};
	}
}
