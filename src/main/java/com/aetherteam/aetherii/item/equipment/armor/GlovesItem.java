package com.aetherteam.aetherii.item.equipment.armor;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.inventory.AetherIIAccessorySlots;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.EquipmentAsset;

public class GlovesItem extends Item implements Accessory {
    public static final ResourceLocation BASE_GLOVES_COOLDOWN_RESTORATION_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "base_gloves_cooldown_restoration");

    protected final ResourceKey<EquipmentAsset> material;
    private final double restoration;
    protected ResourceLocation GLOVES_TEXTURE;


    public GlovesItem(ArmorMaterial material, double restoration, Properties properties) {
        this(material.assetId(), restoration, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, material.assetId().location().getPath() + "_gloves"), properties.durability(getDurability(material.durability())));
    }

    public GlovesItem(ResourceKey<EquipmentAsset> material, double restoration, ResourceLocation glovesName, Properties properties) {
        super(properties);
        this.material = material;
        this.restoration = restoration;
        this.setRenderTexture(glovesName.getNamespace(), glovesName.getPath());
    }

    @Override
    public void getDynamicModifiers(ItemStack stack, SlotReference reference, AccessoryAttributeBuilder builder) {
        if (reference.slotName().equals(AetherIIAccessorySlots.HANDWEAR_SLOT_LOCATION.toString())) {
            builder.addStackable(AetherIIAttributes.SHIELD_COOLDOWN_REDUCTION, new AttributeModifier(BASE_GLOVES_COOLDOWN_RESTORATION_ID, this.restoration, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    public ResourceKey<EquipmentAsset> getMaterial() {
        return this.material;
    } //todo this should be removeable because data components. all the render stuff has to be changed to that format

    public void setRenderTexture(String modId, String registryName) {
        this.GLOVES_TEXTURE = ResourceLocation.fromNamespaceAndPath(modId, "textures/models/accessory/handwear/" + registryName + "_accessory.png");
    }

    public ResourceLocation getGlovesTexture() {
        return this.GLOVES_TEXTURE;
    }

    public static int getDurability(int durabilityFactor) {
        return 13 * durabilityFactor;
    }
}
