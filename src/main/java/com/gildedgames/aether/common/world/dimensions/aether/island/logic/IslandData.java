package com.gildedgames.aether.common.world.dimensions.aether.island.logic;

import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.common.util.helpers.NBTHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

import java.awt.*;

public class IslandData implements NBT
{

	private Rectangle bounds;

	private int minY, height;

	private boolean asleep, toRemove;

	private Biome biome;

	private BlockPos mysteriousHengePos;

	private BlockPos labyrinthEntrancePos;

	private IslandData()
	{

	}

	public IslandData(Rectangle bounds, int minY, int height, Biome biome)
	{
		this.bounds = bounds;
		this.minY = minY;
		this.height = height;
		this.biome = biome;
	}

	public Rectangle getBounds()
	{
		return this.bounds;
	}

	public int getHeight()
	{
		return this.height;
	}

	public int getMinY()
	{
		return this.minY;
	}

	public boolean isAsleep()
	{
		return this.asleep;
	}

	public void setAsleep(boolean flag)
	{
		this.asleep = flag;
	}

	public boolean toRemove()
	{
		return this.toRemove;
	}

	public void setToRemove(boolean flag)
	{
		this.toRemove = flag;
	}

	public Biome getBiome()
	{
		return this.biome;
	}

	public BlockPos getMysteriousHengePos()
	{
		return this.mysteriousHengePos;
	}

	public void setMysteriousHengePos(BlockPos pos)
	{
		this.mysteriousHengePos = pos;
	}

	public BlockPos getLabyrinthEntrancePos()
	{
		return this.labyrinthEntrancePos;
	}

	public void setLabyrinthEntrancePos(BlockPos pos)
	{
		this.labyrinthEntrancePos = pos;
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		tag.setInteger("minX", (int) this.bounds.getX());
		tag.setInteger("minZ", (int) this.bounds.getY());
		tag.setInteger("width", (int) this.bounds.getWidth());
		tag.setInteger("length", (int) this.bounds.getHeight());

		tag.setInteger("minY", this.minY);
		tag.setInteger("height", this.height);

		tag.setString("biomeID", this.biome.getRegistryName().toString());

		tag.setTag("mysteriousHengePos", NBTHelper.writeBlockPos(this.mysteriousHengePos));
		tag.setTag("labyrinthEntrancePos", NBTHelper.writeBlockPos(this.labyrinthEntrancePos));
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		int minX = tag.getInteger("minX");
		int minZ = tag.getInteger("minZ");
		int width = tag.getInteger("width");
		int length = tag.getInteger("length");

		this.bounds = new Rectangle(minX, minZ, width, length);

		this.minY = tag.getInteger("minY");
		this.height = tag.getInteger("height");

		this.biome = Biome.REGISTRY.getObject(new ResourceLocation(tag.getString("biomeID")));

		if (tag.hasKey("mysteriousHengePos"))
		{
			this.mysteriousHengePos = NBTHelper.readBlockPos(tag.getCompoundTag("mysteriousHengePos"));
		}

		if (tag.hasKey("labyrinthEntrancePos"))
		{
			this.labyrinthEntrancePos = NBTHelper.readBlockPos(tag.getCompoundTag("labyrinthEntrancePos"));
		}
	}
}
