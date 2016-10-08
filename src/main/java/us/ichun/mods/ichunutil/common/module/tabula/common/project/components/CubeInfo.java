package us.ichun.mods.ichunutil.common.module.tabula.common.project.components;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.RandomStringUtils;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.ProjectInfo;

import java.util.ArrayList;

public class CubeInfo
{
	public CubeInfo(String name)
	{
		this.name = name;
		this.dimensions = new int[] { 1, 1, 1 };
		this.scale = new double[] { 1D, 1D, 1D };
		this.opacity = 100D;
		this.identifier = RandomStringUtils.randomAscii(ProjectInfo.IDENTIFIER_LENGTH);
	}

	public String name;

	public int[] dimensions = new int[3];

	public double[] position = new double[3];

	public double[] offset = new double[3];

	public double[] rotation = new double[3];

	public double[] scale = new double[3];

	public int[] txOffset = new int[2];

	public boolean txMirror = false;

	public double mcScale = 0.0D;

	public double opacity = 100D;

	public boolean hidden = false;

	public ArrayList<String> metadata = new ArrayList<>();

	private ArrayList<CubeInfo> children = new ArrayList<>();

	public String parentIdentifier;

	public String identifier;

	@SideOnly(Side.CLIENT)
	public transient ModelRenderer modelCube;

	public void addChild(CubeInfo info)
	{
		this.children.add(info);
		info.scale = new double[] { 1D, 1D, 1D };
		info.mcScale = 0.0D;
		info.opacity = this.opacity;
		info.parentIdentifier = this.identifier;
		info.hidden = false;
	}

	public void removeChild(CubeInfo info)
	{
		this.children.remove(info);
		if (info.parentIdentifier != null && info.parentIdentifier.equals(this.identifier))
		{
			info.parentIdentifier = null;
		}
	}

	public CubeInfo createModel(ModelBase base)
	{
		this.modelCube = new ModelRenderer(base, this.txOffset[0], this.txOffset[1]);
		this.modelCube.mirror = this.txMirror;
		this.modelCube.setRotationPoint((float) this.position[0], (float) this.position[1], (float) this.position[2]);
		this.modelCube.addBox((float) this.offset[0], (float) this.offset[1], (float) this.offset[2], this.dimensions[0], this.dimensions[1], this.dimensions[2], (float) this.mcScale);
		this.modelCube.rotateAngleX = (float) Math.toRadians(this.rotation[0]);
		this.modelCube.rotateAngleY = (float) Math.toRadians(this.rotation[1]);
		this.modelCube.rotateAngleZ = (float) Math.toRadians(this.rotation[2]);

		this.createChildren(base);

		return this;
	}

	public void createChildren(ModelBase base)
	{
		for (CubeInfo child : this.getChildren())
		{
			child.modelCube = new ModelRenderer(base, child.txOffset[0], child.txOffset[1]);
			child.modelCube.mirror = child.txMirror;
			child.modelCube.addBox((float) child.offset[0], (float) child.offset[1], (float) child.offset[2], child.dimensions[0], child.dimensions[1], child.dimensions[2]);
			child.modelCube.setRotationPoint((float) child.position[0], (float) child.position[1], (float) child.position[2]);
			child.modelCube.rotateAngleX = (float) Math.toRadians(child.rotation[0]);
			child.modelCube.rotateAngleY = (float) Math.toRadians(child.rotation[1]);
			child.modelCube.rotateAngleZ = (float) Math.toRadians(child.rotation[2]);

			this.modelCube.addChild(child.modelCube);

			child.createChildren(base);
		}
	}

	public ArrayList<CubeInfo> getChildren()
	{
		return this.children;
	}
}
