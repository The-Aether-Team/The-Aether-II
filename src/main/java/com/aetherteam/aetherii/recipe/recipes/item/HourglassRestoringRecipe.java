package com.aetherteam.aetherii.recipe.recipes.item;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import com.aetherteam.aetherii.recipe.book.AmberHourglassBookCategory;
import com.aetherteam.aetherii.recipe.display.slot.AmberFuel;
import com.aetherteam.aetherii.recipe.display.AmberHourglassRecipeDisplay;
import com.aetherteam.aetherii.recipe.input.SingleRecipeInputWithRandom;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.OutputEntry;
import com.aetherteam.aetherii.recipe.serializer.AetherIIRecipeSerializers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HourglassRestoringRecipe implements Recipe<SingleRecipeInputWithRandom> {
    private final String group;
    protected final AmberHourglassBookCategory category;
    private final Ingredient ingredient;
    private final HourglassOutput results;
    protected final float experience;
    protected final int processingTime;
    @Nullable
    private PlacementInfo placementInfo;

    public HourglassRestoringRecipe(String group, AmberHourglassBookCategory category, Ingredient ingredient, HourglassOutput results, float experience, int processingTime) {
        this.group = group;
        this.category = category;
        this.ingredient = ingredient;
        this.results = results;
        this.experience = experience;
        this.processingTime = processingTime;
    }

    @Override
    public String group() {
        return this.group;
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

    public AmberHourglassBookCategory category() {
        return this.category;
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
    public ItemStack assemble(SingleRecipeInputWithRandom input, HolderLookup.Provider provider) {
        return this.results.output1().process(input.randomSource());
    }

    public List<ItemStack> assembleOutputs(SingleRecipeInputWithRandom input, HolderLookup.Provider provider) {
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
        return AetherIIRecipeSerializers.HOURGLASS_RESTORING.get();
    }

    @Override
    public List<RecipeDisplay> display() {
        List<SlotDisplay> results1 = this.results().output1().list().stream().map(ItemStack::getItem).distinct().filter((item) -> item != Items.AIR).map((item) -> new SlotDisplay.ItemSlotDisplay(item.builtInRegistryHolder())).collect(Collectors.toUnmodifiableList());
        List<SlotDisplay> results2 = this.results().output2().list().stream().map(ItemStack::getItem).distinct().filter((item) -> item != Items.AIR).map((item) -> new SlotDisplay.ItemSlotDisplay(item.builtInRegistryHolder())).collect(Collectors.toUnmodifiableList());
        List<SlotDisplay> results3 = this.results().output3().list().stream().map(ItemStack::getItem).distinct().filter((item) -> item != Items.AIR).map((item) -> new SlotDisplay.ItemSlotDisplay(item.builtInRegistryHolder())).collect(Collectors.toUnmodifiableList());
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

    public static class Serializer implements RecipeSerializer<HourglassRestoringRecipe> {
        private final MapCodec<HourglassRestoringRecipe> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, HourglassRestoringRecipe> streamCodec;

        public Serializer() {
            this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.STRING.optionalFieldOf("group", "").forGetter(HourglassRestoringRecipe::group),
                    AmberHourglassBookCategory.CODEC.fieldOf("category").orElse(AmberHourglassBookCategory.RESTORATION).forGetter(HourglassRestoringRecipe::category),
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(HourglassRestoringRecipe::ingredient),
                    HourglassOutput.CODEC.fieldOf("results").forGetter(HourglassRestoringRecipe::results),
                    Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(HourglassRestoringRecipe::experience),
                    Codec.INT.fieldOf("processing_time").orElse(200).forGetter(HourglassRestoringRecipe::processingTime)
            ).apply(instance, HourglassRestoringRecipe::new));
            this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
        }

        @Override
        public MapCodec<HourglassRestoringRecipe> codec() {
            return this.codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, HourglassRestoringRecipe> streamCodec() {
            return this.streamCodec;
        }

        public HourglassRestoringRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            AmberHourglassBookCategory category = buffer.readEnum(AmberHourglassBookCategory.class);
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            HourglassOutput results = HourglassOutput.STREAM_CODEC.decode(buffer);
            float experience = buffer.readFloat();
            int processingTime = buffer.readVarInt();
            return new HourglassRestoringRecipe(group, category, ingredient, results, experience, processingTime);
        }

        public void toNetwork(RegistryFriendlyByteBuf buffer, HourglassRestoringRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeEnum(recipe.category());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
            HourglassOutput.STREAM_CODEC.encode(buffer, recipe.results);
            buffer.writeFloat(recipe.experience());
            buffer.writeVarInt(recipe.processingTime());
        }
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
