package com.gildedgames.aether.common.items.tools.handlers;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.items.weapons.swords.ItemHolystoneSword;
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

public class ItemHolystoneToolHandler implements IToolEventHandler
{
	@Override
	public void onHarvestBlock(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityPlayer entity, List<ItemStack> drops)
	{
		if (!world.isRemote && world.rand.nextInt(100) <= 5)
		{
			if (state.getBlockHardness(world, pos) > 0.0f)
			{
				drops.add(new ItemStack(ItemsAether.ambrosium_shard));
			}
		}
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
				TextFormatting.WHITE + I18n.format("item.aether.tool.holystone.ability.desc")));
	}

	@Override
	public void onEntityHit(ItemStack stack, Entity target, EntityLivingBase attacker)
	{
		ItemHolystoneSword.trySpawnAmbrosium(stack, target, attacker);
	}

	@Override
	public float getBreakSpeed(ItemStack stack, IBlockState state, float original)
	{
		return original;
	}
}
