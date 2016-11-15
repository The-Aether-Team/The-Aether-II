package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.common.blocks.util.ISkyrootMinable;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.world.chunk.hooks.capabilities.ChunkAttachmentCapability;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.List;

public class BlockSkyrootLog extends BlockAetherLog implements ISkyrootMinable
{

	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
	{
		IPlacementFlagCapability data = ChunkAttachmentCapability.get(world).getAttachment(new ChunkPos(pos), AetherCapabilities.CHUNK_PLACEMENT_FLAG);

		boolean wasPlaced = data.isMarked(pos);

		if (!wasPlaced && world.rand.nextInt(5) == 0)
		{
			Block.spawnAsEntity(world, pos, new ItemStack(ItemsAether.skyroot_fragment, stack != null && stack.getItem() == ItemsAether.skyroot_axe ? 2 : 1));
		}

		super.harvestBlock(world, player, pos, state, te, stack);
	}

	@Override
	public boolean canBlockDropDoubles(EntityLivingBase player, ItemStack stack, IBlockState state)
	{
		return true;
	}

	@Override
	public Collection<ItemStack> getAdditionalDrops(World world, BlockPos pos, IBlockState state, EntityLivingBase living)
	{
		List<ItemStack> drops = Lists.newArrayList();

		drops.add(new ItemStack(state.getBlock().getItemDropped(state, living.getRNG(), 0)));

		return drops;
	}

}
