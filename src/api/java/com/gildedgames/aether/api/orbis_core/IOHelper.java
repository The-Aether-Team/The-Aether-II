package com.gildedgames.aether.api.orbis_core;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.util.IClassSerializer;
import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.api.util.NBTHelper;
import com.gildedgames.aether.api.util.SimpleSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class IOHelper
{

	public IClassSerializer serializer = new SimpleSerializer();

	public IOHelper()
	{

	}

	public IClassSerializer getSerializer()
	{
		return this.serializer;
	}

	public NBTTagCompound write(final NBT nbt)
	{
		return NBTHelper.write(this.getSerializer(), nbt);
	}

	public <T extends NBT> T read(final World world, final NBTTagCompound tag)
	{
		return NBTHelper.read(world, this.getSerializer(), tag);
	}

	public NBTFunnel createFunnel(final NBTTagCompound tag)
	{
		return new NBTFunnel(tag, this.serializer);
	}

}
