package com.aetherteam.aetherii.accessories;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.slot.SlotBasedPredicate;
import io.wispforest.accessories.api.slot.SlotTypeReference;
import io.wispforest.accessories.api.slot.UniqueSlotHandling;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class AetherIISlotHandling implements UniqueSlotHandling.RegistrationCallback {
    public static final AetherIISlotHandling INSTANCE = new AetherIISlotHandling();

    private AetherIISlotHandling() { }

    private static SlotTypeReference RELIC_SLOT;
    private static SlotTypeReference HANDWEAR_SLOT;
    private static SlotTypeReference ACCESSORY_SLOT;

    @Override
    public void registerSlots(UniqueSlotHandling.UniqueSlotBuilderFactory factory) {
        ResourceLocation relicPredicate = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "relic_items");
        ResourceLocation handwearPredicate = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "handwear_items");
        ResourceLocation accessoryPredicate = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "accessory_items");

        AccessoriesAPI.registerPredicate(relicPredicate, SlotBasedPredicate.ofItem(item -> new ItemStack(item).is(AetherIITags.Items.EQUIPMENT_RELICS)));
        AccessoriesAPI.registerPredicate(handwearPredicate, SlotBasedPredicate.ofItem(item -> new ItemStack(item).is(AetherIITags.Items.EQUIPMENT_HANDWEAR)));
        AccessoriesAPI.registerPredicate(accessoryPredicate, SlotBasedPredicate.ofItem(item -> new ItemStack(item).is(AetherIITags.Items.EQUIPMENT_ACCESSORIES)));

        RELIC_SLOT = factory.create(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "relic_slot"), 2).slotPredicates(relicPredicate).validTypes(EntityType.PLAYER).build();
        HANDWEAR_SLOT = factory.create(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "handwear_slot"), 1).slotPredicates(handwearPredicate).validTypes(EntityType.PLAYER).build();
        ACCESSORY_SLOT = factory.create(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "accessory_slot"), 1).slotPredicates(accessoryPredicate).validTypes(EntityType.PLAYER).build();
    }

    @Nullable
    public static SlotTypeReference getRelicSlotType() {
        return RELIC_SLOT;
    }

    @Nullable
    public static SlotTypeReference getHandwearSlotType() {
        return HANDWEAR_SLOT;
    }

    @Nullable
    public static SlotTypeReference getAccessorySlotType() {
        return ACCESSORY_SLOT;
    }
}
