package com.gildedgames.aether.common.items.weapons;

import com.gildedgames.aether.common.AetherMaterials;
import com.gildedgames.aether.common.entities.living.EntityPhyg;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class ItemPigSlayer extends ItemSword
{
	public ItemPigSlayer()
	{
		super(AetherMaterials.LEGENDARY_TOOL);

		this.setMaxDamage(200);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (target instanceof EntityPig || target instanceof EntityPhyg || target instanceof EntityPigZombie)
		{
			if (target.getHealth() > 0)
			{
				if (attacker instanceof EntityPlayer)
				{
					target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), Integer.MAX_VALUE);
				}
				else
				{
					target.attackEntityFrom(DamageSource.causeMobDamage(attacker), Integer.MAX_VALUE);
				}

				target.setVelocity(0, 0, 0);
			}
		}

		return super.hitEntity(stack, target, attacker);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(EnumChatFormatting.BLUE + "Ability: " + EnumChatFormatting.WHITE + "Slaughters pigs");
	}
}
