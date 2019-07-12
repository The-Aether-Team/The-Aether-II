package com.gildedgames.aether.common.items.tools.handlers;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public interface IToolEventHandler
{
	void onHarvestBlock(ItemStack stack, World world, BlockState state, BlockPos pos, PlayerEntity entity, List<ItemStack> drops);

	boolean onRightClickBlock(World world, BlockPos pos, PlayerEntity player, Hand hand, Direction facing);

	void onRightClickItem(PlayerEntity player, Hand hand);

	void addInformation(ItemStack stack, List<String> tooltip);

	void onEntityHit(ItemStack stack, Entity target, LivingEntity attacker);

	float getBreakSpeed(ItemStack stack, World world, BlockState state, BlockPos pos, PlayerEntity player, float original);
}
