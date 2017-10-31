package com.gildedgames.orbis.common.exceptions;

import com.gildedgames.orbis_core.data.management.IProjectIdentifier;

public class OrbisMissingProjectException extends RuntimeException
{
	private static final long serialVersionUID = -5459245293021393093L;

	public final IProjectIdentifier identifier;

	public OrbisMissingProjectException(final String folderName)
	{
		super("A project could not be found while trying load it: " + folderName);

		this.identifier = null;
	}

	public OrbisMissingProjectException(final IProjectIdentifier identifier)
	{
		super("A project could not be found while trying load it: " + identifier.toString());

		this.identifier = identifier;
	}
}
