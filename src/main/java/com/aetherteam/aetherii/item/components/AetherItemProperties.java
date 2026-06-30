package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.registries.AetherIIJukeboxSongs;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.util.RegistryObjectUtil;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class AetherItemProperties extends Item.Properties {
    private final Map<DataComponentType<?>, Object> defaultComponents = new LinkedHashMap<>();

    @Override
    public AetherItemProperties food(FoodProperties food) {
        super.food(food);
        return this;
    }

    @Override
    public AetherItemProperties stacksTo(int maxStackSize) {
        super.stacksTo(maxStackSize);
        return this;
    }

    @Override
    public AetherItemProperties defaultDurability(int durability) {
        super.defaultDurability(durability);
        return this;
    }

    @Override
    public AetherItemProperties durability(int durability) {
        super.durability(durability);
        return this;
    }

    @Override
    public AetherItemProperties craftRemainder(Item craftingRemainingItem) {
        super.craftRemainder(craftingRemainingItem);
        return this;
    }

    @Override
    public AetherItemProperties rarity(Rarity rarity) {
        super.rarity(rarity);
        component(DataComponents.RARITY, rarity);
        return this;
    }

    @Override
    public AetherItemProperties fireResistant() {
        super.fireResistant();
        return this;
    }

    @Override
    public AetherItemProperties setNoRepair() {
        super.setNoRepair();
        return this;
    }

    @Override
    public AetherItemProperties requiredFeatures(FeatureFlag... requiredFeatures) {
        super.requiredFeatures(requiredFeatures);
        return this;
    }

    public AetherItemProperties enchantable(int enchantmentValue) {
        return this;
    }

    public AetherItemProperties setNoCombineRepair() {
        return this.setNoRepair();
    }

    public AetherItemProperties usingConvertsTo(Item item) {
        return this;
    }

    public AetherItemProperties useItemDescriptionPrefix() {
        return this;
    }

    public AetherItemProperties spawnEgg(EntityType<? extends Mob> entityType) {
        return this;
    }

    public <T> AetherItemProperties component(DataComponentType<T> type, T value) {
        this.defaultComponents.put(type, value);
        return this;
    }

    public <T> AetherItemProperties delayedComponent(DataComponentType<T> type, Function<DelayedComponentContext, T> factory) {
        return this.component(type, factory.apply(DelayedComponentContext.INSTANCE));
    }

    public Map<DataComponentType<?>, Object> defaultComponents() {
        return Map.copyOf(this.defaultComponents);
    }

    public static final class DelayedComponentContext {
        private static final DelayedComponentContext INSTANCE = new DelayedComponentContext();

        private DelayedComponentContext() {
        }

        @SuppressWarnings("unchecked")
        public <T> Holder<T> getOrThrow(ResourceKey<T> key) {
            if (key.registry().equals(JukeboxSong.REGISTRY_KEY.location())) {
                return (Holder<T>) Holder.direct(jukeboxSong((ResourceKey<JukeboxSong>) key));
            }
            throw new IllegalArgumentException("Unsupported delayed item component key: " + key);
        }

        private static JukeboxSong jukeboxSong(ResourceKey<JukeboxSong> key) {
            String name = key.location().getPath();
            return switch (name) {
                case "ascending_dawn" -> song(key, RegistryObjectUtil.holder(AetherIISoundEvents.ITEM_ENGRAVED_DISC_ASCENDING_DAWN), 350, 2);
                case "aerwhale" -> song(key, RegistryObjectUtil.holder(AetherIISoundEvents.ITEM_ENGRAVED_DISC_AERWHALE), 178, 3);
                case "approaches" -> song(key, RegistryObjectUtil.holder(AetherIISoundEvents.ITEM_ENGRAVED_DISC_APPROACHES), 274, 4);
                case "demise" -> song(key, RegistryObjectUtil.holder(AetherIISoundEvents.ITEM_ENGRAVED_DISC_DEMISE), 300, 5);
                case "chinchilla" -> song(key, RegistryObjectUtil.holder(AetherIISoundEvents.ITEM_ENGRAVED_DISC_CHINCHILLA), 163, 6);
                case "high" -> song(key, RegistryObjectUtil.holder(AetherIISoundEvents.ITEM_ENGRAVED_DISC_HIGH), 186, 7);
                case "revolutions" -> song(key, RegistryObjectUtil.holder(AetherIISoundEvents.ITEM_ENGRAVED_DISC_REVOLUTIONS), 221, 8);
                default -> throw new IllegalArgumentException("Unknown jukebox song key: " + key);
            };
        }

        private static JukeboxSong song(ResourceKey<JukeboxSong> key, Holder<net.minecraft.sounds.SoundEvent> soundEvent, int lengthInSeconds, int comparatorOutput) {
            ResourceLocation id = key.location();
            if (!AetherII.MODID.equals(id.getNamespace())) {
                throw new IllegalArgumentException("Unsupported jukebox song namespace: " + id);
            }
            return new JukeboxSong(soundEvent, Component.translatable(Util.makeDescriptionId("jukebox_song", id)), (float) lengthInSeconds, comparatorOutput);
        }
    }
}
