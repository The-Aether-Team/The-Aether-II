package com.aetherteam.aetherii.network.codec;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface StreamCodec<B, V> {
    void encode(B buffer, V value);

    V decode(B buffer);

    static <B, V> StreamCodec<B, V> of(BiConsumer<B, V> encoder, Function<B, V> decoder) {
        return new StreamCodec<>() {
            @Override
            public void encode(B buffer, V value) {
                encoder.accept(buffer, value);
            }

            @Override
            public V decode(B buffer) {
                return decoder.apply(buffer);
            }
        };
    }

    static <B, V> StreamCodec<B, V> unit(V value) {
        return of((buffer, object) -> {
        }, buffer -> value);
    }

    default <O> StreamCodec<B, O> map(Function<? super V, ? extends O> to, Function<? super O, ? extends V> from) {
        StreamCodec<B, V> self = this;
        return of((buffer, value) -> self.encode(buffer, from.apply(value)), buffer -> to.apply(self.decode(buffer)));
    }

    default <O> StreamCodec<B, O> apply(Function<StreamCodec<B, V>, StreamCodec<B, O>> operation) {
        return operation.apply(this);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    default <O> StreamCodec<B, O> dispatch(Function<? super O, ? extends V> typeGetter, Function<? super V, ? extends StreamCodec<? super B, ? extends O>> codecGetter) {
        StreamCodec<B, V> self = this;
        return of((buffer, value) -> {
            V type = typeGetter.apply(value);
            self.encode(buffer, type);
            ((StreamCodec) codecGetter.apply(type)).encode(buffer, value);
        }, buffer -> {
            V type = self.decode(buffer);
            return (O) ((StreamCodec) codecGetter.apply(type)).decode(buffer);
        });
    }

    static <B, C, T1> StreamCodec<B, C> composite(StreamCodec<? super B, T1> codec1, Function<C, T1> getter1, Function<T1, C> factory) {
        return of((buffer, value) -> codec1.encode(buffer, getter1.apply(value)), buffer -> factory.apply(codec1.decode(buffer)));
    }

    static <B, C, T1, T2> StreamCodec<B, C> composite(StreamCodec<? super B, T1> codec1, Function<C, T1> getter1, StreamCodec<? super B, T2> codec2, Function<C, T2> getter2, BiFunction<T1, T2, C> factory) {
        return of((buffer, value) -> {
            codec1.encode(buffer, getter1.apply(value));
            codec2.encode(buffer, getter2.apply(value));
        }, buffer -> factory.apply(codec1.decode(buffer), codec2.decode(buffer)));
    }

    static <B, C, T1, T2, T3> StreamCodec<B, C> composite(StreamCodec<? super B, T1> codec1, Function<C, T1> getter1, StreamCodec<? super B, T2> codec2, Function<C, T2> getter2, StreamCodec<? super B, T3> codec3, Function<C, T3> getter3, Function3<T1, T2, T3, C> factory) {
        return of((buffer, value) -> {
            codec1.encode(buffer, getter1.apply(value));
            codec2.encode(buffer, getter2.apply(value));
            codec3.encode(buffer, getter3.apply(value));
        }, buffer -> factory.apply(codec1.decode(buffer), codec2.decode(buffer), codec3.decode(buffer)));
    }

    static <B, C, T1, T2, T3, T4> StreamCodec<B, C> composite(StreamCodec<? super B, T1> codec1, Function<C, T1> getter1, StreamCodec<? super B, T2> codec2, Function<C, T2> getter2, StreamCodec<? super B, T3> codec3, Function<C, T3> getter3, StreamCodec<? super B, T4> codec4, Function<C, T4> getter4, Function4<T1, T2, T3, T4, C> factory) {
        return of((buffer, value) -> {
            codec1.encode(buffer, getter1.apply(value));
            codec2.encode(buffer, getter2.apply(value));
            codec3.encode(buffer, getter3.apply(value));
            codec4.encode(buffer, getter4.apply(value));
        }, buffer -> factory.apply(codec1.decode(buffer), codec2.decode(buffer), codec3.decode(buffer), codec4.decode(buffer)));
    }

    static <B, C, T1, T2, T3, T4, T5> StreamCodec<B, C> composite(StreamCodec<? super B, T1> codec1, Function<C, T1> getter1, StreamCodec<? super B, T2> codec2, Function<C, T2> getter2, StreamCodec<? super B, T3> codec3, Function<C, T3> getter3, StreamCodec<? super B, T4> codec4, Function<C, T4> getter4, StreamCodec<? super B, T5> codec5, Function<C, T5> getter5, Function5<T1, T2, T3, T4, T5, C> factory) {
        return of((buffer, value) -> {
            codec1.encode(buffer, getter1.apply(value));
            codec2.encode(buffer, getter2.apply(value));
            codec3.encode(buffer, getter3.apply(value));
            codec4.encode(buffer, getter4.apply(value));
            codec5.encode(buffer, getter5.apply(value));
        }, buffer -> factory.apply(codec1.decode(buffer), codec2.decode(buffer), codec3.decode(buffer), codec4.decode(buffer), codec5.decode(buffer)));
    }

    static <B, C, T1, T2, T3, T4, T5, T6> StreamCodec<B, C> composite(StreamCodec<? super B, T1> codec1, Function<C, T1> getter1, StreamCodec<? super B, T2> codec2, Function<C, T2> getter2, StreamCodec<? super B, T3> codec3, Function<C, T3> getter3, StreamCodec<? super B, T4> codec4, Function<C, T4> getter4, StreamCodec<? super B, T5> codec5, Function<C, T5> getter5, StreamCodec<? super B, T6> codec6, Function<C, T6> getter6, Function6<T1, T2, T3, T4, T5, T6, C> factory) {
        return of((buffer, value) -> {
            codec1.encode(buffer, getter1.apply(value));
            codec2.encode(buffer, getter2.apply(value));
            codec3.encode(buffer, getter3.apply(value));
            codec4.encode(buffer, getter4.apply(value));
            codec5.encode(buffer, getter5.apply(value));
            codec6.encode(buffer, getter6.apply(value));
        }, buffer -> factory.apply(codec1.decode(buffer), codec2.decode(buffer), codec3.decode(buffer), codec4.decode(buffer), codec5.decode(buffer), codec6.decode(buffer)));
    }

    static <B, C, T1, T2, T3, T4, T5, T6, T7> StreamCodec<B, C> composite(StreamCodec<? super B, T1> codec1, Function<C, T1> getter1, StreamCodec<? super B, T2> codec2, Function<C, T2> getter2, StreamCodec<? super B, T3> codec3, Function<C, T3> getter3, StreamCodec<? super B, T4> codec4, Function<C, T4> getter4, StreamCodec<? super B, T5> codec5, Function<C, T5> getter5, StreamCodec<? super B, T6> codec6, Function<C, T6> getter6, StreamCodec<? super B, T7> codec7, Function<C, T7> getter7, Function7<T1, T2, T3, T4, T5, T6, T7, C> factory) {
        return of((buffer, value) -> {
            codec1.encode(buffer, getter1.apply(value));
            codec2.encode(buffer, getter2.apply(value));
            codec3.encode(buffer, getter3.apply(value));
            codec4.encode(buffer, getter4.apply(value));
            codec5.encode(buffer, getter5.apply(value));
            codec6.encode(buffer, getter6.apply(value));
            codec7.encode(buffer, getter7.apply(value));
        }, buffer -> factory.apply(codec1.decode(buffer), codec2.decode(buffer), codec3.decode(buffer), codec4.decode(buffer), codec5.decode(buffer), codec6.decode(buffer), codec7.decode(buffer)));
    }

    static <B, C, T1, T2, T3, T4, T5, T6, T7, T8> StreamCodec<B, C> composite(StreamCodec<? super B, T1> codec1, Function<C, T1> getter1, StreamCodec<? super B, T2> codec2, Function<C, T2> getter2, StreamCodec<? super B, T3> codec3, Function<C, T3> getter3, StreamCodec<? super B, T4> codec4, Function<C, T4> getter4, StreamCodec<? super B, T5> codec5, Function<C, T5> getter5, StreamCodec<? super B, T6> codec6, Function<C, T6> getter6, StreamCodec<? super B, T7> codec7, Function<C, T7> getter7, StreamCodec<? super B, T8> codec8, Function<C, T8> getter8, Function8<T1, T2, T3, T4, T5, T6, T7, T8, C> factory) {
        return of((buffer, value) -> {
            codec1.encode(buffer, getter1.apply(value));
            codec2.encode(buffer, getter2.apply(value));
            codec3.encode(buffer, getter3.apply(value));
            codec4.encode(buffer, getter4.apply(value));
            codec5.encode(buffer, getter5.apply(value));
            codec6.encode(buffer, getter6.apply(value));
            codec7.encode(buffer, getter7.apply(value));
            codec8.encode(buffer, getter8.apply(value));
        }, buffer -> factory.apply(codec1.decode(buffer), codec2.decode(buffer), codec3.decode(buffer), codec4.decode(buffer), codec5.decode(buffer), codec6.decode(buffer), codec7.decode(buffer), codec8.decode(buffer)));
    }

    @FunctionalInterface
    interface Function3<T1, T2, T3, R> {
        R apply(T1 value1, T2 value2, T3 value3);
    }

    @FunctionalInterface
    interface Function4<T1, T2, T3, T4, R> {
        R apply(T1 value1, T2 value2, T3 value3, T4 value4);
    }

    @FunctionalInterface
    interface Function5<T1, T2, T3, T4, T5, R> {
        R apply(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5);
    }

    @FunctionalInterface
    interface Function6<T1, T2, T3, T4, T5, T6, R> {
        R apply(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6);
    }

    @FunctionalInterface
    interface Function7<T1, T2, T3, T4, T5, T6, T7, R> {
        R apply(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7);
    }

    @FunctionalInterface
    interface Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> {
        R apply(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8);
    }
}
