package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.registry.minecraft.CreativeTabsAether;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemAetherTool extends ItemTool
{
	protected final EnumToolType toolType;

	protected final String name;

	public ItemAetherTool(Item.ToolMaterial material, String name, EnumToolType toolType, float attackDamage, float attackSpeed)
	{
		super(attackDamage, attackSpeed, material, toolType.getEffectiveBlocks());

		this.toolType = toolType;
		this.name = name;

		this.setCreativeTab(CreativeTabsAether.TOOLS);
	}

	public EnumToolType getToolType()
	{
		return this.toolType;
	}

	protected boolean isAbilityPassive()
	{
		return true;
	}

	protected boolean hasAbility()
	{
		return true;
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass)
	{
		int level = super.getHarvestLevel(stack, toolClass);

		if (level == -1 && toolClass.equals(this.toolType.getToolClass()))
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
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		if (this.hasAbility())
		{
			tooltip.add(String.format("%s: %s",
					TextFormatting.BLUE + I18n.format("item.aether.tooltip.ability"),
					TextFormatting.WHITE + I18n.format("item.aether.tool." + this.name + ".ability.desc")));

			if (!this.isAbilityPassive())
			{
				tooltip.add(String.format("%s: %s",
						TextFormatting.DARK_AQUA + I18n.format("item.aether.tooltip.use"),
						TextFormatting.WHITE + I18n.format("item.aether.tool." + this.name + ".use.desc")));
			}
		}
	}
}
