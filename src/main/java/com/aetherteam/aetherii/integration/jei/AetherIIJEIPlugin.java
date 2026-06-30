package com.aetherteam.aetherii.integration.jei;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookEquipmentScreen;
import com.aetherteam.aetherii.client.gui.screen.inventory.AlkahestPurifierScreen;
import com.aetherteam.aetherii.client.gui.screen.inventory.AltarScreen;
import com.aetherteam.aetherii.client.gui.screen.inventory.AmberHourglassScreen;
import com.aetherteam.aetherii.client.gui.screen.inventory.HolystoneFurnaceScreen;
import com.aetherteam.aetherii.client.gui.screen.inventory.HolystoneSmokerScreen;
import com.aetherteam.aetherii.client.gui.screen.inventory.SkyrootCraftingScreen;
import com.aetherteam.aetherii.integration.jei.categories.block.AlkahestCorrosionRecipeCategory;
import com.aetherteam.aetherii.integration.jei.categories.block.AmbrosiumConversionRecipeCategory;
import com.aetherteam.aetherii.integration.jei.categories.block.DustIrradiationRecipeCategory;
import com.aetherteam.aetherii.integration.jei.categories.block.IcestoneFreezingRecipeCategory;
import com.aetherteam.aetherii.integration.jei.categories.block.SwetGelConversionRecipeCategory;
import com.aetherteam.aetherii.integration.jei.categories.item.AlkahestPurifierRecipeCategory;
import com.aetherteam.aetherii.integration.jei.categories.item.AltarRecipeCategory;
import com.aetherteam.aetherii.integration.jei.categories.item.AmberHourglassRecipeCategory;
import com.aetherteam.aetherii.integration.jei.interpreter.AmberDartInterpreter;
import com.aetherteam.aetherii.integration.jei.interpreter.HealingStoneInterpreter;
import com.aetherteam.aetherii.inventory.menu.AetherIIMenuTypes;
import com.aetherteam.aetherii.inventory.menu.AlkahestPurifierMenu;
import com.aetherteam.aetherii.inventory.menu.AltarMenu;
import com.aetherteam.aetherii.inventory.menu.AmberHourglassMenu;
import com.aetherteam.aetherii.inventory.menu.HolystoneFurnaceMenu;
import com.aetherteam.aetherii.inventory.menu.HolystoneSmokerMenu;
import com.aetherteam.aetherii.inventory.menu.SkyrootCraftingMenu;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.handlers.IGuiProperties;
import mezz.jei.api.gui.handlers.IScreenHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@JeiPlugin
public class AetherIIJEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(AetherII.MODID, "jei");
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
        registration.addRecipeCatalysts(AlkahestPurifierRecipeCategory.ALKAHEST_PURIFICATION, AetherIIBlocks.ALKAHEST_PURIFIER.get());
        registration.addRecipeCatalysts(AltarRecipeCategory.ALTAR_ENCHANTING, AetherIIBlocks.ALTAR.get());
        registration.addRecipeCatalysts(AmberHourglassRecipeCategory.HOURGLASS_RESTORING, AetherIIBlocks.AMBER_HOURGLASS.get());

        registration.addRecipeCatalysts(AlkahestCorrosionRecipeCategory.ALKAHEST_CORROSION, AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get());
        registration.addRecipeCatalysts(AmbrosiumConversionRecipeCategory.AMBROSIUM_CONVERSION, AetherIIItems.AMBROSIUM_SHARD.get());
        registration.addRecipeCatalysts(DustIrradiationRecipeCategory.DUST_IRRADIATION, AetherIIItems.IRRADIATED_DUST.get());
        registration.addRecipeCatalysts(IcestoneFreezingRecipeCategory.ICESTONE_FREEZABLE, AetherIIBlocks.ICESTONE.get(), AetherIIBlocks.ICESTONE_STAIRS.get(), AetherIIBlocks.ICESTONE_SLAB.get(), AetherIIBlocks.ICESTONE_WALL.get());
        registration.addRecipeCatalysts(SwetGelConversionRecipeCategory.SWET_GEL_CONVERSION, AetherIIItems.SWET_GEL.get());

        registration.addRecipeCatalysts(RecipeTypes.CRAFTING, AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get());
        registration.addRecipeCatalysts(RecipeTypes.SMELTING, AetherIIBlocks.HOLYSTONE_FURNACE.get());
        registration.addRecipeCatalysts(RecipeTypes.FUELING, AetherIIBlocks.HOLYSTONE_FURNACE.get(), AetherIIBlocks.HOLYSTONE_SMOKER.get());
        registration.addRecipeCatalysts(RecipeTypes.SMOKING, AetherIIBlocks.HOLYSTONE_SMOKER.get());
        registration.addRecipeCatalysts(RecipeTypes.STONECUTTING, AetherIIBlocks.ARTISANS_BENCH.get());
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(AlkahestPurifierRecipeCategory.ALKAHEST_PURIFICATION, recipes(AetherIIRecipeTypes.ALKAHEST_PURIFICATION.get()));
        registration.addRecipes(AltarRecipeCategory.ALTAR_ENCHANTING, recipes(AetherIIRecipeTypes.ALTAR_ENCHANTING.get()));
        registration.addRecipes(AmberHourglassRecipeCategory.HOURGLASS_RESTORING, recipes(AetherIIRecipeTypes.HOURGLASS_RESTORING.get()));

        registration.addRecipes(AlkahestCorrosionRecipeCategory.ALKAHEST_CORROSION, recipes(AetherIIRecipeTypes.ALKAHEST_CORROSION.get()));
        registration.addRecipes(AmbrosiumConversionRecipeCategory.AMBROSIUM_CONVERSION, recipes(AetherIIRecipeTypes.AMBROSIUM_ENCHANTING.get()));
        registration.addRecipes(DustIrradiationRecipeCategory.DUST_IRRADIATION, recipes(AetherIIRecipeTypes.DUST_IRRADIATION.get()));
        registration.addRecipes(IcestoneFreezingRecipeCategory.ICESTONE_FREEZABLE, recipes(AetherIIRecipeTypes.ICESTONE_FREEZABLE.get()));
        registration.addRecipes(SwetGelConversionRecipeCategory.SWET_GEL_CONVERSION, recipes(AetherIIRecipeTypes.SWET_GEL_CONVERSION.get()));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(SkyrootCraftingScreen.class, 88, 32, 28, 23, RecipeTypes.CRAFTING);
        registration.addRecipeClickArea(HolystoneFurnaceScreen.class, 78, 32, 28, 23, RecipeTypes.SMELTING, RecipeTypes.FUELING);
        registration.addRecipeClickArea(HolystoneSmokerScreen.class, 78, 32, 28, 23, RecipeTypes.SMOKING, RecipeTypes.FUELING);
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
        registration.addRecipeTransferHandler(HolystoneSmokerMenu.class, AetherIIMenuTypes.HOLYSTONE_SMOKER.get(), RecipeTypes.SMOKING, 0, 1, 3, 36);

        registration.addRecipeTransferHandler(AlkahestPurifierMenu.class, AetherIIMenuTypes.ALKAHEST_PURIFIER.get(), AlkahestPurifierRecipeCategory.ALKAHEST_PURIFICATION, 0, 1, 7, 36);
        registration.addRecipeTransferHandler(AltarMenu.class, AetherIIMenuTypes.ALTAR.get(), AltarRecipeCategory.ALTAR_ENCHANTING, 0, 9, 10, 36);
        registration.addRecipeTransferHandler(AmberHourglassMenu.class, AetherIIMenuTypes.AMBER_HOURGLASS.get(), AmberHourglassRecipeCategory.HOURGLASS_RESTORING, 0, 2, 5, 36);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(AetherIIItems.AMBER_DARTS.get(), new AmberDartInterpreter());
        registration.registerSubtypeInterpreter(AetherIIItems.HEALING_STONE.get(), new HealingStoneInterpreter());
    }

    private static <C extends Container, T extends Recipe<C>> List<T> recipes(net.minecraft.world.item.crafting.RecipeType<T> recipeType) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientPacketListener connection = minecraft.getConnection();
        if (connection != null) {
            return connection.getRecipeManager().getAllRecipesFor(recipeType);
        }
        if (minecraft.level != null) {
            return minecraft.level.getRecipeManager().getAllRecipesFor(recipeType);
        }
        if (minecraft.getSingleplayerServer() != null) {
            return minecraft.getSingleplayerServer().getRecipeManager().getAllRecipesFor(recipeType);
        }
        if (AetherII.LOGGER.isDebugEnabled()) {
            AetherII.LOGGER.debug("Skipping JEI recipe registration for {} because no recipe manager is available yet", recipeType);
        }
        return List.of();
    }
}
