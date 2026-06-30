package com.aetherteam.aetherii.item.equipment.accessories;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;

import java.util.Set;

public class KineticThrustersItem extends AccessoryItem {
    private static final ResourceLocation MOVEMENT_SPEED = new ResourceLocation(AetherII.MODID, "accessory.ability.kinetic_thrusters.movement_speed");
    private static final ResourceLocation STEP_HEIGHT = new ResourceLocation(AetherII.MODID, "accessory.ability.kinetic_thrusters.step_height");

    public KineticThrustersItem(Properties properties) {
        super(properties.stacksTo(1), AccessoryContainer.SlotType.RELIC);
    }

    @Override
    public Set<ConditionalAttribute> gatherAttributes(Set<ConditionalAttribute> attributes) {
        attributes = super.gatherAttributes(attributes);
        attributes.add(new ConditionalAttribute(Attributes.MOVEMENT_SPEED, new ConditionalModifier(MOVEMENT_SPEED, 0.075, AttributeModifier.Operation.MULTIPLY_TOTAL), (stack, wearer) -> true));
        attributes.add(new ConditionalAttribute(ForgeMod.STEP_HEIGHT_ADDITION.getHolder().orElseThrow(), new ConditionalModifier(STEP_HEIGHT, 0.5F, AttributeModifier.Operation.ADDITION), (stack, wearer) -> !wearer.isShiftKeyDown()));
        return attributes;
    }
}
