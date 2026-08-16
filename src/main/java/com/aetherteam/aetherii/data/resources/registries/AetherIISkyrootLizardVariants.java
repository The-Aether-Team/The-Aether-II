package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.entity.variant.SkyrootLizardVariant;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;

import java.util.Objects;
import java.util.Optional;

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
        return ResourceKey.create(AetherIIRegistries.SKYROOT_LIZARD_VARIANT, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<SkyrootLizardVariant> context) {
        register(context, SKYROOT, AetherIIBlocks.SKYROOT_LEAVES);
        register(context, SKYPLANE, AetherIIBlocks.SKYPLANE_LEAVES);
        register(context, SKYBIRCH, AetherIIBlocks.SKYBIRCH_LEAVES);
        register(context, SKYPINE, AetherIIBlocks.SKYPINE_LEAVES);
        register(context, WISPROOT, AetherIIBlocks.WISPROOT_LEAVES);
        register(context, WISPTOP, AetherIIBlocks.WISPTOP_LEAVES);
        register(context, GREATROOT, AetherIIBlocks.GREATROOT_LEAVES);
        register(context, GREATOAK, AetherIIBlocks.GREATOAK_LEAVES);
        register(context, GREATBOA, AetherIIBlocks.GREATBOA_LEAVES);
        register(context, AMBEROOT, AetherIIBlocks.AMBEROOT_LEAVES);
    }

    private static void register(BootstrapContext<SkyrootLizardVariant> context, ResourceKey<SkyrootLizardVariant> key, Holder<Block> leafBlock) {
        context.register(key, new SkyrootLizardVariant(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/skyroot_lizard/" + key.identifier().getPath() + ".png"), leafBlock));
    }

    public static Holder<SkyrootLizardVariant> getVariantForLeaves(RegistryAccess registryAccess, Holder<Block> leafBlock) {
        Registry<SkyrootLizardVariant> registry = registryAccess.lookupOrThrow(AetherIIRegistries.SKYROOT_LIZARD_VARIANT);
        Optional<Holder.Reference<SkyrootLizardVariant>> optional = registry.listElements().filter((variant) -> variant.value().leafBlock().is(leafBlock)).findFirst().or(() -> registry.get(SKYROOT));
        Objects.requireNonNull(registry);
        return optional.or(registry::getAny).orElseThrow();
    }

    public static Holder<SkyrootLizardVariant> getRandomVariant(RandomSource randomSource, RegistryAccess registryAccess) {
        Registry<SkyrootLizardVariant> registry = registryAccess.lookupOrThrow(AetherIIRegistries.SKYROOT_LIZARD_VARIANT);
        Optional<Holder.Reference<SkyrootLizardVariant>> optional = registry.getRandom(randomSource);
        Objects.requireNonNull(registry);
        return optional.or(registry::getAny).orElseThrow();
    }
}
