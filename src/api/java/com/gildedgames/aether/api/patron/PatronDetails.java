package com.gildedgames.aether.api.patron;

import java.util.List;

public class PatronDetails
{
	private List<PatronPayment> payments;

	public PatronDetails(List<PatronPayment> payments)
	{
		this.payments = payments;
	}

	public boolean hasEverHad(PatronTier tier)
	{
		return this.payments.stream().anyMatch(p -> p.getTier() != null && p.getTier().getCents() >= tier.getCents());
	}

	public PatronPayment getLastPayment()
	{
		PatronPayment last = null;

		for (PatronPayment payment : this.getPayments())
		{
			if (last == null)
			{
				last = payment;
				continue;
			}

			if (last.getDate().isBefore(payment.getDate()))
			{
				last = payment;
			}
		}

		return last;
	}

	public List<PatronPayment> getPayments()
	{
		return this.payments;
	}
}
