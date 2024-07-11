package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.BurrukaiBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.BurrukaiModel;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.*;
import com.aetherteam.aetherii.client.renderer.entity.model.taegore.TaegoreBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.taegore.TaegoreModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.function.Function;

public enum BiomeVariantPresets {
    HIGHFIELDS_TAEGORE(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/taegore/highfields/taegore_highfields.png"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/taegore/highfields/taegore_highfields_baby.png"),
            (context) -> new TaegoreModel(context.bakeLayer(AetherIIModelLayers.HIGHFIELDS_TAEGORE)),
            (context) -> new TaegoreBabyModel(context.bakeLayer(AetherIIModelLayers.HIGHFIELDS_TAEGORE_BABY))),
    MAGNETIC_TAEGORE(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/taegore/magnetic/taegore_magnetic.png"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/taegore/magnetic/taegore_magnetic_baby.png"),
            (context) -> new TaegoreModel(context.bakeLayer(AetherIIModelLayers.MAGNETIC_TAEGORE)),
            (context) -> new TaegoreBabyModel(context.bakeLayer(AetherIIModelLayers.MAGNETIC_TAEGORE_BABY))),
    ARCTIC_TAEGORE(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/taegore/arctic/taegore_arctic.png"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/taegore/arctic/taegore_arctic_baby.png"),
            (context) -> new TaegoreModel(context.bakeLayer(AetherIIModelLayers.ARCTIC_TAEGORE)),
            (context) -> new TaegoreBabyModel(context.bakeLayer(AetherIIModelLayers.ARCTIC_TAEGORE_BABY))),

    HIGHFIELDS_BURRUKAI(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/burrukai/highfields/burrukai_highfields.png"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/burrukai/highfields/burrukai_highfields_baby.png"),
            (context) -> new BurrukaiModel(context.bakeLayer(AetherIIModelLayers.HIGHFIELDS_BURRUKAI)),
            (context) -> new BurrukaiBabyModel(context.bakeLayer(AetherIIModelLayers.HIGHFIELDS_BURRUKAI_BABY))),
    MAGNETIC_BURRUKAI(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/burrukai/magnetic/burrukai_magnetic.png"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/burrukai/magnetic/burrukai_magnetic_baby.png"),
            (context) -> new BurrukaiModel(context.bakeLayer(AetherIIModelLayers.MAGNETIC_BURRUKAI)),
            (context) -> new BurrukaiBabyModel(context.bakeLayer(AetherIIModelLayers.MAGNETIC_BURRUKAI_BABY))),
    ARCTIC_BURRUKAI(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/burrukai/arctic/burrukai_arctic.png"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/burrukai/arctic/burrukai_arctic_baby.png"),
            (context) -> new BurrukaiModel(context.bakeLayer(AetherIIModelLayers.ARCTIC_BURRUKAI)),
            (context) -> new BurrukaiBabyModel(context.bakeLayer(AetherIIModelLayers.ARCTIC_BURRUKAI_BABY))),

    HIGHFIELDS_KIRRID(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/highfields/kirrid_highfields.png"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/highfields/kirrid_highfields_baby.png"),
            (context) -> new HighfieldsKirridModel(context.bakeLayer(AetherIIModelLayers.HIGHFIELDS_KIRRID)),
            (context) -> new HighfieldsKirridBabyModel(context.bakeLayer(AetherIIModelLayers.HIGHFIELDS_KIRRID_BABY))),
    MAGNETIC_KIRRID(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/magnetic/kirrid_magnetic.png"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/magnetic/kirrid_magnetic_baby.png"),
            (context) -> new MagneticKirridModel(context.bakeLayer(AetherIIModelLayers.MAGNETIC_KIRRID)),
            (context) -> new MagneticKirridBabyModel(context.bakeLayer(AetherIIModelLayers.MAGNETIC_KIRRID_BABY))),
    ARCTIC_KIRRID(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/arctic/kirrid_arctic.png"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/arctic/kirrid_arctic_baby.png"),
            (context) -> new ArcticKirridModel(context.bakeLayer(AetherIIModelLayers.ARCTIC_KIRRID)),
            (context) -> new ArcticKirridBabyModel(context.bakeLayer(AetherIIModelLayers.ARCTIC_KIRRID_BABY)));

    private final ResourceLocation defaultTexture;
    private final ResourceLocation babyTexture;
    private final Function<EntityRendererProvider.Context, HierarchicalModel<? extends Entity>> defaultModel;
    private final Function<EntityRendererProvider.Context, HierarchicalModel<? extends Entity>> babyModel;

    BiomeVariantPresets(ResourceLocation defaultTexture, ResourceLocation babyTexture, Function<EntityRendererProvider.Context, HierarchicalModel<?>> defaultModel, Function<EntityRendererProvider.Context, HierarchicalModel<?>> babyModel) {
        this.defaultTexture = defaultTexture;
        this.babyTexture = babyTexture;
        this.defaultModel = defaultModel;
        this.babyModel = babyModel;
    }

    public ResourceLocation getDefaultTexture() {
        return this.defaultTexture;
    }

    public ResourceLocation getBabyTexture() {
        return this.babyTexture;
    }

    public HierarchicalModel<? extends Entity> getDefaultModel(EntityRendererProvider.Context context) {
        return this.defaultModel.apply(context);
    }

    public HierarchicalModel<? extends Entity> getBabyModel(EntityRendererProvider.Context context) {
        return this.babyModel.apply(context);
    }
}
