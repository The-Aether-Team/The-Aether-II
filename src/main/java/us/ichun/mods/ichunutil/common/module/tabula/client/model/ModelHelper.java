package us.ichun.mods.ichunutil.common.module.tabula.client.model;

import com.gildedgames.aether.common.ReflectionAether;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.components.CubeInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.ReflectionHelper.UnableToAccessFieldException;

import java.lang.reflect.Field;
import java.util.*;

public class ModelHelper
{

    public static Random rand = new Random();

    public static ArrayList<ModelRenderer> separateChildren(ArrayList<ModelRenderer> modelList) //Do not call this on models you do not plan to discard immediately after
    {
        for(int i = 0; i < modelList.size(); i++)
        {
            ModelRenderer renderer = modelList.get(i);
            if(renderer.childModels != null)
            {
                ArrayList<ModelRenderer> children = new ArrayList<ModelRenderer>();
                while(!renderer.childModels.isEmpty())
                {
                    children.add((ModelRenderer)renderer.childModels.get(renderer.childModels.size() - 1));
                    renderer.childModels.remove(renderer.childModels.size() - 1);
                }
                separateChildren(children);

                modelList.addAll(children);
            }
        }
        return modelList;
    }

    //Makes a copy of the models from an original, only models that are being rendered if the world exists.
    public static ArrayList<ModelRenderer> getModelCubesCopy(ArrayList<ModelRenderer> modelList, ModelBase base, Entity ent)
    {
        if(Minecraft.getMinecraft().getRenderManager().renderEngine != null && Minecraft.getMinecraft().getRenderManager().renderViewEntity != null && ent != null)
        {
            for(int i = 0; i < modelList.size(); i++)
            {
                ModelRenderer cube = modelList.get(i);

                if(cube.compiled)
                {
                    GLAllocation.deleteDisplayLists(cube.displayList);
                    cube.compiled = false;
                }
            }
            Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(ent).doRender(ent, 0.0D, -500D, 0.0D, 0.0F, 1.0F);

            ArrayList<ModelRenderer> modelListCopy = new ArrayList<ModelRenderer>(modelList);
            ArrayList<ModelRenderer> list = new ArrayList<ModelRenderer>();

            for(int i = modelListCopy.size() - 1; i >= 0; i--)
            {
                ModelRenderer cube = modelListCopy.get(i);
                if(!cube.compiled)
                {
                    modelListCopy.remove(i);
                }
            }
            for(ModelRenderer cube : modelListCopy)
            {
                list.add(buildCopy(cube, base, 0, true, false));
            }
            return list;
        }
        else
        {

            ArrayList<ModelRenderer> list = new ArrayList<ModelRenderer>();

            for(int i = 0; i < modelList.size(); i++)
            {
                ModelRenderer cube = modelList.get(i);
                list.add(buildCopy(cube, base, 0, true, false));
            }

            return list;
        }
    }

    //Copied over from Tabula. CubeInfo contains all the information (and some of Tabula's) of a ModelBox, enough to reconstruct it. Children is not reconstructed however
    public static CubeInfo createCubeInfoFromModelBox(ModelRenderer rend, ModelBox box, String name)
    {
        CubeInfo info = new CubeInfo(name);

        info.dimensions[0] = (int)Math.abs(box.posX2 - box.posX1);
        info.dimensions[1] = (int)Math.abs(box.posY2 - box.posY1);
        info.dimensions[2] = (int)Math.abs(box.posZ2 - box.posZ1);

        info.position[0] = rend.rotationPointX;
        info.position[1] = rend.rotationPointY;
        info.position[2] = rend.rotationPointZ;

        info.offset[0] = box.posX1;
        info.offset[1] = box.posY1;
        info.offset[2] = box.posZ1;

        info.rotation[0] = Math.toDegrees(rend.rotateAngleX);
        info.rotation[1] = Math.toDegrees(rend.rotateAngleY);
        info.rotation[2] = Math.toDegrees(rend.rotateAngleZ);

        info.scale[0] = info.scale[1] = info.scale[2] = 1.0F;

        PositionTextureVertex[] vertices = box.quadList[1].vertexPositions;// left Quad, txOffsetX, txOffsetY + sizeZ

        info.txMirror = (((vertices[info.txMirror ? 1 : 2].vector3D.yCoord - vertices[info.txMirror ? 3 : 0].vector3D.yCoord) - info.dimensions[1]) / 2 < 0.0D);//silly techne check to see if the model is really mirrored or not

        info.txOffset[0] = (int)(vertices[info.txMirror ? 2 : 1].texturePositionX * rend.textureWidth);
        info.txOffset[1] = (int)(vertices[info.txMirror ? 2 : 1].texturePositionY * rend.textureHeight) - info.dimensions[2];

        if(vertices[info.txMirror ? 2 : 1].texturePositionY > vertices[info.txMirror ? 1 : 2].texturePositionY) //Check to correct the texture offset on the y axis to fix some minecraft models
        {
            info.txMirror = !info.txMirror;

            info.txOffset[0] = (int)(vertices[info.txMirror ? 2 : 1].texturePositionX * rend.textureWidth);
            info.txOffset[1] = (int)(vertices[info.txMirror ? 2 : 1].texturePositionY * rend.textureHeight) - info.dimensions[2];
        }

        if(box.boxName != null)
        {
            TextureOffset textureoffset = rend.baseModel.getTextureOffset(box.boxName);
            if(textureoffset != null)
            {
                info.txOffset[0] = textureoffset.textureOffsetX;
                info.txOffset[1] = textureoffset.textureOffsetY;
            }
        }

        info.mcScale = ((vertices[info.txMirror ? 1 : 2].vector3D.yCoord - vertices[info.txMirror ? 3 : 0].vector3D.yCoord) - info.dimensions[1]) / 2;

        return info;
    }

    //Recreate a copy of a ModelRenderer. hasFullModelBox will put the boxes in the original spot. False will randomly put it attached to a random Model location based off the original
    public static ModelRenderer buildCopy(ModelRenderer original, ModelBase copyBase, int depth, boolean hasFullModelBox, boolean isEmpty) //exactDupes no longer exist cause the modelbox is reconstructed almost completely without using the original model textures.
    {
        int txOffsetX = original.textureOffsetX;
        int txOffsetY = original.textureOffsetY;

        ModelRenderer cubeCopy = new ModelRenderer(copyBase, txOffsetX, txOffsetY);
        cubeCopy.textureHeight = original.textureHeight;
        cubeCopy.textureWidth = original.textureWidth;

        for(int j = 0; j < original.cubeList.size(); j++)
        {
            ModelBox box = (ModelBox)original.cubeList.get(j);
            CubeInfo info = createCubeInfoFromModelBox(original, box, box.boxName != null ? (box.boxName.substring(box.boxName.lastIndexOf(".") + 1)) : "");

            cubeCopy.mirror = info.txMirror;
            cubeCopy.textureOffsetX = info.txOffset[0];
            cubeCopy.textureOffsetY = info.txOffset[1];

            if(isEmpty)
            {
                info.offset[0] = info.offset[1] = info.offset[2] = info.dimensions[0] = info.dimensions[1] = info.dimensions[2] = 0;
            }
            if(hasFullModelBox)
            {
                cubeCopy.addBox((float)info.offset[0], (float)info.offset[1], (float)info.offset[2], info.dimensions[0], info.dimensions[1], info.dimensions[2], (float)info.mcScale);
            }
            else
            {
                ModelBox randBox = (ModelBox)original.cubeList.get(rand.nextInt(original.cubeList.size()));

                float x = randBox.posX1 + ((randBox.posX2 - randBox.posX1) > 0F ? rand.nextInt(((int)(randBox.posX2 - randBox.posX1) > 0) ? (int)(randBox.posX2 - randBox.posX1) : 1) : 0F);
                float y = randBox.posY1 + ((randBox.posY2 - randBox.posY1) > 0F ? rand.nextInt(((int)(randBox.posY2 - randBox.posY1) > 0) ? (int)(randBox.posY2 - randBox.posY1) : 1) : 0F);
                float z = randBox.posZ1 + ((randBox.posZ2 - randBox.posZ1) > 0F ? rand.nextInt(((int)(randBox.posZ2 - randBox.posZ1) > 0) ? (int)(randBox.posZ2 - randBox.posZ1) : 1) : 0F);

                cubeCopy.addBox(x, y, z, info.dimensions[0], info.dimensions[1], info.dimensions[2], (float)info.mcScale);
            }
        }

        cubeCopy.mirror = original.mirror;

        if(original.childModels != null && depth < 20)
        {
            for(int i = 0; i < original.childModels.size(); i++)
            {
                ModelRenderer child = (ModelRenderer)original.childModels.get(i);
                cubeCopy.addChild(buildCopy(child, copyBase, depth + 1, hasFullModelBox, isEmpty));
            }
        }

        cubeCopy.setRotationPoint(original.rotationPointX, original.rotationPointY, original.rotationPointZ);

        cubeCopy.rotateAngleX = original.rotateAngleX;
        cubeCopy.rotateAngleY = original.rotateAngleY;
        cubeCopy.rotateAngleZ = original.rotateAngleZ;
        return cubeCopy;
    }

    //Gets the parent ModelRenderers in a ModelBase.
    public static ArrayList<ModelRenderer> getModelCubes(ModelBase parent)
    {
        return new ArrayList<ModelRenderer>(getModelCubesWithNames(parent).values());
    }

    //Gets the parent ModelRenderers in a ModelBase with their field names. No children are in this list.
    public static HashMap<String, ModelRenderer> getModelCubesWithNames(ModelBase parent)
    {
        HashMap<String, ModelRenderer> list = new HashMap<String, ModelRenderer>();

        HashMap<String,  ModelRenderer[]> list1 = new HashMap<String, ModelRenderer[]>();

        if(parent != null)
        {
            Class clz = parent.getClass();
            while(clz != ModelBase.class && ModelBase.class.isAssignableFrom(clz))
            {
                try
                {
                    Field[] fields = clz.getDeclaredFields();
                    for(Field f : fields)
                    {
                        f.setAccessible(true);
                        if(f.getType() == ModelRenderer.class || ModelRenderer.class.isAssignableFrom(f.getType()))
                        {
                            ModelRenderer rend = (ModelRenderer)f.get(parent);
                            if(rend != null)
                            {
                                String name = f.getName();
                                if(rend.boxName != null)
                                {
                                    name = rend.boxName;
                                    while(list.containsKey(name))
                                    {
                                        name = name + "_";
                                    }
                                }
                                list.put(name, rend); // Add normal parent fields
                            }
                        }
                        else if(f.getType() == ModelRenderer[].class || ModelRenderer[].class.isAssignableFrom(f.getType()))
                        {
                            ModelRenderer[] rend = (ModelRenderer[])f.get(parent);
                            if(rend != null)
                            {
                                list1.put(f.getName(), rend);
                            }
                        }
                    }
                    clz = clz.getSuperclass();
                }
                catch(Exception e)
                {
                    throw new UnableToAccessFieldException(new String[0], e);
                }
            }
        }

        for(Map.Entry<String, ModelRenderer[]> e : list1.entrySet())
        {
            int count = 1;
            for(ModelRenderer cube : e.getValue())
            {
                if(cube != null && !list.containsValue(cube))
                {
                    list.put(e.getKey() + count, cube); //Add stuff like flying blaze rods stored in MR[] fields.
                    count++;
                }
            }
        }

        ArrayList<ModelRenderer> children = new ArrayList<ModelRenderer>();

        for(Map.Entry<String, ModelRenderer> e : list.entrySet())
        {
            ModelRenderer cube = e.getValue();
            for(ModelRenderer child : getChildren(cube, true, 0))
            {
                if(!children.contains(child))
                {
                    children.add(child);
                }
            }
        }

        for(ModelRenderer child : children)
        {
            Iterator<Map.Entry<String, ModelRenderer>> ite = list.entrySet().iterator();
            while(ite.hasNext())
            {
                Map.Entry<String, ModelRenderer> e = ite.next();
                if(e.getValue() == child)
                {
                    ite.remove();
                }
            }
        }

        return list;
    }

    //Gets all the model cubes from several models
    public static ArrayList<ModelRenderer> getMultiModelCubes(ArrayList<ModelBase> parent)
    {
        ArrayList<ModelRenderer> list = new ArrayList<ModelRenderer>();
        for(ModelBase base : parent)
        {
            list.addAll(getModelCubes(base));
        }
        return list;
    }

    //Gets the children models from a ModelRenderer
    public static ArrayList<ModelRenderer> getChildren(ModelRenderer parent, boolean recursive, int depth)
    {
        ArrayList<ModelRenderer> list = new ArrayList<ModelRenderer>();
        if(parent.childModels != null && depth < 20)
        {
            for(int i = 0; i < parent.childModels.size(); i++)
            {
                ModelRenderer child = (ModelRenderer)parent.childModels.get(i);
                if(recursive)
                {
                    ArrayList<ModelRenderer> children = getChildren(child, recursive, depth + 1);
                    for(ModelRenderer child1 : children)
                    {
                        if(!list.contains(child1))
                        {
                            list.add(child1);
                        }
                    }
                }
                if(!list.contains(child))
                {
                    list.add(child);
                }
            }
        }
        return list;
    }

    //Tries to find the most likely model for the Render class
    public static ModelBase getPossibleModel(Render rend)
    {
        ArrayList<ArrayList<ModelBase>> models = new ArrayList<ArrayList<ModelBase>>();

        if(rend != null)
        {
            try
            {
                Class clz = rend.getClass();
                while(clz != Render.class)
                {
                    ArrayList<ModelBase> priorityLevel = new ArrayList<ModelBase>();

                    Field[] fields = clz.getDeclaredFields();
                    for(Field f : fields)
                    {
                        f.setAccessible(true);
                        if(ModelBase.class.isAssignableFrom(f.getType()))
                        {
                            ModelBase base = (ModelBase)f.get(rend);
                            if(base != null)
                            {
                                priorityLevel.add(base); // Add normal parent fields
                            }
                        }
                        else if(ModelBase[].class.isAssignableFrom(f.getType()))
                        {
                            ModelBase[] modelBases = (ModelBase[])f.get(rend);
                            if(modelBases != null)
                            {
                                priorityLevel.addAll(Arrays.asList(modelBases));
                            }
                        }
                    }

                    models.add(priorityLevel);

                    if(clz == RenderLivingBase.class)
                    {
                        ArrayList<ModelBase> topPriority = new ArrayList<ModelBase>();
                        for(Field f : fields)
                        {
                            f.setAccessible(true);
                            if(ModelBase.class.isAssignableFrom(f.getType()) && (f.getName().equalsIgnoreCase(ReflectionAether.MAIN_MODEL.getMappings()[0]) || f.getName().equalsIgnoreCase(ReflectionAether.MAIN_MODEL.getMappings()[1])))
                            {
                                ModelBase base = (ModelBase)f.get(rend);
                                if(base != null)
                                {
                                    topPriority.add(base);
                                }
                            }
                        }
                        models.add(topPriority);
                    }
                    clz = clz.getSuperclass();
                }
            }
            catch(Exception e)
            {
                throw new UnableToAccessFieldException(new String[0], e);
            }
        }

        ModelBase base1 = null;
        int priorityLevel = -1;
        int size = -1;

        int currentPriority = 0;

        for(ArrayList<ModelBase> modelList : models)
        {
            for(ModelBase base : modelList)
            {
                ArrayList<ModelRenderer> mrs = getModelCubes(base);
                if(mrs.size() > size || mrs.size() == size && currentPriority > priorityLevel)
                {
                    size = mrs.size();
                    base1 = base;
                    priorityLevel = currentPriority;
                }
            }
            currentPriority++;
        }

        return base1;
    }

    public static boolean areModelsEqual(ModelBase model1, ModelBase model2)//Compares two model bases. Ugly code but it works.
    {
        if(model1.getClass() == model2.getClass() && model1.textureHeight == model2.textureHeight && model1.textureWidth == model2.textureWidth)
        {
            ArrayList<ModelRenderer> parts1 = getModelCubes(model1);
            ArrayList<ModelRenderer> parts2 = getModelCubes(model2);
            if(parts1.size() == parts2.size())
            {
                for(int i = parts1.size() - 1; i >= 0; i--)
                {
                    ModelRenderer rend1 = parts1.get(i);
                    boolean remove = true;
                    for(int k = rend1.cubeList.size() - 1; k >= 0; k--)
                    {
                        ModelBox box1 = (ModelBox)rend1.cubeList.get(k);
                        CubeInfo cube1 = createCubeInfoFromModelBox(rend1, box1, box1.boxName != null ? box1.boxName : "");
                        boolean found = false;
                        for(int j = parts2.size() - 1; j >= 0; j--)
                        {
                            ModelRenderer rend2 = parts2.get(j);
                            if(rend1.cubeList.size() == rend2.cubeList.size() && (rend1.childModels == null && rend2.childModels == null || rend1.childModels != null && rend2.childModels != null && rend1.childModels.size() == rend2.childModels.size()))
                            {
                                for(int l = rend2.cubeList.size(); l >= 0; l--)
                                {
                                    ModelBox box2 = (ModelBox)rend2.cubeList.get(l);
                                    CubeInfo cube2 = createCubeInfoFromModelBox(rend2, box2, box2.boxName != null ? box2.boxName : "");
                                    for(int m = 0; m < 3; m++)
                                    {
                                        if(cube1.dimensions[m] == cube2.dimensions[m] && cube1.offset[m] == cube2.offset[m])
                                        {
                                            if(m == 2)
                                            {
                                                found = true;
                                            }
                                        }
                                        else
                                        {
                                            break;
                                        }
                                    }
                                    if(found)
                                    {
                                        break;
                                    }
                                }
                            }
                            if(found)
                            {
                                break;
                            }
                        }
                        if(!found)
                        {
                            remove = false;
                        }
                    }
                    if(remove)
                    {
                        parts1.remove(i);
                    }
                }
                return parts1.isEmpty();
            }
        }
        return false;
    }

    public static int getModelHeight(ModelRenderer model)
    {
        int height = 0;//Y1 lower than Y2
        for(int i = 0; i < model.cubeList.size(); i++)
        {
            ModelBox box = (ModelBox)model.cubeList.get(i);
            if((int)Math.abs(box.posY2 - box.posY1) > height)
            {
                height = (int)Math.abs(box.posY2 - box.posY1);
            }
        }
        return height;
    }

    //Gets the model cubes from the entity living.
    public static ArrayList<ModelRenderer> getModelCubes(Entity entity)
    {
        return getModelCubes(Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(entity));
    }

    public static ArrayList<ModelRenderer> getModelCubes(Render rend)
    {
        ArrayList<ModelRenderer> map = classToModelRendererMap.get(rend.getClass());
        if(map == null)
        {
            map = getModelCubes(getPossibleModel(rend));
            classToModelRendererMap.put(rend.getClass(), map);
        }
        return map;
    }

    public static ModelRenderer[] getPotentialArms(ModelBase parent) //array of arms, 0 = right, 1 = left.
    {
        ModelRenderer[] arms = new ModelRenderer[2];
        if(parent != null)
        {
            Class clz = parent.getClass();
            if(clz == ModelHorse.class)
            {
                ModelHorse dummy = new ModelHorse();

                ModelRenderer leg = new ModelRenderer(dummy, 60, 29);
                leg.addBox(-1.1F, -1.0F, -2.1F, 3, 8, 4);
                leg.setRotationPoint(-4.0F, 9.0F, -8.0F);

                ModelRenderer shin = new ModelRenderer(dummy, 60, 41);
                shin.addBox(-1.1F, 0.0F, -1.6F, 3, 5, 3);
                shin.setRotationPoint(0.0F, 7.0F, 0.0F);
                leg.addChild(shin);

                ModelRenderer hoof = new ModelRenderer(dummy, 60, 51);
                hoof.addBox(-1.6F, 5.1F, -2.1F, 4, 3, 4);
                hoof.setRotationPoint(0.0F, 0.0F, 0.0F);

                shin.addChild(hoof);

                ModelRenderer[] legs = new ModelRenderer[] { leg, leg };

                armMappings.put(ModelHorse.class, legs);

                return legs;
            }
            else if(clz == ModelPlayer.class)
            {
                arms[0] = ((ModelPlayer)parent).bipedRightArm;
                arms[1] = ((ModelPlayer)parent).bipedLeftArm;

                armMappings.put(ModelPlayer.class, arms);

                return arms;
            }
            else
            {
                while(clz != ModelBase.class && ModelBase.class.isAssignableFrom(clz))
                {
                    try
                    {
                        Field[] fields = clz.getDeclaredFields();
                        for(Field f : fields)
                        {
                            f.setAccessible(true);
                            if(f.getType() == ModelRenderer.class)
                            {
                                //right arm
                                if(     clz == ModelBiped.class &&      (f.getName().equalsIgnoreCase("field_178723_h")|| f.getName().equalsIgnoreCase("bipedRightArm")         || f.getName().equalsIgnoreCase("h")) ||
                                        clz == ModelQuadruped.class &&  (f.getName().equalsIgnoreCase("field_78147_e") || f.getName().equalsIgnoreCase("leg3")                  || f.getName().equalsIgnoreCase("e")) ||
                                        clz == ModelCreeper.class &&    (f.getName().equalsIgnoreCase("field_78129_f") || f.getName().equalsIgnoreCase("leg3")                  || f.getName().equalsIgnoreCase("f")) ||
                                        clz == ModelIronGolem.class &&  (f.getName().equalsIgnoreCase("field_78177_c") || f.getName().equalsIgnoreCase("ironGolemRightArm")     || f.getName().equalsIgnoreCase("c")) ||
                                        clz == ModelSpider.class &&     (f.getName().equalsIgnoreCase("field_78210_j") || f.getName().equalsIgnoreCase("spiderLeg7")            || f.getName().equalsIgnoreCase("j")) ||
                                        clz == ModelWolf.class &&       (f.getName().equalsIgnoreCase("field_78182_e") || f.getName().equalsIgnoreCase("wolfLeg3")              || f.getName().equalsIgnoreCase("e")) ||
                                        clz == ModelOcelot.class &&     (f.getName().equalsIgnoreCase("field_78157_d") || f.getName().equalsIgnoreCase("ocelotFrontRightLeg")   || f.getName().equalsIgnoreCase("d")) ||
                                        clz == ModelRabbit.class &&     (f.getName().equalsIgnoreCase("field_178693_g")|| f.getName().equalsIgnoreCase("rabbitRightArm")        || f.getName().equalsIgnoreCase("g")) ||
                                        clz != ModelBiped.class && clz != ModelQuadruped.class && clz != ModelCreeper.class && clz != ModelIronGolem.class && clz != ModelSpider.class && clz != ModelWolf.class && clz != ModelOcelot.class && clz != ModelRabbit.class &&
                                                f.getName().toLowerCase().contains("right") && (f.getName().toLowerCase().contains("arm") || f.getName().toLowerCase().contains("hand")))
                                {
                                    ModelRenderer arm = (ModelRenderer)f.get(parent);
                                    if(arm != null && arms[0] == null)
                                    {
                                        arms[0] = arm;

                                        armMappings.put(parent.getClass(), arms);
                                    }
                                }

                                //left arm
                                if(     clz == ModelBiped.class &&      (f.getName().equalsIgnoreCase("field_178724_i")|| f.getName().equalsIgnoreCase("bipedLeftArm")         || f.getName().equalsIgnoreCase("i")) ||
                                        clz == ModelQuadruped.class &&  (f.getName().equalsIgnoreCase("field_78144_f") || f.getName().equalsIgnoreCase("leg4")                  || f.getName().equalsIgnoreCase("f")) ||
                                        clz == ModelCreeper.class &&    (f.getName().equalsIgnoreCase("field_78130_g") || f.getName().equalsIgnoreCase("leg4")                  || f.getName().equalsIgnoreCase("g")) ||
                                        clz == ModelIronGolem.class &&  (f.getName().equalsIgnoreCase("field_78174_d") || f.getName().equalsIgnoreCase("ironGolemLeftArm")      || f.getName().equalsIgnoreCase("d")) ||
                                        clz == ModelSpider.class &&     (f.getName().equalsIgnoreCase("field_78211_k") || f.getName().equalsIgnoreCase("spiderLeg8")            || f.getName().equalsIgnoreCase("k")) ||
                                        clz == ModelWolf.class &&       (f.getName().equalsIgnoreCase("field_78179_f") || f.getName().equalsIgnoreCase("wolfLeg4")              || f.getName().equalsIgnoreCase("f")) ||
                                        clz == ModelOcelot.class &&     (f.getName().equalsIgnoreCase("field_78160_c") || f.getName().equalsIgnoreCase("ocelotFrontLeftLeg")    || f.getName().equalsIgnoreCase("c")) ||
                                        clz == ModelRabbit.class &&     (f.getName().equalsIgnoreCase("field_178692_f")|| f.getName().equalsIgnoreCase("rabbitLeftArm")         || f.getName().equalsIgnoreCase("f")) ||
                                        clz != ModelBiped.class && clz != ModelQuadruped.class && clz != ModelCreeper.class && clz != ModelIronGolem.class && clz != ModelSpider.class && clz != ModelWolf.class && clz != ModelOcelot.class && clz != ModelRabbit.class &&
                                                f.getName().toLowerCase().contains("left") && (f.getName().toLowerCase().contains("arm") || f.getName().toLowerCase().contains("hand")))
                                {
                                    ModelRenderer arm = (ModelRenderer)f.get(parent);
                                    if(arm != null && arms[1] == null)
                                    {
                                        arms[1] = arm;

                                        armMappings.put(parent.getClass(), arms);
                                    }
                                }
                            }
                            else if(f.getType() == ModelRenderer[].class && clz == ModelSquid.class && (f.getName().equalsIgnoreCase("squidTentacles") || f.getName().equalsIgnoreCase("b") || f.getName().equalsIgnoreCase("field_78201_b")))
                            {
                                ModelRenderer arm = ((ModelRenderer[])f.get(parent))[0];
                                arms[0] = arms[1] = arm;
                                armMappings.put(ModelSquid.class, arms);
                                return arms;
                            }
                        }
                        clz = clz.getSuperclass();
                    }
                    catch(Exception e)
                    {
                        throw new UnableToAccessFieldException(new String[0], e);
                    }
                }
                return arms;
            }
        }
        return arms;
    }

    public static HashMap<Class<? extends ModelBase>, ModelRenderer[]> armMappings = new HashMap<Class<? extends ModelBase>, ModelRenderer[]>();
    public static HashMap<Class<? extends Render>, ArrayList<ModelRenderer>> classToModelRendererMap = new HashMap<Class<? extends Render>, ArrayList<ModelRenderer>>();
}
