package com.gildedgames.aether.api.orbis.util;

public class OrbisTuple<F, S>
{
	private final F first;

	private final S second;

	public OrbisTuple(final F first, final S second)
	{
		this.first = first;
		this.second = second;
	}

	public F getFirst()
	{
		return this.first;
	}

	public S getSecond()
	{
		return this.second;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (obj instanceof OrbisTuple)
		{
			final OrbisTuple<?, ?> tuple = (OrbisTuple<?, ?>) obj;
			return tuple.first.equals(this.first) && tuple.second.equals(this.second);
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode()
	{
		int hash = 17;
		hash += this.first.hashCode();
		hash = hash * 31 + this.second.hashCode();
		return hash;
	}
}
