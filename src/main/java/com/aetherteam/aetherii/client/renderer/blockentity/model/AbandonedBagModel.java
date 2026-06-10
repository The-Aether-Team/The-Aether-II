package com.aetherteam.aetherii.client.renderer.blockentity.model;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public class AbandonedBagModel extends Model<Float> {
	private final ModelPart lid;
	private final ModelPart main;

	public AbandonedBagModel(ModelPart root) {
		super(root, RenderTypes::entityCutout);
		this.lid = root.getChild("lid");
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition lid = partdefinition.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 11).addBox(-4.0F, -3.0F, -6.0F, 8.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(24, 20).addBox(-1.0F, -1.0F, -7.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.0F, 6.0F));

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(12, 20).addBox(4.0F, -4.0F, 2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition axe = main.addOrReplaceChild("axe", CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, -6.0F, -3.0F, 0.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.3491F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Float openness) {
		super.setupAnim(openness);
		this.lid.xRot = -(openness * ((float)Math.PI / 2F));
		//this.lock.xRot = this.lid.xRot;
	}
}