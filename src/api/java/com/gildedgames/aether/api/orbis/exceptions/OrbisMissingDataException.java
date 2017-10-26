package com.gildedgames.aether.api.orbis.exceptions;

import com.gildedgames.aether.api.orbis.management.IDataIdentifier;

public class OrbisMissingDataException extends RuntimeException
{
	private static final long serialVersionUID = -1623444802325365075L;

	public final IDataIdentifier identifier;

	public OrbisMissingDataException(final IDataIdentifier identifier)
	{
		super("A data file could not be found in the '" + identifier.getProjectIdentifier().toString() + "' project while trying to load this file: "
				+ identifier.toString());

		this.identifier = identifier;
	}
}
