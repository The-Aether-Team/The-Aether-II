package us.ichun.mods.ichunutil.common.module.tabula.client.wrappers;

import com.gildedgames.aether.common.ReflectionAether;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.TexturedQuad;

import java.lang.reflect.Field;

public class ModelBoxAccessor
{
	private static Field quadListField;

	static
	{
		quadListField = ReflectionAether.getField(ModelBox.class, "quadList", "field_78254_i");
	}

	public static TexturedQuad[] getQuadList(ModelBox box)
	{
		return (TexturedQuad[]) ReflectionAether.getValue(quadListField, box);
	}
}
