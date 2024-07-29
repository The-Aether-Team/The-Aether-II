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
    private static final ResourceLocation RELIC_PREDICATE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "relic_items");
    private static final ResourceLocation HANDWEAR_PREDICATE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "handwear_items");
    private static final ResourceLocation ACCESSORY_PREDICATE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "accessory_items");

    public static final AetherIISlotHandling INSTANCE = new AetherIISlotHandling();

    private static SlotTypeReference RELIC_SLOT;
    private static SlotTypeReference HANDWEAR_SLOT;
    private static SlotTypeReference ACCESSORY_SLOT;

    private AetherIISlotHandling() {
        AccessoriesAPI.registerPredicate(RELIC_PREDICATE, SlotBasedPredicate.ofItem(item -> new ItemStack(item).is(AetherIITags.Items.EQUIPMENT_RELICS)));
        AccessoriesAPI.registerPredicate(HANDWEAR_PREDICATE, SlotBasedPredicate.ofItem(item -> new ItemStack(item).is(AetherIITags.Items.EQUIPMENT_HANDWEAR)));
        AccessoriesAPI.registerPredicate(ACCESSORY_PREDICATE, SlotBasedPredicate.ofItem(item -> new ItemStack(item).is(AetherIITags.Items.EQUIPMENT_ACCESSORIES)));
    }

    @Override
    public void registerSlots(UniqueSlotHandling.UniqueSlotBuilderFactory factory) {
        RELIC_SLOT = factory.create(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "relic_slot"), 2).slotPredicates(RELIC_PREDICATE).validTypes(EntityType.PLAYER).allowEquipFromUse(true).build();
        HANDWEAR_SLOT = factory.create(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "handwear_slot"), 1).slotPredicates(HANDWEAR_PREDICATE).validTypes(EntityType.PLAYER).allowEquipFromUse(true).build();
        ACCESSORY_SLOT = factory.create(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "accessory_slot"), 1).slotPredicates(ACCESSORY_PREDICATE).validTypes(EntityType.PLAYER).allowEquipFromUse(true).build();
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
