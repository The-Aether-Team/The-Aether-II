package com.aetherteam.aetherii.mixin;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.state.LightmapRenderState;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.ApiStatus;

public class ClientMixinHooks {
    @ApiStatus.Internal
    public static boolean RENDERING_ACCESSORY = false;
    @ApiStatus.Internal
    public static SoundInstance LAST_MUSIC = null;

    public static void adjustLightmapBrightness(LightmapRenderState renderState) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            if (player.level().getBiome(BlockPos.containing(player.getEyePosition())).is(AetherIITags.Biomes.CAVE_DARKNESS)) {
                float maxY = 100.0F;
                float minY = 75.0F;
                float range = maxY - minY;
                float multiplier = 1 / range;
                float heightModifier = (float) ((player.getEyeY() - minY) * multiplier);
                renderState.brightness = Math.max(renderState.brightness * Mth.clamp(heightModifier, -1.0F, 1.0F), -0.1F);
            }
        }
    }

    public static <T extends HumanoidRenderState> void positionMoaRider(T renderState, ModelPart head, ModelPart body, ModelPart rightArm, ModelPart leftArm, ModelPart rightLeg, ModelPart leftLeg) { //todo
        rightArm.xRot += -10.0F * Mth.DEG_TO_RAD;
        rightArm.zRot += -30.0F * Mth.DEG_TO_RAD;
        leftArm.xRot += -10.0F * Mth.DEG_TO_RAD;
        leftArm.zRot += 30.0F * Mth.DEG_TO_RAD;

//        rightLeg.xRot = -30.0F * Mth.DEG_TO_RAD;
//        rightLeg.zRot = 32.5F * Mth.DEG_TO_RAD;
//        leftLeg.xRot = -30.0F * Mth.DEG_TO_RAD;
//        leftLeg.zRot = -32.5F * Mth.DEG_TO_RAD;

        rightLeg.x -= 1;
        rightLeg.y -= 1;
        rightLeg.xRot += 40.0F * Mth.DEG_TO_RAD;
        rightLeg.yRot += 10.0F * Mth.DEG_TO_RAD;
        leftLeg.x += 1;
        leftLeg.y -= 1;
        leftLeg.xRot += 40.0F * Mth.DEG_TO_RAD;
        leftLeg.yRot -= 10.0F * Mth.DEG_TO_RAD;
    }
}
