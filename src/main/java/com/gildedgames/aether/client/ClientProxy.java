package com.gildedgames.aether.client;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.gildedgames.aether.client.models.ModelsAether;
import com.gildedgames.aether.client.renderer.AetherRenderers;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.CommonProxy;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.items.ItemsAether;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);

		ModelsAether.preInit();
		
		this.TabBlocks.setDisplayStack(new ItemStack(BlocksAether.aether_grass, BlockAetherGrass.AETHER_GRASS.getMeta()));
		this.TabMaterials.setDisplayStack(new ItemStack(ItemsAether.skyroot_stick));
		this.TabTools.setDisplayStack(new ItemStack(ItemsAether.gravitite_pickaxe));
		this.TabWeapons.setDisplayStack(new ItemStack(ItemsAether.gravitite_sword));
		this.TabArmor.setDisplayStack(new ItemStack(ItemsAether.zanite_chestplate));
		this.TabConsumables.setDisplayStack(new ItemStack(ItemsAether.orange));
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);

		ModelsAether.init();
		AetherRenderers.init();
	}
}
