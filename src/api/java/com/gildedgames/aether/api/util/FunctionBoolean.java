package com.gildedgames.aether.api.util;

import java.util.Objects;
import java.util.function.Function;

public interface FunctionBoolean<T> extends Function<T, Boolean>
{
	default Function<T, Boolean> or(Function<? super T, ? extends Boolean> after)
	{
		Objects.requireNonNull(after);
		return (b) -> after.apply(b) || this.apply(b);
	}
}