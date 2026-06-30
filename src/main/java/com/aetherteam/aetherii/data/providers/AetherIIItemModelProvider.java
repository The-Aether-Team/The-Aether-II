package com.aetherteam.aetherii.data.providers;

import com.aetherteam.nitrogen.data.providers.NitrogenItemModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class AetherIIItemModelProvider extends NitrogenItemModelProvider {
    public AetherIIItemModelProvider(PackOutput output, String id, ExistingFileHelper helper) {
        super(output, id, helper);
    }

    @Override
    protected ResourceLocation texture(String name, String location) {
        return this.texture(name);
    }

    protected ResourceLocation itemTexture(Item item) {
        return this.itemTexture(this.itemName(item));
    }

    protected ResourceLocation itemTexture(Item item, String suffix) {
        return this.itemTexture(this.itemName(item) + suffix);
    }

    protected ResourceLocation itemTexture(String name) {
        return this.modLoc("item/" + name);
    }

    @Override
    public void item(Item item, String location) {
        this.withExistingParent(this.itemName(item), this.mcLoc("item/generated"))
                .texture("layer0", this.itemTexture(item));
    }

    @Override
    public void handheldItem(Item item, String location) {
        this.withExistingParent(this.itemName(item), this.mcLoc("item/handheld"))
                .texture("layer0", this.itemTexture(item));
    }

    @Override
    public void bowItem(Item item, String location) {
        this.withExistingParent(this.itemName(item) + "_pulling_0", this.mcLoc("item/bow")).texture("layer0", this.itemTexture(item, "_pulling_0"));
        this.withExistingParent(this.itemName(item) + "_pulling_1", this.mcLoc("item/bow")).texture("layer0", this.itemTexture(item, "_pulling_1"));
        this.withExistingParent(this.itemName(item) + "_pulling_2", this.mcLoc("item/bow")).texture("layer0", this.itemTexture(item, "_pulling_2"));
        this.withExistingParent(this.itemName(item), this.mcLoc("item/bow"))
                .texture("layer0", this.itemTexture(item))
                .override().predicate(new ResourceLocation("pulling"), 1).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_pulling_0"))).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.65F).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_pulling_1"))).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.9F).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_pulling_2"))).end();
    }

    @Override
    public void armorItem(Item item, String location, String type) {
        ItemModelBuilder builder = this.withExistingParent(this.itemName(item), this.mcLoc("item/generated"))
                .texture("layer0", this.itemTexture(item));
        if (this.itemName(item).startsWith("beast_pelt_") || this.itemName(item).startsWith("burrukai_plate_")) {
            builder.texture("layer1", this.itemTexture(item, "_dyed"));
        }
    }

    @Override
    public void dyedItem(Item item, String location) {
        this.withExistingParent(this.itemName(item), this.mcLoc("item/generated"))
                .texture("layer0", this.itemTexture(item))
                .texture("layer1", this.itemTexture(item, "_dyed"));
    }

    public void crossbowItem(Item item, String location) {
        ModelBuilder<ItemModelBuilder>.TransformsBuilder crossbow = this.withExistingParent(this.itemName(item), this.mcLoc("item/crossbow"))
                .texture("layer0", this.itemTexture(item))
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(-90.0F, 0.0F, -60.0F).translation(2.0F, 0.1F, -3.0F).scale(0.9F, 0.9F, 0.9F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(-90.0F, 0.0F, 30.0F).translation(2.0F, 0.1F, -3.0F).scale(0.9F, 0.9F, 0.9F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(-90.0F, 0.0F, -55.0F).translation(1.13F, 3.2F, 1.13F).scale(0.68F, 0.68F, 0.68F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(-90.0F, 0.0F, 35.0F).translation(1.13F, 3.2F, 1.13F).scale(0.68F, 0.68F, 0.68F).end();
        this.withExistingParent(this.itemName(item) + "_pulling_0", this.mcLoc("item/crossbow")).texture("layer0", this.itemTexture(item, "_pulling_0"));
        this.withExistingParent(this.itemName(item) + "_pulling_1", this.mcLoc("item/crossbow")).texture("layer0", this.itemTexture(item, "_pulling_1"));
        this.withExistingParent(this.itemName(item) + "_pulling_2", this.mcLoc("item/crossbow")).texture("layer0", this.itemTexture(item, "_pulling_2"));
        this.withExistingParent(this.itemName(item) + "_bolt", this.mcLoc("item/crossbow")).texture("layer0", this.itemTexture(item, "_bolt"));
        crossbow.end()
                .override().predicate(new ResourceLocation("pulling"), 1).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_pulling_0"))).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.58F).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_pulling_1"))).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 1.0F).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_pulling_2"))).end()
                .override().predicate(new ResourceLocation("charged"), 1).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_bolt"))).end();
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
                .texture("layer0", this.itemTexture(item))
                .texture("layer1", this.itemTexture(item, "_inside"));
    }

    public void aercloudItem(Block block) {
        this.withExistingParent(this.blockName(block), this.mcLoc("block/cube_all")).texture("all", this.texture(this.blockName(block), "natural/")).renderType(new ResourceLocation("translucent"));
    }

    public void builtinEntityBlockItem(Block block, ResourceLocation parent, Block particle) {
        this.withExistingParent(this.blockName(block), parent)
                .texture("particle", this.modLoc("block/" + this.blockName(particle)));
    }
}
