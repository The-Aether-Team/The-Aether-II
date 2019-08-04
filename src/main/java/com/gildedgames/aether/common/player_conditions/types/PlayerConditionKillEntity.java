package com.gildedgames.aether.common.player_conditions.types;

import com.gildedgames.aether.api.player.IPlayerConditionModule;
import com.gildedgames.aether.api.player.conditions.types.IPlayerConditionEntity;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerConditionModule;
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

import java.lang.reflect.Type;
import java.util.Objects;

public class PlayerConditionKillEntity extends PlayerConditionBase implements IPlayerConditionEntity
{
	private final ResourceLocation entityId;

	private final EntityEntry entityEntry;

	private final ResourceLocation uniqueId;

	public PlayerConditionKillEntity(final ResourceLocation entityId)
	{
		Validate.notNull(entityId, "entityId cannot be null.");

		this.entityId = entityId;
		this.entityEntry = ForgeRegistries.ENTITIES.getValue(this.entityId);

		if (this.entityEntry == null)
		{
			throw new RuntimeException("Entity entry cannot be found with given entityId: " + this.entityId);
		}

		this.uniqueId = AetherCore.getResource("killEntity:" + this.entityId);
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
		final PlayerConditionKillEntity that = (PlayerConditionKillEntity) o;
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
	public void onKillEntity(final LivingDeathEvent event)
	{
		final EntityLivingBase entity = event.getEntityLiving();

		if (entity.getClass() != this.entityEntry.getEntityClass() || !(event.getSource().getTrueSource() instanceof EntityPlayer))
		{
			return;
		}

		final EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();

		final PlayerAether playerAether = PlayerAether.getPlayer(player);
		final IPlayerConditionModule module = playerAether.getModule(PlayerConditionModule.class);

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
