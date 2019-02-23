package com.gildedgames.aether.common.entities.util.flying.advanced;

import com.gildedgames.aether.common.entities.ai.EntityAIForcedWander;
import com.gildedgames.aether.common.entities.util.AetherDataSerializers;
import com.gildedgames.aether.common.entities.util.flying.PathNavigateFlyer;
import com.gildedgames.aether.common.util.helpers.MathUtil;
import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

import javax.vecmath.Point3d;
import java.util.Optional;

public class EntityFlyingAdvanced extends EntityCreature
{

	private static final DataParameter<Optional<FlightPath>> CUR_PATH = EntityDataManager.createKey(EntityFlyingAdvanced.class, AetherDataSerializers.PATH);

	private static final DataParameter<Optional<FlightPath>> FUTURE_PATH = EntityDataManager.createKey(EntityFlyingAdvanced.class, AetherDataSerializers.PATH);

	private static final DataParameter<Float> FLY_T = EntityDataManager.createKey(EntityFlyingAdvanced.class, DataSerializers.FLOAT);

	private static final DataParameter<Optional<FloatList>> SPEED_MAP = EntityDataManager.createKey(EntityFlyingAdvanced.class, AetherDataSerializers.FLOAT_LIST);

	private static final DataParameter<Optional<FloatList>> FUTURE_SPEED_MAP = EntityDataManager.createKey(EntityFlyingAdvanced.class, AetherDataSerializers.FLOAT_LIST);

	private static final DataParameter<Optional<FloatList>> TIME_MAP = EntityDataManager.createKey(EntityFlyingAdvanced.class, AetherDataSerializers.FLOAT_LIST);

	private static final DataParameter<Optional<FloatList>> FUTURE_TIME_MAP = EntityDataManager.createKey(EntityFlyingAdvanced.class, AetherDataSerializers.FLOAT_LIST);

	private float lastVelocity = 0.023f, velocityStep = 0.03f, tagetVelocity = 0.023f;

	public EntityFlyingAdvanced(World worldIn)
	{
		super(worldIn);

		this.moveHelper = new FlyingAdvancedMoveHelper(this);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(CUR_PATH, Optional.empty());
		this.dataManager.register(FUTURE_PATH,  Optional.empty());
		this.dataManager.register(FLY_T, 0F);
		this.dataManager.register(SPEED_MAP, Optional.empty());
		this.dataManager.register(TIME_MAP, Optional.empty());
		this.dataManager.register(FUTURE_SPEED_MAP, Optional.empty());
		this.dataManager.register(FUTURE_TIME_MAP, Optional.empty());
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if (this.getFlightPath() == null)
		{
			this.moveHelper.setMoveTo(this.posX + this.rand.nextInt(5), this.posY + this.rand.nextInt(5), this.posZ + this.rand.nextInt(5), 1);
		}

		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		this.rotationYaw = 0;
		this.rotationPitch = 0;

		if (this.getFlightPath() != null && !this.world.isRemote)
		{
			float t = this.getTime();

			Optional<FloatList> speedMap = this.dataManager.get(SPEED_MAP);
			Optional<FloatList> timeMap = this.dataManager.get(TIME_MAP);

			if (speedMap.isPresent() && timeMap.isPresent())
			{
				FloatList speedList = speedMap.get();
				FloatList timeList = timeMap.get();

				if (!timeList.isEmpty())
				{
					float threshold = timeList.get(0);

					if (t > threshold)
					{
						timeList.remove(0);

//						this.lastVelocity = this.tagetVelocity;
//						this.tagetVelocity = speedList.get(0) * 20f;
//						this.velocityStep = (this.tagetVelocity - this.lastVelocity) / 20f;

						speedList.remove(0);
					}
				}
			}

			if (t < 1)
			{
				this.setFlyTime(t + 0.05f);
//				this.setFlyTime(t + (this.lastVelocity = Math.min(this.tagetVelocity, this.lastVelocity + this.velocityStep)));

				if (t > .1f)
				{
					((FlyingAdvancedMoveHelper) this.moveHelper).setNext(this.getFlightPath(), null);
				}
			}

			Point3d pos = MathUtil.getPoints(this.getFlightPath(), this.getTime());

			this.setPositionAndUpdate(pos.x, pos.y, pos.z);

			if (t >= 1 && this.getFutureFlightPath() != null)
			{
				((FlyingAdvancedMoveHelper) this.moveHelper).next();
				this.updateFlyNav(this.getFutureFlightPath());
				this.updateSpeedMap(this.getFutureSpeedMap(), this.getFutureTimeMap());
				this.setFlyTime(0);
			}
		}
	}

	@Override
	protected void initEntityAI()
	{
		final EntityAIMoveTowardsRestriction moveTowardsRestriction = new EntityAIMoveTowardsRestriction(this, 0.4D);
		final EntityAIWander wander = new EntityAIForcedWander(this, 0.4D, 20, 20, 20);

		wander.setMutexBits(3);
		moveTowardsRestriction.setMutexBits(3);

		this.tasks.addTask(1, moveTowardsRestriction);
		this.tasks.addTask(2, wander);
	}

	@Override
	public void fall(float distance, float damageMultiplier)
	{

	}

	@Override
	public boolean attackable()
	{
		return false;
	}

	@Override
	protected PathNavigate createNavigator(final World worldIn)
	{
		return new PathNavigateFlyer(this, worldIn);
	}

	public FlyingAdvancedMoveHelper getFlyHelper()
	{
		return (FlyingAdvancedMoveHelper) this.moveHelper;
	}

	public void updateFlyNav(FlightPath flyNav)
	{
		this.dataManager.set(CUR_PATH, Optional.ofNullable(flyNav));
	}

	public void updateFutureFlyNav(FlightPath flyNav)
	{
		this.dataManager.set(FUTURE_PATH, Optional.ofNullable(flyNav));
	}

	public void setFlyTime(float t)
	{
		this.dataManager.set(FLY_T, t);
	}

	public float getTime()
	{
		return this.dataManager.get(FLY_T);
	}

	public Point3d getPoint(float off)
	{
		return MathUtil.getPoints(this.getFlightPath(), this.getTime() + this.velocityStep * off);
	}

	public FlightPath getFlightPath()
	{
		Optional<FlightPath> path = this.dataManager.get(CUR_PATH);

		return path.map(o -> path.get()).orElse(null);
	}


	public FlightPath getFutureFlightPath()
	{
		Optional<FlightPath> path = this.dataManager.get(FUTURE_PATH);

		return path.map(o -> path.get()).orElse(null);
	}

	public void updateSpeedMap(FloatList speedMap, FloatList timeMap)
	{
		this.dataManager.set(SPEED_MAP, Optional.ofNullable(speedMap));
		this.dataManager.set(TIME_MAP, Optional.ofNullable(timeMap));
	}

	public FloatList getSpeedMap()
	{
		Optional<FloatList> path = this.dataManager.get(SPEED_MAP);

		return path.map(o -> path.get()).orElse(null);
	}

	public FloatList getTimeMap()
	{
		Optional<FloatList> path = this.dataManager.get(TIME_MAP);

		return path.map(o -> path.get()).orElse(null);
	}


	public void updateFutureSpeedMap(FloatList speedMap, FloatList timeMap)
	{
		this.dataManager.set(FUTURE_SPEED_MAP, Optional.ofNullable(speedMap));
		this.dataManager.set(FUTURE_TIME_MAP, Optional.ofNullable(timeMap));
	}

	public FloatList getFutureSpeedMap()
	{
		Optional<FloatList> path = this.dataManager.get(FUTURE_SPEED_MAP);

		return path.map(o -> path.get()).orElse(null);
	}

	public FloatList getFutureTimeMap()
	{
		Optional<FloatList> path = this.dataManager.get(FUTURE_TIME_MAP);

		return path.map(o -> path.get()).orElse(null);
	}
}
