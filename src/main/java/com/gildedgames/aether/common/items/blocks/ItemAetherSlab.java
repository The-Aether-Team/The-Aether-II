package com.gildedgames.aether.common.items.blocks;

import com.gildedgames.aether.common.blocks.util.BlockCustomSlab;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAetherSlab extends ItemBlock
{
	public ItemAetherSlab(Block block)
	{
		super(block);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing,
			float hitX, float hitY, float hitZ)
	{
		if (stack.stackSize != 0 && player.canPlayerEdit(pos.offset(facing), facing, stack))
		{
			IBlockState state = worldIn.getBlockState(pos);

			if (state.getBlock() == this.block)
			{
				BlockCustomSlab.SlabState slabState = state.getValue(BlockCustomSlab.PROPERTY_SLAB_STATE);

				if ((facing == EnumFacing.UP && slabState == BlockCustomSlab.SlabState.BOTTOM_HALF
						|| facing == EnumFacing.DOWN && slabState == BlockCustomSlab.SlabState.TOP_HALF))
				{
					IBlockState placeState = this.block.getDefaultState().withProperty(BlockCustomSlab.PROPERTY_SLAB_STATE, BlockCustomSlab.SlabState.FULL_BLOCK);

					AxisAlignedBB bounds = placeState.getCollisionBoundingBox(worldIn, pos);

					if (bounds != Block.NULL_AABB && worldIn.checkNoEntityCollision(bounds.offset(pos))
							&& worldIn.setBlockState(pos, placeState, 11))
					{
						SoundType soundtype = this.block.getSoundType();
						worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS,
								(soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

						--stack.stackSize;
					}

					return EnumActionResult.SUCCESS;
				}
			}

			return this.tryPlace(player, stack, worldIn, pos.offset(facing)) ? EnumActionResult.SUCCESS :
					super.onItemUse(stack, player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
		}
		else
		{
			return EnumActionResult.FAIL;
		}
	}

	@SideOnly(Side.CLIENT)
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack)
	{
		IBlockState state = worldIn.getBlockState(pos);

		if (state.getBlock() == this.block && state.getValue(BlockCustomSlab.PROPERTY_SLAB_STATE) != BlockCustomSlab.SlabState.FULL_BLOCK)
		{
			boolean flag = state.getValue(BlockCustomSlab.PROPERTY_SLAB_STATE) == BlockCustomSlab.SlabState.TOP_HALF;

			if ((side == EnumFacing.UP && !flag || side == EnumFacing.DOWN && flag))
			{
				return true;
			}
		}

		return state.getBlock() == this.block || super.canPlaceBlockOnSide(worldIn, pos, side, player, stack);
	}

	private boolean tryPlace(EntityPlayer player, ItemStack stack, World worldIn, BlockPos pos)
	{
		IBlockState iblockstate = worldIn.getBlockState(pos);

		if (iblockstate.getBlock() == this.block)
		{
			IBlockState state = this.block.getDefaultState();

			AxisAlignedBB bounds = state.getCollisionBoundingBox(worldIn, pos);

			if (bounds != Block.NULL_AABB && worldIn.checkNoEntityCollision(bounds.offset(pos)) && worldIn.setBlockState(pos, state, 11))
			{
				SoundType soundtype = this.block.getSoundType();
				worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS,
						(soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

				--stack.stackSize;
			}

			return true;
		}

		return false;
	}
}
