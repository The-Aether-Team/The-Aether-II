package com.aetherteam.aetherii.client.renderer.entity.model.kirrid;

import com.aetherteam.aetherii.client.renderer.entity.animation.KirridAnimations;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;

public abstract class AbstractKirridModel extends HierarchicalModel<Kirrid> {
    protected final ModelPart root;
    protected final ModelPart head;
    protected final ModelPart neck;
    public final ModelPart body;
    public final ModelPart wool;

    public AbstractKirridModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.wool = this.body.getChild("wool");
    }

    @Override
    public void setupAnim(Kirrid kirrid, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
        this.head.xRot = headPitch * (float) (Math.PI / 180.0);
        this.animate(kirrid.jumpAnimationState, KirridAnimations.JUMP, ageInTicks, 1.0F);
        this.animate(kirrid.ramAnimationState, KirridAnimations.START_RAM, ageInTicks, 1.0F);
        this.animate(kirrid.eatAnimationState, KirridAnimations.EAT, ageInTicks, 1.0F);
        if (!kirrid.jumpAnimationState.isStarted()) {
            this.animateWalk(KirridAnimations.WALK, limbSwing, limbSwingAmount, 2.0F, 2.0F);

        }
        this.wool.visible = !kirrid.isSheared();
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
