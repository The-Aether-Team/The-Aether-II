package com.aetherteam.aetherii.recipe.display.slot;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIISlotDisplays {
    public static final DeferredRegister<SlotDisplay.Type<?>> SLOT_DISPLAYS = DeferredRegister.create(BuiltInRegistries.SLOT_DISPLAY, AetherII.MODID);

    public static final DeferredHolder<SlotDisplay.Type<?>, SlotDisplay.Type<AmberFuel>> AMBER_FUEL = SLOT_DISPLAYS.register("amber_fuel", () -> AmberFuel.TYPE);
}
