package com.gildedgames.aether.common.items.tools.handlers;

import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import net.minecraft.block.IGrowable;
import net.minecraft.block.BlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ItemSkyrootToolHandler implements IToolEventHandler
{
	@Override
	public void onHarvestBlock(final ItemStack stack, final World world, final BlockState state, final BlockPos pos, final PlayerEntity entity,
			final List<ItemStack> drops)
	{
		if (!world.isRemote)
		{
			if (state.getBlock() instanceof IGrowable)
			{
				return;
			}

			final IPlacementFlagCapability data = world.getChunk(pos).getCapability(CapabilitiesAether.CHUNK_PLACEMENT_FLAG, Direction.UP);

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
	public boolean onRightClickBlock(final World world, final BlockPos pos, final PlayerEntity player, final Hand hand, final Direction facing)
	{
		return false;
	}

	@Override
	public void onRightClickItem(final PlayerEntity player, final Hand hand)
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
	public void onEntityHit(final ItemStack stack, final Entity target, final LivingEntity attacker)
	{

	}

	@Override
	public float getBreakSpeed(final ItemStack stack, final World world, final BlockState state, final BlockPos pos, final PlayerEntity player,
			final float original)
	{
		return original;
	}
}
