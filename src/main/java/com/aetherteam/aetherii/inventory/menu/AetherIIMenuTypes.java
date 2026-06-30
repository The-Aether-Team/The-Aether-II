package com.aetherteam.aetherii.inventory.menu;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookEquipmentScreen;
import com.aetherteam.aetherii.client.gui.screen.inventory.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AetherIIMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, AetherII.MODID);

    public static final RegistryObject<MenuType<GuidebookEquipmentMenu>> GUIDEBOOK = MENU_TYPES.register("guidebook", () -> IForgeMenuType.create(GuidebookEquipmentMenu::new));
    public static final RegistryObject<MenuType<SkyrootCraftingMenu>> SKYROOT_CRAFTING_TABLE = register("skyroot_crafting_table", SkyrootCraftingMenu::new);
    public static final RegistryObject<MenuType<HolystoneFurnaceMenu>> HOLYSTONE_FURNACE = register("holystone_furnace", HolystoneFurnaceMenu::new);
    public static final RegistryObject<MenuType<HolystoneSmokerMenu>> HOLYSTONE_SMOKER = register("holystone_smoker", HolystoneSmokerMenu::new);
    public static final RegistryObject<MenuType<ArtisansBenchMenu>> ARTISANS_BENCH = register("artisans_bench", ArtisansBenchMenu::new);
    public static final RegistryObject<MenuType<AmberHourglassMenu>> AMBER_HOURGLASS = register("amber_hourglass", AmberHourglassMenu::new);
    public static final RegistryObject<MenuType<AltarMenu>> ALTAR = register("altar", AltarMenu::new);
    public static final RegistryObject<MenuType<ArkeniumForgeMenu>> ARKENIUM_FORGE = register("arkenium_forge", ArkeniumForgeMenu::new);
    public static final RegistryObject<MenuType<AlkahestPurifierMenu>> ALKAHEST_PURIFIER = register("alkahest_purifier", AlkahestPurifierMenu::new);

    private static<T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String name, MenuType.MenuSupplier<T> menu) {
        return MENU_TYPES.register(name, () -> new MenuType<>(menu, FeatureFlags.VANILLA_SET));
    }

    @SuppressWarnings("deprecation")
    public static void registerMenuScreens() {
        MenuScreens.register(AetherIIMenuTypes.GUIDEBOOK.get(), GuidebookEquipmentScreen::new);
        MenuScreens.register(AetherIIMenuTypes.SKYROOT_CRAFTING_TABLE.get(), SkyrootCraftingScreen::new);
        MenuScreens.register(AetherIIMenuTypes.HOLYSTONE_FURNACE.get(), HolystoneFurnaceScreen::new);
        MenuScreens.register(AetherIIMenuTypes.HOLYSTONE_SMOKER.get(), HolystoneSmokerScreen::new);
        MenuScreens.register(AetherIIMenuTypes.AMBER_HOURGLASS.get(), AmberHourglassScreen::new);
        MenuScreens.register(AetherIIMenuTypes.ALTAR.get(), AltarScreen::new);
        MenuScreens.register(AetherIIMenuTypes.ARTISANS_BENCH.get(), ArtisansBenchScreen::new);
        MenuScreens.register(AetherIIMenuTypes.ARKENIUM_FORGE.get(), ArkeniumForgeScreen::new);
        MenuScreens.register(AetherIIMenuTypes.ALKAHEST_PURIFIER.get(), AlkahestPurifierScreen::new);
    }
}
