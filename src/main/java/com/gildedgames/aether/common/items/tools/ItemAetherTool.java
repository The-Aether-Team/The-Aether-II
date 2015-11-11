package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.AetherCreativeTabs;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemAetherTool extends ItemTool
{
	protected final EnumToolType toolType;

	protected final String name;

	public ItemAetherTool(ToolMaterial material, String name, EnumToolType toolType)
	{
		super(toolType.getBaseDamage(), material, toolType.getEffectiveBlocks());

		this.toolType = toolType;
		this.name = name;

		this.setCreativeTab(AetherCreativeTabs.tabTools);
	}

	protected boolean isAbilityPassive()
	{
		return true;
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass)
	{
		int level = super.getHarvestLevel(stack, toolClass);

		if (level == -1 && toolClass != null && toolClass.equals(this.toolType.getToolClass()))
		{
			return this.toolMaterial.getHarvestLevel();
		}
		else
		{
			return level;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(String.format("%s: %s", EnumChatFormatting.BLUE + I18n.format("item.tooltip.ability"),
				EnumChatFormatting.WHITE + I18n.format("item.tool." + this.name + ".ability.desc")));

		if (!this.isAbilityPassive())
		{
			tooltip.add(String.format("%s: %s", EnumChatFormatting.DARK_AQUA + I18n.format("item.tooltip.use"),
					EnumChatFormatting.WHITE + I18n.format("item.tool." + this.name + ".use.desc")));
		}
	}
}
