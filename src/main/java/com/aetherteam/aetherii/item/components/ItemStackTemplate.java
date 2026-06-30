package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public record ItemStackTemplate(Item item, int count, @Nullable DataComponentPatch components) implements ItemLike {
    public static final Codec<ItemStackTemplate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(template -> BuiltInRegistries.ITEM.getKey(template.item())),
            Codec.INT.optionalFieldOf("count", 1).forGetter(ItemStackTemplate::count)
    ).apply(instance, (id, count) -> new ItemStackTemplate(BuiltInRegistries.ITEM.get(id), count)));
    public static final StreamCodec<FriendlyByteBuf, ItemStackTemplate> STREAM_CODEC = StreamCodec.of(
            (buffer, template) -> buffer.writeItem(template.create()),
            buffer -> fromNonEmptyStack(buffer.readItem())
    );

    public ItemStackTemplate(ItemLike item) {
        this(item.asItem(), 1);
    }

    public ItemStackTemplate(ItemLike item, int count) {
        this(item.asItem(), count, null);
    }

    public ItemStackTemplate(ItemLike item, int count, DataComponentPatch components) {
        this(item.asItem(), count, components);
    }

    public ItemStackTemplate(RegistryObject<? extends Item> item, int count) {
        this(item.get(), count, null);
    }

    public ItemStackTemplate(RegistryObject<? extends Item> item, int count, DataComponentPatch components) {
        this(item.get(), count, components);
    }

    public ItemStackTemplate(Holder<Item> item, int count) {
        this(item.value(), count, null);
    }

    public ItemStackTemplate(Holder<Item> item, int count, DataComponentPatch components) {
        this(item.value(), count, components);
    }

    public ItemStack create() {
        ItemStack stack = new ItemStack(this.item, this.count);
        if (this.components != null) {
            this.components.apply(stack);
        }
        return stack;
    }

    public static ItemStackTemplate fromNonEmptyStack(ItemStack stack) {
        return new ItemStackTemplate(stack.getItem(), stack.getCount());
    }

    @Override
    public Item asItem() {
        return this.item;
    }
}
