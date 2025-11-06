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
    public static final ResourceLocation BASE_GLOVES_COOLDOWN_RESTORATION_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "base_gloves_cooldown_restoration");

    private final double restoration;
    protected ResourceLocation glovesTexture;

    public GlovesItem(ArmorMaterial material, double restoration, Properties properties) {
        super(properties.durability(13 * material.durability()), AccessoryContainer.SlotType.HANDWEAR);
        this.restoration = restoration;
        this.setRenderTexture(material.assetId().location().getNamespace(), material.assetId().location().getPath());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipComponents, tooltipFlag);
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = Multimaps.forMap(Map.of(AetherIIAttributes.SHIELD_COOLDOWN_REDUCTION, new AttributeModifier(BASE_GLOVES_COOLDOWN_RESTORATION_ID, this.getRestoration(), AttributeModifier.Operation.ADD_VALUE)));
        AccessoryUtil.addAttributeTooltips(stack, tooltipComponents, AttributeTooltipContext.of(null, context, tooltipDisplay, tooltipFlag), modifiers, "blocking");
    }

    public void setRenderTexture(String modId, String registryName) {
        this.glovesTexture = ResourceLocation.fromNamespaceAndPath(modId, "textures/entity/equipment/humanoid_gloves/" + registryName + ".png");
    }

    public ResourceLocation getGlovesTexture() {
        return this.glovesTexture;
    }

    public double getRestoration() {
        return this.restoration;
    }

    public static void updatePlayerAttributes(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            AttributeInstance attribute = livingEntity.getAttribute(AetherIIAttributes.SHIELD_COOLDOWN_REDUCTION);

            AccessoryUtil.getFirst(livingEntity, AccessoryContainer.SlotType.HANDWEAR).ifPresentOrElse((stack) -> {
                if (attribute != null && !attribute.hasModifier(BASE_GLOVES_COOLDOWN_RESTORATION_ID)) {
                    attribute.addTransientModifier(new AttributeModifier(BASE_GLOVES_COOLDOWN_RESTORATION_ID, ((GlovesItem) stack.getItem()).getRestoration(), AttributeModifier.Operation.ADD_VALUE));
                }
            }, () -> {
                if (attribute != null && attribute.hasModifier(BASE_GLOVES_COOLDOWN_RESTORATION_ID)) {
                    attribute.removeModifier(BASE_GLOVES_COOLDOWN_RESTORATION_ID);
                }
            });
        }
    }
}
