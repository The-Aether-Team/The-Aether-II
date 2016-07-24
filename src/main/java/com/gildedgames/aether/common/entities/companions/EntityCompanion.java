package com.gildedgames.aether.common.entities.companions;

import com.gildedgames.aether.common.entities.ai.EntityAICompanionFollow;
import com.gildedgames.aether.common.player.PlayerAether;
import com.google.common.base.Optional;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class EntityCompanion extends EntityLiving
{
	private static final DataParameter<Optional<UUID>> OWNER_UUID = new DataParameter<>(20, DataSerializers.OPTIONAL_UNIQUE_ID);

	private boolean wasDespawned = false;

	public EntityCompanion(World worldIn)
	{
		super(worldIn);
	}

	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAICompanionFollow(this));
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAILookIdle(this));
		this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(OWNER_UUID, Optional.<UUID>absent());
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		this.fallDistance = 0.0f;

		if (!this.worldObj.isRemote && (this.getOwner() == null || this.getOwner().isDead))
		{
			this.setDead();

			this.wasDespawned = true;
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		Entity attacker = source.getEntity();

		if (attacker != null && attacker == this.getOwner())
		{
			double motionX = attacker.posX - this.posX;
			double motionZ;

			motionZ = attacker.posZ - this.posZ;

			while (motionX * motionX + motionZ * motionZ < 1.0E-4D)
			{
				motionX = (Math.random() - Math.random()) * 0.01D;
				motionZ = (Math.random() - Math.random()) * 0.01D;
			}

			this.knockBack(this.getOwner(), 0.5F, motionX, motionZ);

			return false;
		}

		return super.attackEntityFrom(source, amount);
	}

	public abstract void tickEffects(PlayerAether aePlayer);

	public abstract void addEffects(PlayerAether aePlayer);

	public abstract void removeEffects(PlayerAether aePlayer);

	public void setOwner(EntityPlayer owner)
	{
		this.dataManager.set(OWNER_UUID, Optional.of(owner.getUniqueID()));
	}

	public EntityPlayer getOwner()
	{
		Optional<UUID> uuid = this.dataManager.get(OWNER_UUID);

		if (!uuid.isPresent())
		{
			return null;
		}

		return this.worldObj.getPlayerEntityByUUID(uuid.get());
	}

	public boolean wasDespawned()
	{
		return this.wasDespawned;
	}
}
