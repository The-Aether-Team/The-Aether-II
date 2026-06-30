package com.aetherteam.aetherii.item.equipment.weapons;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.equipment.AetherIINeoItemAbilities;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import com.aetherteam.aetherii.item.components.ItemAttributeModifiers;
import net.minecraftforge.common.ToolAction;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class TieredShortswordItem extends TieredItem {
    public static final ResourceLocation BASE_SWEEP_RANGE_ID = new ResourceLocation(AetherII.MODID, "base_sweep_range");
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public TieredShortswordItem(Tier tier, float damage, float speed, List<ItemAttributeModifiers.Entry> specialDamage, Item.Properties properties) {
        super(tier, properties.defaultDurability(tier.getUses()));
        this.defaultModifiers = createAttributes(tier, damage, speed, specialDamage);
    }

    public static Multimap<Attribute, AttributeModifier> createAttributes(Tier tier, int attackDamage, float attackSpeed) {
        return createAttributes(tier, (float) attackDamage, attackSpeed);
    }

    public static Multimap<Attribute, AttributeModifier> createAttributes(Tier tier, float attackDamage, float attackSpeed) {
        return createAttributes(tier, attackDamage, attackSpeed, List.of());
    }

    public static Multimap<Attribute, AttributeModifier> createAttributes(Tier tier, float attackDamage, float attackSpeed, List<ItemAttributeModifiers.Entry> specialDamage) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        for (ItemAttributeModifiers.Entry entry : specialDamage) {
            builder.put(entry.attribute().value(), toLegacyModifier(entry.modifier()));
        }
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(AetherIIAttributes.SWEEP_RANGE.get(), new AttributeModifier(uuid(BASE_SWEEP_RANGE_ID), BASE_SWEEP_RANGE_ID.toString(), 2.0, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction itemAbility) {
        return AetherIINeoItemAbilities.DEFAULT_SHORTSWORD_ACTIONS.contains(itemAbility);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    private static AttributeModifier toLegacyModifier(AttributeModifier modifier) {
        return new AttributeModifier(modifier.getId(), modifier.getName(), modifier.getAmount(), modifier.getOperation());
    }

    private static UUID uuid(ResourceLocation id) {
        return UUID.nameUUIDFromBytes(id.toString().getBytes(StandardCharsets.UTF_8));
    }
}
