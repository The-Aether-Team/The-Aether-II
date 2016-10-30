package com.gildedgames.aether.common.capabilities.entity.effects.rules;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class HoldingItemRule implements EntityEffectRule
{

	private ItemStack stack;

	public HoldingItemRule(ItemStack stack)
	{
		this.stack = stack;
	}

	@Override
	public boolean isMet(Entity source)
	{
		if (!(source instanceof EntityLivingBase))
		{
			return false;
		}

		EntityLivingBase living = (EntityLivingBase) source;

		return this.stack.isItemEqual(living.getActiveItemStack());
	}

	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { ChatFormatting.GRAY + "" + ChatFormatting.ITALIC + "Holding: " + this.stack.getDisplayName() };
	}

	@Override
	public boolean blockLivingAttack(Entity source, float amount, Entity target)
	{
		return false;
	}

	@Override
	public boolean blockLivingHurt(Entity source, LivingHurtEvent event)
	{
		return false;
	}

}
