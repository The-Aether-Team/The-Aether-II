package com.aetherteam.aetherii.client.renderer.accessory;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface FirstPersonRendering {
    void renderOnFirstPerson(HumanoidArm arm, ItemStack stack, Player player, PoseStack poseStack, PlayerModel<?> model, MultiBufferSource buffer, int packedLight);
}
