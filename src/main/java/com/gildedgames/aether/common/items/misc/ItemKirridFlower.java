package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.natural.plants.BlockKirridGrass;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import javax.annotation.Nonnull;

public class ItemKirridFlower extends Item
{
	private final Block PLANT;

	public ItemKirridFlower()
	{
		this.PLANT = BlocksAether.kirrid_grass;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack itemstack = player.getHeldItem(hand);
		IBlockState state = worldIn.getBlockState(pos);

		if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && state.getBlock() instanceof BlockAetherGrass && worldIn.isAirBlock(pos.up()))
		{
			worldIn.setBlockState(pos.up(), PLANT.getDefaultState().withProperty(BlocksAether.kirrid_grass.PROPERTY_HARVESTABLE, false).withProperty(BlocksAether.kirrid_grass.PROPERTY_VARIANT, BlocksAether.kirrid_grass.SPROUT));
			itemstack.shrink(1);
			return EnumActionResult.SUCCESS;
		}
		else
		{
			return EnumActionResult.FAIL;
		}
	}

	//TODO: Add implementation for Kirrid mating.
}
