package com.gildedgames.aether.common.entities.companions;

import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.capabilities.player.modules.PlayerCompanionModule;
import com.gildedgames.aether.common.items.companions.ItemDeathSeal;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityNexSpirit extends EntityCompanion
{
	private static final DataParameter<Boolean> IS_BROKEN = new DataParameter<>(21, DataSerializers.BOOLEAN);

	public EntityNexSpirit(World worldIn)
	{
		super(worldIn);

		this.setSize(0.6f, 1.85f);
		this.stepHeight = 1.0F;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.getOwner() != null)
		{
			PlayerAetherImpl aePlayer = PlayerAetherImpl.getPlayer(this.getOwner());

			PlayerCompanionModule companionManager = aePlayer.getCompanionModule();

			ItemStack equippedCompanion = companionManager.getEquippedCompanionItem();

			if (equippedCompanion != null && equippedCompanion.getItem() instanceof ItemDeathSeal)
			{
				long ticks = ItemDeathSeal.getTicksUntilEnabled(equippedCompanion, this.worldObj);

				this.setBroken(ticks > 0);
			}
		}
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(IS_BROKEN, false);
	}

	@Override
	public void tickEffects(PlayerAetherImpl aePlayer)
	{

	}

	@Override
	public void addEffects(PlayerAetherImpl aePlayer)
	{

	}

	@Override
	public void removeEffects(PlayerAetherImpl aePlayer)
	{

	}

	public void setBroken(boolean broken)
	{
		this.dataManager.set(IS_BROKEN, broken);
	}

	public boolean isBroken()
	{
		return this.dataManager.get(IS_BROKEN);
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{

	}

	@Override
	public boolean canTriggerWalking()
	{
		return false;
	}

}
