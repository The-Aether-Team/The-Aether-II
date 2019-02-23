package com.gildedgames.aether.common.entities.living.passive;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntitySheepuff extends EntityKirrid
{

	public static final DataParameter<Float> PUFFINESS = EntityDataManager.createKey(EntitySheepuff.class, DataSerializers.FLOAT);

	public EntitySheepuff(World worldIn)
	{
		super(worldIn);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(PUFFINESS, 0f);
	}

	@Override
	public EntityKirrid createChild(EntityAgeable ageable)
	{
		return new EntitySheepuff(this.world);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.motionY < -0.1d)
		{
			this.motionY = -0.1d;
		}

		if (!this.onGround)
		{
			this.addPuffiness(0.1f);
		}
		else
		{
			this.addPuffiness(-0.05f);
		}
	}

	@Override
	public void fall(float distance, float damageMultiplier)
	{
	}

	@Override
	public void eatGrassBonus()
	{
		super.eatGrassBonus();

		this.motionY = 1.5f + this.rand.nextFloat();
	}

	@Override
	public int getEatChance()
	{
		return 350;
	}

	public void addPuffiness(float puffiness)
	{
		this.dataManager.set(PUFFINESS, MathHelper.clamp(this.getPuffiness() + puffiness, 0f, 1f));
	}

	public float getPuffiness()
	{
		return this.dataManager.get(PUFFINESS);
	}
}
