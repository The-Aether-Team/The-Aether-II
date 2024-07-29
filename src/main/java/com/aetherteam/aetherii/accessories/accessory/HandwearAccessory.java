package com.aetherteam.aetherii.accessories.accessory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.accessory.GlovesRenderer;
import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.combat.GlovesItem;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public class HandwearAccessory implements Accessory { //todo attach to GlovesItem and move registries to item and client registries
    public static final ResourceLocation BASE_GLOVES_STAMINA_RESTORATION_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "base_gloves_stamina_restoration");

    public static void clientInit() {
        AccessoriesRendererRegistry.registerRenderer(AetherIIItems.TAEGORE_HIDE_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherIIItems.BURRUKAI_PELT_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherIIItems.ZANITE_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherIIItems.ARKENIUM_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherIIItems.GRAVITITE_GLOVES.get(), GlovesRenderer::new);
    }

    public static void init() {
        AccessoriesAPI.registerAccessory(AetherIIItems.TAEGORE_HIDE_GLOVES.get(), new HandwearAccessory(5));
        AccessoriesAPI.registerAccessory(AetherIIItems.BURRUKAI_PELT_GLOVES.get(), new HandwearAccessory(10));
        AccessoriesAPI.registerAccessory(AetherIIItems.ZANITE_GLOVES.get(), new HandwearAccessory(15));
        AccessoriesAPI.registerAccessory(AetherIIItems.ARKENIUM_GLOVES.get(), new HandwearAccessory(15));
        AccessoriesAPI.registerAccessory(AetherIIItems.GRAVITITE_GLOVES.get(), new HandwearAccessory(20));
    }

    private final double restoration;

    public HandwearAccessory(double restoration) {
        this.restoration = restoration;
    }

    @Override
    public void getModifiers(ItemStack stack, SlotReference reference, AccessoryAttributeBuilder builder) {
        if (reference.slotName().equals("aether_ii:handwear_slot")) {
            builder.addStackable(AetherIIAttributes.SHIELD_STAMINA_RESTORATION, new AttributeModifier(BASE_GLOVES_STAMINA_RESTORATION_ID, this.restoration, AttributeModifier.Operation.ADD_VALUE));
        }
    }
}
