package com.gildedgames.aether.common.world.dungeon.util;

public class ConnectorList
{
	FaceConnector first, last;

	public FaceConnector getFirst()
	{
		return this.first;
	}

	void addFirst(FaceConnector connector)
	{
		this.first.previous = connector;
		connector.next = this.first;
		this.first = connector;
	}

	public void add(FaceConnector element)
	{
		if (this.last != null)
		{
			this.last.next = element;
		}
		element.previous = this.last;
		this.last = element;
		if (this.first == null)
		{
			this.first = element;
		}
	}

	public void remove(FaceConnector connector)
	{
		if (connector.previous != null)
		{
			connector.previous.next = connector.next;
		}
		else if (connector.previous == null)
		{
			this.first = connector.next;
		}

		if (connector.next != null)
		{
			connector.next.previous = connector.previous;
		}
		else if (connector.next == null)
		{
			this.last = connector.previous;
		}

		connector.next = null;
		connector.previous = null;
	}
}
