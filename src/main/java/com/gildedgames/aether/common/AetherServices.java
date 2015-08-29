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
	
	public static final CreativeTab TAB_BLOCKS = new CreativeTab("aetherBlocks");

	public static final CreativeTab TAB_MATERIALS = new CreativeTab("aetherMaterials");

	public static final CreativeTab TAB_TOOLS = new CreativeTab("aetherTools");

	public static final CreativeTab TAB_WEAPONS = new CreativeTab("aetherWeapons");

	public static final CreativeTab TAB_ARMOR = new CreativeTab("aetherArmor");

	public static final CreativeTab TAB_CONSUMABLES = new CreativeTab("aetherConsumables");

	public AetherServices(Side side)
	{
		this.side = side;

		this.playerHookPool = new PlayerHookPool<PlayerAether>(AetherCore.MOD_ID, new PlayerAetherHookFactory(), side);
	}
	
	public void registerTabIcons()
	{
		this.TAB_BLOCKS.setDisplayStack(new ItemStack(BlocksAether.aether_grass, BlockAetherGrass.AETHER_GRASS.getMeta()));
		this.TAB_MATERIALS.setDisplayStack(new ItemStack(ItemsAether.skyroot_stick));
		this.TAB_TOOLS.setDisplayStack(new ItemStack(ItemsAether.gravitite_pickaxe));
		this.TAB_WEAPONS.setDisplayStack(new ItemStack(ItemsAether.gravitite_sword));
		this.TAB_ARMOR.setDisplayStack(new ItemStack(ItemsAether.zanite_chestplate));
		this.TAB_CONSUMABLES.setDisplayStack(new ItemStack(ItemsAether.orange));
	}

	public IPlayerHookPool<PlayerAether> getPool()
	{
		return this.playerHookPool;
	}
	
}
