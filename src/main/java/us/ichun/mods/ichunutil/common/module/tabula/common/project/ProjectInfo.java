package us.ichun.mods.ichunutil.common.module.tabula.common.project;

import com.gildedgames.aether.common.AetherCore;
import com.google.gson.Gson;
import us.ichun.mods.ichunutil.common.module.tabula.client.model.ModelInfo;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import us.ichun.mods.ichunutil.common.module.tabula.client.model.ModelBaseDummy;
import us.ichun.mods.ichunutil.common.module.tabula.client.model.ModelHelper;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.components.Animation;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.components.CubeGroup;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.components.CubeInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ProjectInfo
{
    public static final int IDENTIFIER_LENGTH = 20;
    public static final int PROJ_VERSION = 4;

    public transient String identifier;
    public transient File saveFile;
    public transient String saveFileMd5;
    public transient boolean saved;
    public transient boolean tampered;

    public transient int lastAutosave;
    public transient boolean autosaved;

    public transient float cameraFov = 30F;
    public transient float cameraZoom = 1.0F;
    public transient float cameraYaw;
    public transient float cameraPitch;
    public transient float cameraOffsetX;
    public transient float cameraOffsetY;

    public transient boolean ignoreNextImage;
    public transient File textureFile;
    public transient String textureFileMd5;

    public transient BufferedImage bufferedTexture;
    public transient int bufferedTextureId = -1;

    public transient boolean destroyed;

    public transient ArrayList<String> states;
    public transient int lastState;
    public transient int switchState;

    @SideOnly(Side.CLIENT)
    public transient ModelBaseDummy model;

    public transient ProjectInfo ghostModel;

    public String modelName;
    public String authorName;
    public int projVersion; //TODO if projVersion < current version, do file repairs and resave.

    public ArrayList<String> metadata;

    public int textureWidth = 64;
    public int textureHeight = 32;

    public double[] scale = new double[] { 1.0D, 1.0D, 1.0D };

    public ArrayList<CubeGroup> cubeGroups;
    public ArrayList<CubeInfo> cubes;

    public ArrayList<Animation> anims;

    public int cubeCount;

    public ProjectInfo()
    {
        modelName = "";
        authorName = "";
        metadata = new ArrayList<String>();
        cameraFov = 30F;
        cameraZoom = 1.0F;
        cubeGroups = new ArrayList<CubeGroup>();
        cubes = new ArrayList<CubeInfo>();
        anims = new ArrayList<Animation>();

        states = new ArrayList<String>();
        switchState = -1;
    }

    public ProjectInfo(String name, String author)
    {
        this();
        modelName = name;
        authorName = author;

        cubeGroups = new ArrayList<CubeGroup>();
        cubes = new ArrayList<CubeInfo>();
    }

    public Animation getAnimation(String name)
    {
        for (Animation anim : this.anims)
        {
            if (anim != null && anim.name.equals(name))
            {
                return anim;
            }
        }

        return null;
    }

    public String getAsJson()
    {
        //        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Gson gson = new Gson();

        return gson.toJson(this);
    }

    @SideOnly(Side.CLIENT)
    public void initClient()
    {
        model = new ModelBaseDummy(this);
//        model.textureWidth = textureWidth;
//        model.textureHeight = textureHeight;
        for(int i = 0; i < cubeGroups.size(); i++)
        {
            createGroupCubes(cubeGroups.get(i));
        }
        for(int i = 0 ; i < cubes.size(); i++)
        {
            model.cubes.add(cubes.get(i).createModel(model));
        }
    }

    @SideOnly(Side.CLIENT)
    public void destroy()
    {
        if(model != null && !destroyed)
        {
            destroyed = true;
            for(int i = model.cubes.size() - 1; i >= 0; i--)
            {
                model.removeCubeInfo(model.cubes.get(i));
            }
            model.rotationPoint.destroy();
            model.rotationControls.destroy();
            model.sizeControls.destroy();

            if(bufferedTextureId != -1)
            {
                TextureUtil.deleteTexture(bufferedTextureId);
            }
        }
        if(ghostModel != null)
        {
            ghostModel.destroy();
        }
    }

    public ArrayList<CubeInfo> getAllCubes()
    {
        ArrayList<CubeInfo> cubes = new ArrayList<CubeInfo>();
        addAllCubes(cubes, this.cubes);
        addAllCubesFromGroups(cubes, this.cubeGroups);
        return cubes;
    }

    public void addAllCubes(ArrayList<CubeInfo> list, ArrayList<CubeInfo> cubes)
    {
        list.addAll(cubes);
        for(CubeInfo cube : cubes)
        {
            addAllCubes(list, cube.getChildren());
        }
    }

    public void addAllCubesFromGroups(ArrayList<CubeInfo> list, ArrayList<CubeGroup> groups)
    {
        for(CubeGroup group : groups)
        {
            addAllCubes(list, group.cubes);
            addAllCubesFromGroups(list, group.cubeGroups);
        }
    }

    public void createNewGroup()
    {
        cubeGroups.add(new CubeGroup("Group"));
    }

    public void createNewCube()
    {
        cubeCount++;
        cubes.add(new CubeInfo("shape" + Integer.toString(cubeCount)));
    }

    public void createNewAnimation(String name, boolean loop)
    {
        Animation anim = new Animation(name);
        anim.loops = loop;

        anims.add(anim);
    }

    private void createGroupCubes(CubeGroup group)
    {
        for(int i = 0; i < group.cubeGroups.size(); i++)
        {
            createGroupCubes(group.cubeGroups.get(i));
        }
        for(int i = 0; i < group.cubes.size(); i++)
        {
            model.cubes.add(group.cubes.get(i).createModel(model));
        }
    }

    public Object getObjectByIdent(String ident)
    {
        Object obj = null;
        for(CubeInfo inf : cubes)
        {
            if(obj == null)
            {
                obj = findObjectInCube(ident, inf);
            }
        }
        for(CubeGroup group1 : cubeGroups)
        {
            if(obj == null)
            {
                obj = findObjectInGroup(ident, group1);
            }
        }
        return obj;
    }

    public Object findObjectInCube(String ident, CubeInfo cube)
    {
        if(cube.identifier.equals(ident))
        {
            return cube;
        }
        Object obj = null;
        for(CubeInfo inf : cube.getChildren())
        {
            if(obj == null)
            {
                obj = findObjectInCube(ident, inf);
            }
        }
        return obj;
    }

    public Object findObjectInGroup(String ident, CubeGroup group)
    {
        if(group.identifier.equals(ident))
        {
            return group;
        }
        Object obj = null;
        for(CubeInfo inf : group.cubes)
        {
            if(obj == null)
            {
                obj = findObjectInCube(ident, inf);
            }
        }
        for(CubeGroup group1 : group.cubeGroups)
        {
            if(obj == null)
            {
                obj = findObjectInGroup(ident, group1);
            }
        }
        return obj;
    }

    @SideOnly(Side.CLIENT)
    public boolean importModel(ModelInfo model, boolean texture)
    {
        for(Map.Entry<String, ModelRenderer> e : model.modelList.entrySet())
        {
            ModelRenderer rend = e.getValue();

            if(cubes.isEmpty())
            {
                textureHeight = (int)rend.textureHeight;
                textureWidth = (int)rend.textureWidth;
            }

            CubeInfo firstCreated = null;

            if(rend.cubeList.isEmpty())
            {
                CubeInfo info = new CubeInfo(e.getKey());

                info.dimensions[0] = info.dimensions[1] = info.dimensions[2] = 0;
                info.scale[0] = info.scale[1] = info.scale[2] = 1.0F;

                cubeCount++;
                cubes.add(info);
                firstCreated = info;
            }
            else
            {
                for(int j = 0; j < rend.cubeList.size(); j++)
                {
                    ModelBox box = (ModelBox)rend.cubeList.get(j);
                    CubeInfo info = ModelHelper.createCubeInfoFromModelBox(rend, box, box.boxName != null ? (box.boxName.substring(box.boxName.lastIndexOf(".") + 1)) : (e.getKey() + (rend.cubeList.size() == 1 ? "" : (" - " + j))));

                    cubeCount++;
                    cubes.add(info);
                    if(firstCreated == null)
                    {
                        firstCreated = info;
                    }
                }
            }
            if(firstCreated != null)
            {
                createChildren(e.getKey(), rend, firstCreated);
            }
        }
        if(texture)
        {
            if(model.texture != null)
            {
                InputStream inputstream = null;
                try
                {
                    IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(model.texture);
                    inputstream = iresource.getInputStream();
                    bufferedTexture = ImageIO.read(inputstream);

                    if(bufferedTexture != null)
                    {
                        for(Map.Entry<String, ModelRenderer> e : model.modelList.entrySet())
                        {
                            ModelRenderer rend = e.getValue();

                            textureHeight = (int)rend.textureHeight;
                            textureWidth = (int)rend.textureWidth;

                            break;
                        }
                        return true;
                    }
                }
                catch(Exception e)
                {

                }
            }
            else
            {
                bufferedTexture = null;
                return true;
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void createChildren(String name, ModelRenderer rend, CubeInfo info)
    {
        if(rend.childModels == null)
        {
            return;
        }
        for(int i = 0; i < rend.childModels.size(); i++)
        {
            CubeInfo firstCreated = null;
            ModelRenderer rend1 = (ModelRenderer)rend.childModels.get(i);
            for(int j = 0; j < rend1.cubeList.size(); j++)
            {
                String fullName = name + (rend1.cubeList.size() == 1 ? "Child" : ("Child - " + j));
                ModelBox box = (ModelBox)rend1.cubeList.get(j);
                if(box.boxName != null)
                {
                    fullName = box.boxName.substring(box.boxName.lastIndexOf(".") + 1);
                }
                CubeInfo info1 = new CubeInfo(fullName);

                info1.dimensions[0] = (int)Math.abs(box.posX2 - box.posX1);
                info1.dimensions[1] = (int)Math.abs(box.posY2 - box.posY1);
                info1.dimensions[2] = (int)Math.abs(box.posZ2 - box.posZ1);

                info1.position[0] = rend1.rotationPointX;
                info1.position[1] = rend1.rotationPointY;
                info1.position[2] = rend1.rotationPointZ;

                info1.offset[0] = box.posX1;
                info1.offset[1] = box.posY1;
                info1.offset[2] = box.posZ1;

                info1.rotation[0] = Math.toDegrees(rend1.rotateAngleX);
                info1.rotation[1] = Math.toDegrees(rend1.rotateAngleY);
                info1.rotation[2] = Math.toDegrees(rend1.rotateAngleZ);

                info1.scale[0] = info1.scale[1] = info1.scale[2] = 1.0F;

                info1.txOffset[0] = rend1.textureOffsetX;
                info1.txOffset[1] = rend1.textureOffsetY;

                if(box.boxName != null)
                {
                    ModelBase modelBase = rend1.baseModel;

                    TextureOffset textureoffset = modelBase.getTextureOffset(box.boxName);

                    if(textureoffset != null)
                    {
                        info1.txOffset[0] = textureoffset.textureOffsetX;
                        info1.txOffset[1] = textureoffset.textureOffsetY;
                    }
                }

                TexturedQuad[] quadList = box.quadList;

                PositionTextureVertex[] vertices = quadList[1].vertexPositions;// left Quad, txOffsetX, txOffsetY + sizeZ
                info1.txMirror = (((vertices[info1.txMirror ? 1 : 2].vector3D.yCoord - vertices[info1.txMirror ? 3 : 0].vector3D.yCoord) - info1.dimensions[1]) / 2 < 0.0D);//silly techne check to see if the model is really mirrored or not

                cubeCount++;
                info.addChild(info1);
                if(firstCreated == null)
                {
                    firstCreated = info1;
                }
            }
            if(firstCreated != null)
            {
                createChildren(name + "Child", rend1, firstCreated);
            }
        }
    }

    public void cloneFrom(ProjectInfo info)
    {
        this.saveFile = info.saveFile;
        this.saveFileMd5 = info.saveFileMd5;
        this.textureFile = info.textureFile;
        this.ignoreNextImage = info.ignoreNextImage;
        this.bufferedTexture = info.bufferedTexture;
        this.cameraFov = info.cameraFov;
        this.cameraZoom = info.cameraZoom;
        this.cameraYaw = info.cameraYaw;
        this.cameraPitch = info.cameraPitch;
        this.cameraOffsetX = info.cameraOffsetX;
        this.cameraOffsetY = info.cameraOffsetY;
        this.lastAutosave = info.lastAutosave;
    }

    public void inherit(ProjectInfo info)//for use of mainframe
    {
        this.cloneFrom(info);
        this.identifier = info.identifier;
        this.textureFileMd5 = info.textureFileMd5;
        this.states = info.states;
        this.lastState = info.lastState;
        this.projVersion = info.projVersion;
        this.textureWidth = info.textureWidth;
        this.textureHeight = info.textureHeight;
        this.switchState = info.switchState;
    }

    public float getMaximumSize() //horizontal max, vert is halved
    {
        ArrayList<CubeInfo> allCubes = getAllCubes();
        double minX = 0F;
        double minY = 0F;
        double minZ = 0F;
        double maxX = 0F;
        double maxY = 0F;
        double maxZ = 0F;
        for(CubeInfo info : allCubes)
        {
            if((info.position[0] + info.offset[0]) * info.scale[0] < minX)
            {
                minX = (info.position[0] + info.offset[0]) * info.scale[0];
            }
            if((info.dimensions[0] + info.position[0] + info.offset[0]) * info.scale[0] > maxX)
            {
                maxX = (info.dimensions[0] + info.position[0] + info.offset[0]) * info.scale[0];
            }
            if((info.position[1] + info.offset[1]) * info.scale[1] < minY)
            {
                minY = (info.position[1] + info.offset[1]) * info.scale[1];
            }
            if((info.dimensions[1] + info.position[1] + info.offset[1]) * info.scale[1] > maxY)
            {
                maxY = (info.dimensions[1] + info.position[1] + info.offset[1]) * info.scale[1];
            }
            if((info.position[2] + info.offset[2]) * info.scale[2] < minZ)
            {
                minZ = (info.position[2] + info.offset[2]) * info.scale[2];
            }
            if((info.dimensions[2] + info.position[2] + info.offset[2]) * info.scale[2] > maxZ)
            {
                maxZ = (info.dimensions[2] + info.position[2] + info.offset[2]) * info.scale[2];
            }
        }
        return (float)Math.max((maxX - minX) / scale[0], Math.max((maxY - minY) / (scale[1] * 2), (maxZ - minZ) / scale[2]));
    }

    public ProjectInfo repair()
    {
        while(projVersion < ProjectInfo.PROJ_VERSION)
        {
            if(projVersion == 1)
            {
                scale = new double[] { 1D, 1D, 1D };

                for(CubeInfo info : getAllCubes())
                {
                    info.opacity = 100D;
                }
            }
            else if(projVersion == 2)
            {
                metadata = new ArrayList<String>();
            }
            else if(projVersion == 3)
            {
                for(CubeGroup group : cubeGroups)
                {
                    group.metadata = new ArrayList<String>();
                }
                ArrayList<CubeInfo> cubes = getAllCubes();
                for(CubeInfo info : cubes)
                {
                    info.metadata = new ArrayList<String>();
                }
            }
            projVersion++;
        }
        return this;
    }

    public static boolean saveProject(ProjectInfo info, File file)
    {
        try
        {
            file.getParentFile().mkdirs();

            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));
            out.setLevel(9);
            out.putNextEntry(new ZipEntry("model.json"));

            byte[] data = (new Gson()).toJson(info).getBytes();
            out.write(data, 0, data.length);
            out.closeEntry();

            if(info.bufferedTexture != null)
            {
                out.putNextEntry(new ZipEntry("texture.png"));
                ImageIO.write(info.bufferedTexture, "png", out);
                out.closeEntry();
            }

            out.close();

            info.saved = true;
            return true;
        }
        catch(Exception e)
        {
            AetherCore.LOGGER.warn("Failed to save model: " + info.modelName);
            e.printStackTrace();
            return false;
        }
    }
}
