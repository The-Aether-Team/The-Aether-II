package com.aetherteam.aetherii.integration.jei;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.AetherIIClientCaches;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookEquipmentScreen;
import com.aetherteam.aetherii.client.gui.screen.inventory.*;
import com.aetherteam.aetherii.integration.jei.categories.block.*;
import com.aetherteam.aetherii.integration.jei.categories.item.AlkahestPurifierRecipeCategory;
import com.aetherteam.aetherii.integration.jei.categories.item.AltarRecipeCategory;
import com.aetherteam.aetherii.integration.jei.categories.item.AmberHourglassRecipeCategory;
import com.aetherteam.aetherii.integration.jei.interpreter.AmberDartInterpreter;
import com.aetherteam.aetherii.integration.jei.interpreter.HealingStoneInterpreter;
import com.aetherteam.aetherii.inventory.menu.*;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.handlers.IGuiProperties;
import mezz.jei.api.gui.handlers.IScreenHandler;
import mezz.jei.api.registration.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

@JeiPlugin
public class AetherIIJEIPlugin implements IModPlugin {
    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(AetherII.MODID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new AlkahestPurifierRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new AltarRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new AmberHourglassRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new AlkahestCorrosionRecipeCategory(registration.getJeiHelpers().getGuiHelper(), registration.getJeiHelpers().getPlatformFluidHelper()));
        registration.addRecipeCategories(new AmbrosiumConversionRecipeCategory(registration.getJeiHelpers().getGuiHelper(), registration.getJeiHelpers().getPlatformFluidHelper()));
        registration.addRecipeCategories(new DustIrradiationRecipeCategory(registration.getJeiHelpers().getGuiHelper(), registration.getJeiHelpers().getPlatformFluidHelper()));
        registration.addRecipeCategories(new IcestoneFreezingRecipeCategory(registration.getJeiHelpers().getGuiHelper(), registration.getJeiHelpers().getPlatformFluidHelper()));
        registration.addRecipeCategories(new SwetGelConversionRecipeCategory(registration.getJeiHelpers().getGuiHelper(), registration.getJeiHelpers().getPlatformFluidHelper()));

    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(AlkahestPurifierRecipeCategory.ALKAHEST_PURIFICATION, AetherIIBlocks.ALKAHEST_PURIFIER);
        registration.addCraftingStation(AltarRecipeCategory.ALTAR_ENCHANTING, AetherIIBlocks.ALTAR);
        registration.addCraftingStation(AmberHourglassRecipeCategory.HOURGLASS_RESTORING, AetherIIBlocks.AMBER_HOURGLASS);

        registration.addCraftingStation(AlkahestCorrosionRecipeCategory.ALKAHEST_CORROSION, AetherIIItems.ARKENIUM_ALKAHEST_CANISTER);
        registration.addCraftingStation(AmbrosiumConversionRecipeCategory.AMBROSIUM_CONVERSION, AetherIIItems.AMBROSIUM_SHARD);
        registration.addCraftingStation(DustIrradiationRecipeCategory.DUST_IRRADIATION, AetherIIItems.IRRADIATED_DUST);
        registration.addCraftingStation(IcestoneFreezingRecipeCategory.ICESTONE_FREEZABLE, AetherIIBlocks.ICESTONE, AetherIIBlocks.ICESTONE_STAIRS, AetherIIBlocks.ICESTONE_SLAB, AetherIIBlocks.ICESTONE_WALL);
        registration.addCraftingStation(SwetGelConversionRecipeCategory.SWET_GEL_CONVERSION, AetherIIItems.SWET_GEL);

        registration.addCraftingStation(RecipeTypes.CRAFTING, AetherIIBlocks.SKYROOT_CRAFTING_TABLE);
        registration.addCraftingStation(RecipeTypes.SMELTING, AetherIIBlocks.HOLYSTONE_FURNACE);
        registration.addCraftingStation(RecipeTypes.SMELTING_FUEL, AetherIIBlocks.HOLYSTONE_FURNACE);
        registration.addCraftingStation(RecipeTypes.STONECUTTING, AetherIIBlocks.ARTISANS_BENCH);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(AlkahestPurifierRecipeCategory.ALKAHEST_PURIFICATION, AetherIIClientCaches.CLIENT_CACHES.byType(AetherIIRecipeTypes.ALKAHEST_PURIFICATION.get()).stream().map(RecipeHolder::value).toList());
        registration.addRecipes(AltarRecipeCategory.ALTAR_ENCHANTING, AetherIIClientCaches.CLIENT_CACHES.byType(AetherIIRecipeTypes.ALTAR_ENCHANTING.get()).stream().map(RecipeHolder::value).toList());
        registration.addRecipes(AmberHourglassRecipeCategory.HOURGLASS_RESTORING, AetherIIClientCaches.CLIENT_CACHES.byType(AetherIIRecipeTypes.HOURGLASS_RESTORING.get()).stream().map(RecipeHolder::value).toList());

        registration.addRecipes(AlkahestCorrosionRecipeCategory.ALKAHEST_CORROSION, AetherIIClientCaches.CLIENT_CACHES.byType(AetherIIRecipeTypes.ALKAHEST_CORROSION.get()).stream().map(RecipeHolder::value).toList());
        registration.addRecipes(AmbrosiumConversionRecipeCategory.AMBROSIUM_CONVERSION, AetherIIClientCaches.CLIENT_CACHES.byType(AetherIIRecipeTypes.AMBROSIUM_ENCHANTING.get()).stream().map(RecipeHolder::value).toList());
        registration.addRecipes(DustIrradiationRecipeCategory.DUST_IRRADIATION, AetherIIClientCaches.CLIENT_CACHES.byType(AetherIIRecipeTypes.DUST_IRRADIATION.get()).stream().map(RecipeHolder::value).toList());
        registration.addRecipes(IcestoneFreezingRecipeCategory.ICESTONE_FREEZABLE, AetherIIClientCaches.CLIENT_CACHES.byType(AetherIIRecipeTypes.ICESTONE_FREEZABLE.get()).stream().map(RecipeHolder::value).toList());
        registration.addRecipes(SwetGelConversionRecipeCategory.SWET_GEL_CONVERSION, AetherIIClientCaches.CLIENT_CACHES.byType(AetherIIRecipeTypes.SWET_GEL_CONVERSION.get()).stream().map(RecipeHolder::value).toList());
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(SkyrootCraftingScreen.class, 88, 32, 28, 23, RecipeTypes.CRAFTING);
        registration.addRecipeClickArea(HolystoneFurnaceScreen.class, 78, 32, 28, 23, RecipeTypes.SMELTING, RecipeTypes.SMELTING_FUEL);
        registration.addRecipeClickArea(HolystoneSmokerScreen.class, 78, 32, 28, 23, RecipeTypes.SMOKING, RecipeTypes.SMOKING_FUEL);
        registration.addRecipeClickArea(AlkahestPurifierScreen.class, 119, 37, 18, 20, AlkahestPurifierRecipeCategory.ALKAHEST_PURIFICATION);
        registration.addRecipeClickArea(AltarScreen.class, 107, 58, 26, 16, AltarRecipeCategory.ALTAR_ENCHANTING);
        registration.addRecipeClickArea(AmberHourglassScreen.class, 41, 23, 28, 47, AmberHourglassRecipeCategory.HOURGLASS_RESTORING);
        registration.addRecipeClickArea(AmberHourglassScreen.class, 107, 23, 28, 47, AmberHourglassRecipeCategory.HOURGLASS_RESTORING);

        registration.addGuiScreenHandler(GuidebookEquipmentScreen.class, new IScreenHandler<>() {
            @Override
            public @Nullable IGuiProperties apply(GuidebookEquipmentScreen guiScreen) {
                return null;
            }
        });
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(SkyrootCraftingMenu.class, AetherIIMenuTypes.SKYROOT_CRAFTING_TABLE.get(), RecipeTypes.CRAFTING, 1, 9, 10, 36);
        registration.addRecipeTransferHandler(HolystoneFurnaceMenu.class, AetherIIMenuTypes.HOLYSTONE_FURNACE.get(), RecipeTypes.SMELTING, 0, 1, 3, 36);
        registration.addRecipeTransferHandler(HolystoneFurnaceMenu.class, AetherIIMenuTypes.HOLYSTONE_FURNACE.get(), RecipeTypes.SMELTING_FUEL, 1, 1, 3, 36);
        registration.addRecipeTransferHandler(HolystoneSmokerMenu.class, AetherIIMenuTypes.HOLYSTONE_SMOKER.get(), RecipeTypes.SMOKING, 0, 1, 3, 36);
        registration.addRecipeTransferHandler(HolystoneSmokerMenu.class, AetherIIMenuTypes.HOLYSTONE_SMOKER.get(), RecipeTypes.SMOKING_FUEL, 1, 1, 3, 36);

        registration.addRecipeTransferHandler(AlkahestPurifierMenu.class, AetherIIMenuTypes.ALKAHEST_PURIFIER.get(), AlkahestPurifierRecipeCategory.ALKAHEST_PURIFICATION, 0, 1, 7, 36);
        registration.addRecipeTransferHandler(AltarMenu.class, AetherIIMenuTypes.ALTAR.get(), AltarRecipeCategory.ALTAR_ENCHANTING, 0, 9, 10, 36);
        registration.addRecipeTransferHandler(AmberHourglassMenu.class, AetherIIMenuTypes.AMBER_HOURGLASS.get(), AmberHourglassRecipeCategory.HOURGLASS_RESTORING, 0, 2, 5, 36);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(AetherIIItems.AMBER_DARTS.get(), new AmberDartInterpreter());
        registration.registerSubtypeInterpreter(AetherIIItems.HEALING_STONE.get(), new HealingStoneInterpreter());
    }
}
