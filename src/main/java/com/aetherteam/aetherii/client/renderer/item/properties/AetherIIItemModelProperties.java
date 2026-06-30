package com.aetherteam.aetherii.client.renderer.item.properties;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.item.properties.conditional.LassoThrow;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.miscellaneous.glider.AercloudGliderItem;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AetherIIItemModelProperties {
    private static final ResourceLocation BLOCKING = new ResourceLocation("blocking");
    private static final ResourceLocation PULL = new ResourceLocation("pull");
    private static final ResourceLocation PULLING = new ResourceLocation("pulling");
    private static final ResourceLocation CHARGED = new ResourceLocation("charged");
    private static final ResourceLocation PARACHUTING = new ResourceLocation(AetherII.MODID, "parachuting");
    private static final ResourceLocation LASSO_THROW = new ResourceLocation(AetherII.MODID, "lasso_throw");
    private static final ResourceLocation HEALING_STONE_CHARGES = new ResourceLocation(AetherII.MODID, "healing_stone_charges");
    private static final ClampedItemPropertyFunction SHIELD_BLOCKING = (stack, level, entity, seed) ->
            entity != null && entity.isUsingItem() && ItemStack.isSameItemSameTags(entity.getUseItem(), stack) ? 1.0F : 0.0F;
    private static final ClampedItemPropertyFunction CROSSBOW_PULL = (stack, level, entity, seed) -> {
        if (entity == null || CrossbowItem.isCharged(stack)) {
            return 0.0F;
        }
        return (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / (float) CrossbowItem.getChargeDuration(stack);
    };
    private static final ClampedItemPropertyFunction CROSSBOW_PULLING = (stack, level, entity, seed) ->
            isUsingCrossbow(stack, entity) && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
    private static final ClampedItemPropertyFunction CROSSBOW_CHARGED = (stack, level, entity, seed) ->
            CrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
    private static final ClampedItemPropertyFunction PARACHUTING_PROPERTY = (stack, level, entity, seed) ->
            isParachuting(stack, entity) ? 1.0F : 0.0F;
    private static final ClampedItemPropertyFunction LASSO_THROW_PROPERTY = (stack, level, entity, seed) ->
            LassoThrow.get(stack, level, entity) ? 1.0F : 0.0F;
    private static final ClampedItemPropertyFunction HEALING_STONE_CHARGES_PROPERTY = (stack, level, entity, seed) ->
            com.aetherteam.aetherii.item.components.AetherIIDataComponents.getOrDefault(stack, com.aetherteam.aetherii.item.components.AetherIIDataComponents.HEALING_STONE_CHARGES, 0) / 10.0F;

    public static void registerItemProperties() {
        registerShield(AetherIIItems.SKYROOT_SHIELD.get());
        registerShield(AetherIIItems.BURRUKAI_PLATE_SHIELD.get());
        registerShield(AetherIIItems.ZANITE_SHIELD.get());
        registerShield(AetherIIItems.ARKENIUM_SHIELD.get());
        registerShield(AetherIIItems.GRAVITITE_SHIELD.get());
        registerCrossbow(AetherIIItems.SKYROOT_CROSSBOW.get());
        registerCrossbow(AetherIIItems.HOLYSTONE_CROSSBOW.get());
        registerCrossbow(AetherIIItems.ZANITE_CROSSBOW.get());
        registerCrossbow(AetherIIItems.ARKENIUM_CROSSBOW.get());
        registerCrossbow(AetherIIItems.GRAVITITE_CROSSBOW.get());
        registerGlider(AetherIIItems.COLD_AERCLOUD_GLIDER.get());
        registerGlider(AetherIIItems.GOLDEN_AERCLOUD_GLIDER.get());
        registerGlider(AetherIIItems.BLUE_AERCLOUD_GLIDER.get());
        registerGlider(AetherIIItems.PURPLE_AERCLOUD_GLIDER.get());
        registerLasso(AetherIIItems.BRETTL_LASSO.get());
        registerHealingStone(AetherIIItems.HEALING_STONE.get());
    }

    private static void registerShield(Item item) {
        ItemProperties.register(item, BLOCKING, SHIELD_BLOCKING);
    }

    private static void registerCrossbow(Item item) {
        ItemProperties.register(item, PULL, CROSSBOW_PULL);
        ItemProperties.register(item, PULLING, CROSSBOW_PULLING);
        ItemProperties.register(item, CHARGED, CROSSBOW_CHARGED);
    }

    private static void registerGlider(Item item) {
        ItemProperties.register(item, PARACHUTING, PARACHUTING_PROPERTY);
    }

    private static void registerLasso(Item item) {
        ItemProperties.register(item, LASSO_THROW, LASSO_THROW_PROPERTY);
    }

    private static void registerHealingStone(Item item) {
        ItemProperties.register(item, HEALING_STONE_CHARGES, HEALING_STONE_CHARGES_PROPERTY);
    }

    private static boolean isUsingCrossbow(ItemStack stack, LivingEntity entity) {
        return entity != null
                && entity.isUsingItem()
                && entity.getUseItem().getItem() instanceof CrossbowItem
                && ItemStack.isSameItemSameTags(entity.getUseItem(), stack);
    }

    private static boolean isParachuting(ItemStack stack, LivingEntity entity) {
        return entity != null
                && entity.isUsingItem()
                && entity.getUseItem().getItem() instanceof AercloudGliderItem
                && ItemStack.isSameItemSameTags(entity.getUseItem(), stack);
    }
}
