package com.gildedgames.orbis.common.data.framework.generation;

import com.gildedgames.orbis.common.data.pathway.Entrance;
import com.gildedgames.orbis.common.data.pathway.PathwayData;
import net.minecraft.util.math.BlockPos;

public class FDGDEdge
{
	private final FDGDNode node1, node2;

	private final PathwayData pathway;

	private Entrance connection1, connection2;

	private float ent1X, ent1Y, ent1Z, ent2X, ent2Y, ent2Z;

	public FDGDEdge(FDGDNode node1, FDGDNode node2, PathwayData pathway)
	{
		this.node1 = node1;
		this.node2 = node2;
		this.pathway = pathway;
	}

	public Entrance getConnection(FDGDNode node)
	{
		if (node.equals(this.node1))
		{
			return this.connection1;
		}
		if (node.equals(this.node2))
		{
			return this.connection2;
		}
		throw new IllegalArgumentException();
	}

	public void setConnection(FDGDNode node, Entrance connection)
	{
		if (node.equals(this.node1))
		{
			this.connection1 = connection;
			final BlockPos pos = connection.getPos();
			this.ent1X = pos.getX();
			this.ent1Y = pos.getY();
			this.ent1Z = pos.getZ();
		}
		else if (node.equals(this.node2))
		{
			this.connection2 = connection;
			final BlockPos pos = connection.getPos();
			this.ent2X = pos.getX();
			this.ent2Y = pos.getY();
			this.ent2Z = pos.getZ();
		}
		else
		{
			throw new IllegalArgumentException();
		}
	}

	public FDGDNode getOpposite(FDGDNode node)
	{
		return node.equals(this.node1) ? this.node2 : node.equals(this.node2) ? this.node1 : null;
	}

	public PathwayData pathway()
	{
		return this.pathway;
	}

	public FDGDNode node1()
	{
		return this.node1;
	}

	public FDGDNode node2()
	{
		return this.node2;
	}

	public Entrance connectionOf1()
	{
		return this.connection1;
	}

	public Entrance connectionOf2()
	{
		return this.connection2;
	}

	public float entrance1X()
	{
		return this.ent1X;
	}

	public float entrance1Y()
	{
		return this.ent1Y;
	}

	public float entrance1Z()
	{
		return this.ent1Z;
	}

	public float entrance2X()
	{
		return this.ent2X;
	}

	public float entrance2Y()
	{
		return this.ent2Y;
	}

	public float entrance2Z()
	{
		return this.ent2Z;
	}

	public BlockPos entrance1()
	{
		return new BlockPos(this.entrance1X(), this.entrance1Y(), this.entrance1Z());
	}

	public BlockPos entrance2()
	{
		return new BlockPos(this.entrance2X(), this.entrance2Y(), this.entrance2Z());
	}

	public float xOf(FDGDNode node)
	{
		if (node == this.node1)
		{
			return this.ent1X;
		}
		else
		{
			return this.ent2X;
		}
	}

	public float yOf(FDGDNode node)
	{
		if (node == this.node1)
		{
			return this.ent1Y;
		}
		else
		{
			return this.ent2Y;
		}
	}

	public float zOf(FDGDNode node)
	{
		if (node == this.node1)
		{
			return this.ent1Z;
		}
		else
		{
			return this.ent2Z;
		}
	}

	public void applyForce()
	{
		this.ent1X += this.node1.getForceX();
		this.ent1Y += this.node1.getForceY();
		this.ent1Z += this.node1.getForceZ();

		this.ent2X += this.node2.getForceX();
		this.ent2Y += this.node2.getForceY();
		this.ent2Z += this.node2.getForceZ();
	}
}
