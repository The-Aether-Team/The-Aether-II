package com.gildedgames.aether.common.world.island;

import com.gildedgames.aether.api.world.islands.IIslandBounds;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class IslandBounds implements IIslandBounds
{
	private final int x1, y1, z1;

	private final int x2, y2, z2;

	public IslandBounds(int x1, int y1, int z1, int x2, int y2, int z2)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;

		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
	}

	public IslandBounds(NBTBase nbt)
	{
		NBTTagCompound tag = (NBTTagCompound) nbt;

		int[] min = tag.getIntArray("Min");
		int[] max = tag.getIntArray("Max");

		this.x1 = min[0];
		this.y1 = min[1];
		this.z1 = min[2];

		this.x2 = max[0];
		this.y2 = max[1];
		this.z2 = max[2];
	}

	@Override
	public boolean intersects(int x1, int y1, int z1, int x2, int y2, int z2)
	{
		return this.x1 <= x2 && this.x2 >= x1 && this.y1 <= y2 && this.y2 >= y1 && this.z1 <= z2 && this.z2 >= z1;
	}

	@Override
	public boolean contains(int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
	{
		return this.x1 <= minX && this.x2 >= maxX && this.y1 <= minY && this.y2 >= maxY && this.z1 <= minZ && this.z2 >= maxZ;
	}

	@Override
	public boolean contains(int x, int y, int z)
	{
		return this.intersects(x, y, z, x, y, z);
	}

	@Override
	public int getMinX()
	{
		return this.x1;
	}

	@Override
	public int getMinY()
	{
		return this.y1;
	}

	@Override
	public int getMinZ()
	{
		return this.z1;
	}

	@Override
	public int getMaxX()
	{
		return this.x2;
	}

	@Override
	public int getMaxY()
	{
		return this.y2;
	}

	@Override
	public int getMaxZ()
	{
		return this.z2;
	}

	@Override
	public int getWidth()
	{
		return this.getMaxX() - this.getMinX();
	}

	@Override
	public int getHeight()
	{
		return this.getMaxY() - this.getMinY();
	}

	@Override
	public int getLength()
	{
		return this.getMaxZ() - this.getMinZ();
	}

	@Override
	public double getCenterX()
	{
		return this.getMinX() + (this.getWidth() / 2.0D);
	}

	@Override
	public double getCenterY()
	{
		return this.getMinY() + (this.getHeight() / 2.0D);
	}

	@Override
	public double getCenterZ()
	{
		return this.getMinZ() + (this.getLength() / 2.0D);
	}

	@Override
	public NBTBase serialize()
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setIntArray("Min", new int[] { this.x1, this.y1, this.z1 });
		tag.setIntArray("Max", new int[] { this.x2, this.y2, this.z2 });

		return tag;
	}
}
