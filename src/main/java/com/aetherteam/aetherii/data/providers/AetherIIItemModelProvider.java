package com.aetherteam.aetherii.data.providers;

import com.aetherteam.aetherii.AetherII;
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

    public void reinforcedItem(Item item, String location) {
        ItemModelBuilder builder = this.withExistingParent(this.itemName(item), this.mcLoc("item/handheld")).texture("layer0", this.modLoc("item/" + location + this.itemName(item)));
        this.withExistingParent(this.itemName(item) + "_reinforced_1", this.mcLoc("item/generated")).texture("layer0", this.modLoc("item/" + location + this.itemName(item) + "_reinforced_1"));
        this.withExistingParent(this.itemName(item) + "_reinforced_2", this.mcLoc("item/generated")).texture("layer0", this.modLoc("item/" + location + this.itemName(item) + "_reinforced_2"));
        builder
                .override().predicate(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "reinforcement_tier"), 0.1F).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_reinforced_1"))).end()
                .override().predicate(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "reinforcement_tier"), 0.3F).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_reinforced_2"))).end();
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

    public void dyedHelmetItem(Item item, String location) {
        this.dyedStyledArmorItem(item, location, "helmet");
    }

    public void dyedChestplateItem(Item item, String location) {
        this.dyedStyledArmorItem(item, location, "chestplate");
    }

    public void dyedLeggingsItem(Item item, String location) {
        this.dyedStyledArmorItem(item, location, "leggings");
    }

    public void dyedBootsItem(Item item, String location) {
        this.dyedStyledArmorItem(item, location, "boots");
    }

    public void dyedGlovesItem(Item item, String location) {
        this.dyedStyledArmorItem(item, location, "gloves");
    }

    public void dyedStyledArmorItem(Item item, String location, String type) {
        this.withExistingParent(this.itemName(item), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("item/" + location + this.itemName(item)))
                .texture("layer1", this.modLoc("item/" + location + this.itemName(item) + "_dyed"));
    }

    public void helmetItem(Item item, String location) {
        this.styledArmorItem(item, location, "helmet");
    }

    public void chestplateItem(Item item, String location) {
        this.styledArmorItem(item, location, "chestplate");
    }

    public void leggingsItem(Item item, String location) {
        this.styledArmorItem(item, location, "leggings");
    }

    public void bootsItem(Item item, String location) {
        this.styledArmorItem(item, location, "boots");
    }

    public void styledArmorItem(Item item, String location, String type) {
        this.withExistingParent(this.itemName(item), this.mcLoc("item/generated")).texture("layer0", this.modLoc("item/" + location + this.itemName(item)));
    }

    public void healingStoneItem(Item item) {
        ItemModelBuilder builder = this.withExistingParent(this.itemName(item), this.mcLoc("item/handheld")).texture("layer0", this.modLoc("item/consumables/" + this.itemName(item) + "_0"));

        this.withExistingParent(this.itemName(item) + "_1", this.modLoc("item/" + this.itemName(item))).texture("layer0", this.modLoc("item/consumables/" + this.itemName(item) + "_1"));
        this.withExistingParent(this.itemName(item) + "_2", this.modLoc("item/" + this.itemName(item))).texture("layer0", this.modLoc("item/consumables/" + this.itemName(item) + "_2"));
        this.withExistingParent(this.itemName(item) + "_3", this.modLoc("item/" + this.itemName(item))).texture("layer0", this.modLoc("item/consumables/" + this.itemName(item) + "_3"));
        this.withExistingParent(this.itemName(item) + "_4", this.modLoc("item/" + this.itemName(item))).texture("layer0", this.modLoc("item/consumables/" + this.itemName(item) + "_4"));
        this.withExistingParent(this.itemName(item) + "_5", this.modLoc("item/" + this.itemName(item))).texture("layer0", this.modLoc("item/consumables/" + this.itemName(item) + "_5"));

        builder.override().predicate(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "charge"), 0.1F).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_1"))).end()
                .override().predicate(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "charge"), 0.2F).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_2"))).end()
                .override().predicate(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "charge"), 0.3F).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_3"))).end()
                .override().predicate(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "charge"), 0.4F).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_4"))).end()
                .override().predicate(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "charge"), 0.5F).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_5"))).end();
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

    public void valkyrieSprout(Block block) {
        this.withExistingParent(this.blockName(block), this.mcLoc("item/generated")).texture("layer0", this.texture(this.blockName(block) + "_0", "natural/"));
    }

    public void portalItem(Item item, String location) {
        this.withExistingParent(this.itemName(item), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("item/" + location + this.itemName(item)))
                .texture("layer1", this.modLoc("item/" + location + this.itemName(item) + "_inside"));
    }

    public void aercloudItem(Block block) {
        this.withExistingParent(this.blockName(block), this.mcLoc("block/cube_all")).texture("all", this.texture(this.blockName(block), "natural/")).renderType(ResourceLocation.withDefaultNamespace("translucent"));
    }

    public void blockWithItem(Block block, String location) {
        this.withExistingParent(this.blockName(block), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("item/" + location + this.itemName(block.asItem())));
    }
}