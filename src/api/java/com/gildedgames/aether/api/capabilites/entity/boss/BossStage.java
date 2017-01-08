package com.gildedgames.aether.api.capabilites.entity.boss;

import com.gildedgames.aether.api.util.NBT;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

public abstract class BossStage<T extends Entity> implements NBT
{

	private boolean hasBegun;

	protected abstract boolean conditionsMet(T entity, IBossManager<T> manager);

	protected abstract void onStageBegin(T entity, IBossManager<T> manager);

	public void update(T entity, IBossManager<T> manager)
	{
		if (!this.hasBegun() && this.conditionsMet(entity, manager))
		{
			this.onStageBegin(entity, manager);
			this.setHasBegun(true);
		}
	}

	public boolean hasBegun()
	{
		return this.hasBegun;
	}

	private void setHasBegun(boolean flag)
	{
		this.hasBegun = flag;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		output.setBoolean("hasBegun", this.hasBegun);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.hasBegun = input.getBoolean("hasBegun");
	}
}
