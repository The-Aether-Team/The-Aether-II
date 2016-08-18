package com.gildedgames.aether.common.entities.companions;

import com.gildedgames.aether.common.player.PlayerAether;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityFleetingWisp extends EntityCompanion
{
	public EntityFleetingWisp(World worldIn)
	{
		super(worldIn);

		this.setSize(0.75f, 2.0f);
		this.stepHeight = 1.0F;
	}

	@Override
	public void tickEffects(PlayerAether aePlayer)
	{

	}

	@Override
	public void addEffects(PlayerAether aePlayer)
	{

	}

	@Override
	public void removeEffects(PlayerAether aePlayer)
	{

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
