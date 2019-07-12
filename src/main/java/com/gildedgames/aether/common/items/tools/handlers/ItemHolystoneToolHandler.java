package com.gildedgames.aether.common.items.tools.handlers;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.items.weapons.swords.ItemHolystoneSword;
import net.minecraft.block.BlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemHolystoneToolHandler implements IToolEventHandler
{
	@Override
	public void onHarvestBlock(ItemStack stack, World world, BlockState state, BlockPos pos, PlayerEntity entity, List<ItemStack> drops)
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
	public boolean onRightClickBlock(World world, BlockPos pos, PlayerEntity player, Hand hand, Direction facing)
	{
		return false;
	}

	@Override
	public void onRightClickItem(PlayerEntity player, Hand hand)
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
	public void onEntityHit(ItemStack stack, Entity target, LivingEntity attacker)
	{
		ItemHolystoneSword.trySpawnAmbrosium(stack, target, attacker);
	}

	@Override
	public float getBreakSpeed(ItemStack stack, World world, BlockState state, BlockPos pos, PlayerEntity player, float original)
	{
		return original;
	}
}
