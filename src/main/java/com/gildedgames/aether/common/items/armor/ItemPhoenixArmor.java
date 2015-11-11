package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.util.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemPhoenixArmor extends ItemAetherArmor
{
	public ItemPhoenixArmor(ArmorMaterial material, int armorType)
	{
		super(material, "phoenix", armorType);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		if (!world.isRemote)
		{
			if (PlayerUtil.isWearingFullSet(player, ItemPhoenixArmor.class))
			{
				PotionEffect flameResistance = new PotionEffect(Potion.fireResistance.getId(), 20, 3, false, false);

				player.addPotionEffect(flameResistance);
				player.extinguish();
			}
		}
	}
}
