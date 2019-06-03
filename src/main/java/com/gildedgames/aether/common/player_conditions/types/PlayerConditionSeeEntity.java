package com.gildedgames.aether.common.player_conditions.types;

import com.gildedgames.aether.api.player.IPlayerConditionModule;
import com.gildedgames.aether.api.player.conditions.events.ISeeEntityEventsListener;
import com.gildedgames.aether.api.player.conditions.types.IPlayerConditionSeeEntity;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.player_conditions.PlayerConditionBase;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.lang.reflect.Type;

public class PlayerConditionSeeEntity extends PlayerConditionBase implements IPlayerConditionSeeEntity, ISeeEntityEventsListener
{
	private final ResourceLocation entityId;

	private final EntityEntry entityEntry;

	public PlayerConditionSeeEntity(final ResourceLocation entityId)
	{
		Validate.notNull(entityId, "entityId cannot be null.");

		this.entityId = entityId;
		this.entityEntry = ForgeRegistries.ENTITIES.getValue(this.entityId);

		if (this.entityEntry == null)
		{
			throw new RuntimeException("Entity entry cannot be found with given entityId: " + this.entityId);
		}
	}

	@Override
	public String getUniqueIdentifier()
	{
		return "seeEntity:" + this.entityId;
	}

	@Override
	public void onTracked()
	{
		AetherCore.PROXY.content().seeEntityEvents().listen(this);
	}

	@Override
	public void onUntracked()
	{
		AetherCore.PROXY.content().seeEntityEvents().unlisten(this);
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().append("seeEntity:").append(this.entityId).toHashCode();
	}

	@Override
	public ResourceLocation getEntityId()
	{
		return this.entityId;
	}

	@Override
	public EntityEntry getEntityEntry()
	{
		return this.entityEntry;
	}

	@Override
	public void onSeeEntity(final Entity entity, final EntityPlayer player)
	{
		if (entity.getClass() != this.entityEntry.getEntityClass())
		{
			return;
		}

		final PlayerAether playerAether = PlayerAether.getPlayer(player);
		final IPlayerConditionModule module = playerAether.getPlayerConditionModule();

		if (module.isConditionFlagged(this.getUniqueIdentifier()))
		{
			return;
		}

		this.triggerCondition(player);
	}

	public static class Deserializer implements JsonDeserializer<PlayerConditionSeeEntity>
	{
		@Override
		public PlayerConditionSeeEntity deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
				throws JsonParseException
		{
			return new PlayerConditionSeeEntity(new ResourceLocation(json.getAsJsonObject().get("entityId").getAsString()));
		}
	}
}
