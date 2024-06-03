package com.aetherteam.aetherii;

import com.aetherteam.aetherii.api.damage.DamageInfliction;
import com.aetherteam.aetherii.api.damage.DamageResistance;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.client.AetherIIClient;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.data.AetherIIData;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.event.listeners.EffectsSystemListeners;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageInflictions;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageResistances;
import com.aetherteam.aetherii.entity.AetherIIAttributes;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.event.listeners.*;
import com.aetherteam.aetherii.data.resources.AetherIIMobCategory;
import com.aetherteam.aetherii.event.listeners.AerbunnyMountListener;
import com.aetherteam.aetherii.event.listeners.PortalTeleportationListener;
import com.aetherteam.aetherii.event.listeners.WorldInteractionListener;
import com.aetherteam.aetherii.inventory.menu.AetherIIMenuTypes;
import com.aetherteam.aetherii.item.AetherIICreativeTabs;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.network.packet.AerbunnyMountSyncPacket;
import com.aetherteam.aetherii.network.packet.PortalTeleportationSyncPacket;
import com.aetherteam.aetherii.network.packet.clientbound.EffectBuildupPacket;
import com.aetherteam.aetherii.network.packet.clientbound.DamageTypeParticlePacket;
import com.aetherteam.aetherii.network.packet.clientbound.PortalTravelSoundPacket;
import com.aetherteam.aetherii.network.packet.clientbound.RemountAerbunnyPacket;
import com.aetherteam.aetherii.network.packet.serverbound.AerbunnyPuffPacket;
import com.aetherteam.aetherii.network.packet.serverbound.StepHeightPacket;
import com.aetherteam.aetherii.world.AetherIIPoi;
import com.aetherteam.aetherii.world.density.AetherIIDensityFunctionTypes;
import com.aetherteam.aetherii.world.feature.AetherIIFeatures;
import com.aetherteam.aetherii.world.structure.AetherIIStructureTypes;
import com.aetherteam.aetherii.world.tree.decorator.AetherIITreeDecoratorTypes;
import com.aetherteam.aetherii.world.tree.foliage.AetherIIFoliagePlacerTypes;
import com.aetherteam.aetherii.world.tree.trunk.AetherIITrunkPlacerTypes;
import com.google.common.reflect.Reflection;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(AetherII.MODID)
public class AetherII {
    public static final String MODID = "aether_ii";
    public static final Logger LOGGER = LogUtils.getLogger();

    public AetherII(IEventBus bus, Dist dist) {
        bus.addListener(AetherII::commonSetup);
        bus.addListener(AetherIIData::dataSetup);
        bus.addListener(this::registerPackets);

        bus.addListener(DataPackRegistryEvent.NewRegistry.class, event -> event.dataPackRegistry(AetherIIDamageInflictions.DAMAGE_INFLICTION_REGISTRY_KEY, DamageInfliction.CODEC, DamageInfliction.CODEC));
        bus.addListener(DataPackRegistryEvent.NewRegistry.class, event -> event.dataPackRegistry(AetherIIDamageResistances.DAMAGE_RESISTANCE_REGISTRY_KEY, DamageResistance.CODEC, DamageResistance.CODEC));

        DeferredRegister<?>[] registers = {
                AetherIIBlocks.BLOCKS,
                AetherIIItems.ITEMS,
                AetherIIEntityTypes.ENTITY_TYPES,
                AetherIIBlockEntityTypes.BLOCK_ENTITY_TYPES,
                AetherIIEffects.EFFECTS,
                AetherIICreativeTabs.CREATIVE_MODE_TABS,
                AetherIIFeatures.FEATURES,
                AetherIITreeDecoratorTypes.TREE_DECORATORS,
                AetherIITrunkPlacerTypes.TRUNK_PLACERS,
                AetherIIFoliagePlacerTypes.FOLIAGE_PLACERS,
                AetherIIStructureTypes.STRUCTURE_TYPES,
                AetherIIParticleTypes.PARTICLES,
                AetherIISoundEvents.SOUNDS,
                AetherIIAttributes.ATTRIBUTES,
                AetherIIMenuTypes.MENU_TYPES,
                AetherIIMemoryModuleTypes.MEMORY_MODULE_TYPES,
                AetherIIPoi.POI,
                AetherIIDataAttachments.ATTACHMENTS,
                AetherIIDensityFunctionTypes.DENSITY_FUNCTION_TYPES
        };

        for (DeferredRegister<?> register : registers) {
            register.register(bus);
        }

        AetherII.eventSetup(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, AetherIIConfig.SERVER_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AetherIIConfig.COMMON_SPEC);

        if (dist == Dist.CLIENT) {
            AetherIIClient.clientInit(bus);
        }
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Reflection.initialize(AetherIIMobCategory.class);
            AetherIIBlocks.registerPots();
        });
    }

    public static void eventSetup(IEventBus neoBus) {
        IEventBus bus = NeoForge.EVENT_BUS;

        EffectsSystemListeners.listen(bus);
        DamageSystemListener.listen(bus);
        PortalTeleportationListener.listen(bus);
        ToolAbilityListener.listen(bus);
        ToolModificationListener.listen(bus);
        ToolAbilityListener.listen(bus);
        PortalTeleportationListener.listen(bus);
        AerbunnyMountListener.listen(bus);
        WorldInteractionListener.listen(bus);

        neoBus.addListener(AetherIIAttributes::registerEntityAttributes);
        neoBus.addListener(AetherIIEntityTypes::registerSpawnPlacements);
        neoBus.addListener(AetherIIEntityTypes::registerEntityAttributes);
    }

    public void registerPackets(RegisterPayloadHandlerEvent event) {
        IPayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();

        // CLIENTBOUND
        registrar.play(EffectBuildupPacket.Set.ID, EffectBuildupPacket.Set::decode, payload -> payload.client(EffectBuildupPacket.Set::handle));
        registrar.play(EffectBuildupPacket.Remove.ID, EffectBuildupPacket.Remove::decode, payload -> payload.client(EffectBuildupPacket.Remove::handle));
        registrar.play(DamageTypeParticlePacket.ID, DamageTypeParticlePacket::decode, payload -> payload.client(DamageTypeParticlePacket::handle));
        registrar.play(PortalTravelSoundPacket.ID, PortalTravelSoundPacket::decode, payload -> payload.client(PortalTravelSoundPacket::handle));
        registrar.play(RemountAerbunnyPacket.ID, RemountAerbunnyPacket::decode, payload -> payload.client(RemountAerbunnyPacket::handle));

        //SERVERBOUND
        registrar.play(AerbunnyPuffPacket.ID, AerbunnyPuffPacket::decode, payload -> payload.server(AerbunnyPuffPacket::handle));
        registrar.play(StepHeightPacket.ID, StepHeightPacket::decode, payload -> payload.server(StepHeightPacket::handle));

        // BOTH
        registrar.play(AerbunnyMountSyncPacket.ID, AerbunnyMountSyncPacket::decode, AerbunnyMountSyncPacket::handle);
        registrar.play(PortalTeleportationSyncPacket.ID, PortalTeleportationSyncPacket::decode, PortalTeleportationSyncPacket::handle);
    }
}