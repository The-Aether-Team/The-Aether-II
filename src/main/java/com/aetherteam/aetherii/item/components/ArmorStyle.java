package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.api.styles.StyleMaterial;
import com.aetherteam.aetherii.api.styles.StyleDesign;
import com.aetherteam.aetherii.data.resources.registries.AetherIIStyleDesigns;
import com.aetherteam.aetherii.data.resources.registries.AetherIIStyleMaterials;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.function.Consumer;

public record ArmorStyle(ResourceKey<StyleMaterial> material, ResourceKey<StyleDesign> design, boolean showInTooltip) implements TooltipProvider {
    public static final Codec<ArmorStyle> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceKey.codec(AetherIIStyleMaterials.STYLE_MATERIAL_REGISTRY_KEY).fieldOf("material").forGetter(ArmorStyle::material),
            ResourceKey.codec(AetherIIStyleDesigns.STYLE_DESIGN_REGISTRY_KEY).fieldOf("design").forGetter(ArmorStyle::design),
            Codec.BOOL.optionalFieldOf("show_in_tooltip", true).forGetter(ArmorStyle::showInTooltip)
    ).apply(instance, ArmorStyle::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ArmorStyle> STREAM_CODEC = StreamCodec.composite(
            ResourceKey.streamCodec(AetherIIStyleMaterials.STYLE_MATERIAL_REGISTRY_KEY), ArmorStyle::material,
            ResourceKey.streamCodec(AetherIIStyleDesigns.STYLE_DESIGN_REGISTRY_KEY), ArmorStyle::design,
            ByteBufCodecs.BOOL, ArmorStyle::showInTooltip,
            ArmorStyle::new);

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag) { //todo

    }

    @OnlyIn(Dist.CLIENT)
    public record SpriteKey(RegistryAccess access, ArmorStyle style, String layerType) {
        private static String getColorPaletteSuffix(Holder<StyleMaterial> material) {
            return material.value().assetId().getPath();
        }

        public ResourceLocation textureId() {
            Holder<StyleMaterial> materialHolder = AetherIIStyleMaterials.getRegistry(this.access()).getOrThrow(this.style().material());
            Holder<StyleDesign> designHolder = AetherIIStyleDesigns.getRegistry(this.access()).getOrThrow(this.style().design());
            ResourceLocation designLocation = designHolder.value().assetId();
            String color = getColorPaletteSuffix(materialHolder);
            return designLocation.withPath((string) -> "armor_styles/entity/" + this.layerType() + "/" + string + "_" + color);
        }
    }
}
