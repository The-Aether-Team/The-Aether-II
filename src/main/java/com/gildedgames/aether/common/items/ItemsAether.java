package com.gildedgames.aether.common.items;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.capabilites.entity.properties.ElementalState;
import com.gildedgames.aether.api.capabilites.items.properties.IItemPropertiesCapability;
import com.gildedgames.aether.api.capabilites.items.properties.ItemEquipmentType;
import com.gildedgames.aether.api.capabilites.items.properties.ItemRarity;
import com.gildedgames.aether.api.registry.equipment.IEquipmentProperties;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.capabilities.entity.effects.EntityEffects;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.ChangeAttackElementEffect;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.DoubleDropEffect;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.FreezeBlocksEffect;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.LeechLifeEffect;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.LevitateAttackersEffect;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.ModifyAttackSpeedEffect;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.ModifyDamageEffect;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.ModifyDefenseEffect;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.ModifyMaxHealthEffect;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.ModifySpeedEffect;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.ModifyWeightEffect;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.RegenerateHealthEffect;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.SetAttackersOnFireEffect;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.player.ModifyXPCollectionEffect;
import com.gildedgames.aether.common.capabilities.entity.effects.rules.DamagingElementRule;
import com.gildedgames.aether.common.capabilities.entity.effects.rules.DamagingMobRule;
import com.gildedgames.aether.common.capabilities.entity.effects.rules.DamagingPassiveAnimalsRule;
import com.gildedgames.aether.common.capabilities.entity.effects.rules.DamagingUndeadRule;
import com.gildedgames.aether.common.capabilities.entity.effects.rules.HoldingItemRule;
import com.gildedgames.aether.common.capabilities.entity.effects.rules.InCombatRule;
import com.gildedgames.aether.common.capabilities.entity.effects.rules.InDirectMoonlightRule;
import com.gildedgames.aether.common.capabilities.entity.effects.rules.InDirectSunlightRule;
import com.gildedgames.aether.common.capabilities.entity.effects.rules.OutOfCombatRule;
import com.gildedgames.aether.common.capabilities.entity.effects.rules.WhenPoisonedRule;
import com.gildedgames.aether.common.capabilities.item.effects.ItemEffects;
import com.gildedgames.aether.common.entities.living.companions.EntityEtheralWisp;
import com.gildedgames.aether.common.entities.living.companions.EntityFangrin;
import com.gildedgames.aether.common.entities.living.companions.EntityFleetingWisp;
import com.gildedgames.aether.common.entities.living.companions.EntityFrostpineTotem;
import com.gildedgames.aether.common.entities.living.companions.EntityKraisith;
import com.gildedgames.aether.common.entities.living.companions.EntityNexSpirit;
import com.gildedgames.aether.common.entities.living.companions.EntityPinkBabySwet;
import com.gildedgames.aether.common.entities.living.companions.EntityShadeOfArkenzus;
import com.gildedgames.aether.common.entities.living.companions.EntitySoaringWisp;
import com.gildedgames.aether.common.items.armor.ItemAetherGloves;
import com.gildedgames.aether.common.items.armor.ItemAetherShield;
import com.gildedgames.aether.common.items.armor.ItemGravititeArmor;
import com.gildedgames.aether.common.items.armor.ItemLeatherGloves;
import com.gildedgames.aether.common.items.armor.ItemNeptuneArmor;
import com.gildedgames.aether.common.items.armor.ItemPhoenixArmor;
import com.gildedgames.aether.common.items.armor.ItemValkyrieArmor;
import com.gildedgames.aether.common.items.armor.ItemZaniteArmor;
import com.gildedgames.aether.common.items.companions.ItemCompanion;
import com.gildedgames.aether.common.items.companions.ItemDeathSeal;
import com.gildedgames.aether.common.items.consumables.ItemAmbrosiumChunk;
import com.gildedgames.aether.common.items.consumables.ItemCloudParachute;
import com.gildedgames.aether.common.items.consumables.ItemEnchantedWyndberry;
import com.gildedgames.aether.common.items.consumables.ItemGummySwet;
import com.gildedgames.aether.common.items.consumables.ItemHealingStone;
import com.gildedgames.aether.common.items.consumables.ItemSentryVault;
import com.gildedgames.aether.common.items.consumables.ItemShardOfLife;
import com.gildedgames.aether.common.items.consumables.ItemSkyrootConsumableBucket;
import com.gildedgames.aether.common.items.consumables.ItemStomperPop;
import com.gildedgames.aether.common.items.consumables.ItemSwetJelly;
import com.gildedgames.aether.common.items.misc.*;
import com.gildedgames.aether.common.items.tools.EnumToolType;
import com.gildedgames.aether.common.items.tools.ItemArkeniumTool;
import com.gildedgames.aether.common.items.tools.ItemGravititeTool;
import com.gildedgames.aether.common.items.tools.ItemHolystoneTool;
import com.gildedgames.aether.common.items.tools.ItemSkyrootBucket;
import com.gildedgames.aether.common.items.tools.ItemSkyrootTool;
import com.gildedgames.aether.common.items.tools.ItemValkyrieTool;
import com.gildedgames.aether.common.items.tools.ItemZaniteTool;
import com.gildedgames.aether.common.items.weapons.ItemDart;
import com.gildedgames.aether.common.items.weapons.ItemDartShooter;
import com.gildedgames.aether.common.items.weapons.ItemVampireBlade;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBolt;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemCrossbow;
import com.gildedgames.aether.common.items.weapons.swords.ItemAetherSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemArkeniumSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemCandyCaneSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemElementalSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemGravititeSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemHolystoneSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemSkyrootSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemZaniteSword;
import com.gildedgames.aether.common.registry.GenerationAether;
import com.gildedgames.aether.common.registry.TemperatureHandler;
import com.gildedgames.aether.common.registry.minecraft.CreativeTabsAether;
import com.gildedgames.aether.common.registry.minecraft.MaterialsAether;
import com.gildedgames.aether.common.registry.minecraft.SoundsAether;
import com.gildedgames.aether.common.util.Constraint;
import com.gildedgames.aether.common.util.selectors.RandomItemSelector;
import com.gildedgames.aether.common.world.dimensions.aether.features.WorldGenTemplate;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ItemsAether
{
	private static final Collection<Item> registeredItems = new ArrayList<>();

	public static final Item skyroot_stick = new Item();

	public static final Item ambrosium_shard = new Item(),
			ambrosium_chunk = new ItemAmbrosiumChunk(),
			zanite_gemstone = new Item(),
			arkenium = new Item(),
			arkenium_strip = new Item(),
			arkenium_chunk = new Item(),
			gravitite_chunk = new Item(),
			gravitite_plate = new Item();

	public static final ItemSkyrootTool skyroot_axe = new ItemSkyrootTool(EnumToolType.AXE, 6.0F, -3.2F),
			skyroot_pickaxe = new ItemSkyrootTool(EnumToolType.PICKAXE, 1.0F, -2.8F),
			skyroot_shovel = new ItemSkyrootTool(EnumToolType.SHOVEL, 1.5F, -3.0F);

	public static final ItemHolystoneTool holystone_axe = new ItemHolystoneTool(EnumToolType.AXE, 8.0F, -3.2F),
			holystone_pickaxe = new ItemHolystoneTool(EnumToolType.PICKAXE, 1.0F, -2.8F),
			holystone_shovel = new ItemHolystoneTool(EnumToolType.SHOVEL, 1.5F, -3.0F);

	public static final ItemZaniteTool zanite_axe = new ItemZaniteTool(EnumToolType.AXE, 8.0F, -3.1F),
			zanite_pickaxe = new ItemZaniteTool(EnumToolType.PICKAXE, 1.0F, -2.8F),
			zanite_shovel = new ItemZaniteTool(EnumToolType.SHOVEL, 1.5F, -3.0F);

	public static final ItemGravititeTool gravitite_axe = new ItemGravititeTool(EnumToolType.AXE, 8.0F, -3.0F),
			gravitite_pickaxe = new ItemGravititeTool(EnumToolType.PICKAXE, 1.0F, -2.8F),
			gravitite_shovel = new ItemGravititeTool(EnumToolType.SHOVEL, 1.5F, -3.0F);

	public static final ItemValkyrieTool valkyrie_axe = new ItemValkyrieTool(EnumToolType.AXE, 8.0F, -3.0F),
			valkyrie_pickaxe = new ItemValkyrieTool(EnumToolType.PICKAXE, 1.0F, -2.8F),
			valkyrie_shovel = new ItemValkyrieTool(EnumToolType.SHOVEL, 1.5F, -3.0F);

	public static final ItemArkeniumTool arkenium_axe = new ItemArkeniumTool(EnumToolType.AXE, 7.0F, -3.3F),
			arkenium_pickaxe = new ItemArkeniumTool(EnumToolType.PICKAXE, 4.0F, -3.2F),
			arkenium_shovel = new ItemArkeniumTool(EnumToolType.SHOVEL, 4.5F, -3.3F);

	public static final ItemShears arkenium_shears = new ItemShears();

	public static final ItemAetherSword skyroot_sword = new ItemSkyrootSword(),
			holystone_sword = new ItemHolystoneSword(),
			zanite_sword = new ItemZaniteSword(),
			gravitite_sword = new ItemGravititeSword(),
			arkenium_sword = new ItemArkeniumSword();

	public static final ItemZaniteArmor zanite_helmet = new ItemZaniteArmor(EntityEquipmentSlot.HEAD),
			zanite_chestplate = new ItemZaniteArmor(EntityEquipmentSlot.CHEST),
			zanite_leggings = new ItemZaniteArmor(EntityEquipmentSlot.LEGS),
			zanite_boots = new ItemZaniteArmor(EntityEquipmentSlot.FEET);

	public static final ItemGravititeArmor gravitite_helmet = new ItemGravititeArmor(EntityEquipmentSlot.HEAD),
			gravitite_chestplate = new ItemGravititeArmor(EntityEquipmentSlot.CHEST),
			gravitite_leggings = new ItemGravititeArmor(EntityEquipmentSlot.LEGS),
			gravitite_boots = new ItemGravititeArmor(EntityEquipmentSlot.FEET);

	public static final ItemNeptuneArmor neptune_helmet = new ItemNeptuneArmor(EntityEquipmentSlot.HEAD),
			neptune_chestplate = new ItemNeptuneArmor(EntityEquipmentSlot.CHEST),
			neptune_leggings = new ItemNeptuneArmor(EntityEquipmentSlot.LEGS),
			neptune_boots = new ItemNeptuneArmor(EntityEquipmentSlot.FEET);

	public static final ItemPhoenixArmor phoenix_helmet = new ItemPhoenixArmor(EntityEquipmentSlot.HEAD),
			phoenix_chestplate = new ItemPhoenixArmor(EntityEquipmentSlot.CHEST),
			phoenix_leggings = new ItemPhoenixArmor(EntityEquipmentSlot.LEGS),
			phoenix_boots = new ItemPhoenixArmor(EntityEquipmentSlot.FEET);

	public static final ItemValkyrieArmor valkyrie_helmet = new ItemValkyrieArmor(EntityEquipmentSlot.HEAD),
			valkyrie_chestplate = new ItemValkyrieArmor(EntityEquipmentSlot.CHEST),
			valkyrie_leggings = new ItemValkyrieArmor(EntityEquipmentSlot.LEGS),
			valkyrie_boots = new ItemValkyrieArmor(EntityEquipmentSlot.FEET);

	public static final Item golden_amber = new Item();

	public static final ItemAetherFood blueberries = new ItemAetherFood(2, false),
			enchanted_blueberry = new ItemAetherFood(6, false),
			orange = new ItemAetherFood(4, false),
			wyndberry = new ItemAetherFood(4, false),
			enchanted_wyndberry = new ItemEnchantedWyndberry(),
			swet_jelly = new ItemSwetJelly(),
			gummy_swet = new ItemGummySwet();

	public static final ItemAetherFood candy_corn = new ItemAetherFood(8, false),
			cocoatrice = new ItemAetherFood(12, false),
			wrapped_chocolates = new ItemAetherFood(12, false),
			jelly_pumpkin = new ItemAetherFood(12, false),
			stomper_pop = new ItemStomperPop(),
			blueberry_lollipop = new ItemAetherFood(10, false),
			orange_lollipop = new ItemAetherFood(8, false),
			icestone_poprocks = new ItemAetherFood(5, false);

	public static final ItemFood ginger_bread_man = new ItemFood(2, false),
			candy_cane = new ItemFood(2, false);

	public static final ItemSkyrootBucket skyroot_bucket = new ItemSkyrootBucket(Blocks.AIR),
			skyroot_water_bucket = new ItemSkyrootBucket(Blocks.FLOWING_WATER);

	public static final ItemSkyrootConsumableBucket skyroot_milk_bucket = new ItemSkyrootConsumableBucket(),
			skyroot_poison_bucket = new ItemSkyrootConsumableBucket();

	public static final ItemAetherRecord valkyrie_music_disc = new ItemAetherRecord("valkyrie", SoundsAether.record_valkyrie),
			labyrinth_music_disc = new ItemAetherRecord("labyrinth", SoundsAether.record_labyrinth),
			moa_music_disc = new ItemAetherRecord("moa", SoundsAether.record_moa),
			aerwhale_music_disc = new ItemAetherRecord("aerwhale", SoundsAether.record_aerwhale),
			recording_892 = new ItemAetherRecord("recording_892", SoundsAether.record_recording_892);

	public static final Item healing_stone = new ItemHealingStone();

	public static final Item healing_stone_depleted = new Item();

	public static final ItemDartShooter dart_shooter = new ItemDartShooter();

	public static final ItemDart dart = new ItemDart();

	public static final ItemElementalSword flaming_sword = new ItemElementalSword(ItemElementalSword.SwordElement.FIRE),
			holy_sword = new ItemElementalSword(ItemElementalSword.SwordElement.HOLY),
			lightning_sword = new ItemElementalSword(ItemElementalSword.SwordElement.LIGHTNING);

	public static final ItemSword vampire_blade = new ItemVampireBlade(),
			candy_cane_sword = new ItemCandyCaneSword(),
			valkyrie_lance = new ItemAetherSword(MaterialsAether.LEGENDARY_TOOL, ItemAbilityType.PASSIVE);

	public static final ItemDoor skyroot_door = new ItemDoor(BlocksAether.skyroot_door),
			arkenium_door = new ItemDoor(BlocksAether.arkenium_door),
			blightwillow_door = new ItemDoor(BlocksAether.blightwillow_door),
			earthshifter_door = new ItemDoor(BlocksAether.earthshifter_door),
			frostpine_door = new ItemDoor(BlocksAether.frostpine_door);

	public static final ItemCrossbow skyroot_crossbow = new ItemCrossbow().setDurationInTicks(20).setKnockBackValue(0.5F),
			holystone_crossbow = new ItemCrossbow().setDurationInTicks(15).setKnockBackValue(0.7F),
			zanite_crossbow = new ItemCrossbow().setDurationInTicks(10).setKnockBackValue(0.5F),
			arkenium_crossbow = new ItemCrossbow().setDurationInTicks(10).setKnockBackValue(0.5F),
			gravitite_crossbow = new ItemCrossbow().setDurationInTicks(5).setKnockBackValue(1.2F),
			vampire_crossbow = new ItemCrossbow().setDurationInTicks(5).setKnockBackValue(0.7F);

	public static final ItemBolt bolt = new ItemBolt();

	public static final Item iron_ring = new Item(), gold_ring = new Item();

	public static final Item zanite_ring = new Item(), zanite_pendant = new Item();

	public static final Item iron_pendant = new Item(), gold_pendant = new Item();

	public static final Item iron_bubble = new Item(), regeneration_stone = new Item();

	public static final Item ice_ring = new Item(), ice_pendant = new Item();

	public static final Item daggerfrost_rune = new Item();

	public static final Item candy_ring = new Item(),
			bone_ring = new Item(),
			skyroot_ring = new Item();

	public static final Item icestone = new Item();

	public static final Item skyroot_sign = new ItemSkyrootSign();
	
	public static final Item aether_portal_frame = new ItemTemplatePlacer(() -> GenerationAether.aether_portal);

	public static final Item nether_portal_frame = new ItemTemplatePlacer(() -> GenerationAether.nether_portal);

	public static final Item end_portal_frame = new ItemTemplatePlacer(() -> GenerationAether.end_portal);

	public static final Item aechor_petal = new Item();

	public static final Item zanite_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.ZANITE),
			gravitite_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.GRAVITITE),
			valkyrie_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.VALKYRIE),
			neptune_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.NEPTUNE),
			phoenix_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.PHOENIX);

	public static final Item leather_gloves = new ItemLeatherGloves(),
			iron_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.IRON),
			gold_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.GOLD),
			chain_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.CHAIN),
			diamond_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.DIAMOND);

    public static final Item pink_baby_swet = new ItemCompanion(EntityPinkBabySwet.class);

    public static final Item shard_of_life = new ItemShardOfLife();
    
	public static final ItemAetherShield skyroot_shield = new ItemAetherShield(),
			holystone_shield = new ItemAetherShield(),
			zanite_shield = new ItemAetherShield(),
			arkenium_shield = new ItemAetherShield(),
			gravitite_shield = new ItemAetherShield();

	public static final ItemCompanion ethereal_stone = new ItemCompanion(EntityEtheralWisp.class),
			fleeting_stone = new ItemCompanion(EntityFleetingWisp.class),
			soaring_stone = new ItemCompanion(EntitySoaringWisp.class);

	public static final ItemCompanion frostpine_totem = new ItemCompanion(EntityFrostpineTotem.class),
			kraisith_capsule = new ItemCompanion(EntityKraisith.class, (stack, player, tooltip, advanced) ->
			{
				tooltip.add(TextFormatting.RED + "\u2022 " + "10 Health");
				tooltip.add(TextFormatting.BLUE + "\u2022 " + "0.5 Attack Damage");
				tooltip.add(TextFormatting.BLUE + "\u2022 " + "Slows Enemies");
			}),
			orb_of_arkenzus = new ItemCompanion(EntityShadeOfArkenzus.class),
			fangrin_capsule = new ItemCompanion(EntityFangrin.class, (stack, player, tooltip, advanced) ->
			{
				tooltip.add(TextFormatting.RED + "\u2022 " + "10 Health");
				tooltip.add(TextFormatting.BLUE + "\u2022 " + "1.5 Attack Damage");
			}),
			death_seal = new ItemDeathSeal(EntityNexSpirit.class);

	public static final Item barbed_iron_ring = new Item(),
			barbed_gold_ring = new Item();

	public static final Item solar_band = new Item(),
			lunar_band = new Item();

	public static final Item ring_of_growth = new Item(),
			plague_coil = new Item();

	public static final Item fleeting_ring = new Item(),
			lesser_ring_of_growth = new Item(),
			winged_ring = new Item();

	public static final Item life_coil = new Item();

	public static final Item glamoured_iron_screw = new Item(),
			wisdom_bauble = new Item(),
			glamoured_bone_shard = new Item();

	public static final Item moa_feather = new Item(),
			blight_ward = new Item(),
			glamoured_skyroot_twig = new Item();

	public static final Item glamoured_gold_screw = new Item(),
			ambrosium_talisman = new Item(),
			sunlit_scroll = new Item();

	public static final Item moonlit_scroll = new Item(),
			glamoured_cockatrice_heart = new Item();

	public static final Item damaged_moa_feather = new Item(),
			osseous_bane = new Item(),
			rot_bane = new Item();

	public static final ItemSkyrootBed skyroot_bed = new ItemSkyrootBed();

	public static final ItemMoaEgg moa_egg = new ItemMoaEgg(false),
			rainbow_moa_egg = new ItemMoaEgg(true);

	public static final Item aether_developer_wand = new ItemAetherDeveloperWand();

	public static final Item cloud_parachute = new ItemCloudParachute();

	public static final Item amulet_of_growth = new Item(),
			lesser_amulet_of_growth = new Item();

	public static final Item frostward_scarf = new Item(),
			gruegar_scarf = new Item();

	public static final Item zanite_studded_choker = new Item(),
			arkenium_studded_choker = new Item();

	public static final Item hide_gorget = new Item(),
			raegorite_gorget = new Item(),
			thiefs_gorget = new Item(),
			moon_sect_warden_gorget = new Item();

	public static final Item glamoured_holystone_chip = new Item(),
			glamoured_zephyr_husk = new Item(),
			glamoured_ice_shard = new Item(),
			glamoured_blue_swet_jelly = new Item(),
			glamoured_cockatrice_talons = new Item(),
			glamoured_coal_ember = new Item();

	public static final Item granite_ring = new Item(),
			gust_ring = new Item(),
			typhoon_ring = new Item(),
			sporing_ring = new Item(),
			ember_ring = new Item();

	public static final Item white_moa_feather = new Item(),
			sakura_moa_feather = new Item();

	public static final Item valkyrie_wings = new Item(),
			sunlit_tome = new Item(),
			moonlit_tome = new Item(),
			phoenix_rune = new Item(),
			gravitite_core = new Item(),
			primal_totem_of_survival = new Item(),
			primal_totem_of_rage = new Item(),
			glamoured_taegore_tusk = new Item(),
			divine_beacon = new Item();

	public static final Item dust_ring = new Item(), mud_ring = new Item(),
			steam_ring = new Item(),
			storm_ring = new Item();

	public static final Item butchers_knife = new Item();

	public static final Item fleeting_scarf = new Item(),
			winged_necklace = new Item(),
			gust_amulet = new Item(),
			typhoon_amulet = new Item(),
			chain_of_sporing_bones = new Item(),
			molten_amulet = new Item(),
			granite_studded_choker = new Item();

	public static final Item muggers_cloak = new Item(),
			bandit_shawl = new Item(),
			hide_pouch = new Item(),
			gruegar_pouch = new Item(),
			soul_shard = new Item(),
			angel_bandage = new Item(),
			swift_rune = new Item(),
			wynd_cluster = new Item(),
			wisdom_rune = new Item(),
			glamoured_aerogel_chip = new Item();

	public static final Item ring_of_strength = new Item(),
			gruegar_ring = new Item(),
			arkenium_ring = new Item(),
			swift_ribbon = new Item(),
			wynd_cluster_ring = new Item(),
			lesser_ring_of_wisdom = new Item(),
			ring_of_wisdom = new Item();

	public static final Item bone_shard = new Item();

	public static final Item holystone_chip = new Item(),
			skyroot_fragment = new Item(),
			scatterglass_shard = new Item();

	public static final Item glamoured_cockatrice_keratin = new Item();

	public static final Item irradiated_chunk = new ItemIrradiated(new RandomItemSelector(stack -> !(stack instanceof ItemIrradiated))),
			irradiated_sword = new ItemIrradiated(new RandomItemSelector(item -> item.getUnlocalizedName().contains("sword") && !(item instanceof ItemIrradiated))),
			irradiated_armor = new ItemIrradiated(new RandomItemSelector(item -> item instanceof ItemArmor)),
			irradiated_tool = new ItemIrradiated(new RandomItemSelector(item -> item instanceof ItemTool)),
			irradiated_ring = new ItemIrradiated(new RandomItemSelector(item ->
			{
				IEquipmentProperties props = AetherAPI.equipment().getProperties(item);

				return props != null && props.getEquipmentType() == ItemEquipmentType.RING;
			})),
			irradiated_neckwear = new ItemIrradiated(new RandomItemSelector(item ->
			{
				IEquipmentProperties props = AetherAPI.equipment().getProperties(item);

				return props != null && props.getEquipmentType() == ItemEquipmentType.NECKWEAR;
			})),
			irradiated_charm = new ItemIrradiated(new RandomItemSelector(item ->
			{
				IEquipmentProperties props = AetherAPI.equipment().getProperties(item);

				return props != null && props.getEquipmentType() == ItemEquipmentType.CHARM;
			})),
			irradiated_dust = new ItemIrradiatedVisuals();

	public static final Item sentry_vaultbox = new ItemSentryVault(),
			wrapping_paper = new ItemWrappingPaper(),
			fried_moa_egg = new ItemFood(10, false);

	public static final ItemAetherSpawnEgg aether_spawn_egg = new ItemAetherSpawnEgg();

	public static void preInit()
	{
		registerItem("skyroot_stick", skyroot_stick.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("ambrosium_shard", ambrosium_shard.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("ambrosium_chunk", ambrosium_chunk.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("zanite_gemstone", zanite_gemstone.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("arkenium", arkenium.setCreativeTab(CreativeTabsAether.MATERIALS));
        registerItem("arkenium_strip", arkenium_strip.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("icestone", icestone.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("arkenium_chunk", arkenium_chunk.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("gravitite_chunk", gravitite_chunk.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("gravitite_plate", gravitite_plate.setCreativeTab(CreativeTabsAether.MATERIALS));

		registerItem("skyroot_axe", skyroot_axe);
		registerItem("skyroot_pickaxe", skyroot_pickaxe);
		registerItem("skyroot_shovel", skyroot_shovel);
		registerItem("skyroot_sword", skyroot_sword);
        registerItem("skyroot_shield", skyroot_shield);

		registerItem("holystone_axe", holystone_axe);
		registerItem("holystone_pickaxe", holystone_pickaxe);
		registerItem("holystone_shovel", holystone_shovel);
		registerItem("holystone_sword", holystone_sword);
		registerItem("holystone_shield", holystone_shield);

		registerItem("zanite_axe", zanite_axe);
		registerItem("zanite_pickaxe", zanite_pickaxe);
		registerItem("zanite_shovel", zanite_shovel);
		registerItem("zanite_sword", zanite_sword);
		registerItem("zanite_shield", zanite_shield);

        registerItem("arkenium_axe", arkenium_axe);
        registerItem("arkenium_pickaxe", arkenium_pickaxe);
        registerItem("arkenium_shovel", arkenium_shovel);
        registerItem("arkenium_sword", arkenium_sword);
        registerItem("arkenium_shield", arkenium_shield);

		registerItem("gravitite_axe", gravitite_axe);
		registerItem("gravitite_pickaxe", gravitite_pickaxe);
		registerItem("gravitite_shovel", gravitite_shovel);
		registerItem("gravitite_sword", gravitite_sword);
        registerItem("gravitite_shield", gravitite_shield);

		registerItem("valkyrie_axe", valkyrie_axe);
		registerItem("valkyrie_pickaxe", valkyrie_pickaxe);
		registerItem("valkyrie_shovel", valkyrie_shovel);

		registerItem("arkenium_shears", arkenium_shears.setCreativeTab(CreativeTabsAether.TOOLS));

		registerItem("zanite_helmet", zanite_helmet);
		registerItem("zanite_chestplate", zanite_chestplate);
		registerItem("zanite_leggings", zanite_leggings);
		registerItem("zanite_boots", zanite_boots);
        registerItem("zanite_gloves", zanite_gloves);

		registerItem("gravitite_helmet", gravitite_helmet);
		registerItem("gravitite_chestplate", gravitite_chestplate);
		registerItem("gravitite_leggings", gravitite_leggings);
		registerItem("gravitite_boots", gravitite_boots);
        registerItem("gravitite_gloves", gravitite_gloves);

		registerItem("neptune_helmet", neptune_helmet);
		registerItem("neptune_chestplate", neptune_chestplate);
		registerItem("neptune_leggings", neptune_leggings);
		registerItem("neptune_boots", neptune_boots);
        registerItem("neptune_gloves", neptune_gloves);

		registerItem("phoenix_helmet", phoenix_helmet);
		registerItem("phoenix_chestplate", phoenix_chestplate);
		registerItem("phoenix_leggings", phoenix_leggings);
		registerItem("phoenix_boots", phoenix_boots);
        registerItem("phoenix_gloves", phoenix_gloves);

		registerItem("valkyrie_helmet", valkyrie_helmet);
		registerItem("valkyrie_chestplate", valkyrie_chestplate);
		registerItem("valkyrie_leggings", valkyrie_leggings);
		registerItem("valkyrie_boots", valkyrie_boots);
        registerItem("valkyrie_gloves", valkyrie_gloves);

		registerItem("golden_amber", golden_amber.setCreativeTab(CreativeTabsAether.MATERIALS));

		registerItem("aechor_petal", aechor_petal.setCreativeTab(CreativeTabsAether.MISCELLANEOUS));

		registerItem("blueberries", blueberries);
		registerItem("enchanted_blueberry", enchanted_blueberry);
		registerItem("orange", orange);
		registerItem("wyndberry", wyndberry);
		registerItem("enchanted_wyndberry", enchanted_wyndberry);
		registerItem("swet_jelly", swet_jelly);
		registerItem("gummy_swet", gummy_swet);

		registerItem("candy_corn", candy_corn);
		registerItem("cocoatrice", cocoatrice);
		registerItem("wrapped_chocolates", wrapped_chocolates);
		registerItem("jelly_pumpkin", jelly_pumpkin);
		registerItem("stomper_pop", stomper_pop);
		registerItem("blueberry_lollipop", blueberry_lollipop);
		registerItem("orange_lollipop", orange_lollipop);
		registerItem("icestone_poprocks", icestone_poprocks);

		registerItem("ginger_bread_man", ginger_bread_man.setCreativeTab(CreativeTabsAether.CONSUMABLES));
		registerItem("candy_cane", candy_cane.setCreativeTab(CreativeTabsAether.CONSUMABLES));

		registerItem("skyroot_bucket", skyroot_bucket);
		registerItem("skyroot_water_bucket", skyroot_water_bucket);
		registerItem("skyroot_milk_bucket", skyroot_milk_bucket);
		registerItem("skyroot_poison_bucket", skyroot_poison_bucket);

		registerItem("valkyrie_music_disc", valkyrie_music_disc);
		registerItem("labyrinth_music_disc", labyrinth_music_disc);
		registerItem("moa_music_disc", moa_music_disc);
		registerItem("aerwhale_music_disc", aerwhale_music_disc);
		registerItem("recording_892", recording_892);

		registerItem("healing_stone_depleted", healing_stone_depleted.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CONSUMABLES));
		registerItem("healing_stone", healing_stone.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CONSUMABLES));

		registerItem("dart_shooter", dart_shooter.setCreativeTab(CreativeTabsAether.WEAPONS));
		registerItem("dart", dart.setCreativeTab(CreativeTabsAether.WEAPONS));

		registerItem("skyroot_crossbow", skyroot_crossbow);
		registerItem("holystone_crossbow", holystone_crossbow);
		registerItem("zanite_crossbow", zanite_crossbow);
		registerItem("arkenium_crossbow", arkenium_crossbow);
		registerItem("gravitite_crossbow", gravitite_crossbow);
		registerItem("vampire_crossbow", vampire_crossbow);
		registerItem("bolt", bolt.setCreativeTab(CreativeTabsAether.WEAPONS));

		registerItem("flaming_sword", flaming_sword);
		registerItem("holy_sword", holy_sword);
		registerItem("lightning_sword", lightning_sword);

		registerItem("vampire_blade", vampire_blade);
		registerItem("candy_cane_sword", candy_cane_sword);
		registerItem("valkyrie_lance", valkyrie_lance);

		registerItem("skyroot_door_item", skyroot_door.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerItem("arkenium_door_item", arkenium_door.setCreativeTab(CreativeTabsAether.BLOCKS));
		//registerItem("blightwillow_door_item", blightwillow_door_item.setCreativeTab(CreativeTabsAether.BLOCKS));
		//registerItem("earthshifter_door_item", earthshifter_door_item.setCreativeTab(CreativeTabsAether.BLOCKS));
		//registerItem("frostpine_door_item", frostpine_door_item.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerItem("iron_ring", iron_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("gold_ring", gold_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("iron_pendant", iron_pendant.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("gold_pendant", gold_pendant.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("zanite_ring", zanite_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("zanite_pendant", zanite_pendant.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("iron_bubble", iron_bubble.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("regeneration_stone", regeneration_stone.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("ice_ring", ice_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("ice_pendant", ice_pendant.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("daggerfrost_rune", daggerfrost_rune.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("candy_ring", candy_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("bone_ring", bone_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("skyroot_ring", skyroot_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		
		registerItem("skyroot_sign", skyroot_sign.setCreativeTab(CreativeTabsAether.BLOCKS));
		
		registerItem("aether_portal_frame", aether_portal_frame.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerItem("nether_portal_frame", nether_portal_frame.setCreativeTab(CreativeTabs.TRANSPORTATION));
		registerItem("end_portal_frame", end_portal_frame.setCreativeTab(CreativeTabs.TRANSPORTATION));

        registerItem("shard_of_life", shard_of_life.setMaxStackSize(4).setCreativeTab(CreativeTabsAether.CONSUMABLES));

		registerItem("leather_gloves", leather_gloves.setCreativeTab(CreativeTabs.COMBAT));
		registerItem("iron_gloves", iron_gloves.setCreativeTab(CreativeTabs.COMBAT));
		registerItem("gold_gloves", gold_gloves.setCreativeTab(CreativeTabs.COMBAT));
		registerItem("chain_gloves", chain_gloves.setCreativeTab(CreativeTabs.COMBAT));
		registerItem("diamond_gloves", diamond_gloves.setCreativeTab(CreativeTabs.COMBAT));

		registerItem("ethereal_stone", ethereal_stone);
		registerItem("fleeting_stone", fleeting_stone);
		registerItem("soaring_stone", soaring_stone);

		registerItem("pink_baby_swet", pink_baby_swet);
		registerItem("frostpine_totem", frostpine_totem);
		registerItem("kraisith_capsule", kraisith_capsule);
		registerItem("orb_of_arkenzus", orb_of_arkenzus);
		registerItem("fangrin_capsule", fangrin_capsule);
		registerItem("death_seal", death_seal);

		registerItem("barbed_iron_ring", barbed_iron_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("barbed_gold_ring", barbed_gold_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("solar_band", solar_band.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("lunar_band", lunar_band.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("ring_of_growth", ring_of_growth.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("plague_coil", plague_coil.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("fleeting_ring", fleeting_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("lesser_ring_of_growth", lesser_ring_of_growth.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("winged_ring", winged_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("life_coil", life_coil.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("glamoured_iron_screw", glamoured_iron_screw.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("wisdom_bauble", wisdom_bauble.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("glamoured_bone_shard", glamoured_bone_shard.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("moa_feather", moa_feather.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("blight_ward", blight_ward.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("glamoured_skyroot_twig", glamoured_skyroot_twig.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("glamoured_gold_screw", glamoured_gold_screw.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("ambrosium_talisman", ambrosium_talisman.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("sunlit_scroll", sunlit_scroll.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("moonlit_scroll", moonlit_scroll.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("glamoured_cockatrice_heart", glamoured_cockatrice_heart.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("damaged_moa_feather", damaged_moa_feather.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("osseous_bane", osseous_bane.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("rot_bane", rot_bane.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("skyroot_bed_item", skyroot_bed.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerItem("moa_egg_item", moa_egg);
		registerItem("rainbow_moa_egg", rainbow_moa_egg.setCreativeTab(CreativeTabsAether.MISCELLANEOUS));

		registerItem("aether_developer_wand", aether_developer_wand);

		registerItem("cloud_parachute", cloud_parachute.setCreativeTab(CreativeTabsAether.CONSUMABLES));

		registerItem("amulet_of_growth", amulet_of_growth.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("lesser_amulet_of_growth", lesser_amulet_of_growth.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("frostward_scarf", frostward_scarf.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("gruegar_scarf", gruegar_scarf.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("zanite_studded_choker", zanite_studded_choker.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("arkenium_studded_choker", arkenium_studded_choker.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("hide_gorget", hide_gorget.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("raegorite_gorget", raegorite_gorget.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("thiefs_gorget", thiefs_gorget.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("moon_sect_warden_gorget", moon_sect_warden_gorget.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));

		registerItem("glamoured_holystone_chip", glamoured_holystone_chip.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("glamoured_zephyr_husk", glamoured_zephyr_husk.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("glamoured_ice_shard", glamoured_ice_shard.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("glamoured_blue_swet_jelly", glamoured_blue_swet_jelly.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("glamoured_cockatrice_talons", glamoured_cockatrice_talons.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("glamoured_coal_ember", glamoured_coal_ember.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));

		registerItem("granite_ring", granite_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("gust_ring", gust_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("typhoon_ring", typhoon_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("sporing_ring", sporing_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("ember_ring", ember_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("dust_ring", dust_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("mud_ring", mud_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("storm_ring", storm_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("steam_ring", steam_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));

		registerItem("white_moa_feather", white_moa_feather.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("sakura_moa_feather", sakura_moa_feather.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));

		registerItem("sunlit_tome", sunlit_tome.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("moonlit_tome", moonlit_tome.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("primal_totem_of_survival", primal_totem_of_survival.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("primal_totem_of_rage", primal_totem_of_rage.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("divine_beacon", divine_beacon.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("phoenix_rune", phoenix_rune.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("glamoured_taegore_tusk", glamoured_taegore_tusk.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));

		registerItem("valkyrie_wings", valkyrie_wings.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.ARTIFACTS));
		registerItem("gravitite_core", gravitite_core.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.ARTIFACTS));

		registerItem("butchers_knife", butchers_knife.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));

		registerItem("fleeting_scarf", fleeting_scarf.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("winged_necklace", winged_necklace.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("gust_amulet", gust_amulet.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("typhoon_amulet", typhoon_amulet.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("chain_of_sporing_bones", chain_of_sporing_bones.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("molten_amulet", molten_amulet.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("granite_studded_choker", granite_studded_choker.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("muggers_cloak", muggers_cloak.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("bandit_shawl", bandit_shawl.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));

		registerItem("hide_pouch", hide_pouch.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("gruegar_pouch", gruegar_pouch.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("soul_shard", soul_shard.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("angel_bandage", angel_bandage.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("swift_rune", swift_rune.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("wynd_cluster", wynd_cluster.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("wisdom_rune", wisdom_rune.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("glamoured_aerogel_chip", glamoured_aerogel_chip.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));

		registerItem("ring_of_strength", ring_of_strength.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("gruegar_ring", gruegar_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("arkenium_ring", arkenium_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("swift_ribbon", swift_ribbon.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("wynd_cluster_ring", wynd_cluster_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("lesser_ring_of_wisdom", lesser_ring_of_wisdom.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("ring_of_wisdom", ring_of_wisdom.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));

		registerItem("bone_shard", bone_shard.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("holystone_chip", holystone_chip.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("skyroot_fragment", skyroot_fragment.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("scatterglass_shard", scatterglass_shard.setCreativeTab(CreativeTabsAether.MATERIALS));

		registerItem("irradiated_chunk", irradiated_chunk.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("irradiated_sword", irradiated_sword.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("irradiated_armor", irradiated_armor.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("irradiated_tool", irradiated_tool.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("irradiated_ring", irradiated_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("irradiated_neckwear", irradiated_neckwear.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("irradiated_charm", irradiated_charm.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("irradiated_dust", irradiated_dust.setCreativeTab(CreativeTabsAether.MATERIALS));

		registerItem("glamoured_cockatrice_keratin", glamoured_cockatrice_keratin.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));

		registerItem("sentry_vaultbox", sentry_vaultbox.setCreativeTab(CreativeTabsAether.MISCELLANEOUS));
		registerItem("wrapping_paper", wrapping_paper.setCreativeTab(CreativeTabsAether.MISCELLANEOUS));

		registerItem("fried_moa_egg", fried_moa_egg.setCreativeTab(CreativeTabsAether.CONSUMABLES));

		registerItem("aether_spawn_egg", aether_spawn_egg.setCreativeTab(CreativeTabsAether.MISCELLANEOUS));

		final TemperatureHandler temperatureHandler = new TemperatureHandler();

		AetherAPI.temperature().register(ItemsAether.irradiated_dust, temperatureHandler);
		AetherAPI.temperature().register(ItemsAether.ambrosium_chunk, temperatureHandler);

		AetherAPI.temperature().register(ItemsAether.moa_egg, temperatureHandler);
		AetherAPI.temperature().register(ItemsAether.rainbow_moa_egg, temperatureHandler);

		AetherAPI.temperature().register(ItemsAether.icestone, temperatureHandler);
		AetherAPI.temperature().register(ItemsAether.irradiated_chunk, temperatureHandler);
		AetherAPI.temperature().register(ItemsAether.irradiated_sword, temperatureHandler);
		AetherAPI.temperature().register(ItemsAether.irradiated_armor, temperatureHandler);
		AetherAPI.temperature().register(ItemsAether.irradiated_tool, temperatureHandler);
		AetherAPI.temperature().register(ItemsAether.irradiated_ring, temperatureHandler);
		AetherAPI.temperature().register(ItemsAether.irradiated_neckwear, temperatureHandler);
		AetherAPI.temperature().register(ItemsAether.irradiated_charm, temperatureHandler);

		AetherAPI.equipment().register(ItemsAether.neptune_helmet, ItemRarity.COMMON, null);
		AetherAPI.equipment().register(ItemsAether.neptune_chestplate, ItemRarity.COMMON, null);
		AetherAPI.equipment().register(ItemsAether.neptune_leggings, ItemRarity.COMMON, null);
		AetherAPI.equipment().register(ItemsAether.neptune_boots, ItemRarity.COMMON, null);
		AetherAPI.equipment().register(ItemsAether.neptune_gloves, ItemRarity.COMMON, ItemEquipmentType.HANDWEAR);

		AetherAPI.equipment().register(ItemsAether.valkyrie_helmet, ItemRarity.RARE, null);
		AetherAPI.equipment().register(ItemsAether.valkyrie_chestplate, ItemRarity.RARE, null);
		AetherAPI.equipment().register(ItemsAether.valkyrie_leggings, ItemRarity.RARE, null);
		AetherAPI.equipment().register(ItemsAether.valkyrie_boots, ItemRarity.RARE, null);
		AetherAPI.equipment().register(ItemsAether.valkyrie_gloves, ItemRarity.RARE, ItemEquipmentType.HANDWEAR);

		AetherAPI.equipment().register(ItemsAether.phoenix_helmet, ItemRarity.EPIC, null);
		AetherAPI.equipment().register(ItemsAether.phoenix_chestplate, ItemRarity.EPIC, null);
		AetherAPI.equipment().register(ItemsAether.phoenix_leggings, ItemRarity.EPIC, null);
		AetherAPI.equipment().register(ItemsAether.phoenix_boots, ItemRarity.EPIC, null);
		AetherAPI.equipment().register(ItemsAether.phoenix_gloves, ItemRarity.EPIC, ItemEquipmentType.HANDWEAR);

		AetherAPI.equipment().register(ItemsAether.iron_ring, ItemRarity.NONE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.gold_ring, ItemRarity.NONE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.zanite_ring, ItemRarity.NONE, ItemEquipmentType.RING);

		AetherAPI.equipment().register(ItemsAether.zanite_gloves, ItemRarity.NONE, ItemEquipmentType.HANDWEAR);
		AetherAPI.equipment().register(ItemsAether.gravitite_gloves, ItemRarity.NONE, ItemEquipmentType.HANDWEAR);
		AetherAPI.equipment().register(ItemsAether.leather_gloves, ItemRarity.NONE, ItemEquipmentType.HANDWEAR);
		AetherAPI.equipment().register(ItemsAether.iron_gloves, ItemRarity.NONE, ItemEquipmentType.HANDWEAR);
		AetherAPI.equipment().register(ItemsAether.gold_gloves, ItemRarity.NONE, ItemEquipmentType.HANDWEAR);
		AetherAPI.equipment().register(ItemsAether.chain_gloves, ItemRarity.NONE, ItemEquipmentType.HANDWEAR);
		AetherAPI.equipment().register(ItemsAether.diamond_gloves, ItemRarity.NONE, ItemEquipmentType.HANDWEAR);

		/** ARTIFACTS **/

		AetherAPI.equipment().register(ItemsAether.gravitite_core, ItemRarity.MYTHIC, ItemEquipmentType.ARTIFACT);
		AetherAPI.equipment().register(ItemsAether.valkyrie_wings, ItemRarity.MYTHIC, ItemEquipmentType.ARTIFACT);

		/** RELICS **/

		AetherAPI.equipment().register(ItemsAether.iron_bubble, ItemRarity.COMMON, ItemEquipmentType.RELIC);

		AetherAPI.equipment().register(ItemsAether.sunlit_tome, ItemRarity.RARE, ItemEquipmentType.RELIC);
		AetherAPI.equipment().register(ItemsAether.moonlit_tome, ItemRarity.RARE, ItemEquipmentType.RELIC);
		AetherAPI.equipment().register(ItemsAether.primal_totem_of_survival, ItemRarity.RARE, ItemEquipmentType.RELIC);
		AetherAPI.equipment().register(ItemsAether.primal_totem_of_rage, ItemRarity.RARE, ItemEquipmentType.RELIC);
		AetherAPI.equipment().register(ItemsAether.divine_beacon, ItemRarity.RARE, ItemEquipmentType.RELIC);
		AetherAPI.equipment().register(ItemsAether.regeneration_stone, ItemRarity.RARE, ItemEquipmentType.RELIC);

		AetherAPI.equipment().register(ItemsAether.phoenix_rune, ItemRarity.EPIC, ItemEquipmentType.RELIC);
		AetherAPI.equipment().register(ItemsAether.daggerfrost_rune, ItemRarity.EPIC, ItemEquipmentType.RELIC);
		AetherAPI.equipment().register(ItemsAether.sunlit_scroll, ItemRarity.EPIC, ItemEquipmentType.RELIC);
		AetherAPI.equipment().register(ItemsAether.moonlit_scroll, ItemRarity.EPIC, ItemEquipmentType.RELIC);

		/** NECKWEAR **/

		AetherAPI.equipment().register(ItemsAether.iron_pendant, ItemRarity.NONE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.gold_pendant, ItemRarity.NONE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.zanite_pendant, ItemRarity.NONE, ItemEquipmentType.NECKWEAR);

		AetherAPI.equipment().register(ItemsAether.zanite_studded_choker, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.lesser_amulet_of_growth, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.hide_gorget, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.fleeting_scarf, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);

		AetherAPI.equipment().register(ItemsAether.ice_pendant, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.muggers_cloak, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.amulet_of_growth, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.arkenium_studded_choker, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.raegorite_gorget, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.gruegar_scarf, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.winged_necklace, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.gust_amulet, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.typhoon_amulet, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.chain_of_sporing_bones, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.molten_amulet, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.granite_studded_choker, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.bandit_shawl, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);

		AetherAPI.equipment().register(ItemsAether.moon_sect_warden_gorget, ItemRarity.EPIC, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.thiefs_gorget, ItemRarity.EPIC, ItemEquipmentType.NECKWEAR);

		AetherAPI.equipment().register(ItemsAether.frostward_scarf, ItemRarity.MYTHIC, ItemEquipmentType.NECKWEAR);

		/** COMPANIONS **/

		AetherAPI.equipment().register(ItemsAether.pink_baby_swet, ItemRarity.COMMON, ItemEquipmentType.COMPANION);
		AetherAPI.equipment().register(ItemsAether.kraisith_capsule, ItemRarity.COMMON, ItemEquipmentType.COMPANION);
		AetherAPI.equipment().register(ItemsAether.fangrin_capsule, ItemRarity.COMMON, ItemEquipmentType.COMPANION);

		AetherAPI.equipment().register(ItemsAether.orb_of_arkenzus, ItemRarity.RARE, ItemEquipmentType.COMPANION);
		AetherAPI.equipment().register(ItemsAether.ethereal_stone, ItemRarity.RARE, ItemEquipmentType.COMPANION);
		AetherAPI.equipment().register(ItemsAether.fleeting_stone, ItemRarity.RARE, ItemEquipmentType.COMPANION);
		AetherAPI.equipment().register(ItemsAether.soaring_stone, ItemRarity.RARE, ItemEquipmentType.COMPANION);
		AetherAPI.equipment().register(ItemsAether.frostpine_totem, ItemRarity.RARE, ItemEquipmentType.COMPANION);

		AetherAPI.equipment().register(ItemsAether.death_seal, ItemRarity.EPIC, ItemEquipmentType.COMPANION);

		/** RINGS **/
		AetherAPI.equipment().register(ItemsAether.barbed_iron_ring, ItemRarity.COMMON, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.fleeting_ring, ItemRarity.COMMON, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.lesser_ring_of_growth, ItemRarity.COMMON, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.ring_of_strength, ItemRarity.COMMON, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.swift_ribbon, ItemRarity.COMMON, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.lesser_ring_of_wisdom, ItemRarity.COMMON, ItemEquipmentType.RING);

		AetherAPI.equipment().register(ItemsAether.bone_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.ice_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.granite_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.gust_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.typhoon_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.sporing_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.ember_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.ring_of_growth, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.barbed_gold_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.winged_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.gruegar_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.wynd_cluster_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.ring_of_wisdom, ItemRarity.RARE, ItemEquipmentType.RING);

		AetherAPI.equipment().register(ItemsAether.solar_band, ItemRarity.EPIC, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.lunar_band, ItemRarity.EPIC, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.skyroot_ring, ItemRarity.EPIC, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.candy_ring, ItemRarity.EPIC, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.dust_ring, ItemRarity.EPIC, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.mud_ring, ItemRarity.EPIC, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.storm_ring, ItemRarity.EPIC, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.steam_ring, ItemRarity.EPIC, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.arkenium_ring, ItemRarity.EPIC, ItemEquipmentType.RING);

		AetherAPI.equipment().register(ItemsAether.plague_coil, ItemRarity.MYTHIC, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.life_coil, ItemRarity.MYTHIC, ItemEquipmentType.RING);

		/** CHARMS **/
		AetherAPI.equipment().register(ItemsAether.glamoured_iron_screw, ItemRarity.COMMON, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.white_moa_feather, ItemRarity.COMMON, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.hide_pouch, ItemRarity.COMMON, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.angel_bandage, ItemRarity.COMMON, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.wisdom_bauble, ItemRarity.COMMON, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.swift_rune, ItemRarity.COMMON, ItemEquipmentType.CHARM);

		AetherAPI.equipment().register(ItemsAether.wisdom_rune, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_bone_shard, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_holystone_chip, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_zephyr_husk, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_ice_shard, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_blue_swet_jelly, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_cockatrice_talons, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_coal_ember, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.moa_feather, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.blight_ward, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_skyroot_twig, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_gold_screw, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.ambrosium_talisman, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.gruegar_pouch, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.soul_shard, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.wynd_cluster, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_cockatrice_keratin, ItemRarity.RARE, ItemEquipmentType.CHARM);

		AetherAPI.equipment().register(ItemsAether.glamoured_taegore_tusk, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_cockatrice_heart, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.damaged_moa_feather, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.sakura_moa_feather, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.osseous_bane, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.rot_bane, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.butchers_knife, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_aerogel_chip, ItemRarity.EPIC, ItemEquipmentType.CHARM);

		/** OFF-HAND **/

		AetherAPI.equipment().register(Items.SHIELD, ItemRarity.NONE, ItemEquipmentType.OFFHAND);
		AetherAPI.equipment().register(ItemsAether.skyroot_shield, ItemRarity.NONE, ItemEquipmentType.OFFHAND);
		AetherAPI.equipment().register(ItemsAether.holystone_shield, ItemRarity.NONE, ItemEquipmentType.OFFHAND);
		AetherAPI.equipment().register(ItemsAether.zanite_shield, ItemRarity.NONE, ItemEquipmentType.OFFHAND);
		AetherAPI.equipment().register(ItemsAether.arkenium_shield, ItemRarity.NONE, ItemEquipmentType.OFFHAND);
		AetherAPI.equipment().register(ItemsAether.gravitite_shield, ItemRarity.NONE, ItemEquipmentType.OFFHAND);

		AetherAPI.equipment().register(ItemsAether.bolt, ItemRarity.NONE, ItemEquipmentType.OFFHAND);
		AetherAPI.equipment().register(ItemsAether.dart, ItemRarity.NONE, ItemEquipmentType.OFFHAND);

		class Effects implements ItemEffects.ItemEffectsProvider
		{

			private List<Pair<EntityEffectProcessor, EntityEffectInstance>> pairs = Lists.newArrayList();

			public Effects()
			{

			}

			public <I extends EntityEffectInstance> Effects add(EntityEffectProcessor<I> processor, I instance)
			{
				Pair<EntityEffectProcessor, EntityEffectInstance> effectPair = Pair.of((EntityEffectProcessor) processor, instance);

				this.pairs.add(effectPair);

				return this;
			}

			public Effects add(EntityEffectProcessor<EntityEffectInstance> processor)
			{
				return this.add(processor, new EntityEffectInstance());
			}

			@Override
			public List<Pair<EntityEffectProcessor, EntityEffectInstance>> provide()
			{
				List<Pair<EntityEffectProcessor, EntityEffectInstance>> clone = Lists.newArrayList();

				for (Pair<EntityEffectProcessor, EntityEffectInstance> pair : this.pairs)
				{
					clone.add(Pair.of(pair.getLeft(), pair.getRight().cloneInstance()));
				}

				return clone;
			}

		}

		/** ARMOR **/

		ItemEffects.register(ItemsAether.gravitite_helmet, new Effects()
				.add(EntityEffects.LEVITATE_ATTACKERS, new LevitateAttackersEffect.Instance(0.01D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(ItemsAether.gravitite_chestplate, new Effects()
				.add(EntityEffects.LEVITATE_ATTACKERS, new LevitateAttackersEffect.Instance(0.05D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(15D)));

		ItemEffects.register(ItemsAether.gravitite_leggings, new Effects()
				.add(EntityEffects.LEVITATE_ATTACKERS, new LevitateAttackersEffect.Instance(0.02D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(7.5D)));

		ItemEffects.register(ItemsAether.gravitite_boots, new Effects()
				.add(EntityEffects.LEVITATE_ATTACKERS, new LevitateAttackersEffect.Instance(0.01D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.gravitite_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 2.0D))
				.add(EntityEffects.LEVITATE_ATTACKERS, new LevitateAttackersEffect.Instance(0.01D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.gravitite_shield, new Effects()
				.add(EntityEffects.LEVITATE_ATTACKERS, new LevitateAttackersEffect.Instance(0.02D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(10D)));

		ItemEffects.register(ItemsAether.phoenix_helmet, new Effects()
				.add(EntityEffects.SET_ATTACKERS_ON_FIRE, new SetAttackersOnFireEffect.Instance(0.01D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(ItemsAether.phoenix_chestplate, new Effects()
				.add(EntityEffects.SET_ATTACKERS_ON_FIRE, new SetAttackersOnFireEffect.Instance(0.05D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(15D)));

		ItemEffects.register(ItemsAether.phoenix_leggings, new Effects()
				.add(EntityEffects.SET_ATTACKERS_ON_FIRE, new SetAttackersOnFireEffect.Instance(0.02D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(7.5D)));

		ItemEffects.register(ItemsAether.phoenix_boots, new Effects()
				.add(EntityEffects.SET_ATTACKERS_ON_FIRE, new SetAttackersOnFireEffect.Instance(0.01D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.phoenix_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.FIRE, 2.0D))
				.add(EntityEffects.SET_ATTACKERS_ON_FIRE, new SetAttackersOnFireEffect.Instance(0.01D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.neptune_helmet, new Effects()
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.WATER, 0.25D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.neptune_chestplate, new Effects()
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.WATER, 0.75D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(ItemsAether.neptune_leggings, new Effects()
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.WATER, 0.5D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.neptune_boots, new Effects()
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.WATER, 0.25D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.neptune_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.WATER, 2.0D))
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.WATER, 0.25D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.zanite_helmet, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.zanite_chestplate, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(ItemsAether.zanite_leggings, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.zanite_boots, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.zanite_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 1.5D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.zanite_shield, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(3.5D)));

		ItemEffects.register(ItemsAether.valkyrie_helmet, new Effects()
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.AIR, 0.25D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.valkyrie_chestplate, new Effects()
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.AIR, 0.75D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(ItemsAether.valkyrie_leggings, new Effects()
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.AIR, 0.5D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.valkyrie_boots, new Effects()
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.AIR, 0.25D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.valkyrie_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.AIR, 2.5D))
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.AIR, 0.25D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(Items.LEATHER_HELMET, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(1D)));

		ItemEffects.register(Items.LEATHER_CHESTPLATE, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(3D)));

		ItemEffects.register(Items.LEATHER_LEGGINGS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2D)));

		ItemEffects.register(Items.LEATHER_BOOTS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(1D)));

		ItemEffects.register(ItemsAether.leather_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 0.5D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(1D)));

		ItemEffects.register(Items.IRON_HELMET, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(Items.IRON_CHESTPLATE, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(Items.IRON_LEGGINGS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(Items.IRON_BOOTS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.iron_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 1.0D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(Items.DIAMOND_HELMET, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(Items.DIAMOND_CHESTPLATE, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(15D)));

		ItemEffects.register(Items.DIAMOND_LEGGINGS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(Items.DIAMOND_BOOTS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(ItemsAether.diamond_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 1.5D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(Items.GOLDEN_HELMET, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(1D)));

		ItemEffects.register(Items.GOLDEN_CHESTPLATE, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(3D)));

		ItemEffects.register(Items.GOLDEN_LEGGINGS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2D)));

		ItemEffects.register(Items.GOLDEN_BOOTS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(1D)));

		ItemEffects.register(ItemsAether.gold_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 0.5D, 1.0D, true))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(1D)));

		ItemEffects.register(Items.CHAINMAIL_HELMET, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(1.5D)));

		ItemEffects.register(Items.CHAINMAIL_CHESTPLATE, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(3D)));

		ItemEffects.register(Items.CHAINMAIL_LEGGINGS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2D)));

		ItemEffects.register(Items.CHAINMAIL_BOOTS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(1.5D)));

		ItemEffects.register(ItemsAether.chain_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 1.0D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(1.5D)));

		ItemEffects.register(Items.SHIELD, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(3.5D)));

		ItemEffects.register(ItemsAether.skyroot_shield, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.holystone_shield, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(3D)));

		ItemEffects.register(ItemsAether.arkenium_shield, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(15D)));

		/** OTHERS **/

		ItemEffects.register(ItemsAether.iron_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 0.5D)));
		ItemEffects.register(ItemsAether.gold_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 0.0D, 1.0D, false)));
		ItemEffects.register(ItemsAether.iron_pendant, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 3.0D)));
		ItemEffects.register(ItemsAether.gold_pendant, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 2.0D, 4.0D, false)));
		ItemEffects.register(ItemsAether.zanite_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 0.5D, 1.0D, true)));
		ItemEffects.register(ItemsAether.zanite_pendant, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 3.0D, 4.0D, false)));
		ItemEffects.register(ItemsAether.iron_bubble, new Effects().add(EntityEffects.BREATHE_UNDERWATER));
		ItemEffects.register(ItemsAether.regeneration_stone, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new OutOfCombatRule(160))));
		ItemEffects.register(ItemsAether.ice_ring, new Effects().add(EntityEffects.FREEZE_BLOCKS, new FreezeBlocksEffect.Instance(2)).add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.FROST, 2.0D)));
		ItemEffects.register(ItemsAether.ice_pendant, new Effects().add(EntityEffects.FREEZE_BLOCKS, new FreezeBlocksEffect.Instance(2)).add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.FROST, 5.0D)));
		ItemEffects.register(ItemsAether.daggerfrost_rune, new Effects().add(EntityEffects.DAGGERFROST, new EntityEffectInstance()));
		ItemEffects.register(ItemsAether.candy_ring, new Effects().add(EntityEffects.REDUCE_HUNGER));
		ItemEffects.register(ItemsAether.bone_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 2.0D)));
		ItemEffects.register(ItemsAether.skyroot_ring, new Effects().add(EntityEffects.DOUBLE_DROPS, new DoubleDropEffect.Instance(1.5D)));

		ItemEffects.register(ItemsAether.barbed_iron_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 1.0D)));
		ItemEffects.register(ItemsAether.barbed_gold_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 1.0D, 3.0D, false)));
		ItemEffects.register(ItemsAether.solar_band, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new InDirectSunlightRule())));
		ItemEffects.register(ItemsAether.lunar_band, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new InDirectMoonlightRule())));
		ItemEffects.register(ItemsAether.ring_of_growth, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(2.0D)));
		ItemEffects.register(ItemsAether.plague_coil, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 2.0D, 6.0D, false)).add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(-2.0D)));
		ItemEffects.register(ItemsAether.fleeting_ring, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.10D)));
		ItemEffects.register(ItemsAether.lesser_ring_of_growth, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(1.0D)));
		ItemEffects.register(ItemsAether.winged_ring, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.20D)));
		ItemEffects.register(ItemsAether.life_coil, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(4.0D)).add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, -4.0D)));
		ItemEffects.register(ItemsAether.glamoured_iron_screw, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 0.2D)));
		ItemEffects.register(ItemsAether.wisdom_bauble, new Effects().add(EntityEffects.MODIFY_XP_COLLECTION, new ModifyXPCollectionEffect.Instance(0.10D)));
		ItemEffects.register(ItemsAether.glamoured_bone_shard, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 0.3D)));
		ItemEffects.register(ItemsAether.moa_feather, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.05D)));
		ItemEffects.register(ItemsAether.blight_ward, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 1D, new DamagingElementRule(ElementalState.BLIGHT))));
		ItemEffects.register(ItemsAether.glamoured_skyroot_twig);
		ItemEffects.register(ItemsAether.glamoured_gold_screw, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 0.1D, 0.5D, true)));
		ItemEffects.register(ItemsAether.ambrosium_talisman, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new HoldingItemRule(new ItemStack(ItemsAether.ambrosium_shard)))));
		ItemEffects.register(ItemsAether.sunlit_scroll, new Effects().add(EntityEffects.PAUSE_HUNGER, new EntityEffectInstance(new InDirectSunlightRule())));
		ItemEffects.register(ItemsAether.moonlit_scroll, new Effects().add(EntityEffects.PAUSE_HUNGER, new EntityEffectInstance(new InDirectMoonlightRule())));
		ItemEffects.register(ItemsAether.glamoured_cockatrice_heart, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new WhenPoisonedRule())));
		ItemEffects.register(ItemsAether.damaged_moa_feather, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.075D, new InCombatRule())));
		ItemEffects.register(ItemsAether.white_moa_feather, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.03D)));
		ItemEffects.register(ItemsAether.sakura_moa_feather, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.10D, new OutOfCombatRule(160))));
		ItemEffects.register(ItemsAether.osseous_bane, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 2.0D, new DamagingMobRule(EntitySkeleton.class, "Skeleton"))));
		ItemEffects.register(ItemsAether.rot_bane, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 1.0D, new DamagingUndeadRule())));
		ItemEffects.register(ItemsAether.butchers_knife, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 2.0D, new DamagingPassiveAnimalsRule())));

		ItemEffects.register(ItemsAether.zanite_studded_choker, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 4.0D)));
		ItemEffects.register(ItemsAether.lesser_amulet_of_growth, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(4.0D)));
		ItemEffects.register(ItemsAether.hide_gorget, new Effects().add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(1.0D)));
		ItemEffects.register(ItemsAether.amulet_of_growth, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(6.0D)));
		ItemEffects.register(ItemsAether.arkenium_studded_choker, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 5.0D)));
		ItemEffects.register(ItemsAether.raegorite_gorget, new Effects().add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(2.0D)));
		ItemEffects.register(ItemsAether.gruegar_scarf, new Effects().add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(-15D)));
		ItemEffects.register(ItemsAether.moon_sect_warden_gorget, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(3.0D)).add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(1.0D)));
		ItemEffects.register(ItemsAether.thiefs_gorget, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.3D)).add(EntityEffects.INVISIBILITY, new EntityEffectInstance(new OutOfCombatRule(160))));
		ItemEffects.register(ItemsAether.frostward_scarf, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.4D)).add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.FROST, 1.0D)));

		ItemEffects.register(ItemsAether.glamoured_holystone_chip, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.EARTH, 0.3D)));
		ItemEffects.register(ItemsAether.glamoured_zephyr_husk, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.AIR, 0.3D)));
		ItemEffects.register(ItemsAether.glamoured_ice_shard, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.FROST, 0.3D)));
		ItemEffects.register(ItemsAether.glamoured_blue_swet_jelly, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.WATER, 0.3D)));
		ItemEffects.register(ItemsAether.glamoured_cockatrice_talons, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BLIGHT, 0.3D)));
		ItemEffects.register(ItemsAether.glamoured_coal_ember, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.FIRE, 0.3D)));

		ItemEffects.register(ItemsAether.granite_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.EARTH, 2.0D)));
		ItemEffects.register(ItemsAether.gust_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.AIR, 2.0D)));
		ItemEffects.register(ItemsAether.typhoon_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.WATER, 2.0D)));
		ItemEffects.register(ItemsAether.sporing_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BLIGHT, 2.0D)));
		ItemEffects.register(ItemsAether.ember_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.FIRE, 2.0D)));
		ItemEffects.register(ItemsAether.dust_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.EARTH, 1.0D)).add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.AIR, 1.0D)));
		ItemEffects.register(ItemsAether.mud_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.EARTH, 1.0D)).add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.WATER, 1.0D)));
		ItemEffects.register(ItemsAether.storm_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.AIR, 1.0D)).add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.WATER, 1.0D)));
		ItemEffects.register(ItemsAether.steam_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.FIRE, 1.0D)).add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.WATER, 1.0D)));

		ItemEffects.register(ItemsAether.sunlit_tome, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new InDirectSunlightRule())));
		ItemEffects.register(ItemsAether.moonlit_tome, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new InDirectMoonlightRule())));
		ItemEffects.register(ItemsAether.phoenix_rune, new Effects().add(EntityEffects.CHANGE_ATTACK_ELEMENT, new ChangeAttackElementEffect.Instance(ElementalState.FIRE)).add(EntityEffects.FIRE_IMMUNITY));
		ItemEffects.register(ItemsAether.glamoured_taegore_tusk, new Effects().add(EntityEffects.LEECH_LIFE, new LeechLifeEffect.Instance(0.3D)));
		ItemEffects.register(ItemsAether.primal_totem_of_survival, new Effects().add(EntityEffects.MODIFY_COMPANION_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(20.0D)));
		ItemEffects.register(ItemsAether.primal_totem_of_rage, new Effects().add(EntityEffects.MODIFY_COMPANION_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 2.0D)));

		ItemEffects.register(ItemsAether.gravitite_core, new Effects().add(EntityEffects.EXTRA_JUMPS));
		ItemEffects.register(ItemsAether.valkyrie_wings, new Effects().add(EntityEffects.SLOWFALL));

		ItemEffects.register(ItemsAether.fleeting_scarf, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.2D)));
		ItemEffects.register(ItemsAether.winged_necklace, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.4D)));
		ItemEffects.register(ItemsAether.gust_amulet, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.AIR, 5.0D)));
		ItemEffects.register(ItemsAether.typhoon_amulet, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.WATER, 5.0D)));
		ItemEffects.register(ItemsAether.chain_of_sporing_bones, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BLIGHT, 5.0D)));
		ItemEffects.register(ItemsAether.molten_amulet, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.FIRE, 5.0D)));
		ItemEffects.register(ItemsAether.granite_studded_choker, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.EARTH, 5.0D)));
		ItemEffects.register(ItemsAether.muggers_cloak, new Effects().add(EntityEffects.MODIFY_ATTACK_SPEED, new ModifyAttackSpeedEffect.Instance((float) SharedMonsterAttributes.ATTACK_SPEED.getDefaultValue() * 0.2D)));
		ItemEffects.register(ItemsAether.bandit_shawl, new Effects().add(EntityEffects.MODIFY_ATTACK_SPEED, new ModifyAttackSpeedEffect.Instance((float) SharedMonsterAttributes.ATTACK_SPEED.getDefaultValue() * 0.1D)));

		ItemEffects.register(ItemsAether.hide_pouch, new Effects().add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(-3.0D)));
		ItemEffects.register(ItemsAether.gruegar_pouch, new Effects().add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(-5.0D)));
		ItemEffects.register(ItemsAether.soul_shard, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(1.0D)));
		ItemEffects.register(ItemsAether.angel_bandage, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(0.5D)));
		ItemEffects.register(ItemsAether.swift_rune, new Effects().add(EntityEffects.MODIFY_ATTACK_SPEED, new ModifyAttackSpeedEffect.Instance((float) SharedMonsterAttributes.ATTACK_SPEED.getDefaultValue() * 0.01D)));
		ItemEffects.register(ItemsAether.wynd_cluster, new Effects().add(EntityEffects.MODIFY_ATTACK_SPEED, new ModifyAttackSpeedEffect.Instance((float) SharedMonsterAttributes.ATTACK_SPEED.getDefaultValue() * 0.02D)));
		ItemEffects.register(ItemsAether.wisdom_rune, new Effects().add(EntityEffects.MODIFY_XP_COLLECTION, new ModifyXPCollectionEffect.Instance(0.20D)));
		ItemEffects.register(ItemsAether.glamoured_aerogel_chip, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.AIR, 0.1D)).add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.FIRE, 0.2D)));

		ItemEffects.register(ItemsAether.gruegar_ring, new Effects().add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(-10.0D)));
		ItemEffects.register(ItemsAether.ring_of_strength, new Effects().add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(-7.5D)));
		ItemEffects.register(ItemsAether.arkenium_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 3.0D)).add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(10.0D)));
		ItemEffects.register(ItemsAether.swift_ribbon, new Effects().add(EntityEffects.MODIFY_ATTACK_SPEED, new ModifyAttackSpeedEffect.Instance((float) SharedMonsterAttributes.ATTACK_SPEED.getDefaultValue() * 0.05D)));
		ItemEffects.register(ItemsAether.wynd_cluster_ring, new Effects().add(EntityEffects.MODIFY_ATTACK_SPEED, new ModifyAttackSpeedEffect.Instance((float) SharedMonsterAttributes.ATTACK_SPEED.getDefaultValue() * 0.1D)));
		ItemEffects.register(ItemsAether.lesser_ring_of_wisdom, new Effects().add(EntityEffects.MODIFY_XP_COLLECTION, new ModifyXPCollectionEffect.Instance(0.20D)));
		ItemEffects.register(ItemsAether.ring_of_wisdom, new Effects().add(EntityEffects.MODIFY_XP_COLLECTION, new ModifyXPCollectionEffect.Instance(0.40D)));

		ItemEffects.register(ItemsAether.glamoured_cockatrice_keratin, new Effects().add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.BLIGHT, 0.5D)));

		registerItemProperties();
	}

	private static void registerItemProperties()
	{
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER,
				new ItemStack(ItemsAether.skyroot_water_bucket), new ItemStack(ItemsAether.skyroot_bucket));
	}

	private static <T extends Item> T registerItem(String name, T item)
	{
		item.setUnlocalizedName(AetherCore.MOD_ID + "." + name);
		item.setRegistryName(name);

		GameRegistry.register(item);

		registeredItems.add(item);

		return item;
	}

	public static Collection<Item> getRegisteredItems()
	{
		return Collections.unmodifiableCollection(registeredItems);
	}
}
