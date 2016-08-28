package com.gildedgames.aether.common.entities.util.mounts;

import com.gildedgames.aether.api.entity.IMountProcessor;
import net.minecraft.entity.Entity;

public class FlyingMount implements IMountProcessor
{

	private IFlyingMountData data;

	public FlyingMount(IFlyingMountData data)
	{
		this.data = data;
	}

	public IFlyingMountData getData()
	{
		return this.data;
	}

	@Override
	public void onUpdate(Entity mount, Entity rider)
	{
		if (mount.onGround)
		{
			this.data.resetRemainingAirborneTime();
		}
	}

	@Override
	public void onHoldSpaceBar(Entity mount, Entity rider)
	{
		if (mount.onGround)
		{
			mount.motionY = 0.55D;
			this.data.resetRemainingAirborneTime();
		}
		else if (mount.motionY < 0.1D && this.data.getRemainingAirborneTime() > 0.0F)
		{
			mount.motionY = 0.1D;

			this.data.addRemainingAirborneTime(-0.1F);
		}
	}

	@Override
	public float getMountedStepHeight(Entity mount)
	{
		return 1.0F;
	}

	@Override
	public boolean canBeMounted(Entity mount)
	{
		return true;
	}

	@Override
	public void onMountedBy(Entity mount, Entity rider)
	{

	}

	@Override
	public void onDismountedBy(Entity mount, Entity rider)
	{

	}

}
