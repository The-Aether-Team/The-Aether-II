package com.gildedgames.aether.common.entities.util.groups;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;

import java.util.*;

public class EntityGroup
{

	private static final Map<Integer, EntityGroup> packs = new HashMap<>();

	private static int nextPackID = Integer.MIN_VALUE;

	final List<EntityGroupAggressor> agressors = new ArrayList<>();

	int tickCounter = 0;

	private int size;

	private int id, optimalSize;

	private boolean hasLeader;

	public EntityGroup()
	{
	}

	public EntityGroup(final int id)
	{
		this.id = id;
	}

	public static int getNextID()
	{
		return EntityGroup.nextPackID++;
	}

	public static void onUpdate()
	{
		for (final EntityGroup pack : packs.values())
		{
			++pack.tickCounter;
			pack.removeDeadAndOldAgressors();
		}
	}

	public int getID()
	{
		return this.id;
	}

	public int getSize()
	{
		return this.size;
	}

	public void setSize(final int size)
	{
		this.size = size;

		this.refresh();
	}

	public int getOptimalSize()
	{
		return this.optimalSize;
	}

	public void setOptimalSize(final int size)
	{
		this.optimalSize = size;
		this.refresh();
	}

	public boolean hasLeader()
	{
		return this.hasLeader;
	}

	public void onAnimalDeath(final EntityGroupMember groupEntity)
	{
		if (groupEntity.isGroupLeader())
		{
			this.hasLeader = false;
		}

		this.size--;

		this.refresh();
	}

	public void onAnimalJoin(final EntityGroupMember groupEntity)
	{
		if (groupEntity.isGroupLeader())
		{
			this.hasLeader = true;
		}

		this.size++;

		this.refresh();
	}

	public void addOrRenewAggressor(final LivingEntity entity)
	{
		final Iterator iterator = this.agressors.iterator();
		EntityGroupAggressor agressor;

		do
		{
			if (!iterator.hasNext())
			{
				this.agressors.add(new EntityGroupAggressor(this, entity, this.tickCounter));
				return;
			}

			agressor = (EntityGroupAggressor) iterator.next();
		}
		while (agressor.agressor != entity);

		agressor.time = this.tickCounter;
	}

	public LivingEntity findNearestAggressor(final LivingEntity entity)
	{
		double d0 = Double.MAX_VALUE;
		EntityGroupAggressor agressor = null;

		for (final EntityGroupAggressor agressor1 : this.agressors)
		{
			final double d1 = agressor1.agressor.getDistanceSq(entity);

			if (d1 <= d0)
			{
				agressor = agressor1;
				d0 = d1;
			}
		}

		return agressor != null ? agressor.agressor : null;
	}

	private void removeDeadAndOldAgressors()
	{
		final Iterator iterator = this.agressors.iterator();

		while (iterator.hasNext())
		{
			final EntityGroupAggressor agressor = (EntityGroupAggressor) iterator.next();

			if (!agressor.agressor.isAlive() || (this.tickCounter - agressor.time) > 2200)
			{
				iterator.remove();
			}
		}
	}

	private void refresh()
	{
		EntityGroup.packs.put(this.id, this);
	}

	@Override
	public boolean equals(final Object object)
	{
		if (!(object instanceof EntityGroup))
		{
			return false;
		}

		final EntityGroup pack = (EntityGroup) object;

		return pack.getID() == this.getID();
	}

	public void writeToNBT(final CompoundNBT nbt)
	{
		nbt.putInt("packID", this.id);
		nbt.putInt("optimalPackSize", this.optimalSize);
		nbt.putInt("size", this.size);
		nbt.putInt("nextPackID", EntityGroup.nextPackID);
		nbt.putInt("tickCounter", this.tickCounter);
		nbt.putBoolean("hasLeader", this.hasLeader);
	}

	public void readFromNBT(final CompoundNBT nbt)
	{
		this.id = nbt.getInt("packID");
		this.optimalSize = nbt.getInt("optimalPackSize");
		this.size = nbt.getInt("size");
		EntityGroup.nextPackID = nbt.getInt("nextPackID");
		this.hasLeader = nbt.getBoolean("hasLeader");
		this.tickCounter = nbt.getInt("tickCounter");

		this.refresh();
	}

}
