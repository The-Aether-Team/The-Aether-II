package com.aetherteam.aetherii.client.renderer.entity.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class AerbunnyAnimation {
        public static final AnimationDefinition SITTING = AnimationDefinition.Builder.withLength(0.0F)
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))

                .addAnimation("leg_front_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("leg_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(-10.0F, 40.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("leg_front_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("leg_front_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(-10.0F, -40.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("leg_back_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.2F, KeyframeAnimations.posVec(-0.5F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("leg_back_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.2F, KeyframeAnimations.posVec(0.5F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .build();
}
