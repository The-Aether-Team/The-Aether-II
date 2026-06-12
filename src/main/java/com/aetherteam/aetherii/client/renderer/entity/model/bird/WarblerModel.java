package com.aetherteam.aetherii.client.renderer.entity.model.bird;// Made with Blockbench 5.1.4

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class WarblerModel extends EntityModel<LivingEntityRenderState> {
	private final ModelPart Head;
	private final ModelPart Tails;
	private final ModelPart Wings;
	private final ModelPart bb_main;

	public WarblerModel(ModelPart root) {
        super(root);
        this.Head = root.getChild("Head");
		this.Tails = root.getChild("Tails");
		this.Wings = root.getChild("Wings");
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(3, 17).addBox(-2.0F, -2.25F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(22, 4).addBox(0.0F, -0.25F, -6.25F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(39, 27).addBox(-1.0F, -1.25F, -5.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(34, 13).addBox(-3.0F, -0.25F, -2.5F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.5F, -2.5F));

		PartDefinition Body_r1 = Head.addOrReplaceChild("Body_r1", CubeListBuilder.create().texOffs(3, 4).addBox(-2.0F, -2.0F, -1.0F, 5.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 4.75F, -0.25F, 1.5708F, 0.0F, 0.0F));

		PartDefinition Tails = partdefinition.addOrReplaceChild("Tails", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 2.0F));

		PartDefinition Tail_Horizontal_r1 = Tails.addOrReplaceChild("Tail_Horizontal_r1", CubeListBuilder.create().texOffs(43, 34).addBox(-1.0F, 0.0F, 0.25F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.25F, -0.25F, 1.2602F, 0.4503F, 0.982F));

		PartDefinition Tail_Horizontal_r2 = Tails.addOrReplaceChild("Tail_Horizontal_r2", CubeListBuilder.create().texOffs(37, 34).addBox(-1.0F, 0.0F, 0.25F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.25F, -0.25F, 1.1834F, 0.344F, 0.6711F));

		PartDefinition Tail_Horizontal_r3 = Tails.addOrReplaceChild("Tail_Horizontal_r3", CubeListBuilder.create().texOffs(31, 34).addBox(-1.0F, 0.0F, 0.25F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.25F, -0.25F, 1.0887F, 0.2129F, 0.3838F));

		PartDefinition Tail_Horizontal_r4 = Tails.addOrReplaceChild("Tail_Horizontal_r4", CubeListBuilder.create().texOffs(25, 34).addBox(-1.0F, 0.0F, 0.25F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -0.25F, -0.25F, 1.0821F, 0.1084F, 0.1897F));

		PartDefinition Tail_Horizontal_r5 = Tails.addOrReplaceChild("Tail_Horizontal_r5", CubeListBuilder.create().texOffs(-5, 34).addBox(-1.0F, 0.0F, 0.25F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.25F, -0.25F, 1.2602F, -0.4503F, -0.982F));

		PartDefinition Tail_Horizontal_r6 = Tails.addOrReplaceChild("Tail_Horizontal_r6", CubeListBuilder.create().texOffs(1, 34).addBox(-1.0F, 0.0F, 0.25F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.25F, -0.25F, 1.1834F, -0.344F, -0.6711F));

		PartDefinition Tail_Horizontal_r7 = Tails.addOrReplaceChild("Tail_Horizontal_r7", CubeListBuilder.create().texOffs(7, 34).addBox(-1.0F, 0.0F, 0.25F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.25F, -0.25F, 1.0887F, -0.2129F, -0.3838F));

		PartDefinition Tail_Horizontal_r8 = Tails.addOrReplaceChild("Tail_Horizontal_r8", CubeListBuilder.create().texOffs(13, 34).addBox(-1.0F, 0.0F, 0.25F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -0.25F, -0.25F, 1.0821F, -0.1084F, -0.1897F));

		PartDefinition Tail_Horizontal_r9 = Tails.addOrReplaceChild("Tail_Horizontal_r9", CubeListBuilder.create().texOffs(19, 34).addBox(-1.0F, 0.0F, 0.25F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, -0.25F, 1.0996F, 0.0F, 0.0F));

		PartDefinition Wings = partdefinition.addOrReplaceChild("Wings", CubeListBuilder.create(), PartPose.offset(2.0F, 21.75F, 1.5F));

		PartDefinition Wing_Right_r1 = Wings.addOrReplaceChild("Wing_Right_r1", CubeListBuilder.create().texOffs(31, 25).addBox(-1.0F, -5.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -0.5F, 0.0F, 1.3948F, -0.1289F, 0.0229F));

		PartDefinition Wing_Left_r1 = Wings.addOrReplaceChild("Wing_Left_r1", CubeListBuilder.create().texOffs(21, 25).addBox(0.0F, -5.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.5F, 0.0F, 1.3948F, 0.1289F, -0.0229F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(3, 27).addBox(0.25F, -2.0F, -2.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(11, 27).addBox(-1.25F, -2.0F, -2.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}