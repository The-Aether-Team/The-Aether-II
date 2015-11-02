package com.gildedgames.aether.common.entities.living;

import com.gildedgames.aether.common.entities.ai.AechorPlantAI;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityAechorPlant extends EntityMob
{
	public float sinage;

	public EntityAechorPlant(World worldIn)
	{
		super(worldIn);

		this.targetTasks.addTask(0, new AechorPlantAI(this, true));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));

		this.setSize(0.75F /*+ (this.getSize() * 0.125F)*/, 0.5F /*+ (this.getSize() * 0.075F)*/);

		this.sinage = this.rand.nextFloat() * 6F;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(4.0F);
	}

	public boolean isTargettingPrey()
	{
		return this.getAttackTarget() != null;
	}

	public int getSize()
	{
		return 1;
	}
}
