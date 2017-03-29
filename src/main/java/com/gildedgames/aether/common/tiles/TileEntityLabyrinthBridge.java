package com.gildedgames.aether.common.tiles;

import com.gildedgames.aether.common.tiles.util.TileEntitySynced;
import com.gildedgames.aether.common.util.TickTimer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileEntityLabyrinthBridge extends TileEntitySynced implements ITickable
{

	public static final int MAX_DAMAGE = 9;

	private int damage = -1;

	private TickTimer timer = new TickTimer();

	public void setDamage(int damage)
	{
		if (this.timer.getTicksPassed() > 100)
		{
			if (damage >= MAX_DAMAGE)
			{
				this.world.destroyBlock(this.getPos(), false);

				return;
			}

			this.damage = damage;
			this.timer.reset();

			this.sync();
		}
	}

	public int getDamage()
	{
		return this.damage;
	}

	@Override
	public void update()
	{
		this.timer.tick();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		compound.setInteger("damage", this.getDamage());

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		this.damage = compound.getInteger("damage");
	}

}
