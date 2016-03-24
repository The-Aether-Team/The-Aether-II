package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.util.PlayerUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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

	public ItemAetherArmor(ArmorMaterial material, String name, int armorType)
	{
		super(material, 0, armorType);

		this.name = name;

		this.setCreativeTab(AetherCreativeTabs.tabArmor);
	}

	@Override
	public boolean getIsRepairable(ItemStack target, ItemStack stack)
	{
		return false;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
	{
		ItemAetherArmor armor = (ItemAetherArmor) stack.getItem();

		// Only the helmet should tick. This prevents the calculations being done 4x times.
		if (armor.armorType == 0)
		{
			Class<? extends Item> fullSet = PlayerUtil.findArmorSet(player);

			if (fullSet != null && fullSet.isAssignableFrom(this.getClass()))
			{
				this.applyFullSetBonus(world, player);
			}
		}
	}

	protected void applyFullSetBonus(World world, EntityPlayer player)
	{
	}

	protected boolean isAbilityPassive()
	{
		return true;
	}

	public float getExtraDamageReduction(ItemStack stack)
	{
		return 0.0f;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return AetherCore.getResourcePath("textures/armor/" + this.name + "_layer_" + (slot == 2 ? 2 : 1) + ".png");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add(EnumChatFormatting.BLUE + I18n.format("item.aether.armor." + this.name + ".ability.desc"));

		if (!this.isAbilityPassive())
		{
			tooltip.add(EnumChatFormatting.DARK_AQUA + I18n.format("item.aether.tooltip.use") + ": " +
					EnumChatFormatting.WHITE + I18n.format("item.aether.armor." + this.name + ".use.desc"));
		}
	}
}
