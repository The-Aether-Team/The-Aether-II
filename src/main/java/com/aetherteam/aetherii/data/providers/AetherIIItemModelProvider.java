package com.aetherteam.aetherii.data.providers;

import com.aetherteam.aetherii.client.renderer.item.properties.ReinforcementTierRange;
import com.aetherteam.aetherii.client.renderer.item.properties.SelectFeatherColor;
import com.aetherteam.aetherii.client.renderer.item.properties.SelectMoaEggType;
import com.aetherteam.aetherii.client.renderer.item.properties.TieredCrossbowPullRange;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.components.MoaEggType;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.select.Charge;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AetherIIItemModelProvider extends ModelProvider {
    public AetherIIItemModelProvider(PackOutput output, String modId) {
        super(output, modId);
    }

    public void generateReinforcedItem(ItemModelGenerators itemModels, Item item, ModelTemplate template) {
        ItemModel.Unbaked base = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item));
        ItemModel.Unbaked reinforced1 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_reinforced_1", template));
        ItemModel.Unbaked reinforced2 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_reinforced_2", template));
        itemModels.itemModelOutput.accept(item, ItemModelUtils.rangeSelect(new ReinforcementTierRange(), base, ItemModelUtils.override(reinforced1, 0.1F), ItemModelUtils.override(reinforced2, 0.3F)));
    }

    public void generateCrossbow(ItemModelGenerators itemModels, Item item) {
        ItemModel.Unbaked base = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item));
        ItemModel.Unbaked pulling0 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_pulling_0", ModelTemplates.CROSSBOW));
        ItemModel.Unbaked pulling1 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_pulling_1", ModelTemplates.CROSSBOW));
        ItemModel.Unbaked pulling2 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_pulling_2", ModelTemplates.CROSSBOW));
        ItemModel.Unbaked arrow = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_arrow", ModelTemplates.CROSSBOW));
        itemModels.itemModelOutput.accept(item, ItemModelUtils.conditional(ItemModelUtils.isUsingItem(),
                ItemModelUtils.rangeSelect(new TieredCrossbowPullRange(), pulling0, ItemModelUtils.override(pulling1, 0.58F), ItemModelUtils.override(pulling2, 1.0F)),
                ItemModelUtils.select(new Charge(), base, ItemModelUtils.when(CrossbowItem.ChargeType.ARROW, arrow))));
    }

    public void generateDyedArmorItem(ItemModelGenerators itemModels, Item item, int defaultColor) {
        ResourceLocation resourcelocation = itemModels.generateLayeredItem(item, TextureMapping.getItemTexture(item), TextureMapping.getItemTexture(item).withSuffix("_dyed"));
        itemModels.itemModelOutput.accept(item, ItemModelUtils.tintedModel(resourcelocation, new Dye(defaultColor)));
    }

    public void generateMoaFeatherItem(ItemModelGenerators itemModels, Item item) {
        ResourceLocation modelLocation = ModelLocationUtils.getModelLocation(item);
        ResourceLocation textureLocation = TextureMapping.getItemTexture(item);
        List<SelectItemModel.SwitchCase<Moa.FeatherColor>> list = new ArrayList<>(Moa.FeatherColor.values().length);
        for (Moa.FeatherColor featherColor : Moa.FeatherColor.values()) {
            ResourceLocation name = modelLocation.withSuffix("_" + featherColor.getSerializedName());
            ItemModel.Unbaked model = ItemModelUtils.plainModel(name);
            ModelTemplates.FLAT_ITEM.create(textureLocation, TextureMapping.layer0(textureLocation), itemModels.modelOutput);
            list.add(ItemModelUtils.when(featherColor, model));
        }
        itemModels.itemModelOutput.accept(item, ItemModelUtils.select(new SelectFeatherColor(), ItemModelUtils.plainModel(modelLocation), list));
    }

    public void generateHealingStoneItem(ItemModelGenerators itemModels, Item item) {
        ItemModel.Unbaked base = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item));
        ItemModel.Unbaked charged1 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_1", ModelTemplates.FLAT_HANDHELD_ITEM));
        ItemModel.Unbaked charged2 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_2", ModelTemplates.FLAT_HANDHELD_ITEM));
        ItemModel.Unbaked charged3 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_3", ModelTemplates.FLAT_HANDHELD_ITEM));
        ItemModel.Unbaked charged4 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_4", ModelTemplates.FLAT_HANDHELD_ITEM));
        ItemModel.Unbaked charged5 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_5", ModelTemplates.FLAT_HANDHELD_ITEM));
        itemModels.itemModelOutput.accept(item, ItemModelUtils.rangeSelect(new TieredCrossbowPullRange(), base,
                ItemModelUtils.override(charged1, 0.1F),
                ItemModelUtils.override(charged2, 0.2F),
                ItemModelUtils.override(charged3, 0.3F),
                ItemModelUtils.override(charged4, 0.4F),
                ItemModelUtils.override(charged5, 0.5F)
        ));
    }

    public void generateMoaEggItem(ItemModelGenerators itemModels, Item item) {
        ResourceLocation modelLocation = ModelLocationUtils.getModelLocation(item);
        ResourceLocation textureLocation = TextureMapping.getItemTexture(item);
        List<SelectItemModel.SwitchCase<MoaEggType>> list = new ArrayList<>(Moa.KeratinColor.values().length * Moa.EyeColor.values().length * Moa.FeatherColor.values().length * Moa.FeatherShape.values().length);
        for (Moa.KeratinColor keratinColor : Moa.KeratinColor.values()) {
            for (Moa.EyeColor eyeColor : Moa.EyeColor.values()) {
                for (Moa.FeatherColor featherColor : Moa.FeatherColor.values()) {
                    for (Moa.FeatherShape featherShape : Moa.FeatherShape.values()) {
                        MoaEggType type = new MoaEggType(keratinColor, eyeColor, featherColor, featherShape);
                        ResourceLocation name = modelLocation.withSuffix("_"
                                + featherShape.getSerializedName()
                                + "_keratin_" + keratinColor.getSerializedName()
                                + "_eyes_" + eyeColor.getSerializedName()
                                + "_feather_" + featherColor.getSerializedName());
                        ItemModel.Unbaked model = ItemModelUtils.plainModel(name);
                        ModelTemplates.FLAT_ITEM.create(textureLocation, TextureMapping.layer0(textureLocation), itemModels.modelOutput);
                        list.add(ItemModelUtils.when(type, model));
                    }
                }
            }
        }
        itemModels.itemModelOutput.accept(item, ItemModelUtils.select(new SelectMoaEggType(), ItemModelUtils.plainModel(modelLocation), list));
    }

    @Override
    public Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.of();
    }

    @Override
    public final String getName() {
        return this.modId + " Item Models";
    }
}
