package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.blocks.util.BlockRotatable;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class BlockSkyrootBeam extends BlockRotatable
{

	public BlockSkyrootBeam()
	{
		super(Material.WOOD);

		this.setHardness(2.0F);

		this.setHarvestLevel("axe", 0);
	}

	@Override
	public String getUnlocalizedName()
	{
		return "tile.aether.skyroot_planks.normal";
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		tooltip.add(TextFormatting.GRAY + "" + I18n.format("tile.aether.skyroot_planks.beam.name"));
	}

}
