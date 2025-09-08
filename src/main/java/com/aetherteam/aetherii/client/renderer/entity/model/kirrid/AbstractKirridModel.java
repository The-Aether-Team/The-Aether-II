package com.aetherteam.aetherii.client.renderer.entity.model.kirrid;

import com.aetherteam.aetherii.client.renderer.entity.animation.KirridAnimations;
import com.aetherteam.aetherii.client.renderer.entity.state.KirridRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;

public abstract class AbstractKirridModel extends EntityModel<KirridRenderState> {
    private final KeyframeAnimation jumpAnimation;
    private final KeyframeAnimation ramAnimation;
    private final KeyframeAnimation eatAnimation;
    private final KeyframeAnimation walkAnimation;
    protected final ModelPart head;
    protected final ModelPart neck;
    public final ModelPart body;
    public final ModelPart wool;

    public AbstractKirridModel(ModelPart root) {
        super(root);
        this.jumpAnimation = KirridAnimations.JUMP.bake(root);
        this.ramAnimation = KirridAnimations.START_RAM.bake(root);
        this.eatAnimation = KirridAnimations.EAT.bake(root);
        this.walkAnimation = KirridAnimations.WALK.bake(root);
        this.body = root.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.wool = this.body.getChild("wool");
    }

    @Override
    public void setupAnim(KirridRenderState kirrid) {
        super.setupAnim(kirrid);
        this.head.yRot = kirrid.yRot * (float) (Math.PI / 180.0);
        this.head.xRot = kirrid.xRot * (float) (Math.PI / 180.0);
        this.jumpAnimation.apply(kirrid.jumpAnimationState, kirrid.ageInTicks, 1.0F);
        this.ramAnimation.apply(kirrid.ramAnimationState, kirrid.ageInTicks, 1.0F);
        this.eatAnimation.apply(kirrid.eatAnimationState, kirrid.ageInTicks, 1.0F);
        if (!kirrid.jumpAnimationState.isStarted()) {
            this.walkAnimation.applyWalk(kirrid.walkAnimationPos, kirrid.walkAnimationSpeed, 2.0F, 2.0F);

        }
        this.wool.visible = kirrid.wool;
    }
}
