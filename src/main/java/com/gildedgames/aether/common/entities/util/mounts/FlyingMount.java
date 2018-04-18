package com.gildedgames.aether.common.entities.util.mounts;

import com.gildedgames.aether.api.entity.IMountProcessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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
		return this.data.canBeMounted();
	}

	@Override
	public boolean canProcessMountInteraction(Entity mount, Entity rider)
	{
		if (rider instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) rider;
			ItemStack stack = player.getHeldItemMainhand();

			return this.data.canProcessMountInteraction(player, stack);
		}

		return false;
	}

	@Override
	public void onMountedBy(Entity mount, Entity rider)
	{
		if (mount instanceof EntityLivingBase)
		{
			((EntityLivingBase) mount).getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
		}
	}

	@Override
	public void onDismountedBy(Entity mount, Entity rider)
	{
		if (mount instanceof EntityLivingBase)
		{
			((EntityLivingBase) mount).getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		}
	}

}
