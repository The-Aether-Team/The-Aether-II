package com.gildedgames.aether.common.entities.dungeon.labyrinth;

import com.gildedgames.aether.common.SoundsAether;
import com.gildedgames.aether.common.entities.projectiles.EntityBattleBomb;
import com.gildedgames.aether.common.util.TickTimer;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityBattleGolem extends EntityMob implements IRangedAttackMob
{

	private static final DataParameter<Integer> BOMB_COUNT = EntityDataManager.createKey(EntityBattleGolem.class, DataSerializers.VARINT);

	private TickTimer resupplyBombTimer = new TickTimer();

	public EntityBattleGolem(World worldIn)
	{
		super(worldIn);

		this.tasks.addTask(0, new EntityAIAttackRanged(this, 1.0D, 60, 10.0F));
		this.tasks.addTask(1, new EntityAIWander(this, 0.6D));

		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));

		this.setSize(1.0F, 2.0F);
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
		return super.attackEntityAsMob(entity);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.worldObj.isRemote)
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

			if (this.resupplyBombTimer.getTicksPassed() % 20 == 0)
			{
				this.addBomb();
			}

			if (this.resupplyBombTimer.getSecondsPassed() >= 4)
			{
				this.resupplyBombTimer.reset();
			}
		}
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

		double d0 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D;
		double d1 = target.posX + target.motionX - this.posX;
		double d2 = d0 - this.posY;
		double d3 = target.posZ + target.motionZ - this.posZ;
		float f = MathHelper.sqrt_double(d1 * d1 + d3 * d3);

		EntityBattleBomb bomb = new EntityBattleBomb(this.worldObj, this);
		bomb.rotationPitch -= -20.0F;
		bomb.setThrowableHeading(d1, d2 + (double)(f * 0.2F), d3, 0.75F, 8.0F);

		this.worldObj.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);

		this.worldObj.spawnEntityInWorld(bomb);

		this.removeBomb();
	}

}
