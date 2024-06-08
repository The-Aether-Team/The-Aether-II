package com.aetherteam.aetherii;

import com.aetherteam.aetherii.api.damage.DamageInfliction;
import com.aetherteam.aetherii.api.damage.DamageResistance;
import com.aetherteam.aetherii.api.moaegg.MoaType;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.client.AetherIIClient;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.data.AetherIIData;
import com.aetherteam.aetherii.data.ReloadListeners;
import com.aetherteam.aetherii.data.resources.AetherIIMobCategory;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageInflictions;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageResistances;
import com.aetherteam.aetherii.data.resources.registries.AetherIIMoaTypes;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.event.listeners.*;
import com.aetherteam.aetherii.inventory.AetherIIRecipeBookTypes;
import com.aetherteam.aetherii.inventory.menu.AetherIIMenuTypes;
import com.aetherteam.aetherii.item.AetherIICreativeTabs;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.network.packet.AerbunnyMountSyncPacket;
import com.aetherteam.aetherii.network.packet.PortalTeleportationSyncPacket;
import com.aetherteam.aetherii.network.packet.clientbound.DamageTypeParticlePacket;
import com.aetherteam.aetherii.network.packet.clientbound.EffectBuildupPacket;
import com.aetherteam.aetherii.network.packet.clientbound.PortalTravelSoundPacket;
import com.aetherteam.aetherii.network.packet.clientbound.RemountAerbunnyPacket;
import com.aetherteam.aetherii.network.packet.serverbound.AerbunnyPuffPacket;
import com.aetherteam.aetherii.network.packet.serverbound.StepHeightPacket;
import com.aetherteam.aetherii.recipe.serializer.AetherIIRecipeSerializers;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.world.AetherIIPoi;
import com.aetherteam.aetherii.world.density.AetherIIDensityFunctionTypes;
import com.aetherteam.aetherii.world.feature.AetherIIFeatures;
import com.aetherteam.aetherii.world.structure.AetherIIStructureTypes;
import com.aetherteam.aetherii.world.tree.decorator.AetherIITreeDecoratorTypes;
import com.aetherteam.aetherii.world.tree.foliage.AetherIIFoliagePlacerTypes;
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
        bus.addListener(AetherIIData::dataSetup);
        bus.addListener(this::commonSetup);
        bus.addListener(this::registerPackets);

        bus.addListener(DataPackRegistryEvent.NewRegistry.class, event -> event.dataPackRegistry(AetherIIDamageInflictions.DAMAGE_INFLICTION_REGISTRY_KEY, DamageInfliction.CODEC, DamageInfliction.CODEC));
        bus.addListener(DataPackRegistryEvent.NewRegistry.class, event -> event.dataPackRegistry(AetherIIDamageResistances.DAMAGE_RESISTANCE_REGISTRY_KEY, DamageResistance.CODEC, DamageResistance.CODEC));
        bus.addListener(DataPackRegistryEvent.NewRegistry.class, event -> event.dataPackRegistry(AetherIIMoaTypes.MOA_TYPE_REGISTRY_KEY, MoaType.CODEC, MoaType.CODEC));

        DeferredRegister<?>[] registers = {
                AetherIIBlocks.BLOCKS,
                AetherIIItems.ITEMS,
                AetherIIEntityTypes.ENTITY_TYPES,
                AetherIIBlockEntityTypes.BLOCK_ENTITY_TYPES,
                AetherIIAttributes.ATTRIBUTES,
                AetherIIMemoryModuleTypes.MEMORY_MODULE_TYPES,
                AetherIIEffects.EFFECTS,
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
                AetherIIDensityFunctionTypes.DENSITY_FUNCTION_TYPES
        };

        for (DeferredRegister<?> register : registers) {
            register.register(bus);
        }

        this.eventSetup(bus);

        AetherIIBlocks.registerWoodTypes(); // Registered this early to avoid bugs with WoodTypes and signs.

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, AetherIIConfig.SERVER_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AetherIIConfig.COMMON_SPEC);

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
        });
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

        bus.addListener(ReloadListeners::reloadListenerSetup);

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