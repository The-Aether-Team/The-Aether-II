package com.gildedgames.aether.common;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.aether.common.player.PlayerAetherHookFactory;
import com.gildedgames.aether.common.util.CreativeTab;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.PlayerHookPool;

public class AetherServices
{
	
	private final IPlayerHookPool<PlayerAether> playerHookPool;

	private final Side side;
	
	public final CreativeTab tabBlocks = new CreativeTab("aetherBlocks");

	public final CreativeTab tabMaterials = new CreativeTab("aetherMaterials");

	public final CreativeTab tabTools = new CreativeTab("aetherTools");

	public final CreativeTab tabWeapons = new CreativeTab("aetherWeapons");

	public final CreativeTab tabArmor = new CreativeTab("aetherArmor");

	public final CreativeTab tabConsumables = new CreativeTab("aetherConsumables");

	public AetherServices(Side side)
	{
		this.side = side;

		this.playerHookPool = new PlayerHookPool<PlayerAether>(AetherCore.MOD_ID, new PlayerAetherHookFactory(), side);
	}
	
	public void registerTabIcons()
	{
		tabBlocks.setDisplayStack(new ItemStack(BlocksAether.aether_grass, BlockAetherGrass.AETHER_GRASS.getMeta()));
		tabMaterials.setDisplayStack(new ItemStack(ItemsAether.skyroot_stick));
		tabTools.setDisplayStack(new ItemStack(ItemsAether.gravitite_pickaxe));
		tabWeapons.setDisplayStack(new ItemStack(ItemsAether.gravitite_sword));
		tabArmor.setDisplayStack(new ItemStack(ItemsAether.zanite_chestplate));
		tabConsumables.setDisplayStack(new ItemStack(ItemsAether.orange));
	}

	public IPlayerHookPool<PlayerAether> getPool()
	{
		return this.playerHookPool;
	}
	
}
