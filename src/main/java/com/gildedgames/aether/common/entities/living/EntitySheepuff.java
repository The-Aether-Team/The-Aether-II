package com.gildedgames.aether.common.entities.living;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntitySheepuff extends EntitySheep
{
	public EntitySheepuff(World world)
	{
		super(world);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
	}

	@Override
	public EntitySheepuff createChild(EntityAgeable ageable)
	{
		return new EntitySheepuff(this.worldObj);
	}
}
