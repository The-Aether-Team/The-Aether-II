package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class LootTablesAether
{
	public static ResourceLocation ENTITY_KIRRID_SHEARED;

	public static ResourceLocation ENTITY_KIRRID;

	public static ResourceLocation ENTITY_AECHOR_PLANT;

	public static ResourceLocation ENTITY_COCKATRICE;

	public static ResourceLocation ENTITY_SWET, ENTITY_SWET_BLUE, ENTITY_SWET_GOLDEN, ENTITY_SWET_DARK, ENTITY_SWET_LIGHT;

	public static ResourceLocation ENTITY_FLYING_COW;

	public static ResourceLocation ENTITY_PHYG;

	public static ResourceLocation ENTITY_CARRION_SPROUT;

	public static ResourceLocation ENTITY_AERBUNNY;

	public static void preInit()
	{
		ENTITY_KIRRID_SHEARED = LootTableList.register(AetherCore.getResource("entities/kirrid/kirrid_sheared"));
		ENTITY_KIRRID = LootTableList.register(AetherCore.getResource("entities/kirrid/kirrid"));

		ENTITY_AECHOR_PLANT = LootTableList.register(AetherCore.getResource("entities/aechor_plant"));

		ENTITY_COCKATRICE = LootTableList.register(AetherCore.getResource("entities/cockatrice"));

		ENTITY_SWET = LootTableList.register(AetherCore.getResource("entities/swet/swet"));
		ENTITY_SWET_BLUE = LootTableList.register(AetherCore.getResource("entities/swet/swet_blue"));
		ENTITY_SWET_GOLDEN = LootTableList.register(AetherCore.getResource("entities/swet/swet_golden"));
		ENTITY_SWET_DARK = LootTableList.register(AetherCore.getResource("entities/swet/swet_dark"));
		ENTITY_SWET_LIGHT = LootTableList.register(AetherCore.getResource("entities/swet/swet_light"));

		ENTITY_FLYING_COW = LootTableList.register(AetherCore.getResource("entities/flying_cow"));

		ENTITY_PHYG = LootTableList.register(AetherCore.getResource("entities/phyg"));

		ENTITY_CARRION_SPROUT = LootTableList.register(AetherCore.getResource("entities/carrion_sprout"));

		ENTITY_AERBUNNY = LootTableList.register(AetherCore.getResource("entities/aerbunny"));
	}
}
