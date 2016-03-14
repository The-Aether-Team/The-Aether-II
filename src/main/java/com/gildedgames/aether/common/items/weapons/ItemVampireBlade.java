package com.gildedgames.aether.common.items.weapons;

import com.gildedgames.aether.common.AetherMaterials;
import com.gildedgames.aether.common.items.ItemAbilityType;
import com.gildedgames.aether.common.items.weapons.swords.ItemAetherSword;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemVampireBlade extends ItemAetherSword
{
	public ItemVampireBlade()
	{
		super(AetherMaterials.LEGENDARY_TOOL, ItemAbilityType.PASSIVE);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		int healPlayer = (int)(Math.min(target.getHealth(), 20) / getDamageVsEntity());
		
		if (attacker instanceof EntityPlayer)
		{
			attacker.heal(Math.max(0, healPlayer));
		}

		return super.hitEntity(stack, target, attacker);
	}
}
