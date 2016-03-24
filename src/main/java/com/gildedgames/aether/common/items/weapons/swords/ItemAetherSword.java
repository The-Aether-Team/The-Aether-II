package com.gildedgames.aether.common.items.weapons.swords;

import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.items.ItemAbilityType;
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
	private final ItemAbilityType abilityType;

	public ItemAetherSword(ToolMaterial material, ItemAbilityType abilityType)
	{
		super(material);

		this.abilityType = abilityType;

		this.setCreativeTab(AetherCreativeTabs.tabWeapons);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		if (this.abilityType != ItemAbilityType.NONE)
		{
			tooltip.add(String.format("%s: %s",
					EnumChatFormatting.BLUE + I18n.format("item.aether.tooltip.ability"),
					EnumChatFormatting.WHITE + I18n.format(this.getUnlocalizedName() + ".ability.desc")));

			if (!this.abilityType.isPassive())
			{
				tooltip.add(String.format("%s: %s",
						EnumChatFormatting.DARK_AQUA + I18n.format("item.aether.tooltip.use"),
						EnumChatFormatting.WHITE + I18n.format(this.getUnlocalizedName() + ".use.desc")));
			}
		}
	}
}
