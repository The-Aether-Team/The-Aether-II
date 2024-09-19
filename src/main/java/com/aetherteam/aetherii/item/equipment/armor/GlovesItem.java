package com.aetherteam.aetherii.item.equipment.armor;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.inventory.AetherIIAccessorySlots;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GlovesItem extends Item implements Accessory {
    public static final ResourceLocation BASE_GLOVES_COOLDOWN_RESTORATION_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "base_gloves_cooldown_restoration");

    protected final Holder<ArmorMaterial> material;
    private final double restoration;
    protected ResourceLocation GLOVES_TEXTURE;


    public GlovesItem(Holder<ArmorMaterial> material, double restoration, String glovesName, Properties properties) {
        this(material, restoration, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, glovesName), properties);
    }

    public GlovesItem(Holder<ArmorMaterial> material, double restoration, ResourceLocation glovesName, Properties properties) {
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

    public Holder<ArmorMaterial> getMaterial() {
        return this.material;
    }

    public void setRenderTexture(String modId, String registryName) {
        this.GLOVES_TEXTURE = ResourceLocation.fromNamespaceAndPath(modId, "textures/models/accessory/handwear/" + registryName + "_accessory.png");
    }

    public ResourceLocation getGlovesTexture() {
        return this.GLOVES_TEXTURE;
    }

    public static int getDurability(int durabilityFactor) {
        return 13 * durabilityFactor;
    } //todo
}
