package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.AttributeMapAccessor;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;

import java.util.List;
import java.util.Map;

public record ArmorSet(TagKey<Item> armor) {
    public static final Codec<ArmorSet> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            TagKey.codec(Registries.ITEM).fieldOf("armor").forGetter(ArmorSet::armor)
    ).apply(builder, ArmorSet::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ArmorSet> STREAM_CODEC = StreamCodec.composite(
            TagKey.streamCodec(Registries.ITEM), ArmorSet::armor,
            ArmorSet::new);

    public static void addAbilityAttributeTooltip(ItemStack itemStack, List<Component> tooltipLines, AttributeTooltipContext context) {
        ArmorSet armorSet = itemStack.get(AetherIIDataComponents.ARMOR_SET);
        if (armorSet != null) {
            Player player = context.player();
            if (player != null && EquipmentUtil.getEquipment(player).stream().map(ItemStack::getItem).toList().contains(itemStack.getItem())) {
                for (Map.Entry<Holder<Attribute>, AttributeInstance> entry : ((AttributeMapAccessor) player.getAttributes()).aether_ii$getAttributes().entrySet()) {
                    for (AttributeModifier modifier : entry.getValue().getModifiers()) {
                        if (modifier.id().getPath().startsWith("armor_set.ability.") && modifier.id().getPath().contains(armorSet.armor().location().getPath().substring(armorSet.armor().location().getPath().lastIndexOf('/') + 1))) {
                            tooltipLines.add(entry.getKey().value().toComponent(modifier, context.flag()));
                        }
                    }
                }
            }
        }
    }
}
