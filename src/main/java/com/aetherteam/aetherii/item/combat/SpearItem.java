package com.aetherteam.aetherii.item.combat;

import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.item.AetherIIToolActions;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ToolAction;

import java.util.UUID;

public class SpearItem extends TieredItem implements Vanishable {
    public static final UUID BASE_STAB_RADIUS_UUID = UUID.fromString("B618A75A-8C8E-4611-B72F-4648C31955CB");
    public static final UUID BASE_STAB_DISTANCE_UUID = UUID.fromString("AA902B99-D675-4498-9955-2AC8DE01CB70");

    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public SpearItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Item.Properties properties) {
        super(tier, properties);
        this.attackDamage = (float) attackDamageModifier + tier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeedModifier, AttributeModifier.Operation.ADDITION));
        builder.put(AetherIIAttributes.STAB_RADIUS.get(), new AttributeModifier(BASE_STAB_RADIUS_UUID, "Weapon modifier", 1.5, AttributeModifier.Operation.ADDITION));
        builder.put(AetherIIAttributes.STAB_DISTANCE.get(), new AttributeModifier(BASE_STAB_DISTANCE_UUID, "Weapon modifier", 5.0, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public float getDamage() {
        return this.attackDamage;
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity livingEntity) {
        if (state.getDestroySpeed(level, pos) != 0.0F) {
            stack.hurtAndBreak(2, livingEntity, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return AetherIIToolActions.DEFAULT_SPEAR_ACTIONS.contains(toolAction);
    }
}
