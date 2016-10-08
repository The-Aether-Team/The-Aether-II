package us.ichun.mods.ichunutil.common.module.tabula.client.wrappers;

import com.gildedgames.aether.common.ReflectionAether;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GLAllocation;

import java.lang.reflect.Field;

public class ModelRendererAccessor
{
	private static Field compiledField;

	private static Field displayListField;

	private static Field baseModelField;

	private static Field textureOffsetYField, textureOffsetXField;

	static
	{
		compiledField = ReflectionAether.getField(ModelRenderer.class, "compiled", "field_78812_q");
		displayListField = ReflectionAether.getField(ModelRenderer.class, "displayList", "field_78811_r");
		baseModelField = ReflectionAether.getField(ModelRenderer.class, "baseModel", "field_78810_s");
		textureOffsetYField = ReflectionAether.getField(ModelRenderer.class, "textureOffsetY", "field_78813_p");
		textureOffsetXField = ReflectionAether.getField(ModelRenderer.class, "textureOffsetX", "field_78803_o");
	}

	public static boolean isCompiled(ModelRenderer renderer)
	{
		return (boolean) ReflectionAether.getValue(compiledField, renderer);
	}

	public static int getDisplayList(ModelRenderer renderer)
	{
		return (int) ReflectionAether.getValue(displayListField, renderer);
	}

	public static int getTextureOffsetY(ModelRenderer renderer)
	{
		return (int) ReflectionAether.getValue(textureOffsetYField, renderer);
	}

	public static int getTextureOffsetX(ModelRenderer renderer)
	{
		return (int) ReflectionAether.getValue(textureOffsetXField, renderer);
	}

	public static void deleteDisplayLists(ModelRenderer renderer)
	{
		int id = getDisplayList(renderer);

		GLAllocation.deleteDisplayLists(id);
	}

	public static ModelBase getBaseModel(ModelRenderer renderer)
	{
		return (ModelBase) ReflectionAether.getValue(baseModelField, renderer);
	}
}
