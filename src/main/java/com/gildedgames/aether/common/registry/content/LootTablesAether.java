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

	public static ResourceLocation ENTITY_SWET, ENTITY_SWET_BLUE, ENTITY_SWET_GREEN, ENTITY_SWET_PURPLE;

	public static ResourceLocation ENTITY_FLYING_COW;

	public static ResourceLocation ENTITY_TAEGORE;

	public static ResourceLocation ENTITY_BURRUKAI;

	public static ResourceLocation ENTITY_CARRION_SPROUT;

	public static ResourceLocation ENTITY_AERBUNNY;

	public static ResourceLocation ENTITY_ZEPHYR;

	public static void preInit()
	{
		ENTITY_KIRRID_SHEARED = LootTableList.register(AetherCore.getResource("entities/kirrid/kirrid_sheared"));
		ENTITY_KIRRID = LootTableList.register(AetherCore.getResource("entities/kirrid/kirrid"));

		ENTITY_AECHOR_PLANT = LootTableList.register(AetherCore.getResource("entities/aechor_plant"));

		ENTITY_COCKATRICE = LootTableList.register(AetherCore.getResource("entities/cockatrice"));

		ENTITY_SWET = LootTableList.register(AetherCore.getResource("entities/swet/swet"));
		ENTITY_SWET_BLUE = LootTableList.register(AetherCore.getResource("entities/swet/swet_blue"));
		ENTITY_SWET_GREEN = LootTableList.register(AetherCore.getResource("entities/swet/swet_green"));
		ENTITY_SWET_PURPLE = LootTableList.register(AetherCore.getResource("entities/swet/swet_purple"));

		ENTITY_FLYING_COW = LootTableList.register(AetherCore.getResource("entities/flying_cow"));

		ENTITY_TAEGORE = LootTableList.register(AetherCore.getResource("entities/taegore"));

		ENTITY_BURRUKAI = LootTableList.register(AetherCore.getResource("entities/burrukai"));

		ENTITY_CARRION_SPROUT = LootTableList.register(AetherCore.getResource("entities/carrion_sprout"));

		ENTITY_AERBUNNY = LootTableList.register(AetherCore.getResource("entities/aerbunny"));

		ENTITY_ZEPHYR = LootTableList.register(AetherCore.getResource("entities/zephyr"));
	}
}
