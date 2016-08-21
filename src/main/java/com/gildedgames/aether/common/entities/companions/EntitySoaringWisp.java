package com.gildedgames.aether.common.entities.companions;

import net.minecraft.block.Block;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntitySoaringWisp extends EntityBasicCompanion
{

	public EntitySoaringWisp(World worldIn)
	{
		super(worldIn);

		this.setSize(0.75f, 2.0f);
		this.setPotion(MobEffects.SPEED, 0);

		this.stepHeight = 1.0F;
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
