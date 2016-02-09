package com.gildedgames.aether.common.entities.effects.rules;

import net.minecraft.entity.Entity;
import net.minecraft.world.biome.BiomeGenBase;

import com.gildedgames.aether.common.entities.effects.AbilityRule;
import com.mojang.realmsclient.gui.ChatFormatting;

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
		return new String[] { ChatFormatting.GRAY + "" + ChatFormatting.ITALIC + "In " + ChatFormatting.BOLD + this.biome.biomeName + "s" };
	}

}
