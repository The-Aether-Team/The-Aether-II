package com.gildedgames.aether.common.items.tools.handlers;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerBlockLevitateModule;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemGravititeToolHandler implements IToolEventHandler
{
	@Override
	public void onHarvestBlock(ItemStack stack, World world, BlockState state, BlockPos pos, PlayerEntity entity, List<ItemStack> drops)
	{

	}

	@Override
	public boolean onRightClickBlock(World world, BlockPos pos, PlayerEntity player, Hand hand, Direction facing)
	{
		if (hand != Hand.MAIN_HAND)
		{
			return false;
		}

		PlayerAether aePlayer = PlayerAether.getPlayer(player);

		if (!aePlayer.getEntity().capabilities.allowEdit)
		{
			return false;
		}

		ItemStack stack = player.getHeldItem(hand);

		if (aePlayer.getModule(PlayerBlockLevitateModule.class).getHeldBlock() == null && player.isSneaking())
		{
			BlockState state = world.getBlockState(pos);

			if (state.getBlock().hasTileEntity(state))
			{
				return false;
			}

			if (!state.isFullBlock() || state.getBlockHardness(world, pos) < 0.0f)
			{
				return false;
			}

			if (!world.isRemote)
			{
				if (aePlayer.getModule(PlayerBlockLevitateModule.class).pickupBlock(pos, world))
				{
					stack.damageItem(2, player);
				}
			}
			else
			{
				for (int i = 0; i < 15; i++)
				{
					world.spawnParticle(ParticleTypes.BLOCK_DUST,
							pos.getX() + (world.rand.nextDouble() * 1.2D),
							pos.getY() + (world.rand.nextDouble()),
							pos.getZ() + (world.rand.nextDouble() * 1.2D), 0.0D, 0.0D, 0.0D,
							Block.getStateId(state));
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public void onRightClickItem(PlayerEntity player, Hand hand)
	{
		if (hand == Hand.MAIN_HAND && !player.world.isRemote)
		{
			PlayerAether aePlayer = PlayerAether.getPlayer(player);
			PlayerBlockLevitateModule blockLevitateModule = aePlayer.getModule(PlayerBlockLevitateModule.class);

			if (blockLevitateModule.getHeldBlock() != null && blockLevitateModule.getHeldBlock().ticksExisted > 1)
			{
				blockLevitateModule.dropHeldBlock();
			}
		}
	}

	@Override
	public void addInformation(ItemStack stack, List<String> tooltip)
	{
		tooltip.add(1, String.format("%s: %s",
				TextFormatting.DARK_AQUA + I18n.format("item.aether.tooltip.use"),
				TextFormatting.WHITE + I18n.format("item.aether.tool.gravitite.use.desc")));

		tooltip.add(1, String.format("%s: %s",
				TextFormatting.BLUE + I18n.format("item.aether.tooltip.ability"),
				TextFormatting.WHITE + I18n.format("item.aether.tool.gravitite.ability.desc")));
	}

	@Override
	public void onEntityHit(ItemStack stack, Entity target, LivingEntity attacker)
	{

	}

	@Override
	public float getBreakSpeed(ItemStack stack, World world, BlockState state, BlockPos pos, PlayerEntity player, float original)
	{
		return original;
	}
}
