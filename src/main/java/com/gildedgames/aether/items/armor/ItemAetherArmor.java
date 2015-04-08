package com.gildedgames.aether.items.armor;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherCreativeTabs;

public class ItemAetherArmor extends ItemArmor
{

	public ItemAetherArmor(EnumAetherArmorMaterial material, int renderIndex, int armorType) 
	{
		super(material.getArmorMaterial(), renderIndex, armorType);
		
		this.setCreativeTab(AetherCreativeTabs.tabArmor);
	}
	
	public String getArmorTexture(ItemStack stack)
    {
        if (stack.getItem() == Aether.getItems().zanite_helmet || stack.getItem() == Aether.getItems().zanite_chestplate || stack.getItem() == Aether.getItems().zanite_boots)
        {
        	 return "aether:textures/armor/zanite_1.png";
        }
        if (stack.getItem() == Aether.getItems().zanite_leggings)
        {
        	 return "aether:textures/armor/zanite_2.png";
        }
        if (stack.getItem() == Aether.getItems().gravitite_helmet || stack.getItem() == Aether.getItems().gravitite_chestplate || stack.getItem() == Aether.getItems().gravitite_boots)
        {
        	 return "aether:textures/armor/gravitite_1.png";
        }
        if (stack.getItem() == Aether.getItems().gravitite_leggings)
        {
        	 return "aether:textures/armor/gravitite_2.png";
        }
		return "aether:textures/armor/zanite_1.png";
    }

}
