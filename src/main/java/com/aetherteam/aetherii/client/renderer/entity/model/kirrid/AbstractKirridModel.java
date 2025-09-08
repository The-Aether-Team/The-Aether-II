package com.aetherteam.aetherii.client.renderer.entity.model.kirrid;

import com.aetherteam.aetherii.client.renderer.entity.animation.KirridAnimations;
import com.aetherteam.aetherii.client.renderer.entity.state.KirridRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;

public abstract class AbstractKirridModel extends EntityModel<KirridRenderState> {
    protected final ModelPart head;
    protected final ModelPart neck;
    public final ModelPart body;
    public final ModelPart wool;

    public AbstractKirridModel(ModelPart root) {
        super(root);
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
        this.animate(kirrid.jumpAnimationState, KirridAnimations.JUMP, kirrid.ageInTicks, 1.0F);
        this.animate(kirrid.ramAnimationState, KirridAnimations.START_RAM, kirrid.ageInTicks, 1.0F);
        this.animate(kirrid.eatAnimationState, KirridAnimations.EAT, kirrid.ageInTicks, 1.0F);
        if (!kirrid.jumpAnimationState.isStarted()) {
            this.animateWalk(KirridAnimations.WALK, kirrid.walkAnimationPos, kirrid.walkAnimationSpeed, 2.0F, 2.0F);

        }
        this.wool.visible = kirrid.wool;
    }
}
