package com.aetherteam.aetherii;

import com.aetherteam.aetherii.advancement.AetherIIAdvancementSoundOverrides;
import com.aetherteam.aetherii.advancement.predicate.AetherIIEntitySubPredicates;
import com.aetherteam.aetherii.advancement.trigger.AetherIIAdvancementTriggers;
import com.aetherteam.aetherii.api.ItemReinforcement;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.api.guidebook.EffectsEntry;
import com.aetherteam.aetherii.api.guidebook.ExplorationEntry;
import com.aetherteam.aetherii.api.guidebook.RewardWrapper;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.api.styles.StyleDesign;
import com.aetherteam.aetherii.api.styles.StyleMaterial;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.block.*;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.client.AetherIIClient;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.command.AetherIICommands;
import com.aetherteam.aetherii.data.AetherIIData;
import com.aetherteam.aetherii.data.ReloadListeners;
import com.aetherteam.aetherii.data.resources.AetherIIMobCategory;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDataMaps;
import com.aetherteam.aetherii.data.resources.registries.AetherIIMurals;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.brain.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.ai.brain.sensor.AetherIISensorTypes;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.entity.variant.GlitterwingVariant;
import com.aetherteam.aetherii.entity.variant.ShroudwingVariant;
import com.aetherteam.aetherii.entity.variant.SkyrootLizardVariant;
import com.aetherteam.aetherii.entity.variant.spawning.AetherIISpawnConditions;
import com.aetherteam.aetherii.inventory.AetherIIRecipeBookTypes;
import com.aetherteam.aetherii.inventory.menu.AetherIIMenuTypes;
import com.aetherteam.aetherii.item.AetherIICreativeTabs;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.consumeeffect.AetherIIConsumeEffectTypes;
import com.aetherteam.aetherii.loot.conditions.AetherIILootConditions;
import com.aetherteam.aetherii.loot.functions.AetherIILootFunctions;
import com.aetherteam.aetherii.loot.modifiers.AetherIILootModifiers;
import com.aetherteam.aetherii.network.packet.clientbound.*;
import com.aetherteam.aetherii.network.packet.serverbound.*;
import com.aetherteam.aetherii.recipe.AetherIIRecipeSerializers;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import com.aetherteam.aetherii.recipe.display.AetherIIRecipeDisplays;
import com.aetherteam.aetherii.recipe.display.slot.AetherIISlotDisplays;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.set.AetherIIRecipePropertySets;
import com.aetherteam.aetherii.world.AetherIIPoi;
import com.aetherteam.aetherii.world.density.AetherIIDensityFunctionTypes;
import com.aetherteam.aetherii.world.feature.AetherIIFeatures;
import com.aetherteam.aetherii.world.feature.modifier.filter.AetherIIPlacementModifierTypes;
import com.aetherteam.aetherii.world.feature.modifier.predicate.AetherIIBlockPredicateTypes;
import com.aetherteam.aetherii.world.structure.piece.AetherIIStructurePieceTypes;
import com.aetherteam.aetherii.world.structure.pool.AetherIIPoolElementTypes;
import com.aetherteam.aetherii.world.structure.processor.AetherIIStructureProcessorTypes;
import com.aetherteam.aetherii.world.structure.type.AetherIIStructureTypes;
import com.aetherteam.aetherii.world.surfacerule.AetherIISurfaceRules;
import com.aetherteam.aetherii.world.tree.decorator.AetherIITreeDecoratorTypes;
import com.aetherteam.aetherii.world.tree.foliage.AetherIIFoliagePlacerTypes;
import com.aetherteam.aetherii.world.tree.trunk.AetherIITrunkPlacerTypes;
import com.google.common.reflect.Reflection;
import com.mojang.logging.LogUtils;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.slf4j.Logger;

@Mod(AetherII.MODID)
public class AetherII {
    public static final String MODID = "aether_ii";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final boolean DEBUG_MODE = false;

    public AetherII(ModContainer mod, IEventBus bus, Dist dist) {
        bus.addListener(AetherIIData::data);
        bus.addListener(this::commonSetup);
        bus.addListener(this::registerPackets);

        bus.addListener(DataPackRegistryEvent.NewRegistry.class, AetherII::registerDataPackRegistries);

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
                AetherIIConsumeEffectTypes.CONSUME_EFFECT_TYPE,
                AetherIIDataSerializers.ENTITY_DATA_SERIALIZERS,
                AetherIIDataComponents.DATA_COMPONENT_TYPES,
                AetherIIDataAttachments.ATTACHMENTS,
                AetherIICreativeTabs.CREATIVE_MODE_TABS,
                AetherIIMenuTypes.MENU_TYPES,
                AetherIIParticleTypes.PARTICLES,
                AetherIISoundEvents.SOUNDS,
                AetherIIRecipeTypes.RECIPE_TYPES,
                AetherIIRecipeSerializers.RECIPE_SERIALIZERS,
                AetherIIRecipeDisplays.RECIPE_DISPLAYS,
                AetherIISlotDisplays.SLOT_DISPLAYS,
                AetherIIRecipeBookCategories.RECIPE_BOOK_CATEGORIES,
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
                AetherIIPlacementModifierTypes.PLACEMENT_MODIFIER_TYPES,
                AetherIIAdvancementTriggers.TRIGGERS,
                AetherIIEntitySubPredicates.ENTITY_SUB_PREDICATES,
                AetherIISpawnConditions.SPAWN_CONDITION_TYPES
        };

        for (DeferredRegister<?> register : registers) {
            register.register(bus);
        }

        this.eventSetup(bus);

        AetherIIBlocks.registerWoodTypes(); // Registered this early to avoid bugs with WoodTypes and signs.

        mod.registerConfig(ModConfig.Type.SERVER, AetherIIConfig.SERVER_SPEC);
        mod.registerConfig(ModConfig.Type.COMMON, AetherIIConfig.COMMON_SPEC);

        if (dist == Dist.CLIENT) {
            AetherIIClient.clientInit(bus);
            mod.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }

    private static void registerDataPackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(AetherIIRegistries.BESTIARY_ENTRY, BestiaryEntry.DIRECT_CODEC, BestiaryEntry.DIRECT_CODEC);
        event.dataPackRegistry(AetherIIRegistries.EFFECTS_ENTRY, EffectsEntry.DIRECT_CODEC, EffectsEntry.DIRECT_CODEC);
        event.dataPackRegistry(AetherIIRegistries.EXPLORATION_ENTRY, ExplorationEntry.DIRECT_CODEC, ExplorationEntry.DIRECT_CODEC);
        event.dataPackRegistry(AetherIIRegistries.STYLE_DESIGN, StyleDesign.DIRECT_CODEC, StyleDesign.DIRECT_CODEC);
        event.dataPackRegistry(AetherIIRegistries.STYLE_MATERIAL, StyleMaterial.DIRECT_CODEC, StyleMaterial.DIRECT_CODEC);
        event.dataPackRegistry(AetherIIRegistries.ITEM_REINFORCEMENT, ItemReinforcement.DIRECT_CODEC, ItemReinforcement.DIRECT_CODEC);
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

            AetherIIRecipePropertySets.addToMap();

            this.registerDispenserBehaviors();
            this.registerCauldronInteractions();
        });
    }

    public void eventSetup(IEventBus neoBus) {
        IEventBus bus = NeoForge.EVENT_BUS;

        AetherIIEventListeners.listen(bus);
        AetherIIItems.registerEquipmentAbilities(bus);
        AetherIIMobEffects.registerUniqueBehaviors(bus);

        bus.addListener(AetherIICommands::registerCommands);
        bus.addListener(ReloadListeners::registerReloadListeners);
        neoBus.addListener(AetherII::addAliases);
        neoBus.addListener(AetherIIBlockEntityTypes::registerValidBlockEntityTypes);
        neoBus.addListener(AetherIIAttributes::registerEntityAttributes);
        neoBus.addListener(AetherIIEntityTypes::registerSpawnPlacements);
        neoBus.addListener(AetherIIEntityTypes::registerEntityAttributes);
        neoBus.addListener(AetherIIDataMaps::registerDataMaps);
        neoBus.addListener(AetherIICreativeTabs::addCreativeModTabContents);
        neoBus.addListener(AetherIIItems::modifyDefaultComponents);
    }

    public void registerPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();

        // CLIENTBOUND
        registrar.playToClient(AerbunnyMessagePacket.TYPE, AerbunnyMessagePacket.STREAM_CODEC, AerbunnyMessagePacket::execute);
        registrar.playToClient(AlkahestDamageBlockPacket.TYPE, AlkahestDamageBlockPacket.STREAM_CODEC, AlkahestDamageBlockPacket::execute);
        registrar.playToClient(AlkahestFizzPacket.TYPE, AlkahestFizzPacket.STREAM_CODEC, AlkahestFizzPacket::execute);
        registrar.playToClient(AlkahestItemSmokePacket.TYPE, AlkahestItemSmokePacket.STREAM_CODEC, AlkahestItemSmokePacket::execute);
        registrar.playToClient(AltarParticlesPacket.TYPE, AltarParticlesPacket.STREAM_CODEC, AltarParticlesPacket::execute);
        registrar.playToClient(AttackShockParticlePacket.TYPE, AttackShockParticlePacket.STREAM_CODEC, AttackShockParticlePacket::execute);
        registrar.playToClient(AttackStabParticlePacket.TYPE, AttackStabParticlePacket.STREAM_CODEC, AttackStabParticlePacket::execute);
        registrar.playToClient(BossInfoPacket.Display.TYPE, BossInfoPacket.Display.STREAM_CODEC, BossInfoPacket.Display::execute);
        registrar.playToClient(BossInfoPacket.Remove.TYPE, BossInfoPacket.Remove.STREAM_CODEC, BossInfoPacket.Remove::execute);
        registrar.playToClient(BreakItemPacket.TYPE, BreakItemPacket.STREAM_CODEC, BreakItemPacket::execute);
        registrar.playToClient(ClientGrabItemPacket.TYPE, ClientGrabItemPacket.STREAM_CODEC, ClientGrabItemPacket::execute);
        registrar.playToClient(FlushGuidebookDataPacket.TYPE, FlushGuidebookDataPacket.STREAM_CODEC, FlushGuidebookDataPacket::execute);
        registrar.playToClient(ForgeSoundPacket.TYPE, ForgeSoundPacket.STREAM_CODEC, ForgeSoundPacket::execute);
        registrar.playToClient(FreezingParticlePacket.TYPE, FreezingParticlePacket.STREAM_CODEC, FreezingParticlePacket::execute);
        registrar.playToClient(HestveilExplosionEffectsPacket.TYPE, HestveilExplosionEffectsPacket.STREAM_CODEC, HestveilExplosionEffectsPacket::execute);
        registrar.playToClient(GuidebookToastPacket.TYPE, GuidebookToastPacket.STREAM_CODEC, GuidebookToastPacket::execute);
        registrar.playToClient(DamageTypeParticlePacket.TYPE, DamageTypeParticlePacket.STREAM_CODEC, DamageTypeParticlePacket::execute);
        registrar.playToClient(PortalTravelSoundPacket.TYPE, PortalTravelSoundPacket.STREAM_CODEC, PortalTravelSoundPacket::execute);
        registrar.playToClient(HourglassFinishParticlesPacket.TYPE, HourglassFinishParticlesPacket.STREAM_CODEC, HourglassFinishParticlesPacket::execute);
        registrar.playToClient(HourglassProcessParticlesPacket.TYPE, HourglassProcessParticlesPacket.STREAM_CODEC, HourglassProcessParticlesPacket::execute);
        registrar.playToClient(MusicBlockPlayPacket.TYPE, MusicBlockPlayPacket.STREAM_CODEC, MusicBlockPlayPacket::execute);
        registrar.playToClient(RemountAerbunnyPacket.TYPE, RemountAerbunnyPacket.STREAM_CODEC, RemountAerbunnyPacket::execute);
        registrar.playToClient(ResistanceKnockbackPacket.TYPE, ResistanceKnockbackPacket.STREAM_CODEC, ResistanceKnockbackPacket::execute);
        registrar.playToClient(SetAccessoriesPacket.TYPE, SetAccessoriesPacket.STREAM_CODEC, SetAccessoriesPacket::execute);
        registrar.playToClient(SetVehiclePacket.TYPE, SetVehiclePacket.STREAM_CODEC, SetVehiclePacket::execute);
        registrar.playToClient(GrassTintSyncPacket.TYPE, GrassTintSyncPacket.STREAM_CODEC, GrassTintSyncPacket::execute);

        // SERVERBOUND
        registrar.playToServer(AlkahestBreakBlockPacket.TYPE, AlkahestBreakBlockPacket.STREAM_CODEC, AlkahestBreakBlockPacket::execute);
        registrar.playToServer(AerbunnyPuffPacket.TYPE, AerbunnyPuffPacket.STREAM_CODEC, AerbunnyPuffPacket::execute);
        registrar.playToServer(CheckBestiaryEntryPacket.TYPE, CheckBestiaryEntryPacket.STREAM_CODEC, CheckBestiaryEntryPacket::execute);
        registrar.playToServer(CheckEffectsEntryPacket.TYPE, CheckEffectsEntryPacket.STREAM_CODEC, CheckEffectsEntryPacket::execute);
        registrar.playToServer(ClearAccessoriesPacket.TYPE, ClearAccessoriesPacket.STREAM_CODEC, ClearAccessoriesPacket::execute);
        registrar.playToServer(ClearItemPacket.TYPE, ClearItemPacket.STREAM_CODEC, ClearItemPacket::execute);
        registrar.playToServer(CurrencyAmountPacket.TYPE, CurrencyAmountPacket.STREAM_CODEC, CurrencyAmountPacket::execute);
        registrar.playToServer(DiscardCompanionDeathPacket.TYPE, DiscardCompanionDeathPacket.STREAM_CODEC, DiscardCompanionDeathPacket::execute);
        registrar.playToServer(DiscardCompanionPacket.TYPE, DiscardCompanionPacket.STREAM_CODEC, DiscardCompanionPacket::execute);
        registrar.playToServer(ForgeRenamePacket.TYPE, ForgeRenamePacket.STREAM_CODEC, ForgeRenamePacket::execute);
        registrar.playToServer(ForgeSlotCharmsPacket.TYPE, ForgeSlotCharmsPacket.STREAM_CODEC, ForgeSlotCharmsPacket::execute);
        registrar.playToServer(ForgeTriggerSoundPacket.TYPE, ForgeTriggerSoundPacket.STREAM_CODEC, ForgeTriggerSoundPacket::execute);
        registrar.playToServer(ForgeUpgradePacket.TYPE, ForgeUpgradePacket.STREAM_CODEC, ForgeUpgradePacket::execute);
        registrar.playToServer(HeldCurrencyPacket.TYPE, HeldCurrencyPacket.STREAM_CODEC, HeldCurrencyPacket::execute);
        registrar.playToServer(MoaFlyModeChangePacket.TYPE, MoaFlyModeChangePacket.STREAM_CODEC, MoaFlyModeChangePacket::execute);
        registrar.playToServer(MountJumpedPacket.TYPE, MountJumpedPacket.STREAM_CODEC, MountJumpedPacket::execute);
        registrar.playToServer(MovementDataPacket.TYPE, MovementDataPacket.STREAM_CODEC, MovementDataPacket::execute);
        registrar.playToServer(OpenGuidebookPacket.TYPE, OpenGuidebookPacket.STREAM_CODEC, OpenGuidebookPacket::execute);
        registrar.playToServer(OpenInventoryPacket.TYPE, OpenInventoryPacket.STREAM_CODEC, OpenInventoryPacket::execute);
        registrar.playToServer(OutpostRespawnPacket.TYPE, OutpostRespawnPacket.STREAM_CODEC, OutpostRespawnPacket::execute);
        registrar.playToServer(SkiffParticlesPacket.TYPE, SkiffParticlesPacket.STREAM_CODEC, SkiffParticlesPacket::execute);
        registrar.playToServer(SkiffSteeringPacket.TYPE, SkiffSteeringPacket.STREAM_CODEC, SkiffSteeringPacket::execute);
        registrar.playToServer(StoreCompanionItemEntityPacket.TYPE, StoreCompanionItemEntityPacket.STREAM_CODEC, StoreCompanionItemEntityPacket::execute);
    }

    private void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(AetherIIItems.SKYROOT_WATER_BUCKET.get(), AetherIIDispenseBehaviors.SKYROOT_BUCKET_DISPENSE_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherIIItems.SKYROOT_BUCKET.get(), AetherIIDispenseBehaviors.SKYROOT_BUCKET_PICKUP_BEHAVIOR);
    }

    private void registerCauldronInteractions() {
        CauldronInteractions.EMPTY.put(AetherIIItems.SKYROOT_WATER_BUCKET.get(), AetherIICauldronInteractions.FILL_WATER);
        CauldronInteractions.WATER.put(AetherIIItems.SKYROOT_WATER_BUCKET.get(), AetherIICauldronInteractions.FILL_WATER);
        CauldronInteractions.LAVA.put(AetherIIItems.SKYROOT_WATER_BUCKET.get(), AetherIICauldronInteractions.FILL_WATER);
        CauldronInteractions.POWDER_SNOW.put(AetherIIItems.SKYROOT_WATER_BUCKET.get(), AetherIICauldronInteractions.FILL_WATER);
        CauldronInteractions.EMPTY.put(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherIICauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteractions.WATER.put(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherIICauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteractions.LAVA.put(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherIICauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteractions.POWDER_SNOW.put(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherIICauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteractions.WATER.put(AetherIIItems.SKYROOT_BUCKET.get(), AetherIICauldronInteractions.EMPTY_WATER);
        CauldronInteractions.POWDER_SNOW.put(AetherIIItems.SKYROOT_BUCKET.get(), AetherIICauldronInteractions.EMPTY_POWDER_SNOW);
    }

    public static void addAliases(RegisterEvent event) {
        if (event.getRegistryKey() == Registries.ITEM) {
            event.getRegistry().addAlias(Identifier.fromNamespaceAndPath(AetherII.MODID, "skyroot_spear"), Identifier.fromNamespaceAndPath(AetherII.MODID, "skyroot_pike"));
            event.getRegistry().addAlias(Identifier.fromNamespaceAndPath(AetherII.MODID, "holystone_spear"), Identifier.fromNamespaceAndPath(AetherII.MODID, "holystone_pike"));
            event.getRegistry().addAlias(Identifier.fromNamespaceAndPath(AetherII.MODID, "zanite_spear"), Identifier.fromNamespaceAndPath(AetherII.MODID, "zanite_pike"));
            event.getRegistry().addAlias(Identifier.fromNamespaceAndPath(AetherII.MODID, "arkenium_spear"), Identifier.fromNamespaceAndPath(AetherII.MODID, "arkenium_pike"));
            event.getRegistry().addAlias(Identifier.fromNamespaceAndPath(AetherII.MODID, "gravitite_spear"), Identifier.fromNamespaceAndPath(AetherII.MODID, "gravitite_pike"));
            event.getRegistry().addAlias(Identifier.fromNamespaceAndPath(AetherII.MODID, "ice_pendant"), Identifier.fromNamespaceAndPath(AetherII.MODID, "icestone_pendant"));
        }
    }
}