package us.ichun.mods.ichunutil.common.module.tabula.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.Entity;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.ProjectInfo;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.components.Animation;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.components.AnimationComponent;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.components.CubeGroup;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.components.CubeInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Model class used to render a ProjectInfo (Tabula format) in game. Not meant to be discarded by GC, no memory freeing is done in this class.
 * Meant to used on Projects which have a texture as well.
 * If you want to use a Model you can discard and recreate without leaking memory, use ModelBaseDummy in the same package.
 */
public class ModelTabula extends ModelBase
{

	public ArrayList<CubeInfo> cubes;

	public ModelTabula(ProjectInfo projectInfo)
	{
		this.textureHeight = projectInfo.textureHeight;
		this.textureWidth = projectInfo.textureWidth;

		this.cubes = new ArrayList<>();

		for (int i = 0; i < projectInfo.cubeGroups.size(); i++)
		{
			this.createGroupCubes(projectInfo.cubeGroups.get(i));
		}

		for (int i = 0; i < projectInfo.cubes.size(); i++)
		{
			projectInfo.cubes.get(i).createModel(this);
			this.cubes.add(projectInfo.cubes.get(i));
		}
	}

	private CubeInfo getFromName(String name)
	{
		for (CubeInfo info : this.cubes)
		{
			if (info.name.equals(name))
			{
				return info;
			}
		}

		return null;
	}

	@Override
	public void render(Entity ent, float f, float f1, float f2, float f3, float f4, float f5)
	{
		IAnimatedEntity animated = (IAnimatedEntity) ent;

		for (Animation anim : animated.getProjectInfo().anims)
		{
			for (Map.Entry<String, ArrayList<AnimationComponent>> e : anim.sets.entrySet())
			{
				for (CubeInfo cube : this.cubes)
				{
					if (cube.identifier.equals(e.getKey()))
					{
						ArrayList<AnimationComponent> components = e.getValue();

						Collections.sort(components);

						for (AnimationComponent comp : components)
						{
							if (!comp.hidden)
							{
								comp.animate(cube, anim.playTime);
							}
						}
					}
				}
			}
		}

		this.render(f5, false, false, 1F, 1F, 1F, 1F, animated.getProjectInfo());

		for (Animation anim : animated.getProjectInfo().anims)
		{
			for (Map.Entry<String, ArrayList<AnimationComponent>> e : anim.sets.entrySet())
			{
				for (CubeInfo cube : this.cubes)
				{
					if (cube.identifier.equals(e.getKey()))
					{
						ArrayList<AnimationComponent> components = e.getValue();

						Collections.sort(components);

						for (AnimationComponent comp : components)
						{
							if (!comp.hidden)
							{
								comp.reset(cube, anim.playTime);
							}
						}
					}
				}
			}
		}
	}

	public void render(float f5, boolean useTexture, boolean useOpacity, ProjectInfo projectInfo)
	{
		this.render(f5, useTexture, useOpacity, 1F, 1F, 1F, 1F, projectInfo);
	}

	public void render(float f5, boolean useTexture, boolean useOpacity, float r, float g, float b, float alpha, ProjectInfo projectInfo)
	{
		if (useTexture && projectInfo.bufferedTexture != null)
		{
			if (projectInfo.bufferedTextureId == -1)
			{
				projectInfo.bufferedTextureId = TextureUtil.uploadTextureImage(TextureUtil.glGenTextures(), projectInfo.bufferedTexture);
			}
			GlStateManager.bindTexture(projectInfo.bufferedTextureId);
		}

		GlStateManager.pushMatrix();

		GlStateManager.scale(1D / projectInfo.scale[0], 1D / projectInfo.scale[1], 1D / projectInfo.scale[2]);

		for (CubeInfo info : this.cubes)
		{
			if (info.modelCube != null && !info.hidden)
			{
				GlStateManager.pushMatrix();

				if (useOpacity)
				{
					GlStateManager.color(r, g, b, alpha * (float) (info.opacity / 100D));
				}

				GlStateManager.disableCull();
				GlStateManager.enableLighting();

				GlStateManager.translate(info.modelCube.offsetX, info.modelCube.offsetY, info.modelCube.offsetZ);
				GlStateManager.translate(info.modelCube.rotationPointX * f5, info.modelCube.rotationPointY * f5, info.modelCube.rotationPointZ * f5);
				GlStateManager.scale(info.scale[0], info.scale[1], info.scale[2]);
				GlStateManager.translate(-info.modelCube.offsetX, -info.modelCube.offsetY, -info.modelCube.offsetZ);
				GlStateManager.translate(-info.modelCube.rotationPointX * f5, -info.modelCube.rotationPointY * f5, -info.modelCube.rotationPointZ * f5);

				info.modelCube.render(f5);

				GlStateManager.disableLighting();

				GlStateManager.enableLighting();

				GlStateManager.popMatrix();
			}
		}

		GlStateManager.popMatrix();
	}

	private void createGroupCubes(CubeGroup group)
	{
		for (int i = 0; i < group.cubeGroups.size(); i++)
		{
			this.createGroupCubes(group.cubeGroups.get(i));
		}
		for (int i = 0; i < group.cubes.size(); i++)
		{
			group.cubes.get(i).createModel(this);
			this.cubes.add(group.cubes.get(i));
		}
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

}
