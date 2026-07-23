package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.api.AetherIIMenus;
import com.aetherteam.aetherii.client.event.listeners.DimensionClientListener;
import com.aetherteam.aetherii.client.gui.screen.HolyIslesReceivingLevelScreen;
import com.aetherteam.aetherii.client.particle.AetherIIParticleFactories;
import com.aetherteam.aetherii.client.renderer.AetherIIDimensionRenderers;
import com.aetherteam.aetherii.client.renderer.AetherIIOverlays;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderers;
import com.aetherteam.aetherii.client.renderer.item.color.AetherIIItemTintSources;
import com.aetherteam.aetherii.client.renderer.item.properties.AetherIIItemModelProperties;
import com.aetherteam.aetherii.client.sprite.AetherIISpriteSourceTypes;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import com.aetherteam.aetherii.inventory.menu.AetherIIMenuTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import com.aetherteam.nitrogen.event.listeners.TooltipListeners;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.CubeMapTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
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
            registerTooltipOverrides();
        });
    }

    public static void eventSetup(IEventBus neoBus) {
        IEventBus bus = NeoForge.EVENT_BUS;

        AetherIIClientEventListeners.listen(bus);

        bus.addListener(AetherIIRenderers::submitCustomGeometryRendering);
        bus.addListener(DimensionClientListener::onRenderFog);
        //bus.addListener(LevelClientListener::onKeyPress);
        bus.addListener(AetherIIDimensionRenderers::extractDimensionEffect);

        neoBus.addListener(AetherIIClient::registerMenuTextures);
        neoBus.addListener(AetherIIMenuTypes::registerMenuScreens);
        neoBus.addListener(AetherIIColorResolvers::registerColorResolvers);
        neoBus.addListener(AetherIIColorResolvers::registerBlockColor);
        neoBus.addListener(AetherIIParticleFactories::registerParticleFactories);
        neoBus.addListener(AetherIIOverlays::registerOverlays);
        neoBus.addListener(AetherIIRenderers::registerAddLayer);
        neoBus.addListener(AetherIIRenderers::registerEntityRenderers);
        neoBus.addListener(AetherIIRenderers::registerLayerDefinition);
        neoBus.addListener(AetherIIRenderers::registerItemModels);
        neoBus.addListener(AetherIIRenderers::registerBlockStateModels);
        neoBus.addListener(AetherIIRenderers::registerFluidModels);
        neoBus.addListener(AetherIIRenderers::registerBakedModels);
        neoBus.addListener(AetherIIRenderers::registerRenderStateModifier);
        neoBus.addListener(AetherIIRenderers::registerSpecialModelRenderers);
        neoBus.addListener(AetherIIDimensionRenderers::registerDimensionEffect);
        neoBus.addListener(AetherIIRenderPipelines::registerShaders);
        neoBus.addListener(AetherIIItemDecorators::registerItemDecorators);
        neoBus.addListener(AetherIIClientTooltips::registerClientTooltipComponents);
        neoBus.addListener(AetherIIClientExtensions::registerClientItemExtensions);
        neoBus.addListener(AetherIIRenderTypes::registerRenderBuffers);
        neoBus.addListener(AetherIIRecipeBookCategories::registerRecipeBookSearchCategories);
        neoBus.addListener(AetherIIItemModelProperties::registerConditionalProperties);
        neoBus.addListener(AetherIIItemModelProperties::registerSelectProperties);
        neoBus.addListener(AetherIIItemModelProperties::registerRangeSelectProperties);
        neoBus.addListener(AetherIIAtlases::registerAtlases);
        neoBus.addListener(AetherIISpriteSourceTypes::registerSpriteSourceTypes);
        neoBus.addListener(AetherIIItemTintSources::registerTintSources);
        neoBus.addListener(AetherIIClientCaches::registerReloadListeners);
        neoBus.addListener(AetherIIKeyMappings::registerKeyMappings);
    }

    public static void registerMenuTextures(AddClientReloadListenersEvent event) {
        Minecraft.getInstance().getTextureManager().register(AetherIIMenus.AETHER_II_PANORAMA, new CubeMapTexture(AetherIIMenus.AETHER_II_PANORAMA));
    }

    public static void registerDimensionTransitionScreens(RegisterDimensionTransitionScreenEvent event) {
        event.registerIncomingEffect(AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL, HolyIslesReceivingLevelScreen::new);
        event.registerOutgoingEffect(AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL, HolyIslesReceivingLevelScreen::new);
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

        TooltipListeners.PREDICATES.put(AetherIIItems.BEAST_PELT_HELMET, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.BEAST_PELT_CHESTPLATE, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.BEAST_PELT_LEGGINGS, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.BEAST_PELT_BOOTS, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.BEAST_PELT_GLOVES, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.BURRUKAI_PLATE_HELMET, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.BURRUKAI_PLATE_CHESTPLATE, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.BURRUKAI_PLATE_LEGGINGS, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.BURRUKAI_PLATE_BOOTS, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.BURRUKAI_PLATE_GLOVES, setBonusPredicate);
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
        TooltipListeners.PREDICATES.put(AetherIIItems.NEPTUNE_HELMET, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.NEPTUNE_CHESTPLATE, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.NEPTUNE_LEGGINGS, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.NEPTUNE_BOOTS, setBonusPredicate);
        TooltipListeners.PREDICATES.put(AetherIIItems.NEPTUNE_GLOVES, setBonusPredicate);
    }
}