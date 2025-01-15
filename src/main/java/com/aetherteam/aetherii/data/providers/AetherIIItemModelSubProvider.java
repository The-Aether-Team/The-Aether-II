package com.aetherteam.aetherii.data.providers;

import com.aetherteam.aetherii.client.renderer.item.properties.*;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIIModelTemplates;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.components.MoaEggType;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.select.Charge;
import net.minecraft.client.renderer.item.properties.select.DisplayContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class AetherIIItemModelSubProvider extends ItemModelGenerators {
    public AetherIIItemModelSubProvider(ItemModelOutput itemModelOutput, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(itemModelOutput, modelOutput);
    }

    public void generateReinforcedItem(Item item, ModelTemplate template) {
        ItemModel.Unbaked base = ItemModelUtils.plainModel(this.createFlatItemModel(item, template));
        ItemModel.Unbaked reinforced1 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_reinforced_1", template));
        ItemModel.Unbaked reinforced2 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_reinforced_2", template));
        this.itemModelOutput.accept(item, ItemModelUtils.rangeSelect(new ReinforcementTierRange(), base, ItemModelUtils.override(reinforced1, 0.1F), ItemModelUtils.override(reinforced2, 0.3F)));
    }

    public void generateCrossbow(Item item) {
        ItemModel.Unbaked base = ItemModelUtils.plainModel(this.createFlatItemModel(item, ModelTemplates.CROSSBOW));
        ItemModel.Unbaked pulling0 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_pulling_0", ModelTemplates.CROSSBOW));
        ItemModel.Unbaked pulling1 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_pulling_1", ModelTemplates.CROSSBOW));
        ItemModel.Unbaked pulling2 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_pulling_2", ModelTemplates.CROSSBOW));
        ItemModel.Unbaked arrow = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_bolt", ModelTemplates.CROSSBOW));
        this.itemModelOutput.accept(item, ItemModelUtils.conditional(ItemModelUtils.isUsingItem(),
                ItemModelUtils.rangeSelect(new TieredCrossbowPullRange(), pulling0, ItemModelUtils.override(pulling1, 0.58F), ItemModelUtils.override(pulling2, 1.0F)),
                ItemModelUtils.select(new Charge(), base, ItemModelUtils.when(CrossbowItem.ChargeType.ARROW, arrow))));
    }

    public void generateModeledShield(Item item) {
        ItemModel.Unbaked normal = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item));
        ItemModel.Unbaked blocking = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item, "_blocking"));
        this.generateBooleanDispatch(item, ItemModelUtils.isUsingItem(), blocking, normal);
    }

    public void generateDyedArmorItem(Item item, int defaultColor) {
        ResourceLocation resourcelocation = this.generateLayeredItem(item, TextureMapping.getItemTexture(item), TextureMapping.getItemTexture(item).withSuffix("_dyed"));
        this.itemModelOutput.accept(item, ItemModelUtils.tintedModel(resourcelocation, BLANK_LAYER, new Dye(defaultColor)));
    }

    public void generateMoaFeatherItem(Item item) {
        ResourceLocation modelLocation = ModelLocationUtils.getModelLocation(item);
        List<SelectItemModel.SwitchCase<Moa.FeatherColor>> list = new ArrayList<>(Moa.FeatherColor.values().length);
        for (Moa.FeatherColor featherColor : Moa.FeatherColor.values()) {
            ResourceLocation name = modelLocation.withSuffix("_" + featherColor.getSerializedName());
            ItemModel.Unbaked model = ItemModelUtils.plainModel(name);
            ModelTemplates.FLAT_ITEM.create(name, TextureMapping.layer0(name), this.modelOutput);
            list.add(ItemModelUtils.when(featherColor, model));
        }
        this.itemModelOutput.accept(item, ItemModelUtils.select(new SelectFeatherColor(), ItemModelUtils.plainModel(modelLocation), list));
    }

    public void generateHealingStoneItem(Item item) {
        ItemModel.Unbaked base = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_0", ModelTemplates.FLAT_HANDHELD_ITEM));
        ItemModel.Unbaked charged1 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_1", ModelTemplates.FLAT_HANDHELD_ITEM));
        ItemModel.Unbaked charged2 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_2", ModelTemplates.FLAT_HANDHELD_ITEM));
        ItemModel.Unbaked charged3 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_3", ModelTemplates.FLAT_HANDHELD_ITEM));
        ItemModel.Unbaked charged4 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_4", ModelTemplates.FLAT_HANDHELD_ITEM));
        ItemModel.Unbaked charged5 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_5", ModelTemplates.FLAT_HANDHELD_ITEM));
        this.itemModelOutput.accept(item, ItemModelUtils.rangeSelect(new HealingStoneChargeRange(), base,
                ItemModelUtils.override(charged1, 0.1F),
                ItemModelUtils.override(charged2, 0.2F),
                ItemModelUtils.override(charged3, 0.3F),
                ItemModelUtils.override(charged4, 0.4F),
                ItemModelUtils.override(charged5, 0.5F)
        ));
    }

    public void generateGliderItem(Item item, boolean hasAbility) {
        ResourceLocation normalInventorySprite = ModelTemplates.FLAT_ITEM.create(item, TextureMapping.layer0(item), this.modelOutput);
        List<SelectItemModel.SwitchCase<ItemDisplayContext>> normalList = List.of(
                ItemModelUtils.when(ItemDisplayContext.GUI, ItemModelUtils.plainModel(normalInventorySprite)),
                ItemModelUtils.when(ItemDisplayContext.GROUND, ItemModelUtils.plainModel(normalInventorySprite)),
                ItemModelUtils.when(ItemDisplayContext.FIXED, ItemModelUtils.plainModel(normalInventorySprite))
        );

        ItemModel.Unbaked closedGliderModelBase = ItemModelUtils.plainModel(AetherIIModelTemplates.AERCLOUD_GLIDER_CLOSED.extend()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, (builder) -> builder.rotation(-90.0F, 0.0F, -80.0F).translation(5.75F, -2.0F, 4.15F).scale(0.75F))
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, (builder) -> builder.rotation(0.0F, 0.0F, -90.0F).translation(7.5F, 0.0F, 4.0F))
                .build()
                .create(item, new TextureMapping().put(TextureSlot.TEXTURE, TextureMapping.getItemTexture(item, "_model")), this.modelOutput));
        ItemModel.Unbaked openGliderModelBase = ItemModelUtils.plainModel(AetherIIModelTemplates.AERCLOUD_GLIDER_OPEN.extend()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, (builder) -> builder.rotation(-90.0F, 0.0F, -35.0F).translation(11.55F, -2.0F, -0.1F))
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, (builder) -> builder.rotation(0.0F, 0.0F, 0.0F).translation(-8.9F, 17.0F, 5.5F))
                .build()
                .create(item, new TextureMapping().put(TextureSlot.TEXTURE, TextureMapping.getItemTexture(item, "_model")), this.modelOutput));

        ItemModel.Unbaked normalRangeSelect = ItemModelUtils.rangeSelect(
                new ParachutingRange(),
                ItemModelUtils.select(new DisplayContext(), closedGliderModelBase, normalList),
                ItemModelUtils.override(ItemModelUtils.select(new DisplayContext(), openGliderModelBase, normalList), 1.0F));

        if (!hasAbility) {
            this.itemModelOutput.accept(item, normalRangeSelect);
        } else {
            ResourceLocation dullInventorySprite = ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item, "_dull"), TextureMapping.layer0(TextureMapping.getItemTexture(item, "_dull")), this.modelOutput);
            List<SelectItemModel.SwitchCase<ItemDisplayContext>> dullList = List.of(
                    ItemModelUtils.when(ItemDisplayContext.GUI, ItemModelUtils.plainModel(dullInventorySprite)),
                    ItemModelUtils.when(ItemDisplayContext.GROUND, ItemModelUtils.plainModel(dullInventorySprite)),
                    ItemModelUtils.when(ItemDisplayContext.FIXED, ItemModelUtils.plainModel(dullInventorySprite))
            );

            ItemModel.Unbaked dullRangeSelect = ItemModelUtils.rangeSelect(
                    new ParachutingRange(),
                    ItemModelUtils.select(new DisplayContext(), closedGliderModelBase, dullList),
                    ItemModelUtils.override(ItemModelUtils.select(new DisplayContext(), openGliderModelBase, dullList), 1.0F));

            this.itemModelOutput.accept(item, ItemModelUtils.rangeSelect(
                    new DullAbilityRange(),
                    normalRangeSelect,
                    ItemModelUtils.override(dullRangeSelect, 1.0F))
            );
        }
    }

    public void generateMoaEggItem(Item item) {
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
                                + "_feather_" + featherColor.getSerializedName()
                                + "_keratin_" + keratinColor.getSerializedName()
                                + "_eyes_" + eyeColor.getSerializedName());
                        ItemModel.Unbaked model = ItemModelUtils.plainModel(name);
                        ModelTemplates.THREE_LAYERED_ITEM.create(name, TextureMapping.layered(
                                textureLocation.withSuffix("_" + featherShape.getSerializedName() + "_" + featherColor.getSerializedName()),
                                textureLocation.withSuffix("_keratin_" + keratinColor.getSerializedName()),
                                textureLocation.withSuffix("_eyes_" + eyeColor.getSerializedName())
                        ), this.modelOutput);
                        list.add(ItemModelUtils.when(type, model));
                    }
                }
            }
        }
        this.itemModelOutput.accept(item, ItemModelUtils.select(new SelectMoaEggType(), ItemModelUtils.plainModel(modelLocation), list));
    }

    public void generatePortalFrameItem(Item item) {
        ResourceLocation location = ModelTemplates.TWO_LAYERED_ITEM.extend().renderType(ResourceLocation.withDefaultNamespace("translucent")).build()
                .create(item, TextureMapping.layered(TextureMapping.getItemTexture(item), TextureMapping.getItemTexture(item, "_inside")), this.modelOutput);
        this.itemModelOutput.accept(item, ItemModelUtils.plainModel(location));
    }
}
