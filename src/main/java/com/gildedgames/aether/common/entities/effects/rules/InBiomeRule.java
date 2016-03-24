package com.gildedgames.aether.common.entities.effects.rules;

import com.gildedgames.aether.common.entities.effects.EffectRule;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class InBiomeRule implements EffectRule
{

	private BiomeGenBase biome;

	public InBiomeRule(BiomeGenBase biome)
	{
		this.biome = biome;
	}

	@Override
	public boolean isMet(Entity source)
	{
		return source.worldObj.getBiomeGenForCoords(source.getPosition()) == this.biome;
	}

	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "In " + this.biome.biomeName + "s" };
	}

	@Override
	public boolean blockLivingAttackAbility(Entity source, LivingHurtEvent event)
	{
		return false;
	}

}
