package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.world.chunk.hooks.capabilities.ChunkAttachmentCapability;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockCrudeScatterglass extends BlockBreakable
{

	public BlockCrudeScatterglass()
	{
		super(Material.ROCK, false);

		this.setHardness(1f);
		this.setResistance(2000f);

		this.setLightOpacity(3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(BlocksAether.crude_scatterglass);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
	{
		IPlacementFlagCapability data = ChunkAttachmentCapability.get(world).getAttachment(new ChunkPos(pos), AetherCapabilities.CHUNK_PLACEMENT_FLAG);

		boolean wasPlaced = data.isMarked(pos);

		if (!wasPlaced)
		{
			Block.spawnAsEntity(world, pos, new ItemStack(ItemsAether.scatterglass_shard));
		}

		super.harvestBlock(world, player, pos, state, te, stack);
	}

}
