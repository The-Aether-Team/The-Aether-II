package com.aetherteam.aetherii.item;

import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.item.consumeeffect.PreventStatusEffectConsumeEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.RemoveStatusEffectsConsumeEffect;

import java.util.List;

public class AetherIIConsumables {
    public static final Consumable FAST = Consumables.defaultFood().consumeSeconds(0.8F).build();
    public static final Consumable WATER_VIAL = Consumables.defaultDrink().build();
    public static final Consumable BANDAGE = Consumables.defaultFood().animation(ItemUseAnimation.BOW).consumeSeconds(1.3F).onConsume(new PreventStatusEffectConsumeEffect(AetherIIPreventatives.BANDAGE)).build();
    public static final Consumable SPLINT = Consumables.defaultFood().animation(ItemUseAnimation.BOW).consumeSeconds(1.3F).onConsume(new RemoveStatusEffectsConsumeEffect(AetherIIEffects.FRACTURE)).build();
    public static final Consumable ANTITOXIN_VIAL = Consumables.defaultDrink().consumeSeconds(1.3F).onConsume(new PreventStatusEffectConsumeEffect(AetherIIPreventatives.ANTITOXIN_VIAL)).build();
    public static final Consumable ANTIVENOM_VIAL = Consumables.defaultDrink().consumeSeconds(1.3F).onConsume(new PreventStatusEffectConsumeEffect(AetherIIPreventatives.ANTIVENOM_VIAL)).build();
    public static final Consumable VALKYRIE_TEA = Consumables.defaultDrink().consumeSeconds(1.3F).onConsume(new ApplyStatusEffectsConsumeEffect(List.of(
            new MobEffectInstance(AetherIIEffects.SATURATION_BOOST, 1000, 0, false, true, true)
    ), 1.0F)).build();
}
