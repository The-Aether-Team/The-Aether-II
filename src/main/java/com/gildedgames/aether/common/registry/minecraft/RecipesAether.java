package com.gildedgames.aether.common.registry.minecraft;

import com.gildedgames.aether.api.registry.altar.IAltarRecipe;
import com.gildedgames.aether.api.registry.altar.IAltarRecipeRegistry;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.aether.common.recipes.RecipePresentCrafting;
import com.gildedgames.aether.common.recipes.RecipeLeatherGlovesDyes;
import com.gildedgames.aether.common.recipes.RecipeWrappingPaper;
import com.gildedgames.aether.common.recipes.altar.AltarEnchantRecipe;
import com.gildedgames.aether.common.recipes.altar.AltarRepairRecipe;
import com.gildedgames.aether.common.entities.blocks.EntityParachute;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBoltType;
import com.gildedgames.aether.common.registry.AltarRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;

public class RecipesAether implements IAltarRecipeRegistry
{
	private final AltarRegistry altarRegistry = new AltarRegistry();

	public void init()
	{
		this.registerFurnaceRecipes();
		this.registerCraftingRecipes();
		this.registerToolRecipes();
		this.registerArmorRecipes();
		this.registerAccessoryRecipes();
		this.registerConsumableRecipes();
		this.registerAltarRecipes();

		GameRegistry.registerFuelHandler(new AetherFuelHandler());
	}

	private void registerFurnaceRecipes()
	{
		registerSmeltingRecipe(new ItemStack(ItemsAether.arkenium_ore), new ItemStack(ItemsAether.arkenium), 0.85f);
		registerSmeltingRecipe(new ItemStack(ItemsAether.gravitite_ore), new ItemStack(ItemsAether.gravitite_plate), 1.0f);
		registerSmeltingRecipe(new ItemStack(BlocksAether.quicksoil), new ItemStack(BlocksAether.quicksoil_glass), 0.1f);
		registerSmeltingRecipe(new ItemStack(ItemsAether.moa_egg), new ItemStack(ItemsAether.fried_moa_egg), 0.4f);
		registerSmeltingRecipe(new ItemStack(ItemsAether.rainbow_moa_egg), new ItemStack(ItemsAether.fried_moa_egg), 0.4f);
		registerSmeltingRecipe(new ItemStack(BlocksAether.crude_scatterglass), new ItemStack(BlocksAether.scatterglass), 0.1f);
	}

	private void registerCraftingRecipes()
	{
		RecipeSorter.register("aether:leatherGlovesDying", RecipeLeatherGlovesDyes.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("aether:wrappingPaper", RecipeWrappingPaper.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped");
		RecipeSorter.register("aether:presentCrafting", RecipePresentCrafting.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped");

		CraftingManager.getInstance().addRecipe(new RecipeLeatherGlovesDyes());
		CraftingManager.getInstance().addRecipe(new RecipeWrappingPaper());
		CraftingManager.getInstance().addRecipe(new RecipePresentCrafting());

		// Parachutes

		registerShapedRecipe(new ItemStack(ItemsAether.cloud_parachute, 1, EntityParachute.Type.COLD.ordinal()), "XXX", " S ",
				'X', new ItemStack(BlocksAether.aercloud, 1, BlockAercloud.COLD_AERCLOUD.getMeta()), 'S', Items.STRING);

		registerShapedRecipe(new ItemStack(ItemsAether.cloud_parachute, 1, EntityParachute.Type.BLUE.ordinal()), "XXX", " S ",
				'X', new ItemStack(BlocksAether.aercloud, 1, BlockAercloud.BLUE_AERCLOUD.getMeta()), 'S', Items.STRING);

		registerShapedRecipe(new ItemStack(ItemsAether.cloud_parachute, 1, EntityParachute.Type.GOLDEN.ordinal()), "XXX", " S ",
				'X', new ItemStack(BlocksAether.aercloud, 1, BlockAercloud.GOLDEN_AERCLOUD.getMeta()), 'S', Items.STRING);

		registerShapedRecipe(new ItemStack(ItemsAether.cloud_parachute, 1, EntityParachute.Type.PURPLE.ordinal()), "XXX", " S ",
				'X', new ItemStack(BlocksAether.aercloud, 1, BlockAercloud.PURPLE_AERCLOUD.getMeta()), 'S', Items.STRING);

		registerShapedRecipe(new ItemStack(ItemsAether.cloud_parachute, 1, EntityParachute.Type.GREEN.ordinal()), "XXX", " S ",
				'X', new ItemStack(BlocksAether.aercloud, 1, BlockAercloud.GREEN_AERCLOUD.getMeta()), 'S', Items.STRING);

		// Saddle
		registerShapedRecipe(new ItemStack(Items.SADDLE, 1), "XXX", "XZX",
				'X', Items.LEATHER, 'Z', Items.STRING);

		// Skyroot Planks
		registerShapelessRecipe(new ItemStack(BlocksAether.aether_planks, 4),
				new ItemStack(BlocksAether.skyroot_log));

		// Skyroot Sticks
		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_stick, 4), "X", "X",
				'X', new ItemStack(BlocksAether.aether_planks));

		// Skyroot Crafting Table
		registerShapedRecipe(new ItemStack(BlocksAether.aether_crafting_table), "XX", "XX",
				'X', new ItemStack(BlocksAether.aether_planks));

		// Skyroot Bed
		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_bed), "XXX", "YYY",
				'X', new ItemStack(Blocks.WOOL),
				'Y', new ItemStack(BlocksAether.aether_planks));

		// Skyroot Chest
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_chest), "XXX", "X X", "XXX",
				'X', new ItemStack(BlocksAether.aether_planks));

		// Altar
        registerShapedRecipe(new ItemStack(BlocksAether.altar), "AZA", " H ", "HHH",
				'H', new ItemStack(BlocksAether.holystone), 'Z', new ItemStack(ItemsAether.zanite_gemstone), 'A', new ItemStack(ItemsAether.arkenium));

		// Frostpine Cooler
		registerShapedRecipe(new ItemStack(BlocksAether.frostpine_cooler), "AZA", "HIH", "SSS",
				'H', new ItemStack(BlocksAether.holystone), 'Z', new ItemStack(ItemsAether.arkenium), 'A', new ItemStack(ItemsAether.arkenium_strip), 'I', new ItemStack(ItemsAether.icestone), 'S', new ItemStack(BlocksAether.aether_planks));

		// Arkenium Strip
		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_strip, 4), "X", "X",
				'X', new ItemStack(ItemsAether.arkenium));

		// Skyroot Frame Quicksoil Glass
		registerShapelessRecipe(new ItemStack(BlocksAether.skyroot_frame_quicksoil_glass, 1),
				new ItemStack(BlocksAether.quicksoil_glass), new ItemStack(ItemsAether.skyroot_stick));

		// Skyroot Frame Scatterglass
		registerShapelessRecipe(new ItemStack(BlocksAether.skyroot_frame_scatterglass, 1),
				new ItemStack(BlocksAether.scatterglass), new ItemStack(ItemsAether.skyroot_stick));

		// Arkenium Frame Quicksoil Glass
		registerShapelessRecipe(new ItemStack(BlocksAether.arkenium_frame_quicksoil_glass, 1),
				new ItemStack(BlocksAether.quicksoil_glass), new ItemStack(ItemsAether.arkenium_strip));

		// Arkenium Frame Scatterglass
		registerShapelessRecipe(new ItemStack(BlocksAether.arkenium_frame_scatterglass, 1),
				new ItemStack(BlocksAether.scatterglass), new ItemStack(ItemsAether.arkenium_strip));

		// Skyroot Door
		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_door, 3), "XX", "XX", "XX",
				'X', new ItemStack(BlocksAether.aether_planks));

		// Arkenium Door
		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_door, 3), "XX", "XX", "XX",
				'X', new ItemStack(ItemsAether.arkenium));

		// Skyroot Trap Door
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_trapdoor, 2), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.aether_planks));

		// Skyroot Ladder
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_ladder, 3), "X X", "XXX", "X X",
				'X', new ItemStack(ItemsAether.skyroot_stick));

		// Skyroot Pressure Plate
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_pressure_plate), "XX ",
				'X', new ItemStack(BlocksAether.aether_planks));

		// Skyroot Button
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_button), "X",
				'X', new ItemStack(BlocksAether.aether_planks));

		// Holystone Pressure Plate
		registerShapedRecipe(new ItemStack(BlocksAether.holystone_pressure_plate), "XX ",
				'X', new ItemStack(BlocksAether.holystone));

		// Holystone Button
		registerShapedRecipe(new ItemStack(BlocksAether.holystone_button), "X",
				'X', new ItemStack(BlocksAether.holystone));

		// Golden Dart Shooter
		registerShapedRecipe(new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.GOLDEN.ordinal()), "X", "X", "Y",
				'X', new ItemStack(BlocksAether.aether_planks), 'Y', new ItemStack(ItemsAether.golden_amber));

		// Poison Dart Shooter
		registerShapelessRecipe(new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.POISON.ordinal()),
				new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.GOLDEN.ordinal()), new ItemStack(ItemsAether.skyroot_poison_bucket));

        // Golden Dart
        registerShapedRecipe(new ItemStack(ItemsAether.dart), " X", " Y", " Z",
                'X', new ItemStack(ItemsAether.golden_amber),
                'Y', new ItemStack(ItemsAether.skyroot_stick),
                'Z', new ItemStack(Items.FEATHER));

        // Poison Dart
        registerShapedRecipe(new ItemStack(ItemsAether.dart, 2), "XXX", "XYX", "XXX",
                'X', new ItemStack(ItemsAether.dart),
                'Y', new ItemStack(ItemsAether.skyroot_poison_bucket));

        // Skyroot Bucket
        registerShapedRecipe(new ItemStack(ItemsAether.skyroot_bucket), "X X", " X ",
                'X', new ItemStack(BlocksAether.aether_planks));

		// Zanite Block
		registerShapedRecipe(new ItemStack(BlocksAether.zanite_block), "XXX", "XXX", "XXX",
				'X', new ItemStack(ItemsAether.zanite_gemstone));

		// Gravitite Block
		registerShapedRecipe(new ItemStack(BlocksAether.gravitite_block), "XXX", "XXX", "XXX",
				'X', new ItemStack(ItemsAether.gravitite_plate));

        // Zanite Uncraft
        registerShapedRecipe(new ItemStack(ItemsAether.zanite_gemstone, 9), "X",
                'X', new ItemStack(BlocksAether.zanite_block));

		// Holystone Brick
		registerShapedRecipe(new ItemStack(BlocksAether.holystone_brick, 4), "XX", "XX",
				'X', new ItemStack(BlocksAether.holystone));

		// Icestone Bricks
		registerShapedRecipe(new ItemStack(BlocksAether.icestone_bricks, 4), "XX", "XX",
				'X', new ItemStack(ItemsAether.icestone));

		// Walls

		registerShapedRecipe(new ItemStack(BlocksAether.holystone_wall, 6), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.holystone));
        registerShapedRecipe(new ItemStack(BlocksAether.mossy_holystone_wall, 6), "XXX", "XXX",
                'X', new ItemStack(BlocksAether.holystone, 1, BlockHolystone.MOSSY_HOLYSTONE.getMeta()));
        registerShapedRecipe(new ItemStack(BlocksAether.holystone_brick_wall, 6), "XXX", "XXX",
                'X', new ItemStack(BlocksAether.holystone_brick));
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_log_wall, 6), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.skyroot_log));
		registerShapedRecipe(new ItemStack(BlocksAether.icestone_wall, 6), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.icestone_bricks));
		registerShapedRecipe(new ItemStack(BlocksAether.carved_stone_wall, 6), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.carved_stone));
		registerShapedRecipe(new ItemStack(BlocksAether.scatterglass_wall, 6), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.crude_scatterglass));

		// Slabs

		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_slab, 6), "XXX",
				'X', new ItemStack(BlocksAether.aether_planks));
		registerShapedRecipe(new ItemStack(BlocksAether.holystone_slab, 6), "XXX",
			    'X', new ItemStack(BlocksAether.holystone));
		registerShapedRecipe(new ItemStack(BlocksAether.holystone_brick_slab, 6), "XXX",
				'X', new ItemStack(BlocksAether.holystone_brick));
		registerShapedRecipe(new ItemStack(BlocksAether.carved_stone_slab, 6), "XXX",
				'X', new ItemStack(BlocksAether.carved_stone));
		registerShapedRecipe(new ItemStack(BlocksAether.sentry_stone_slab, 6), "XXX",
				'X', new ItemStack(BlocksAether.sentry_stone));
		registerShapedRecipe(new ItemStack(BlocksAether.icestone_slab, 6), "XXX",
				'X', new ItemStack(BlocksAether.icestone_bricks));
		registerShapedRecipe(new ItemStack(BlocksAether.labyrinth_capstone_slab, 6), "XXX",
				'X', new ItemStack(BlocksAether.labyrinth_capstone));
		registerShapedRecipe(new ItemStack(BlocksAether.labyrinth_wall_slab, 6), "XXX",
				'X', new ItemStack(BlocksAether.labyrinth_wall));

		// Gates
        registerShapedRecipe(new ItemStack(BlocksAether.skyroot_fence_gate, 1), "XYX", "XYX",
                'X', new ItemStack(ItemsAether.skyroot_stick),
                'Y', new ItemStack(BlocksAether.aether_planks));

        // Fences
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_fence, 3), "YXY", "YXY",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(BlocksAether.aether_planks));

		// Sign
		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_sign, 3), "XXX", "XXX", " Y ",
				'X', new ItemStack(BlocksAether.aether_planks),
				'Y', new ItemStack(ItemsAether.skyroot_stick));

        // Ambrosium Torch
        registerShapedRecipe(new ItemStack(BlocksAether.ambrosium_torch, 8), " Y", " X",
                'X', new ItemStack(ItemsAether.skyroot_stick),
                'Y', new ItemStack(ItemsAether.ambrosium_shard));

		// Ambrosium Chunk
		registerShapedRecipe(new ItemStack(ItemsAether.ambrosium_chunk, 1), "XX", "XX",
				'X', new ItemStack(ItemsAether.ambrosium_shard));

		// Ambrosium Chunk Uncraft
		registerShapedRecipe(new ItemStack(ItemsAether.ambrosium_shard, 4), "X",
				'X', new ItemStack(ItemsAether.ambrosium_chunk));

		// Depleted Healing Stone
		registerShapedRecipe(new ItemStack(ItemsAether.healing_stone_depleted, 1), " X ", "ZXZ", " Y ",
				'X', new ItemStack(ItemsAether.ambrosium_chunk),
				'Y', new ItemStack(ItemsAether.ambrosium_shard),
				'Z', new ItemStack(BlocksAether.holystone));

        // Holystone Furnace
        registerShapedRecipe(new ItemStack(BlocksAether.holystone_furnace, 1), "XXX", "X X", "XXX",
                'X', new ItemStack(BlocksAether.holystone));

		// Incubator
		registerShapedRecipe(new ItemStack(BlocksAether.incubator), "XXX", "XYX", "ZZZ",
				'X', new ItemStack(BlocksAether.holystone),
				'Y', new ItemStack(ItemsAether.ambrosium_chunk),
				'Z', new ItemStack(BlocksAether.aether_planks));

		// Crossbow
		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_crossbow), "ZZY", "XYZ", "WXZ",
				'W', new ItemStack(ItemsAether.skyroot_stick),
				'X', new ItemStack(Items.STRING),
				'Y', new ItemStack(BlocksAether.aether_planks),
				'Z', new ItemStack(ItemsAether.arkenium_strip));

		registerShapedRecipe(new ItemStack(ItemsAether.holystone_crossbow), "ZZY", "XYZ", "WXZ",
				'W', new ItemStack(ItemsAether.skyroot_stick),
				'X', new ItemStack(Items.STRING),
				'Y', new ItemStack(BlocksAether.holystone),
				'Z', new ItemStack(ItemsAether.arkenium_strip));

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_crossbow), "ZZY", "XYZ", "WXZ",
				'W', new ItemStack(ItemsAether.skyroot_stick),
				'X', new ItemStack(Items.STRING),
				'Y', new ItemStack(ItemsAether.zanite_gemstone),
				'Z', new ItemStack(ItemsAether.arkenium_strip));

		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_crossbow), "ZZY", "XYZ", "WXZ",
				'W', new ItemStack(ItemsAether.skyroot_stick),
				'X', new ItemStack(Items.STRING),
				'Y', new ItemStack(ItemsAether.arkenium),
				'Z', new ItemStack(ItemsAether.arkenium_strip));

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_crossbow), "ZZY", "XYZ", "WXZ",
				'W', new ItemStack(ItemsAether.skyroot_stick),
				'X', new ItemStack(Items.STRING),
				'Y', new ItemStack(ItemsAether.gravitite_plate),
				'Z', new ItemStack(ItemsAether.arkenium_strip));

		// Crossbow Bolts
		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.SKYROOT.ordinal()), "  Y", " X ", "Z  ",
			'X', new ItemStack(ItemsAether.skyroot_stick),
			'Y', new ItemStack(ItemsAether.skyroot_fragment),
			'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.SKYROOT.ordinal()), "  Z", " X ", "Y  ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.skyroot_fragment),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.SKYROOT.ordinal()), " Y", " X", " Z",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.skyroot_fragment),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.SKYROOT.ordinal()), " Z", " X", " Y",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.skyroot_fragment),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.HOLYSTONE.ordinal()), "  Y", " X ", "Z  ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.holystone_chip),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.HOLYSTONE.ordinal()), "  Z", " X ", "Y  ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.holystone_chip),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.HOLYSTONE.ordinal()), " Y", " X", " Z",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.holystone_chip),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.HOLYSTONE.ordinal()), " Z", " X", " Y",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.holystone_chip),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.SCATTERGLASS.ordinal()), "  Y", " X ", "Z  ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.scatterglass_shard),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.SCATTERGLASS.ordinal()), "  Z", " X ", "Y  ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.scatterglass_shard),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.SCATTERGLASS.ordinal()), " Y", " X", " Z",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.scatterglass_shard),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.SCATTERGLASS.ordinal()), " Z", " X", " Y",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.scatterglass_shard),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.BONESHARD.ordinal()), "  Y", " X ", "Z  ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.bone_shard),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.BONESHARD.ordinal()), "  Z", " X ", "Y  ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.bone_shard),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.BONESHARD.ordinal()), " Y", " X", " Z",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.bone_shard),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.BONESHARD.ordinal()), " Z", " X", " Y",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.bone_shard),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.ZANITE.ordinal()), "  Y", " X ", "Z  ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.zanite_gemstone),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.ZANITE.ordinal()), "  Z", " X ", "Y  ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.zanite_gemstone),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.ZANITE.ordinal()), " Y", " X", " Z",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.zanite_gemstone),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.ZANITE.ordinal()), " Z", " X", " Y",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.zanite_gemstone),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.ARKENIUM.ordinal()), "  Y", " X ", "Z  ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.arkenium_strip),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.ARKENIUM.ordinal()), "  Z", " X ", "Y  ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.arkenium_strip),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.ARKENIUM.ordinal()), " Y", " X", " Z",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.arkenium_strip),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.ARKENIUM.ordinal()), " Z", " X", " Y",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.arkenium_strip),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.GRAVITITE.ordinal()), "  Y", " X ", "Z  ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.gravitite_plate),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.GRAVITITE.ordinal()), "  Z", " X ", "Y  ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.gravitite_plate),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.GRAVITITE.ordinal()), " Y", " X", " Z",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.gravitite_plate),
				'Z', new ItemStack(Items.FEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.GRAVITITE.ordinal()), " Z", " X", " Y",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.gravitite_plate),
				'Z', new ItemStack(Items.FEATHER));

		// Shields
		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_shield), "YXY", "YYY", " Y ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(BlocksAether.aether_planks));

		registerShapedRecipe(new ItemStack(ItemsAether.holystone_shield), "YXY", "YYY", " Y ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(BlocksAether.holystone));

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_shield), "YXY", "YYY", " Y ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.zanite_gemstone));

		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_shield), "YXY", "YYY", " Y ",
				'X', new ItemStack(ItemsAether.arkenium_strip),
				'Y', new ItemStack(ItemsAether.arkenium));

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_shield), "YXY", "YYY", " Y ",
				'X', new ItemStack(ItemsAether.arkenium_strip),
				'Y', new ItemStack(ItemsAether.gravitite_plate));

		// Stairs

		registerShapedRecipe(new ItemStack(BlocksAether.holystone_stairs, 4), "U  ", "UU ", "UUU",
				'U', new ItemStack(BlocksAether.holystone));

		registerShapedRecipe(new ItemStack(BlocksAether.mossy_holystone_stairs, 4), "U  ", "UU ", "UUU",
				'U', new ItemStack(BlocksAether.holystone, 1, BlockHolystone.MOSSY_HOLYSTONE.getMeta()));

		registerShapedRecipe(new ItemStack(BlocksAether.icestone_brick_stairs, 4), "U  ", "UU ", "UUU",
				'U', ItemsAether.icestone);

		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_stairs, 4), "U  ", "UU ", "UUU",
				'U', BlocksAether.aether_planks);

		registerShapedRecipe(new ItemStack(BlocksAether.carved_stone_stairs, 4), "U  ", "UU ", "UUU",
				'U', BlocksAether.carved_stone);

		registerShapedRecipe(new ItemStack(BlocksAether.sentry_stone_stairs, 4), "U  ", "UU ", "UUU",
				'U', new ItemStack(BlocksAether.sentry_stone));

		registerShapedRecipe(new ItemStack(BlocksAether.holystone_brick_stairs, 4), "U  ", "UU ", "UUU",
				'U', new ItemStack(BlocksAether.holystone_brick));

		registerShapedRecipe(new ItemStack(BlocksAether.scatterglass_stairs, 4), "U  ", "UU ", "UUU",
				'U', new ItemStack(BlocksAether.crude_scatterglass));
	}

	private void registerToolRecipes()
	{
		ItemStack skyrootStick = new ItemStack(ItemsAether.skyroot_stick);

		ItemStack skyroot = new ItemStack(BlocksAether.aether_planks);
		ItemStack holystone = new ItemStack(BlocksAether.holystone);
		ItemStack zanite = new ItemStack(ItemsAether.zanite_gemstone);
		ItemStack gravitite = new ItemStack(ItemsAether.gravitite_plate);
		ItemStack arkenium = new ItemStack(ItemsAether.arkenium);
		ItemStack ark_strip = new ItemStack(ItemsAether.arkenium_strip);

		// Vanilla Gloves

		registerShapedRecipe(new ItemStack(ItemsAether.leather_gloves), "X X",
				'X', new ItemStack(Items.LEATHER));

		registerShapedRecipe(new ItemStack(ItemsAether.iron_gloves), "X X",
				'X', new ItemStack(Items.IRON_INGOT));

		registerShapedRecipe(new ItemStack(ItemsAether.gold_gloves), "X X",
				'X', new ItemStack(Items.GOLD_INGOT));

		registerShapedRecipe(new ItemStack(ItemsAether.diamond_gloves), "X X",
				'X', new ItemStack(Items.DIAMOND));

		// Skyroot Tools
		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_axe), "XX ", "XY ", " Y ",
				'X', skyroot, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_pickaxe), "XXX", " Y ", " Y ",
				'X', skyroot, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_shovel), "X", "Y", "Y",
				'X', skyroot, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_sword), "X", "X", "Y",
				'X', skyroot, 'Y', skyrootStick);

		// Holystone Tools
		registerShapedRecipe(new ItemStack(ItemsAether.holystone_axe), "XX ", "XY ", " Y ",
				'X', holystone, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.holystone_pickaxe), "XXX", " Y ", " Y ",
				'X', holystone, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.holystone_shovel), "X", "Y", "Y",
				'X', holystone, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.holystone_sword), "X", "X", "Y",
				'X', holystone, 'Y', skyrootStick);

		// Zanite Tools
		registerShapedRecipe(new ItemStack(ItemsAether.zanite_axe), "XX ", "XY ", " Y ",
				'X', zanite, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_pickaxe), "XXX", " Y ", " Y ",
				'X', zanite, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_shovel), "X", "Y", "Y",
				'X', zanite, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_sword), "X", "X", "Y",
				'X', zanite, 'Y', skyrootStick);

		// Gravitite Tools
		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_axe), "XX ", "XY ", " Y ",
				'X', gravitite, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_pickaxe), "XXX", " Y ", " Y ",
				'X', gravitite, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_shovel), "X", "Y", "Y",
				'X', gravitite, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_sword), "X", "X", "Y",
				'X', gravitite, 'Y', skyrootStick);

		// Arkenium Tools
		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_axe), "XX ", "XY ", " Y ",
				'X', arkenium, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_pickaxe), "XXX", " Y ", " Y ",
				'X', arkenium, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_shovel), "X", "Y", "Y",
				'X', arkenium, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_sword), "X", "X", "Y",
				'X', arkenium, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_shears), "X ", " X",
				'X', ark_strip);

	}

	private void registerArmorRecipes()
	{
		ItemStack zanite = new ItemStack(ItemsAether.zanite_gemstone);
		ItemStack gravitite = new ItemStack(ItemsAether.gravitite_plate);

		// Zanite Armor
		registerShapedRecipe(new ItemStack(ItemsAether.zanite_helmet), "XXX", "X X",
				'X', zanite);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_chestplate), "X X", "XXX", "XXX",
				'X', zanite);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_leggings), "XXX", "X X", "X X",
				'X', zanite);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_boots), "X X", "X X",
				'X', zanite);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_gloves), "X X",
				'X', zanite);

		// Gravitite Armor
		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_helmet), "XXX", "X X",
				'X', gravitite);

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_chestplate), "X X", "XXX", "XXX",
				'X', gravitite);

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_leggings), "XXX", "X X", "X X",
				'X', gravitite);

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_boots), "X X", "X X",
				'X', gravitite);

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_gloves), "X X",
				'X', gravitite);
	}

    private void registerAccessoryRecipes() {
        // Iron Ring
        registerShapedRecipe(new ItemStack(ItemsAether.iron_ring), " X ", "X X", " X ",
                'X', new ItemStack(Items.IRON_INGOT));
        // Iron Pendant
        registerShapedRecipe(new ItemStack(ItemsAether.iron_pendant), "XXX", "X X", " Y ",
                'X', new ItemStack(Items.STRING),
                'Y', new ItemStack(Items.IRON_INGOT));
        // Gold Ring
        registerShapedRecipe(new ItemStack(ItemsAether.gold_ring), " X ", "X X", " X ",
                'X', new ItemStack(Items.GOLD_INGOT));
        // Gold Pendant
        registerShapedRecipe(new ItemStack(ItemsAether.gold_pendant), "XXX", "X X", " Y ",
                'X', new ItemStack(Items.STRING),
                'Y', new ItemStack(Items.GOLD_INGOT));
        // Zanite Ring
        registerShapedRecipe(new ItemStack(ItemsAether.zanite_ring), " X ", "X X", " X ",
                'X', new ItemStack(ItemsAether.zanite_gemstone));
        // Zanite Pendant
        registerShapedRecipe(new ItemStack(ItemsAether.zanite_pendant), "XXX", "X X", " Y ",
                'X', new ItemStack(Items.STRING),
                'Y', new ItemStack(ItemsAether.zanite_gemstone));
    }

    private void registerConsumableRecipes()
    {
        // Blue Gummy Swet
        registerShapedRecipe(new ItemStack(ItemsAether.gummy_swet), "XXX", "XYX", "XXX",
                'X', new ItemStack(ItemsAether.swet_jelly),
                'Y', new ItemStack(Items.SUGAR));

        // Gold Gummy Swet
        registerShapedRecipe(new ItemStack(ItemsAether.gummy_swet, 1, 1), "XXX", "XYX", "XXX",
                'X', new ItemStack(ItemsAether.swet_jelly, 1, 1),
                'Y', new ItemStack(Items.SUGAR));

        // Dark Gummy Swet
        registerShapedRecipe(new ItemStack(ItemsAether.gummy_swet, 1, 2), "XXX", "XYX", "XXX",
                'X', new ItemStack(ItemsAether.swet_jelly, 1, 2),
                'Y', new ItemStack(Items.SUGAR));

        // Cocoatrice
        registerShapedRecipe(new ItemStack(ItemsAether.cocoatrice), "XY", "Z ",
                'X', new ItemStack(Items.SUGAR),
                'Y', new ItemStack(Items.DYE, 1, 3),
                'Z', new ItemStack(ItemsAether.skyroot_milk_bucket));

        // Wrapped Chocolate
        registerShapedRecipe(new ItemStack(ItemsAether.wrapped_chocolates), "WX", "YZ",
                'W', new ItemStack(Items.SUGAR),
                'X', new ItemStack(Items.DYE, 1, 3),
                'Y', new ItemStack(ItemsAether.skyroot_milk_bucket),
                'Z', new ItemStack(ItemsAether.aechor_petal));

        // Jelly Pumpkin
        registerShapedRecipe(new ItemStack(ItemsAether.jelly_pumpkin), "XY", "Z ",
                'X', ItemsAether.swet_jelly,
                'Y', new ItemStack(ItemsAether.orange),
 	            'Z', new ItemStack(Items.SUGAR));

        // Stomper Pop
        registerShapedRecipe(new ItemStack(ItemsAether.stomper_pop), " X", " Z",
                'X', new ItemStack(ItemsAether.skyroot_stick),
                'Z', new ItemStack(ItemsAether.pink_baby_swet));

		registerShapedRecipe(new ItemStack(ItemsAether.stomper_pop), " Z", " X",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Z', new ItemStack(ItemsAether.pink_baby_swet));

        // Blueberry Lollipop
        registerShapedRecipe(new ItemStack(ItemsAether.blueberry_lollipop), "XY", "Z ",
                'X', new ItemStack(Items.SUGAR),
                'Y', new ItemStack(ItemsAether.blueberries),
                'Z', new ItemStack(ItemsAether.skyroot_stick));

        // Orange Lollipop
        registerShapedRecipe(new ItemStack(ItemsAether.orange_lollipop), "XY", "Z ",
                'X', new ItemStack(Items.SUGAR),
                'Y', new ItemStack(ItemsAether.orange),
                'Z', new ItemStack(ItemsAether.skyroot_stick));

        // Icestone Poprocks
        registerShapelessRecipe(new ItemStack(ItemsAether.icestone_poprocks, 1),
				new ItemStack(Items.SUGAR), new ItemStack(ItemsAether.icestone));
    }

	private void registerAltarRecipes()
	{
		// Enchanted Dart Shooter
		this.registerAltarRecipe(new AltarEnchantRecipe(4, new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.GOLDEN.ordinal()),
				new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.ENCHANTED.ordinal())));

		// Enchanted Darts
		this.registerAltarRecipe(new AltarEnchantRecipe(1, new ItemStack(ItemsAether.dart, 1, ItemDartType.GOLDEN.ordinal()),
				new ItemStack(ItemsAether.dart, 1, ItemDartType.ENCHANTED.ordinal())));

        // Enchanted Strawberry
		this.registerAltarRecipe(new AltarEnchantRecipe(2, new ItemStack(ItemsAether.blueberries),
                new ItemStack(ItemsAether.enchanted_blueberry)));

        // Rainbow Strawberry
		this.registerAltarRecipe(new AltarEnchantRecipe(4, new ItemStack(ItemsAether.wyndberry),
                new ItemStack(ItemsAether.enchanted_wyndberry)));

		// Tool Repair Recipes
		this.registerAltarRecipe(new AltarRepairRecipe());

		// Healing Stone
		this.registerAltarRecipe(new AltarEnchantRecipe(5, new ItemStack(ItemsAether.healing_stone_depleted),
				new ItemStack(ItemsAether.healing_stone)));
	}

	private static void registerShapelessRecipe(ItemStack output, Object... stacks)
	{
		GameRegistry.addShapelessRecipe(output, stacks);
	}

	private static void registerShapedRecipe(ItemStack output, Object... params)
	{
		GameRegistry.addShapedRecipe(output, params);
	}

	private static void registerSmeltingRecipe(ItemStack input, ItemStack output, float xp)
	{
		GameRegistry.addSmelting(input, output, xp);
	}

	@Override
	public void registerAltarEnchantment(ItemStack input, ItemStack output, int cost)
	{
		this.altarRegistry.addRecipe(new AltarEnchantRecipe(cost, input, output));
	}

	@Override
	public void registerAltarRecipe(IAltarRecipe recipe)
	{
		this.altarRegistry.addRecipe(recipe);
	}

	public AltarRegistry getAltarRegistry()
	{
		return this.altarRegistry;
	}
}
