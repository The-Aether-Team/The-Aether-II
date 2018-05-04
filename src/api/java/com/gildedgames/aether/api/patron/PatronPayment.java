package com.gildedgames.aether.api.patron;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.annotation.Nullable;
import java.time.YearMonth;

public class PatronPayment
{
	private int cents;

	private PatronTier tier;

	private YearMonth date;

	public PatronPayment(int cents, YearMonth date)
	{
		this.cents = cents;
		this.date = date;

		this.tier = null;

		for (PatronTier tier : PatronTier.values())
		{
			if (tier.getCents() <= cents && (this.tier == null || this.tier.getCents() < tier.getCents()))
			{
				this.tier = tier;
			}
		}
	}

	@Nullable
	public PatronTier getTier()
	{
		return this.tier;
	}

	public int getCents()
	{
		return this.cents;
	}

	public YearMonth getDate()
	{
		return this.date;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
		{
			return true;
		}

		if (obj instanceof PatronPayment)
		{
			PatronPayment p = (PatronPayment) obj;

			return p.cents == this.cents && p.date.equals(this.date) && p.tier.equals(this.tier);
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(this.cents);
		builder.append(this.date);
		builder.append(this.tier);

		return builder.toHashCode();
	}
}
