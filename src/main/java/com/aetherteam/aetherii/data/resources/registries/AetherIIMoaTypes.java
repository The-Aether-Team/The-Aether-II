package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.moaegg.MoaType;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AetherIIMoaTypes {
    public static final ResourceKey<Registry<MoaType>> MOA_TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_type"));

    public static final ResourceKey<MoaType> BLUE = createKey("blue");
    public static final ResourceKey<MoaType> WHITE = createKey("white");
    public static final ResourceKey<MoaType> BLACK = createKey("black");

    private static ResourceKey<MoaType> createKey(String name) {
        return ResourceKey.create(AetherIIMoaTypes.MOA_TYPE_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<MoaType> context) {
        context.register(BLUE, new MoaType(new ItemStack(AetherIIBlocks.BLUE_MOA_EGG.get()), 3, 0.155F, 100, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/blue_moa.png"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/moa_saddle.png")));
    }

    @Nullable
    public static ResourceKey<MoaType> getResourceKey(RegistryAccess registryAccess, String location) {
        return getResourceKey(registryAccess, new ResourceLocation(location));
    }

    @Nullable
    public static ResourceKey<MoaType> getResourceKey(RegistryAccess registryAccess, ResourceLocation location) {
        MoaType moaType = getMoaType(registryAccess, location);
        if (moaType != null) {
            return registryAccess.registryOrThrow(AetherIIMoaTypes.MOA_TYPE_REGISTRY_KEY).getResourceKey(moaType).orElse(null);
        } else {
            return null;
        }
    }

    @Nullable
    public static ResourceKey<MoaType> getResourceKey(RegistryAccess registryAccess, MoaType moaType) {
        return registryAccess.registryOrThrow(AetherIIMoaTypes.MOA_TYPE_REGISTRY_KEY).getResourceKey(moaType).orElse(null);
    }

    @Nullable
    public static MoaType getMoaType(RegistryAccess registryAccess, String location) {
        return getMoaType(registryAccess, new ResourceLocation(location));
    }

    @Nullable
    public static MoaType getMoaType(RegistryAccess registryAccess, ResourceLocation location) {
        return registryAccess.registryOrThrow(AetherIIMoaTypes.MOA_TYPE_REGISTRY_KEY).get(location);
    }


//    @Nullable
//    public static MoaType get(RegistryAccess registryAccess, String location) {
//        return get(registryAccess, new ResourceLocation(location));
//    }
//
//    @Nullable
//    public static MoaType get(RegistryAccess registryAccess, ResourceLocation location) {
//        return registryAccess.registryOrThrow(AetherIIMoaTypes.MOA_TYPE_REGISTRY_KEY).get(location);
//    }
//
//    @Nullable
//    public static ResourceKey<MoaType> getKey(RegistryAccess registryAccess, String location) {
//        return getKey(registryAccess, new ResourceLocation(location));
//    }
//
//    @Nullable
//    public static ResourceKey<MoaType> getKey(RegistryAccess registryAccess, ResourceLocation location) {
//        MoaType moaType = get(registryAccess, location);
//        if (moaType != null) {
//            return registryAccess.registryOrThrow(AetherIIMoaTypes.MOA_TYPE_REGISTRY_KEY).getResourceKey(moaType).orElse(null);
//        }
//        return null;
//    }

    /**
     * Gets a random {@link MoaType} with a weighted chance. This is used when spawning Moas in the world.<br>
     * A {@link SimpleWeightedRandomList} is built with all the {@link MoaType}s and their spawn chance weights, and one is randomly picked out of the list.
     *
     * @param registryAccess The {@link RegistryAccess} to use.
     * @param random         The {@link RandomSource} to use.
     * @return The {@link MoaType}.
     */
    public static MoaType getWeightedChance(RegistryAccess registryAccess, RandomSource random) {
        Registry<MoaType> moaTypeRegistry = registryAccess.registryOrThrow(AetherIIMoaTypes.MOA_TYPE_REGISTRY_KEY);
        SimpleWeightedRandomList.Builder<MoaType> weightedListBuilder = SimpleWeightedRandomList.builder();
        moaTypeRegistry.holders().forEach((moaType) -> weightedListBuilder.add(moaType.value(), moaType.value().spawnChance()));
        SimpleWeightedRandomList<MoaType> weightedList = weightedListBuilder.build();
        Optional<MoaType> moaType = weightedList.getRandomValue(random);
        return moaType.orElse(moaTypeRegistry.get(AetherIIMoaTypes.BLUE));
    }
}