package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.AetherIIFluids;
import com.aetherteam.aetherii.block.AetherIIWoodTypes;
import com.aetherteam.aetherii.client.particle.AetherIIParticleFactories;
import com.aetherteam.aetherii.client.renderer.AetherIIOverlays;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderers;
import com.aetherteam.aetherii.client.renderer.item.properties.AetherIIItemModelProperties;
import com.aetherteam.aetherii.client.sprite.AetherIISpriteSourceTypes;
import com.aetherteam.aetherii.inventory.menu.AetherIIMenuTypes;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Supplier;

public class AetherIIClient {
    public static void clientInit(IEventBus bus) {
        AetherIISpriteSourceTypes.init();
        AetherIIClientEventListeners.listen(MinecraftForge.EVENT_BUS);
        bus.addListener(AetherIIClient::clientSetup);
        bus.addListener(AetherIIAtlases::registerReloadListeners);
        bus.addListener(AetherIIColorResolvers::registerBlockColor);
        bus.addListener(AetherIIColorResolvers::registerItemColor);
        bus.addListener(AetherIIParticleFactories::registerParticleFactories);
        bus.addListener(AetherIIRenderers::registerEntityRenderers);
        bus.addListener(AetherIIRenderers::registerAddLayer);
        bus.addListener(AetherIIRenderers::registerLayerDefinition);
        bus.addListener(AetherIIRenderers::registerAdditionalModels);
        bus.addListener(AetherIIRenderers::bakeModels);
        bus.addListener(AetherIIOverlays::registerOverlays);
        bus.addListener(AetherIIClientTooltips::registerClientTooltipComponents);
        bus.addListener(AetherIIKeyMappings::registerKeyMappings);
        bus.addListener(AetherIIRecipeBookCategories::registerRecipeBookSearchCategories);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            AetherIIAtlases.registerSkyrootChestAtlases();
            registerSignMaterials();
            AetherIIMenuTypes.registerMenuScreens();
            registerRenderLayers();
            AetherIIItemModelProperties.registerItemProperties();
        });
    }

    @SuppressWarnings("deprecation")
    private static void registerRenderLayers() {
        ItemBlockRenderTypes.setRenderLayer(AetherIIFluids.ALKAHEST.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(AetherIIFluids.FLOWING_ALKAHEST.get(), RenderType.translucent());

        setLayer(RenderType.translucent(),
                AetherIIBlocks.AETHER_PORTAL,
                AetherIIBlocks.HESTVEIL,
                AetherIIBlocks.CRUDE_SCATTERGLASS,
                AetherIIBlocks.ARCTIC_ICE,
                AetherIIBlocks.FRAGILE_ARCTIC_ICE,
                AetherIIBlocks.COLD_AERCLOUD,
                AetherIIBlocks.BLUE_AERCLOUD,
                AetherIIBlocks.GOLDEN_AERCLOUD,
                AetherIIBlocks.GREEN_AERCLOUD,
                AetherIIBlocks.PURPLE_AERCLOUD,
                AetherIIBlocks.STORM_AERCLOUD,
                AetherIIBlocks.QUICKSOIL_GLASS,
                AetherIIBlocks.TILED_QUICKSOIL_GLASS,
                AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS,
                AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS,
                AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS,
                AetherIIBlocks.SCATTERGLASS,
                AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS,
                AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS,
                AetherIIBlocks.QUICKSOIL_GLASS_PANE,
                AetherIIBlocks.TILED_QUICKSOIL_GLASS_PANE,
                AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS_PANE,
                AetherIIBlocks.CRUDE_SCATTERGLASS_PANE,
                AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS_PANE,
                AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS_PANE,
                AetherIIBlocks.SCATTERGLASS_PANE,
                AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS_PANE,
                AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS_PANE);

        setLayer(RenderType.cutoutMipped(),
                AetherIIBlocks.AETHER_GRASS_BLOCK,
                AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK,
                AetherIIBlocks.SKYROOT_LEAVES,
                AetherIIBlocks.SKYPLANE_LEAVES,
                AetherIIBlocks.SKYBIRCH_LEAVES,
                AetherIIBlocks.SKYPINE_LEAVES,
                AetherIIBlocks.WISPROOT_LEAVES,
                AetherIIBlocks.WISPTOP_LEAVES,
                AetherIIBlocks.GREATROOT_LEAVES,
                AetherIIBlocks.GREATOAK_LEAVES,
                AetherIIBlocks.GREATBOA_LEAVES,
                AetherIIBlocks.AMBEROOT_LEAVES,
                AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES,
                AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES,
                AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES,
                AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES,
                AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES,
                AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES,
                AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES,
                AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES,
                AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES,
                AetherIIBlocks.UNDERGROWTH_LEAVES);

        setLayer(RenderType.cutout(),
                AetherIIBlocks.SKY_ROOTS,
                AetherIIBlocks.POINTED_HOLYSTONE,
                AetherIIBlocks.POINTED_ICHORITE,
                AetherIIBlocks.LARGE_ARCTIC_ICE_CRYSTAL,
                AetherIIBlocks.MEDIUM_ARCTIC_ICE_CRYSTAL,
                AetherIIBlocks.SMALL_ARCTIC_ICE_CRYSTAL,
                AetherIIBlocks.CORROBONITE_CLUSTER,
                AetherIIBlocks.BRYALINN_MOSS_VINES,
                AetherIIBlocks.SHAYELINN_MOSS_VINES,
                AetherIIBlocks.AMBRELINN_MOSS_VINES,
                AetherIIBlocks.TARAHESP_FLOWERS,
                AetherIIBlocks.WOVEN_SKYROOT_STICKS,
                AetherIIBlocks.SKYROOT_LEAF_PILE,
                AetherIIBlocks.SKYPLANE_LEAF_PILE,
                AetherIIBlocks.SKYBIRCH_LEAF_PILE,
                AetherIIBlocks.SKYPINE_LEAF_PILE,
                AetherIIBlocks.WISPROOT_LEAF_PILE,
                AetherIIBlocks.WISPTOP_LEAF_PILE,
                AetherIIBlocks.GREATROOT_LEAF_PILE,
                AetherIIBlocks.GREATOAK_LEAF_PILE,
                AetherIIBlocks.GREATBOA_LEAF_PILE,
                AetherIIBlocks.AMBEROOT_LEAF_PILE,
                AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_PILE,
                AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_PILE,
                AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_PILE,
                AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_PILE,
                AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_PILE,
                AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_PILE,
                AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_PILE,
                AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_PILE,
                AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_PILE,
                AetherIIBlocks.SKYROOT_SAPLING,
                AetherIIBlocks.SKYPLANE_SAPLING,
                AetherIIBlocks.SKYBIRCH_SAPLING,
                AetherIIBlocks.SKYPINE_SAPLING,
                AetherIIBlocks.WISPROOT_SAPLING,
                AetherIIBlocks.WISPTOP_SAPLING,
                AetherIIBlocks.GREATROOT_SAPLING,
                AetherIIBlocks.GREATOAK_SAPLING,
                AetherIIBlocks.GREATBOA_SAPLING,
                AetherIIBlocks.AMBEROOT_SAPLING,
                AetherIIBlocks.POTTED_SKYROOT_SAPLING,
                AetherIIBlocks.POTTED_SKYPLANE_SAPLING,
                AetherIIBlocks.POTTED_SKYBIRCH_SAPLING,
                AetherIIBlocks.POTTED_SKYPINE_SAPLING,
                AetherIIBlocks.POTTED_WISPROOT_SAPLING,
                AetherIIBlocks.POTTED_WISPTOP_SAPLING,
                AetherIIBlocks.POTTED_GREATROOT_SAPLING,
                AetherIIBlocks.POTTED_GREATOAK_SAPLING,
                AetherIIBlocks.POTTED_GREATBOA_SAPLING,
                AetherIIBlocks.POTTED_AMBEROOT_SAPLING,
                AetherIIBlocks.SHORT_AETHER_GRASS,
                AetherIIBlocks.MEDIUM_AETHER_GRASS,
                AetherIIBlocks.TALL_AETHER_GRASS,
                AetherIIBlocks.AETHER_FERN,
                AetherIIBlocks.SHIELD_FERN,
                AetherIIBlocks.HESPEROSE,
                AetherIIBlocks.TARABLOOM,
                AetherIIBlocks.POASPROUT,
                AetherIIBlocks.LILICHIME,
                AetherIIBlocks.PLURACIAN,
                AetherIIBlocks.SATIVAL_SHOOT,
                AetherIIBlocks.HOLPUPEA,
                AetherIIBlocks.BLADE_POA,
                AetherIIBlocks.VALKYRIE_SPROUT,
                AetherIIBlocks.MAGNETIC_SHROOM,
                AetherIIBlocks.TANGLED_BRANCHES,
                AetherIIBlocks.SPOTTED_MAGNETIC_SHROOM_BLOCK,
                AetherIIBlocks.ARILUM_SHOOT,
                AetherIIBlocks.ARILUM,
                AetherIIBlocks.ARILUM_PLANT,
                AetherIIBlocks.BLOOMING_ARILUM,
                AetherIIBlocks.BLOOMING_ARILUM_PLANT,
                AetherIIBlocks.AECHOR_CUTTING,
                AetherIIBlocks.CARRION_CUTTING,
                AetherIIBlocks.BRETTL_PLANT,
                AetherIIBlocks.BRETTL_PLANT_TIP,
                AetherIIBlocks.BRETTL_FLOWER,
                AetherIIBlocks.POTTED_MAGNETIC_SHROOM,
                AetherIIBlocks.POTTED_AETHER_FERN,
                AetherIIBlocks.POTTED_SHIELD_FERN,
                AetherIIBlocks.POTTED_HESPEROSE,
                AetherIIBlocks.POTTED_TARABLOOM,
                AetherIIBlocks.POTTED_POASPROUT,
                AetherIIBlocks.POTTED_PLURACIAN,
                AetherIIBlocks.POTTED_SATIVAL_SHOOT,
                AetherIIBlocks.POTTED_LILICHIME,
                AetherIIBlocks.POTTED_BLADE_POA,
                AetherIIBlocks.POTTED_AECHOR_CUTTING,
                AetherIIBlocks.POTTED_CARRION_CUTTING,
                AetherIIBlocks.AETHER_BUSH,
                AetherIIBlocks.BLUEBERRY_BUSH,
                AetherIIBlocks.BLUEBERRY_BUSH_STEM,
                AetherIIBlocks.POTTED_AETHER_BUSH,
                AetherIIBlocks.POTTED_BLUEBERRY_BUSH,
                AetherIIBlocks.POTTED_BLUEBERRY_BUSH_STEM,
                AetherIIBlocks.ORANGE_TREE,
                AetherIIBlocks.SKYROOT_DOOR,
                AetherIIBlocks.SKYROOT_TRAPDOOR,
                AetherIIBlocks.SECRET_SKYROOT_DOOR,
                AetherIIBlocks.SECRET_SKYROOT_TRAPDOOR,
                AetherIIBlocks.GREATROOT_DOOR,
                AetherIIBlocks.GREATROOT_TRAPDOOR,
                AetherIIBlocks.SECRET_GREATROOT_DOOR,
                AetherIIBlocks.SECRET_GREATROOT_TRAPDOOR,
                AetherIIBlocks.WISPROOT_DOOR,
                AetherIIBlocks.WISPROOT_TRAPDOOR,
                AetherIIBlocks.SECRET_WISPROOT_DOOR,
                AetherIIBlocks.SECRET_WISPROOT_TRAPDOOR,
                AetherIIBlocks.AMBEROOT_DOOR,
                AetherIIBlocks.AMBEROOT_TRAPDOOR,
                AetherIIBlocks.SECRET_AMBEROOT_DOOR,
                AetherIIBlocks.SECRET_AMBEROOT_TRAPDOOR,
                AetherIIBlocks.ARKENIUM_DOOR,
                AetherIIBlocks.ARKENIUM_TRAPDOOR,
                AetherIIBlocks.AMBROSIUM_TORCH,
                AetherIIBlocks.AMBROSIUM_WALL_TORCH,
                AetherIIBlocks.ARKENIUM_LANTERN,
                AetherIIBlocks.RUSTIC_ARKENIUM_LANTERN,
                AetherIIBlocks.ARKENIUM_CHAIN,
                AetherIIBlocks.ARKENIUM_BARS,
                AetherIIBlocks.FLORAL_ARKENIUM_BARS,
                AetherIIBlocks.PATTERNED_ARKENIUM_BARS,
                AetherIIBlocks.CURVED_ARKENIUM_BARS,
                AetherIIBlocks.RUSTIC_ARKENIUM_BARS,
                AetherIIBlocks.RUSTIC_FLORAL_ARKENIUM_BARS,
                AetherIIBlocks.RUSTIC_PATTERNED_ARKENIUM_BARS,
                AetherIIBlocks.RUSTIC_CURVED_ARKENIUM_BARS,
                AetherIIBlocks.AMBER_HOURGLASS,
                AetherIIBlocks.AMBROSIUM_CAMPFIRE,
                AetherIIBlocks.OUTPOST_CAMPFIRE,
                AetherIIBlocks.SKYROOT_LADDER,
                AetherIIBlocks.UNDERGROWTH_VINES,
                AetherIIBlocks.HANGING_UNDERGROWTH,
                AetherIIBlocks.HANGING_UNDERGROWTH_PLANT,
                AetherIIBlocks.ROTSHROOM,
                AetherIIBlocks.POTTED_ROTSHROOM,
                AetherIIBlocks.ROTSHROOM_CLUSTER,
                AetherIIBlocks.ROTSHROOM_TOADSTOOL,
                AetherIIBlocks.SHELF_ROTSHROOM,
                AetherIIBlocks.ROTGROWTH_VINES,
                AetherIIBlocks.SENTRY_BRICKS,
                AetherIIBlocks.SENTRY_BRICK_STAIRS,
                AetherIIBlocks.SENTRY_BRICK_SLAB,
                AetherIIBlocks.SENTRY_BRICK_WALL,
                AetherIIBlocks.SENTRY_BUTTON,
                AetherIIBlocks.SENTRY_LIGHTSTONE,
                AetherIIBlocks.SENTRY_FLAGSTONES,
                AetherIIBlocks.SENTRY_TILE,
                AetherIIBlocks.SENTRY_BASE_BRICKS,
                AetherIIBlocks.SENTRY_CAPSTONE_BRICKS,
                AetherIIBlocks.SENTRY_BASE_PILLAR,
                AetherIIBlocks.SENTRY_CAPSTONE_PILLAR,
                AetherIIBlocks.SENTRY_PILLAR,
                AetherIIBlocks.SENTRY_TRAP,
                AetherIIBlocks.SENTRY_CRATE,
                AetherIIBlocks.SENTRY_SPAWNER,
                AetherIIBlocks.GUARDIAN_DONATION_BOX,
                AetherIIBlocks.ANIMAL_STASH,
                AetherIIBlocks.MURAL,
                AetherIIBlocks.ABANDONED_BAG,
                AetherIIBlocks.FUNGAL_CACHE,
                AetherIIBlocks.SAGE_CHEST,
                AetherIIBlocks.SKYROOT_BED,
                AetherIIBlocks.WHITE_SKYROOT_BED,
                AetherIIBlocks.ORANGE_SKYROOT_BED,
                AetherIIBlocks.MAGENTA_SKYROOT_BED,
                AetherIIBlocks.LIGHT_BLUE_SKYROOT_BED,
                AetherIIBlocks.YELLOW_SKYROOT_BED,
                AetherIIBlocks.LIME_SKYROOT_BED,
                AetherIIBlocks.PINK_SKYROOT_BED,
                AetherIIBlocks.GRAY_SKYROOT_BED,
                AetherIIBlocks.LIGHT_GRAY_SKYROOT_BED,
                AetherIIBlocks.CYAN_SKYROOT_BED,
                AetherIIBlocks.PURPLE_SKYROOT_BED,
                AetherIIBlocks.BLUE_SKYROOT_BED,
                AetherIIBlocks.BROWN_SKYROOT_BED,
                AetherIIBlocks.GREEN_SKYROOT_BED,
                AetherIIBlocks.RED_SKYROOT_BED,
                AetherIIBlocks.BLACK_SKYROOT_BED,
                AetherIIBlocks.SKYROOT_SIGN,
                AetherIIBlocks.SKYROOT_WALL_SIGN,
                AetherIIBlocks.SKYROOT_HANGING_SIGN,
                AetherIIBlocks.SKYROOT_WALL_HANGING_SIGN,
                AetherIIBlocks.GREATROOT_SIGN,
                AetherIIBlocks.GREATROOT_WALL_SIGN,
                AetherIIBlocks.GREATROOT_HANGING_SIGN,
                AetherIIBlocks.GREATROOT_WALL_HANGING_SIGN,
                AetherIIBlocks.WISPROOT_SIGN,
                AetherIIBlocks.WISPROOT_WALL_SIGN,
                AetherIIBlocks.WISPROOT_HANGING_SIGN,
                AetherIIBlocks.WISPROOT_WALL_HANGING_SIGN,
                AetherIIBlocks.AMBEROOT_SIGN,
                AetherIIBlocks.AMBEROOT_WALL_SIGN,
                AetherIIBlocks.AMBEROOT_HANGING_SIGN,
                AetherIIBlocks.AMBEROOT_WALL_HANGING_SIGN);
    }

    private static void registerSignMaterials() {
        registerSignMaterial(AetherIIWoodTypes.SKYROOT, "skyroot");
        registerSignMaterial(AetherIIWoodTypes.GREATROOT, "greatroot");
        registerSignMaterial(AetherIIWoodTypes.WISPROOT, "wisproot");
        registerSignMaterial(AetherIIWoodTypes.AMBEROOT, "amberoot");
    }

    private static void registerSignMaterial(WoodType woodType, String path) {
        Sheets.SIGN_MATERIALS.put(woodType, new Material(Sheets.SIGN_SHEET, new ResourceLocation(AetherII.MODID, "entity/signs/" + path)));
        Sheets.HANGING_SIGN_MATERIALS.put(woodType, new Material(Sheets.SIGN_SHEET, new ResourceLocation(AetherII.MODID, "entity/signs/hanging/" + path)));
    }

    @SafeVarargs
    @SuppressWarnings("deprecation")
    private static void setLayer(RenderType renderType, Supplier<? extends Block>... blocks) {
        for (Supplier<? extends Block> block : blocks) {
            ItemBlockRenderTypes.setRenderLayer(block.get(), renderType);
        }
    }
}
