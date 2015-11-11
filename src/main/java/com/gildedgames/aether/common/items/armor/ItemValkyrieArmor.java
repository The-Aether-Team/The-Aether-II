package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.util.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemValkyrieArmor extends ItemAetherArmor
{
	public ItemValkyrieArmor(ArmorMaterial material, int armorType)
	{
		super(material, "valkyrie", armorType);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		if (!world.isRemote)
		{
			if (PlayerUtil.isWearingFullSet(player, ItemValkyrieArmor.class))
			{
				// Add wearing full set effect here
			}
		}
	}
}
