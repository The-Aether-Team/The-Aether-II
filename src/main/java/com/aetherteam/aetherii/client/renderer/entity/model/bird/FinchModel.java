package com.aetherteam.aetherii.client.renderer.entity.model.bird;// Made with Blockbench 5.1.4

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class FinchModel extends EntityModel<LivingEntityRenderState> {
	private final ModelPart bb_main;

	public FinchModel(ModelPart root) {
        super(root);
        this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(1, 7).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(10, 6).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(12, 12).addBox(-0.5F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition HeadCrestFront_r1 = bb_main.addOrReplaceChild("HeadCrestFront_r1", CubeListBuilder.create().texOffs(13, 17).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -6.0F, 0.0F, 1.5708F, 0.0F, -1.5708F));

		PartDefinition Tail_Vertical_r1 = bb_main.addOrReplaceChild("Tail_Vertical_r1", CubeListBuilder.create().texOffs(-1, 15).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.5F, 5.0F, 0.0F, -1.5708F, 1.5708F));

		PartDefinition Wing_Right_r1 = bb_main.addOrReplaceChild("Wing_Right_r1", CubeListBuilder.create().texOffs(22, 7).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -2.0F, 0.5F, 0.0896F, -0.2613F, -0.0118F));

		PartDefinition Wing_Left_r1 = bb_main.addOrReplaceChild("Wing_Left_r1", CubeListBuilder.create().texOffs(22, 4).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -2.0F, 0.5F, 0.0896F, 0.2613F, 0.0118F));

		PartDefinition HeadCrest_r1 = bb_main.addOrReplaceChild("HeadCrest_r1", CubeListBuilder.create().texOffs(-3, 1).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 1.0F, 3.1416F, 0.0F, -1.5708F));

		PartDefinition Tail_Horizontal_r1 = bb_main.addOrReplaceChild("Tail_Horizontal_r1", CubeListBuilder.create().texOffs(8, 1).addBox(-3.0F, 0.0F, -1.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -3.5F, 3.5F, 0.7854F, 0.0F, 0.0F));

		PartDefinition Beak_r1 = bb_main.addOrReplaceChild("Beak_r1", CubeListBuilder.create().texOffs(3, 12).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -2.25F, -1.25F, -0.5672F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}
}