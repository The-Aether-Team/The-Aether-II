package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.blocks.util.BlockRotatable;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockHolystonePillar extends BlockRotatable
{

	public BlockHolystonePillar()
	{
		super(Material.ROCK);

		this.setHardness(2.0F);

		this.setHarvestLevel("pickaxe", 0);
	}

	@Override
	public String getUnlocalizedName()
	{
		return "tile.aether.holystone_brick.normal";
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		tooltip.add(TextFormatting.GRAY + "" + I18n.format("tile.aether.holystone_brick.pillar.name"));
	}

}
