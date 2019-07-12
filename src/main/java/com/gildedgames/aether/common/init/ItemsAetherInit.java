package com.gildedgames.aether.common.init;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.items.ItemDropOnDeath;
import com.gildedgames.aether.common.items.armor.*;
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
import net.minecraft.block.Blocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
		r.register("aerwhale_music_disc", new ItemAetherRecord("aerwhale", new SoundEvent(AetherCore.getResource("records.aerwhale"))));
		r.register("aether_saddle", new ItemDropOnDeath().setMaxStackSize(1).setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS));
		r.register("ambrosium_chunk", new ItemAmbrosiumChunk().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("ambrosium_shard", new ItemAmbrosiumShard().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("arkenium", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("arkenium_axe", new ItemAetherAxe(MaterialsAether.ARKENIUM_TOOL, 7.0F, -3.3F));
		r.register("arkenium_boots", new ItemArkeniumArmor(EquipmentSlotType.FEET).pierceDefense(1).<ItemArkeniumArmor>impactDefense(1));
		r.register("arkenium_chestplate", new ItemArkeniumArmor(EquipmentSlotType.CHEST).slashDefense(2).<ItemArkeniumArmor>impactDefense(1));
		r.register("arkenium_crossbow", new ItemCrossbow().setDurationInTicks(35).setKnockBackValue(0.5F).setType(ItemCrossbow.crossBowTypes.ARKENIUM));
		r.register("arkenium_door_item", new ItemDoor(BlocksAether.arkenium_door).setCreativeTab(CreativeTabsAether.TAB_CONSTRUCTION));
		r.register("arkenium_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.ARKENIUM));
		r.register("arkenium_helmet", new ItemArkeniumArmor(EquipmentSlotType.HEAD).<ItemArkeniumArmor>impactDefense(1));
		r.register("arkenium_leggings", new ItemArkeniumArmor(EquipmentSlotType.LEGS).pierceDefense(2).<ItemArkeniumArmor>impactDefense(1));
		r.register("arkenium_pickaxe", new ItemAetherPickaxe(MaterialsAether.ARKENIUM_TOOL));
		r.register("arkenium_shears", new ItemArkeniumShears().setCreativeTab(CreativeTabsAether.TAB_TOOLS));
		r.register("arkenium_shield", new ItemAetherShield());
		r.register("arkenium_shovel", new ItemAetherShovel(MaterialsAether.ARKENIUM_TOOL));
		r.register("arkenium_strip", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("arkenium_sword", new ItemArkeniumSword());
		r.register("blueberries", new ItemAetherFood(2, false).setConsumptionDuration(12));
		r.register("blueberry_lollipop", new ItemAetherFood(4, 0.2F, false).setConsumptionDuration(8));
		r.register("bolt", new ItemBolt().setCreativeTab(CreativeTabsAether.TAB_WEAPONS));
		r.register("brettl_cane", new ItemBrettlCane().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("brettl_grass", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("burrukai_pelt", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("burrukai_rib_cut", new ItemAetherFood(5, 0.3F, true).setConsumptionDuration(40));
		r.register("burrukai_ribs", new ItemAetherFood(12, 0.5F, true).setConsumptionDuration(40));
		r.register("candy_cane", new ItemAetherFood(5, 0.3F, false).setConsumptionDuration(10).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("candy_corn", new ItemAetherFood(4, 0.1F, false).setConsumptionDuration(5));
		r.register("cloud_parachute", new ItemCloudParachute().setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS));
		r.register("cloudtwine", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("cockatrice_feather", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("cocoatrice", new ItemAetherFood(8, 0.2F, false).setConsumptionDuration(8));
		r.register("dart", new ItemDart().setCreativeTab(CreativeTabsAether.TAB_WEAPONS));
		r.register("dart_shooter", new ItemDartShooter().setCreativeTab(CreativeTabsAether.TAB_WEAPONS));
		r.register("eggnog", new ItemEggnog().setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("enchanted_blueberry", new ItemAetherFood(6, false).setConsumptionDuration(12));
		r.register("enchanted_wyndberry", new ItemEnchantedWyndberry().setConsumptionDuration(22));
		r.register("fried_moa_egg", new ItemFood(10, false).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("ginger_bread_man", new ItemAetherFood(6, 0.2F, false).setConsumptionDuration(8).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("golden_amber", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("gravitite_axe", new ItemAetherAxe(MaterialsAether.GRAVITITE_TOOL));
		r.register("gravitite_boots", new ItemGravititeArmor(EquipmentSlotType.FEET).<ItemGravititeArmor>pierceDefense(2));
		r.register("gravitite_chestplate", new ItemGravititeArmor(EquipmentSlotType.CHEST).<ItemGravititeArmor>slashDefense(3));
		r.register("gravitite_crossbow", new ItemCrossbow().setDurationInTicks(25).setKnockBackValue(1.2F).setType(ItemCrossbow.crossBowTypes.GRAVETITE));
		r.register("gravitite_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.GRAVITITE));
		r.register("gravitite_helmet", new ItemGravititeArmor(EquipmentSlotType.HEAD).<ItemGravititeArmor>impactDefense(3));
		r.register("gravitite_leggings", new ItemGravititeArmor(EquipmentSlotType.LEGS).<ItemGravititeArmor>pierceDefense(3));
		r.register("gravitite_pickaxe", new ItemAetherPickaxe(MaterialsAether.GRAVITITE_TOOL));
		r.register("gravitite_plate", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("gravitite_shield", new ItemAetherShield());
		r.register("gravitite_shovel", new ItemAetherShovel(MaterialsAether.GRAVITITE_TOOL));
		r.register("gravitite_sword", new ItemGravititeSword());
		r.register("healing_stone", new ItemHealingStone().setMaxStackSize(1).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("healing_stone_depleted", new ItemDropOnDeath().setMaxStackSize(1).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("holystone_axe", new ItemAetherAxe(MaterialsAether.HOLYSTONE_TOOL));
		r.register("holystone_crossbow", new ItemCrossbow().setDurationInTicks(30).setKnockBackValue(0.7F).setType(ItemCrossbow.crossBowTypes.HOLYSTONE));
		r.register("holystone_pickaxe", new ItemAetherPickaxe(MaterialsAether.HOLYSTONE_TOOL));
		r.register("holystone_shield", new ItemAetherShield());
		r.register("holystone_shovel", new ItemAetherShovel(MaterialsAether.HOLYSTONE_TOOL));
		r.register("holystone_sword", new ItemHolystoneSword());
		r.register("icestone", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("icestone_poprocks", new ItemAetherFood(2, 0.5F, false).setConsumptionDuration(8));
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
		r.register("irradiated_tool", new ItemIrradiated(new RandomItemSelector(item -> item instanceof ToolItem)).setMaxStackSize(1)
				.setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("jelly_plumproot", new ItemAetherFood(6, 0.25F, false).setConsumptionDuration(15));
		r.register("kirrid_cutlet", new ItemAetherFood(12, 0.5F, true));
		r.register("kirrid_loin", new ItemAetherFood(5, 0.3F, true));
		r.register("labyrinth_music_disc", new ItemAetherRecord("labyrinth", new SoundEvent(AetherCore.getResource("records.labyrinth"))));
		r.register("moa_egg_item", new ItemMoaEgg(false));
		r.register("moa_feather", new ItemMoaFeather().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("moa_music_disc", new ItemAetherRecord("moa", new SoundEvent(AetherCore.getResource("records.moa"))));
		r.register("orange", new ItemAetherFood(4, false).setConsumptionDuration(22));
		r.register("orange_lollipop", new ItemAetherFood(4, 0.2F, false).setConsumptionDuration(8));
		r.register("plumproot_mash", new ItemAetherFood(3, 1.0F, false).setConsumptionDuration(12));
		r.register("plumproot_pie", new ItemAetherFood(10, 0.5F, false).setConsumptionDuration(40));
		r.register("rainbow_moa_egg", new ItemMoaEgg(true).setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS));
		r.register("raw_taegore_meat", new ItemAetherFood(5, 0.3F, true));
		r.register("recording_892", new ItemAetherRecord("recording_892", new SoundEvent(AetherCore.getResource("records.recording_892"))));
		r.register("secret_skyroot_door_item", new ItemDoor(BlocksAether.secret_skyroot_door).setCreativeTab(CreativeTabsAether.TAB_DECORATIVE_BLOCKS));
		r.register("shard_of_life", new ItemShardOfLife().setMaxStackSize(4).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("skyroot_axe", new ItemAetherAxe(MaterialsAether.SKYROOT_TOOL));
		r.register("skyroot_bed_item", new ItemSkyrootBed().setCreativeTab(CreativeTabsAether.TAB_UTILITY));
		r.register("skyroot_bucket", new ItemSkyrootBucket(Blocks.AIR));
		r.register("skyroot_crossbow", new ItemCrossbow().setDurationInTicks(20).setKnockBackValue(0.5F).setType(ItemCrossbow.crossBowTypes.SKYROOT));
		r.register("skyroot_door_item", new ItemDoor(BlocksAether.skyroot_door).setCreativeTab(CreativeTabsAether.TAB_CONSTRUCTION));
		r.register("skyroot_lizard_stick", new ItemAetherFood(2, 0.5F, false).setConsumptionDuration(30).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("skyroot_lizard_stick_roasted",
				new ItemAetherFood(8, 0.5F, false).setConsumptionDuration(20).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("skyroot_milk_bucket", new ItemSkyrootConsumableBucket());
		r.register("skyroot_pickaxe", new ItemAetherPickaxe(MaterialsAether.SKYROOT_TOOL));
		r.register("skyroot_poison_bucket", new ItemSkyrootConsumableBucket());
		r.register("skyroot_shield", new ItemAetherShield());
		r.register("skyroot_shovel", new ItemAetherShovel(MaterialsAether.SKYROOT_TOOL));
		r.register("skyroot_sign", new ItemSkyrootSign().setCreativeTab(CreativeTabsAether.TAB_CONSTRUCTION));
		r.register("skyroot_stick", new ItemSkyrootStick().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("skyroot_sword", new ItemSkyrootSword());
		r.register("skyroot_water_bucket", new ItemSkyrootBucket(Blocks.FLOWING_WATER));
		r.register("stomper_pop", new ItemStomperPop());
		r.register("swet_gel", new ItemSwetGel().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("swet_jelly", new ItemSwetJelly());
		r.register("swet_sugar", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("taegore_hide", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("taegore_hide_boots", new ItemTaegoreHideArmor(EquipmentSlotType.FEET).<ItemTaegoreHideArmor>slashDefense(1));
		r.register("taegore_hide_chestplate", new ItemTaegoreHideArmor(EquipmentSlotType.CHEST).<ItemTaegoreHideArmor>slashDefense(2));
		r.register("taegore_hide_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.TAEGOREHIDE));
		r.register("taegore_hide_helmet", new ItemTaegoreHideArmor(EquipmentSlotType.HEAD).<ItemTaegoreHideArmor>impactDefense(2));
		r.register("taegore_hide_leggings", new ItemTaegoreHideArmor(EquipmentSlotType.LEGS).<ItemTaegoreHideArmor>pierceDefense(2));
		r.register("taegore_steak", new ItemAetherFood(12, 0.5F, true));
		r.register("valkyrie_music_disc", new ItemAetherRecord("valkyrie", new SoundEvent(AetherCore.getResource("records.valkyrie"))));
		r.register("valkyrie_wings", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("winter_hat", new ItemWinterHat().setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS));
		r.register("wrapped_chocolates", new ItemAetherFood(6, 0.25F, false).setConsumptionDuration(8));
		r.register("wrapping_paper", new ItemWrappingPaper().setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS));
		r.register("wyndberry", new ItemAetherFood(4, false).setConsumptionDuration(22));
		r.register("yule_log", new ItemAetherFood(10, 0.25F, false).setConsumptionDuration(50).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("zanite_axe", new ItemAetherAxe(MaterialsAether.ZANITE_TOOL));
		r.register("zanite_boots", new ItemZaniteArmor(EquipmentSlotType.FEET).<ItemZaniteArmor>pierceDefense(2));
		r.register("zanite_chestplate", new ItemZaniteArmor(EquipmentSlotType.CHEST).<ItemZaniteArmor>slashDefense(2));
		r.register("zanite_crossbow", new ItemCrossbow().setDurationInTicks(20).setKnockBackValue(0.5F).setType(ItemCrossbow.crossBowTypes.ZANITE));
		r.register("zanite_gemstone", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("zanite_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.ZANITE));
		r.register("zanite_helmet", new ItemZaniteArmor(EquipmentSlotType.HEAD).<ItemZaniteArmor>impactDefense(2));
		r.register("zanite_leggings", new ItemZaniteArmor(EquipmentSlotType.LEGS).<ItemZaniteArmor>pierceDefense(2));
		r.register("zanite_pickaxe", new ItemAetherPickaxe(MaterialsAether.ZANITE_TOOL));
		r.register("zanite_shield", new ItemAetherShield());
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
