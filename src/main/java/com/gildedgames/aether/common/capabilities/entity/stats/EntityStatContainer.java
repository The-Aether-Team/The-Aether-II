package com.gildedgames.aether.common.capabilities.entity.stats;

import com.gildedgames.aether.api.capabilites.entity.stats.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;

import java.util.*;

public class EntityStatContainer implements IEntityStatContainer
{
	private final HashMap<ResourceLocation, StatCollection> stats = new HashMap<>();

	public EntityStatContainer(EntityLivingBase entity)
	{
		if (entity instanceof IEntityWithStats)
		{
			Collection<StatBase> collection = new ArrayList<>();

			((IEntityWithStats) entity).registerStats(collection);

			for (StatBase stat : collection)
			{
				this.addBaseStat(stat);
			}
		}
	}

	private void addBaseStat(StatBase stat)
	{
		this.stats.put(stat.getResourceID(), new StatCollection(stat));
	}

	@Override
	public void addStatProvider(IEntityStatProvider modifier)
	{
		for (StatBase stat : modifier.getStats())
		{
			StatCollection collection = this.stats.computeIfAbsent(stat.getResourceID(), k -> new StatCollection());
			collection.addModifier(stat);
		}
	}

	@Override
	public void removeStatProvider(IEntityStatProvider modifier)
	{
		for (StatBase stat : modifier.getStats())
		{
			StatCollection collection = this.stats.get(stat.getResourceID());

			if (collection != null)
			{
				collection.removeModifier(stat);

				if (collection.isEmpty())
				{
					this.stats.remove(stat.getResourceID());
				}
			}
		}
	}

	@Override
	public Collection<IEntityStat> getAllStats()
	{
		return Collections.unmodifiableCollection(this.stats.values());
	}

	@Override
	public Optional<IEntityStat> getStat(ResourceLocation id)
	{
		return Optional.ofNullable(this.stats.get(id));
	}

	public static class Storage implements Capability.IStorage<IEntityStatContainer>
	{
		@Override
		public NBTBase writeNBT(Capability<IEntityStatContainer> capability, IEntityStatContainer instance, EnumFacing side)
		{
			return new NBTTagCompound();
		}

		@Override
		public void readNBT(Capability<IEntityStatContainer> capability, IEntityStatContainer instance, EnumFacing side, NBTBase nbt)
		{

		}
	}

	class StatCollection implements IEntityStat
	{
		private StatBase base;

		private final Collection<StatBase> modifiers = new HashSet<>();

		private StatCollection()
		{
			this(null);
		}

		private StatCollection(StatBase base)
		{
			this.base = base;
		}

		@Override
		public void addModifier(StatBase base)
		{
			this.modifiers.add(base);
		}

		@Override
		public void removeModifier(StatBase base)
		{
			this.modifiers.remove(base);
		}

		private void setBaseStat(StatBase stat)
		{
			this.base = stat;
		}

		@Override
		public Optional<StatBase> getBaseStat()
		{
			return Optional.of(this.base);
		}

		public double getModifiedValue()
		{
			double value = 0;

			for (StatBase stat : this.modifiers)
			{
				value += stat.getValue();
			}

			return value;
		}

		@Override
		public double getValue()
		{
			double base = 0.0D;

			if (this.base != null)
			{
				base = this.base.getValue();
			}

			return base + this.getModifiedValue();
		}

		private boolean isEmpty()
		{
			return this.base == null && this.modifiers.size() <= 0;
		}
	}
}
