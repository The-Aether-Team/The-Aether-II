package com.gildedgames.aether.common.blocks.util;

import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;

public class BlockCustomPane extends BlockPane
{

	private String[] addInformation;

	public BlockCustomPane(final IBlockState state, boolean canDrop, String... addInformation)
	{
		super(state.getMaterial(), canDrop);

		this.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS);

		final Block block = state.getBlock();

		BlocksAether.applyPostRegistration(() -> BlockCustomPane.this.setHarvestLevel(block.getHarvestTool(state), block.getHarvestLevel(state)));

		this.setLightOpacity(0);
		this.setLightLevel(block.getLightValue(state));

		this.setSoundType(block.getSoundType());

		float blockHardness = ObfuscationReflectionHelper.getPrivateValue(Block.class, block, ReflectionAether.BLOCK_HARDNESS.getMappings());

		this.setHardness(blockHardness);

		this.addInformation = addInformation;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		Collections.addAll(tooltip, this.addInformation);
	}

}
