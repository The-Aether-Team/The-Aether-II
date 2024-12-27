package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIFluids;
import com.aetherteam.aetherii.client.event.listeners.DimensionClientListener;
import com.aetherteam.aetherii.client.gui.screen.HighlandsReceivingLevelScreen;
import com.aetherteam.aetherii.client.particle.AetherIIParticleFactories;
import com.aetherteam.aetherii.client.renderer.AetherIIOverlays;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderers;
import com.aetherteam.aetherii.client.renderer.entity.MoaRenderer;
import com.aetherteam.aetherii.client.renderer.level.AetherIIRenderEffects;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import com.aetherteam.aetherii.inventory.menu.AetherIIMenuTypes;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.ModelManagerAccessor;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
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

        ModelManagerAccessor.setAtlases(ImmutableMap.<ResourceLocation, ResourceLocation>builder()
                .putAll(ModelManagerAccessor.getAtlases())
                .put(MoaRenderer.MOA_FEATHER_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_feather"))
                .put(MoaRenderer.MOA_EYES_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_eyes"))
                .put(MoaRenderer.MOA_KERATIN_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_keratin"))
                .build()); //todo 1.21.3 replaces this with an event
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            AetherIIAtlases.registerSkyrootChestAtlases();
            registerItemModelProperties();
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
    }

    public static void registerDimensionTransitionScreens(RegisterDimensionTransitionScreenEvent event) {
        event.registerIncomingEffect(AetherIIDimensions.AETHER_HIGHLANDS_LEVEL, HighlandsReceivingLevelScreen::new);
        event.registerOutgoingEffect(AetherIIDimensions.AETHER_HIGHLANDS_LEVEL, HighlandsReceivingLevelScreen::new);
    }

    public static void registerItemModelProperties() {
        //TODO Properties Support in 1.21.4
        /*registerMoaFeatherProperties(AetherIIItems.MOA_FEATHER.get());

        registerMoaEggProperties(AetherIIItems.MOA_EGG.get());

        registerGliderProperties(AetherIIItems.COLD_AERCLOUD_GLIDER.get(), false);
        registerGliderProperties(AetherIIItems.GOLDEN_AERCLOUD_GLIDER.get(), false);
        registerGliderProperties(AetherIIItems.BLUE_AERCLOUD_GLIDER.get(), true);
        registerGliderProperties(AetherIIItems.PURPLE_AERCLOUD_GLIDER.get(), true);

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

        registerHealingStoneProperties(AetherIIItems.HEALING_STONE.get());

        registerGenericProperties();*/
    }

    private static void registerMoaFeatherProperties(Item item) {

        /*ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "feather_color"), (stack, level, livingEntity, value) -> {
            Moa.FeatherColor featherColor = stack.get(AetherIIDataComponents.FEATHER_COLOR);
            if (featherColor != null) {
                return new BigDecimal((double) featherColor.ordinal() / Moa.FeatherColor.values().length, new MathContext(3)).floatValue();
            }
            return 0.0F;
        });*/
    }

    /*private static void registerMoaEggProperties(Item item) {
        ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "keratin_color"), (stack, level, livingEntity, value) -> {
            MoaEggType moaEggType = stack.get(AetherIIDataComponents.MOA_EGG_TYPE);
            if (moaEggType != null) {
                return new BigDecimal((double) moaEggType.keratinColor().ordinal() / Moa.KeratinColor.values().length, new MathContext(3)).floatValue();
            }
            return 0.0F;
        });
        ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "eye_color"), (stack, level, livingEntity, value) -> {
            MoaEggType moaEggType = stack.get(AetherIIDataComponents.MOA_EGG_TYPE);
            if (moaEggType != null) {
                return new BigDecimal((double) moaEggType.eyeColor().ordinal() / Moa.EyeColor.values().length, new MathContext(3)).floatValue();
            }
            return 0.0F;
        });
        ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "feather_color"), (stack, level, livingEntity, value) -> {
            MoaEggType moaEggType = stack.get(AetherIIDataComponents.MOA_EGG_TYPE);
            if (moaEggType != null) {
                return new BigDecimal((double) moaEggType.featherColor().ordinal() / Moa.FeatherColor.values().length, new MathContext(3)).floatValue();
            }
            return 0.0F;
        });
        ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "feather_shape"), (stack, level, livingEntity, value) -> {
            MoaEggType moaEggType = stack.get(AetherIIDataComponents.MOA_EGG_TYPE);
            if (moaEggType != null) {
                return new BigDecimal((double) moaEggType.featherShape().ordinal() / Moa.FeatherShape.values().length, new MathContext(3)).floatValue();
            }
            return 0.0F;
        });
    }

    private static void registerGliderProperties(Item item, boolean hasAbility) {
        ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "parachuting"), (stack, level, livingEntity, value) ->
                livingEntity == null ? 0.0F : ItemStack.isSameItem(stack, livingEntity.getUseItem()) && livingEntity.getUseItemRemainingTicks() > 0 ? 1.0F : 0.0F);
        if (hasAbility) {
            ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "dull"), (stack, level, livingEntity, value) ->
                    livingEntity == null ? 0.0F : livingEntity instanceof Player player && !player.getData(AetherIIDataAttachments.PLAYER).getCanRefuelAbilities().get(stack.getItemHolder()) ? 1.0F : 0.0F);
        }
    }

    private static void registerCrossbowProperties(Item item) {
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pull"), (stack, level, livingEntity, value) ->
                livingEntity == null ? 0.0F : TieredCrossbowItem.isCharged(stack) ? 0.0F : (float) (stack.getUseDuration(livingEntity) - livingEntity.getUseItemRemainingTicks()) / (float) ((TieredCrossbowItem) stack.getItem()).getCrossbowChargeDuration(stack, livingEntity));
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pulling"), (stack, level, livingEntity, value) ->
                livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("charged"), (stack, level, livingEntity, value) ->
                TieredCrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
    }

    private static void registerShieldProperties(Item item) {
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("blocking"), (stack, level, livingEntity, value) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1.0F : 0.0F);
    }

    private static void registerHealingStoneProperties(Item item) {
        ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "charge"), (stack, level, livingEntity, value) ->
                stack.get(AetherIIDataComponents.HEALING_STONE_CHARGES) != null ? stack.get(AetherIIDataComponents.HEALING_STONE_CHARGES) / 10.0F : 0.0F);
    }

    private static void registerGenericProperties() {
        ClampedItemPropertyFunction reinforcementProperty = (stack, level, livingEntity, value) -> {
            ReinforcementTier tier = stack.get(AetherIIDataComponents.REINFORCEMENT_TIER);
            return tier != null ? tier.getTier() * 0.1F : Float.NEGATIVE_INFINITY;
        };
        ItemProperties.registerGeneric(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "reinforcement_tier"), reinforcementProperty);
    }
*/
    public static void registerTooltipOverrides() {
        //TODO TOOL TIP PORT IN 1.21.4
        /*TooltipListeners.TooltipPredicate setBonusPredicate = (player, itemStack, components, context, component) -> {
            if (player != null && (itemStack.getItem() instanceof ArmorItem || itemStack.getItem() instanceof GlovesItem) && component.getString().contains("%s")) {
                Holder<ArmorMaterial> material = null;
                if (itemStack.getItem() instanceof ArmorItem armorItem) {
                    material = armorItem.getMaterial();
                } else if (itemStack.getItem() instanceof GlovesItem glovesItem) {
                    material = glovesItem.getMaterial();
                }
                if (material != null) {
                    int currentEquipmentCount = EquipmentUtil.getArmorCount(player, material);
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
        };*/

        /*TooltipListeners.PREDICATES.put(AetherIIItems.TAEGORE_HIDE_HELMET, setBonusPredicate);
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
        TooltipListeners.PREDICATES.put(AetherIIItems.GRAVITITE_GLOVES, setBonusPredicate);*/
    }
}