package com.gildedgames.aether.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.AetherMaterials;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.effects.EffectInstance;
import com.gildedgames.aether.common.entities.effects.EntityEffects;
import com.gildedgames.aether.common.entities.effects.processors.DoubleDropEffect;
import com.gildedgames.aether.common.entities.effects.processors.FreezeBlocksEffect;
import com.gildedgames.aether.common.entities.effects.processors.ModifyDamageEffect;
import com.gildedgames.aether.common.entities.effects.processors.ModifyMaxHealthEffect;
import com.gildedgames.aether.common.entities.effects.processors.ModifySpeedEffect;
import com.gildedgames.aether.common.entities.effects.processors.RegenerateHealthEffect;
import com.gildedgames.aether.common.entities.effects.processors.player.ModifyXPCollectionEffect;
import com.gildedgames.aether.common.entities.effects.rules.DamagingMobRule;
import com.gildedgames.aether.common.entities.effects.rules.DamagingUndeadRule;
import com.gildedgames.aether.common.entities.effects.rules.HoldingItemRule;
import com.gildedgames.aether.common.entities.effects.rules.InBiomeRule;
import com.gildedgames.aether.common.entities.effects.rules.InDirectMoonlightRule;
import com.gildedgames.aether.common.entities.effects.rules.InDirectSunlightRule;
import com.gildedgames.aether.common.entities.effects.rules.OutOfCombatRule;
import com.gildedgames.aether.common.items.armor.ItemGravititeArmor;
import com.gildedgames.aether.common.items.armor.ItemNeptuneArmor;
import com.gildedgames.aether.common.items.armor.ItemObsidianArmor;
import com.gildedgames.aether.common.items.armor.ItemPhoenixArmor;
import com.gildedgames.aether.common.items.armor.ItemSentryBoots;
import com.gildedgames.aether.common.items.armor.ItemValkyrieArmor;
import com.gildedgames.aether.common.items.armor.ItemZaniteArmor;
import com.gildedgames.aether.common.items.consumables.ItemAmbrosiumShard;
import com.gildedgames.aether.common.items.consumables.ItemGummySwet;
import com.gildedgames.aether.common.items.consumables.ItemRainbowStrawberry;
import com.gildedgames.aether.common.items.consumables.ItemSkyrootConsumableBucket;
import com.gildedgames.aether.common.items.consumables.ItemStomperPop;
import com.gildedgames.aether.common.items.consumables.ItemSwetJelly;
import com.gildedgames.aether.common.items.tools.EnumToolType;
import com.gildedgames.aether.common.items.tools.ItemGravititeTool;
import com.gildedgames.aether.common.items.tools.ItemHolystoneTool;
import com.gildedgames.aether.common.items.tools.ItemSkyrootBucket;
import com.gildedgames.aether.common.items.tools.ItemSkyrootTool;
import com.gildedgames.aether.common.items.tools.ItemZaniteTool;
import com.gildedgames.aether.common.items.weapons.ItemDart;
import com.gildedgames.aether.common.items.weapons.ItemDartShooter;
import com.gildedgames.aether.common.items.weapons.ItemPigSlayer;
import com.gildedgames.aether.common.items.weapons.ItemVampireBlade;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBolt;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemCrossbow;
import com.gildedgames.aether.common.items.weapons.swords.ItemAetherSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemCandyCaneSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemElementalSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemGravititeSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemHolystoneSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemSkyrootSword;
import com.gildedgames.aether.common.items.weapons.swords.ItemZaniteSword;

public class ItemsAether
{
	public static Item skyroot_stick;

	public static Item ambrosium_shard, continuum_orb, zanite_gemstone;

	public static ItemSkyrootTool skyroot_axe, skyroot_pickaxe, skyroot_shovel;

	public static ItemHolystoneTool holystone_axe, holystone_pickaxe, holystone_shovel;

	public static ItemZaniteTool zanite_axe, zanite_pickaxe, zanite_shovel;

	public static ItemGravititeTool gravitite_axe, gravitite_pickaxe, gravitite_shovel;

	public static ItemAetherSword skyroot_sword, holystone_sword, zanite_sword, gravitite_sword;

	public static ItemZaniteArmor zanite_helmet, zanite_chestplate, zanite_leggings, zanite_boots;

	public static ItemGravititeArmor gravitite_helmet, gravitite_chestplate, gravitite_leggings, gravitite_boots;

	public static ItemObsidianArmor obsidian_helmet, obsidian_chestplate, obsidian_leggings, obsidian_boots;

	public static ItemNeptuneArmor neptune_helmet, neptune_chestplate, neptune_leggings, neptune_boots;

	public static ItemPhoenixArmor phoenix_helmet, phoenix_chestplate, phoenix_leggings, phoenix_boots;

	public static ItemValkyrieArmor valkyrie_helmet, valkyrie_chestplate, valkyrie_leggings, valkyrie_boots;
	
	public static ItemSentryBoots sentry_boots;

	public static Item golden_amber;

	public static ItemFood blueberry, orange, wyndberry, rainbow_strawberry, swet_jelly, gummy_swet;

	public static ItemFood candy_corn, cocoatrice, wrapped_chocolates, jelly_pumpkin, stomper_pop, blueberry_lollipop, orange_lollipop, icestone_poprocks;

	public static ItemFood ginger_bread_man, candy_cane;

	public static ItemSkyrootBucket skyroot_bucket, skyroot_water_bucket;

	public static ItemSkyrootConsumableBucket skyroot_milk_bucket, skyroot_poison_bucket;

	public static ItemAetherRecord valkyrie_music_disc, labyrinth_music_disc, moa_music_disc, aerwhale_music_disc, recording_892;

	public static ItemFood healing_stone;

	public static ItemDartShooter dart_shooter;

	public static ItemDart dart;

	public static ItemCrossbow crossbow;

	public static ItemBolt bolt;

	public static ItemElementalSword flaming_sword, holy_sword, lightning_sword;

	public static ItemSword pig_slayer, vampire_blade, candy_cane_sword;

	public static ItemDoor skyroot_door;

	public static ItemAccessory iron_ring, gold_ring;

	public static ItemAccessory zanite_ring, zanite_pendant;

	public static ItemAccessory iron_pendant, gold_pendant;

	public static ItemAccessory iron_bubble, regeneration_stone;
	
	public static ItemAccessory ice_ring, ice_pendant;

	public static ItemAccessory daggerfrost_locket;
	
	public static ItemAccessory candy_ring, bone_ring, skyroot_ring;
	
	public static ItemAccessory barbed_iron_ring, barbed_gold_ring;
	
	public static ItemAccessory solar_band, lunar_band;
	
	public static ItemAccessory ring_of_growth, plague_coil;
	
	public static ItemAccessory fleeting_ring, lesser_ring_of_growth, winged_ring;
	
	public static ItemAccessory life_coil;
	
	public static ItemAccessory iron_barbed_wire, wisdom_bauble, bone_shard;
	
	public static ItemAccessory moa_feather, blight_ward, skyroot_twig;

	public static ItemAccessory gold_barbed_wire, ambrosium_talisman, carrion_petal;
	
	public static ItemAccessory moonlit_petal, cockatrice_heart;
	
	public static ItemAccessory damaged_moa_feather, osseous_bane, rot_bane;
	
	public static ItemAccessory continuum_talisman, labyrinth_plans;

	public static Item icestone;

	public static void preInit()
	{
		skyroot_stick = registerItem("skyroot_stick", new Item(), AetherCreativeTabs.tabMaterials);
		ambrosium_shard = registerItem("ambrosium_shard", new ItemAmbrosiumShard(), AetherCreativeTabs.tabMaterials);
		continuum_orb = registerItem("continuum_orb", new Item(), AetherCreativeTabs.tabMaterials);
		zanite_gemstone = registerItem("zanite_gemstone", new Item(), AetherCreativeTabs.tabMaterials);

		skyroot_axe = registerItem("skyroot_axe", new ItemSkyrootTool(EnumToolType.AXE));
		skyroot_pickaxe = registerItem("skyroot_pickaxe", new ItemSkyrootTool(EnumToolType.PICKAXE));
		skyroot_shovel = registerItem("skyroot_shovel", new ItemSkyrootTool(EnumToolType.SHOVEL));
		skyroot_sword = registerItem("skyroot_sword", new ItemSkyrootSword());

		holystone_axe = registerItem("holystone_axe", new ItemHolystoneTool(EnumToolType.AXE));
		holystone_pickaxe = registerItem("holystone_pickaxe", new ItemHolystoneTool(EnumToolType.PICKAXE));
		holystone_shovel = registerItem("holystone_shovel", new ItemHolystoneTool(EnumToolType.SHOVEL));
		holystone_sword = registerItem("holystone_sword", new ItemHolystoneSword());

		zanite_axe = registerItem("zanite_axe", new ItemZaniteTool(EnumToolType.AXE));
		zanite_pickaxe = registerItem("zanite_pickaxe", new ItemZaniteTool(EnumToolType.PICKAXE));
		zanite_shovel = registerItem("zanite_shovel", new ItemZaniteTool(EnumToolType.SHOVEL));
		zanite_sword = registerItem("zanite_sword", new ItemZaniteSword());

		gravitite_axe = registerItem("gravitite_axe", new ItemGravititeTool(EnumToolType.AXE));
		gravitite_pickaxe = registerItem("gravitite_pickaxe", new ItemGravititeTool(EnumToolType.PICKAXE));
		gravitite_shovel = registerItem("gravitite_shovel", new ItemGravititeTool(EnumToolType.SHOVEL));
		gravitite_sword = registerItem("gravitite_sword", new ItemGravititeSword());

		zanite_helmet = registerItem("zanite_helmet", new ItemZaniteArmor(0));
		zanite_chestplate = registerItem("zanite_chestplate", new ItemZaniteArmor(1));
		zanite_leggings = registerItem("zanite_leggings", new ItemZaniteArmor(2));
		zanite_boots = registerItem("zanite_boots", new ItemZaniteArmor(3));

		gravitite_helmet = registerItem("gravitite_helmet", new ItemGravititeArmor(0));
		gravitite_chestplate = registerItem("gravitite_chestplate", new ItemGravititeArmor(1));
		gravitite_leggings = registerItem("gravitite_leggings", new ItemGravititeArmor(2));
		gravitite_boots = registerItem("gravitite_boots", new ItemGravititeArmor(3));

		obsidian_helmet = registerItem("obsidian_helmet", new ItemObsidianArmor(AetherMaterials.OBSIDIAN_ARMOR, 0));
		obsidian_chestplate = registerItem("obsidian_chestplate", new ItemObsidianArmor(AetherMaterials.OBSIDIAN_ARMOR, 1));
		obsidian_leggings = registerItem("obsidian_leggings", new ItemObsidianArmor(AetherMaterials.OBSIDIAN_ARMOR, 2));
		obsidian_boots = registerItem("obsidian_boots", new ItemObsidianArmor(AetherMaterials.OBSIDIAN_ARMOR, 3));

		neptune_helmet = registerItem("neptune_helmet", new ItemNeptuneArmor(0));
		neptune_chestplate = registerItem("neptune_chestplate", new ItemNeptuneArmor(1));
		neptune_leggings = registerItem("neptune_leggings", new ItemNeptuneArmor(2));
		neptune_boots = registerItem("neptune_boots", new ItemNeptuneArmor(3));

		phoenix_helmet = registerItem("phoenix_helmet", new ItemPhoenixArmor(0));
		phoenix_chestplate = registerItem("phoenix_chestplate", new ItemPhoenixArmor(1));
		phoenix_leggings = registerItem("phoenix_leggings", new ItemPhoenixArmor(2));
		phoenix_boots = registerItem("phoenix_boots", new ItemPhoenixArmor(3));

		valkyrie_helmet = registerItem("valkyrie_helmet", new ItemValkyrieArmor(AetherMaterials.VALKYRIE_ARMOR, 0));
		valkyrie_chestplate = registerItem("valkyrie_chestplate", new ItemValkyrieArmor(AetherMaterials.VALKYRIE_ARMOR, 1));
		valkyrie_leggings = registerItem("valkyrie_leggings", new ItemValkyrieArmor(AetherMaterials.VALKYRIE_ARMOR, 2));
		valkyrie_boots = registerItem("valkyrie_boots", new ItemValkyrieArmor(AetherMaterials.VALKYRIE_ARMOR, 3));
		
		sentry_boots = registerItem("sentry_boots", new ItemSentryBoots(AetherMaterials.LEGENDARY_ARMOR, 3));

		golden_amber = registerItem("golden_amber", new Item(), AetherCreativeTabs.tabMaterials);

		blueberry = registerItem("blueberry", new ItemFood(2, false), AetherCreativeTabs.tabConsumables);

		orange = registerItem("orange", new ItemFood(4, false), AetherCreativeTabs.tabConsumables);

		wyndberry = registerItem("wyndberry", new ItemFood(4, false), AetherCreativeTabs.tabConsumables);

		rainbow_strawberry = registerItem("rainbow_strawberry", new ItemRainbowStrawberry(), AetherCreativeTabs.tabConsumables);

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

		skyroot_bucket = registerItem("skyroot_bucket", new ItemSkyrootBucket(Blocks.air), AetherCreativeTabs.tabTools);
		skyroot_water_bucket = registerItem("skyroot_water_bucket", new ItemSkyrootBucket(Blocks.flowing_water), AetherCreativeTabs.tabTools);
		skyroot_milk_bucket = registerItem("skyroot_milk_bucket", new ItemSkyrootConsumableBucket(), AetherCreativeTabs.tabConsumables);
		skyroot_poison_bucket = registerItem("skyroot_poison_bucket", new ItemSkyrootConsumableBucket(), AetherCreativeTabs.tabConsumables);

		valkyrie_music_disc = registerItem("valkyrie_music_disc", new ItemAetherRecord("valkyrie"), AetherCreativeTabs.tabTools);
		labyrinth_music_disc = registerItem("labyrinth_music_disc", new ItemAetherRecord("labyrinth"), AetherCreativeTabs.tabTools);
		moa_music_disc = registerItem("moa_music_disc", new ItemAetherRecord("moa"), AetherCreativeTabs.tabTools);
		aerwhale_music_disc = registerItem("aerwhale_music_disc", new ItemAetherRecord("aerwhale"), AetherCreativeTabs.tabTools);
		recording_892 = registerItem("recording_892", new ItemAetherRecord("recording_892"), AetherCreativeTabs.tabTools);

		healing_stone = registerItem("healing_stone", new ItemFood(0, 1.2f, false).setAlwaysEdible().setPotionEffect(Potion.regeneration.getId(), 30, 0, 1.0f), AetherCreativeTabs.tabConsumables);

		dart_shooter = registerItem("dart_shooter", new ItemDartShooter(), AetherCreativeTabs.tabWeapons);
		dart = registerItem("dart", new ItemDart(), AetherCreativeTabs.tabWeapons);

		crossbow = registerItem("crossbow", new ItemCrossbow(), AetherCreativeTabs.tabWeapons);
		bolt = registerItem("bolt", new ItemBolt(), AetherCreativeTabs.tabWeapons);

		flaming_sword = registerItem("flaming_sword", new ItemElementalSword(ItemElementalSword.SwordElement.FIRE));
		holy_sword = registerItem("holy_sword", new ItemElementalSword(ItemElementalSword.SwordElement.HOLY));
		lightning_sword = registerItem("lightning_sword", new ItemElementalSword(ItemElementalSword.SwordElement.LIGHTNING));

		pig_slayer = registerItem("pig_slayer", new ItemPigSlayer(), AetherCreativeTabs.tabWeapons);
		vampire_blade = registerItem("vampire_blade", new ItemVampireBlade(), AetherCreativeTabs.tabWeapons);
		candy_cane_sword = registerItem("candy_cane_sword", new ItemCandyCaneSword(), AetherCreativeTabs.tabWeapons);

		skyroot_door = registerItem("skyroot_door_item", new ItemDoor(BlocksAether.skyroot_door), AetherCreativeTabs.tabBlocks);

		iron_ring = registerItem("iron_ring", new ItemAccessory(AccessoryType.RING), AetherCreativeTabs.tabAccessories);
		gold_ring = registerItem("gold_ring", new ItemAccessory(AccessoryType.RING), AetherCreativeTabs.tabAccessories);
		iron_pendant = registerItem("iron_pendant", new ItemAccessory(AccessoryType.NECKWEAR), AetherCreativeTabs.tabAccessories);
		gold_pendant = registerItem("gold_pendant", new ItemAccessory(AccessoryType.NECKWEAR), AetherCreativeTabs.tabAccessories);

		zanite_ring = registerItem("zanite_ring", new ItemAccessory(AccessoryType.RING), AetherCreativeTabs.tabAccessories);
		zanite_pendant = registerItem("zanite_pendant", new ItemAccessory(AccessoryType.NECKWEAR), AetherCreativeTabs.tabAccessories);

		iron_bubble = registerItem("iron_bubble", new ItemAccessory(AccessoryType.RELIC).effect(EntityEffects.BREATHE_UNDERWATER, new EffectInstance()), AetherCreativeTabs.tabAccessories);
		regeneration_stone = registerItem("regeneration_stone", new ItemAccessory(AccessoryType.RELIC).effect(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new OutOfCombatRule(160))), AetherCreativeTabs.tabAccessories);

		ice_ring = registerItem("ice_ring", new ItemAccessory(AccessoryType.RING).effect(EntityEffects.FREEZE_BLOCKS, new FreezeBlocksEffect.Instance(3)), AetherCreativeTabs.tabAccessories);
		ice_pendant = registerItem("ice_pendant", new ItemAccessory(AccessoryType.NECKWEAR).effect(EntityEffects.FREEZE_BLOCKS, new FreezeBlocksEffect.Instance(3)), AetherCreativeTabs.tabAccessories);
		
		daggerfrost_locket = registerItem("daggerfrost_locket", new ItemAccessory(AccessoryType.NECKWEAR).effect(EntityEffects.DAGGERFROST, new EffectInstance()), AetherCreativeTabs.tabAccessories);
		
		candy_ring = registerItem("candy_ring", new ItemAccessory(AccessoryType.RING).effect(EntityEffects.PAUSE_HUNGER, new EffectInstance()), AetherCreativeTabs.tabAccessories);
		
		bone_ring = registerItem("bone_ring", new ItemAccessory(ItemRarity.RARE, AccessoryType.RING).effect(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(2.0F)), AetherCreativeTabs.tabAccessories);
		
		skyroot_ring = registerItem("skyroot_ring", new ItemAccessory(ItemRarity.RARE, AccessoryType.RING).effect(EntityEffects.DOUBLE_DROPS, new DoubleDropEffect.Instance(1.5F)), AetherCreativeTabs.tabAccessories);

		icestone = registerItem("icestone", new Item(), AetherCreativeTabs.tabMaterials);
		
		barbed_iron_ring = registerItem("barbed_iron_ring", new ItemAccessory(AccessoryType.RING).effect(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(1.0F)), AetherCreativeTabs.tabAccessories);
		
		barbed_gold_ring = registerItem("barbed_gold_ring", new ItemAccessory(ItemRarity.RARE, AccessoryType.RING).effect(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(1.0F, 3.0F, false)), AetherCreativeTabs.tabAccessories);

		solar_band = registerItem("solar_band", new ItemAccessory(ItemRarity.EPIC, AccessoryType.RING).effect(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new InDirectSunlightRule())), AetherCreativeTabs.tabAccessories);
		
		lunar_band = registerItem("lunar_band", new ItemAccessory(ItemRarity.EPIC, AccessoryType.RING).effect(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new InDirectMoonlightRule())), AetherCreativeTabs.tabAccessories);
		
		ring_of_growth = registerItem("ring_of_growth", new ItemAccessory(ItemRarity.RARE, AccessoryType.RING).effect(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(1.0F)), AetherCreativeTabs.tabAccessories);
		
		plague_coil = registerItem("plague_coil", new ItemAccessory(ItemRarity.MYTHIC, AccessoryType.RING).effect(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(2.0F, 6.0F, false)).effect(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(-2.0F)), AetherCreativeTabs.tabAccessories);
		
		fleeting_ring = registerItem("fleeting_ring", new ItemAccessory(ItemRarity.COMMON, AccessoryType.RING).effect(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float)SharedMonsterAttributes.movementSpeed.getDefaultValue() * 0.10F)), AetherCreativeTabs.tabAccessories);
		
		lesser_ring_of_growth = registerItem("lesser_ring_of_growth", new ItemAccessory(ItemRarity.COMMON, AccessoryType.RING).effect(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(0.5F)), AetherCreativeTabs.tabAccessories);
		
		winged_ring = registerItem("winged_ring", new ItemAccessory(ItemRarity.RARE, AccessoryType.RING).effect(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float)SharedMonsterAttributes.movementSpeed.getDefaultValue() * 0.20F)), AetherCreativeTabs.tabAccessories);
		
		life_coil = registerItem("life_coil", new ItemAccessory(ItemRarity.MYTHIC, AccessoryType.RING).effect(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(2.0F)).effect(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(-4.0F)), AetherCreativeTabs.tabAccessories);
		
		iron_barbed_wire = registerItem("iron_barbed_wire", new ItemAccessory(ItemRarity.COMMON, AccessoryType.CHARM).effect(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(0.2F)), AetherCreativeTabs.tabAccessories);
		
		wisdom_bauble = registerItem("wisdom_bauble", new ItemAccessory(ItemRarity.RARE, AccessoryType.CHARM).effect(EntityEffects.MODIFY_XP_COLLECTION, new ModifyXPCollectionEffect.Instance(0.10F)), AetherCreativeTabs.tabAccessories);
		
		bone_shard = registerItem("bone_shard", new ItemAccessory(ItemRarity.RARE, AccessoryType.CHARM).effect(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(0.3F)), AetherCreativeTabs.tabAccessories);
		
		moa_feather = registerItem("moa_feather", new ItemAccessory(ItemRarity.RARE, AccessoryType.CHARM).effect(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float)SharedMonsterAttributes.movementSpeed.getDefaultValue() * 0.03F)), AetherCreativeTabs.tabAccessories);
		
		blight_ward = registerItem("blight_ward", new ItemAccessory(ItemRarity.RARE, AccessoryType.CHARM), AetherCreativeTabs.tabAccessories);
		
		skyroot_twig = registerItem("skyroot_twig", new ItemAccessory(ItemRarity.RARE, AccessoryType.CHARM), AetherCreativeTabs.tabAccessories);
		
		gold_barbed_wire = registerItem("gold_barbed_wire", new ItemAccessory(ItemRarity.RARE, AccessoryType.CHARM).effect(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(0.1F, 0.5F, true)), AetherCreativeTabs.tabAccessories);
		
		ambrosium_talisman = registerItem("ambrosium_talisman", new ItemAccessory(ItemRarity.GODLY, AccessoryType.CHARM).effect(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new HoldingItemRule(new ItemStack(ItemsAether.ambrosium_shard)))), AetherCreativeTabs.tabAccessories);
		
		carrion_petal = registerItem("carrion_petal", new ItemAccessory(ItemRarity.EPIC, AccessoryType.CHARM).effect(EntityEffects.PAUSE_HUNGER, new EffectInstance(new InDirectSunlightRule())), AetherCreativeTabs.tabAccessories);
		
		moonlit_petal = registerItem("moonlit_petal", new ItemAccessory(ItemRarity.EPIC, AccessoryType.CHARM).effect(EntityEffects.PAUSE_HUNGER, new EffectInstance(new InDirectMoonlightRule())), AetherCreativeTabs.tabAccessories);
		
		cockatrice_heart = registerItem("cockatrice_heart", new ItemAccessory(ItemRarity.EPIC, AccessoryType.CHARM).effect(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new InBiomeRule(BiomeGenBase.beach))), AetherCreativeTabs.tabAccessories);

		damaged_moa_feather = registerItem("damaged_moa_feather", new ItemAccessory(ItemRarity.EPIC, AccessoryType.CHARM).effect(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float)SharedMonsterAttributes.movementSpeed.getDefaultValue() * 0.10F, new OutOfCombatRule(160))), AetherCreativeTabs.tabAccessories);
		
		osseous_bane = registerItem("osseous_bane", new ItemAccessory(ItemRarity.EPIC, AccessoryType.CHARM).effect(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(2.0F, new DamagingMobRule(EntitySkeleton.class, "Skeleton"))), AetherCreativeTabs.tabAccessories);
		
		rot_bane = registerItem("rot_bane", new ItemAccessory(ItemRarity.EPIC, AccessoryType.CHARM).effect(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(1.0F, new DamagingUndeadRule())), AetherCreativeTabs.tabAccessories);
		
		continuum_talisman = registerItem("continuum_talisman", new ItemAccessory(ItemRarity.MYTHIC, AccessoryType.CHARM), AetherCreativeTabs.tabAccessories);
		
		labyrinth_plans = registerItem("labyrinth_plans", new ItemAccessory(ItemRarity.MYTHIC, AccessoryType.CHARM), AetherCreativeTabs.tabAccessories);
		
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

		GameRegistry.registerItem(item);

		return item;
	}
}
