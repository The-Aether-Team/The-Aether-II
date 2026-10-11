package com.aetherteam.aetherii.item.equipment.accessories;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.item.components.Accessory;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.equipment.ArmorMaterial;

import java.util.Set;

public class GlovesItem extends AccessoryItem {
    public static final Identifier BASE_GLOVES_ENDURANCE_RECOVERY_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "base_gloves_endurance_recovery");
    public static final Identifier BASE_GLOVES_MAXIMUM_ENDURANCE_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "base_gloves_maximum_endurance");

    private final double maxEndurance;
    private final double enduranceRecovery;
    private final Holder<SoundEvent> equipSound;

    public GlovesItem(ArmorMaterial material, double maxEndurance, double enduranceRecovery, Properties properties) {
        super(properties.durability(13 * material.durability()).component(AetherIIDataComponents.ACCESSORY, new Accessory(material.assetId())), AccessoryContainer.SlotType.HANDWEAR);
        this.maxEndurance = maxEndurance;
        this.enduranceRecovery = enduranceRecovery;
        this.equipSound = material.equipSound();
    }

    @Override
    public Set<ConditionalAttribute> gatherAttributes(Set<ConditionalAttribute> attributes) {
        attributes = super.gatherAttributes(attributes);
        attributes.add(new ConditionalAttribute(AetherIIAttributes.MAXIMUM_ENDURANCE, new ConditionalModifier(BASE_GLOVES_MAXIMUM_ENDURANCE_ID, (stack) -> ((GlovesItem) stack.getItem()).getMaxEndurance(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), (stack, wearer) -> true));
        attributes.add(new ConditionalAttribute(AetherIIAttributes.ENDURANCE_RECOVERY, new ConditionalModifier(BASE_GLOVES_ENDURANCE_RECOVERY_ID, (stack) -> ((GlovesItem) stack.getItem()).getEnduranceRecovery(), AttributeModifier.Operation.ADD_VALUE), (stack, wearer) -> true));
        return attributes;
    }

    public double getMaxEndurance() {
        return this.maxEndurance;
    }

    public double getEnduranceRecovery() {
        return this.enduranceRecovery;
    }

    @Override
    public Holder<SoundEvent> getEquipSound() {
        return this.equipSound;
    }

    @Override
    public boolean rendersInFirstPerson(ItemStack stack) {
        return true;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return false;
    }
}
