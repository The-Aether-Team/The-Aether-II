package com.gildedgames.aether.common.entities.living.companions;

import com.gildedgames.aether.common.entities.ai.companion.EntityAICompanionFollow;
import com.google.common.base.Optional;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class EntityCompanion extends EntityCreature
{
	private static final DataParameter<Optional<UUID>> OWNER_UUID = new DataParameter<>(20, DataSerializers.OPTIONAL_UNIQUE_ID);

	protected boolean isFlying = false;

	private boolean wasDespawned = false;

	public EntityCompanion(final World worldIn)
	{
		super(worldIn);
	}

	@Override
	public void setAttackTarget(@Nullable final EntityLivingBase target)
	{
		if (target == this.getOwner())
		{
			return;
		}

		super.setAttackTarget(target);
	}

	@Override
	protected void initEntityAI()
	{
		final EntityAIBase follow = new EntityAICompanionFollow(this);

		follow.setMutexBits(1);

		this.tasks.addTask(1, follow);
		this.tasks.addTask(2, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(OWNER_UUID, Optional.absent());
	}

	@Override
	protected void playStepSound(final BlockPos pos, final Block blockIn)
	{
		if (!this.isFlying)
		{
			super.playStepSound(pos, blockIn);
		}
	}

	@Override
	public boolean canTriggerWalking()
	{
		return !this.isFlying;
	}

	@Override
	public boolean canDespawn()
	{
		return false;
	}

	@Override
	public void onUpdate()
	{
		if (!this.world.isRemote && (this.getOwner() == null || this.getOwner().isDead))
		{
			this.setDead();

			this.wasDespawned = true;
		}

		super.onUpdate();

		this.fallDistance = 0.0f;
	}

	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount)
	{
		final Entity attacker = source.getTrueSource();

		if (attacker != null && attacker == this.getOwner())
		{
			return super.attackEntityFrom(source, 0.0f);
		}

		return super.attackEntityFrom(source, amount);
	}

	@Override
	protected void collideWithEntity(final Entity entity)
	{
		if (entity != this.getOwner())
		{
			entity.applyEntityCollision(this);
		}
	}

	@Override
	public boolean writeToNBTOptional(final NBTTagCompound compound)
	{
		// Never save Companions to disk...
		return false;
	}

	@Override
	public void setPortal(final BlockPos pos)
	{
		// Never teleport the companion...
	}

	public EntityPlayer getOwner()
	{
		final Optional<UUID> uuid = this.dataManager.get(OWNER_UUID);

		if (!uuid.isPresent())
		{
			return null;
		}

		return this.world.getPlayerEntityByUUID(uuid.get());
	}

	public void setOwner(final EntityPlayer owner)
	{
		this.dataManager.set(OWNER_UUID, owner == null ? Optional.absent() : Optional.of(owner.getUniqueID()));
	}

	public boolean wasDespawned()
	{
		return this.wasDespawned;
	}

	public void setDespawned(final boolean despawned)
	{
		this.wasDespawned = despawned;
	}
}
