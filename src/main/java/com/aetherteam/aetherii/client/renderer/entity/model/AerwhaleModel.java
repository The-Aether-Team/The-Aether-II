package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.entity.passive.Aerwhale;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class AerwhaleModel extends HierarchicalModel<Aerwhale> {
    private final ModelPart root;
    private final ModelPart Aerwhale;
    private final ModelPart HeadandJaw;
    private final ModelPart Head;
    private final ModelPart Jaw;
    private final ModelPart Body;
    private final ModelPart Tail2;
    private final ModelPart Tail;
    private final ModelPart TailFin;
    private final ModelPart LeftFin;
    private final ModelPart RightFin;

    public AerwhaleModel(ModelPart root) {
        this.root = root;
        this.Aerwhale = root.getChild("Aerwhale");
        this.HeadandJaw = this.Aerwhale.getChild("HeadandJaw");
        this.Head = this.HeadandJaw.getChild("Head");
        this.Jaw = this.HeadandJaw.getChild("Jaw");
        this.Body = this.Aerwhale.getChild("Body");
        this.Tail2 = this.Aerwhale.getChild("Tail2");
        this.Tail = this.Tail2.getChild("Tail");
        this.TailFin = this.Tail2.getChild("TailFin");
        this.LeftFin = this.Aerwhale.getChild("LeftFin");
        this.RightFin = this.Aerwhale.getChild("RightFin");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Aerwhale = partdefinition.addOrReplaceChild("Aerwhale", CubeListBuilder.create(), PartPose.offset(-0.55F, 13.1F, -6.6F));

        PartDefinition HeadandJaw = Aerwhale.addOrReplaceChild("HeadandJaw", CubeListBuilder.create(), PartPose.offset(0.3F, -0.1F, -1.9F));

        PartDefinition Head = HeadandJaw.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-13.0F, -18.0F, -41.0F, 25.0F, 13.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.25F, 11.0F, 8.5F));

        PartDefinition Jaw = HeadandJaw.addOrReplaceChild("Jaw", CubeListBuilder.create(), PartPose.offset(-0.25F, -1.0F, -10.5F));

        PartDefinition Jaw_r1 = Jaw.addOrReplaceChild("Jaw_r1", CubeListBuilder.create().texOffs(82, 0).addBox(-13.5F, -2.0F, -23.0F, 27.0F, 9.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition Body = Aerwhale.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 45).addBox(-9.5F, -6.5F, -9.5F, 19.0F, 12.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.05F, -0.6F, 7.1F));

        PartDefinition DorsalFin_r1 = Body.addOrReplaceChild("DorsalFin_r1", CubeListBuilder.create().texOffs(24, 100).addBox(-1.5F, -7.0F, -3.0F, 3.0F, 14.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.5F, -11.5F, -0.5236F, 0.0F, 0.0F));

        PartDefinition Tail2 = Aerwhale.addOrReplaceChild("Tail2", CubeListBuilder.create(), PartPose.offset(-0.45F, -0.1F, 15.6F));

        PartDefinition Tail = Tail2.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(0, 100).addBox(-2.0F, -21.0F, 23.0F, 3.0F, 14.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 75).addBox(-7.0F, -16.0F, 9.0F, 13.0F, 8.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 11.0F, -9.0F));

        PartDefinition TailFin = Tail2.addOrReplaceChild("TailFin", CubeListBuilder.create().texOffs(40, 80).addBox(-19.5F, 0.0F, 0.0F, 39.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -1.0F, 18.0F));

        PartDefinition LeftFin = Aerwhale.addOrReplaceChild("LeftFin", CubeListBuilder.create(), PartPose.offset(9.55F, -1.1F, -6.4F));

        PartDefinition LeftFin_r1 = LeftFin.addOrReplaceChild("LeftFin_r1", CubeListBuilder.create().texOffs(97, 50).addBox(-3.0F, 0.0F, 0.0F, 27.0F, 1.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 1.0F, -4.0F, -0.1222F, -0.1745F, 0.6109F));

        PartDefinition RightFin = Aerwhale.addOrReplaceChild("RightFin", CubeListBuilder.create(), PartPose.offset(-8.45F, 0.9F, -6.4F));

        PartDefinition RightFin_r1 = RightFin.addOrReplaceChild("RightFin_r1", CubeListBuilder.create().texOffs(97, 32).addBox(-24.0F, 0.0F, 0.0F, 27.0F, 1.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, -4.0F, -0.1222F, 0.1745F, -0.6109F));

        return LayerDefinition.create(meshdefinition, 256, 128);
    }

    @Override
    public void setupAnim(Aerwhale aerwhale, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float glide = Mth.sin(ageInTicks * 0.08F) * 0.08F;
        this.Tail2.yRot = glide;
        this.Tail.yRot = glide * 1.5F;
        this.TailFin.yRot = glide * 2.0F;
        this.LeftFin.zRot += Mth.sin(ageInTicks * 0.05F) * 0.05F;
        this.RightFin.zRot -= Mth.sin(ageInTicks * 0.05F) * 0.05F;
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
