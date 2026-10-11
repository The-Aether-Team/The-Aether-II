package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.ArcticBurrukaiModel;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.BurrukaiBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.BurrukaiModel;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.*;
import com.aetherteam.aetherii.client.renderer.entity.model.taegore.TaegoreBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.taegore.TaegoreModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;

import java.util.function.Function;

public enum BiomeVariantPresets {
    HIGHFIELDS_TAEGORE(
            Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/taegore/highfields/taegore_highfields.png"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/taegore/highfields/taegore_highfields_baby.png"),
            (context) -> new TaegoreModel(context.bakeLayer(AetherIIModelLayers.HIGHFIELDS_TAEGORE)),
            (context) -> new TaegoreBabyModel(context.bakeLayer(AetherIIModelLayers.HIGHFIELDS_TAEGORE_BABY))),
    MAGNETIC_TAEGORE(
            Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/taegore/magnetic/taegore_magnetic.png"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/taegore/magnetic/taegore_magnetic_baby.png"),
            (context) -> new TaegoreModel(context.bakeLayer(AetherIIModelLayers.MAGNETIC_TAEGORE)),
            (context) -> new TaegoreBabyModel(context.bakeLayer(AetherIIModelLayers.MAGNETIC_TAEGORE_BABY))),
    ARCTIC_TAEGORE(
            Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/taegore/arctic/taegore_arctic.png"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/taegore/arctic/taegore_arctic_baby.png"),
            (context) -> new TaegoreModel(context.bakeLayer(AetherIIModelLayers.ARCTIC_TAEGORE)),
            (context) -> new TaegoreBabyModel(context.bakeLayer(AetherIIModelLayers.ARCTIC_TAEGORE_BABY))),

    HIGHFIELDS_BURRUKAI(
            Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/burrukai/highfields/burrukai_highfields.png"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/burrukai/highfields/burrukai_highfields_baby.png"),
            (context) -> new BurrukaiModel(context.bakeLayer(AetherIIModelLayers.HIGHFIELDS_BURRUKAI)),
            (context) -> new BurrukaiBabyModel(context.bakeLayer(AetherIIModelLayers.HIGHFIELDS_BURRUKAI_BABY))),
    MAGNETIC_BURRUKAI(
            Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/burrukai/magnetic/burrukai_magnetic.png"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/burrukai/magnetic/burrukai_magnetic_baby.png"),
            (context) -> new BurrukaiModel(context.bakeLayer(AetherIIModelLayers.MAGNETIC_BURRUKAI)),
            (context) -> new BurrukaiBabyModel(context.bakeLayer(AetherIIModelLayers.MAGNETIC_BURRUKAI_BABY))),
    ARCTIC_BURRUKAI(
            Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/burrukai/arctic/burrukai_arctic.png"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/burrukai/arctic/burrukai_arctic_baby.png"),
            (context) -> new ArcticBurrukaiModel(context.bakeLayer(AetherIIModelLayers.ARCTIC_BURRUKAI)),
            (context) -> new BurrukaiBabyModel(context.bakeLayer(AetherIIModelLayers.ARCTIC_BURRUKAI_BABY))),

    HIGHFIELDS_KIRRID(
            Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/highfields/kirrid_highfields.png"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/highfields/kirrid_highfields_baby.png"),
            (context) -> new HighfieldsKirridModel(context.bakeLayer(AetherIIModelLayers.HIGHFIELDS_KIRRID)),
            (context) -> new HighfieldsKirridBabyModel(context.bakeLayer(AetherIIModelLayers.HIGHFIELDS_KIRRID_BABY))),
    MAGNETIC_KIRRID(
            Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/magnetic/kirrid_magnetic.png"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/magnetic/kirrid_magnetic_baby.png"),
            (context) -> new MagneticKirridModel(context.bakeLayer(AetherIIModelLayers.MAGNETIC_KIRRID)),
            (context) -> new MagneticKirridBabyModel(context.bakeLayer(AetherIIModelLayers.MAGNETIC_KIRRID_BABY))),
    ARCTIC_KIRRID(
            Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/arctic/kirrid_arctic.png"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/arctic/kirrid_arctic_baby.png"),
            (context) -> new ArcticKirridModel(context.bakeLayer(AetherIIModelLayers.ARCTIC_KIRRID)),
            (context) -> new ArcticKirridBabyModel(context.bakeLayer(AetherIIModelLayers.ARCTIC_KIRRID_BABY)));

    private final Identifier defaultTexture;
    private final Identifier babyTexture;
    private final Function<EntityRendererProvider.Context, EntityModel<? extends EntityRenderState>> defaultModel;
    private final Function<EntityRendererProvider.Context, EntityModel<? extends EntityRenderState>> babyModel;

    BiomeVariantPresets(Identifier defaultTexture, Identifier babyTexture, Function<EntityRendererProvider.Context, EntityModel<?>> defaultModel, Function<EntityRendererProvider.Context, EntityModel<?>> babyModel) {
        this.defaultTexture = defaultTexture;
        this.babyTexture = babyTexture;
        this.defaultModel = defaultModel;
        this.babyModel = babyModel;
    }

    public Identifier getDefaultTexture() {
        return this.defaultTexture;
    }

    public Identifier getBabyTexture() {
        return this.babyTexture;
    }

    public EntityModel<? extends EntityRenderState> getDefaultModel(EntityRendererProvider.Context context) {
        return this.defaultModel.apply(context);
    }

    public EntityModel<? extends EntityRenderState> getBabyModel(EntityRendererProvider.Context context) {
        return this.babyModel.apply(context);
    }
}
