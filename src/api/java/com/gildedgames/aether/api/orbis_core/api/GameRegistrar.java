package com.gildedgames.aether.api.orbis_core.api;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

public class GameRegistrar
{
	private final IForgeRegistry<Block> blockRegistry;

	public GameRegistrar()
	{
		this.blockRegistry = GameRegistry.findRegistry(Block.class);
	}

	public ResourceLocation getIdentifierFor(final Block block)
	{
		return this.blockRegistry.getKey(block);
	}

	public Block findBlock(final ResourceLocation identifier)
	{
		final String modId = identifier.getResourceDomain();
		final String name = identifier.getResourcePath();

		if (modId.equals("minecraft") || modId.equals("") || modId == null)
		{
			return Block.getBlockFromName(name);
		}

		return this.blockRegistry.getValue(identifier);
	}

	public int getBlockId(final Block block)
	{
		return Block.getIdFromBlock(block);
	}

	public IBlockState getStateFromMeta(final Block block, final int meta)
	{
		return block.getStateFromMeta(meta);
	}

	public IBlockState getStateFromId(final int id)
	{
		return Block.getStateById(id);
	}

	public int getStateId(final IBlockState blockState)
	{
		return Block.getStateId(blockState);
	}

}