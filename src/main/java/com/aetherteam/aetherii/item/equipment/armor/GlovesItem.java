package com.aetherteam.aetherii.item.equipment.armor;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.inventory.AetherIIAccessorySlots;
import io.wispforest.accessories.api.AccessoryItem;
import io.wispforest.accessories.api.SoundEventData;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import org.jetbrains.annotations.Nullable;

public class GlovesItem extends AccessoryItem {
    public static final ResourceLocation BASE_GLOVES_COOLDOWN_RESTORATION_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "base_gloves_cooldown_restoration");

    private final double restoration;
    protected ResourceLocation glovesTexture;
    protected Holder<SoundEvent> equipSound;

    public GlovesItem(ArmorMaterial material, double restoration, Properties properties) {
        super(properties.durability(13 * material.durability()));
        this.restoration = restoration;
        this.setRenderTexture(material.assetId().location().getNamespace(), material.assetId().location().getPath());
        this.equipSound = material.equipSound();
    }

    @Override
    public void getDynamicModifiers(ItemStack stack, SlotReference reference, AccessoryAttributeBuilder builder) {
        if (reference.slotName().equals(AetherIIAccessorySlots.HANDWEAR_SLOT_LOCATION.toString())) {
            builder.addStackable(AetherIIAttributes.SHIELD_COOLDOWN_REDUCTION, new AttributeModifier(BASE_GLOVES_COOLDOWN_RESTORATION_ID, this.restoration, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    @Nullable
    @Override
    public SoundEventData getEquipSound(ItemStack stack, SlotReference reference) {
        return new SoundEventData(this.equipSound, 1.0F, 1.0F);
    }

    public void setRenderTexture(String modId, String registryName) {
        this.glovesTexture = ResourceLocation.fromNamespaceAndPath(modId, "textures/entity/equipment/humanoid_gloves/" + registryName + ".png");
    }

    public ResourceLocation getGlovesTexture() {
        return this.glovesTexture;
    }
}
