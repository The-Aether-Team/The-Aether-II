package com.gildedgames.aether.common.items.tools.handlers;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.common.blocks.util.ISkyrootMinable;
import com.gildedgames.aether.common.world.chunk.hooks.capabilities.ChunkAttachmentCapability;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;

public class ItemSkyrootToolHandler implements IToolEventHandler
{
	@Override
	public void onHarvestBlock(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityPlayer entity, List<ItemStack> drops)
	{
		if (!world.isRemote)
		{
			IPlacementFlagCapability data = ChunkAttachmentCapability.get(world).getAttachment(new ChunkPos(pos), AetherCapabilities.CHUNK_PLACEMENT_FLAG);

			if (data.isMarked(pos))
			{
				return;
			}

			if (state.getBlock().canHarvestBlock(world, pos, entity))
			{
				int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);

				if (state.getBlock() instanceof ISkyrootMinable)
				{
					ISkyrootMinable doubleBlock = (ISkyrootMinable) state.getBlock();

					if (doubleBlock.canBlockDropDoubles(entity, stack, state))
					{
						drops.addAll(doubleBlock.getAdditionalDrops(world, pos, state, entity));
					}
				}
				else
				{
					for (int i = 0; i < fortune + 1; i++)
					{
						drops.addAll(drops.stream().map(ItemStack::copyItemStack).collect(Collectors.toList()));
					}
				}
			}
		}
	}

	@Override
	public void onRightClickBlock(ItemStack stack, World world, BlockPos pos, EntityPlayer player, EnumFacing facing)
	{

	}

	@Override
	public void onRightClickItem(ItemStack stack, World world, EntityPlayer player)
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
	public float getBreakSpeed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityPlayer player, float original)
	{
		return original;
	}
}
