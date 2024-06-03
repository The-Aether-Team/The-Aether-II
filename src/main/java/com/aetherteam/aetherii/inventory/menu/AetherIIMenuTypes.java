package com.aetherteam.aetherii.inventory.menu;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, AetherII.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<HolystoneFurnaceMenu>> HOLYSTONE_FURNACE = register("holystone_furnace", HolystoneFurnaceMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<MasonryBenchMenu>> MASONRY_BENCH = register("masonry_bench", MasonryBenchMenu::new);

    private static<T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> register(String name, MenuType.MenuSupplier<T> menu) {
        return MENU_TYPES.register(name, () -> new MenuType<>(menu, FeatureFlags.VANILLA_SET));
    }
}