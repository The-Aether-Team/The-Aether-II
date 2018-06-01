package com.gildedgames.aether.common.items.tools.handlers;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ItemSkyrootToolHandler implements IToolEventHandler
{
	@Override
	public void onHarvestBlock(final ItemStack stack, final World world, final IBlockState state, final BlockPos pos, final EntityPlayer entity,
			final List<ItemStack> drops)
	{
		if (!world.isRemote)
		{
			if (state.getBlock() instanceof IGrowable)
			{
				return;
			}

			final IPlacementFlagCapability data = world.getChunkFromBlockCoords(pos).getCapability(AetherCapabilities.CHUNK_PLACEMENT_FLAG, EnumFacing.UP);

			if (data.isModified(pos))
			{
				return;
			}

			if (state.getBlock().canHarvestBlock(world, pos, entity))
			{
				final int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);

				final List<ItemStack> copy = new ArrayList<>(drops);

				for (int i = 0; i < fortune + 1; i++)
				{
					drops.addAll(copy);
				}
			}
		}
	}

	@Override
	public boolean onRightClickBlock(final World world, final BlockPos pos, final EntityPlayer player, final EnumHand hand, final EnumFacing facing)
	{
		return false;
	}

	@Override
	public void onRightClickItem(final EntityPlayer player, final EnumHand hand)
	{

	}

	@Override
	public void addInformation(final ItemStack stack, final List<String> tooltip)
	{
		tooltip.add(1, String.format("%s: %s",
				TextFormatting.BLUE + I18n.format("item.aether.tooltip.ability"),
				TextFormatting.WHITE + I18n.format("item.aether.tool.skyroot.ability.desc")));
	}

	@Override
	public void onEntityHit(final ItemStack stack, final Entity target, final EntityLivingBase attacker)
	{

	}

	@Override
	public float getBreakSpeed(final ItemStack stack, final World world, final IBlockState state, final BlockPos pos, final EntityPlayer player,
			final float original)
	{
		return original;
	}
}
