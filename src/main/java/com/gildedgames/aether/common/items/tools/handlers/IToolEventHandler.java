package com.gildedgames.aether.common.items.tools.handlers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public interface IToolEventHandler
{
	void onHarvestBlock(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityPlayer entity, List<ItemStack> drops);

	void onRightClickBlock(ItemStack stack, World world, BlockPos pos, EntityPlayer player, EnumFacing facing);

	void onRightClickItem(ItemStack stack, World world, EntityPlayer player);

	void addInformation(ItemStack stack, List<String> tooltip);

	void onEntityHit(ItemStack stack, Entity target, EntityLivingBase attacker);

	float getBreakSpeed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityPlayer player, float original);
}
