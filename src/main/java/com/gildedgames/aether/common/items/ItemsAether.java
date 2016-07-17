package com.gildedgames.aether.common.items;

import com.gildedgames.aether.api.entities.effects.EntityEffectInstance;
import com.gildedgames.aether.api.entities.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.items.properties.ItemEquipmentType;
import com.gildedgames.aether.api.items.properties.ItemRarity;
import com.gildedgames.aether.api.registry.equipment.IEquipmentRegistry;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.MaterialsAether;
import com.gildedgames.aether.common.SoundsAether;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.effects.EntityEffects;
import com.gildedgames.aether.common.entities.effects.processors.DoubleDropEffect;
import com.gildedgames.aether.common.entities.effects.processors.FreezeBlocksEffect;
import com.gildedgames.aether.common.entities.effects.processors.ModifyDamageEffect;
import com.gildedgames.aether.common.entities.effects.processors.RegenerateHealthEffect;
import com.gildedgames.aether.common.entities.effects.rules.OutOfCombatRule;
import com.gildedgames.aether.common.items.armor.ItemAetherGloves;
import com.gildedgames.aether.common.items.armor.ItemAetherShield;
import com.gildedgames.aether.common.items.armor.ItemGravititeArmor;
import com.gildedgames.aether.common.items.armor.ItemLeatherGloves;
import com.gildedgames.aether.common.items.armor.ItemNeptuneArmor;
import com.gildedgames.aether.common.items.armor.ItemObsidianArmor;
import com.gildedgames.aether.common.items.armor.ItemPhoenixArmor;
import com.gildedgames.aether.common.items.armor.ItemSentryBoots;
import com.gildedgames.aether.common.items.armor.ItemValkyrieArmor;
import com.gildedgames.aether.common.items.armor.ItemZaniteArmor;
import com.gildedgames.aether.common.items.companions.ItemPinkBabySwet;
import com.gildedgames.aether.common.items.consumables.*;
import com.gildedgames.aether.common.items.effects.ItemEffects;
import com.gildedgames.aether.common.items.miscellaneous.ItemShardOfLife;
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
import com.gildedgames.aether.common.items.weapons.ItemPigSlayer;
import com.gildedgames.aether.common.items.weapons.ItemVampireBlade;
import com.gildedgames.aether.common.items.weapons.crossbow.*;
import com.gildedgames.aether.common.items.weapons.swords.ItemAetherSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemArkeniumSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemCandyCaneSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemElementalSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemGravititeSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemHolystoneSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemSkyrootSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemZaniteSword;
import com.google.common.collect.Lists;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class ItemsAether
{
	public static Item skyroot_stick;

	public static Item ambrosium_shard, zanite_gemstone, arkenium;

	public static ItemSkyrootTool skyroot_axe, skyroot_pickaxe, skyroot_shovel;

	public static ItemHolystoneTool holystone_axe, holystone_pickaxe, holystone_shovel;

	public static ItemZaniteTool zanite_axe, zanite_pickaxe, zanite_shovel;

	public static ItemGravititeTool gravitite_axe, gravitite_pickaxe, gravitite_shovel;

	public static ItemValkyrieTool valkyrie_axe, valkyrie_pickaxe, valkyrie_shovel;

	public static ItemArkeniumTool arkenium_axe, arkenium_pickaxe, arkenium_shovel;

	public static ItemAetherSword skyroot_sword, holystone_sword, zanite_sword, gravitite_sword, arkenium_sword, valkyrie_lance;

	public static ItemZaniteArmor zanite_helmet, zanite_chestplate, zanite_leggings, zanite_boots;

	public static ItemGravititeArmor gravitite_helmet, gravitite_chestplate, gravitite_leggings, gravitite_boots;

	public static ItemNeptuneArmor neptune_helmet, neptune_chestplate, neptune_leggings, neptune_boots;

	public static ItemPhoenixArmor phoenix_helmet, phoenix_chestplate, phoenix_leggings, phoenix_boots;

	public static ItemObsidianArmor obsidian_helmet, obsidian_chestplate, obsidian_leggings, obsidian_boots;

	public static ItemValkyrieArmor valkyrie_helmet, valkyrie_chestplate, valkyrie_leggings, valkyrie_boots;

	public static ItemSentryBoots sentry_boots;

	public static Item golden_amber;

	public static ItemFood blueberries, enchanted_blueberry, orange, wyndberry, enchanted_wyndberry, swet_jelly, gummy_swet;

	public static ItemFood candy_corn, cocoatrice, wrapped_chocolates, jelly_pumpkin, stomper_pop, blueberry_lollipop, orange_lollipop, icestone_poprocks;

	public static ItemFood ginger_bread_man, candy_cane;

	public static ItemSkyrootBucket skyroot_bucket, skyroot_water_bucket;

	public static ItemSkyrootConsumableBucket skyroot_milk_bucket, skyroot_poison_bucket;

	public static ItemAetherRecord valkyrie_music_disc, labyrinth_music_disc, moa_music_disc, aerwhale_music_disc, recording_892;

	public static ItemFood healing_stone;

	public static ItemDartShooter dart_shooter;

	public static ItemDart dart;

	public static ItemElementalSword flaming_sword, holy_sword, lightning_sword;

	public static ItemSword pig_slayer, vampire_blade, candy_cane_sword;

	public static ItemDoor skyroot_door;

	public static ItemSkyrootCrossbow skyroot_crossbow;

	public static ItemHolystoneCrossbow holystone_crossbow;

	public static ItemZaniteCrossbow zanite_crossbow;

	public static ItemArkeniumCrossbow arkenium_crossbow;

	public static ItemGravititeCrossbow gravitite_crossbow;

	public static ItemVampireCrossbow vampire_crossbow;

	public static ItemBolt bolt;

	public static Item iron_ring, gold_ring;

	public static Item zanite_ring, zanite_pendant;

	public static Item iron_pendant, gold_pendant;

	public static Item iron_bubble, regeneration_stone;

	public static Item ice_ring, ice_pendant;

	public static Item daggerfrost_locket;

	public static Item candy_ring, bone_ring, skyroot_ring;

	public static Item icestone, continuum_orb;

	public static Item skyroot_sign;
	
	public static Item aether_portal_frame;

	public static Item aechor_petal;

	public static Item zanite_gloves, gravitite_gloves, valkyrie_gloves, neptune_gloves, phoenix_gloves, obsidian_gloves;

	public static Item leather_gloves, iron_gloves, gold_gloves, chain_gloves, diamond_gloves;

    public static Item pink_baby_swet;

    public static Item shard_of_life;
    
	public static ItemAetherShield skyroot_shield;

	public static void preInit()
	{
		skyroot_stick = registerItem("skyroot_stick", new Item(), AetherCreativeTabs.tabMaterials);
		ambrosium_shard = registerItem("ambrosium_shard", new ItemAmbrosiumShard(), AetherCreativeTabs.tabMaterials);
		zanite_gemstone = registerItem("zanite_gemstone", new Item(), AetherCreativeTabs.tabMaterials);
		arkenium = registerItem("arkenium", new Item(), AetherCreativeTabs.tabMaterials);
		icestone = registerItem("icestone", new Item(), AetherCreativeTabs.tabMaterials);
		continuum_orb = registerItem("continuum_orb", new ItemContinuumOrb(), AetherCreativeTabs.tabMaterials);

		skyroot_axe = registerItem("skyroot_axe", new ItemSkyrootTool(EnumToolType.AXE, 6.0F, -3.2F));
		skyroot_pickaxe = registerItem("skyroot_pickaxe", new ItemSkyrootTool(EnumToolType.PICKAXE, 1.0F, -2.8F));
		skyroot_shovel = registerItem("skyroot_shovel", new ItemSkyrootTool(EnumToolType.SHOVEL, 1.5F, -3.0F));
		skyroot_sword = registerItem("skyroot_sword", new ItemSkyrootSword());
        skyroot_shield = registerItem("skyroot_shield", new ItemAetherShield(), AetherCreativeTabs.tabWeapons);

        holystone_axe = registerItem("holystone_axe", new ItemHolystoneTool(EnumToolType.AXE, 8.0F, -3.2F));
		holystone_pickaxe = registerItem("holystone_pickaxe", new ItemHolystoneTool(EnumToolType.PICKAXE, 1.0F, -2.8F));
		holystone_shovel = registerItem("holystone_shovel", new ItemHolystoneTool(EnumToolType.SHOVEL, 1.5F, -3.0F));
		holystone_sword = registerItem("holystone_sword", new ItemHolystoneSword());

		zanite_axe = registerItem("zanite_axe", new ItemZaniteTool(EnumToolType.AXE, 8.0F, -3.1F));
		zanite_pickaxe = registerItem("zanite_pickaxe", new ItemZaniteTool(EnumToolType.PICKAXE, 1.0F, -2.8F));
		zanite_shovel = registerItem("zanite_shovel", new ItemZaniteTool(EnumToolType.SHOVEL, 1.5F, -3.0F));
		zanite_sword = registerItem("zanite_sword", new ItemZaniteSword());

        arkenium_axe = registerItem("arkenium_axe", new ItemArkeniumTool(EnumToolType.AXE, 8.0F, -3.3F));
        arkenium_pickaxe = registerItem("arkenium_pickaxe", new ItemArkeniumTool(EnumToolType.PICKAXE, 4.0F, -3.2F));
        arkenium_shovel = registerItem("arkenium_shovel", new ItemArkeniumTool(EnumToolType.SHOVEL, 4.5F, -3.3F));
        arkenium_sword = registerItem("arkenium_sword", new ItemArkeniumSword());

		gravitite_axe = registerItem("gravitite_axe", new ItemGravititeTool(EnumToolType.AXE, 8.0F, -3.0F));
		gravitite_pickaxe = registerItem("gravitite_pickaxe", new ItemGravititeTool(EnumToolType.PICKAXE, 1.0F, -2.8F));
		gravitite_shovel = registerItem("gravitite_shovel", new ItemGravititeTool(EnumToolType.SHOVEL, 1.5F, -3.0F));
		gravitite_sword = registerItem("gravitite_sword", new ItemGravititeSword());

		valkyrie_axe = registerItem("valkyrie_axe", new ItemValkyrieTool(EnumToolType.AXE, 8.0F, -3.0F));
		valkyrie_pickaxe = registerItem("valkyrie_pickaxe", new ItemValkyrieTool(EnumToolType.PICKAXE, 1.0F, -2.8F));
		valkyrie_shovel = registerItem("valkyrie_shovel", new ItemValkyrieTool(EnumToolType.SHOVEL,  1.5F, -3.0F));
		valkyrie_lance = registerItem("valkyrie_lance", new ItemAetherSword(MaterialsAether.LEGENDARY_TOOL, ItemAbilityType.PASSIVE));

		zanite_helmet = registerItem("zanite_helmet", new ItemZaniteArmor(EntityEquipmentSlot.HEAD));
		zanite_chestplate = registerItem("zanite_chestplate", new ItemZaniteArmor(EntityEquipmentSlot.CHEST));
		zanite_leggings = registerItem("zanite_leggings", new ItemZaniteArmor(EntityEquipmentSlot.LEGS));
		zanite_boots = registerItem("zanite_boots", new ItemZaniteArmor(EntityEquipmentSlot.FEET));
        zanite_gloves = registerItem("zanite_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.ZANITE));

		gravitite_helmet = registerItem("gravitite_helmet", new ItemGravititeArmor(EntityEquipmentSlot.HEAD));
		gravitite_chestplate = registerItem("gravitite_chestplate", new ItemGravititeArmor(EntityEquipmentSlot.CHEST));
		gravitite_leggings = registerItem("gravitite_leggings", new ItemGravititeArmor(EntityEquipmentSlot.LEGS));
		gravitite_boots = registerItem("gravitite_boots", new ItemGravititeArmor(EntityEquipmentSlot.FEET));
        gravitite_gloves = registerItem("gravitite_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.GRAVITITE));

		neptune_helmet = registerItem("neptune_helmet", new ItemNeptuneArmor(EntityEquipmentSlot.HEAD));
		neptune_chestplate = registerItem("neptune_chestplate", new ItemNeptuneArmor(EntityEquipmentSlot.CHEST));
		neptune_leggings = registerItem("neptune_leggings", new ItemNeptuneArmor(EntityEquipmentSlot.LEGS));
		neptune_boots = registerItem("neptune_boots", new ItemNeptuneArmor(EntityEquipmentSlot.FEET));
        neptune_gloves = registerItem("neptune_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.NEPTUNE));

		phoenix_helmet = registerItem("phoenix_helmet", new ItemPhoenixArmor(EntityEquipmentSlot.HEAD));
		phoenix_chestplate = registerItem("phoenix_chestplate", new ItemPhoenixArmor(EntityEquipmentSlot.CHEST));
		phoenix_leggings = registerItem("phoenix_leggings", new ItemPhoenixArmor(EntityEquipmentSlot.LEGS));
		phoenix_boots = registerItem("phoenix_boots", new ItemPhoenixArmor(EntityEquipmentSlot.FEET));
        phoenix_gloves = registerItem("phoenix_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.PHOENIX));

		obsidian_helmet = registerItem("obsidian_helmet", new ItemObsidianArmor(EntityEquipmentSlot.HEAD));
		obsidian_chestplate = registerItem("obsidian_chestplate", new ItemObsidianArmor(EntityEquipmentSlot.CHEST));
		obsidian_leggings = registerItem("obsidian_leggings", new ItemObsidianArmor(EntityEquipmentSlot.LEGS));
		obsidian_boots = registerItem("obsidian_boots", new ItemObsidianArmor(EntityEquipmentSlot.FEET));
		obsidian_gloves = registerItem("obsidian_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.OBSIDIAN));

		valkyrie_helmet = registerItem("valkyrie_helmet", new ItemValkyrieArmor(EntityEquipmentSlot.HEAD));
		valkyrie_chestplate = registerItem("valkyrie_chestplate", new ItemValkyrieArmor(EntityEquipmentSlot.CHEST));
		valkyrie_leggings = registerItem("valkyrie_leggings", new ItemValkyrieArmor(EntityEquipmentSlot.LEGS));
		valkyrie_boots = registerItem("valkyrie_boots", new ItemValkyrieArmor(EntityEquipmentSlot.FEET));
        valkyrie_gloves = registerItem("valkyrie_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.VALKYRIE));

		sentry_boots = registerItem("sentry_boots", new ItemSentryBoots(MaterialsAether.LEGENDARY_ARMOR));

		golden_amber = registerItem("golden_amber", new Item(), AetherCreativeTabs.tabMaterials);

		aechor_petal = registerItem("aechor_petal", new Item(), AetherCreativeTabs.tabMiscellaneous);

		blueberries = registerItem("blueberries", new ItemFood(2, false), AetherCreativeTabs.tabConsumables);

		enchanted_blueberry = registerItem("enchanted_blueberry", new ItemFood(6, false), AetherCreativeTabs.tabConsumables);

		orange = registerItem("orange", new ItemFood(4, false), AetherCreativeTabs.tabConsumables);

		wyndberry = registerItem("wyndberry", new ItemFood(4, false), AetherCreativeTabs.tabConsumables);

		enchanted_wyndberry = registerItem("enchanted_wyndberry", new ItemEnchantedWyndberry(), AetherCreativeTabs.tabConsumables);

		swet_jelly = registerItem("swet_jelly", new ItemSwetJelly(), AetherCreativeTabs.tabConsumables);

		gummy_swet = registerItem("gummy_swet", new ItemGummySwet(), AetherCreativeTabs.tabConsumables);

		candy_corn = registerItem("candy_corn", new ItemFood(8, false), AetherCreativeTabs.tabConsumables);

		cocoatrice = registerItem("cocoatrice", new ItemFood(12, false), AetherCreativeTabs.tabConsumables);

		wrapped_chocolates = registerItem("wrapped_chocolates", new ItemFood(12, false), AetherCreativeTabs.tabConsumables);

		jelly_pumpkin = registerItem("jelly_pumpkin", new ItemFood(12, false), AetherCreativeTabs.tabConsumables);

		stomper_pop = registerItem("stomper_pop", new ItemStomperPop(), AetherCreativeTabs.tabConsumables);

		blueberry_lollipop = registerItem("blueberry_lollipop", new ItemFood(10, false), AetherCreativeTabs.tabConsumables);

		orange_lollipop = registerItem("orange_lollipop", new ItemFood(8, false), AetherCreativeTabs.tabConsumables);

		icestone_poprocks = registerItem("icestone_poprocks", new ItemFood(5, false), AetherCreativeTabs.tabConsumables);

		ginger_bread_man = registerItem("ginger_bread_man", new ItemFood(2, false), AetherCreativeTabs.tabConsumables);

		candy_cane = registerItem("candy_cane", new ItemFood(2, false), AetherCreativeTabs.tabConsumables);

		skyroot_bucket = registerItem("skyroot_bucket", new ItemSkyrootBucket(Blocks.AIR), AetherCreativeTabs.tabMiscellaneous);
		skyroot_water_bucket = registerItem("skyroot_water_bucket", new ItemSkyrootBucket(Blocks.FLOWING_WATER), AetherCreativeTabs.tabMiscellaneous);
		skyroot_milk_bucket = registerItem("skyroot_milk_bucket", new ItemSkyrootConsumableBucket(), AetherCreativeTabs.tabMiscellaneous);
		skyroot_poison_bucket = registerItem("skyroot_poison_bucket", new ItemSkyrootConsumableBucket(), AetherCreativeTabs.tabMiscellaneous);

		valkyrie_music_disc = registerItem("valkyrie_music_disc", new ItemAetherRecord("valkyrie", SoundsAether.record_valkyrie), AetherCreativeTabs.tabMiscellaneous);
		labyrinth_music_disc = registerItem("labyrinth_music_disc", new ItemAetherRecord("labyrinth", SoundsAether.record_labyrinth), AetherCreativeTabs.tabMiscellaneous);
		moa_music_disc = registerItem("moa_music_disc", new ItemAetherRecord("moa", SoundsAether.record_moa), AetherCreativeTabs.tabMiscellaneous);
		aerwhale_music_disc = registerItem("aerwhale_music_disc", new ItemAetherRecord("aerwhale", SoundsAether.record_aerwhale), AetherCreativeTabs.tabMiscellaneous);
		recording_892 = registerItem("recording_892", new ItemAetherRecord("recording_892", SoundsAether.record_recording_892), AetherCreativeTabs.tabMiscellaneous);

		healing_stone = registerItem("healing_stone", new ItemFood(0, 1.2f, false).setAlwaysEdible().setPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("regeneration"), 200, 0), 1.0f), AetherCreativeTabs.tabConsumables);

		dart_shooter = registerItem("dart_shooter", new ItemDartShooter(), AetherCreativeTabs.tabWeapons);
		dart = registerItem("dart", new ItemDart(), AetherCreativeTabs.tabWeapons);

		skyroot_crossbow = registerItem("skyroot_crossbow", new ItemSkyrootCrossbow(), AetherCreativeTabs.tabWeapons);
		holystone_crossbow = registerItem("holystone_crossbow", new ItemHolystoneCrossbow(), AetherCreativeTabs.tabWeapons);
		zanite_crossbow = registerItem("zanite_crossbow", new ItemZaniteCrossbow(), AetherCreativeTabs.tabWeapons);
		arkenium_crossbow = registerItem("arkenium_crossbow", new ItemArkeniumCrossbow(), AetherCreativeTabs.tabWeapons);
		gravitite_crossbow = registerItem("gravitite_crossbow", new ItemGravititeCrossbow(), AetherCreativeTabs.tabWeapons);
		vampire_crossbow = registerItem("vampire_crossbow",new ItemVampireCrossbow(), AetherCreativeTabs.tabWeapons);
		bolt = registerItem("bolt", new ItemBolt(), AetherCreativeTabs.tabWeapons);

		flaming_sword = registerItem("flaming_sword", new ItemElementalSword(ItemElementalSword.SwordElement.FIRE));
		holy_sword = registerItem("holy_sword", new ItemElementalSword(ItemElementalSword.SwordElement.HOLY));
		lightning_sword = registerItem("lightning_sword", new ItemElementalSword(ItemElementalSword.SwordElement.LIGHTNING));

		pig_slayer = registerItem("pig_slayer", new ItemPigSlayer(), AetherCreativeTabs.tabWeapons);
		vampire_blade = registerItem("vampire_blade", new ItemVampireBlade(), AetherCreativeTabs.tabWeapons);
		candy_cane_sword = registerItem("candy_cane_sword", new ItemCandyCaneSword(), AetherCreativeTabs.tabWeapons);

		skyroot_door = registerItem("skyroot_door_item", new ItemDoor(BlocksAether.skyroot_door), AetherCreativeTabs.tabBlocks);

		iron_ring = registerItem("iron_ring", new Item().setMaxStackSize(1), AetherCreativeTabs.tabAccessories);
		gold_ring = registerItem("gold_ring", new Item().setMaxStackSize(1), AetherCreativeTabs.tabAccessories);
		iron_pendant = registerItem("iron_pendant", new Item().setMaxStackSize(1), AetherCreativeTabs.tabAccessories);
		gold_pendant = registerItem("gold_pendant", new Item().setMaxStackSize(1), AetherCreativeTabs.tabAccessories);

		zanite_ring = registerItem("zanite_ring", new Item().setMaxStackSize(1), AetherCreativeTabs.tabAccessories);
		zanite_pendant = registerItem("zanite_pendant", new Item().setMaxStackSize(1), AetherCreativeTabs.tabAccessories);

		iron_bubble = registerItem("iron_bubble", new Item().setMaxStackSize(1), AetherCreativeTabs.tabAccessories);
		regeneration_stone = registerItem("regeneration_stone", new Item().setMaxStackSize(1), AetherCreativeTabs.tabAccessories);

		ice_ring = registerItem("ice_ring", new Item().setMaxStackSize(1), AetherCreativeTabs.tabAccessories);
		ice_pendant = registerItem("ice_pendant", new Item().setMaxStackSize(1), AetherCreativeTabs.tabAccessories);

		daggerfrost_locket = registerItem("daggerfrost_locket", new Item().setMaxStackSize(1), AetherCreativeTabs.tabAccessories);

		candy_ring = registerItem("candy_ring", new Item().setMaxStackSize(1), AetherCreativeTabs.tabAccessories);

		bone_ring = registerItem("bone_ring", new Item().setMaxStackSize(1), AetherCreativeTabs.tabAccessories);

		skyroot_ring = registerItem("skyroot_ring", new Item().setMaxStackSize(1), AetherCreativeTabs.tabAccessories);

		skyroot_sign = registerItem("skyroot_sign", new ItemSkyrootSign(), AetherCreativeTabs.tabBlocks);
		
		aether_portal_frame = registerItem("aether_portal_frame", new ItemAetherPortalFrame(), AetherCreativeTabs.tabBlocks);

        pink_baby_swet = registerItem("pink_baby_swet", new ItemPinkBabySwet().setMaxStackSize(1), AetherCreativeTabs.tabCompanions);

        shard_of_life = registerItem("shard_of_life", new ItemShardOfLife().setMaxStackSize(4), AetherCreativeTabs.tabConsumables);

		leather_gloves = registerItem("leather_gloves", new ItemLeatherGloves());
		iron_gloves = registerItem("iron_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.IRON));
		gold_gloves = registerItem("gold_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.GOLD));
		chain_gloves = registerItem("chain_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.CHAIN));
		diamond_gloves = registerItem("diamond_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.DIAMOND));


		IEquipmentRegistry equipmentRegistry = AetherCore.INSTANCE.getEquipmentRegistry();

		equipmentRegistry.register(ItemsAether.iron_ring, ItemRarity.COMMON, ItemEquipmentType.RING);
		equipmentRegistry.register(ItemsAether.gold_ring, ItemRarity.COMMON, ItemEquipmentType.RING);
		equipmentRegistry.register(ItemsAether.iron_pendant, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
		equipmentRegistry.register(ItemsAether.gold_pendant, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
		equipmentRegistry.register(ItemsAether.zanite_ring, ItemRarity.COMMON, ItemEquipmentType.RING);
		equipmentRegistry.register(ItemsAether.zanite_pendant, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
		equipmentRegistry.register(ItemsAether.iron_bubble, ItemRarity.COMMON, ItemEquipmentType.RELIC);
		equipmentRegistry.register(ItemsAether.regeneration_stone, ItemRarity.COMMON, ItemEquipmentType.RELIC);
		equipmentRegistry.register(ItemsAether.ice_ring, ItemRarity.COMMON, ItemEquipmentType.RING);
		equipmentRegistry.register(ItemsAether.ice_pendant, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
		equipmentRegistry.register(ItemsAether.daggerfrost_locket, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
		equipmentRegistry.register(ItemsAether.candy_ring, ItemRarity.COMMON, ItemEquipmentType.RING);
		equipmentRegistry.register(ItemsAether.bone_ring, ItemRarity.COMMON, ItemEquipmentType.RING);
		equipmentRegistry.register(ItemsAether.skyroot_ring, ItemRarity.COMMON, ItemEquipmentType.RING);
		equipmentRegistry.register(ItemsAether.zanite_gloves, ItemRarity.COMMON, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.gravitite_gloves, ItemRarity.COMMON, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.valkyrie_gloves, ItemRarity.RARE, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.neptune_gloves, ItemRarity.RARE, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.phoenix_gloves, ItemRarity.RARE, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.obsidian_gloves, ItemRarity.RARE, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.leather_gloves, ItemRarity.COMMON, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.iron_gloves, ItemRarity.COMMON, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.gold_gloves, ItemRarity.COMMON, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.chain_gloves, ItemRarity.COMMON, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.diamond_gloves, ItemRarity.COMMON, ItemEquipmentType.HANDWEAR);

		class Effects implements ItemEffects.ItemEffectsProvider
		{

			private List<Pair<EntityEffectProcessor, EntityEffectInstance>> pairs = Lists.newArrayList();

			public Effects()
			{

			}

			public <I extends EntityEffectInstance> Effects add(EntityEffectProcessor<I> processor, I instance)
			{
				Pair<EntityEffectProcessor, EntityEffectInstance> effectPair = Pair.of((EntityEffectProcessor) processor, (EntityEffectInstance) instance);

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

		ItemEffects.register(iron_ring);
		ItemEffects.register(gold_ring);
		ItemEffects.register(iron_pendant);
		ItemEffects.register(gold_pendant);
		ItemEffects.register(zanite_ring);
		ItemEffects.register(zanite_pendant);
		ItemEffects.register(bolt);
		ItemEffects.register(iron_bubble, new Effects().add(EntityEffects.BREATHE_UNDERWATER));
		ItemEffects.register(regeneration_stone, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new OutOfCombatRule(160))));
		ItemEffects.register(ice_ring, new Effects().add(EntityEffects.FREEZE_BLOCKS, new FreezeBlocksEffect.Instance(3)));
		ItemEffects.register(ice_pendant, new Effects().add(EntityEffects.FREEZE_BLOCKS, new FreezeBlocksEffect.Instance(3)));
		ItemEffects.register(daggerfrost_locket, new Effects().add(EntityEffects.DAGGERFROST, new EntityEffectInstance()));
		ItemEffects.register(candy_ring, new Effects().add(EntityEffects.PAUSE_HUNGER));
		ItemEffects.register(bone_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(2.0F)));
		ItemEffects.register(skyroot_ring, new Effects().add(EntityEffects.DOUBLE_DROPS, new DoubleDropEffect.Instance(1.5F)));
		ItemEffects.register(zanite_gloves, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(1.5F)));
		ItemEffects.register(gravitite_gloves, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(2.0F)));
		ItemEffects.register(valkyrie_gloves, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(2.5F)));
		ItemEffects.register(neptune_gloves, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(2.0F)));
		ItemEffects.register(phoenix_gloves, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(2.0F)));
		ItemEffects.register(obsidian_gloves, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(3.0F)));

		registerItemProperties();
	}

	private static void registerItemProperties()
	{
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER,
				new ItemStack(ItemsAether.skyroot_water_bucket), new ItemStack(ItemsAether.skyroot_bucket));
	}

	private static <T extends Item> T registerItem(String name, T item, CreativeTabs tab)
	{
		item.setCreativeTab(tab);

		return registerItem(name, item);
	}

	private static <T extends Item> T registerItem(String name, T item)
	{
		item.setUnlocalizedName(AetherCore.MOD_ID + "." + name);
		item.setRegistryName(name);

		GameRegistry.register(item);

		return item;
	}
}
