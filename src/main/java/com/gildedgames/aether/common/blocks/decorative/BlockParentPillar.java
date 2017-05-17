package com.gildedgames.aether.common.blocks.decorative;

import com.gildedgames.aether.common.blocks.util.BlockRotatable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class BlockParentPillar extends BlockRotatable
{
	private final Block parentBlock;

	public BlockParentPillar(Material material, Block parentBlock)
	{
		super(material);

		this.parentBlock = parentBlock;
	}

	@Override
	public BlockParentPillar setSoundType(SoundType type)
	{
		super.setSoundType(type);

		return this;
	}

	@Override
	public String getUnlocalizedName()
	{
		return this.parentBlock.getUnlocalizedName();
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		tooltip.add(TextFormatting.GRAY + "Pillar");
	}
}
