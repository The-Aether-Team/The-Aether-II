package com.aetherteam.aetherii.client.renderer.entity.model.bird;// Made with Blockbench 5.1.4

import com.aetherteam.aetherii.client.renderer.entity.state.BirdRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class PheasantModel extends EntityModel<BirdRenderState> {
	private final ModelPart Head;
	private final ModelPart Tails;
	private final ModelPart Wings;
	private final ModelPart bb_main;

	public PheasantModel(ModelPart root) {
		super(root);
		this.Head = root.getChild("Head");
		this.Tails = root.getChild("Tails");
		this.Wings = root.getChild("Wings");
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(5, 11).addBox(-1.5F, -2.25F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(38, 3).addBox(-1.5F, -6.25F, -1.5F, 3.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(40, 14).addBox(0.0F, -6.25F, -1.5F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(26, 29).addBox(-1.0F, -1.0F, -4.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.5F, -2.5F));

		PartDefinition Tails = partdefinition.addOrReplaceChild("Tails", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, 3.0F));

		PartDefinition Tail_Horizontal_r1 = Tails.addOrReplaceChild("Tail_Horizontal_r1", CubeListBuilder.create().texOffs(30, 33).addBox(-3.0F, -1.0F, -1.0F, 6.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.25F, 1.5F, 0.4363F, 0.0F, 0.0F));

		PartDefinition Wings = partdefinition.addOrReplaceChild("Wings", CubeListBuilder.create(), PartPose.offset(2.0F, 17.75F, 1.5F));

		PartDefinition Wing_Right_r1 = Wings.addOrReplaceChild("Wing_Right_r1", CubeListBuilder.create().texOffs(15, 24).addBox(-1.0F, -5.0F, -2.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, 1.3948F, -0.1289F, 0.0229F));

		PartDefinition Wing_Left_r1 = Wings.addOrReplaceChild("Wing_Left_r1", CubeListBuilder.create().texOffs(5, 24).addBox(0.0F, -5.0F, -2.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 1.3948F, 0.1289F, -0.0229F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(15, 35).addBox(0.25F, -5.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(9, 35).addBox(-1.25F, -5.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Body_r1 = bb_main.addOrReplaceChild("Body_r1", CubeListBuilder.create().texOffs(19, 9).addBox(-2.0F, -2.0F, -2.0F, 5.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -6.75F, -2.0F, 1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}