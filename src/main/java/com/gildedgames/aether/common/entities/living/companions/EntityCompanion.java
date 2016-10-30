package com.gildedgames.aether.common.entities.living.companions;

import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.entities.ai.companion.EntityAICompanionFollow;
import com.google.common.base.Optional;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class EntityCompanion extends EntityCreature
{
	private static final DataParameter<Optional<UUID>> OWNER_UUID = new DataParameter<>(20, DataSerializers.OPTIONAL_UNIQUE_ID);

	private boolean wasDespawned = false;

	public EntityCompanion(World worldIn)
	{
		super(worldIn);
	}

	@Override
	public void setAttackTarget(@Nullable EntityLivingBase target)
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
		EntityAIBase follow = new EntityAICompanionFollow(this);

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
	public void onUpdate()
	{
		super.onUpdate();

		this.fallDistance = 0.0f;

		if (!this.worldObj.isRemote)
		{
			if (this.getOwner() == null || this.getOwner().isDead)
			{
				this.setDead();

				this.wasDespawned = true;
			}
			else
			{
				PlayerAetherImpl aePlayer = PlayerAetherImpl.getPlayer(this.getOwner());

				if (aePlayer.getCompanionModule().getCompanionEntity() != this)
				{
					this.setDead();

					this.wasDespawned = true;
				}
			}
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		Entity attacker = source.getEntity();

		if (attacker != null && attacker == this.getOwner())
		{
			return super.attackEntityFrom(source, 0.0f);
		}

		return super.attackEntityFrom(source, amount);
	}

	public abstract void tickEffects(PlayerAetherImpl aePlayer);

	public abstract void addEffects(PlayerAetherImpl aePlayer);

	public abstract void removeEffects(PlayerAetherImpl aePlayer);

	public void setOwner(EntityPlayer owner)
	{
		this.dataManager.set(OWNER_UUID, owner == null ? Optional.absent() : Optional.of(owner.getUniqueID()));
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
