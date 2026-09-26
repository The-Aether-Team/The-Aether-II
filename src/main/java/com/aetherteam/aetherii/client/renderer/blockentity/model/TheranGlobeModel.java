package com.aetherteam.aetherii.client.renderer.blockentity.model;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public class TheranGlobeModel extends Model<Float> {
	//private final ModelPart globe;
	private final ModelPart main;

	public TheranGlobeModel(ModelPart root) {
        super(root, RenderTypes::entityCutout);
       // this.globe = root.getChild("globe");
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition globe = partdefinition.addOrReplaceChild("globe", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-2.0F, -5.0F, -5.0F, 4.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(32, 9).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-8.0F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public void setupAnim(Float globeRotation) {
		super.setupAnim(globeRotation);
		//this.globe.yRot = globeRotation;
	}
}