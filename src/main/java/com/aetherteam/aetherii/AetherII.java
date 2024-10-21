package com.aetherteam.aetherii;

import com.aetherteam.aetherii.api.entity.MoaFeatherShape;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.api.guidebook.EffectsEntry;
import com.aetherteam.aetherii.api.guidebook.ExplorationEntry;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.AetherIICauldronInteractions;
import com.aetherteam.aetherii.block.AetherIIDispenseBehaviors;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.client.AetherIIClient;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.data.AetherIIData;
import com.aetherteam.aetherii.data.ReloadListeners;
import com.aetherteam.aetherii.data.resources.AetherIIMobCategory;
import com.aetherteam.aetherii.data.resources.registries.*;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.inventory.AetherIIAccessorySlots;
import com.aetherteam.aetherii.inventory.AetherIIRecipeBookTypes;
import com.aetherteam.aetherii.inventory.menu.AetherIIMenuTypes;
import com.aetherteam.aetherii.item.AetherIICreativeTabs;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.equipment.armor.AetherIIArmorMaterials;
import com.aetherteam.aetherii.loot.functions.AetherIILootFunctions;
import com.aetherteam.aetherii.loot.modifiers.AetherIILootModifiers;
import com.aetherteam.aetherii.network.packet.AetherIIPlayerSyncPacket;
import com.aetherteam.aetherii.network.packet.CurrencySyncPacket;
import com.aetherteam.aetherii.network.packet.DamageSystemSyncPacket;
import com.aetherteam.aetherii.network.packet.OutpostTrackerSyncPacket;
import com.aetherteam.aetherii.network.packet.clientbound.*;
import com.aetherteam.aetherii.network.packet.serverbound.*;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.serializer.AetherIIRecipeSerializers;
import com.aetherteam.aetherii.world.AetherIIPoi;
import com.aetherteam.aetherii.world.density.AetherIIDensityFunctionTypes;
import com.aetherteam.aetherii.world.feature.AetherIIFeatures;
import com.aetherteam.aetherii.world.feature.modifier.filter.AetherIIPlacementModifierTypes;
import com.aetherteam.aetherii.world.feature.modifier.predicate.AetherIIBlockPredicateTypes;
import com.aetherteam.aetherii.world.structure.AetherIIStructureTypes;
import com.aetherteam.aetherii.world.structure.pool.AetherIIPoolElementTypes;
import com.aetherteam.aetherii.world.surfacerule.AetherIISurfaceRules;
import com.aetherteam.aetherii.world.tree.decorator.AetherIITreeDecoratorTypes;
import com.aetherteam.aetherii.world.tree.foliage.AetherIIFoliagePlacerTypes;
import com.google.common.reflect.Reflection;
import com.mojang.logging.LogUtils;
import io.wispforest.accessories.api.slot.UniqueSlotHandling;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(AetherII.MODID)
public class AetherII {
    public static final String MODID = "aether_ii";
    public static final Logger LOGGER = LogUtils.getLogger();

    public AetherII(ModContainer mod, IEventBus bus, Dist dist) {
        bus.addListener(AetherIIData::dataSetup);
        bus.addListener(this::commonSetup);
        bus.addListener(this::registerPackets);

        bus.addListener(DataPackRegistryEvent.NewRegistry.class, event -> event.dataPackRegistry(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY, BestiaryEntry.DIRECT_CODEC, BestiaryEntry.DIRECT_CODEC));
        bus.addListener(DataPackRegistryEvent.NewRegistry.class, event -> event.dataPackRegistry(AetherIIEffectsEntries.EFFECTS_ENTRY_REGISTRY_KEY, EffectsEntry.DIRECT_CODEC, EffectsEntry.DIRECT_CODEC));
        bus.addListener(DataPackRegistryEvent.NewRegistry.class, event -> event.dataPackRegistry(AetherIIExplorationEntries.EXPLORATION_ENTRY_REGISTRY_KEY, ExplorationEntry.DIRECT_CODEC, ExplorationEntry.DIRECT_CODEC));
        bus.addListener(DataPackRegistryEvent.NewRegistry.class, event -> event.dataPackRegistry(AetherIIMoaFeatherShapes.MOA_FEATHER_SHAPE_REGISTRY_KEY, MoaFeatherShape.DIRECT_CODEC, MoaFeatherShape.DIRECT_CODEC));

        DeferredRegister<?>[] registers = {
                AetherIIBlocks.BLOCKS,
                AetherIIItems.ITEMS,
                AetherIIEntityTypes.ENTITY_TYPES,
                AetherIIBlockEntityTypes.BLOCK_ENTITY_TYPES,
                AetherIIAttributes.ATTRIBUTES,
                AetherIIMemoryModuleTypes.MEMORY_MODULE_TYPES,
                AetherIIEffects.EFFECTS,
                AetherIIArmorMaterials.ARMOR_MATERIALS,
                AetherIIDataSerializers.ENTITY_DATA_SERIALIZERS,
                AetherIIDataComponents.DATA_COMPONENT_TYPES,
                AetherIIDataAttachments.ATTACHMENTS,
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
                AetherIIStructureTypes.STRUCTURE_TYPES,
                AetherIIPoolElementTypes.POOL_ELEMENTS,
                AetherIIDensityFunctionTypes.DENSITY_FUNCTION_TYPES,
                AetherIILootFunctions.LOOT_FUNCTION_TYPES,
                AetherIILootModifiers.GLOBAL_LOOT_MODIFIERS,
                AetherIISurfaceRules.MATERIAL_RULES,
                AetherIIBlockPredicateTypes.BLOCK_PREDICATE_TYPES,
                AetherIIPlacementModifierTypes.PLACEMENT_MODIFIER_TYPES
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
        }
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        Reflection.initialize(AetherIIRecipeBookTypes.class);
        Reflection.initialize(AetherIIMobCategory.class);

        event.enqueueWork(() -> {
            AetherIIBlocks.registerPots();
            AetherIIBlocks.registerFlammability();

            AetherIIItems.registerAccessories();

            this.registerDispenserBehaviors();
            this.registerCauldronInteractions();
        });

        UniqueSlotHandling.EVENT.register(AetherIIAccessorySlots.INSTANCE);
    }

    public void eventSetup(IEventBus neoBus) {
        IEventBus bus = NeoForge.EVENT_BUS;

        AetherIIEventListeners.listen(bus);
        AetherIIItems.registerEquipmentAbilities(bus);
        AetherIIEffects.registerUniqueBehaviors(bus);

        bus.addListener(ReloadListeners::registerReloadListeners);
        neoBus.addListener(AetherIIBlockEntityTypes::registerValidBlockEntityTypes);
        neoBus.addListener(AetherIIAttributes::registerEntityAttributes);
        neoBus.addListener(AetherIIEntityTypes::registerSpawnPlacements);
        neoBus.addListener(AetherIIEntityTypes::registerEntityAttributes);
        neoBus.addListener(AetherIIDataMaps::registerDataMaps);
    }

    public void registerPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();

        // CLIENTBOUND
        registrar.playToClient(ClientGrabItemPacket.TYPE, ClientGrabItemPacket.STREAM_CODEC, ClientGrabItemPacket::execute);
        registrar.playToClient(EffectBuildupSetPacket.TYPE, EffectBuildupSetPacket.STREAM_CODEC, EffectBuildupSetPacket::execute);
        registrar.playToClient(EffectBuildupRemovePacket.TYPE, EffectBuildupRemovePacket.STREAM_CODEC, EffectBuildupRemovePacket::execute);
        registrar.playToClient(ForgeSoundPacket.TYPE, ForgeSoundPacket.STREAM_CODEC, ForgeSoundPacket::execute);
        registrar.playToClient(GuidebookToastPacket.TYPE, GuidebookToastPacket.STREAM_CODEC, GuidebookToastPacket::execute);
        registrar.playToClient(DamageTypeParticlePacket.TYPE, DamageTypeParticlePacket.STREAM_CODEC, DamageTypeParticlePacket::execute);
        registrar.playToClient(PortalTravelSoundPacket.TYPE, PortalTravelSoundPacket.STREAM_CODEC, PortalTravelSoundPacket::execute);
        registrar.playToClient(RemountAerbunnyPacket.TYPE, RemountAerbunnyPacket.STREAM_CODEC, RemountAerbunnyPacket::execute);
        registrar.playToClient(UpdateGuidebookDiscoveryPacket.TYPE, UpdateGuidebookDiscoveryPacket.STREAM_CODEC, UpdateGuidebookDiscoveryPacket::execute);
        registrar.playToClient(SetVehiclePacket.TYPE, SetVehiclePacket.STREAM_CODEC, SetVehiclePacket::execute);
        registrar.playToClient(SwetSyncPacket.TYPE, SwetSyncPacket.STREAM_CODEC, SwetSyncPacket::execute);

        // SERVERBOUND
        registrar.playToServer(AerbunnyPuffPacket.TYPE, AerbunnyPuffPacket.STREAM_CODEC, AerbunnyPuffPacket::execute);
        registrar.playToServer(ForgeRenamePacket.TYPE, ForgeRenamePacket.STREAM_CODEC, ForgeRenamePacket::execute);
        registrar.playToServer(ForgeSlotCharmsPacket.TYPE, ForgeSlotCharmsPacket.STREAM_CODEC, ForgeSlotCharmsPacket::execute);
        registrar.playToServer(ForgeTriggerSoundPacket.TYPE, ForgeTriggerSoundPacket.STREAM_CODEC, ForgeTriggerSoundPacket::execute);
        registrar.playToServer(ForgeUpgradePacket.TYPE, ForgeUpgradePacket.STREAM_CODEC, ForgeUpgradePacket::execute);
        registrar.playToServer(CheckGuidebookEntryPacket.TYPE, CheckGuidebookEntryPacket.STREAM_CODEC, CheckGuidebookEntryPacket::execute);
        registrar.playToServer(ClearItemPacket.TYPE, ClearItemPacket.STREAM_CODEC, ClearItemPacket::execute);
        registrar.playToServer(HeldCurrencyPacket.TYPE, HeldCurrencyPacket.STREAM_CODEC, HeldCurrencyPacket::execute);
        registrar.playToServer(OpenGuidebookPacket.TYPE, OpenGuidebookPacket.STREAM_CODEC, OpenGuidebookPacket::execute);
        registrar.playToServer(OpenInventoryPacket.TYPE, OpenInventoryPacket.STREAM_CODEC, OpenInventoryPacket::execute);
        registrar.playToServer(OutpostRespawnPacket.TYPE, OutpostRespawnPacket.STREAM_CODEC, OutpostRespawnPacket::execute);
        registrar.playToServer(StepHeightPacket.TYPE, StepHeightPacket.STREAM_CODEC, StepHeightPacket::execute);

        // BOTH
        registrar.playBidirectional(AetherIIPlayerSyncPacket.TYPE, AetherIIPlayerSyncPacket.STREAM_CODEC, AetherIIPlayerSyncPacket::execute);
        registrar.playBidirectional(CurrencySyncPacket.TYPE, CurrencySyncPacket.STREAM_CODEC, CurrencySyncPacket::execute);
        registrar.playBidirectional(DamageSystemSyncPacket.TYPE, DamageSystemSyncPacket.STREAM_CODEC, DamageSystemSyncPacket::execute);
        registrar.playBidirectional(OutpostTrackerSyncPacket.TYPE, OutpostTrackerSyncPacket.STREAM_CODEC, OutpostTrackerSyncPacket::execute);
    }

    private void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(AetherIIItems.SKYROOT_WATER_BUCKET.get(), AetherIIDispenseBehaviors.SKYROOT_BUCKET_DISPENSE_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherIIItems.SKYROOT_BUCKET.get(), AetherIIDispenseBehaviors.SKYROOT_BUCKET_PICKUP_BEHAVIOR);
    }

    private void registerCauldronInteractions() {
        CauldronInteraction.EMPTY.map().put(AetherIIItems.SKYROOT_WATER_BUCKET.get(), AetherIICauldronInteractions.FILL_WATER);
        CauldronInteraction.WATER.map().put(AetherIIItems.SKYROOT_WATER_BUCKET.get(), AetherIICauldronInteractions.FILL_WATER);
        CauldronInteraction.LAVA.map().put(AetherIIItems.SKYROOT_WATER_BUCKET.get(), AetherIICauldronInteractions.FILL_WATER);
        CauldronInteraction.POWDER_SNOW.map().put(AetherIIItems.SKYROOT_WATER_BUCKET.get(), AetherIICauldronInteractions.FILL_WATER);
        CauldronInteraction.EMPTY.map().put(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherIICauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteraction.WATER.map().put(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherIICauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteraction.LAVA.map().put(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherIICauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteraction.POWDER_SNOW.map().put(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherIICauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteraction.WATER.map().put(AetherIIItems.SKYROOT_BUCKET.get(), AetherIICauldronInteractions.EMPTY_WATER);
        CauldronInteraction.POWDER_SNOW.map().put(AetherIIItems.SKYROOT_BUCKET.get(), AetherIICauldronInteractions.EMPTY_POWDER_SNOW);
    }
}