package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.api.styles.StyleDesign;
import com.aetherteam.aetherii.api.styles.StyleMaterial;
import com.aetherteam.aetherii.data.resources.registries.AetherIIStyleDesigns;
import com.aetherteam.aetherii.data.resources.registries.AetherIIStyleMaterials;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import com.aetherteam.aetherii.item.components.DataComponentGetter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.TooltipFlag;
import com.aetherteam.aetherii.item.components.TooltipProvider;

import java.util.function.Consumer;

public record ArmorStyle(ResourceKey<StyleMaterial> material, ResourceKey<StyleDesign> design, boolean showInTooltip) implements TooltipProvider {
    public static final Codec<ArmorStyle> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceKey.codec(AetherIIRegistries.STYLE_MATERIAL).fieldOf("material").forGetter(ArmorStyle::material),
            ResourceKey.codec(AetherIIRegistries.STYLE_DESIGN).fieldOf("design").forGetter(ArmorStyle::design),
            Codec.BOOL.optionalFieldOf("show_in_tooltip", true).forGetter(ArmorStyle::showInTooltip)
    ).apply(instance, ArmorStyle::new));
    public static final StreamCodec<FriendlyByteBuf, ArmorStyle> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.RESOURCE_LOCATION.map(location -> ResourceKey.create(AetherIIRegistries.STYLE_MATERIAL, location), ResourceKey::location), ArmorStyle::material,
            ByteBufCodecs.RESOURCE_LOCATION.map(location -> ResourceKey.create(AetherIIRegistries.STYLE_DESIGN, location), ResourceKey::location), ArmorStyle::design,
            ByteBufCodecs.BOOL, ArmorStyle::showInTooltip,
            ArmorStyle::new);

    @Override
    public void addToTooltip(Object tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) { //todo

    }

    public record SpriteKey(RegistryAccess access, ArmorStyle style, String layerType) {
        private static String getColorPaletteSuffix(Holder<StyleMaterial> material) {
            return material.value().assetId().getPath();
        }

        public ResourceLocation textureId() {
            Holder<StyleMaterial> materialHolder = AetherIIStyleMaterials.getRegistry(this.access()).getHolderOrThrow(this.style().material());
            Holder<StyleDesign> designHolder = AetherIIStyleDesigns.getRegistry(this.access()).getHolderOrThrow(this.style().design());
            ResourceLocation designLocation = designHolder.value().assetId();
            String color = getColorPaletteSuffix(materialHolder);
            return designLocation.withPath((string) -> "armor_styles/entity/" + this.layerType() + "/" + string + "_" + color);
        }
    }
}
