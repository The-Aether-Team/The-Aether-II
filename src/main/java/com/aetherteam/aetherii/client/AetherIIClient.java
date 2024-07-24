package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.event.listeners.ClientTooltipsListener;
import com.aetherteam.aetherii.client.event.listeners.PlayerRespawnClientListeners;
import com.aetherteam.aetherii.accessories.accessory.HandwearAccessory;
import com.aetherteam.aetherii.client.event.listeners.GuiExtensionListener;
import com.aetherteam.aetherii.client.event.listeners.MusicListener;
import com.aetherteam.aetherii.client.event.listeners.attachment.AerbunnyMountClientListners;
import com.aetherteam.aetherii.client.event.listeners.attachment.DimensionRenderEffectListeners;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.renderer.AetherIIOverlays;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderers;
import com.aetherteam.aetherii.client.renderer.level.AetherIIRenderEffects;
import com.aetherteam.aetherii.inventory.menu.AetherIIMenuTypes;
import com.aetherteam.aetherii.item.AetherIIDataComponents;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.ReinforcementTier;
import com.aetherteam.aetherii.item.combat.AetherIICrossbowItem;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

public class AetherIIClient {
    public static void clientInit(IEventBus bus) {
        bus.addListener(AetherIIClient::clientSetup);

        AetherIIClient.eventSetup(bus);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            AetherIIAtlases.registerSkyrootChestAtlases();
            registerItemModelProperties();
        });

        HandwearAccessory.clientInit();
    }

    public static void eventSetup(IEventBus neoBus) {
        IEventBus bus = NeoForge.EVENT_BUS;

        AerbunnyMountClientListners.listen(bus);
        DimensionRenderEffectListeners.listen(bus);
        PlayerRespawnClientListeners.listen(bus);
        GuiExtensionListener.listen(bus);
        MusicListener.listen(bus);
        ClientTooltipsListener.listen(bus);

        neoBus.addListener(AetherIIMenuTypes::registerMenuScreens);
        neoBus.addListener(AetherIIColorResolvers::registerBlockColor);
        neoBus.addListener(AetherIIColorResolvers::registerItemColor);
        neoBus.addListener(AetherIIRecipeCategories::registerRecipeCategories);
        neoBus.addListener(AetherIIParticleTypes::registerParticleFactories);
        neoBus.addListener(AetherIIOverlays::registerOverlays);
        neoBus.addListener(AetherIIRenderers::registerEntityRenderers);
        neoBus.addListener(AetherIIRenderers::registerLayerDefinition);
        neoBus.addListener(AetherIIRenderers::bakeModels);
        neoBus.addListener(AetherIIRenderEffects::registerRenderEffects);
        neoBus.addListener(AetherIIShaders::registerShaders);
        neoBus.addListener(AetherIIItemDecorators::registerItemDecorators);
        neoBus.addListener(AetherIIClientTooltips::registerClientTooltipComponents);
    }

    public static void registerItemModelProperties() {
        registerCrossbowProperties(AetherIIItems.SKYROOT_CROSSBOW.get());
        registerCrossbowProperties(AetherIIItems.HOLYSTONE_CROSSBOW.get());
        registerCrossbowProperties(AetherIIItems.ZANITE_CROSSBOW.get());
        registerCrossbowProperties(AetherIIItems.ARKENIUM_CROSSBOW.get());
        registerCrossbowProperties(AetherIIItems.GRAVITITE_CROSSBOW.get());

        registerShieldProperties(AetherIIItems.SKYROOT_SHIELD.get());
        registerShieldProperties(AetherIIItems.HOLYSTONE_SHIELD.get());
        registerShieldProperties(AetherIIItems.ZANITE_SHIELD.get());
        registerShieldProperties(AetherIIItems.ARKENIUM_SHIELD.get());
        registerShieldProperties(AetherIIItems.GRAVITITE_SHIELD.get());
    }

    private static void registerCrossbowProperties(Item item) { //todo: crossbow needs porting
//        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pull"), (stack, level, livingEntity, value) ->
//                livingEntity == null ? 0.0F : AetherIICrossbowItem.isCharged(stack) ? 0.0F : (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / (float) AetherIICrossbowItem.getCrossbowChargeDuration(stack));
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pull"), (stack, level, livingEntity, value) ->0.0F);
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pulling"), (stack, level, livingEntity, value) ->
                livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("charged"), (stack, level, livingEntity, value) ->
                AetherIICrossbowItem.isCharged(stack) ? 1.0F : 0.0F);

        ClampedItemPropertyFunction reinforcementProperty = (stack, level, livingEntity, value) -> {
            ReinforcementTier tier = stack.get(AetherIIDataComponents.REINFORCEMENT_TIER);
            return tier != null ? tier.getTier() * 0.1F : Float.NEGATIVE_INFINITY;
        };
        ItemProperties.registerGeneric(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "reinforcement_tier"), reinforcementProperty);
    }

    private static void registerShieldProperties(Item item) {
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("blocking"), (stack, level, livingEntity, value) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1.0F : 0.0F);
    }
}