package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.loot.Loot;
import com.gildedgames.aether.api.loot.LootPool;
import com.gildedgames.aether.api.loot.selectors.ChanceLoot;
import com.gildedgames.aether.api.loot.selectors.RangedLoot;
import com.gildedgames.aether.api.loot.selectors.SingleStackLoot;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.living.mobs.EntitySwet;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBoltType;
import net.minecraft.item.ItemStack;

public class LootDefinitions
{

	private LootDefinitions() {}

	public static final LootPool LABYRINTH_FINAL_BOSS = new LootPool()
	{

		Loot[] loot = new Loot[]
		{
			new SingleStackLoot(new ItemStack(ItemsAether.ice_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.ice_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.candy_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.bone_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.skyroot_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.barbed_iron_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.barbed_gold_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.solar_band)),
			new SingleStackLoot(new ItemStack(ItemsAether.lunar_band)),
			new SingleStackLoot(new ItemStack(ItemsAether.ring_of_growth)),
			new SingleStackLoot(new ItemStack(ItemsAether.plague_coil)),
			new SingleStackLoot(new ItemStack(ItemsAether.fleeting_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.lesser_ring_of_growth)),
			new SingleStackLoot(new ItemStack(ItemsAether.winged_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.life_coil)),
			new SingleStackLoot(new ItemStack(ItemsAether.granite_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.gust_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.typhoon_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.sporing_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.ember_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.steam_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.mud_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.storm_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.dust_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.ring_of_strength)),
			new SingleStackLoot(new ItemStack(ItemsAether.gruegar_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.arkenium_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.swift_ribbon)),
			new SingleStackLoot(new ItemStack(ItemsAether.wynd_cluster_ring)),
			new SingleStackLoot(new ItemStack(ItemsAether.ring_of_wisdom)),
			new SingleStackLoot(new ItemStack(ItemsAether.lesser_ring_of_wisdom)),
			new SingleStackLoot(new ItemStack(ItemsAether.ice_pendant)),
			new SingleStackLoot(new ItemStack(ItemsAether.amulet_of_growth)),
			new SingleStackLoot(new ItemStack(ItemsAether.lesser_amulet_of_growth)),
			new SingleStackLoot(new ItemStack(ItemsAether.frostward_scarf)),
			new SingleStackLoot(new ItemStack(ItemsAether.gruegar_scarf)),
			new SingleStackLoot(new ItemStack(ItemsAether.zanite_studded_choker)),
			new SingleStackLoot(new ItemStack(ItemsAether.arkenium_studded_choker)),
			new SingleStackLoot(new ItemStack(ItemsAether.hide_gorget)),
			new SingleStackLoot(new ItemStack(ItemsAether.raegorite_gorget)),
			new SingleStackLoot(new ItemStack(ItemsAether.thiefs_gorget)),
			new SingleStackLoot(new ItemStack(ItemsAether.moon_sect_warden_gorget)),
			new SingleStackLoot(new ItemStack(ItemsAether.fleeting_scarf)),
			new SingleStackLoot(new ItemStack(ItemsAether.winged_necklace)),
			new SingleStackLoot(new ItemStack(ItemsAether.gust_amulet)),
			new SingleStackLoot(new ItemStack(ItemsAether.typhoon_amulet)),
			new SingleStackLoot(new ItemStack(ItemsAether.chain_of_sporing_bones)),
			new SingleStackLoot(new ItemStack(ItemsAether.molten_amulet)),
			new SingleStackLoot(new ItemStack(ItemsAether.granite_studded_choker)),
			new SingleStackLoot(new ItemStack(ItemsAether.muggers_cloak)),
			new SingleStackLoot(new ItemStack(ItemsAether.bandit_shawl)),
			new SingleStackLoot(new ItemStack(ItemsAether.iron_bubble)),
			new SingleStackLoot(new ItemStack(ItemsAether.regeneration_stone)),
			new SingleStackLoot(new ItemStack(ItemsAether.daggerfrost_rune)),
			new SingleStackLoot(new ItemStack(ItemsAether.moonlit_scroll)),
			new SingleStackLoot(new ItemStack(ItemsAether.sunlit_scroll)),
			new SingleStackLoot(new ItemStack(ItemsAether.moonlit_tome)),
			new SingleStackLoot(new ItemStack(ItemsAether.sunlit_tome)),
			new SingleStackLoot(new ItemStack(ItemsAether.primal_totem_of_survival)),
			new SingleStackLoot(new ItemStack(ItemsAether.primal_totem_of_rage)),
			new SingleStackLoot(new ItemStack(ItemsAether.divine_beacon)),
			new SingleStackLoot(new ItemStack(ItemsAether.phoenix_rune)),
			new SingleStackLoot(new ItemStack(ItemsAether.glamoured_iron_screw)),
			new SingleStackLoot(new ItemStack(ItemsAether.wisdom_bauble)),
			new SingleStackLoot(new ItemStack(ItemsAether.moa_feather)),
			new SingleStackLoot(new ItemStack(ItemsAether.blight_ward)),
			new SingleStackLoot(new ItemStack(ItemsAether.glamoured_skyroot_twig)),
			new SingleStackLoot(new ItemStack(ItemsAether.glamoured_gold_screw)),
			new SingleStackLoot(new ItemStack(ItemsAether.ambrosium_talisman)),
			new SingleStackLoot(new ItemStack(ItemsAether.glamoured_cockatrice_heart)),
			new SingleStackLoot(new ItemStack(ItemsAether.damaged_moa_feather)),
			new SingleStackLoot(new ItemStack(ItemsAether.osseous_bane)),
			new SingleStackLoot(new ItemStack(ItemsAether.rot_bane)),
			new SingleStackLoot(new ItemStack(ItemsAether.glamoured_zephyr_husk)),
			new SingleStackLoot(new ItemStack(ItemsAether.glamoured_blue_swet_jelly)),
			new SingleStackLoot(new ItemStack(ItemsAether.glamoured_cockatrice_talons)),
			new SingleStackLoot(new ItemStack(ItemsAether.glamoured_coal_ember)),
			new SingleStackLoot(new ItemStack(ItemsAether.white_moa_feather)),
			new SingleStackLoot(new ItemStack(ItemsAether.sakura_moa_feather)),
			new SingleStackLoot(new ItemStack(ItemsAether.glamoured_taegore_tusk)),
			new SingleStackLoot(new ItemStack(ItemsAether.butchers_knife)),
			new SingleStackLoot(new ItemStack(ItemsAether.gruegar_pouch)),
			new SingleStackLoot(new ItemStack(ItemsAether.hide_pouch)),
			new SingleStackLoot(new ItemStack(ItemsAether.angel_bandage)),
			new SingleStackLoot(new ItemStack(ItemsAether.swift_rune)),
			new SingleStackLoot(new ItemStack(ItemsAether.wynd_cluster)),
			new SingleStackLoot(new ItemStack(ItemsAether.wisdom_rune)),
			new SingleStackLoot(new ItemStack(ItemsAether.glamoured_cockatrice_keratin)),
			new SingleStackLoot(new ItemStack(ItemsAether.neptune_helmet)),
			new SingleStackLoot(new ItemStack(ItemsAether.neptune_chestplate)),
			new SingleStackLoot(new ItemStack(ItemsAether.neptune_leggings)),
			new SingleStackLoot(new ItemStack(ItemsAether.neptune_boots)),
			new SingleStackLoot(new ItemStack(ItemsAether.neptune_gloves)),
			new SingleStackLoot(new ItemStack(ItemsAether.phoenix_helmet)),
			new SingleStackLoot(new ItemStack(ItemsAether.phoenix_chestplate)),
			new SingleStackLoot(new ItemStack(ItemsAether.phoenix_leggings)),
			new SingleStackLoot(new ItemStack(ItemsAether.phoenix_boots)),
			new SingleStackLoot(new ItemStack(ItemsAether.phoenix_gloves)),
			new SingleStackLoot(new ItemStack(ItemsAether.valkyrie_helmet)),
			new SingleStackLoot(new ItemStack(ItemsAether.valkyrie_chestplate)),
			new SingleStackLoot(new ItemStack(ItemsAether.valkyrie_leggings)),
			new SingleStackLoot(new ItemStack(ItemsAether.valkyrie_boots)),
			new SingleStackLoot(new ItemStack(ItemsAether.valkyrie_gloves))
		};

		@Override
		public Loot[] getPossibleLoot()
		{
			return this.loot;
		}

	};

	public static final LootPool LABYRINTH_TRASH = new LootPool()
	{

		Loot[] loot = new Loot[]
		{
			new SingleStackLoot(new ItemStack(ItemsAether.shard_of_life)),
			new RangedLoot(new ItemStack(ItemsAether.icestone_poprocks), 2, 5),
			new RangedLoot(new ItemStack(ItemsAether.orange_lollipop), 1, 3),
			new RangedLoot(new ItemStack(ItemsAether.blueberry_lollipop), 1, 3),
			new RangedLoot(new ItemStack(ItemsAether.jelly_pumpkin), 1, 3),
			new RangedLoot(new ItemStack(ItemsAether.candy_corn), 3, 5),
			new RangedLoot(new ItemStack(ItemsAether.wrapped_chocolates), 1, 4),
			new RangedLoot(new ItemStack(ItemsAether.cocoatrice), 1, 2),
			new ChanceLoot(new ItemStack(ItemsAether.gummy_swet, 1, EntitySwet.Type.BLUE.ordinal()), 0.2F),
			new ChanceLoot(new ItemStack(ItemsAether.gummy_swet, 1, EntitySwet.Type.DARK.ordinal()), 0.2F),
			new ChanceLoot(new ItemStack(ItemsAether.gummy_swet, 1, EntitySwet.Type.LIGHT.ordinal()), 0.2F),
			new ChanceLoot(new ItemStack(ItemsAether.gummy_swet, 1, EntitySwet.Type.GOLDEN.ordinal()), 0.2F),
			new ChanceLoot(new ItemStack(ItemsAether.sentry_vaultbox), 0.02F),
			new RangedLoot(new ItemStack(BlocksAether.ambrosium_torch), 3, 9),
			new ChanceLoot(new ItemStack(ItemsAether.skyroot_sword), 0.2F),
			new ChanceLoot(new ItemStack(ItemsAether.skyroot_shield), 0.2F),
			new RangedLoot(new ItemStack(ItemsAether.dart), 1, 6),
			new RangedLoot(new ItemStack(ItemsAether.bolt, 1, ItemBoltType.SKYROOT.ordinal()), 1, 6),
			new RangedLoot(new ItemStack(ItemsAether.bolt, 1, ItemBoltType.HOLYSTONE.ordinal()), 1, 6),
			new ChanceLoot(new ItemStack(ItemsAether.aerwhale_music_disc), 0.05F),
			new ChanceLoot(new ItemStack(ItemsAether.moa_music_disc), 0.05F),
			new ChanceLoot(new ItemStack(ItemsAether.labyrinth_music_disc), 0.05F),
			new ChanceLoot(new ItemStack(ItemsAether.valkyrie_music_disc), 0.05F),
			new ChanceLoot(new ItemStack(ItemsAether.chain_gloves), 0.05F),
			new RangedLoot(new ItemStack(ItemsAether.swet_jelly, 1, EntitySwet.Type.BLUE.ordinal()), 1, 4),
			new RangedLoot(new ItemStack(ItemsAether.swet_jelly, 1, EntitySwet.Type.DARK.ordinal()), 1, 4),
			new RangedLoot(new ItemStack(ItemsAether.swet_jelly, 1, EntitySwet.Type.LIGHT.ordinal()), 1, 4),
			new RangedLoot(new ItemStack(ItemsAether.swet_jelly, 1, EntitySwet.Type.GOLDEN.ordinal()), 1, 4),
			new ChanceLoot(new ItemStack(ItemsAether.irradiated_charm), 0.10F),
			new ChanceLoot(new ItemStack(ItemsAether.irradiated_neckwear), 0.05F),
			new ChanceLoot(new ItemStack(ItemsAether.irradiated_ring), 0.05F),
			new ChanceLoot(new ItemStack(ItemsAether.irradiated_tool), 0.05F),
			new ChanceLoot(new ItemStack(ItemsAether.irradiated_armor), 0.05F),
			new ChanceLoot(new ItemStack(ItemsAether.irradiated_sword), 0.05F),
			new ChanceLoot(new ItemStack(ItemsAether.irradiated_chunk), 0.15F)
		};

		@Override
		public Loot[] getPossibleLoot()
		{
			return this.loot;
		}

	};

}
