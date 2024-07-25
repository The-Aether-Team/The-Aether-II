package com.aetherteam.aetherii.inventory.menu;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookEquipmentScreen;
import com.aetherteam.aetherii.client.gui.screen.inventory.AltarScreen;
import com.aetherteam.aetherii.client.gui.screen.inventory.ArkeniumForgeScreen;
import com.aetherteam.aetherii.client.gui.screen.inventory.ArtisansBenchScreen;
import com.aetherteam.aetherii.client.gui.screen.inventory.HolystoneFurnaceScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, AetherII.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<GuidebookEquipmentMenu>> GUIDEBOOK = register("guidebook", GuidebookEquipmentMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<HolystoneFurnaceMenu>> HOLYSTONE_FURNACE = register("holystone_furnace", HolystoneFurnaceMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<ArtisansBenchMenu>> ARTISANS_BENCH = register("artisans_bench", ArtisansBenchMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<AltarMenu>> ALTAR = register("altar", AltarMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<ArkeniumForgeMenu>> ARKENIUM_FORGE = register("arkenium_forge", ArkeniumForgeMenu::new);

    private static<T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> register(String name, MenuType.MenuSupplier<T> menu) {
        return MENU_TYPES.register(name, () -> new MenuType<>(menu, FeatureFlags.VANILLA_SET));
    }

    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(AetherIIMenuTypes.GUIDEBOOK.get(), GuidebookEquipmentScreen::new);
        event.register(AetherIIMenuTypes.HOLYSTONE_FURNACE.get(), HolystoneFurnaceScreen::new);
        event.register(AetherIIMenuTypes.ALTAR.get(), AltarScreen::new);
        event.register(AetherIIMenuTypes.ARTISANS_BENCH.get(), ArtisansBenchScreen::new);
        event.register(AetherIIMenuTypes.ARKENIUM_FORGE.get(), ArkeniumForgeScreen::new);
    }
}