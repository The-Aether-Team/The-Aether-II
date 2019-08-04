package com.gildedgames.aether.common.player_conditions;

import com.gildedgames.aether.api.player.conditions.IPlayerCondition;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.player_conditions.types.PlayerConditionFeedEntity;
import com.gildedgames.aether.common.player_conditions.types.PlayerConditionKillEntity;
import com.gildedgames.aether.common.player_conditions.types.PlayerConditionSeeEntity;
import com.google.gson.*;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;
import java.util.HashMap;

public class PlayerConditionDeserializer implements JsonDeserializer<IPlayerCondition>
{
	private final HashMap<ResourceLocation, Class<? extends IPlayerCondition>> conditions = new HashMap<>();

	public PlayerConditionDeserializer()
	{
		this.conditions.put(AetherCore.getResource("seeEntity"), PlayerConditionSeeEntity.class);
		this.conditions.put(AetherCore.getResource("feedEntity"), PlayerConditionFeedEntity.class);
		this.conditions.put(AetherCore.getResource("killEntity"), PlayerConditionKillEntity.class);
	}

	@Override
	public IPlayerCondition deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
	{
		final JsonObject root = json.getAsJsonObject();

		if (!root.has("type"))
		{
			throw new JsonParseException("Missing required field 'type' for condition");
		}

		final ResourceLocation type = new ResourceLocation(root.get("type").getAsString());

		if (!this.conditions.containsKey(type))
		{
			throw new JsonParseException("Invalid condition type " + type);
		}

		return context.deserialize(json, this.conditions.get(type));
	}
}
