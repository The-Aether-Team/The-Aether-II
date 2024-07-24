package com.aetherteam.aetherii.client.renderer.entity.model.kirrid;

import com.aetherteam.aetherii.client.renderer.entity.animation.KirridAnimations;
import com.aetherteam.aetherii.client.renderer.entity.animation.KirridBabyAnimations;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;

public abstract class AbstractKirridBabyModel extends HierarchicalModel<Kirrid> {
    protected final ModelPart root;
    protected final ModelPart body_main;
    protected final ModelPart wool;
    protected final ModelPart head;
    protected final ModelPart plate;
    protected final ModelPart ears;
    protected final ModelPart leg_b_l;
    protected final ModelPart leg_b_r;
    protected final ModelPart tail;
    protected final ModelPart leg_f_l;
    protected final ModelPart leg_f_r;

    public AbstractKirridBabyModel(ModelPart root) {
        this.root = root;
        this.body_main = root.getChild("body_main");
        this.wool = this.body_main.getChild("wool");
        this.head = this.body_main.getChild("head");
        this.plate = this.head.getChild("plate");
        this.ears = this.head.getChild("ears");
        this.leg_b_l = this.body_main.getChild("leg_b_l");
        this.leg_b_r = this.body_main.getChild("leg_b_r");
        this.tail = this.body_main.getChild("tail");
        this.leg_f_l = this.body_main.getChild("leg_f_l");
        this.leg_f_r = this.body_main.getChild("leg_f_r");
    }

    @Override
    public void setupAnim(Kirrid kirrid, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
        this.head.xRot = headPitch * (float) (Math.PI / 180.0);
        this.animate(kirrid.jumpAnimationState, KirridBabyAnimations.JUMP, ageInTicks, 1.0F);
        this.animate(kirrid.eatAnimationState, KirridBabyAnimations.EAT, ageInTicks, 1.0F);
        if (!kirrid.jumpAnimationState.isStarted()) {
            this.animateWalk(KirridAnimations.WALK, limbSwing, limbSwingAmount, 2.0F, 2.0F);

        }
        this.plate.visible = kirrid.hasPlate();
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
