package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.block.AetherIIFluids;
import com.aetherteam.aetherii.client.event.listeners.DimensionClientListener;
import com.aetherteam.aetherii.client.gui.screen.HighlandsReceivingLevelScreen;
import com.aetherteam.aetherii.client.particle.AetherIIParticleFactories;
import com.aetherteam.aetherii.client.renderer.AetherIIOverlays;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderers;
import com.aetherteam.aetherii.client.renderer.item.color.AetherIIItemTintSources;
import com.aetherteam.aetherii.client.renderer.item.properties.AetherIIItemModelProperties;
import com.aetherteam.aetherii.client.renderer.level.AetherIIRenderEffects;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import com.aetherteam.aetherii.inventory.menu.AetherIIMenuTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import com.aetherteam.nitrogen.event.listeners.TooltipListeners;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionTransitionScreenEvent;
import net.neoforged.neoforge.common.NeoForge;

public class AetherIIClient {
    public static void clientInit(IEventBus bus) {
        bus.addListener(AetherIIClient::clientSetup);
        bus.addListener(AetherIIClient::registerDimensionTransitionScreens);

        AetherIIClient.eventSetup(bus);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            AetherIIAtlases.registerSkyrootChestAtlases();
            registerTooltipOverrides();

            ItemBlockRenderTypes.setRenderLayer(AetherIIFluids.FLOWING_ACID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(AetherIIFluids.ACID.get(), RenderType.translucent());
        });

        AetherIIRenderers.registerAccessoryRenderers();
    }

    public static void eventSetup(IEventBus neoBus) {
        IEventBus bus = NeoForge.EVENT_BUS;

        AetherIIClientEventListeners.listen(bus);

        bus.addListener(DimensionClientListener::onRenderFog);

        neoBus.addListener(AetherIIMenuTypes::registerMenuScreens);
        neoBus.addListener(AetherIIColorResolvers::registerBlockColor);
        neoBus.addListener(AetherIIParticleFactories::registerParticleFactories);
        neoBus.addListener(AetherIIOverlays::registerOverlays);
        neoBus.addListener(AetherIIRenderers::registerAddLayer);
        neoBus.addListener(AetherIIRenderers::registerEntityRenderers);
        neoBus.addListener(AetherIIRenderers::registerLayerDefinition);
        neoBus.addListener(AetherIIRenderers::registerBakedModels);
        neoBus.addListener(AetherIIRenderers::registerRenderStateModifier);
        neoBus.addListener(AetherIIRenderEffects::registerRenderEffects);
        neoBus.addListener(AetherIIShaders::registerShaders);
        neoBus.addListener(AetherIIItemDecorators::registerItemDecorators);
        neoBus.addListener(AetherIIClientTooltips::registerClientTooltipComponents);
        neoBus.addListener(AetherIIClientExtensions::registerClientItemExtensions);
        neoBus.addListener(AetherIIRenderTypes::registerRenderBuffers);
        neoBus.addListener(AetherIIRecipeBookCategories::registerRecipeBookSearchCategories);
        neoBus.addListener(AetherIIItemModelProperties::registerSelectProperties);
        neoBus.addListener(AetherIIItemModelProperties::registerRangeSelectProperties);
        neoBus.addListener(AetherIIAtlases::registerAtlases);
        neoBus.addListener(AetherIIItemTintSources::registerTintSources);
    }

    public static void registerDimensionTransitionScreens(RegisterDimensionTransitionScreenEvent event) {
        event.registerIncomingEffect(AetherIIDimensions.AETHER_HIGHLANDS_LEVEL, HighlandsReceivingLevelScreen::new);
        event.registerOutgoingEffect(AetherIIDimensions.AETHER_HIGHLANDS_LEVEL, HighlandsReceivingLevelScreen::new);
    }

    public static void registerTooltipOverrides() {
        TooltipListeners.TooltipPredicate setBonusPredicate = (player, itemStack, components, context, component) -> {
            if (player != null && component.getString().contains("%s")) {
                TagKey<Item> armorSet = itemStack.get(AetherIIDataComponents.ARMOR_SET);
                if (armorSet != null) {
                    int currentEquipmentCount = EquipmentUtil.getArmorCount(player, armorSet);
                    Component finalComponent;
                    if (currentEquipmentCount >= 3) {
                        finalComponent = Component.literal("3/3").withStyle(ChatFormatting.WHITE);
                    } else {
                        finalComponent = Component.literal(currentEquipmentCount + "/3").withStyle(ChatFormatting.GRAY);
                    }
                    return Component.translatable(component.getString(), finalComponent);
                }
            }
            return component;
        };

        TooltipListeners.PREDICATES.put(AetherIIItems.TAEGORE_HIDE_HELMET, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.TAEGORE_HIDE_CHESTPLATE, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.TAEGORE_HIDE_LEGGINGS, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.TAEGORE_HIDE_BOOTS, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.TAEGORE_HIDE_GLOVES, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.BURRUKAI_PELT_HELMET, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.BURRUKAI_PELT_CHESTPLATE, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.BURRUKAI_PELT_LEGGINGS, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.BURRUKAI_PELT_BOOTS, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.BURRUKAI_PELT_GLOVES, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.ZANITE_HELMET, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.ZANITE_CHESTPLATE, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.ZANITE_LEGGINGS, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.ZANITE_BOOTS, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.ZANITE_GLOVES, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.ARKENIUM_HELMET, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.ARKENIUM_CHESTPLATE, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.ARKENIUM_LEGGINGS, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.ARKENIUM_BOOTS, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.ARKENIUM_GLOVES, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.GRAVITITE_HELMET, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.GRAVITITE_CHESTPLATE, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.GRAVITITE_LEGGINGS, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.GRAVITITE_BOOTS, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.GRAVITITE_GLOVES, setBonusPredicate);
    }
}