package us.ichun.mods.ichunutil.common.module.tabula.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TextureOffset;
import net.minecraft.client.model.TexturedQuad;
import net.minecraftforge.fml.relauncher.ReflectionHelper.UnableToAccessFieldException;
import us.ichun.mods.ichunutil.common.module.tabula.client.wrappers.ModelBoxAccessor;
import us.ichun.mods.ichunutil.common.module.tabula.client.wrappers.ModelRendererAccessor;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.components.CubeInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class ModelHelper
{

	public static Random rand = new Random();

	//Copied over from Tabula. CubeInfo contains all the information (and some of Tabula's) of a ModelBox, enough to reconstruct it. Children is not reconstructed however
	public static CubeInfo createCubeInfoFromModelBox(ModelRenderer rend, ModelBox box, String name)
	{
		TexturedQuad[] quads = ModelBoxAccessor.getQuadList(box);

		CubeInfo info = new CubeInfo(name);

		info.dimensions[0] = (int) Math.abs(box.posX2 - box.posX1);
		info.dimensions[1] = (int) Math.abs(box.posY2 - box.posY1);
		info.dimensions[2] = (int) Math.abs(box.posZ2 - box.posZ1);

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

		PositionTextureVertex[] vertices = quads[1].vertexPositions;// left Quad, txOffsetX, txOffsetY + sizeZ

		info.txMirror = (((vertices[info.txMirror ? 1 : 2].vector3D.yCoord - vertices[info.txMirror ? 3 : 0].vector3D.yCoord) - info.dimensions[1]) / 2 < 0.0D);//silly techne check to see if the model is really mirrored or not

		info.txOffset[0] = (int) (vertices[info.txMirror ? 2 : 1].texturePositionX * rend.textureWidth);
		info.txOffset[1] = (int) (vertices[info.txMirror ? 2 : 1].texturePositionY * rend.textureHeight) - info.dimensions[2];

		if (vertices[info.txMirror ? 2 : 1].texturePositionY > vertices[info.txMirror ? 1 : 2].texturePositionY) //Check to correct the texture offset on the y axis to fix some minecraft models
		{
			info.txMirror = !info.txMirror;

			info.txOffset[0] = (int) (vertices[info.txMirror ? 2 : 1].texturePositionX * rend.textureWidth);
			info.txOffset[1] = (int) (vertices[info.txMirror ? 2 : 1].texturePositionY * rend.textureHeight) - info.dimensions[2];
		}

		if (box.boxName != null)
		{
			TextureOffset textureoffset = ModelRendererAccessor.getBaseModel(rend).getTextureOffset(box.boxName);

			if (textureoffset != null)
			{
				info.txOffset[0] = textureoffset.textureOffsetX;
				info.txOffset[1] = textureoffset.textureOffsetY;
			}
		}

		info.mcScale = ((vertices[info.txMirror ? 1 : 2].vector3D.yCoord - vertices[info.txMirror ? 3 : 0].vector3D.yCoord) - info.dimensions[1]) / 2;

		return info;
	}

	//Gets the parent ModelRenderers in a ModelBase with their field names. No children are in this list.
	public static HashMap<String, ModelRenderer> getModelCubesWithNames(ModelBase parent)
	{
		HashMap<String, ModelRenderer> list = new HashMap<>();

		HashMap<String, ModelRenderer[]> list1 = new HashMap<>();

		if (parent != null)
		{
			Class clz = parent.getClass();
			while (clz != ModelBase.class && ModelBase.class.isAssignableFrom(clz))
			{
				try
				{
					Field[] fields = clz.getDeclaredFields();
					for (Field f : fields)
					{
						f.setAccessible(true);
						if (f.getType() == ModelRenderer.class || ModelRenderer.class.isAssignableFrom(f.getType()))
						{
							ModelRenderer rend = (ModelRenderer) f.get(parent);
							if (rend != null)
							{
								String name = f.getName();
								if (rend.boxName != null)
								{
									name = rend.boxName;
									while (list.containsKey(name))
									{
										name = name + "_";
									}
								}
								list.put(name, rend); // Add normal parent fields
							}
						}
						else if (f.getType() == ModelRenderer[].class || ModelRenderer[].class.isAssignableFrom(f.getType()))
						{
							ModelRenderer[] rend = (ModelRenderer[]) f.get(parent);
							if (rend != null)
							{
								list1.put(f.getName(), rend);
							}
						}
					}
					clz = clz.getSuperclass();
				}
				catch (Exception e)
				{
					throw new UnableToAccessFieldException(new String[0], e);
				}
			}
		}

		for (Map.Entry<String, ModelRenderer[]> e : list1.entrySet())
		{
			int count = 1;
			for (ModelRenderer cube : e.getValue())
			{
				if (cube != null && !list.containsValue(cube))
				{
					list.put(e.getKey() + count, cube); //Add stuff like flying blaze rods stored in MR[] fields.
					count++;
				}
			}
		}

		ArrayList<ModelRenderer> children = new ArrayList<>();

		for (Map.Entry<String, ModelRenderer> e : list.entrySet())
		{
			ModelRenderer cube = e.getValue();
			for (ModelRenderer child : getChildren(cube, true, 0))
			{
				if (!children.contains(child))
				{
					children.add(child);
				}
			}
		}

		for (ModelRenderer child : children)
		{
			Iterator<Map.Entry<String, ModelRenderer>> ite = list.entrySet().iterator();
			while (ite.hasNext())
			{
				Map.Entry<String, ModelRenderer> e = ite.next();
				if (e.getValue() == child)
				{
					ite.remove();
				}
			}
		}

		return list;
	}

	//Gets the children models from a ModelRenderer
	public static ArrayList<ModelRenderer> getChildren(ModelRenderer parent, boolean recursive, int depth)
	{
		ArrayList<ModelRenderer> list = new ArrayList<>();
		if (parent.childModels != null && depth < 20)
		{
			for (int i = 0; i < parent.childModels.size(); i++)
			{
				ModelRenderer child = parent.childModels.get(i);
				if (recursive)
				{
					ArrayList<ModelRenderer> children = getChildren(child, recursive, depth + 1);
					for (ModelRenderer child1 : children)
					{
						if (!list.contains(child1))
						{
							list.add(child1);
						}
					}
				}
				if (!list.contains(child))
				{
					list.add(child);
				}
			}
		}
		return list;
	}
}
