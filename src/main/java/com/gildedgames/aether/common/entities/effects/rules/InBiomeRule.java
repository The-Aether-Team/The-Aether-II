package com.gildedgames.aether.common.entities.effects.rules;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.biome.BiomeGenBase;

import com.gildedgames.aether.common.entities.effects.AbilityRule;

public class InBiomeRule implements AbilityRule<Entity>
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
		return new String[] { EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "In " + EnumChatFormatting.BOLD + this.biome.biomeName + "s" };
	}

}
