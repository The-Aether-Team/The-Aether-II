package com.gildedgames.aether.common.entities.companions;

import com.gildedgames.aether.common.entities.ai.companion.EntityAICompanionFollow;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

public abstract class EntityCompanion extends CreatureEntity
{
	private static final DataParameter<Optional<UUID>> OWNER_UUID = new DataParameter<>(20, DataSerializers.OPTIONAL_UNIQUE_ID);

	protected boolean isFlying = false;

	private boolean wasDespawned = false;

	protected EntityCompanion(EntityType<? extends CreatureEntity> type, World worldIn)
	{
		super(type, worldIn);
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

		follow.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));

		this.goalSelector.addGoal(1, follow);
		this.goalSelector.addGoal(2, new SwimGoal(this));
		this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
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

		this.dataManager.register(OWNER_UUID, Optional.empty());
	}

	@Override
	protected void playStepSound(final BlockPos pos, final BlockState blockIn)
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
	public boolean canDespawn(double closestDistance)
	{
		return false;
	}

	@Override
	public void tick()
	{
		if (!this.world.isRemote() && (this.getOwner() == null || !this.getOwner().isAlive()))
		{
			this.remove();

			this.wasDespawned = true;
		}

		super.tick();

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
	public void setPortal(final BlockPos pos)
	{
		// Never teleport the companion...
	}

	public PlayerEntity getOwner()
	{
		return this.dataManager.get(OWNER_UUID).map(value -> this.world.getPlayerByUuid(value)).orElse(null);
	}

	public void setOwner(final PlayerEntity owner)
	{
		this.dataManager.set(OWNER_UUID, owner == null ? Optional.empty() : Optional.of(owner.getUniqueID()));
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
