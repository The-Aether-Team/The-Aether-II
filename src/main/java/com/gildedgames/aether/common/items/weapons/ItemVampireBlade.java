package com.gildedgames.aether.common.items.weapons;

import com.gildedgames.aether.common.AetherMaterials;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemVampireBlade extends ItemSword
{
	public ItemVampireBlade()
	{
		super(AetherMaterials.LEGENDARY_TOOL);
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
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(String.format("%s: %s", EnumChatFormatting.BLUE + I18n.format("item.tooltip.ability"),
				EnumChatFormatting.WHITE + I18n.format(this.getUnlocalizedName() + ".ability.desc")));
	}
}
