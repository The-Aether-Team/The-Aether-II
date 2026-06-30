package com.aetherteam.aetherii.item.equipment.accessories;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Set;

public class GlovesItem extends AccessoryItem {
    public static final ResourceLocation BASE_GLOVES_ENDURANCE_RECOVERY_ID = new ResourceLocation(AetherII.MODID, "base_gloves_endurance_recovery");
    public static final ResourceLocation BASE_GLOVES_MAXIMUM_ENDURANCE_ID = new ResourceLocation(AetherII.MODID, "base_gloves_maximum_endurance");

    private final double maxEndurance;
    private final double enduranceRecovery;
    private final Holder<SoundEvent> equipSound;

    public GlovesItem(ArmorMaterial material, double maxEndurance, double enduranceRecovery, Properties properties) {
        super(properties.durability(material.getDurabilityForType(ArmorItem.Type.BOOTS)), AccessoryContainer.SlotType.HANDWEAR);
        this.maxEndurance = maxEndurance;
        this.enduranceRecovery = enduranceRecovery;
        this.equipSound = Holder.direct(material.getEquipSound());
    }

    @Override
    public Set<ConditionalAttribute> gatherAttributes(Set<ConditionalAttribute> attributes) {
        attributes = super.gatherAttributes(attributes);
        attributes.add(new ConditionalAttribute(AetherIIAttributes.MAXIMUM_ENDURANCE, new ConditionalModifier(BASE_GLOVES_MAXIMUM_ENDURANCE_ID, (stack) -> ((GlovesItem) stack.getItem()).getMaxEndurance(), AttributeModifier.Operation.MULTIPLY_TOTAL), (stack, wearer) -> true));
        attributes.add(new ConditionalAttribute(AetherIIAttributes.ENDURANCE_RECOVERY, new ConditionalModifier(BASE_GLOVES_ENDURANCE_RECOVERY_ID, (stack) -> ((GlovesItem) stack.getItem()).getEnduranceRecovery(), AttributeModifier.Operation.ADDITION), (stack, wearer) -> true));
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
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
}
