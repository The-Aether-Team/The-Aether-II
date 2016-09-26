package us.ichun.mods.ichunutil.common.module.tabula.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.ProjectInfo;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.components.Animation;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.components.AnimationComponent;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.components.CubeInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ModelTabulaNew extends ModelBase
{

    public ProjectInfo parent;
    public ArrayList<CubeInfo> cubes = new ArrayList<CubeInfo>();

    public ModelTabulaNew(ProjectInfo par)
    {
        parent = par;

        textureHeight = parent.textureHeight;
        textureWidth = parent.textureWidth;
    }

    @Override
    public void render(Entity ent, float f, float f1, float f2, float f3, float f4, float f5)
    {
        ArrayList<CubeInfo> allCubes = this.parent.getAllCubes();

        for(Animation anim : this.parent.anims)
        {
            for(Map.Entry<String, ArrayList<AnimationComponent>> e : anim.sets.entrySet())
            {
                for(CubeInfo cube : allCubes)
                {
                    if(cube.identifier.equals(e.getKey()))
                    {
                        ArrayList<AnimationComponent> components = e.getValue();
                        Collections.sort(components);

                        for(AnimationComponent comp : components)
                        {
                            if(!comp.hidden)
                            {
                                comp.animate(cube, anim.playTime + (anim.playing ? Minecraft.getMinecraft().getRenderPartialTicks() : 0));
                            }
                        }
                    }
                }
            }
        }

        this.render(0.0625F, 1);

        for(Animation anim : this.parent.anims)
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
        for(CubeInfo cube1 : childList)
        {
            if(cube1.equals(cube))
            {
                return true;
            }
            if(!flag)
            {
                flag = isChild(cube1.getChildren(), cube);
            }
        }
        return flag;
    }

    public ArrayList<CubeInfo> getParents(CubeInfo info) // in reverse order.
    {
        ArrayList<CubeInfo> parents = new ArrayList<CubeInfo>();

        for(CubeInfo cube : cubes)
        {
            addIfParent(parents, cube, info);
        }

        return parents;
    }

    public void addIfParent(List<CubeInfo> parents, CubeInfo parent, CubeInfo cube)
    {
        for(CubeInfo children : parent.getChildren())
        {
            addIfParent(parents, children, cube);
        }
        if(parent.getChildren().contains(cube) || !parents.isEmpty() && parent.getChildren().contains(parents.get(parents.size() - 1)))
        {
            parents.add(parent);
        }
    }

    public void render(float f5, int pass)
    {
        for(int i = cubes.size() - 1; i >= 0 ; i--)
        {
            CubeInfo info = cubes.get(i);

            if(info.modelCube != null)
            {
                if(pass == 1 && (!info.hidden))
                {
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 0.3F * (float)(info.opacity / 100D));

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
    }

    public void renderSelectedCube(CubeInfo info, float f5)
    {
        GlStateManager.disableLighting();
        GlStateManager.enableCull();

        ArrayList<CubeInfo> parents = getParents(info);

        GlStateManager.pushMatrix();

        if(parents.isEmpty())
        {
            GlStateManager.translate(info.modelCube.offsetX, info.modelCube.offsetY, info.modelCube.offsetZ);
            GlStateManager.translate(info.modelCube.rotationPointX * f5, info.modelCube.rotationPointY * f5, info.modelCube.rotationPointZ * f5);
            GlStateManager.scale(info.scale[0], info.scale[1], info.scale[2]);
            GlStateManager.translate(-info.modelCube.offsetX, -info.modelCube.offsetY, -info.modelCube.offsetZ);
            GlStateManager.translate(-info.modelCube.rotationPointX * f5, -info.modelCube.rotationPointY * f5, -info.modelCube.rotationPointZ * f5);
        }

        for(int i = parents.size() - 1; i >= 0; i--)
        {
            CubeInfo parent = parents.get(i);

            if(i == parents.size() - 1)
            {
                GlStateManager.translate(parent.modelCube.offsetX, parent.modelCube.offsetY, parent.modelCube.offsetZ);
                GlStateManager.translate(parent.modelCube.rotationPointX * f5, parent.modelCube.rotationPointY * f5, parent.modelCube.rotationPointZ * f5);
                GlStateManager.scale(parent.scale[0], parent.scale[1], parent.scale[2]);
                GlStateManager.translate(-parent.modelCube.offsetX, -parent.modelCube.offsetY, -parent.modelCube.offsetZ);
                GlStateManager.translate(-parent.modelCube.rotationPointX * f5, -parent.modelCube.rotationPointY * f5, -parent.modelCube.rotationPointZ * f5);
            }

            GlStateManager.translate(parent.modelCube.offsetX, parent.modelCube.offsetY, parent.modelCube.offsetZ);
            GlStateManager.translate(parent.modelCube.rotationPointX * f5, parent.modelCube.rotationPointY * f5, parent.modelCube.rotationPointZ * f5);

            if(parent.modelCube.rotateAngleZ != 0.0F)
            {
                GlStateManager.rotate(parent.modelCube.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
            }

            if(parent.modelCube.rotateAngleY != 0.0F)
            {
                GlStateManager.rotate(parent.modelCube.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
            }

            if(parent.modelCube.rotateAngleX != 0.0F)
            {
                GlStateManager.rotate(parent.modelCube.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
            }

        }

        GlStateManager.translate(info.modelCube.offsetX, info.modelCube.offsetY, info.modelCube.offsetZ);
        GlStateManager.translate(info.modelCube.rotationPointX * f5, info.modelCube.rotationPointY * f5, info.modelCube.rotationPointZ * f5);

        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();

        GlStateManager.disableCull();
        GlStateManager.enableLighting();

        //Render cube
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.90F * (float)(info.opacity / 100D));//to allow rendering of the rotation point internally
        if(info.parentIdentifier == null && (!info.hidden))//only render if it's not a child
        {
            GlStateManager.translate(info.modelCube.offsetX, info.modelCube.offsetY, info.modelCube.offsetZ);
            GlStateManager.translate(info.modelCube.rotationPointX * f5, info.modelCube.rotationPointY * f5, info.modelCube.rotationPointZ * f5);
            GlStateManager.scale(info.scale[0], info.scale[1], info.scale[2]);
            GlStateManager.translate(-info.modelCube.offsetX, -info.modelCube.offsetY, -info.modelCube.offsetZ);
            GlStateManager.translate(-info.modelCube.rotationPointX * f5, -info.modelCube.rotationPointY * f5, -info.modelCube.rotationPointZ * f5);
            info.modelCube.render(f5);
        }

        GlStateManager.disableLighting();

        GlStateManager.enableLighting();

        GlStateManager.popMatrix();
    }

    public void removeCubeInfo(CubeInfo info)
    {
        deleteModelDisplayList(info);
        cubes.remove(info);
    }

    private void deleteModelDisplayList(CubeInfo info)//Done to free up Graphics memory
    {
        for(CubeInfo info1 : info.getChildren())
        {
            deleteModelDisplayList(info1);
        }
        if(info.modelCube != null && info.modelCube.compiled)
        {
            GLAllocation.deleteDisplayLists(info.modelCube.displayList);
            info.modelCube = null;
        }
    }
}
