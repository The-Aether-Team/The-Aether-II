package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.ReflectionAether;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Set;

public enum EnumToolType
{

	PICKAXE("pickaxe")
	{
		@Override
		public Set<Block> createEffectiveBlockSet()
		{
			return ObfuscationReflectionHelper.getPrivateValue(ItemPickaxe.class, (ItemPickaxe)Items.WOODEN_PICKAXE, ReflectionAether.EFFECTIVE_ON_PICKAXE.getMappings());
		}
	},

	AXE("axe")
	{
		@Override
		public Set<Block> createEffectiveBlockSet()
		{
			return ObfuscationReflectionHelper.getPrivateValue(ItemAxe.class, (ItemAxe)Items.WOODEN_AXE, ReflectionAether.EFFECTIVE_ON_AXE.getMappings());
		}
	},

	SHOVEL("shovel")
	{
		@Override
		public Set<Block> createEffectiveBlockSet()
		{
			return ObfuscationReflectionHelper.getPrivateValue(ItemSpade.class, (ItemSpade)Items.WOODEN_SHOVEL, ReflectionAether.EFFECTIVE_ON_SHOVEL.getMappings());
		}
	};

	private final String toolClass;

	private final Set<Block> effectiveBlocks;

	EnumToolType(String toolClass)
	{
		this.toolClass = toolClass;
		this.effectiveBlocks = this.createEffectiveBlockSet();
	}

	public abstract Set<Block> createEffectiveBlockSet();

	public String getToolClass()
	{
		return this.toolClass;
	}

	public Set<Block> getEffectiveBlocks()
	{
		return this.effectiveBlocks;
	}
}
