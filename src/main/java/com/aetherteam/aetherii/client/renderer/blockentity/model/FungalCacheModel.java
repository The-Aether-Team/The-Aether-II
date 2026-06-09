package com.aetherteam.aetherii.client.renderer.blockentity.model;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Mth;

public class FungalCacheModel extends Model<Float> {
	private final ModelPart petal_1;
	private final ModelPart petal_inner1;
	private final ModelPart petal_2;
	private final ModelPart petal_inner2;
	private final ModelPart petal_3;
	private final ModelPart petal_inner3;
	private final ModelPart petal_4;
	private final ModelPart petal_inner4;
	private final ModelPart bb_main;

	public FungalCacheModel(ModelPart root) {
		super(root, RenderTypes::entityCutoutCull);
		this.petal_1 = root.getChild("petal_1");
		this.petal_inner1 = this.petal_1.getChild("petal_inner1");
		this.petal_2 = root.getChild("petal_2");
		this.petal_inner2 = this.petal_2.getChild("petal_inner2");
		this.petal_3 = root.getChild("petal_3");
		this.petal_inner3 = this.petal_3.getChild("petal_inner3");
		this.petal_4 = root.getChild("petal_4");
		this.petal_inner4 = this.petal_4.getChild("petal_inner4");
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition petal_1 = partdefinition.addOrReplaceChild("petal_1", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, 23.0F, -3.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition petal_inner1 = petal_1.addOrReplaceChild("petal_inner1", CubeListBuilder.create().texOffs(24, 16).addBox(0.0F, -9.0F, 0.0F, 0.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

		PartDefinition cube_r1 = petal_inner1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(40, 16).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition petal_2 = partdefinition.addOrReplaceChild("petal_2", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, 23.0F, 3.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition petal_inner2 = petal_2.addOrReplaceChild("petal_inner2", CubeListBuilder.create().texOffs(24, 16).addBox(0.0F, -9.0F, 0.0F, 0.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

		PartDefinition cube_r2 = petal_inner2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(40, 16).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition petal_3 = partdefinition.addOrReplaceChild("petal_3", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, 23.0F, 3.0F, 0.0F, 2.3562F, 0.0F));

		PartDefinition petal_inner3 = petal_3.addOrReplaceChild("petal_inner3", CubeListBuilder.create().texOffs(24, 16).addBox(0.0F, -9.0F, 0.0F, 0.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

		PartDefinition cube_r3 = petal_inner3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(40, 16).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition petal_4 = partdefinition.addOrReplaceChild("petal_4", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, 23.0F, -3.0F, 0.0F, -2.3562F, 0.0F));

		PartDefinition petal_inner4 = petal_4.addOrReplaceChild("petal_inner4", CubeListBuilder.create().texOffs(24, 16).addBox(0.0F, -9.0F, 0.0F, 0.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

		PartDefinition cube_r4 = petal_inner4.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(40, 16).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -1.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition inside_r1 = bb_main.addOrReplaceChild("inside_r1", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -5.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Float openness) {
		super.setupAnim(openness);
		this.petal_inner1.zRot = -(openness * (Mth.PI / 3F));
		this.petal_inner2.zRot = -(openness * (Mth.PI / 3F));
		this.petal_inner3.zRot = -(openness * (Mth.PI / 3F));
		this.petal_inner4.zRot = -(openness * (Mth.PI / 3F));
	}
}