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

		PartDefinition lid = partdefinition.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(42, 19).addBox(-1.0F, -5.0F, -2.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 19).addBox(-6.0F, -7.0F, -1.0F, 12.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -8.0F, -3.0F, 14.0F, 8.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-6.0F, -11.0F, -1.0F, 12.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));


		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public void setupAnim(Float openness) {
		super.setupAnim(openness);
		this.lid.xRot = -(openness * ((float)Math.PI / 2F));
	}
}