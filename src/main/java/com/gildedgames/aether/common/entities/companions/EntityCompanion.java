package com.gildedgames.aether.common.entities.companions;

import com.gildedgames.aether.common.entities.ai.companion.EntityAICompanionFollow;
import java.util.Optional;
import net.minecraft.block.Block;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class EntityCompanion extends CreatureEntity
{
	private static final DataParameter<Optional<UUID>> OWNER_UUID = new DataParameter<>(20, DataSerializers.OPTIONAL_UNIQUE_ID);

	protected boolean isFlying = false;

	private boolean wasDespawned = false;

	public EntityCompanion(final World worldIn)
	{
		super(worldIn);
	}

	@Override
	public void setAttackTarget(@Nullable final LivingEntity target)
	{
		if (target == this.getOwner())
		{
			return;
		}

		super.setAttackTarget(target);
	}

	@Override
	protected void registerGoals()
	{
		final Goal follow = new EntityAICompanionFollow(this);

		follow.setMutexBits(1);

		this.goalSelector.addGoal(1, follow);
		this.goalSelector.addGoal(2, new SwimGoal(this));
		this.goalSelector.addGoal(3, new EntityAILookIdle(this));
		this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 10.0F));
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
	}

	@Override
	protected void registerData()
	{
		super.registerData();

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
	public void livingTick()
	{
		if (!this.world.isRemote && (this.getOwner() == null || this.getOwner().isDead))
		{
			this.remove();

			this.wasDespawned = true;
		}

		super.livingTick();

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
	public boolean writeToNBTOptional(final CompoundNBT compound)
	{
		// Never save Companions to disk...
		return false;
	}

	@Override
	public void setPortal(final BlockPos pos)
	{
		// Never teleport the companion...
	}

	public PlayerEntity getOwner()
	{
		final Optional<UUID> uuid = this.dataManager.get(OWNER_UUID);

		if (!uuid.isPresent())
		{
			return null;
		}

		return this.world.getPlayerEntityByUUID(uuid.get());
	}

	public void setOwner(final PlayerEntity owner)
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
