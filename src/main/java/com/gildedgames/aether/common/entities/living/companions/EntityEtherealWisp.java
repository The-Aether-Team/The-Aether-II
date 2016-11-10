package com.gildedgames.aether.common.entities.living.companions;

import net.minecraft.block.Block;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityEtherealWisp extends EntityBasicCompanion
{

	public EntityEtherealWisp(World worldIn)
	{
		super(worldIn);

		this.setSize(0.75f, 2.0f);
		this.setPotion(MobEffects.INVISIBILITY, 0);

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
