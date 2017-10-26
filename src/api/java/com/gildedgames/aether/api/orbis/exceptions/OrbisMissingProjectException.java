package com.gildedgames.aether.api.orbis.exceptions;

import com.gildedgames.aether.api.orbis.management.IProjectIdentifier;

public class OrbisMissingProjectException extends RuntimeException
{
	private static final long serialVersionUID = -5459245293021393093L;

	public final IProjectIdentifier identifier;

	public OrbisMissingProjectException(final IProjectIdentifier identifier)
	{
		super("A project could not be found while trying load it: " + identifier.toString());

		this.identifier = identifier;
	}
}
