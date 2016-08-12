package com.gildedgames.aether.common.entities.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

import java.util.*;

public class EntityGroup
{

	private static int nextPackID = Integer.MIN_VALUE;

	private static Map<Integer, EntityGroup> packs = new HashMap<>();

	private int size;

	private int id, optimalSize;

	private boolean hasLeader;

	List<EntityGroupAggressor> agressors = new ArrayList<EntityGroupAggressor>();

	int tickCounter = 0;

	public EntityGroup()
	{
	}

	public EntityGroup(int id)
	{
		this.id = id;
	}

	public static int getNextID()
	{
		return EntityGroup.nextPackID++;
	}

	public static void onUpdate()
	{
		for (EntityGroup pack : packs.values())
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

	public int getOptimalSize()
	{
		return this.optimalSize;
	}

	public void setOptimalSize(int size)
	{
		this.optimalSize = size;
		this.refresh();
	}

	public void setSize(int size)
	{
		this.size = size;

		this.refresh();
	}

	public boolean hasLeader()
	{
		return this.hasLeader;
	}

	public void onAnimalDeath(EntityGroupMember groupEntity)
	{
		if (groupEntity.isGroupLeader())
		{
			this.hasLeader = false;
		}

		this.size--;

		this.refresh();
	}

	public void onAnimalJoin(EntityGroupMember groupEntity)
	{
		if (groupEntity.isGroupLeader())
		{
			this.hasLeader = true;
		}

		this.size++;

		this.refresh();
	}

	public void addOrRenewAggressor(EntityLivingBase entity)
	{
		Iterator iterator = this.agressors.iterator();
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

	public EntityLivingBase findNearestAggressor(EntityLivingBase entity)
	{
		double d0 = Double.MAX_VALUE;
		EntityGroupAggressor agressor = null;

		for (int i = 0; i < this.agressors.size(); ++i)
		{
			EntityGroupAggressor agressor1 = this.agressors.get(i);
			double d1 = agressor1.agressor.getDistanceSqToEntity(entity);

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
		Iterator iterator = this.agressors.iterator();

		while (iterator.hasNext())
		{
			EntityGroupAggressor agressor = (EntityGroupAggressor) iterator.next();

			if (!agressor.agressor.isEntityAlive() || (this.tickCounter - agressor.time) > 2200)
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
	public boolean equals(Object object)
	{
		if (!(object instanceof EntityGroup))
		{
			return false;
		}

		EntityGroup pack = (EntityGroup) object;

		return pack.getID() == this.getID();
	}

	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("packID", this.id);
		nbt.setInteger("optimalPackSize", this.optimalSize);
		nbt.setInteger("size", this.size);
		nbt.setInteger("nextPackID", EntityGroup.nextPackID);
		nbt.setInteger("tickCounter", this.tickCounter);
		nbt.setBoolean("hasLeader", this.hasLeader);
	}

	public void readFromNBT(NBTTagCompound nbt)
	{
		this.id = nbt.getInteger("packID");
		this.optimalSize = nbt.getInteger("optimalPackSize");
		this.size = nbt.getInteger("size");
		EntityGroup.nextPackID = nbt.getInteger("nextPackID");
		this.hasLeader = nbt.getBoolean("hasLeader");
		this.tickCounter = nbt.getInteger("tickCounter");

		this.refresh();
	}

}
