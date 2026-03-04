package com.aetherteam.aetherii.client.renderer.entity.state;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class SkyrootLizardRenderState extends LivingEntityRenderState {
    public Identifier texture = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/skyroot_lizard/skyroot.png");
}
