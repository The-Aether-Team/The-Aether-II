package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.providers.AetherIIRecipeProvider;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;

public class AetherIIRecipeData extends AetherIIRecipeProvider {
    public AetherIIRecipeData(PackOutput output) {
        super(output, AetherII.MODID);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        woodFromLogs(consumer, AetherIIBlocks.SKYROOT_WOOD.get(), AetherIIBlocks.SKYROOT_LOG.get());
        woodFromLogs(consumer, AetherIIBlocks.GREATROOT_WOOD.get(), AetherIIBlocks.GREATROOT_LOG.get());
        woodFromLogs(consumer, AetherIIBlocks.WISPROOT_WOOD.get(), AetherIIBlocks.WISPROOT_LOG.get());
        woodFromLogs(consumer, AetherIIBlocks.AMBEROOT_WOOD.get(), AetherIIBlocks.AMBEROOT_LOG.get());
        planksFromLog(consumer, AetherIIBlocks.SKYROOT_PLANKS.get(), AetherIITags.Items.CRAFTS_SKYROOT_PLANKS, 4);
        planksFromLog(consumer, AetherIIBlocks.GREATROOT_PLANKS.get(), AetherIITags.Items.CRAFTS_GREATROOT_PLANKS, 4);
        planksFromLog(consumer, AetherIIBlocks.WISPROOT_PLANKS.get(), AetherIITags.Items.CRAFTS_WISPROOT_PLANKS, 4);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AetherIIItems.SKYROOT_STICK.get(), 4)
                .group("sticks")
                .define('#', AetherIITags.Items.CRAFTS_SKYROOT_STICKS)
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_planks", has(AetherIITags.Items.CRAFTS_SKYROOT_STICKS))
                .save(consumer);

        this.smeltingOreRecipe(AetherIIItems.AMBROSIUM_SHARD.get(), AetherIIBlocks.AMBROSIUM_ORE.get(), 0.1F).save(consumer, this.name("ambrosium_shard_from_smelting"));
        this.blastingOreRecipe(AetherIIItems.AMBROSIUM_SHARD.get(), AetherIIBlocks.AMBROSIUM_ORE.get(), 0.1F).save(consumer, this.name("ambrosium_shard_from_blasting"));

        this.smeltingOreRecipe(AetherIIItems.ZANITE_GEMSTONE.get(), AetherIIBlocks.ZANITE_ORE.get(), 0.7F).save(consumer, this.name("zanite_gemstone_from_smelting"));
        this.blastingOreRecipe(AetherIIItems.ZANITE_GEMSTONE.get(), AetherIIBlocks.ZANITE_ORE.get(), 0.7F).save(consumer, this.name("zanite_gemstone_from_blasting"));

        this.smeltingOreRecipe(AetherIIItems.ARKENIUM_PLATE.get(), AetherIIBlocks.ARKENIUM_ORE.get(), 0.7F).save(consumer, this.name("arkenium_plate_from_smelting"));
        this.blastingOreRecipe(AetherIIItems.ARKENIUM_PLATE.get(), AetherIIBlocks.ARKENIUM_ORE.get(), 0.7F).save(consumer, this.name("arkenium_plate_from_blasting"));
        this.smeltingOreRecipe(AetherIIItems.ARKENIUM_PLATE.get(), AetherIIItems.RAW_ARKENIUM.get(), 0.7F).save(consumer, this.name("arkenium_plate_from_smelting_raw"));
        this.blastingOreRecipe(AetherIIItems.ARKENIUM_PLATE.get(), AetherIIItems.RAW_ARKENIUM.get(), 0.7F).save(consumer, this.name("arkenium_plate_from_blasting_raw"));

        this.smeltingOreRecipe(AetherIIItems.GRAVITITE_PLATE.get(), AetherIIBlocks.GRAVITITE_ORE.get(), 0.7F).save(consumer, this.name("gravitite_plate_from_smelting"));
        this.blastingOreRecipe(AetherIIItems.GRAVITITE_PLATE.get(), AetherIIBlocks.GRAVITITE_ORE.get(), 0.7F).save(consumer, this.name("gravitite_plate_from_blasting"));
        this.smeltingOreRecipe(AetherIIItems.GRAVITITE_PLATE.get(), AetherIIItems.RAW_GRAVITITE.get(), 0.7F).save(consumer, this.name("gravitite_plate_from_smelting_raw"));
        this.blastingOreRecipe(AetherIIItems.GRAVITITE_PLATE.get(), AetherIIItems.RAW_GRAVITITE.get(), 0.7F).save(consumer, this.name("gravitite_plate_from_blasting_raw"));
    }
}
