package com.vali.core.lambda;

import com.kitsrc.watt.api.ThrowingConsumer;
import com.kitsrc.watt.api.ThrowingFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created with IntelliJ IDEA </p>
 *
 * @author : LiJie  </p>
 * @date : 2019/09/29  </p>
 * @time : 13:51  </p>
 * Description:  </p>
 */
public class LambdaExceptionWrappers {
    private LambdaExceptionWrappers() {
    }

    public static <T> Consumer<T> uncheckedConsumerWrapper(ThrowingConsumer<T> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    public static <T, R> Function<T, R> uncheckedFunctionWrapper(ThrowingFunction<T, R> function) {
        return t -> {
            try {
                return function.apply(t);
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    public static <T, R> Function<T, R> uncheckedFunctionWrapper(ThrowingFunction<T, R> function, Object o) {
        return t -> {
            try {
                return function.apply(t);
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    public static <T> Consumer<T> uncheckedConsumerWrapper(ThrowingConsumer<T> consumer, Object o) {
        return t -> {
            try {
                consumer.accept(t);
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}
