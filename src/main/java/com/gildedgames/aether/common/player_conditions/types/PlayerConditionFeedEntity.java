package com.gildedgames.aether.common.player_conditions.types;

import com.gildedgames.aether.api.player.IPlayerConditionModule;
import com.gildedgames.aether.api.player.conditions.types.IPlayerConditionEntity;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerConditionModule;
import com.gildedgames.aether.common.player_conditions.PlayerConditionBase;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.Type;
import java.util.Objects;

public class PlayerConditionFeedEntity extends PlayerConditionBase implements IPlayerConditionEntity
{
	private final ResourceLocation entityId;

	private final EntityEntry entityEntry;

	private final ResourceLocation uniqueId;

	public PlayerConditionFeedEntity(final ResourceLocation entityId)
	{
		Validate.notNull(entityId, "entityId cannot be null.");

		this.entityId = entityId;
		this.entityEntry = ForgeRegistries.ENTITIES.getValue(this.entityId);

		if (this.entityEntry == null)
		{
			throw new RuntimeException("Entity entry cannot be found with given entityId: " + this.entityId);
		}

		this.uniqueId = AetherCore.getResource("feedEntity:" + this.entityId);
	}

	@Override
	public ResourceLocation getUniqueIdentifier()
	{
		return this.uniqueId;
	}

	@Override
	public void onTracked()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void onUntracked()
	{
		MinecraftForge.EVENT_BUS.unregister(this);
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || this.getClass() != o.getClass())
		{
			return false;
		}
		final PlayerConditionFeedEntity that = (PlayerConditionFeedEntity) o;
		return Objects.equals(this.entityId, that.entityId);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.entityId);
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

	@SubscribeEvent
	public void onFeedEntity(final PlayerInteractEvent.EntityInteractSpecific event)
	{
		Entity entity = event.getTarget();

		if (!EntityUtil.checkEntityClass(entity, this.entityEntry.getEntityClass()))
		{
			return;
		}

		// Some entities use this, need to check for cast to get parent
		if (entity instanceof MultiPartEntityPart)
		{
			final MultiPartEntityPart multi = (MultiPartEntityPart) entity;

			entity = (Entity) multi.parent;
		}

		if (!(entity instanceof EntityAnimal))
		{
			return;
		}

		final EntityPlayer player = event.getEntityPlayer();

		final PlayerAether playerAether = PlayerAether.getPlayer(player);
		final IPlayerConditionModule module = playerAether.getModule(PlayerConditionModule.class);

		// If already flagged condition, stop
		if (module.isConditionFlagged(this.getUniqueIdentifier()))
		{
			return;
		}

		final EntityAnimal animal = (EntityAnimal) entity;

		if (animal.isBreedingItem(player.getHeldItem(event.getHand())))
		{
			final int growingAge = animal.getGrowingAge();
			final boolean inLove = animal.isInLove();

			if (animal.isChild() || (growingAge == 0 && !inLove))
			{
				this.triggerCondition(player);
			}
		}
	}

	public static class Deserializer implements JsonDeserializer<PlayerConditionFeedEntity>
	{
		@Override
		public PlayerConditionFeedEntity deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
				throws JsonParseException
		{
			return new PlayerConditionFeedEntity(new ResourceLocation(json.getAsJsonObject().get("entityId").getAsString()));
		}
	}
}
