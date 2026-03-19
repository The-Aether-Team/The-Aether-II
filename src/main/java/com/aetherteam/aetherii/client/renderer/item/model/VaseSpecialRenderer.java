//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.client.renderer.blockentity.VaseRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Vector3fc;

import java.util.function.Consumer;

public class VaseSpecialRenderer implements NoDataSpecialModelRenderer {
    private final VaseRenderer vaseRenderer;

    public VaseSpecialRenderer(VaseRenderer vaseRenderer) {
        this.vaseRenderer = vaseRenderer;
    }


    public void submit(ItemDisplayContext p_440702_, PoseStack poseStack, SubmitNodeCollector nodeCollector, int p_439981_, int p_439623_, boolean p_440093_, int p_451684_) {
        this.vaseRenderer.submit(vaseRenderer.createRenderState(), poseStack, nodeCollector, new CameraRenderState());
    }

    public void getExtents(Consumer<Vector3fc> p_470724_) {
        this.vaseRenderer.getExtents(p_470724_);
    }


    public static record Unbaked() implements NoDataSpecialModelRenderer.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(new Unbaked());

        public Unbaked() {
        }

        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

        public NoDataSpecialModelRenderer bake(NoDataSpecialModelRenderer.BakingContext context) {
            return new VaseSpecialRenderer(new VaseRenderer(context));
        }
    }
}
