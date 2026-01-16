package com.aetherteam.aetherii.item.equipment.accessories;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.equipment.ArmorMaterial;

import java.util.Set;

public class GlovesItem extends AccessoryItem {
    public static final ResourceLocation BASE_GLOVES_ENDURANCE_RECOVERY_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "base_gloves_endurance_recovery");
    public static final ResourceLocation BASE_GLOVES_MAXIMUM_ENDURANCE_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "base_gloves_maximum_endurance");

    private final double maxEndurance;
    private final double enduranceRecovery;
    protected ResourceLocation glovesTexture;

    public GlovesItem(ArmorMaterial material, double maxEndurance, double enduranceRecovery, Properties properties) {
        super(properties.durability(13 * material.durability()), AccessoryContainer.SlotType.HANDWEAR);
        this.maxEndurance = maxEndurance;
        this.enduranceRecovery = enduranceRecovery;
        this.setRenderTexture(material.assetId().location().getNamespace(), material.assetId().location().getPath());
    }

    @Override
    public Set<ConditionalAttribute> gatherAttributes(Set<ConditionalAttribute> attributes) {
        attributes = super.gatherAttributes(attributes);
        attributes.add(new ConditionalAttribute(AetherIIAttributes.MAXIMUM_ENDURANCE, new ConditionalModifier(BASE_GLOVES_MAXIMUM_ENDURANCE_ID, (stack) -> ((GlovesItem) stack.getItem()).getMaxEndurance(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), (stack, wearer) -> true));
        attributes.add(new ConditionalAttribute(AetherIIAttributes.ENDURANCE_RECOVERY, new ConditionalModifier(BASE_GLOVES_ENDURANCE_RECOVERY_ID, (stack) -> ((GlovesItem) stack.getItem()).getEnduranceRecovery(), AttributeModifier.Operation.ADD_VALUE), (stack, wearer) -> true));
        return attributes;
    }

    public void setRenderTexture(String modId, String registryName) {
        this.glovesTexture = ResourceLocation.fromNamespaceAndPath(modId, "textures/entity/equipment/humanoid_gloves/" + registryName + ".png");
    }

    public ResourceLocation getGlovesTexture() {
        return this.glovesTexture;
    }

    public double getMaxEndurance() {
        return this.maxEndurance;
    }

    public double getEnduranceRecovery() {
        return this.enduranceRecovery;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return false;
    }
}
