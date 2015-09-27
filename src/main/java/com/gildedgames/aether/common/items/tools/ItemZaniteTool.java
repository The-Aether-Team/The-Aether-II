package com.gildedgames.aether.common.items.tools;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemZaniteTool extends ItemAetherTool
{
	public ItemZaniteTool(EnumToolType toolType)
	{
		super(ToolMaterial.IRON, toolType);

		this.setHarvestLevel(toolType.getToolClass(), 1);
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, IBlockState state)
	{
		for (String type : this.getToolClasses(stack))
		{
			if (state.getBlock().isToolEffective(type, state))
			{
				return this.efficiencyOnProperMaterial * (2.0F * stack.getItemDamage() / stack.getItem().getMaxDamage() + 0.5F);
			}
		}

		return super.getDigSpeed(stack, state);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(EnumChatFormatting.BLUE + "Ability: " + EnumChatFormatting.WHITE + "Mines faster as");
		tooltip.add(EnumChatFormatting.WHITE + "durability decreases");
	}
}
