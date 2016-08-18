package com.gildedgames.aether.common.world.dungeon;

import com.gildedgames.aether.common.items.ItemsAether;
import com.google.common.base.Supplier;
import net.minecraft.item.ItemStack;

public class LootDefinitions
{

	private LootDefinitions() {}

	public static final Supplier<ItemStack[]> SLIDERS_LABYRINTH = new Supplier<ItemStack[]>()
	{
		@Override
		public ItemStack[] get()
		{
			return new ItemStack[]
			{
				new ItemStack(ItemsAether.ethereal_stone),
				new ItemStack(ItemsAether.fleeting_stone),
				new ItemStack(ItemsAether.soaring_stone),
				new ItemStack(ItemsAether.frostpine_totem),
				new ItemStack(ItemsAether.kraisith_capsule),
				new ItemStack(ItemsAether.orb_of_arkenzus),
				new ItemStack(ItemsAether.fangrin_capsule),
				new ItemStack(ItemsAether.death_seal),
				new ItemStack(ItemsAether.barbed_iron_ring),
				new ItemStack(ItemsAether.barbed_gold_ring),
				new ItemStack(ItemsAether.solar_band),
				new ItemStack(ItemsAether.lunar_band),
				new ItemStack(ItemsAether.ring_of_growth),
				new ItemStack(ItemsAether.plague_coil),
				new ItemStack(ItemsAether.fleeting_ring),
				new ItemStack(ItemsAether.lesser_ring_of_growth),
				new ItemStack(ItemsAether.winged_ring),
				new ItemStack(ItemsAether.life_coil),
				new ItemStack(ItemsAether.iron_barbed_wire),
				new ItemStack(ItemsAether.wisdom_bauble),
				new ItemStack(ItemsAether.bone_shard),
				new ItemStack(ItemsAether.moa_feather),
				new ItemStack(ItemsAether.blight_ward),
				new ItemStack(ItemsAether.skyroot_twig),
				new ItemStack(ItemsAether.gold_barbed_wire),
				new ItemStack(ItemsAether.iron_barbed_wire),
				new ItemStack(ItemsAether.ambrosium_talisman),
				new ItemStack(ItemsAether.carrion_petal),
				new ItemStack(ItemsAether.moonlit_petal),
				new ItemStack(ItemsAether.cockatrice_heart),
				new ItemStack(ItemsAether.damaged_moa_feather),
				new ItemStack(ItemsAether.osseous_bane),
				new ItemStack(ItemsAether.rot_bane),
				new ItemStack(ItemsAether.continuum_talisman),
				new ItemStack(ItemsAether.labyrinth_plans),
				new ItemStack(ItemsAether.shard_of_life),
				new ItemStack(ItemsAether.pink_baby_swet),
				new ItemStack(ItemsAether.candy_ring),
				new ItemStack(ItemsAether.bone_ring),
				new ItemStack(ItemsAether.skyroot_ring),
				new ItemStack(ItemsAether.daggerfrost_locket),
				new ItemStack(ItemsAether.ice_ring),
				new ItemStack(ItemsAether.ice_pendant),
				new ItemStack(ItemsAether.iron_bubble),
				new ItemStack(ItemsAether.regeneration_stone),
				new ItemStack(ItemsAether.vampire_crossbow),
				new ItemStack(ItemsAether.vampire_blade),
				new ItemStack(ItemsAether.pig_slayer),
				new ItemStack(ItemsAether.flaming_sword),
				new ItemStack(ItemsAether.holy_sword),
				new ItemStack(ItemsAether.lightning_sword)
			};
		}
	};

}
