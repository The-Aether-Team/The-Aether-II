package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.variant.SkyrootLizardVariant;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;

import java.util.Objects;
import java.util.Optional;

import static com.aetherteam.aetherii.util.RegistryObjectUtil.block;

public class AetherIISkyrootLizardVariants {
    public static final ResourceKey<SkyrootLizardVariant> SKYROOT = createKey("skyroot");
    public static final ResourceKey<SkyrootLizardVariant> SKYPLANE = createKey("skyplane");
    public static final ResourceKey<SkyrootLizardVariant> SKYBIRCH = createKey("skybirch");
    public static final ResourceKey<SkyrootLizardVariant> SKYPINE = createKey("skypine");
    public static final ResourceKey<SkyrootLizardVariant> WISPROOT = createKey("wisproot");
    public static final ResourceKey<SkyrootLizardVariant> WISPTOP = createKey("wisptop");
    public static final ResourceKey<SkyrootLizardVariant> GREATROOT = createKey("greatroot");
    public static final ResourceKey<SkyrootLizardVariant> GREATOAK = createKey("greatoak");
    public static final ResourceKey<SkyrootLizardVariant> GREATBOA = createKey("greatboa");
    public static final ResourceKey<SkyrootLizardVariant> AMBEROOT = createKey("amberoot");

    private static ResourceKey<SkyrootLizardVariant> createKey(String name) {
        return ResourceKey.create(AetherIIRegistries.SKYROOT_LIZARD_VARIANT, new ResourceLocation(AetherII.MODID, name));
    }

    public static void bootstrap(BootstapContext<SkyrootLizardVariant> context) {
        register(context, SKYROOT, block(AetherIIBlocks.SKYROOT_LEAVES));
        register(context, SKYPLANE, block(AetherIIBlocks.SKYPLANE_LEAVES));
        register(context, SKYBIRCH, block(AetherIIBlocks.SKYBIRCH_LEAVES));
        register(context, SKYPINE, block(AetherIIBlocks.SKYPINE_LEAVES));
        register(context, WISPROOT, block(AetherIIBlocks.WISPROOT_LEAVES));
        register(context, WISPTOP, block(AetherIIBlocks.WISPTOP_LEAVES));
        register(context, GREATROOT, block(AetherIIBlocks.GREATROOT_LEAVES));
        register(context, GREATOAK, block(AetherIIBlocks.GREATOAK_LEAVES));
        register(context, GREATBOA, block(AetherIIBlocks.GREATBOA_LEAVES));
        register(context, AMBEROOT, block(AetherIIBlocks.AMBEROOT_LEAVES));
    }

    private static void register(BootstapContext<SkyrootLizardVariant> context, ResourceKey<SkyrootLizardVariant> key, Holder<Block> leafBlock) {
        context.register(key, new SkyrootLizardVariant(new ResourceLocation(AetherII.MODID, "textures/entity/mobs/skyroot_lizard/" + key.location().getPath() + ".png"), leafBlock));
    }

    public static Holder<SkyrootLizardVariant> getVariantForLeaves(RegistryAccess registryAccess, Holder<Block> leafBlock) {
        Registry<SkyrootLizardVariant> registry = registryAccess.registryOrThrow(AetherIIRegistries.SKYROOT_LIZARD_VARIANT);
        Optional<Holder.Reference<SkyrootLizardVariant>> optional = registry.holders().filter((variant) -> Objects.equals(variant.value().leafBlock().value(), leafBlock.value())).findFirst().or(() -> registry.getHolder(SKYROOT));
        Objects.requireNonNull(registry);
        return optional.or(() -> registry.holders().findFirst()).orElseThrow();
    }

    public static Holder<SkyrootLizardVariant> getRandomVariant(RandomSource randomSource, RegistryAccess registryAccess) {
        Registry<SkyrootLizardVariant> registry = registryAccess.registryOrThrow(AetherIIRegistries.SKYROOT_LIZARD_VARIANT);
        Optional<Holder.Reference<SkyrootLizardVariant>> optional = registry.getRandom(randomSource);
        Objects.requireNonNull(registry);
        return optional.or(() -> registry.holders().findFirst()).orElseThrow();
    }
}
