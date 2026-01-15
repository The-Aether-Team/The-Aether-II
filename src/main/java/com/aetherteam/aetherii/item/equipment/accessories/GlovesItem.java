package com.aetherteam.aetherii.item.equipment.accessories;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.Map;
import java.util.function.Consumer;

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
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = Multimaps.forMap(Map.of(
                AetherIIAttributes.MAXIMUM_ENDURANCE, new AttributeModifier(BASE_GLOVES_MAXIMUM_ENDURANCE_ID, this.getMaxEndurance(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                AetherIIAttributes.ENDURANCE_RECOVERY, new AttributeModifier(BASE_GLOVES_ENDURANCE_RECOVERY_ID, this.getEnduranceRecovery(), AttributeModifier.Operation.ADD_VALUE)
        ));
        AccessoryUtil.addAttributeTooltips(stack, tooltipComponents, AttributeTooltipContext.of(null, context, tooltipDisplay, tooltipFlag), modifiers, "blocking");
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

    public static void updatePlayerAttributes(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            AttributeInstance maximumEndurance = livingEntity.getAttribute(AetherIIAttributes.MAXIMUM_ENDURANCE);
            AttributeInstance enduranceRecovery = livingEntity.getAttribute(AetherIIAttributes.ENDURANCE_RECOVERY);

            AccessoryUtil.getFirst(livingEntity, AccessoryContainer.SlotType.HANDWEAR).ifPresentOrElse((stack) -> {
                if (maximumEndurance != null && !maximumEndurance.hasModifier(BASE_GLOVES_MAXIMUM_ENDURANCE_ID)) {
                    maximumEndurance.addTransientModifier(new AttributeModifier(BASE_GLOVES_MAXIMUM_ENDURANCE_ID, ((GlovesItem) stack.getItem()).getMaxEndurance(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                }
                if (enduranceRecovery != null && !enduranceRecovery.hasModifier(BASE_GLOVES_ENDURANCE_RECOVERY_ID)) {
                    enduranceRecovery.addTransientModifier(new AttributeModifier(BASE_GLOVES_ENDURANCE_RECOVERY_ID, ((GlovesItem) stack.getItem()).getEnduranceRecovery(), AttributeModifier.Operation.ADD_VALUE));
                }
            }, () -> {
                if (maximumEndurance != null && maximumEndurance.hasModifier(BASE_GLOVES_MAXIMUM_ENDURANCE_ID)) {
                    maximumEndurance.removeModifier(BASE_GLOVES_MAXIMUM_ENDURANCE_ID);
                }
                if (enduranceRecovery != null && enduranceRecovery.hasModifier(BASE_GLOVES_ENDURANCE_RECOVERY_ID)) {
                    enduranceRecovery.removeModifier(BASE_GLOVES_ENDURANCE_RECOVERY_ID);
                }
            });
        }
    }
}
