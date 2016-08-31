package com.gildedgames.aether.common.capabilities.entity.effects.rules;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class InBiomeRule implements EntityEffectRule
{

	private Biome biome;

	public InBiomeRule(Biome biome)
	{
		this.biome = biome;
	}

	@Override
	public boolean isMet(Entity source)
	{
		return source.worldObj.getBiome(source.getPosition()) == this.biome;
	}

	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { TextFormatting.GRAY + "" + TextFormatting.ITALIC + "In " + this.biome.getBiomeName() + "s" };
	}

	@Override
	public boolean blockLivingAttack(Entity source, LivingAttackEvent event)
	{
		return false;
	}

	@Override
	public boolean blockLivingHurt(Entity source, LivingHurtEvent event)
	{
		return false;
	}

}
