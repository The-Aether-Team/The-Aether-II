package com.gildedgames.aether.common.entities.living.dungeon.labyrinth;

import com.gildedgames.aether.common.entities.living.mobs.EntityAetherMob;
import com.gildedgames.aether.common.entities.projectiles.EntityBattleBomb;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import com.gildedgames.aether.common.util.TickTimer;
import com.gildedgames.aether.common.util.io.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityBattleGolem extends EntityAetherMob implements IRangedAttackMob
{

	private static final DataParameter<Integer> BOMB_COUNT = EntityDataManager.createKey(EntityBattleGolem.class, DataSerializers.VARINT);

	private static final DataParameter<Boolean> THROWING_SIDE = EntityDataManager.createKey(EntityBattleGolem.class, DataSerializers.BOOLEAN);

	public enum ThrowingSide
	{
		LEFT, RIGHT
	}

	private TickTimer resupplyBombTimer = new TickTimer();

	public EntityBattleGolem(World worldIn)
	{
		super(worldIn);

		this.tasks.addTask(0, new EntityAIAttackRanged(this, 1.0D, 60, 10.0F));
		this.tasks.addTask(1, new EntityAIWander(this, 0.6D));

		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));

		this.setSize(1.5F, 1.5F);

		this.stepHeight = 1.0F;

		this.experienceValue = 10;
	}

	@Override
	public boolean isOnLadder()
	{
		return false;
	}

	@Override
	protected void jump()
	{

	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{

	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(EntityBattleGolem.BOMB_COUNT, 4);
		this.dataManager.register(EntityBattleGolem.THROWING_SIDE, Boolean.TRUE);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.world.isRemote)
		{
			this.targetTasks.onUpdateTasks();

			if (this.getAttackTarget() != null)
			{
				this.faceEntity(this.getAttackTarget(), 10.0F, 10.0F);
			}
		}

		if (this.getBombCount() <= 0 || this.resupplyBombTimer.getTicksPassed() > 0)
		{
			this.resupplyBombTimer.tick();

			if (this.resupplyBombTimer.isMultipleOfSeconds())
			{
				this.addBomb();
			}

			if (this.resupplyBombTimer.getSecondsPassed() >= 4)
			{
				this.resupplyBombTimer.reset();
			}

			this.setCurrentThrowingSide(ThrowingSide.LEFT);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);

		tag.setInteger("bombCount", this.getBombCount());
		tag.setBoolean("throwingSide", this.getCurrentThrowingSide() == ThrowingSide.LEFT);
		NBTHelper.fullySerialize("resupplyBombTimer", this.resupplyBombTimer, tag);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		super.readEntityFromNBT(tag);

		this.setBombCount(tag.getInteger("bombCount"));
		this.setCurrentThrowingSide(tag.getBoolean("throwingSide") ? ThrowingSide.LEFT : ThrowingSide.RIGHT);
		this.resupplyBombTimer = NBTHelper.fullyDeserialize("resupplyBombTimer", tag, this.resupplyBombTimer);
	}

	@Override
	protected net.minecraft.util.SoundEvent getAmbientSound()
	{
		return SoundsAether.battle_golem_ambient;
	}

	@Override
	protected net.minecraft.util.SoundEvent getHurtSound()
	{
		return SoundsAether.battle_golem_hurt;
	}

	@Override
	protected net.minecraft.util.SoundEvent getDeathSound()
	{
		return SoundsAether.sentry_death;
	}

	public ThrowingSide getCurrentThrowingSide()
	{
		return this.dataManager.get(EntityBattleGolem.THROWING_SIDE) ? ThrowingSide.LEFT : ThrowingSide.RIGHT;
	}

	public void setCurrentThrowingSide(ThrowingSide side)
	{
		this.dataManager.set(EntityBattleGolem.THROWING_SIDE, side == ThrowingSide.LEFT);
	}

	public void switchThrowingSide()
	{
		this.setCurrentThrowingSide(this.getCurrentThrowingSide() == ThrowingSide.LEFT ? ThrowingSide.RIGHT : ThrowingSide.LEFT);
	}

	public void removeBomb()
	{
		this.setBombCount(this.getBombCount() - 1);
	}

	public void addBomb()
	{
		this.setBombCount(this.getBombCount() + 1);
	}

	public void setBombCount(int bombCount)
	{
		this.dataManager.set(EntityBattleGolem.BOMB_COUNT, bombCount);
	}

	public int getBombCount()
	{
		return this.dataManager.get(EntityBattleGolem.BOMB_COUNT);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
	{
		if (this.getBombCount() <= 0 || this.resupplyBombTimer.getTicksPassed() > 0)
		{
			return;
		}

		double d0 = target.posY + (double) target.getEyeHeight() - 1.100000023841858D;
		double d1, d2, d3;

		EntityBattleBomb bomb = new EntityBattleBomb(this.world, this);

		double yaw = Math.toRadians(this.renderYawOffset);

		if (this.getCurrentThrowingSide() == ThrowingSide.LEFT)
		{
			double x = this.posX + Math.cos(yaw) * 1.65;
			double y = this.posY + 1.85;
			double z = this.posZ + Math.sin(yaw) * 1.65;

			d1 = target.posX + target.motionX - x;
			d2 = d0 - y;
			d3 = target.posZ + target.motionZ - z;

			bomb.setPosition(x, y, z);
		}
		else
		{
			double x = this.posX + Math.cos(yaw) * -1.65;
			double y = this.posY + 1.85;
			double z = this.posZ + Math.sin(yaw) * -1.65;

			d1 = target.posX + target.motionX - x;
			d2 = d0 - y;
			d3 = target.posZ + target.motionZ - z;

			bomb.setPosition(x, y, z);
		}

		float f = MathHelper.sqrt(d1 * d1 + d3 * d3);

		bomb.rotationPitch -= -20.0F;
		bomb.setThrowableHeading(d1, d2 + (double) (f * 0.2F), d3, 0.75F, 8.0F);

		this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F,
				0.8F + this.rand.nextFloat() * 0.4F);

		this.world.spawnEntity(bomb);

		this.removeBomb();
		this.switchThrowingSide();
	}

	@Override
	public boolean canDespawn()
	{
		return false;
	}

}
