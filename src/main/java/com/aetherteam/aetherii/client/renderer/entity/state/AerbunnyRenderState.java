package com.aetherteam.aetherii.client.renderer.entity.state;

import com.aetherteam.aetherii.entity.EntityReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class AerbunnyRenderState extends TamableRenderState {
    public DyeColor collarColor = DyeColor.RED;
    public boolean isSitting;
    public float puffiness;
    public boolean onGround;
    public boolean isBaguchi;
    public Vec3 deltaMovement;
    public Optional<EntityReference<LivingEntity>> vehicleReference;
}
