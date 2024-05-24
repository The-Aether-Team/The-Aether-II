package com.aetherteam.aetherii.data.providers;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.nitrogen.data.providers.NitrogenRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public abstract class AetherIIRecipeProvider extends NitrogenRecipeProvider {
    public AetherIIRecipeProvider(PackOutput output, String id) {
        super(output, id);
    }

    protected ShapedRecipeBuilder fence(Supplier<? extends Block> fence, Supplier<? extends Block> material) {
        return this.fence(fence, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT));
    }

    protected ShapedRecipeBuilder fenceGate(Supplier<? extends Block> fenceGate, Supplier<? extends Block> material) {
        return this.fenceGate(fenceGate, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT));
    }

    protected ShapedRecipeBuilder makePickaxeWithTag(Supplier<? extends Item> pickaxe, TagKey<Item> material, String has) {
        return this.makePickaxeWithTag(pickaxe, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT), has);
    }

    protected ShapedRecipeBuilder makePickaxeWithBlock(Supplier<? extends Item> pickaxe, Supplier<? extends Block> material) {
        return this.makePickaxeWithBlock(pickaxe, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT));
    }

    protected ShapedRecipeBuilder makeAxeWithTag(Supplier<? extends Item> axe, TagKey<Item> material, String has) {
        return this.makeAxeWithTag(axe, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT), has);
    }

    protected ShapedRecipeBuilder makeAxeWithBlock(Supplier<? extends Item> axe, Supplier<? extends Block> material) {
        return this.makeAxeWithBlock(axe, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT));
    }

    protected ShapedRecipeBuilder makeShovelWithTag(Supplier<? extends Item> shovel, TagKey<Item> material, String has) {
        return this.makeShovelWithTag(shovel, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT), has);
    }

    protected ShapedRecipeBuilder makeShovelWithBlock(Supplier<? extends Item> shovel, Supplier<? extends Block> material) {
        return this.makeShovelWithBlock(shovel, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT));
    }

    protected ShapedRecipeBuilder makeHoeWithTag(Supplier<? extends Item> hoe, TagKey<Item> material, String has) {
        return this.makeHoeWithTag(hoe, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT), has);
    }

    protected ShapedRecipeBuilder makeHoeWithBlock(Supplier<? extends Item> hoe, Supplier<? extends Block> material) {
        return this.makeHoeWithBlock(hoe, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT));
    }

    protected ShapedRecipeBuilder makeSwordWithTag(Supplier<? extends Item> sword, TagKey<Item> material, String has) {
        return this.makeSwordWithTag(sword, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT), has);
    }

    protected ShapedRecipeBuilder makeSwordWithBlock(Supplier<? extends Item> sword, Supplier<? extends Block> material) {
        return this.makeSwordWithBlock(sword, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT));
    }
}
