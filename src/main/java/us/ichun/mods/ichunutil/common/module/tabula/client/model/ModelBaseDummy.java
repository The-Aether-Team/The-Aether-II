package us.ichun.mods.ichunutil.common.module.tabula.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import us.ichun.mods.ichunutil.common.module.tabula.client.wrappers.ModelRendererAccessor;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.ProjectInfo;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.components.Animation;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.components.AnimationComponent;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.components.CubeInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ModelBaseDummy extends ModelBase
{
	public ProjectInfo parent;

	public ArrayList<CubeInfo> cubes = new ArrayList<>();

	public ModelRotationPoint rotationPoint;

	public ModelSizeControls sizeControls;

	public ModelRotationControls rotationControls;

	public ModelBaseDummy(ProjectInfo par)
	{
		this.parent = par;
		this.textureHeight = this.parent.textureHeight;
		this.textureWidth = this.parent.textureWidth;

		this.rotationPoint = new ModelRotationPoint();
		this.sizeControls = new ModelSizeControls();
		this.rotationControls = new ModelRotationControls();
	}

	@Override
	public void render(Entity ent, float f, float f1, float f2, float f3, float f4, float f5)
	{
		List<CubeInfo> empty = Collections.emptyList();

		ArrayList<CubeInfo> allCubes = this.parent.getAllCubes();

		for (Animation anim : this.parent.anims)
		{
			for (Map.Entry<String, ArrayList<AnimationComponent>> e : anim.sets.entrySet())
			{
				for (CubeInfo cube : allCubes)
				{
					if (cube.identifier.equals(e.getKey()))
					{
						List<AnimationComponent> components = e.getValue();

						Collections.sort(components);

						for (AnimationComponent comp : components)
						{
							if (!comp.hidden)
							{
								comp.animate(cube, anim.playTime + (anim.playing ? Minecraft.getMinecraft().getRenderPartialTicks() : 0));
							}
						}
					}
				}
			}
		}

		this.render(0.0625F, empty, empty, 1, false, 0, false, false);

		for (Animation anim : this.parent.anims)
		{
			for (Map.Entry<String, ArrayList<AnimationComponent>> e : anim.sets.entrySet())
			{
				for (CubeInfo cube : allCubes)
				{
					if (cube.identifier.equals(e.getKey()))
					{
						ArrayList<AnimationComponent> components = e.getValue();

						Collections.sort(components);

						for (AnimationComponent comp : components)
						{
							if (!comp.hidden)
							{
								comp.reset(cube, anim.playTime + (anim.playing ? Minecraft.getMinecraft().getRenderPartialTicks() : 0));
							}
						}
					}
				}
			}
		}
	}

	public boolean isChild(List<CubeInfo> childList, CubeInfo cube)
	{
		boolean flag = false;
		for (CubeInfo cube1 : childList)
		{
			if (cube1.equals(cube))
			{
				return true;
			}
			if (!flag)
			{
				flag = this.isChild(cube1.getChildren(), cube);
			}
		}
		return flag;
	}

	public ArrayList<CubeInfo> getParents(CubeInfo info) // in reverse order.
	{
		ArrayList<CubeInfo> parents = new ArrayList<>();

		for (CubeInfo cube : this.cubes)
		{
			this.addIfParent(parents, cube, info);
		}

		return parents;
	}

	public void addIfParent(List<CubeInfo> parents, CubeInfo parent, CubeInfo cube)
	{
		for (CubeInfo children : parent.getChildren())
		{
			this.addIfParent(parents, children, cube);
		}
		if (parent.getChildren().contains(cube) || !parents.isEmpty() && parent.getChildren().contains(parents.get(parents.size() - 1)))
		{
			parents.add(parent);
		}
	}

	public void render(float f5, List<CubeInfo> cubesToSelect, List<CubeInfo> cubesToHide, float zoomLevel, boolean hasTexture, int pass, boolean renderRotationPoint, boolean renderControls)
	{
		ArrayList<CubeInfo> cubesToRender = new ArrayList<>(cubesToSelect);
		ArrayList<CubeInfo> unrendered = new ArrayList<>(cubesToSelect);
		unrendered.removeAll(this.cubes);
		if (!unrendered.isEmpty())
		{
			for (CubeInfo cube : unrendered)
			{
				for (int i = this.cubes.size() - 1; i >= 0; i--)
				{
					if (this.isChild(this.cubes.get(i).getChildren(), cube))
					{
						cubesToRender.add(this.cubes.get(i));
					}
				}
			}
		}
		for (int i = this.cubes.size() - 1; i >= 0; i--)
		{
			CubeInfo info = this.cubes.get(i);
			if (info.modelCube != null)
			{
				if (cubesToRender.isEmpty() && pass == 1 && (!info.hidden && !cubesToHide.contains(info)))
				{
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F * (float) (info.opacity / 100D));

					GlStateManager.pushMatrix();
					GlStateManager.translate(info.modelCube.offsetX, info.modelCube.offsetY, info.modelCube.offsetZ);
					GlStateManager.translate(info.modelCube.rotationPointX * f5, info.modelCube.rotationPointY * f5, info.modelCube.rotationPointZ * f5);
					GlStateManager.scale(info.scale[0], info.scale[1], info.scale[2]);
					GlStateManager.translate(-info.modelCube.offsetX, -info.modelCube.offsetY, -info.modelCube.offsetZ);
					GlStateManager.translate(-info.modelCube.rotationPointX * f5, -info.modelCube.rotationPointY * f5, -info.modelCube.rotationPointZ * f5);

					info.modelCube.render(f5);

					GlStateManager.popMatrix();
				}
				else if (cubesToRender.contains(info))
				{
					if (pass == 0)
					{
						if (cubesToRender.size() == 1)
						{
							GlStateManager.color(0.0F, 0.0F, 0.7F, 0.8F);
						}
						else
						{
							int clr = Math.abs(info.identifier.hashCode()) & 0xffffff;
							float r = (clr >> 16 & 0xff) / 255.0F;
							float g = (clr >> 8 & 0xff) / 255.0F;
							float b = (clr & 0xff) / 255.0F;
							GlStateManager.color(r, g, b, 0.8F);
						}

						this.renderSelectedCube(info, cubesToHide, f5, zoomLevel, hasTexture, unrendered.contains(info) || cubesToSelect.contains(info), renderRotationPoint, renderControls);
					}
				}
				else if (pass == 1 && (!info.hidden && !cubesToHide.contains(info)))
				{
					GlStateManager.color(1.0F, 1.0F, 1.0F, 0.3F * (float) (info.opacity / 100D));

					GlStateManager.pushMatrix();
					GlStateManager.translate(info.modelCube.offsetX, info.modelCube.offsetY, info.modelCube.offsetZ);
					GlStateManager.translate(info.modelCube.rotationPointX * f5, info.modelCube.rotationPointY * f5, info.modelCube.rotationPointZ * f5);
					GlStateManager.scale(info.scale[0], info.scale[1], info.scale[2]);
					GlStateManager.translate(-info.modelCube.offsetX, -info.modelCube.offsetY, -info.modelCube.offsetZ);
					GlStateManager.translate(-info.modelCube.rotationPointX * f5, -info.modelCube.rotationPointY * f5, -info.modelCube.rotationPointZ * f5);

					info.modelCube.render(f5);

					GlStateManager.popMatrix();
				}
			}
		}
		for (CubeInfo info : unrendered)
		{
			if (pass == 0)
			{
				if (cubesToRender.size() == 1)
				{
					GlStateManager.color(0.0F, 0.0F, 0.7F, 0.8F);
				}
				else
				{
					int clr = Math.abs(info.identifier.hashCode()) & 0xffffff;
					float r = (clr >> 16 & 0xff) / 255.0F;
					float g = (clr >> 8 & 0xff) / 255.0F;
					float b = (clr & 0xff) / 255.0F;
					GlStateManager.color(r, g, b, 0.8F);
				}

				this.renderSelectedCube(info, cubesToHide, f5, zoomLevel, hasTexture, unrendered.contains(info) || cubesToSelect.contains(info), renderRotationPoint, renderControls);
			}
		}
	}

	public void renderSelectedCube(CubeInfo info, List<CubeInfo> hidden, float f5, float zoomLevel, boolean hasTexture, boolean focus, boolean renderRotationPoint, boolean renderControls)
	{
		if (hasTexture)
		{
			GlStateManager.disableTexture2D();
		}
		GlStateManager.disableLighting();
		GlStateManager.enableCull();

		ArrayList<CubeInfo> parents = this.getParents(info);

		GlStateManager.pushMatrix();

		if (parents.isEmpty())
		{
			GlStateManager.translate(info.modelCube.offsetX, info.modelCube.offsetY, info.modelCube.offsetZ);
			GlStateManager.translate(info.modelCube.rotationPointX * f5, info.modelCube.rotationPointY * f5, info.modelCube.rotationPointZ * f5);
			GlStateManager.scale(info.scale[0], info.scale[1], info.scale[2]);
			GlStateManager.translate(-info.modelCube.offsetX, -info.modelCube.offsetY, -info.modelCube.offsetZ);
			GlStateManager.translate(-info.modelCube.rotationPointX * f5, -info.modelCube.rotationPointY * f5, -info.modelCube.rotationPointZ * f5);
		}

		for (int i = parents.size() - 1; i >= 0; i--)
		{
			CubeInfo parent = parents.get(i);

			if (i == parents.size() - 1)
			{
				GlStateManager.translate(parent.modelCube.offsetX, parent.modelCube.offsetY, parent.modelCube.offsetZ);
				GlStateManager.translate(parent.modelCube.rotationPointX * f5, parent.modelCube.rotationPointY * f5, parent.modelCube.rotationPointZ * f5);
				GlStateManager.scale(parent.scale[0], parent.scale[1], parent.scale[2]);
				GlStateManager.translate(-parent.modelCube.offsetX, -parent.modelCube.offsetY, -parent.modelCube.offsetZ);
				GlStateManager.translate(-parent.modelCube.rotationPointX * f5, -parent.modelCube.rotationPointY * f5, -parent.modelCube.rotationPointZ * f5);
			}

			GlStateManager.translate(parent.modelCube.offsetX, parent.modelCube.offsetY, parent.modelCube.offsetZ);
			GlStateManager.translate(parent.modelCube.rotationPointX * f5, parent.modelCube.rotationPointY * f5, parent.modelCube.rotationPointZ * f5);

			if (parent.modelCube.rotateAngleZ != 0.0F)
			{
				GlStateManager.rotate(parent.modelCube.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
			}

			if (parent.modelCube.rotateAngleY != 0.0F)
			{
				GlStateManager.rotate(parent.modelCube.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
			}

			if (parent.modelCube.rotateAngleX != 0.0F)
			{
				GlStateManager.rotate(parent.modelCube.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
			}

		}

		GlStateManager.translate(info.modelCube.offsetX, info.modelCube.offsetY, info.modelCube.offsetZ);
		GlStateManager.translate(info.modelCube.rotationPointX * f5, info.modelCube.rotationPointY * f5, info.modelCube.rotationPointZ * f5);

		if (focus)
		{
			if (renderRotationPoint)
			{
				this.rotationPoint.render(f5);
			}
			if (renderControls)
			{
				GlStateManager.pushMatrix();

				if (info.modelCube.rotateAngleZ != 0.0F)
				{
					GlStateManager.rotate(info.modelCube.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
				}

				if (info.modelCube.rotateAngleY != 0.0F)
				{
					GlStateManager.rotate(info.modelCube.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
				}

				if (info.modelCube.rotateAngleX != 0.0F)
				{
					GlStateManager.rotate(info.modelCube.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
				}

				if (!GuiScreen.isShiftKeyDown())
				{
					GlStateManager.pushMatrix();
					float scale = 0.75F;
					GlStateManager.scale(scale, scale, scale);
					GlStateManager.rotate(90F, 1.0F, 0.0F, 0.0F);
					GlStateManager.rotate(90F, 0.0F, 0.0F, 1.0F);
					this.rotationControls.render(f5);
					GlStateManager.rotate(90F, 0.0F, 0.0F, -1.0F);
					this.rotationControls.render(f5);
					GlStateManager.rotate(90F, -1.0F, 0.0F, 0.0F);
					this.rotationControls.render(f5);
					GlStateManager.popMatrix();
				}
				else
				{
					GlStateManager.pushMatrix();
					float scale1 = 0.5F;
					GlStateManager.scale(scale1, scale1, scale1);

					GlStateManager.pushMatrix();
					GlStateManager.translate((0.125F * (info.dimensions[0] / 2D + info.offset[0])), (0.125D * (1D + (info.dimensions[1] + info.offset[1]) + info.mcScale)), (0.125F * (info.dimensions[2] / 2D + info.offset[2])));
					this.sizeControls.render(f5);
					GlStateManager.popMatrix();

					GlStateManager.pushMatrix();
					GlStateManager.translate((0.125F * (info.dimensions[0] / 2D + info.offset[0])), -(0.125D * (1D - (info.offset[1]) + info.mcScale)), (0.125F * (info.dimensions[2] / 2D + info.offset[2])));
					GlStateManager.rotate(180F, 0F, 0F, -1F);
					this.sizeControls.render(f5);
					GlStateManager.popMatrix();

					GlStateManager.pushMatrix();
					GlStateManager.translate((0.125F * (1D + info.dimensions[0] + info.offset[0] + info.mcScale)), (0.125D * ((info.dimensions[1] / 2D + info.offset[1]))), (0.125F * (info.dimensions[2] / 2D + info.offset[2])));
					GlStateManager.rotate(90F, 0F, 0F, -1F);
					this.sizeControls.render(f5);
					GlStateManager.popMatrix();

					GlStateManager.pushMatrix();
					GlStateManager.translate(-(0.125F * (1D - info.offset[0] + info.mcScale)), (0.125D * ((info.dimensions[1] / 2D + info.offset[1]))), (0.125F * (info.dimensions[2] / 2D + info.offset[2])));
					GlStateManager.rotate(90F, 0F, 0F, 1F);
					this.sizeControls.render(f5);
					GlStateManager.popMatrix();

					GlStateManager.pushMatrix();
					GlStateManager.translate((0.125F * (info.dimensions[0] / 2D + info.offset[0])), (0.125D * ((info.dimensions[1] / 2D + info.offset[1]))), (0.125F * (1D + info.dimensions[2] + info.offset[2] + info.mcScale)));
					GlStateManager.rotate(90F, 1F, 0F, 0F);
					this.sizeControls.render(f5);
					GlStateManager.popMatrix();

					GlStateManager.pushMatrix();
					GlStateManager.translate((0.125F * (info.dimensions[0] / 2D + info.offset[0])), (0.125D * ((info.dimensions[1] / 2D + info.offset[1]))), -(0.125F * (1D - info.offset[2] + info.mcScale)));
					GlStateManager.rotate(90F, -1F, 0F, 0F);
					this.sizeControls.render(f5);
					GlStateManager.popMatrix();

					GlStateManager.popMatrix();
				}

				GlStateManager.popMatrix();
			}
		}

		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();

		GlStateManager.disableCull();
		GlStateManager.enableLighting();
		if (hasTexture)
		{
			GlStateManager.enableTexture2D();
		}

		//Render cube
		GlStateManager.color(1.0F, 1.0F, 1.0F, 0.90F * (float) (info.opacity / 100D));//to allow rendering of the rotation point internally
		if (info.parentIdentifier == null && (!info.hidden && !hidden.contains(info)))//only render if it's not a child
		{
			GlStateManager.translate(info.modelCube.offsetX, info.modelCube.offsetY, info.modelCube.offsetZ);
			GlStateManager.translate(info.modelCube.rotationPointX * f5, info.modelCube.rotationPointY * f5, info.modelCube.rotationPointZ * f5);
			GlStateManager.scale(info.scale[0], info.scale[1], info.scale[2]);
			GlStateManager.translate(-info.modelCube.offsetX, -info.modelCube.offsetY, -info.modelCube.offsetZ);
			GlStateManager.translate(-info.modelCube.rotationPointX * f5, -info.modelCube.rotationPointY * f5, -info.modelCube.rotationPointZ * f5);
			info.modelCube.render(f5);
		}

		if (hasTexture)
		{
			GlStateManager.disableTexture2D();
		}
		GlStateManager.disableLighting();

		if (focus)
		{
			GlStateManager.pushMatrix();

			for (int i = parents.size() - 1; i >= 0; i--)
			{
				CubeInfo parent = parents.get(i);

				if (i == parents.size() - 1)
				{
					GlStateManager.translate(parent.modelCube.offsetX, parent.modelCube.offsetY, parent.modelCube.offsetZ);
					GlStateManager.translate(parent.modelCube.rotationPointX * f5, parent.modelCube.rotationPointY * f5, parent.modelCube.rotationPointZ * f5);
					GlStateManager.scale(parent.scale[0], parent.scale[1], parent.scale[2]);
					GlStateManager.translate(-parent.modelCube.offsetX, -parent.modelCube.offsetY, -parent.modelCube.offsetZ);
					GlStateManager.translate(-parent.modelCube.rotationPointX * f5, -parent.modelCube.rotationPointY * f5, -parent.modelCube.rotationPointZ * f5);
				}

				GlStateManager.translate(parent.modelCube.offsetX, parent.modelCube.offsetY, parent.modelCube.offsetZ);
				GlStateManager.translate(parent.modelCube.rotationPointX * f5, parent.modelCube.rotationPointY * f5, parent.modelCube.rotationPointZ * f5);

				if (parent.modelCube.rotateAngleZ != 0.0F)
				{
					GlStateManager.rotate(parent.modelCube.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
				}

				if (parent.modelCube.rotateAngleY != 0.0F)
				{
					GlStateManager.rotate(parent.modelCube.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
				}

				if (parent.modelCube.rotateAngleX != 0.0F)
				{
					GlStateManager.rotate(parent.modelCube.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
				}

			}

			GlStateManager.translate(info.modelCube.offsetX, info.modelCube.offsetY, info.modelCube.offsetZ);
			GlStateManager.translate(info.modelCube.rotationPointX * f5, info.modelCube.rotationPointY * f5, info.modelCube.rotationPointZ * f5);

			if (info.modelCube.rotateAngleZ != 0.0F)
			{
				GlStateManager.rotate(info.modelCube.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
			}

			if (info.modelCube.rotateAngleY != 0.0F)
			{
				GlStateManager.rotate(info.modelCube.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
			}

			if (info.modelCube.rotateAngleX != 0.0F)
			{
				GlStateManager.rotate(info.modelCube.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
			}
			GlStateManager.translate(info.offset[0] * f5, info.offset[1] * f5, info.offset[2] * f5);

			float width = 4F * zoomLevel;
			float border = width * f5 * 0.000625F;
			GL11.glLineWidth(width);
			GlStateManager.color(0.9F, 0.9F, 0.0F, 0.6F);
			GL11.glBegin(GL11.GL_LINES);

			GL11.glVertex3f(-border - (float) info.mcScale * f5, -(float) info.mcScale * f5, -(float) info.mcScale * f5);
			GL11.glVertex3f((float) (info.dimensions[0] + info.mcScale) * f5 + border, -(float) info.mcScale * f5, -(float) info.mcScale * f5);

			GL11.glVertex3f(-border - (float) info.mcScale * f5, (float) (info.dimensions[1] + info.mcScale) * f5, -(float) info.mcScale * f5);
			GL11.glVertex3f((float) (info.dimensions[0] + info.mcScale) * f5 + border, (float) (info.dimensions[1] + info.mcScale) * f5, -(float) info.mcScale * f5);

			GL11.glVertex3f(-border - (float) info.mcScale * f5, -(float) info.mcScale * f5, (float) (info.dimensions[2] + info.mcScale) * f5);
			GL11.glVertex3f((float) (info.dimensions[0] + info.mcScale) * f5 + border, -(float) info.mcScale * f5, (float) (info.dimensions[2] + info.mcScale) * f5);

			GL11.glVertex3f(-border - (float) info.mcScale * f5, (float) (info.dimensions[1] + info.mcScale) * f5, (float) (info.dimensions[2] + info.mcScale) * f5);
			GL11.glVertex3f((float) (info.dimensions[0] + info.mcScale) * f5 + border, (float) (info.dimensions[1] + info.mcScale) * f5, (float) (info.dimensions[2] + info.mcScale) * f5);

			GL11.glVertex3f(-(float) info.mcScale * f5, -(float) info.mcScale * f5, -border - (float) info.mcScale * f5);
			GL11.glVertex3f(-(float) info.mcScale * f5, -(float) info.mcScale * f5, (float) (info.dimensions[2] + info.mcScale) * f5 + border);

			GL11.glVertex3f(-(float) info.mcScale * f5, (float) (info.dimensions[1] + info.mcScale) * f5, -border - (float) info.mcScale * f5);
			GL11.glVertex3f(-(float) info.mcScale * f5, (float) (info.dimensions[1] + info.mcScale) * f5, (float) (info.dimensions[2] + info.mcScale) * f5 + border);

			GL11.glVertex3f((float) (info.dimensions[0] + info.mcScale) * f5, -(float) info.mcScale * f5, -border - (float) info.mcScale * f5);
			GL11.glVertex3f((float) (info.dimensions[0] + info.mcScale) * f5, -(float) info.mcScale * f5, (float) (info.dimensions[2] + info.mcScale) * f5 + border);

			GL11.glVertex3f((float) (info.dimensions[0] + info.mcScale) * f5, (float) (info.dimensions[1] + info.mcScale) * f5, -border - (float) info.mcScale * f5);
			GL11.glVertex3f((float) (info.dimensions[0] + info.mcScale) * f5, (float) (info.dimensions[1] + info.mcScale) * f5, (float) (info.dimensions[2] + info.mcScale) * f5 + border);

			GL11.glVertex3f(-(float) info.mcScale * f5, -border - (float) info.mcScale * f5, -(float) info.mcScale * f5);
			GL11.glVertex3f(-(float) info.mcScale * f5, (float) (info.dimensions[1] + info.mcScale) * f5 + border, -(float) info.mcScale * f5);

			GL11.glVertex3f((float) (info.dimensions[0] + info.mcScale) * f5, -border - (float) info.mcScale * f5, -(float) info.mcScale * f5);
			GL11.glVertex3f((float) (info.dimensions[0] + info.mcScale) * f5, (float) (info.dimensions[1] + info.mcScale) * f5 + border, -(float) info.mcScale * f5);

			GL11.glVertex3f(-(float) info.mcScale * f5, -border - (float) info.mcScale * f5, (float) (info.dimensions[2] + info.mcScale) * f5);
			GL11.glVertex3f(-(float) info.mcScale * f5, (float) (info.dimensions[1] + info.mcScale) * f5 + border, (float) (info.dimensions[2] + info.mcScale) * f5);

			GL11.glVertex3f((float) (info.dimensions[0] + info.mcScale) * f5, -border - (float) info.mcScale * f5, (float) (info.dimensions[2] + info.mcScale) * f5);
			GL11.glVertex3f((float) (info.dimensions[0] + info.mcScale) * f5, (float) (info.dimensions[1] + info.mcScale) * f5 + border, (float) (info.dimensions[2] + info.mcScale) * f5);

			GL11.glEnd();

			GlStateManager.popMatrix();
		}
		GlStateManager.enableLighting();
		if (hasTexture)
		{
			GlStateManager.enableTexture2D();
		}

		GlStateManager.popMatrix();
	}

	public void removeCubeInfo(CubeInfo info)
	{
		this.deleteModelDisplayList(info);
		this.cubes.remove(info);
	}

	private void deleteModelDisplayList(CubeInfo info)//Done to free up Graphics memory
	{
		for (CubeInfo info1 : info.getChildren())
		{
			this.deleteModelDisplayList(info1);
		}

		if (info.modelCube != null && ModelRendererAccessor.isCompiled(info.modelCube))
		{
			ModelRendererAccessor.deleteDisplayLists(info.modelCube);

			info.modelCube = null;
		}
	}
}
