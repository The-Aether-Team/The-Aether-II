package com.gildedgames.aether.common.entities.living.dungeon.labyrinth;

import com.gildedgames.aether.common.entities.ai.dungeon.labyrinth.AISentryGuardianMelee;
import com.gildedgames.aether.common.entities.util.flying.EntityFlyingMob;
import com.gildedgames.aether.common.registry.minecraft.SoundsAether;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import com.gildedgames.aether.common.util.TickTimer;
import com.gildedgames.util.core.nbt.NBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class EntitySentryGuardian extends EntityFlyingMob implements IRangedAttackMob
{

	private static final DataParameter<Boolean> OVERHEATING = EntityDataManager.createKey(EntitySentryGuardian.class, DataSerializers.BOOLEAN);

	private TickTimer attackTimer = new TickTimer();

	private TickTimer overheatingTimer = new TickTimer();

	private TickTimer spawnRepairSentriesTimer = new TickTimer();

	private UUID repairSentry1, repairSentry2;

	public EntitySentryGuardian(World worldIn)
	{
		super(worldIn);

		this.setSize(2.0F, 4.0F);
	}

	@Override
	protected void initEntityAI()
	{
		super.initEntityAI();

		this.tasks.addTask(0, new AISentryGuardianMelee(this, 1.0D, 5));
		this.tasks.addTask(1, new EntityAIAttackRanged(this, 1.0D, 60, 15.0F));

		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
	}

	public TickTimer getAttackTimer()
	{
		return this.attackTimer;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(EntitySentryGuardian.OVERHEATING, Boolean.FALSE);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(60);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
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

		if (this.isOverheating())
		{
			if (this.overheatingTimer.getSecondsPassed() < 10)
			{
				this.overheatingTimer.tick();
			}
			else
			{
				this.setOverheating(false);
				this.overheatingTimer.reset();
			}
		}

		this.spawnRepairSentriesTimer.tick();

		if (this.spawnRepairSentriesTimer.isMultipleOfSeconds(60))
		{
			List<EntityProductionLine> productionLines = this.worldObj.getEntitiesWithinAABB(EntityProductionLine.class, this.getEntityBoundingBox().expand(60.0D, 60.0D, 60.0D));

			for (EntityProductionLine productionLine : productionLines)
			{
				if (productionLine != null && productionLine.isBroken())
				{
					this.spawnRepairSentries();

					break;
				}
			}
		}
	}

	private void spawnRepairSentries()
	{
		if (this.worldObj.isRemote)
		{
			return;
		}

		if (this.repairSentry1 != null)
		{
			Entity entity = EntityUtil.getEntityFromUUID(this.worldObj, this.repairSentry1);

			if (entity == null)
			{
				this.repairSentry1 = null;
			}
		}

		if (this.repairSentry2 != null)
		{
			Entity entity = EntityUtil.getEntityFromUUID(this.worldObj, this.repairSentry2);

			if (entity == null)
			{
				this.repairSentry2 = null;
			}
		}

		if (this.repairSentry1 == null)
		{
			EntityRepairSentry repairSentry = new EntityRepairSentry(this.worldObj);

			int scatterX = (this.rand.nextBoolean() ? 1 : -1) * 2;
			int scatterZ = (this.rand.nextBoolean() ? 1 : -1) * 2;

			repairSentry.setPosition(this.posX + scatterX, this.posY, this.posZ + scatterZ);

			this.worldObj.spawnEntityInWorld(repairSentry);

			this.repairSentry1 = repairSentry.getUniqueID();
		}

		if (this.repairSentry2 == null)
		{
			EntityRepairSentry repairSentry = new EntityRepairSentry(this.worldObj);

			int scatterX = (this.rand.nextBoolean() ? 1 : -1) * 2;
			int scatterZ = (this.rand.nextBoolean() ? 1 : -1) * 2;

			repairSentry.setPosition(this.posX + scatterX, this.posY, this.posZ + scatterZ);

			this.worldObj.spawnEntityInWorld(repairSentry);

			this.repairSentry2 = repairSentry.getUniqueID();
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		return super.attackEntityAsMob(entity);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);

		tag.setBoolean("overheating", this.isOverheating());

		NBTHelper.fullySerialize("attackTimer", this.attackTimer, tag);
		NBTHelper.fullySerialize("overheatingTimer", this.overheatingTimer, tag);
		NBTHelper.fullySerialize("spawnRepairSentriesTimer", this.spawnRepairSentriesTimer, tag);

		if (this.repairSentry1 == null)
		{
			tag.setBoolean("repairSentry1Null", true);
		}
		else
		{
			tag.setBoolean("repairSentry1Null", false);
			tag.setUniqueId("repairSentry1", this.repairSentry1);
		}

		if (this.repairSentry2 == null)
		{
			tag.setBoolean("repairSentry2Null", true);
		}
		else
		{
			tag.setBoolean("repairSentry2Null", false);
			tag.setUniqueId("repairSentry2", this.repairSentry2);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		super.readEntityFromNBT(tag);

		this.setOverheating(tag.getBoolean("overheating"));

		this.attackTimer = NBTHelper.fullyDeserialize("attackTimer", tag);
		this.overheatingTimer = NBTHelper.fullyDeserialize("overheatingTimer", tag);
		this.spawnRepairSentriesTimer = NBTHelper.fullyDeserialize("spawnRepairSentriesTimer", tag);

		if (!tag.getBoolean("repairSentry1Null"))
		{
			this.repairSentry1 = tag.getUniqueId("repairSentry1");
		}

		if (!tag.getBoolean("repairSentry2Null"))
		{
			this.repairSentry2 = tag.getUniqueId("repairSentry2");
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
		this.dataManager.set(EntitySentryGuardian.OVERHEATING, flag);
	}

	public boolean isOverheating()
	{
		return this.dataManager.get(EntitySentryGuardian.OVERHEATING);
	}

	@Override
	public boolean canDespawn()
	{
		return false;
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
	{
		if (!this.isOverheating())
		{
			return;
		}

		double d0 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D;
		double d1 = target.posX + target.motionX - this.posX;
		double d2 = d0 - this.posY;
		double d3 = target.posZ + target.motionZ - this.posZ;
		float f = MathHelper.sqrt_double(d1 * d1 + d3 * d3);

		EntityDetonationSentry sentry = new EntityDetonationSentry(this.worldObj);

		sentry.setPosition(this.posX, this.posY + (double)this.getEyeHeight() - 0.10000000149011612D, this.posZ);

		sentry.rotationPitch -= -20.0F;
		sentry.setThrowableHeading(d1, d2 + (double)(f * 0.2F), d3, 1.0F, 8.0F);

		this.worldObj.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);

		this.worldObj.spawnEntityInWorld(sentry);
	}
}
