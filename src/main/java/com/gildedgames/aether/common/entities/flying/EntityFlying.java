package com.gildedgames.aether.common.entities.flying;

import com.gildedgames.aether.api.entity.damage.IDefenseLevelsHolder;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.common.entities.ai.EntityAIForcedWander;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

public class EntityFlying extends EntityCreature implements IDefenseLevelsHolder
{
	private Map<IAetherStatusEffects.effectTypes, Boolean> applicationTracker = new HashMap<>();
	private Map<IAetherStatusEffects.effectTypes, Double> resistances = new HashMap<>();

	private static final DataParameter<Boolean> IS_MOVING = EntityDataManager.createKey(EntityFlying.class, DataSerializers.BOOLEAN);

	private final float clientSideTailAnimationO;

	private float clientSideTailAnimationSpeed;

	private float clientSideTailAnimation;

	public EntityFlying(final World world)
	{
		super(world);

		this.setSize(0.85F, 0.85F);
		this.moveHelper = new FlyingMoveHelper(this);

		this.clientSideTailAnimation = this.rand.nextFloat();
		this.clientSideTailAnimationO = this.clientSideTailAnimation;
	}

	protected EntityAIBase createWanderTask()
	{
		final EntityAIWander wander = new EntityAIForcedWander(this, 0.4D, 5);

		wander.setMutexBits(1);

		return wander;
	}

	@Override
	protected void initEntityAI()
	{
		final EntityAIMoveTowardsRestriction moveTowardsRestriction = new EntityAIMoveTowardsRestriction(this, 0.4D);

		moveTowardsRestriction.setMutexBits(1);

		this.tasks.addTask(1, moveTowardsRestriction);
		this.tasks.addTask(2, this.createWanderTask());
	}

	@Override
	protected EntityBodyHelper createBodyHelper()
	{
		return new EntityBodyHelperFlying(this);
	}

	@SideOnly(Side.CLIENT)
	public float getTailAnimation(final float deltaTime)
	{
		return this.clientSideTailAnimationO + (this.clientSideTailAnimation - this.clientSideTailAnimationO) * deltaTime;
	}

	@Override
	public boolean isOnLadder()
	{
		return false;
	}

	@Override
	public float getBlockPathWeight(final BlockPos pos)
	{
		return this.world.getBlockState(pos).getMaterial() == Material.AIR ? 10.0F + this.world.getLightBrightness(pos) - 0.5F : super.getBlockPathWeight(pos);
	}

	@Override
	public boolean getCanSpawnHere()
	{
		final IBlockState state = this.world.getBlockState((new BlockPos(this)).down());

		return state.canEntitySpawn(this)
				&& this.getBlockPathWeight(this.world.getTopSolidOrLiquidBlock(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ))) >= 0.0F;
	}

	@Override
	protected void playStepSound(final BlockPos pos, final Block blockIn)
	{

	}

	@Override
	protected void jump()
	{

	}

	@Override
	public void fall(final float distance, final float damageMultiplier)
	{

	}

	@Override
	protected void updateFallState(final double y, final boolean onGroundIn, final IBlockState state, final BlockPos pos)
	{

	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(EntityFlying.IS_MOVING, false);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		this.fallDistance = 0.0F;

		if (this.onGround)
		{
			this.motionY += 0.10D;
			//this.motionX += (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
			//this.motionZ += (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
			this.rotationYaw = this.rand.nextFloat() * 360.0F;
			this.onGround = false;
			this.isAirBorne = true;
		}

		if (this.collidedHorizontally || this.collidedVertically || !this.isNotColliding())
		{
			this.motionX += (this.rand.nextBoolean() ? 1.0F : -1.0F) * (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.2F);
			this.motionZ += (this.rand.nextBoolean() ? 1.0F : -1.0F) * (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.2F);
		}

		if (this.world.isRemote)
		{
			if (this.isMoving())
			{
				if (this.clientSideTailAnimationSpeed < 0.5F)
				{
					this.clientSideTailAnimationSpeed = 4.0F;
				}
				else
				{
					this.clientSideTailAnimationSpeed += (0.5F - this.clientSideTailAnimationSpeed) * 0.1F;
				}
			}
			else
			{
				this.clientSideTailAnimationSpeed += (0.125F - this.clientSideTailAnimationSpeed) * 0.2F;
			}

			this.clientSideTailAnimation += this.clientSideTailAnimationSpeed;
		}
	}

	@Override
	public void onEntityUpdate()
	{
		IAetherStatusEffectPool statusEffectPool = this.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

		if (statusEffectPool == null)
		{
			return;
		}

		for (Map.Entry<IAetherStatusEffects.effectTypes, Double> effect : resistances.entrySet())
		{
			applicationTracker.putIfAbsent(effect.getKey(), false);

			if (!applicationTracker.get(effect.getKey()))
			{
				statusEffectPool.addResistanceToEffect(effect.getKey(), effect.getValue());

				if (statusEffectPool.getResistanceToEffect(effect.getKey()) != 1.0D)
				{
					applicationTracker.put(effect.getKey(), true);
				}
			}
			else
			{
				if (statusEffectPool.getResistanceToEffect(effect.getKey()) == 1.0D)
				{
					applicationTracker.put(effect.getKey(), false);
				}
			}
		}

		super.onEntityUpdate();
	}

	protected Map<IAetherStatusEffects.effectTypes, Double> getResistances()
	{
		return resistances;
	}

	@Override
	protected PathNavigate createNavigator(final World worldIn)
	{
		return new PathNavigateFlyer(this, worldIn);
	}

	public boolean isMoving()
	{
		return this.dataManager.get(EntityFlying.IS_MOVING);
	}

	protected void setMoving(final boolean moving)
	{
		this.dataManager.set(EntityFlying.IS_MOVING, moving);
	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	public boolean isNotColliding()
	{
		return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this)
				&& this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty();
	}

	@Override
	public int getVerticalFaceSpeed()
	{
		return 1;
	}

	@Override
	public int getHorizontalFaceSpeed()
	{
		return 1;
	}

	@Override
	public void travel(final float strafe, final float vertical, final float forward)
	{
		if (this.isServerWorld())
		{
			super.moveRelative(strafe, vertical, forward, 0.1F);
			this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.8999999761581421D;
			this.motionY *= 0.8999999761581421D;
			this.motionZ *= 0.8999999761581421D;
		}
		else
		{
			super.travel(strafe, vertical, forward);
		}
	}

	@Override
	protected boolean canDespawn()
	{
		return false;
	}

}
