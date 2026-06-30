package com.aetherteam.aetherii.integration.jei.interpreter;

import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.BuildupContents;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class AmberDartInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
    @Override
    public String apply(ItemStack stack, UidContext context) {
        BuildupContents contents = AetherIIDataComponents.getOrDefault(stack, AetherIIDataComponents.BUILDUP_CONTENTS, new BuildupContents(EffectBuildupPresets.VULNERABILITY));
        if (contents == null) {
            return IIngredientSubtypeInterpreter.NONE;
        }
        ResourceLocation effect = BuiltInRegistries.MOB_EFFECT.getKey(contents.preset().type().value());
        return effect + ":" + contents.preset().duration() + ":" + contents.preset().amplifier() + ":" + contents.amount();
    }
}
