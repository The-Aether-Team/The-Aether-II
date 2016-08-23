package com.gildedgames.aether.common.entities.dungeon.labyrinth;

import com.gildedgames.aether.common.SoundsAether;
import com.gildedgames.aether.common.entities.ai.dungeon.labyrinth.AISavageAttack;
import com.gildedgames.aether.common.entities.util.EntityExtendedMob;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityChestMimic extends EntityExtendedMob
{

	private static final DataParameter<Boolean> OVERHEATING = EntityDataManager.createKey(EntityChestMimic.class, DataSerializers.BOOLEAN);

	public EntityChestMimic(World worldIn)
	{
		super(worldIn);

		this.tasks.addTask(0, new AISavageAttack(this, 1.0D, 7, 5));

		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));

		this.setSize(1.0F, 1.5F);
		this.stepHeight = 1.0F;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{

	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(EntityChestMimic.OVERHEATING, Boolean.FALSE);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		this.worldObj.spawnParticle(EnumParticleTypes.MOB_APPEARANCE, this.posX + motionX, this.posY + 0.5D + motionY, this.posZ + motionZ, 0.1D, 0.1D, 0.1D);

		return super.attackEntityAsMob(entity);
	}

	@Override
	protected void handleClientAttack()
	{
		for (int k = 0; k < 2; ++k)
		{
			double motionX = (this.getRNG().nextBoolean() ? 1.0D : -1.0D) * this.getRNG().nextFloat();
			double motionY = (this.getRNG().nextBoolean() ? 1.0D : -1.0D) * this.getRNG().nextFloat();
			double motionZ = (this.getRNG().nextBoolean() ? 1.0D : -1.0D) * this.getRNG().nextFloat();

			this.worldObj.spawnParticle(EnumParticleTypes.SWEEP_ATTACK, this.posX + motionX, this.posY + 0.75D + motionY, this.posZ + motionZ, motionX, motionY, motionZ);
		}
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.isOverheating())
		{
			for (int k = 0; k < 2; ++k)
			{
				double motionX = (this.getRNG().nextBoolean() ? 1.0D : -1.0D) * this.getRNG().nextFloat();
				double motionY = (this.getRNG().nextBoolean() ? 1.0D : -1.0D) * this.getRNG().nextFloat();
				double motionZ = (this.getRNG().nextBoolean() ? 1.0D : -1.0D) * this.getRNG().nextFloat();

				this.worldObj.spawnParticle(EnumParticleTypes.CLOUD, this.posX + motionX, this.posY + 0.5D + motionY, this.posZ + motionZ, 0.1D, 0.1D, 0.1D);
			}
		}
	}

	@Override
	protected net.minecraft.util.SoundEvent getAmbientSound()
	{
		return SoundsAether.sentry_ambient;
	}

	@Override
	protected net.minecraft.util.SoundEvent getHurtSound()
	{
		return SoundsAether.sentry_hurt;
	}

	@Override
	protected net.minecraft.util.SoundEvent getDeathSound()
	{
		return SoundsAether.sentry_death;
	}

	public void setOverheating(boolean flag)
	{
		this.dataManager.set(EntityChestMimic.OVERHEATING, flag);
	}

	public boolean isOverheating()
	{
		return this.dataManager.get(EntityChestMimic.OVERHEATING);
	}

}
