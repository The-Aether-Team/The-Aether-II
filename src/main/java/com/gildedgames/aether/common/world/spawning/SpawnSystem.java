package com.gildedgames.aether.common.world.spawning;

import com.gildedgames.aether.api.world.ISpawnHandler;
import com.gildedgames.aether.api.world.ISpawnSystem;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SpawnSystem implements ISpawnSystem
{
	private final Collection<ISpawnHandler> handlers;

	private final World world;

	private boolean hasInit;

	public SpawnSystem()
	{
		this.world = null;
		this.handlers = new ArrayList<>();
	}

	public SpawnSystem(World world, Collection<ISpawnHandler> handler)
	{
		this.world = world;
		this.handlers = Collections.unmodifiableCollection(new ArrayList<>(handler));
	}

	@Override
	public Collection<ISpawnHandler> getSpawnHandlers()
	{
		return this.handlers;
	}

	@Override
	public void tick()
	{
		if (!this.hasInit)
		{
			for (ISpawnHandler handler : this.handlers)
			{
				handler.init(this.world);
			}

			this.hasInit = true;
		}

		for (ISpawnHandler handler : this.handlers)
		{
			handler.tick();
		}
	}

	public static class Storage implements Capability.IStorage<ISpawnSystem>
	{
		@Nullable
		@Override
		public NBTBase writeNBT(Capability<ISpawnSystem> capability, ISpawnSystem instance, EnumFacing side)
		{
			return null;
		}

		@Override
		public void readNBT(Capability<ISpawnSystem> capability, ISpawnSystem instance, EnumFacing side, NBTBase nbt)
		{

		}
	}
}
