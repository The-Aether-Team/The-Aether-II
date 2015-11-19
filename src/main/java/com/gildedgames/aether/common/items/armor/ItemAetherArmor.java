package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.util.PlayerUtil;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemAetherArmor extends ItemArmor
{
	private final String name;
	private ItemAetherArmorAbility armorAbility;

	public ItemAetherArmor(ArmorMaterial material, String name, int armorType)
	{
		super(material, 0, armorType);

		this.name = name;

		this.setCreativeTab(AetherCreativeTabs.tabArmor);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return AetherCore.getResourcePath("textures/armor/" + this.name + "_layer_" + (slot == 2 ? 2 : 1) + ".png");
	}

	@Override
	public boolean getIsRepairable(ItemStack target, ItemStack stack)
	{
		return false;
	}

	protected boolean isAbilityPassive()
	{
		return true;
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		if (!world.isRemote)
		{
			if (PlayerUtil.isWearingFullSet(player, ItemPhoenixArmor.class))
			{
				armorAbility = new ItemAetherArmorAbility(player, 1);
			}
			if (PlayerUtil.isWearingFullSet(player, ItemNeptuneArmor.class))
			{
				armorAbility = new ItemAetherArmorAbility(player, 2);
			}
			if (PlayerUtil.isWearingFullSet(player, ItemObsidianArmor.class))
			{
				armorAbility = new ItemAetherArmorAbility(player, 3);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(EnumChatFormatting.BLUE + I18n.format("item.tooltip.ability") + ": " +
				EnumChatFormatting.WHITE + I18n.format("item.armor." + this.name + ".ability.desc"));

		if (!this.isAbilityPassive())
		{
			tooltip.add(EnumChatFormatting.DARK_AQUA + I18n.format("item.tooltip.use") + ": " +
					EnumChatFormatting.WHITE + I18n.format("item.armor." + this.name + ".use.desc"));
		}
	}
}
