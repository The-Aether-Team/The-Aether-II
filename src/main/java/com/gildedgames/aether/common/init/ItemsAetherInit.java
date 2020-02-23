package com.gildedgames.aether.common.init;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.SoundsAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.items.ItemDropOnDeath;
import com.gildedgames.aether.common.items.armor.*;
import com.gildedgames.aether.common.items.blocks.ItemBlockCustomDoor;
import com.gildedgames.aether.common.items.consumables.*;
import com.gildedgames.aether.common.items.irradiated.ItemIrradiated;
import com.gildedgames.aether.common.items.irradiated.ItemIrradiatedVisuals;
import com.gildedgames.aether.common.items.other.*;
import com.gildedgames.aether.common.items.tools.*;
import com.gildedgames.aether.common.items.weapons.ItemDart;
import com.gildedgames.aether.common.items.weapons.ItemDartShooter;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBolt;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemCrossbow;
import com.gildedgames.aether.common.items.weapons.swords.*;
import com.gildedgames.aether.common.util.selectors.RandomItemSelector;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Mod.EventBusSubscriber
public class ItemsAetherInit
{
	private static final Collection<Item> registeredItems = new ArrayList<>();

	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event)
	{
		final ItemRegistryHelper r = new ItemRegistryHelper(event.getRegistry());
		r.register("aechor_petal", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS));
		r.register("aerwhale_music_disc", new ItemAetherRecord("aerwhale", "records.aerwhale"));
		r.register("aether_saddle", new ItemDropOnDeath().setMaxStackSize(1).setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS));
		r.register("ambrosium_chunk", new ItemAmbrosiumChunk().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("ambrosium_shard", new ItemAmbrosiumShard().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("antitoxin_vial", new ItemCurative(IAetherStatusEffects.effectTypes.TOXIN,true, 32, EnumAction.DRINK)
				.setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("antivenom_vial", new ItemCurative(IAetherStatusEffects.effectTypes.COCKATRICE_VENOM,true, 32, EnumAction.DRINK)
				.setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("arkenium", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("arkenium_axe", new ItemAetherAxe(MaterialsAether.ARKENIUM_TOOL, 7.0F, -3.3F));
		r.register("arkenium_boots", new ItemArkeniumArmor(EntityEquipmentSlot.FEET).slashDefense(3).impactDefense(11));
		r.register("arkenium_chestplate", new ItemArkeniumArmor(EntityEquipmentSlot.CHEST).slashDefense(22).impactDefense(17));
		r.register("arkenium_crossbow", new ItemCrossbow().setDurationInTicks(35).setKnockBackValue(0.5F).setType(ItemCrossbow.crossBowTypes.ARKENIUM));
		r.register("arkenium_door_item", new ItemBlockCustomDoor(BlocksAether.arkenium_door).setCreativeTab(CreativeTabsAether.TAB_CONSTRUCTION));
		r.register("arkenium_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.ARKENIUM));
		r.register("arkenium_helmet", new ItemArkeniumArmor(EntityEquipmentSlot.HEAD).pierceDefense(13));
		r.register("arkenium_leggings", new ItemArkeniumArmor(EntityEquipmentSlot.LEGS).slashDefense(7).pierceDefense(22));
		r.register("arkenium_pickaxe", new ItemAetherPickaxe(MaterialsAether.ARKENIUM_TOOL));
		r.register("arkenium_shears", new ItemArkeniumShears().setCreativeTab(CreativeTabsAether.TAB_TOOLS));
		r.register("arkenium_shield", new ItemAetherShield().setMaxDamage(6154));
		r.register("arkenium_shovel", new ItemAetherShovel(MaterialsAether.ARKENIUM_TOOL));
		r.register("arkenium_strip", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("arkenium_sword", new ItemArkeniumSword());
			r.register("bandage", new ItemCurative(IAetherStatusEffects.effectTypes.BLEED, false, 32, EnumAction.DRINK)
				.setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("blueberries", new ItemAetherFood(1,0.2F,false).setConsumptionDuration(16));
		r.register("blueberry_lollipop", new ItemAetherFood(5, 0.6F, false));
		r.register("bolt", new ItemBolt().setCreativeTab(CreativeTabsAether.TAB_WEAPONS));
		r.register("brettl_cane", new ItemBrettlCane().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("brettl_grass", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("burrukai_pelt", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("burrukai_rib_cut", new ItemAetherFood(4, 0.3F, true).setConsumptionDuration(40));
		r.register("burrukai_ribs", new ItemAetherFood(9, 0.8F, true).setConsumptionDuration(40));
		r.register("candy_cane", new ItemAetherFood(2, 0.1F, false).setConsumptionDuration(16).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("candy_corn", new ItemAetherFood(3, 0.1F, false).setConsumptionDuration(16));
		r.register("cloud_parachute", new ItemCloudParachute().setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS));
		r.register("cloudtwine", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("cockatrice_feather", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("cocoatrice", new ItemAetherFood(4, 0.3F, false));
		r.register("dart", new ItemDart().setCreativeTab(CreativeTabsAether.TAB_WEAPONS));
		r.register("dart_shooter", new ItemDartShooter().setCreativeTab(CreativeTabsAether.TAB_WEAPONS));
		r.register("eggnog", new ItemEggnog().setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("enchanted_blueberry", new ItemAetherFood(3, 0.6F, false).setConsumptionDuration(16));
		r.register("enchanted_wyndberry", new ItemEnchantedWyndberry());
		r.register("fried_moa_egg", new ItemFood(5, 0.3F,false).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("ginger_bread_man", new ItemAetherFood(3, 0.2F, false).setConsumptionDuration(24).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("golden_amber", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("gravitite_axe", new ItemAetherAxe(MaterialsAether.GRAVITITE_TOOL));
		r.register("gravitite_boots", new ItemGravititeArmor(EntityEquipmentSlot.FEET).slashDefense(4).impactDefense(12));
		r.register("gravitite_chestplate", new ItemGravititeArmor(EntityEquipmentSlot.CHEST).slashDefense(24).impactDefense(20));
		r.register("gravitite_crossbow", new ItemCrossbow().setDurationInTicks(25).setKnockBackValue(1.2F).setType(ItemCrossbow.crossBowTypes.GRAVETITE));
		r.register("gravitite_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.GRAVITITE));
		r.register("gravitite_helmet", new ItemGravititeArmor(EntityEquipmentSlot.HEAD).pierceDefense(16));
		r.register("gravitite_leggings", new ItemGravititeArmor(EntityEquipmentSlot.LEGS).slashDefense(8).pierceDefense(24));
		r.register("gravitite_pickaxe", new ItemAetherPickaxe(MaterialsAether.GRAVITITE_TOOL));
		r.register("gravitite_plate", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("gravitite_shield", new ItemAetherShield().setMaxDamage(3010));
		r.register("gravitite_shovel", new ItemAetherShovel(MaterialsAether.GRAVITITE_TOOL));
		r.register("gravitite_sword", new ItemGravititeSword());
		r.register("healing_stone", new ItemHealingStone().setMaxStackSize(1).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("healing_stone_depleted", new ItemDropOnDeath().setMaxStackSize(1).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("holystone_axe", new ItemAetherAxe(MaterialsAether.HOLYSTONE_TOOL));
		r.register("holystone_crossbow", new ItemCrossbow().setDurationInTicks(30).setKnockBackValue(0.7F).setType(ItemCrossbow.crossBowTypes.HOLYSTONE));
		r.register("holystone_pickaxe", new ItemAetherPickaxe(MaterialsAether.HOLYSTONE_TOOL));
		r.register("holystone_shield", new ItemAetherShield().setMaxDamage(253));
		r.register("holystone_shovel", new ItemAetherShovel(MaterialsAether.HOLYSTONE_TOOL));
		r.register("holystone_sword", new ItemHolystoneSword());
		r.register("icestone", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("icestone_poprocks", new ItemAetherFood(2, 0.1F, false).setConsumptionDuration(16));
		r.register("irradiated_armor", new ItemIrradiated(new RandomItemSelector(item -> item instanceof ItemArmor)).setMaxStackSize(1)
				.setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("irradiated_charm", new ItemIrradiated(
				new RandomItemSelector(item -> AetherAPI.content().items().getProperties(item).getEquipmentSlot() == ItemEquipmentSlot.CHARM))
				.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("irradiated_chunk", new ItemIrradiated(new RandomItemSelector(stack -> !(stack instanceof ItemIrradiated))).setMaxStackSize(1)
				.setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("irradiated_dust", new ItemIrradiatedVisuals().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("irradiated_neckwear", new ItemIrradiated(
				new RandomItemSelector(item -> AetherAPI.content().items().getProperties(item).getEquipmentSlot() == ItemEquipmentSlot.NECKWEAR))
				.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("irradiated_ring",
				new ItemIrradiated(new RandomItemSelector(item -> AetherAPI.content().items().getProperties(item).getEquipmentSlot() == ItemEquipmentSlot.RING))
						.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("irradiated_sword",
				new ItemIrradiated(new RandomItemSelector(item -> item.getTranslationKey().contains("sword") && !(item instanceof ItemIrradiated)))
						.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("irradiated_tool", new ItemIrradiated(new RandomItemSelector(item -> item instanceof ItemTool)).setMaxStackSize(1)
				.setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("jelly_plumproot", new ItemAetherFood(6, 0.6F, false).setConsumptionDuration(32));
		r.register("kirrid_cutlet", new ItemAetherFood(6, 0.8F, true));
		r.register("kirrid_loin", new ItemAetherFood(2, 0.3F, true));
		r.register("labyrinth_music_disc", new ItemAetherRecord("labyrinth", "records.labyrinth"));
		r.register("moa_egg_item", new ItemMoaEgg(false));
		r.register("moa_feather", new ItemMoaFeather().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("moa_music_disc", new ItemAetherRecord("moa", "records.moa"));
		r.register("orange", new ItemAetherFood(2, 0.3F, false).setConsumptionDuration(24));
		r.register("orange_lollipop", new ItemAetherFood(7, 0.6F, false));
		r.register("plumproot_mash", new ItemAetherFood(2, 0.2F, false).setConsumptionDuration(24));
		r.register("plumproot_pie", new ItemAetherFood(7, 0.3F, false));
		r.register("rainbow_moa_egg", new ItemMoaEgg(true).setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS));
		r.register("raw_taegore_meat", new ItemAetherFood(3, 0.3F, true));
		r.register("recording_892", new ItemAetherRecord("recording_892", "records.recording_892"));
		r.register("scatterglass_vial", new ItemScatterglassVial().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("secret_skyroot_door_item", new ItemBlockCustomDoor(BlocksAether.secret_skyroot_door).setCreativeTab(CreativeTabsAether.TAB_DECORATIVE_BLOCKS));
		r.register("shard_of_life", new ItemShardOfLife().setMaxStackSize(4).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("skyroot_axe", new ItemAetherAxe(MaterialsAether.SKYROOT_TOOL));
		r.register("skyroot_bed_item", new ItemSkyrootBed().setCreativeTab(CreativeTabsAether.TAB_UTILITY));
		r.register("skyroot_bucket", new ItemSkyrootBucket(Blocks.AIR));
		r.register("skyroot_crossbow", new ItemCrossbow().setDurationInTicks(20).setKnockBackValue(0.5F).setType(ItemCrossbow.crossBowTypes.SKYROOT));
		r.register("skyroot_door_item", new ItemBlockCustomDoor(BlocksAether.skyroot_door).setCreativeTab(CreativeTabsAether.TAB_CONSTRUCTION));
		r.register("skyroot_lizard_stick", new ItemAetherFood(2, 0.3F, false).setConsumptionDuration(16).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("skyroot_lizard_stick_roasted",
				new ItemAetherFood(6, 0.6F, false).setConsumptionDuration(24).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("skyroot_milk_bucket", new ItemSkyrootConsumableBucket());
		r.register("skyroot_pickaxe", new ItemAetherPickaxe(MaterialsAether.SKYROOT_TOOL));
		r.register("skyroot_poison_bucket", new ItemSkyrootConsumableBucket());
		r.register("skyroot_shield", new ItemAetherShield().setMaxDamage(114));
		r.register("skyroot_shovel", new ItemAetherShovel(MaterialsAether.SKYROOT_TOOL));
		r.register("skyroot_sign", new ItemSkyrootSign().setCreativeTab(CreativeTabsAether.TAB_CONSTRUCTION));
		r.register("skyroot_stick", new ItemSkyrootStick().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("skyroot_sword", new ItemSkyrootSword());
		r.register("skyroot_water_bucket", new ItemSkyrootBucket(Blocks.FLOWING_WATER));
		r.register("splint", new ItemCurative(IAetherStatusEffects.effectTypes.FRACTURE,false, 32, EnumAction.DRINK)
				.setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("stomper_pop", new ItemStomperPop());
		r.register("swet_gel", new ItemSwetGel().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("swet_jelly", new ItemSwetJelly().setConsumptionDuration(24));
		r.register("swet_sugar", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("taegore_hide", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("taegore_hide_boots", new ItemTaegoreHideArmor(EntityEquipmentSlot.FEET).slashDefense(1).impactDefense(3));
		r.register("taegore_hide_chestplate", new ItemTaegoreHideArmor(EntityEquipmentSlot.CHEST).slashDefense(6).impactDefense(5));
		r.register("taegore_hide_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.TAEGOREHIDE));
		r.register("taegore_hide_helmet", new ItemTaegoreHideArmor(EntityEquipmentSlot.HEAD).pierceDefense(4));
		r.register("taegore_hide_leggings", new ItemTaegoreHideArmor(EntityEquipmentSlot.LEGS).slashDefense(2).pierceDefense(6));
		r.register("taegore_steak", new ItemAetherFood(8, 0.8F, true));
		r.register("valkyrie_music_disc", new ItemAetherRecord("valkyrie", "records.valkyrie"));
		r.register("valkyrie_wings", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("water_vial", new ItemWaterVial().setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("winter_hat", new ItemWinterHat().setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS));
		r.register("wrapped_chocolates", new ItemAetherFood(4, 0.3F, false));
		r.register("wrapping_paper", new ItemWrappingPaper().setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS));
		r.register("wyndberry", new ItemAetherFood(3, 0.3F, false));
		r.register("yule_log", new ItemAetherFood(8, 0.6F, false).setConsumptionDuration(40).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("zanite_axe", new ItemAetherAxe(MaterialsAether.ZANITE_TOOL));
		r.register("zanite_boots", new ItemZaniteArmor(EntityEquipmentSlot.FEET).slashDefense(2).impactDefense(6));
		r.register("zanite_chestplate", new ItemZaniteArmor(EntityEquipmentSlot.CHEST).slashDefense(12).impactDefense(10));
		r.register("zanite_crossbow", new ItemCrossbow().setDurationInTicks(20).setKnockBackValue(0.5F).setType(ItemCrossbow.crossBowTypes.ZANITE));
		r.register("zanite_gemstone", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("zanite_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.ZANITE));
		r.register("zanite_helmet", new ItemZaniteArmor(EntityEquipmentSlot.HEAD).pierceDefense(8));
		r.register("zanite_leggings", new ItemZaniteArmor(EntityEquipmentSlot.LEGS).slashDefense(4).pierceDefense(12));
		r.register("zanite_pickaxe", new ItemAetherPickaxe(MaterialsAether.ZANITE_TOOL));
		r.register("zanite_shield", new ItemAetherShield().setMaxDamage(482));
		r.register("zanite_shovel", new ItemAetherShovel(MaterialsAether.ZANITE_TOOL));
		r.register("zanite_sword", new ItemZaniteSword());
	}

	public static Collection<Item> getRegisteredItems()
	{
		return Collections.unmodifiableCollection(registeredItems);
	}

	private static class ItemRegistryHelper
	{
		private final IForgeRegistry<Item> registry;

		ItemRegistryHelper(final IForgeRegistry<Item> registry)
		{
			this.registry = registry;
		}

		private void register(final String registryName, final Item item)
		{
			item.setTranslationKey(AetherCore.MOD_ID + "." + registryName);
			item.setRegistryName(registryName);

			this.registry.register(item);

			registeredItems.add(item);
		}
	}
}