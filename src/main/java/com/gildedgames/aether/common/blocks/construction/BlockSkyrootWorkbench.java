package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.Aether;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockSkyrootWorkbench extends Block
{
	public BlockSkyrootWorkbench()
	{
		super(Material.wood);

		this.setHardness(2.5f);

		this.setStepSound(Block.soundTypeWood);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			player.openGui(Aether.INSTANCE, AetherGuiHandler.SKYROOT_WORKBENCH_ID, world, pos.getX(), pos.getY(), pos.getZ());
		}

		return true;
	}
}
