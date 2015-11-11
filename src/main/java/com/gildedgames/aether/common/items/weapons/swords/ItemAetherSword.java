package com.gildedgames.aether.common.items.weapons.swords;

import com.gildedgames.aether.common.AetherCreativeTabs;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemAetherSword extends ItemSword
{
	public ItemAetherSword(ToolMaterial material)
	{
		super(material);

		this.setCreativeTab(AetherCreativeTabs.tabWeapons);
	}

	protected boolean isAbilityPassive()
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(String.format("%s: %s", EnumChatFormatting.BLUE + I18n.format("item.tooltip.ability"),
				EnumChatFormatting.WHITE + I18n.format(this.getUnlocalizedName() + ".ability.desc")));

		if (!this.isAbilityPassive())
		{
			tooltip.add(String.format("%s: %s", EnumChatFormatting.DARK_AQUA + I18n.format("item.tooltip.use"),
					EnumChatFormatting.WHITE + I18n.format(this.getUnlocalizedName() + ".use.desc")));
		}
	}
}
