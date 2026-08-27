package com.aetherteam.aetherii.recipe.recipes.item;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import com.aetherteam.aetherii.recipe.book.AmberHourglassBookCategory;
import com.aetherteam.aetherii.recipe.display.AmberHourglassRecipeDisplay;
import com.aetherteam.aetherii.recipe.display.slot.AmberFuel;
import com.aetherteam.aetherii.recipe.input.SingleRecipeInputWithRandom;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.OutputEntry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HourglassRestoringRecipe implements Recipe<SingleRecipeInputWithRandom> {
    public static final MapCodec<HourglassRestoringRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            CommonInfo.MAP_CODEC.forGetter(r -> r.commonInfo),
            AmberHourglassBookInfo.MAP_CODEC.forGetter(r -> r.bookInfo),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(HourglassRestoringRecipe::ingredient),
            HourglassOutput.CODEC.fieldOf("results").forGetter(HourglassRestoringRecipe::results),
            Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(HourglassRestoringRecipe::experience),
            Codec.INT.fieldOf("processing_time").orElse(200).forGetter(HourglassRestoringRecipe::processingTime)
    ).apply(i, HourglassRestoringRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, HourglassRestoringRecipe> STREAM_CODEC = StreamCodec.composite(
            CommonInfo.STREAM_CODEC, r -> r.commonInfo,
            AmberHourglassBookInfo.STREAM_CODEC, r -> r.bookInfo,
            Ingredient.CONTENTS_STREAM_CODEC, HourglassRestoringRecipe::ingredient,
            HourglassOutput.STREAM_CODEC, HourglassRestoringRecipe::results,
            ByteBufCodecs.FLOAT, HourglassRestoringRecipe::experience,
            ByteBufCodecs.INT, HourglassRestoringRecipe::processingTime,
            HourglassRestoringRecipe::new
    );
    public static final RecipeSerializer<HourglassRestoringRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    protected final Recipe.CommonInfo commonInfo;
    protected final AmberHourglassBookInfo bookInfo;
    private final Ingredient ingredient;
    private final HourglassOutput results;
    protected final float experience;
    protected final int processingTime;
    @Nullable
    private PlacementInfo placementInfo;

    public HourglassRestoringRecipe(Recipe.CommonInfo commonInfo, AmberHourglassBookInfo bookInfo, Ingredient ingredient, HourglassOutput results, float experience, int processingTime) {
        this.commonInfo = commonInfo;
        this.bookInfo = bookInfo;
        this.ingredient = ingredient;
        this.results = results;
        this.experience = experience;
        this.processingTime = processingTime;
    }

    @Override
    public boolean showNotification() {
        return this.commonInfo.showNotification();
    }

    @Override
    public String group() {
        return this.bookInfo.group;
    }

    public AmberHourglassBookCategory category() {
        return this.bookInfo.category;
    }

    public Ingredient ingredient() {
        return this.ingredient;
    }

    public HourglassOutput results() {
        return this.results;
    }

    public float experience() {
        return this.experience;
    }

    public int processingTime() {
        return this.processingTime;
    }

    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.create(this.ingredient);
        }
        return this.placementInfo;
    }

    @Override
    public boolean matches(SingleRecipeInputWithRandom input, Level level) {
        return this.ingredient().test(input.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInputWithRandom input) {
        return this.results.output1().process(input.randomSource());
    }

    public List<ItemStack> assembleOutputs(SingleRecipeInputWithRandom input) {
        List<ItemStack> outputs = new ArrayList<>();
        outputs.add(0, this.results.output1().process(input.randomSource()));
        outputs.add(1, this.results.output2().process(input.randomSource()));
        outputs.add(2, this.results.output3().process(input.randomSource()));
        return outputs;
    }

    @Override
    public RecipeType<HourglassRestoringRecipe> getType() {
        return AetherIIRecipeTypes.HOURGLASS_RESTORING.get();
    }

    @Override
    public RecipeSerializer<HourglassRestoringRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public List<RecipeDisplay> display() {
        List<SlotDisplay> results1 = this.results().output1().list().stream().map(ItemStackTemplate::item).distinct().map(SlotDisplay.ItemSlotDisplay::new).collect(Collectors.toUnmodifiableList());
        List<SlotDisplay> results2 = this.results().output2().list().stream().map(ItemStackTemplate::item).distinct().map(SlotDisplay.ItemSlotDisplay::new).collect(Collectors.toUnmodifiableList());
        List<SlotDisplay> results3 = this.results().output3().list().stream().map(ItemStackTemplate::item).distinct().map(SlotDisplay.ItemSlotDisplay::new).collect(Collectors.toUnmodifiableList());
        return List.of(new AmberHourglassRecipeDisplay(
                this.ingredient().display(),
                AmberFuel.INSTANCE,
                new SlotDisplay.Composite(results1),
                new SlotDisplay.Composite(results2),
                new SlotDisplay.Composite(results3),
                new SlotDisplay.ItemSlotDisplay(AetherIIBlocks.AMBER_HOURGLASS.asItem()),
                this.processingTime,
                this.experience
        ));
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return switch (this.category()) {
            case RESTORATION -> AetherIIRecipeBookCategories.AMBER_HOURGLASS_RESTORATION.get();
            case UNCRAFTING -> AetherIIRecipeBookCategories.AMBER_HOURGLASS_UNCRAFTING.get();
        };
    }

    public record AmberHourglassBookInfo(AmberHourglassBookCategory category, String group) implements Recipe.BookInfo<AmberHourglassBookCategory> {
        public static final MapCodec<AmberHourglassBookInfo> MAP_CODEC = BookInfo.mapCodec(AmberHourglassBookCategory.CODEC, AmberHourglassBookCategory.RESTORATION, AmberHourglassBookInfo::new);
        public static final StreamCodec<RegistryFriendlyByteBuf, AmberHourglassBookInfo> STREAM_CODEC = BookInfo.streamCodec(AmberHourglassBookCategory.STREAM_CODEC, AmberHourglassBookInfo::new);
    }

    public record HourglassOutput(OutputEntry.BaseEntry output1, OutputEntry.BaseEntry output2, OutputEntry.BaseEntry output3) {
        public static final Codec<HourglassOutput> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                OutputEntry.ENTRY_CODEC.fieldOf("output_1").forGetter(HourglassOutput::output1),
                OutputEntry.ENTRY_CODEC.fieldOf("output_2").forGetter(HourglassOutput::output2),
                OutputEntry.ENTRY_CODEC.fieldOf("output_3").forGetter(HourglassOutput::output3)
            ).apply(builder, HourglassOutput::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, HourglassOutput> STREAM_CODEC = StreamCodec.composite(
                OutputEntry.ENTRY_STREAM_CODEC, HourglassOutput::output1,
                OutputEntry.ENTRY_STREAM_CODEC, HourglassOutput::output2,
                OutputEntry.ENTRY_STREAM_CODEC, HourglassOutput::output3,
                HourglassOutput::new
        );
    }
}
