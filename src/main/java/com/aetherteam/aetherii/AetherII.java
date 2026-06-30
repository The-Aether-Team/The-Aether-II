package com.aetherteam.aetherii;

import com.aetherteam.aetherii.advancement.AetherIIAdvancementSoundOverrides;
import com.aetherteam.aetherii.advancement.trigger.AetherIIAdvancementTriggers;
import com.aetherteam.aetherii.api.ItemReinforcement;
import com.aetherteam.aetherii.command.AetherIICommands;
import com.aetherteam.aetherii.loot.conditions.AetherIILootConditions;
import com.aetherteam.aetherii.network.AetherIINetwork;
import com.aetherteam.aetherii.recipe.AetherIIRecipeSerializers;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import com.aetherteam.aetherii.item.components.JukeboxSong;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;

import com.aetherteam.aetherii.advancement.predicate.AetherIIEntitySubPredicates;

import com.aetherteam.aetherii.entity.variant.GlitterwingVariant;
import com.aetherteam.aetherii.entity.variant.ShroudwingVariant;
import com.aetherteam.aetherii.entity.variant.SkyrootLizardVariant;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.api.guidebook.EffectsEntry;
import com.aetherteam.aetherii.api.guidebook.ExplorationEntry;
import com.aetherteam.aetherii.api.guidebook.RewardWrapper;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.api.styles.StyleDesign;
import com.aetherteam.aetherii.api.styles.StyleMaterial;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.AetherIICauldronInteractions;
import com.aetherteam.aetherii.block.AetherIIDispenseBehaviors;
import com.aetherteam.aetherii.block.AetherIIFluidTypes;
import com.aetherteam.aetherii.block.AetherIIFluids;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.client.AetherIIClient;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.data.AetherIIData;
import com.aetherteam.aetherii.data.ReloadListeners;
import com.aetherteam.aetherii.data.resources.AetherIIMobCategory;
import com.aetherteam.aetherii.data.resources.registries.AetherIIMurals;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.brain.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.ai.brain.sensor.AetherIISensorTypes;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.inventory.AetherIIRecipeBookTypes;
import com.aetherteam.aetherii.inventory.menu.AetherIIMenuTypes;
import com.aetherteam.aetherii.item.AetherIICreativeTabs;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.loot.functions.AetherIILootFunctions;
import com.aetherteam.aetherii.loot.modifiers.AetherIILootModifiers;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.world.AetherIIPoi;
import com.aetherteam.aetherii.world.density.AetherIIDensityFunctionTypes;
import com.aetherteam.aetherii.world.feature.AetherIIFeatures;
import com.aetherteam.aetherii.world.feature.modifier.filter.AetherIIPlacementModifierTypes;
import com.aetherteam.aetherii.world.feature.modifier.predicate.AetherIIBlockPredicateTypes;
import com.aetherteam.aetherii.world.structure.piece.AetherIIStructurePieceTypes;
import com.aetherteam.aetherii.world.structure.type.AetherIIStructureTypes;
import com.aetherteam.aetherii.world.structure.pool.AetherIIPoolElementTypes;
import com.aetherteam.aetherii.world.structure.processor.AetherIIStructureProcessorTypes;
import com.aetherteam.aetherii.world.surfacerule.AetherIISurfaceRules;
import com.aetherteam.aetherii.world.tree.decorator.AetherIITreeDecoratorTypes;
import com.aetherteam.aetherii.world.tree.foliage.AetherIIFoliagePlacerTypes;
import com.aetherteam.aetherii.world.tree.trunk.AetherIITrunkPlacerTypes;
import com.google.common.reflect.Reflection;
import com.mojang.logging.LogUtils;

import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.minecraftforge.registries.DeferredRegister;

@Mod(AetherII.MODID)
public class AetherII {
    public static final String MODID = "aether_ii";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final boolean DEBUG_MODE = false;

    public AetherII() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(AetherIIData::data);
        bus.addListener(this::commonSetup);
        AetherIINetwork.register();

        bus.addListener(AetherII::registerDataPackRegistries);

        DeferredRegister<?>[] registers = {
                AetherIIMurals.MURALS,
                AetherIIAdvancementSoundOverrides.ADVANCEMENT_SOUND_OVERRIDES,
                AetherIIFluidTypes.FLUID_TYPES,
                AetherIIFluids.FLUIDS,
                AetherIIBlocks.BLOCKS,
                AetherIIItems.ITEMS,
                AetherIIEntityTypes.ENTITY_TYPES,
                AetherIIBlockEntityTypes.BLOCK_ENTITY_TYPES,
                AetherIIAttributes.ATTRIBUTES,
                AetherIIMemoryModuleTypes.MEMORY_MODULE_TYPES,
                AetherIISensorTypes.SENSOR_TYPES,
                AetherIIMobEffects.EFFECTS,
                AetherIIDataSerializers.ENTITY_DATA_SERIALIZERS,
                AetherIICreativeTabs.CREATIVE_MODE_TABS,
                AetherIIMenuTypes.MENU_TYPES,
                AetherIIParticleTypes.PARTICLES,
                AetherIISoundEvents.SOUNDS,
                AetherIIRecipeTypes.RECIPE_TYPES,
                AetherIIRecipeSerializers.RECIPE_SERIALIZERS,
                AetherIIGameEvents.GAME_EVENTS,
                AetherIIPoi.POI,
                AetherIIFeatures.FEATURES,
                AetherIITreeDecoratorTypes.TREE_DECORATORS,
                AetherIIFoliagePlacerTypes.FOLIAGE_PLACERS,
                AetherIITrunkPlacerTypes.TRUNK_PLACERS,
                AetherIIStructureTypes.STRUCTURE_TYPES,
                AetherIIStructurePieceTypes.STRUCTURE_PIECE_TYPES,
                AetherIIStructureProcessorTypes.STRUCTURE_PROCESSOR_TYPES,
                AetherIIPoolElementTypes.POOL_ELEMENTS,
                AetherIIDensityFunctionTypes.DENSITY_FUNCTION_TYPES,
                AetherIILootFunctions.LOOT_FUNCTION_TYPES,
                AetherIILootConditions.LOOT_CONDITION_TYPES,
                AetherIILootModifiers.GLOBAL_LOOT_MODIFIERS,
                AetherIISurfaceRules.MATERIAL_RULES,
                AetherIIBlockPredicateTypes.BLOCK_PREDICATE_TYPES,
                AetherIIPlacementModifierTypes.PLACEMENT_MODIFIER_TYPES
        };

        for (DeferredRegister<?> register : registers) {
            register.register(bus);
        }

        this.eventSetup(bus);
        AetherIIAdvancementTriggers.init();
        AetherIIEntitySubPredicates.register();

        AetherIIBlocks.registerWoodTypes(); // Registered this early to avoid bugs with WoodTypes and signs.

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, AetherIIConfig.SERVER_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AetherIIConfig.COMMON_SPEC);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            AetherIIClient.clientInit(bus);
        }
    }

    private static void registerDataPackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(AetherIIRegistries.BESTIARY_ENTRY, BestiaryEntry.DIRECT_CODEC, BestiaryEntry.DIRECT_CODEC);
        event.dataPackRegistry(AetherIIRegistries.EFFECTS_ENTRY, EffectsEntry.DIRECT_CODEC, EffectsEntry.DIRECT_CODEC);
        event.dataPackRegistry(AetherIIRegistries.EXPLORATION_ENTRY, ExplorationEntry.DIRECT_CODEC, ExplorationEntry.DIRECT_CODEC);
        event.dataPackRegistry(AetherIIRegistries.STYLE_DESIGN, StyleDesign.DIRECT_CODEC, StyleDesign.DIRECT_CODEC);
        event.dataPackRegistry(AetherIIRegistries.STYLE_MATERIAL, StyleMaterial.DIRECT_CODEC, StyleMaterial.DIRECT_CODEC);
        event.dataPackRegistry(AetherIIRegistries.ITEM_REINFORCEMENT, ItemReinforcement.DIRECT_CODEC, ItemReinforcement.DIRECT_CODEC);
        event.dataPackRegistry(JukeboxSong.REGISTRY_KEY, JukeboxSong.DIRECT_CODEC, JukeboxSong.DIRECT_CODEC);
        event.dataPackRegistry(AetherIIRegistries.SKYROOT_LIZARD_VARIANT, SkyrootLizardVariant.DIRECT_CODEC, SkyrootLizardVariant.DIRECT_CODEC);
        event.dataPackRegistry(AetherIIRegistries.GLITTERWING_VARIANT, GlitterwingVariant.DIRECT_CODEC, GlitterwingVariant.DIRECT_CODEC);
        event.dataPackRegistry(AetherIIRegistries.SHROUDWING_VARIANT, ShroudwingVariant.DIRECT_CODEC, ShroudwingVariant.DIRECT_CODEC);
        event.dataPackRegistry(AetherIIRegistries.REWARD_WRAPPER, RewardWrapper.DIRECT_CODEC, RewardWrapper.DIRECT_CODEC);
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        Reflection.initialize(AetherIIRecipeBookTypes.class);
        Reflection.initialize(AetherIIMobCategory.class);

        event.enqueueWork(() -> {
            AetherIIBlocks.registerPots();
            AetherIIBlocks.registerFlammability();
            AetherIIBlocks.registerFluidInteractions();
            AetherIIBlockEntityTypes.registerValidBlockEntityTypes();

            this.registerDispenserBehaviors();
            this.registerCauldronInteractions();
        });
    }

    public void eventSetup(IEventBus neoBus) {
        IEventBus bus = MinecraftForge.EVENT_BUS;

        AetherIIEventListeners.listen(bus);
        AetherIIItems.registerEquipmentAbilities(bus);
        AetherIIMobEffects.registerUniqueBehaviors(bus);

        bus.addListener(AetherIICommands::registerCommands);
        bus.addListener(ReloadListeners::registerReloadListeners);
        neoBus.addListener(AetherIIAttributes::registerEntityAttributes);
        neoBus.addListener(AetherIIEntityTypes::registerSpawnPlacements);
        neoBus.addListener(AetherIIEntityTypes::registerEntityAttributes);
        neoBus.addListener(AetherIICreativeTabs::addCreativeModTabContents);
    }

    private void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(AetherIIItems.SKYROOT_WATER_BUCKET.get(), AetherIIDispenseBehaviors.SKYROOT_BUCKET_DISPENSE_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherIIItems.SKYROOT_BUCKET.get(), AetherIIDispenseBehaviors.SKYROOT_BUCKET_PICKUP_BEHAVIOR);
    }

    private void registerCauldronInteractions() {
        CauldronInteraction.EMPTY.put(AetherIIItems.SKYROOT_WATER_BUCKET.get(), AetherIICauldronInteractions.FILL_WATER);
        CauldronInteraction.WATER.put(AetherIIItems.SKYROOT_WATER_BUCKET.get(), AetherIICauldronInteractions.FILL_WATER);
        CauldronInteraction.LAVA.put(AetherIIItems.SKYROOT_WATER_BUCKET.get(), AetherIICauldronInteractions.FILL_WATER);
        CauldronInteraction.POWDER_SNOW.put(AetherIIItems.SKYROOT_WATER_BUCKET.get(), AetherIICauldronInteractions.FILL_WATER);
        CauldronInteraction.EMPTY.put(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherIICauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteraction.WATER.put(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherIICauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteraction.LAVA.put(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherIICauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteraction.POWDER_SNOW.put(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherIICauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteraction.WATER.put(AetherIIItems.SKYROOT_BUCKET.get(), AetherIICauldronInteractions.EMPTY_WATER);
        CauldronInteraction.POWDER_SNOW.put(AetherIIItems.SKYROOT_BUCKET.get(), AetherIICauldronInteractions.EMPTY_POWDER_SNOW);
    }

}
