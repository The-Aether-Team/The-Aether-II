package com.aetherteam.aetherii.item;

import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import com.aetherteam.aetherii.item.consumeeffect.ApplyStatusEffectsConsumeEffect;
import com.aetherteam.aetherii.item.consumeeffect.ReduceStatusEffectConsumeEffect;
import com.aetherteam.aetherii.item.consumeeffect.RemoveStatusEffectsConsumeEffect;
import com.aetherteam.aetherii.item.components.Consumable;
import com.aetherteam.aetherii.item.components.Consumables;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

import java.util.List;

public class AetherIIConsumables {
    public static final Consumable FAST = Consumables.defaultFood().consumeSeconds(0.8F).build();
    public static final Consumable WATER_VIAL = Consumables.defaultDrink().onConsume(new ReduceStatusEffectConsumeEffect(AetherIIPreventatives.WATER_VIAL)).build();
    public static final Consumable BANDAGE = Consumables.defaultFood().animation(UseAnim.BOW).consumeSeconds(1.3F).onConsume(new ReduceStatusEffectConsumeEffect(AetherIIPreventatives.BANDAGE)).build();
    public static final Consumable SPLINT = Consumables.defaultFood().animation(UseAnim.BOW).consumeSeconds(1.3F).onConsume(new RemoveStatusEffectsConsumeEffect(AetherIIMobEffects.FRACTURE)).build();
    public static final Consumable ANTITOXIN_VIAL = Consumables.defaultDrink().consumeSeconds(1.3F).onConsume(new ReduceStatusEffectConsumeEffect(AetherIIPreventatives.ANTITOXIN_VIAL)).build();
    public static final Consumable ANTIVENOM_VIAL = Consumables.defaultDrink().consumeSeconds(1.3F).onConsume(new ReduceStatusEffectConsumeEffect(AetherIIPreventatives.ANTIVENOM_VIAL)).build();
    public static final Consumable VALKYRIE_TEA = Consumables.defaultDrink().consumeSeconds(1.3F).onConsume(new ApplyStatusEffectsConsumeEffect(List.of(
            new MobEffectInstance(AetherIIMobEffects.SATURATION_BOOST.get(), 1000, 0, false, true, true)
    ), 1.0F)).build();

    public static Consumable get(ItemStack stack) {
        if (stack.is(AetherIIItems.BLUEBERRY.get())
                || stack.is(AetherIIItems.ENCHANTED_BLUEBERRY.get())
                || stack.is(AetherIIItems.WYNDBERRY.get())
                || stack.is(AetherIIItems.ENCHANTED_WYNDBERRY.get())
                || stack.is(AetherIIItems.SATIVAL_BULB.get())
                || stack.is(AetherIIItems.SKYROOT_LIZARD_ON_A_STICK.get())
                || stack.is(AetherIIItems.ROASTED_SKYROOT_LIZARD_ON_A_STICK.get())) {
            return FAST;
        } else if (stack.is(AetherIIItems.WATER_VIAL.get())) {
            return WATER_VIAL;
        } else if (stack.is(AetherIIItems.BANDAGE.get())) {
            return BANDAGE;
        } else if (stack.is(AetherIIItems.SPLINT.get())) {
            return SPLINT;
        } else if (stack.is(AetherIIItems.ANTITOXIN_VIAL.get())) {
            return ANTITOXIN_VIAL;
        } else if (stack.is(AetherIIItems.ANTIVENOM_VIAL.get())) {
            return ANTIVENOM_VIAL;
        } else if (stack.is(AetherIIItems.VALKYRIE_TEA.get())) {
            return VALKYRIE_TEA;
        } else if (stack.is(AetherIIItems.SKYROOT_MILK_BUCKET.get())) {
            return Consumables.MILK_BUCKET;
        }
        return null;
    }
}
