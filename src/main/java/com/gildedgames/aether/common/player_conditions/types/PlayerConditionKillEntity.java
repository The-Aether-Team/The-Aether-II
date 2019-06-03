package com.gildedgames.aether.common.player_conditions.types;

import com.gildedgames.aether.api.player.IPlayerConditionModule;
import com.gildedgames.aether.api.player.conditions.types.IPlayerConditionEntity;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.player_conditions.PlayerConditionBase;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.lang.reflect.Type;

public class PlayerConditionKillEntity extends PlayerConditionBase implements IPlayerConditionEntity
{
	private final ResourceLocation entityId;

	private final EntityEntry entityEntry;

	public PlayerConditionKillEntity(final ResourceLocation entityId)
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
		return "killEntity:" + this.entityId;
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
	public int hashCode()
	{
		return new HashCodeBuilder().append("killEntity:").append(this.entityId).toHashCode();
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
	public void onKillEntity(final LivingDeathEvent event)
	{
		final EntityLivingBase entity = event.getEntityLiving();

		if (entity.getClass() != this.entityEntry.getEntityClass() || !(event.getSource().getTrueSource() instanceof EntityPlayer))
		{
			return;
		}

		final EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();

		final PlayerAether playerAether = PlayerAether.getPlayer(player);
		final IPlayerConditionModule module = playerAether.getPlayerConditionModule();

		if (module.isConditionFlagged(this.getUniqueIdentifier()))
		{
			return;
		}

		this.triggerCondition(player);
	}

	public static class Deserializer implements JsonDeserializer<PlayerConditionKillEntity>
	{
		@Override
		public PlayerConditionKillEntity deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
				throws JsonParseException
		{
			return new PlayerConditionKillEntity(new ResourceLocation(json.getAsJsonObject().get("entityId").getAsString()));
		}
	}
}
