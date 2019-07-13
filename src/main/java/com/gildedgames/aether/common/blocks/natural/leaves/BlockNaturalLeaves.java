package com.gildedgames.aether.common.blocks.natural.leaves;

import com.gildedgames.aether.common.blocks.natural.wood.BlockAetherLog;
import com.gildedgames.aether.common.entities.animals.EntitySkyrootLizard;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class BlockNaturalLeaves extends BlockAetherLeaves
{
	public BlockNaturalLeaves(Properties properties)
	{
		super(properties);
	}

	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack)
	{
		super.harvestBlock(worldIn, player, pos, state, te, stack);

		if (!worldIn.isRemote && state.get(DECAYABLE) && worldIn.rand.nextInt(15) == 0)
		{
			EntitySkyrootLizard.Type type = EntitySkyrootLizard.Type.getFromWoodType(this.getWoodBlock());

			if (type == null)
			{
				return;
			}

			EntitySkyrootLizard lizard = new EntitySkyrootLizard(worldIn);
			lizard.setPosition(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f);

			this.applyLizardProperties(lizard);

			worldIn.spawnEntity(lizard);
		}
	}

	protected void applyLizardProperties(EntitySkyrootLizard lizard)
	{

	}

	public abstract BlockAetherLog getWoodBlock();
}
