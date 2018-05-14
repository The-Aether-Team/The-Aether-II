package com.gildedgames.aether.common.entities.living.companions;

import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.world.World;

public class EntityNexSpirit extends EntityCompanion
{
	private static final DataParameter<Boolean> IS_BROKEN = new DataParameter<>(21, DataSerializers.BOOLEAN);

	public EntityNexSpirit(World worldIn)
	{
		super(worldIn);

		this.setSize(0.6f, 1.85f);
		this.stepHeight = 1.0F;
		this.isFlying = true;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		//		if (this.getOwner() != null)
		//		{
		//			PlayerAether aePlayer = PlayerAether.getPlayer(this.getOwner());
		//
		//			PlayerCompanionModule companionManager = aePlayer.getCompanionModule();
		//
		//			ItemStack equippedCompanion = companionManager.getCompanionItem();
		//
		//			if (equippedCompanion.getItem() instanceof ItemDeathSeal)
		//			{
		//				long ticks = ItemDeathSeal.getTicksUntilEnabled(equippedCompanion, this.world);
		//
		//				this.setBroken(ticks > 0);
		//			}
		//		}
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(IS_BROKEN, false);
	}

	public boolean isBroken()
	{
		return this.dataManager.get(IS_BROKEN);
	}

	public void setBroken(boolean broken)
	{
		this.dataManager.set(IS_BROKEN, broken);
	}
}
