package com.aetherteam.aetherii;

import com.aetherteam.aetherii.accessories.AetherIISlotHandling;
import com.aetherteam.aetherii.accessories.accessory.HandwearAccessory;
import com.aetherteam.aetherii.api.damage.DamageInfliction;
import com.aetherteam.aetherii.api.damage.DamageResistance;
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
import com.aetherteam.aetherii.data.resources.registries.*;
import com.aetherteam.aetherii.data.resources.AetherIIMobCategory;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.event.listeners.*;
import com.aetherteam.aetherii.event.listeners.attachment.*;
import com.aetherteam.aetherii.inventory.AetherIIRecipeBookTypes;
import com.aetherteam.aetherii.inventory.menu.AetherIIMenuTypes;
import com.aetherteam.aetherii.item.AetherIIArmorMaterials;
import com.aetherteam.aetherii.item.AetherIICreativeTabs;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.loot.modifiers.AetherIILootModifiers;
import com.aetherteam.aetherii.network.packet.AerbunnyMountSyncPacket;
import com.aetherteam.aetherii.network.packet.DamageSystemSyncPacket;
import com.aetherteam.aetherii.network.packet.clientbound.*;
import com.aetherteam.aetherii.network.packet.serverbound.*;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.serializer.AetherIIRecipeSerializers;
import com.aetherteam.aetherii.world.AetherIIPoi;
import com.aetherteam.aetherii.world.density.AetherIIDensityFunctionTypes;
import com.aetherteam.aetherii.world.feature.AetherIIFeatures;
import com.aetherteam.aetherii.world.structure.AetherIIStructureTypes;
import com.aetherteam.aetherii.world.structure.pool.AetherIIPoolElementTypes;
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
        bus.addListener(DataPackRegistryEvent.NewRegistry.class, event -> event.dataPackRegistry(AetherIIDamageInflictions.DAMAGE_INFLICTION_REGISTRY_KEY, DamageInfliction.CODEC, DamageInfliction.CODEC));
        bus.addListener(DataPackRegistryEvent.NewRegistry.class, event -> event.dataPackRegistry(AetherIIDamageResistances.DAMAGE_RESISTANCE_REGISTRY_KEY, DamageResistance.CODEC, DamageResistance.CODEC));
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
                AetherIILootModifiers.GLOBAL_LOOT_MODIFIERS
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

            this.registerDispenserBehaviors();
            this.registerCauldronInteractions();
        });

        HandwearAccessory.init();
        UniqueSlotHandling.EVENT.register(AetherIISlotHandling.INSTANCE);
    }

    public void eventSetup(IEventBus neoBus) {
        IEventBus bus = NeoForge.EVENT_BUS;

        EffectsSystemListeners.listen(bus);
        DamageSystemListener.listen(bus);
        ToolModificationListener.listen(bus);
        ToolAbilityListener.listen(bus);
        PortalTeleportationListener.listen(bus);
        AerbunnyMountListener.listen(bus);
        WorldInteractionListener.listen(bus);
        RecipeListener.listen(bus);
        BlockInteractionListener.listen(bus);
        EntityInteractionListener.listen(bus);
        GuidebookDiscoveryListener.listen(bus);

        bus.addListener(ReloadListeners::reloadListenerSetup);

        neoBus.addListener(AetherIIAttributes::registerEntityAttributes);
        neoBus.addListener(AetherIIEntityTypes::registerSpawnPlacements);
        neoBus.addListener(AetherIIEntityTypes::registerEntityAttributes);
        neoBus.addListener(AetherIIDataMaps::registerDataMaps);
    }

    public void registerPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();

        // CLIENTBOUND
        registrar.playToClient(ClientGrabItemPacket.TYPE, ClientGrabItemPacket.STREAM_CODEC, ClientGrabItemPacket::execute);
        registrar.playToClient(EffectBuildupPacket.Set.TYPE, EffectBuildupPacket.Set.STREAM_CODEC, EffectBuildupPacket.Set::execute);
        registrar.playToClient(EffectBuildupPacket.Remove.TYPE, EffectBuildupPacket.Remove.STREAM_CODEC, EffectBuildupPacket.Remove::execute);
        registrar.playToClient(DamageTypeParticlePacket.TYPE, DamageTypeParticlePacket.STREAM_CODEC, DamageTypeParticlePacket::execute);
        registrar.playToClient(PortalTravelSoundPacket.TYPE, PortalTravelSoundPacket.STREAM_CODEC, PortalTravelSoundPacket::execute);
        registrar.playToClient(RemountAerbunnyPacket.TYPE, RemountAerbunnyPacket.STREAM_CODEC, RemountAerbunnyPacket::execute);
        registrar.playToClient(UpdateGuidebookDiscoveryPacket.TYPE, UpdateGuidebookDiscoveryPacket.STREAM_CODEC, UpdateGuidebookDiscoveryPacket::execute);

        // SERVERBOUND
        registrar.playToServer(AerbunnyPuffPacket.TYPE, AerbunnyPuffPacket.STREAM_CODEC, AerbunnyPuffPacket::execute);
        registrar.playToServer(ClearItemPacket.TYPE, ClearItemPacket.STREAM_CODEC, ClearItemPacket::execute);
        registrar.playToServer(OpenGuidebookPacket.TYPE, OpenGuidebookPacket.STREAM_CODEC, OpenGuidebookPacket::execute);
        registrar.playToServer(OpenInventoryPacket.TYPE, OpenInventoryPacket.STREAM_CODEC, OpenInventoryPacket::execute);
        registrar.playToServer(StepHeightPacket.TYPE, StepHeightPacket.STREAM_CODEC, StepHeightPacket::execute);

        // BOTH
        registrar.playBidirectional(AerbunnyMountSyncPacket.TYPE, AerbunnyMountSyncPacket.STREAM_CODEC, AerbunnyMountSyncPacket::execute);
        registrar.playBidirectional(DamageSystemSyncPacket.TYPE, DamageSystemSyncPacket.STREAM_CODEC, DamageSystemSyncPacket::execute);
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