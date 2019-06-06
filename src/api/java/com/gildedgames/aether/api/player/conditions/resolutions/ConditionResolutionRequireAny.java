package com.gildedgames.aether.api.player.conditions.resolutions;

import com.gildedgames.aether.api.player.conditions.IConditionResolution;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;
import java.util.function.Function;

public class ConditionResolutionRequireAny implements IConditionResolution
{
	@Override
	public boolean areConditionsMet(final ResourceLocation[] conditionIDs, final Function<ResourceLocation, Boolean> isConditionMet)
	{
		for (final ResourceLocation condition : conditionIDs)
		{
			if (isConditionMet.apply(condition))
			{
				return true;
			}
		}

		return false;
	}

	public static class Deserializer implements JsonDeserializer<ConditionResolutionRequireAny>
	{
		@Override
		public ConditionResolutionRequireAny deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
				throws JsonParseException
		{
			return new ConditionResolutionRequireAny();
		}
	}
}
