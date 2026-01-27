package com.aetherteam.aetherii.data.providers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.item.color.EffectBuildupColorSource;
import com.aetherteam.aetherii.client.renderer.item.model.EmissiveModel;
import com.aetherteam.aetherii.client.renderer.item.model.MusicPlayerDiscModel;
import com.aetherteam.aetherii.client.renderer.item.model.ShieldModel;
import com.aetherteam.aetherii.client.renderer.item.properties.conditional.BetterIsUsingItem;
import com.aetherteam.aetherii.client.renderer.item.properties.conditional.HoldingShift;
import com.aetherteam.aetherii.client.renderer.item.properties.conditional.LassoThrow;
import com.aetherteam.aetherii.client.renderer.item.properties.range.*;
import com.aetherteam.aetherii.client.renderer.item.properties.select.SelectFeatherColor;
import com.aetherteam.aetherii.client.renderer.item.properties.select.SelectMoaEggType;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIIModelTemplates;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIITextureMappings;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIITextureSlots;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.conditional.CustomModelDataProperty;
import net.minecraft.client.renderer.item.properties.conditional.HasComponent;
import net.minecraft.client.renderer.item.properties.numeric.CrossbowPull;
import net.minecraft.client.renderer.item.properties.select.Charge;
import net.minecraft.client.renderer.item.properties.select.DisplayContext;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.List;
import java.util.function.BiConsumer;

public class AetherIIItemModelSubProvider extends ItemModelGenerators {
    public AetherIIItemModelSubProvider(ItemModelOutput itemModelOutput, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(itemModelOutput, modelOutput);
    }

    public void generateReinforcedItem(Item item, ModelTemplate template, ReinforcementTier tier) {
        ItemModel.Unbaked base = ItemModelUtils.plainModel(this.createFlatItemModel(item, template));
        ItemModel.Unbaked reinforced1 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_reinforced_1", template));
        ItemModel.Unbaked reinforced2 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_reinforced_2", template));
        this.itemModelOutput.accept(item, ItemModelUtils.rangeSelect(new ReinforcementTierRange(), base, ItemModelUtils.override(reinforced1, 0.1F), ItemModelUtils.override(reinforced2, tier.getTierNumber() * 0.1F)));
    }

    public void generateCrossbow(Item item) {
        ItemModel.Unbaked base = ItemModelUtils.plainModel(this.createFlatItemModel(item, ModelTemplates.CROSSBOW));
        ItemModel.Unbaked pulling0 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_pulling_0", ModelTemplates.CROSSBOW));
        ItemModel.Unbaked pulling1 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_pulling_1", ModelTemplates.CROSSBOW));
        ItemModel.Unbaked pulling2 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_pulling_2", ModelTemplates.CROSSBOW));
        ItemModel.Unbaked arrow = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_bolt", ModelTemplates.CROSSBOW));
        this.itemModelOutput.accept(item, ItemModelUtils.select(new Charge(),
                ItemModelUtils.conditional(ItemModelUtils.isUsingItem(),
                                ItemModelUtils.rangeSelect(
                                        new CrossbowPull(), pulling0, ItemModelUtils.override(pulling1, 0.58F), ItemModelUtils.override(pulling2, 1.0F)),
                                base),
                ItemModelUtils.when(CrossbowItem.ChargeType.ARROW, arrow)));
    }

    public void generateModeledShield(Item item, ResourceLocation particle, String type) {
        ShieldModel.Textures textures = new ShieldModel.Textures(
                List.of(TextureMapping.getItemTexture(item, "_front_0"),
                        TextureMapping.getItemTexture(item, "_front_1"),
                        TextureMapping.getItemTexture(item, "_front_2"),
                        TextureMapping.getItemTexture(item, "_front_3")),
                List.of(TextureMapping.getItemTexture(item, "_back_0"),
                        TextureMapping.getItemTexture(item, "_back_1"),
                        TextureMapping.getItemTexture(item, "_back_2"),
                        TextureMapping.getItemTexture(item, "_back_3")),
                TextureMapping.getItemTexture(item, "_handle"),
                particle);
        ItemModel.Unbaked normal = new ShieldModel.Unbaked(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "item/" + type), textures);
        ItemModel.Unbaked blocking = new ShieldModel.Unbaked(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "item/shield_blocking"), textures);
        this.generateBooleanDispatch(item, ItemModelUtils.isUsingItem(), blocking, normal);
    }

    public void generateDartShooter(Item item) {
        ItemModel.Unbaked normal = ItemModelUtils.plainModel(this.createFlatItemModel(item, AetherIIModelTemplates.DART_SHOOTER));
        ItemModel.Unbaked loaded0 = ItemModelUtils.tintedModel(AetherIIModelTemplates.DART_SHOOTER_TWO_LAYER.create(ModelLocationUtils.getModelLocation(item, "_0"), TextureMapping.layered(TextureMapping.getItemTexture(item, "_tip_0"), TextureMapping.getItemTexture(item, "_base")), this.modelOutput), new EffectBuildupColorSource());
        ItemModel.Unbaked loaded1 = ItemModelUtils.tintedModel(AetherIIModelTemplates.DART_SHOOTER_TWO_LAYER.create(ModelLocationUtils.getModelLocation(item, "_1"), TextureMapping.layered(TextureMapping.getItemTexture(item, "_tip_1"), TextureMapping.getItemTexture(item, "_base")), this.modelOutput), new EffectBuildupColorSource());
        ItemModel.Unbaked loaded2 = ItemModelUtils.tintedModel(AetherIIModelTemplates.DART_SHOOTER_TWO_LAYER.create(ModelLocationUtils.getModelLocation(item, "_2"), TextureMapping.layered(TextureMapping.getItemTexture(item, "_tip_2"), TextureMapping.getItemTexture(item, "_base")), this.modelOutput), new EffectBuildupColorSource());
        ItemModel.Unbaked loaded3 = ItemModelUtils.tintedModel(AetherIIModelTemplates.DART_SHOOTER_TWO_LAYER.create(ModelLocationUtils.getModelLocation(item, "_3"), TextureMapping.layered(TextureMapping.getItemTexture(item, "_tip_3"), TextureMapping.getItemTexture(item, "_base")), this.modelOutput), new EffectBuildupColorSource());
        ItemModel.Unbaked using0 = ItemModelUtils.tintedModel(AetherIIModelTemplates.USING_DART_SHOOTER_TWO_LAYER.create(ModelLocationUtils.getModelLocation(item, "_using_0"), TextureMapping.layered(TextureMapping.getItemTexture(item, "_tip_0"), TextureMapping.getItemTexture(item, "_base")), this.modelOutput), new EffectBuildupColorSource());
        ItemModel.Unbaked using1 = ItemModelUtils.tintedModel(AetherIIModelTemplates.USING_DART_SHOOTER_TWO_LAYER.create(ModelLocationUtils.getModelLocation(item, "_using_1"), TextureMapping.layered(TextureMapping.getItemTexture(item, "_tip_1"), TextureMapping.getItemTexture(item, "_base")), this.modelOutput), new EffectBuildupColorSource());
        ItemModel.Unbaked using2 = ItemModelUtils.tintedModel(AetherIIModelTemplates.USING_DART_SHOOTER_TWO_LAYER.create(ModelLocationUtils.getModelLocation(item, "_using_2"), TextureMapping.layered(TextureMapping.getItemTexture(item, "_tip_2"), TextureMapping.getItemTexture(item, "_base")), this.modelOutput), new EffectBuildupColorSource());
        ItemModel.Unbaked using3 = ItemModelUtils.tintedModel(AetherIIModelTemplates.USING_DART_SHOOTER_TWO_LAYER.create(ModelLocationUtils.getModelLocation(item, "_using_3"), TextureMapping.layered(TextureMapping.getItemTexture(item, "_tip_3"), TextureMapping.getItemTexture(item, "_base")), this.modelOutput), new EffectBuildupColorSource());

        this.itemModelOutput.accept(item, ItemModelUtils.conditional(
                new HasComponent(AetherIIDataComponents.DARTS_LOADED.get(), true), ItemModelUtils.conditional(new BetterIsUsingItem(),
                        ItemModelUtils.rangeSelect(new DartsLoadedRange(), using0, ItemModelUtils.override(using1, 0.25F), ItemModelUtils.override(using2, 0.5F), ItemModelUtils.override(using3, 0.75F)),
                        ItemModelUtils.rangeSelect(new DartsLoadedRange(), loaded0, ItemModelUtils.override(loaded1, 0.25F), ItemModelUtils.override(loaded2, 0.5F), ItemModelUtils.override(loaded3, 0.75F))),
                normal));
    }

    public void generateDarts(Item item) {
        ResourceLocation location = this.generateLayeredItem(item, ModelLocationUtils.getModelLocation(item, "_tip"), ModelLocationUtils.getModelLocation(item, "_base"));
        this.itemModelOutput.accept(item, ItemModelUtils.tintedModel(location, new EffectBuildupColorSource()));
    }

    public void generateHammerOfDemolition(Item item) {
        ResourceLocation inventorySprite = ModelTemplates.FLAT_HANDHELD_ITEM.create(item, TextureMapping.layer0(item), this.modelOutput);
        List<SelectItemModel.SwitchCase<ItemDisplayContext>> normalList = List.of(
                ItemModelUtils.when(ItemDisplayContext.GUI, ItemModelUtils.plainModel(inventorySprite)),
                ItemModelUtils.when(ItemDisplayContext.GROUND, ItemModelUtils.plainModel(inventorySprite)),
                ItemModelUtils.when(ItemDisplayContext.FIXED, ItemModelUtils.plainModel(inventorySprite))
        );

        ResourceLocation melee = AetherIIModelTemplates.HAMMER_OF_DEMOLITION_HANDLE.create(ModelLocationUtils.getModelLocation(item, "_held"), TextureMapping.layer0(TextureMapping.getItemTexture(item, "_held")), this.modelOutput);
        ResourceLocation meleeEmissive = AetherIIModelTemplates.HAMMER_OF_DEMOLITION_HANDLE.create(ModelLocationUtils.getModelLocation(item, "_held_emissive"), TextureMapping.layer0(TextureMapping.getItemTexture(item, "_held_emissive")), this.modelOutput);
        ResourceLocation ranged = AetherIIModelTemplates.HAMMER_OF_DEMOLITION_HANDLE.create(ModelLocationUtils.getModelLocation(item, "_held_ranged"), TextureMapping.layer0(TextureMapping.getItemTexture(item, "_held_ranged")), this.modelOutput);
        ResourceLocation rangedEmissive = AetherIIModelTemplates.HAMMER_OF_DEMOLITION_HANDLE.create(ModelLocationUtils.getModelLocation(item, "_held_ranged_emissive"), TextureMapping.layer0(TextureMapping.getItemTexture(item, "_held_ranged_emissive")), this.modelOutput);

        ResourceLocation head = AetherIIModelTemplates.HAMMER_OF_DEMOLITION_HEAD.create(item, AetherIITextureMappings.emissive(TextureMapping.getItemTexture(item, "_head")), this.modelOutput);
        ResourceLocation headReady = AetherIIModelTemplates.HAMMER_OF_DEMOLITION_HEAD_READY.create(item, AetherIITextureMappings.emissive(TextureMapping.getItemTexture(item, "_head_ranged")), this.modelOutput);
        ResourceLocation headDeployed = AetherIIModelTemplates.HAMMER_OF_DEMOLITION_HEAD_DEPLOYED.create(item, AetherIITextureMappings.emissive(TextureMapping.getItemTexture(item, "_head_ranged")), this.modelOutput);

        ItemModel.Unbaked model = ItemModelUtils.composite(ItemModelUtils.plainModel(melee), ItemModelUtils.plainModel(head), new EmissiveModel.Unbaked(meleeEmissive));
        ItemModel.Unbaked readyModel = ItemModelUtils.composite(ItemModelUtils.plainModel(ranged), ItemModelUtils.plainModel(headReady), new EmissiveModel.Unbaked(rangedEmissive));
        ItemModel.Unbaked deployedModel = ItemModelUtils.composite(ItemModelUtils.plainModel(ranged), ItemModelUtils.plainModel(headDeployed), new EmissiveModel.Unbaked(rangedEmissive));

        ItemModel.Unbaked finalModel = ItemModelUtils.select(new DisplayContext(),
                ItemModelUtils.rangeSelect(new BetterCooldown(), ItemModelUtils.conditional(new HoldingShift(), readyModel, model), ItemModelUtils.override(deployedModel, 0.01F)),
                normalList
        );
        this.itemModelOutput.accept(item, finalModel);
    }

    public void generateDyedArmorItem(Item item, int defaultColor) {
        ResourceLocation resourceLocation = this.generateLayeredItem(item, TextureMapping.getItemTexture(item), TextureMapping.getItemTexture(item).withSuffix("_dyed"));
        this.itemModelOutput.accept(item, ItemModelUtils.tintedModel(resourceLocation, BLANK_LAYER, new Dye(defaultColor)));
    }

    public void generateBrokenItem(Item item) {
        ResourceLocation modelLocation = ModelLocationUtils.getModelLocation(item).withSuffix("_broken");
        ModelTemplates.FLAT_ITEM.create(modelLocation, TextureMapping.layer0(modelLocation), this.modelOutput);
    }

    public void generateCharmItem(Item item, String type, String tier, String stat) {
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);
        ResourceLocation modelLocation = ModelLocationUtils.getModelLocation(item);
        ModelTemplates.TWO_LAYERED_ITEM.create(modelLocation, TextureMapping.layered(
                ResourceLocation.fromNamespaceAndPath(key.getNamespace(), "item/" + type + "_charm_" + tier),
                ResourceLocation.fromNamespaceAndPath(key.getNamespace(), "item/" + type + "_charm_runes_" + stat)
        ), this.modelOutput);
        this.itemModelOutput.accept(item, ItemModelUtils.plainModel(modelLocation));
    }

    public void generateMoaFeatherItem(Item item) {
        ResourceLocation modelLocation = ModelLocationUtils.getModelLocation(item);
        var fallback = ItemModelUtils.plainModel(modelLocation);
        ModelTemplates.FLAT_ITEM.create(modelLocation, TextureMapping.layer0(modelLocation), this.modelOutput);

        var list = Moa.FeatherColor.stream().map((featherColor) -> {
            ResourceLocation name = modelLocation.withSuffix("_" + featherColor.getSerializedName());
            ItemModel.Unbaked model = ItemModelUtils.plainModel(name);
            ModelTemplates.FLAT_ITEM.create(name, TextureMapping.layer0(name), this.modelOutput);
            return ItemModelUtils.when(featherColor, model);
        }).toList();

        ResourceLocation special_name_0 = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "item/special_blue_moa_feather");
        ModelTemplates.FLAT_ITEM.create(special_name_0, TextureMapping.layer0(special_name_0), this.modelOutput);
        this.itemModelOutput.accept(item,
            ItemModelUtils.conditional(
                new CustomModelDataProperty(0),
                ItemModelUtils.plainModel(special_name_0),
                ItemModelUtils.select(new SelectFeatherColor(), fallback, list)));
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

        var textures = new TextureMapping()
            .put(AetherIITextureSlots.MAIN, TextureMapping.getItemTexture(item, "/main"))
            .put(AetherIITextureSlots.SIDE1, TextureMapping.getItemTexture(item, "/side1"))
            .put(AetherIITextureSlots.SIDE2, TextureMapping.getItemTexture(item, "/side2"));

        ItemModel.Unbaked closedGliderModelBase = ItemModelUtils.plainModel(AetherIIModelTemplates.AERCLOUD_GLIDER_CLOSED.extend()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, (builder) -> builder.rotation(-90.0F, 0.0F, -80.0F).translation(5.75F, -2.0F, 4.15F).scale(0.75F))
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, (builder) -> builder.rotation(0.0F, 0.0F, -90.0F).translation(7.5F, 0.0F, 4.0F))
                .build()
                .create(item, textures, this.modelOutput));
        ItemModel.Unbaked openGliderModelBase = ItemModelUtils.plainModel(AetherIIModelTemplates.AERCLOUD_GLIDER_OPEN.extend()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, (builder) -> builder.rotation(-90.0F, 0.0F, -35.0F).translation(11.55F, -2.0F, -0.1F))
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, (builder) -> builder.rotation(0.0F, 0.0F, 0.0F).translation(-8.9F, 17.0F, 5.5F))
                .build()
                .create(item, textures, this.modelOutput));

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

    public void generateMusicDisc(Item item) {
        ResourceLocation modelLocation = ModelLocationUtils.getModelLocation(item);
        ModelTemplates.MUSIC_DISC.create(modelLocation, TextureMapping.layer0(modelLocation), this.modelOutput);
        ModelTemplates.MUSIC_DISC.create(modelLocation.withSuffix("_animated"), TextureMapping.layer0(modelLocation.withSuffix("_animated")), this.modelOutput);
        this.itemModelOutput.accept(item, ItemModelUtils.plainModel(modelLocation));
    }

    public void generateLasso(Item item) {
        ItemModel.Unbaked normal = ItemModelUtils.plainModel(this.createFlatItemModel(item, ModelTemplates.FLAT_HANDHELD_ROD_ITEM));
        ItemModel.Unbaked thrown = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_thrown", ModelTemplates.FLAT_HANDHELD_ROD_ITEM));
        this.generateBooleanDispatch(item, new LassoThrow(), thrown, normal);
    }

    public void generateMoaEggItem(Item item) {
        ResourceLocation modelLocation = ModelLocationUtils.getModelLocation(item);
        var fallback = ItemModelUtils.plainModel(modelLocation);
        ModelTemplates.FLAT_ITEM.create(modelLocation, TextureMapping.layer0(modelLocation), this.modelOutput);
        
        var feathers = ItemModelUtils.select(
            new SelectMoaEggType.FeatherShape(),
            fallback,
            Moa.FeatherShape.stream()
                .map((featherShape) -> ItemModelUtils.when(featherShape,
                    ItemModelUtils.select(new SelectMoaEggType.FeatherColor(), fallback,
                        Moa.FeatherColor.stream()
                            .map((featherColor) -> {
                                String suffix = "_" + featherShape.getSerializedName() + "_" + featherColor.getSerializedName();
                                ResourceLocation name = modelLocation.withSuffix(suffix);
                                ItemModel.Unbaked model = ItemModelUtils.plainModel(name);
                                ModelTemplates.FLAT_ITEM.create(name, TextureMapping.layer0(name), this.modelOutput);
                                return ItemModelUtils.when(featherColor, model);
                            })
                            .toList())))
                .toList());
        var eyes = ItemModelUtils.select(
            new SelectMoaEggType.EyeColor(),
            fallback,
            Moa.EyeColor.stream()
                .map((eyeColor) -> {
                    String suffix = "_eyes_" + eyeColor.getSerializedName();
                    ResourceLocation name = modelLocation.withSuffix(suffix);
                    ItemModel.Unbaked model = ItemModelUtils.plainModel(name);
                    ModelTemplates.FLAT_ITEM.create(name, TextureMapping.layer0(name), this.modelOutput);
                    return ItemModelUtils.when(eyeColor, model);
                })
                .toList());
        var keratin = ItemModelUtils.select(
            new SelectMoaEggType.KeratinColor(),
            fallback,
            Moa.KeratinColor.stream()
                .map((keratinColor) -> {
                    String suffix = "_keratin_" + keratinColor.getSerializedName();
                    ResourceLocation name = modelLocation.withSuffix(suffix);
                    ItemModel.Unbaked model = ItemModelUtils.plainModel(name);
                    ModelTemplates.FLAT_ITEM.create(name, TextureMapping.layer0(name), this.modelOutput);
                    return ItemModelUtils.when(keratinColor, model);
                })
                .toList());
        
        
        this.itemModelOutput.accept(item, ItemModelUtils.composite(feathers, eyes, keratin));

        // List<SelectItemModel.SwitchCase<MoaEggType>> list = new ArrayList<>(Moa.KeratinColor.values().length * Moa.EyeColor.values().length * Moa.FeatherColor.values().length * Moa.FeatherShape.values().length);

        // for (Moa.KeratinColor keratinColor : Moa.KeratinColor.values()) {
        //     for (Moa.EyeColor eyeColor : Moa.EyeColor.values()) {
        //         for (Moa.FeatherColor featherColor : Moa.FeatherColor.values()) {
        //             for (Moa.FeatherShape featherShape : Moa.FeatherShape.values()) {
        //                 MoaEggType type = new MoaEggType(keratinColor, eyeColor, featherColor, featherShape);
        //                 ResourceLocation name = modelLocation.withSuffix("_"
        //                         + featherShape.getSerializedName()
        //                         + "_feather_" + featherColor.getSerializedName()
        //                         + "_keratin_" + keratinColor.getSerializedName()
        //                         + "_eyes_" + eyeColor.getSerializedName());
        //                 ItemModel.Unbaked model = ItemModelUtils.plainModel(name);
        //                 ModelTemplates.THREE_LAYERED_ITEM.create(name, TextureMapping.layered(
        //                         textureLocation.withSuffix("_" + featherShape.getSerializedName() + "_" + featherColor.getSerializedName()),
        //                         textureLocation.withSuffix("_keratin_" + keratinColor.getSerializedName()),
        //                         textureLocation.withSuffix("_eyes_" + eyeColor.getSerializedName())
        //                 ), this.modelOutput);
        //                 list.add(ItemModelUtils.when(type, model));
        //             }
        //         }
        //     }
        // }
        // this.itemModelOutput.accept(item, ItemModelUtils.select(new SelectMoaEggType(), ItemModelUtils.plainModel(modelLocation), list));
    }

    public void generateDyedSaddleItem(Item item) {
        ResourceLocation location = ModelLocationUtils.getModelLocation(item);
        ResourceLocation texture = TextureMapping.getItemTexture(item);
        ResourceLocation overlay = TextureMapping.getItemTexture(item, "_overlay");
        ModelTemplates.TWO_LAYERED_ITEM.create(location, TextureMapping.layered(texture, overlay), this.modelOutput);
        ItemModel.Unbaked model = ItemModelUtils.tintedModel(location, new Dye(0xFF7D8BA3));
        this.itemModelOutput.accept(AetherIIItems.MOA_SADDLE.asItem(), model);
    }

    public void generatePortalFrameItem(Item item) {
        ResourceLocation location = ModelTemplates.TWO_LAYERED_ITEM.extend().renderType(ResourceLocation.withDefaultNamespace("translucent")).build()
                .create(item, TextureMapping.layered(TextureMapping.getItemTexture(item), TextureMapping.getItemTexture(item, "_inside")), this.modelOutput);
        this.itemModelOutput.accept(item, ItemModelUtils.plainModel(location));
    }

    public void generateMusicPlayer(Item item) {
        ResourceLocation backLocation = ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item, "_back"), TextureMapping.layer0(TextureMapping.getItemTexture(item, "_back")), this.modelOutput);
        ResourceLocation frontLocation = ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item, "_front"), TextureMapping.layer0(TextureMapping.getItemTexture(item, "_front")), this.modelOutput);

        ItemModel.Unbaked model = ItemModelUtils.composite(ItemModelUtils.plainModel(backLocation), new MusicPlayerDiscModel.Unbaked(), ItemModelUtils.plainModel(frontLocation));

        this.itemModelOutput.accept(item, model);
    }
}
