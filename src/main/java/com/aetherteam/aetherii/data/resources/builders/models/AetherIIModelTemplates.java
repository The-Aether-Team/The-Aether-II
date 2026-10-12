package com.aetherteam.aetherii.data.resources.builders.models;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.Identifier;

import java.util.Optional;

public class AetherIIModelTemplates {
    public static final ModelTemplate EMPTY = ModelTemplates.create("block", TextureSlot.PARTICLE);
    public static final ModelTemplate PORTAL_NS = ModelTemplates.create("nether_portal_ns", "_ns", AetherIITextureSlots.PORTAL, TextureSlot.PARTICLE);
    public static final ModelTemplate PORTAL_EW = ModelTemplates.create("nether_portal_ew", "_ew", AetherIITextureSlots.PORTAL, TextureSlot.PARTICLE);
    public static final ModelTemplate THIN = ModelTemplates.create("thin_block", TextureSlot.ALL);
    public static final ModelTemplate DIRT_PATH = ModelTemplates.create("dirt_path", TextureSlot.BOTTOM, TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.SIDE);
    public static final ModelTemplate LEAVES = ModelTemplates.create("leaves", TextureSlot.ALL);
    public static final ModelTemplate LADDER = ModelTemplates.create("ladder", TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    public static final ModelTemplate POINTED_STONE_BLOCK = ModelTemplates.create("pointed_dripstone", TextureSlot.CROSS);
    public static final ModelTemplate CARPET_CUTOUT = ModelTemplates.create("carpet", TextureSlot.WOOL);
    public static final ModelTemplate MOSSY_CARPET_SIDE_CUTOUT = ModelTemplates.create("mossy_carpet_side", TextureSlot.SIDE);

    public static final ModelTemplate TEMPLATE_TINTED_GRASS = create("template_tinted_grass", TextureSlot.BOTTOM, TextureSlot.PARTICLE, TextureSlot.TOP, AetherIITextureSlots.TOP_1, AetherIITextureSlots.TOP_2, AetherIITextureSlots.TOP_3, TextureSlot.SIDE, AetherIITextureSlots.SIDE_OVERLAY_1, AetherIITextureSlots.SIDE_OVERLAY_2, AetherIITextureSlots.SIDE_OVERLAY_3);
    public static final ModelTemplate TEMPLATE_TINTED_TALL_GRASS = create("template_tinted_tall_grass", TextureSlot.CROSS, TextureSlot.PARTICLE, AetherIITextureSlots.OVERLAY_1, AetherIITextureSlots.OVERLAY_2, AetherIITextureSlots.OVERLAY_3);
    public static final ModelTemplate TEMPLATE_TRANSLUCENT_INNER_FACES = create("template_translucent_inner_faces", TextureSlot.PARTICLE, TextureSlot.NORTH, TextureSlot.SOUTH, TextureSlot.EAST, TextureSlot.WEST, TextureSlot.UP, TextureSlot.DOWN);
    public static final ModelTemplate TEMPLATE_EMISSIVE_SINGLE_FACE = create("emissive_single_face", TextureSlot.TEXTURE, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_CROSS = create("emissive_cross", TextureSlot.CROSS, TextureSlot.CROSS_EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_FLOWER_POT_CROSS = create("emissive_flower_pot_cross", TextureSlot.PLANT, TextureSlot.CROSS_EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_CUBE_ALL =  create("emissive_cube_all", TextureSlot.ALL, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_CUBE_COLUMN =  create("emissive_cube_column", TextureSlot.SIDE, TextureSlot.END, AetherIITextureSlots.EMISSIVE_SIDE, AetherIITextureSlots.EMISSIVE_END);
    public static final ModelTemplate TEMPLATE_EMISSIVE_CUBE_COLUMN_HORIZONTAL =  create("emissive_cube_column_horizontal", "_horizontal", TextureSlot.SIDE, TextureSlot.END, AetherIITextureSlots.EMISSIVE_SIDE, AetherIITextureSlots.EMISSIVE_END);
    public static final ModelTemplate TEMPLATE_EMISSIVE_STAIRS_STRAIGHT = create("emissive_stairs", TextureSlot.ALL, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_STAIRS_INNER = create("emissive_inner_stairs", "_inner", TextureSlot.ALL, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_STAIRS_OUTER = create("emissive_outer_stairs", "_outer", TextureSlot.ALL, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_SLAB_BOTTOM = create("emissive_slab", TextureSlot.ALL, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_SLAB_TOP = create("emissive_slab_top", "_top", TextureSlot.ALL, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate COLUMN_WALL_POST = create("template_column_wall_post", "_post", TextureSlot.END, TextureSlot.SIDE);
    public static final ModelTemplate COLUMN_WALL_LOW_SIDE = create("template_column_wall_side", "_side", TextureSlot.END, TextureSlot.SIDE);
    public static final ModelTemplate COLUMN_WALL_TALL_SIDE = create("template_column_wall_side_tall", "_side_tall", TextureSlot.END, TextureSlot.SIDE);
    public static final ModelTemplate EMISSIVE_COLUMN_WALL_POST = create("template_emissive_column_wall_post", "_post", TextureSlot.END, TextureSlot.SIDE, AetherIITextureSlots.EMISSIVE_END, AetherIITextureSlots.EMISSIVE_SIDE);
    public static final ModelTemplate EMISSIVE_COLUMN_WALL_LOW_SIDE = create("template_emissive_column_wall_side", "_side", TextureSlot.END, TextureSlot.SIDE, AetherIITextureSlots.EMISSIVE_END, AetherIITextureSlots.EMISSIVE_SIDE);
    public static final ModelTemplate EMISSIVE_COLUMN_WALL_TALL_SIDE = create("template_emissive_column_wall_side_tall", "_side_tall", TextureSlot.END, TextureSlot.SIDE, AetherIITextureSlots.EMISSIVE_END, AetherIITextureSlots.EMISSIVE_SIDE);
    public static final ModelTemplate EMISSIVE_COLUMN_WALL_INVENTORY = create("emissive_column_wall_inventory", "_inventory", TextureSlot.END, TextureSlot.SIDE, TextureSlot.WALL, AetherIITextureSlots.EMISSIVE_END, AetherIITextureSlots.EMISSIVE_SIDE, AetherIITextureSlots.EMISSIVE_WALL);
    public static final ModelTemplate TEMPLATE_EMISSIVE_BUTTON =  create("template_emissive_button", TextureSlot.TEXTURE, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_BUTTON_PRESSED =  create("template_emissive_button_pressed", "_pressed", TextureSlot.TEXTURE, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_BUTTON_INVENTORY =  create("template_emissive_button_inventory", "_inventory", TextureSlot.TEXTURE, AetherIITextureSlots.EMISSIVE);

    public static final ModelTemplate TRUNK_CENTER = create("template_trunk_center", "_center", TextureSlot.ALL);
    public static final ModelTemplate TRUNK_SIDE = create("template_trunk_side", "_side", TextureSlot.ALL);
    public static final ModelTemplate TRUNK_CORNER = create("template_trunk_corner", "_corner", TextureSlot.ALL);
    public static final ModelTemplate TRUNK_CENTER_TALL = create("template_trunk_center_tall", "_center_tall", TextureSlot.ALL);
    public static final ModelTemplate TRUNK_SIDE_TALL = create("template_trunk_side_tall", "_side_tall", TextureSlot.ALL);
    public static final ModelTemplate TRUNK_CORNER_TALL = create("template_trunk_corner_tall", "_corner_tall", TextureSlot.ALL);
    public static final ModelTemplate TRUNK_INVENTORY = create("template_trunk_inventory", "_inventory", TextureSlot.ALL);
    public static final ModelTemplate OVERLAID_LEAVES = create("template_overlaid_leaves", TextureSlot.BOTTOM, TextureSlot.SIDE);
    public static final ModelTemplate TINTED_OVERLAID_LEAVES = create("template_tinted_overlaid_leaves", TextureSlot.BOTTOM, TextureSlot.SIDE);
    public static final ModelTemplate OVERLAY = create("template_overlay", TextureSlot.TOP, TextureSlot.SIDE);
    public static final ModelTemplate MOSS_VINE = create("moss_vine", AetherIITextureSlots.VINE, TextureSlot.PARTICLE);
    public static final ModelTemplate ASYMMETRICAL_CROSS_EVEN = create("asymmetrical_cross_even", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OTHER, TextureSlot.PARTICLE);
    public static final ModelTemplate ASYMMETRICAL_CROSS_EVEN_MIRRORED = create("asymmetrical_cross_even_mirrored", "_mirrored", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OTHER, TextureSlot.PARTICLE);
    public static final ModelTemplate ASYMMETRICAL_CROSS_ODD = create("asymmetrical_cross_odd", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OTHER, TextureSlot.PARTICLE);
    public static final ModelTemplate ASYMMETRICAL_CROSS_ODD_MIRRORED = create("asymmetrical_cross_odd_mirrored", "_mirrored", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OTHER, TextureSlot.PARTICLE);
    public static final ModelTemplate POTTED_ASYMMETRICAL_CROSS_EVEN = create("flower_pot_asymmetrical_cross_even", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OTHER);
    public static final ModelTemplate POTTED_ASYMMETRICAL_CROSS_ODD = create("flower_pot_asymmetrical_cross_odd", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OTHER);
    public static final ModelTemplate LILICHIME = create("template_lilichime", TextureSlot.STEM, AetherIITextureSlots.PETALS, TextureSlot.PARTICLE);
    public static final ModelTemplate PLURACIAN = create("template_pluracian", TextureSlot.STEM, AetherIITextureSlots.LEAVES1, AetherIITextureSlots.LEAVES2, AetherIITextureSlots.PETAL_TOP, AetherIITextureSlots.PETAL_BOTTOM, TextureSlot.PARTICLE);
    public static final ModelTemplate POTTED_LILICHIME = create("flower_pot_lilichime", TextureSlot.STEM, AetherIITextureSlots.PETALS);
    public static final ModelTemplate POTTED_PLURACIAN = create("flower_pot_pluracian", TextureSlot.STEM, AetherIITextureSlots.LEAVES1, AetherIITextureSlots.LEAVES2, AetherIITextureSlots.PETAL_TOP, AetherIITextureSlots.PETAL_BOTTOM);
    public static final ModelTemplate BRYALINN_MOSS_FLOWERS_1 = create("template_bryalinn_moss_flowers_1", "_1", TextureSlot.FLOWERBED, TextureSlot.PARTICLE);
    public static final ModelTemplate BRYALINN_MOSS_FLOWERS_2 = create("template_bryalinn_moss_flowers_2", "_2", TextureSlot.FLOWERBED, TextureSlot.PARTICLE);
    public static final ModelTemplate BRYALINN_MOSS_FLOWERS_3 = create("template_bryalinn_moss_flowers_3", "_3", TextureSlot.FLOWERBED, TextureSlot.PARTICLE);
    public static final ModelTemplate BRYALINN_MOSS_FLOWERS_4 = create("template_bryalinn_moss_flowers_4", "_4", TextureSlot.FLOWERBED, TextureSlot.PARTICLE);
    public static final ModelTemplate HOLPUPEA_1 = create("template_holpupea_1", "_1", TextureSlot.FLOWERBED, TextureSlot.STEM, TextureSlot.PARTICLE);
    public static final ModelTemplate HOLPUPEA_2 = create("template_holpupea_2", "_2", TextureSlot.FLOWERBED, TextureSlot.STEM, TextureSlot.PARTICLE);
    public static final ModelTemplate HOLPUPEA_3 = create("template_holpupea_3", "_3", TextureSlot.FLOWERBED, TextureSlot.STEM, TextureSlot.PARTICLE);
    public static final ModelTemplate HOLPUPEA_4 = create("template_holpupea_4", "_4", TextureSlot.FLOWERBED, TextureSlot.STEM, TextureSlot.PARTICLE);
    public static final ModelTemplate TARAHESP_FLOWERS_1 = create("template_tarahesp_flowers_1", "_1", AetherIITextureSlots.TARAHESP_FLOWERS_PURPLE, AetherIITextureSlots.TARAHESP_FLOWERS_WHITE, TextureSlot.PARTICLE);
    public static final ModelTemplate TARAHESP_FLOWERS_2 = create("template_tarahesp_flowers_2", "_2", AetherIITextureSlots.TARAHESP_FLOWERS_PURPLE, AetherIITextureSlots.TARAHESP_FLOWERS_WHITE, TextureSlot.PARTICLE);
    public static final ModelTemplate TARAHESP_FLOWERS_3 = create("template_tarahesp_flowers_3", "_3", AetherIITextureSlots.TARAHESP_FLOWERS_PURPLE, AetherIITextureSlots.TARAHESP_FLOWERS_WHITE, TextureSlot.PARTICLE);
    public static final ModelTemplate TARAHESP_FLOWERS_4 = create("template_tarahesp_flowers_4", "_4", AetherIITextureSlots.TARAHESP_FLOWERS_PURPLE, AetherIITextureSlots.TARAHESP_FLOWERS_WHITE, TextureSlot.PARTICLE);
    public static final ModelTemplate AMBRELINN_MOSS_VINE = create("template_ambrelinn_moss_vine", AetherIITextureSlots.VINE, TextureSlot.PARTICLE);
    public static final ModelTemplate AETHER_BUSH = create("template_aether_bush", TextureSlot.TEXTURE, TextureSlot.CROSS, AetherIITextureSlots.CROSS_OVERLAY, TextureSlot.PARTICLE);
    public static final ModelTemplate BLUEBERRY_BUSH_STEM = create("template_blueberry_bush_stem", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OVERLAY, TextureSlot.PARTICLE);
    public static final ModelTemplate BLUEBERRY_BUSH = create("template_blueberry_bush", TextureSlot.TEXTURE, AetherIITextureSlots.OVERLAY, TextureSlot.CROSS, AetherIITextureSlots.CROSS_OVERLAY, TextureSlot.PARTICLE);
    public static final ModelTemplate POTTED_AETHER_BUSH = create("flower_pot_aether_bush", TextureSlot.TEXTURE, TextureSlot.CROSS, AetherIITextureSlots.CROSS_OVERLAY);
    public static final ModelTemplate POTTED_BLUEBERRY_BUSH_STEM = create("flower_pot_blueberry_bush_stem", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OVERLAY);
    public static final ModelTemplate POTTED_BLUEBERRY_BUSH = create("flower_pot_blueberry_bush", TextureSlot.TEXTURE, AetherIITextureSlots.OVERLAY, TextureSlot.CROSS, AetherIITextureSlots.CROSS_OVERLAY);
    public static final ModelTemplate TWIG_1 = create("template_twig_1", "_1", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.PARTICLE);
    public static final ModelTemplate TWIG_2 = create("template_twig_2", "_2", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.PARTICLE);
    public static final ModelTemplate ROCK_1 = create("template_rock_1", "_1", TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    public static final ModelTemplate ROCK_2 = create("template_rock_2", "_2", TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    public static final ModelTemplate ROCK_3 = create("template_rock_3", "_3", TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    public static final ModelTemplate HANGING_UNDERGROWTH = create("template_hanging_undergrowth", AetherIITextureSlots.VINE, TextureSlot.PARTICLE);
    public static final ModelTemplate ROTSHROOM_CLUSTER = create("template_rotshroom_cluster", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate DOOR_BOTTOM_LEFT = create("door_bottom_left", "_bottom_left", TextureSlot.FRONT, TextureSlot.SIDE, TextureSlot.END);
    public static final ModelTemplate DOOR_BOTTOM_LEFT_OPEN = create("door_bottom_left_open", "_bottom_left_open", TextureSlot.FRONT, TextureSlot.SIDE, TextureSlot.END);
    public static final ModelTemplate DOOR_BOTTOM_RIGHT = create("door_bottom_right", "_bottom_right", TextureSlot.FRONT, TextureSlot.SIDE, TextureSlot.END);
    public static final ModelTemplate DOOR_BOTTOM_RIGHT_OPEN = create("door_bottom_right_open", "_bottom_right_open", TextureSlot.FRONT, TextureSlot.SIDE, TextureSlot.END);
    public static final ModelTemplate DOOR_TOP_LEFT = create("door_top_left", "_top_left", TextureSlot.FRONT, TextureSlot.SIDE, TextureSlot.END);
    public static final ModelTemplate DOOR_TOP_LEFT_OPEN = create("door_top_left_open", "_top_left_open", TextureSlot.FRONT, TextureSlot.SIDE, TextureSlot.END);
    public static final ModelTemplate DOOR_TOP_RIGHT = create("door_top_right", "_top_right", TextureSlot.FRONT, TextureSlot.SIDE, TextureSlot.END);
    public static final ModelTemplate DOOR_TOP_RIGHT_OPEN = create("door_top_right_open", "_top_right_open", TextureSlot.FRONT, TextureSlot.SIDE, TextureSlot.END);
    public static final ModelTemplate ORIENTABLE_SECRET_TRAPDOOR_TOP = create("template_orientable_secret_trapdoor_top", "_top", TextureSlot.TEXTURE);
    public static final ModelTemplate ORIENTABLE_SECRET_TRAPDOOR_BOTTOM = create("template_orientable_secret_trapdoor_bottom", "_bottom", TextureSlot.TEXTURE);
    public static final ModelTemplate ORIENTABLE_SECRET_TRAPDOOR_OPEN = create("template_orientable_secret_trapdoor_open", "_open", TextureSlot.TEXTURE);
    public static final ModelTemplate ARKENIUM_LANTERN = create("template_arkenium_lantern", TextureSlot.LANTERN);
    public static final ModelTemplate HANGING_ARKENIUM_LANTERN = create("template_hanging_arkenium_lantern", "_hanging", TextureSlot.LANTERN);
    public static final ModelTemplate RUSTIC_ARKENIUM_LANTERN = create("template_rustic_arkenium_lantern", TextureSlot.LANTERN);
    public static final ModelTemplate HANGING_RUSTIC_ARKENIUM_LANTERN = create("template_hanging_rustic_arkenium_lantern", "_hanging", TextureSlot.LANTERN);
    public static final ModelTemplate TALL_TORCH = create("template_tall_torch", TextureSlot.TORCH);
    public static final ModelTemplate TALL_WALL_TORCH = create("template_tall_wall_torch", TextureSlot.TORCH);
    public static final ModelTemplate AMBER_HOURGLASS = create("template_amber_hourglass", TextureSlot.CROSS, TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM, TextureSlot.INNER_TOP, AetherIITextureSlots.INNER_BOTTOM);
    public static final ModelTemplate ALTAR = create("template_altar", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM, AetherIITextureSlots.BASE_TOP, AetherIITextureSlots.BASE_BOTTOM);
    public static final ModelTemplate ARTISANS_BENCH = create("template_artisans_bench", TextureSlot.NORTH, TextureSlot.SOUTH, TextureSlot.EAST, TextureSlot.WEST, TextureSlot.UP, TextureSlot.DOWN, TextureSlot.INSIDE, AetherIITextureSlots.SAW, TextureSlot.PARTICLE);
    public static final ModelTemplate ARKENIUM_FORGE = create("template_arkenium_forge", TextureSlot.SIDE, AetherIITextureSlots.BASE_TOP, AetherIITextureSlots.ANVIL_FRONT, AetherIITextureSlots.ANVIL_SIDE, AetherIITextureSlots.ANVIL_BOTTOM, TextureSlot.PARTICLE);
    public static final ModelTemplate ARILUM_LANTERN = create("template_arilum_lantern", TextureSlot.TEXTURE, TextureSlot.INSIDE);
    public static final ModelTemplate AMBROSIUM_CAMPFIRE_OFF = create("template_ambrosium_campfire_off", AetherIITextureSlots.STONE, AetherIITextureSlots.LOG, TextureSlot.PARTICLE);
    public static final ModelTemplate AMBROSIUM_CAMPFIRE = create("template_ambrosium_campfire", AetherIITextureSlots.STONE, AetherIITextureSlots.LOG, AetherIITextureSlots.LIT, TextureSlot.FIRE, TextureSlot.PARTICLE);
    public static final ModelTemplate SENTRY_TRAP =  create("template_sentry_trap", TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE, AetherIITextureSlots.EMISSIVE_TOP);
    public static final ModelTemplate PRAYER_CANDLE = create("template_prayer_candle", TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    public static final ModelTemplate GUARDIAN_PEW = create("template_guardian_pew", TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    public static final ModelTemplate GUARDIAN_DONATION_BOX = create("template_guardian_donation_box", TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    public static final ModelTemplate ANIMAL_STASH = create("template_animal_stash", TextureSlot.TEXTURE, TextureSlot.PARTICLE).extend().build();
    public static final ModelTemplate ANIMAL_STASH_OPEN = create("template_animal_stash_open", TextureSlot.TEXTURE, TextureSlot.PARTICLE).extend().build();

    public static final ModelTemplate MIRRORED_FLAT_ITEM = createItem("mirrored_flat_item", TextureSlot.LAYER0);
    public static final ModelTemplate DART_SHOOTER = createItem("handheld_dart_shooter", TextureSlot.LAYER0);
    public static final ModelTemplate DART_SHOOTER_TWO_LAYER = createItem("handheld_dart_shooter", TextureSlot.LAYER0, TextureSlot.LAYER1);
    public static final ModelTemplate USING_DART_SHOOTER_TWO_LAYER = createItem("using_dart_shooter", TextureSlot.LAYER0, TextureSlot.LAYER1);
    public static final ModelTemplate HAMMER_OF_DEMOLITION_HANDLE = createItem("template_hammer_of_demolition_handle", "_handle", TextureSlot.LAYER0, TextureSlot.LAYER1);
    public static final ModelTemplate HAMMER_OF_DEMOLITION_HEAD = createItem("template_hammer_of_demolition_head", "_head", TextureSlot.TEXTURE, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate HAMMER_OF_DEMOLITION_HEAD_READY = createItem("template_hammer_of_demolition_head_ready", "_head_ready", TextureSlot.TEXTURE, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate HAMMER_OF_DEMOLITION_HEAD_DEPLOYED = createItem("template_hammer_of_demolition_head_deployed", "_head_deployed", TextureSlot.TEXTURE, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate ALKAHEST_PURIFIER_INVENTORY = createItem("template_alkahest_purifier", TextureSlot.PARTICLE);
    public static final ModelTemplate THERAN_GLOBE_INVENTORY = createItem("template_theran_globe", TextureSlot.PARTICLE);
    public static final ModelTemplate SENTRY_SPAWNER_INVENTORY = createItem("template_sentry_spawner", TextureSlot.PARTICLE);
    public static final ModelTemplate ABANDONED_BAG_INVENTORY = createItem("template_abandoned_bag", TextureSlot.PARTICLE);
    public static final ModelTemplate FUNGAL_CACHE_INVENTORY = createItem("template_fungal_cache", TextureSlot.PARTICLE);
    public static final ModelTemplate LOCKED_BLOCK_INVENTORY = createItem("locked_block_inventory", AetherIITextureSlots.FACE, AetherIITextureSlots.OVERLAY);
    public static final ModelTemplate VASE_INVENTORY = createItem("template_vase", TextureSlot.PARTICLE);

    public static final ModelTemplate TRANSLUCENT_FLAT_ITEM = ModelTemplates.FLAT_ITEM;
    public static final ModelTemplate MEDIUM_CRYSTAL = ModelTemplates.createItem("medium_amethyst_bud", TextureSlot.LAYER0);
    public static final ModelTemplate LARGE_CRYSTAL = ModelTemplates.createItem("large_amethyst_bud", TextureSlot.LAYER0);
    public static final ModelTemplate FULL_CRYSTAL = ModelTemplates.createItem("amethyst_cluster", TextureSlot.LAYER0);
    public static final ModelTemplate POINTED_STONE = ModelTemplates.createItem("pointed_dripstone", TextureSlot.LAYER0);

    public static final ModelTemplate AERCLOUD_GLIDER_CLOSED = createItem("aercloud_glider_closed", "_closed", AetherIITextureSlots.MAIN, AetherIITextureSlots.SIDE1, AetherIITextureSlots.SIDE2);
    public static final ModelTemplate AERCLOUD_GLIDER_OPEN = createItem("aercloud_glider_open", "_open", AetherIITextureSlots.MAIN, AetherIITextureSlots.SIDE1, AetherIITextureSlots.SIDE2);

    public static ModelTemplate create(TextureSlot... textureSlot) {
        return new ModelTemplate(Optional.empty(), Optional.empty(), textureSlot);
    }

    public static ModelTemplate create(String path, TextureSlot... textureSlot) {
        return new ModelTemplate(Optional.of(decorateBlockModelLocation(path)), Optional.empty(), textureSlot);
    }

    public static ModelTemplate create(String path, String suffix, TextureSlot... textureSlot) {
        return new ModelTemplate(Optional.of(decorateBlockModelLocation(path)), Optional.of(suffix), textureSlot);
    }

    public static ModelTemplate createItem(String path, TextureSlot... textureSlot) {
        return new ModelTemplate(Optional.of(decorateItemModelLocation(path)), Optional.empty(), textureSlot);
    }

    public static ModelTemplate createItem(String path, String suffix, TextureSlot... textureSlot) {
        return new ModelTemplate(Optional.of(decorateItemModelLocation(path)), Optional.of(suffix), textureSlot);
    }

    /**
     * Based on {@link ModelLocationUtils#decorateBlockModelLocation(String)}
     */
    public static Identifier decorateBlockModelLocation(String path) {
        return Identifier.fromNamespaceAndPath(AetherII.MODID, "block/" + path);
    }

    /**
     * Based on {@link ModelLocationUtils#decorateItemModelLocation(String)}
     */
    public static Identifier decorateItemModelLocation(String path) {
        return Identifier.fromNamespaceAndPath(AetherII.MODID, "item/" + path);
    }
}