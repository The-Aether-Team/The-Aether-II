package com.gildedgames.aether.common.items.tools.handlers;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.common.world.chunk.capabilities.ChunkAttachment;
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
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ItemSkyrootToolHandler implements IToolEventHandler
{
	@Override
	public void onHarvestBlock(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityPlayer entity, List<ItemStack> drops)
	{
		if (!world.isRemote)
		{
			if (state.getBlock() instanceof IGrowable)
			{
				return;
			}

			IPlacementFlagCapability data = ChunkAttachment.get(world).getAttachment(new ChunkPos(pos), AetherCapabilities.CHUNK_PLACEMENT_FLAG);

			if (data.isModified(pos))
			{
				return;
			}

			if (state.getBlock().canHarvestBlock(world, pos, entity))
			{
				int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);

				List<ItemStack> copy = new ArrayList<>(drops);

				for (int i = 0; i < fortune + 1; i++)
				{
					drops.addAll(copy);
				}
			}
		}
	}

	@Override
	public void onRightClickBlock(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing facing)
	{

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
				TextFormatting.WHITE + I18n.format("item.aether.tool.skyroot.ability.desc")));
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
