package com.gildedgames.aether.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.items.armor.EnumAetherArmorVariant;
import com.gildedgames.aether.common.items.armor.ItemAetherArmor;
import com.gildedgames.aether.common.items.consumables.ItemAmbrosiumShard;
import com.gildedgames.aether.common.items.consumables.ItemCloudParachute;
import com.gildedgames.aether.common.items.tools.EnumToolType;
import com.gildedgames.aether.common.items.tools.ItemGravititeTool;
import com.gildedgames.aether.common.items.tools.ItemHolystoneTool;
import com.gildedgames.aether.common.items.tools.ItemSkyrootTool;
import com.gildedgames.aether.common.items.tools.ItemZaniteTool;
import com.gildedgames.aether.common.items.weapons.ItemDart;
import com.gildedgames.aether.common.items.weapons.ItemDartShooter;
import com.gildedgames.aether.common.items.weapons.ItemGravititeSword;
import com.gildedgames.aether.common.items.weapons.ItemHolystoneSword;
import com.gildedgames.aether.common.items.weapons.ItemSkyrootSword;
import com.gildedgames.aether.common.items.weapons.ItemZaniteSword;

public class ItemsAether
{
	public static Item skyroot_stick;

	public static Item ambrosium_shard, continuum_orb, zanite_gemstone;

	public static Item skyroot_axe, holystone_axe, zanite_axe, gravitite_axe;

	public static Item skyroot_pickaxe, holystone_pickaxe, zanite_pickaxe, gravitite_pickaxe;

	public static Item skyroot_shovel, holystone_shovel, zanite_shovel, gravitite_shovel;

	public static Item skyroot_sword, holystone_sword, zanite_sword, gravitite_sword;

	public static Item zanite_helmet, zanite_chestplate, zanite_leggings, zanite_boots;

	public static Item gravitite_helmet, gravitite_chestplate, gravitite_leggings, gravitite_boots;

	public static Item obsidian_helmet, obsidian_chestplate, obsidian_leggings, obsidian_boots;

	public static Item neptune_helmet, neptune_chestplate, neptune_leggings, neptune_boots;

	public static Item phoenix_helmet, phoenix_chestplate, phoenix_leggings, phoenix_boots;

	public static Item valkyrie_helmet, valkyrie_chestplate, valkyrie_leggings, valkyrie_boots;

	public static Item golden_amber;

	public static Item blueberry, orange;

	public static Item skyroot_bucket, skyroot_water_bucket, skyroot_milk_bucket, skyroot_poison_bucket;

	public static Item valkyrie_music_disc, labyrinth_music_disc, moa_music_disc, aerwhale_music_disc, recording_892;

	public static Item healing_stone;

	public static Item dart_shooter;

	public static Item dart;

	public static Item cloud_parachute;

	public static void preInit()
	{
		skyroot_stick = registerItem("skyroot_stick", new Item(), AetherCore.locate().TAB_MATERIALS);
		ambrosium_shard = registerItem("ambrosium_shard", new ItemAmbrosiumShard(), AetherCore.locate().TAB_MATERIALS);
		continuum_orb = registerItem("continuum_orb", new Item(), AetherCore.locate().TAB_MATERIALS);
		zanite_gemstone = registerItem("zanite_gemstone", new Item(), AetherCore.locate().TAB_MATERIALS);

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

		zanite_helmet = registerItem("zanite_helmet", new ItemAetherArmor(EnumAetherArmorVariant.ZANITE, 0, 0));
		zanite_chestplate = registerItem("zanite_chestplate", new ItemAetherArmor(EnumAetherArmorVariant.ZANITE, 0, 1));
		zanite_leggings = registerItem("zanite_leggings", new ItemAetherArmor(EnumAetherArmorVariant.ZANITE, 0, 2));
		zanite_boots = registerItem("zanite_boots", new ItemAetherArmor(EnumAetherArmorVariant.ZANITE, 0, 3));

		gravitite_helmet = registerItem("gravitite_helmet", new ItemAetherArmor(EnumAetherArmorVariant.GRAVITITE, 0, 0));
		gravitite_chestplate = registerItem("gravitite_chestplate", new ItemAetherArmor(EnumAetherArmorVariant.GRAVITITE, 0, 1));
		gravitite_leggings = registerItem("gravitite_leggings", new ItemAetherArmor(EnumAetherArmorVariant.GRAVITITE, 0, 2));
		gravitite_boots = registerItem("gravitite_boots", new ItemAetherArmor(EnumAetherArmorVariant.GRAVITITE, 0, 3));

		obsidian_helmet = registerItem("obsidian_helmet", new ItemAetherArmor(EnumAetherArmorVariant.OBSIDIAN, 0, 0));
		obsidian_chestplate = registerItem("obsidian_chestplate", new ItemAetherArmor(EnumAetherArmorVariant.OBSIDIAN, 0, 1));
		obsidian_leggings = registerItem("obsidian_leggings", new ItemAetherArmor(EnumAetherArmorVariant.OBSIDIAN, 0, 2));
		obsidian_boots = registerItem("obsidian_boots", new ItemAetherArmor(EnumAetherArmorVariant.OBSIDIAN, 0, 3));

		neptune_helmet = registerItem("neptune_helmet", new ItemAetherArmor(EnumAetherArmorVariant.NEPTUNE, 0, 0));
		neptune_chestplate = registerItem("neptune_chestplate", new ItemAetherArmor(EnumAetherArmorVariant.NEPTUNE, 0, 1));
		neptune_leggings = registerItem("neptune_leggings", new ItemAetherArmor(EnumAetherArmorVariant.NEPTUNE, 0, 2));
		neptune_boots = registerItem("neptune_boots", new ItemAetherArmor(EnumAetherArmorVariant.NEPTUNE, 0, 3));

		phoenix_helmet = registerItem("phoenix_helmet", new ItemAetherArmor(EnumAetherArmorVariant.PHOENIX, 0, 0));
		phoenix_chestplate = registerItem("phoenix_chestplate", new ItemAetherArmor(EnumAetherArmorVariant.PHOENIX, 0, 1));
		phoenix_leggings = registerItem("phoenix_leggings", new ItemAetherArmor(EnumAetherArmorVariant.PHOENIX, 0, 2));
		phoenix_boots = registerItem("phoenix_boots", new ItemAetherArmor(EnumAetherArmorVariant.PHOENIX, 0, 3));

		valkyrie_helmet = registerItem("valkyrie_helmet", new ItemAetherArmor(EnumAetherArmorVariant.VALKYRIE, 0, 0));
		valkyrie_chestplate = registerItem("valkyrie_chestplate", new ItemAetherArmor(EnumAetherArmorVariant.VALKYRIE, 0, 1));
		valkyrie_leggings = registerItem("valkyrie_leggings", new ItemAetherArmor(EnumAetherArmorVariant.VALKYRIE, 0, 2));
		valkyrie_boots = registerItem("valkyrie_boots", new ItemAetherArmor(EnumAetherArmorVariant.VALKYRIE, 0, 3));

		golden_amber = registerItem("golden_amber", new Item(), AetherCore.locate().TAB_MATERIALS);

		blueberry = registerItem("blueberry", new ItemFood(2, false), AetherCore.locate().TAB_CONSUMABLES);
		orange = registerItem("orange", new ItemFood(4, false), AetherCore.locate().TAB_CONSUMABLES);

		skyroot_bucket = registerItem("skyroot_bucket", new ItemSkyrootBucket(Blocks.air), AetherCore.locate().TAB_TOOLS);
		skyroot_water_bucket = registerItem("skyroot_water_bucket", new ItemSkyrootBucket(Blocks.flowing_water), AetherCore.locate().TAB_TOOLS);
		skyroot_milk_bucket = registerItem("skyroot_milk_bucket", new ItemSkyrootConsumableBucket(), AetherCore.locate().TAB_CONSUMABLES);
		skyroot_poison_bucket = registerItem("skyroot_poison_bucket", new ItemSkyrootConsumableBucket(), AetherCore.locate().TAB_CONSUMABLES);

		valkyrie_music_disc = registerItem("valkyrie_music_disc", new ItemAetherRecord("valkyrie"), AetherCore.locate().TAB_TOOLS);
		labyrinth_music_disc = registerItem("labyrinth_music_disc", new ItemAetherRecord("labyrinth"), AetherCore.locate().TAB_TOOLS);
		moa_music_disc = registerItem("moa_music_disc", new ItemAetherRecord("moa"), AetherCore.locate().TAB_TOOLS);
		aerwhale_music_disc = registerItem("aerwhale_music_disc", new ItemAetherRecord("aerwhale"), AetherCore.locate().TAB_TOOLS);
		recording_892 = registerItem("recording_892", new ItemAetherRecord("recording_892"), AetherCore.locate().TAB_TOOLS);

		healing_stone = registerItem("healing_stone", new ItemFood(0, 1.2f, false).setAlwaysEdible().setPotionEffect(Potion.regeneration.getId(), 30, 0, 1.0f), AetherCore.locate().TAB_CONSUMABLES);

		dart_shooter = registerItem("dart_shooter", new ItemDartShooter(), AetherCore.locate().TAB_WEAPONS);
		dart = registerItem("dart", new ItemDart(), AetherCore.locate().TAB_WEAPONS);

		cloud_parachute = registerItem("cloud_parachute", new ItemCloudParachute(), AetherCore.locate().TAB_CONSUMABLES);

		registerItemProperties();
	}

	private static void registerItemProperties()
	{
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER,
				new ItemStack(ItemsAether.skyroot_water_bucket), new ItemStack(ItemsAether.skyroot_bucket));
	}

	private static Item registerItem(String name, Item item, CreativeTabs tab)
	{
		item.setCreativeTab(tab);

		return registerItem(name, item);
	}

	private static Item registerItem(String name, Item item)
	{
		item.setUnlocalizedName(name);
		GameRegistry.registerItem(item, name);

		return item;
	}
}
