package com.gildedgames.aether.common.items.tools.handlers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemArkeniumToolHandler implements IToolEventHandler
{
	@Override
	public void onHarvestBlock(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityPlayer entity, List<ItemStack> drops)
	{

	}

	@Override
	public boolean onRightClickBlock(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing facing)
	{
		return false;
	}

	@Override
	public void onRightClickItem(EntityPlayer player, EnumHand hand)
	{

	}

	@Override
	public void addInformation(ItemStack stack, List<String> tooltip)
	{
		tooltip.add(1, String.format("%s: %s",
				TextFormatting.BLUE + I18n.format("item.aether.tooltip.ability"),
				TextFormatting.WHITE + I18n.format("item.aether.tool.arkenium.ability.desc")));
	}

	@Override
	public void onEntityHit(ItemStack stack, Entity target, EntityLivingBase attacker)
	{

	}

	@Override
	public float getBreakSpeed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityPlayer player, float original)
	{
		return original;
	}
}
