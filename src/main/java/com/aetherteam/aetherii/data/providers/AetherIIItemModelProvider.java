package com.aetherteam.aetherii.data.providers;

import com.aetherteam.nitrogen.data.providers.NitrogenItemModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class AetherIIItemModelProvider extends NitrogenItemModelProvider {
    public AetherIIItemModelProvider(PackOutput output, String id, ExistingFileHelper helper) {
        super(output, id, helper);
    }

    public void crossbowItem(Item item, String location) {
        ModelBuilder<ItemModelBuilder>.TransformsBuilder crossbow = this.withExistingParent(this.itemName(item), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("item/" + location + this.itemName(item) + "_standby"))
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(-90.0F, 0.0F, -60.0F).translation(2.0F, 0.1F, -3.0F).scale(0.9F, 0.9F, 0.9F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(-90.0F, 0.0F, 30.0F).translation(2.0F, 0.1F, -3.0F).scale(0.9F, 0.9F, 0.9F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(-90.0F, 0.0F, -55.0F).translation(1.13F, 3.2F, 1.13F).scale(0.68F, 0.68F, 0.68F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(-90.0F, 0.0F, 35.0F).translation(1.13F, 3.2F, 1.13F).scale(0.68F, 0.68F, 0.68F).end();
        this.withExistingParent(this.itemName(item) + "_pulling_0", this.modLoc("item/" + this.itemName(item))).texture("layer0", this.modLoc("item/" + location + this.itemName(item) + "_pulling_0"));
        this.withExistingParent(this.itemName(item) + "_pulling_1", this.modLoc("item/" + this.itemName(item))).texture("layer0", this.modLoc("item/" + location + this.itemName(item) + "_pulling_1"));
        this.withExistingParent(this.itemName(item) + "_pulling_2", this.modLoc("item/" + this.itemName(item))).texture("layer0", this.modLoc("item/" + location + this.itemName(item) + "_pulling_2"));
        this.withExistingParent(this.itemName(item) + "_bolt", this.modLoc("item/" + this.itemName(item))).texture("layer0", this.modLoc("item/" + location + this.itemName(item) + "_bolt"));
        crossbow.end()
                .override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_pulling_0"))).end()
                .override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).predicate(ResourceLocation.withDefaultNamespace("pull"), 0.58F).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_pulling_1"))).end()
                .override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).predicate(ResourceLocation.withDefaultNamespace("pull"), 1.0F).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_pulling_2"))).end()
                .override().predicate(ResourceLocation.withDefaultNamespace("charged"), 1).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_bolt"))).end();
    }

    public void itemBlockGrass(Block block, String location) {
        this.withExistingParent(this.blockName(block), this.mcLoc("item/generated"))
                .texture("layer0", this.texture(this.blockName(block) + "_1", location))
                .texture("layer1", this.texture(this.blockName(block) + "_2", location))
                .texture("layer2", this.texture(this.blockName(block) + "_3", location));
    }

    public void orangeTree(Block block) {
        this.withExistingParent(this.blockName(block), this.mcLoc("item/generated")).texture("layer0", this.texture(this.blockName(block) + "_bottom_0", "natural/"));
    }

    public void portalItem(Item item, String location) {
        this.withExistingParent(this.itemName(item), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("item/" + location + this.itemName(item)))
                .texture("layer1", this.modLoc("item/" + location + this.itemName(item) + "_inside"));
    }

    public void aercloudItem(Block block) {
        this.withExistingParent(this.blockName(block), this.mcLoc("block/cube_all")).texture("all", this.texture(this.blockName(block), "natural/")).renderType(ResourceLocation.withDefaultNamespace("translucent"));
    }

    public void outpostCampfireItem(Block block) {
        this.withExistingParent(this.blockName(block), this.mcLoc("item/chest"))
                .transforms()
                .transform(ItemDisplayContext.GUI).rotation(30.0F, 45.0F, 0.0F).translation(-4.95F, 1.5F, 0.0F).scale(0.425F, 0.425F, 0.425F).end()
                .transform(ItemDisplayContext.GROUND).rotation(0.0F, 0.0F, 0.0F).translation(-2.0F, 3.0F, -2.0F).scale(0.25F, 0.25F, 0.25F).end()
                .transform(ItemDisplayContext.HEAD).rotation(0.0F, 180.0F, 0.0F).translation(8.0F, 14.5F, 8.0F).scale(1.0F, 1.0F, 1.0F).end()
                .transform(ItemDisplayContext.FIXED).rotation(0.0F, 180.0F, 0.0F).translation(4.0F, 1.0F, 1.0F).scale(0.5F, 0.5F, 0.5F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(75.0F, 315.0F, 0.0F).translation(0.0F, 6.25F, 0.75F).scale(0.225F, 0.225F, 0.225F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0.0F, 315.0F, 0.0F).translation(2.0F, 2.5F, -5.0F).scale(0.4F, 0.4F, 0.4F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(75.0F, -315.0F, 0.0F).translation(0.0F, 6.25F, 0.75F).scale(0.225F, 0.225F, 0.225F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(0.0F, -315.0F, 0.0F).translation(2.0F, 2.5F, -5.0F).scale(0.4F, 0.4F, 0.4F).end()
                .end();
    }
}