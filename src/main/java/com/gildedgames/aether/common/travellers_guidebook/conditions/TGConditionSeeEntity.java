package com.gildedgames.aether.common.travellers_guidebook.conditions;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTGModule;
import com.gildedgames.aether.common.travellers_guidebook.TGConditionBase;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.lang.reflect.Type;
import java.util.List;

public class TGConditionSeeEntity extends TGConditionBase
{
	private final ResourceLocation entityId;

	private final EntityEntry entityEntry;

	public TGConditionSeeEntity(final ResourceLocation entityId)
	{
		Validate.notNull(entityId, "entityId cannot be null.");

		this.entityId = entityId;
		this.entityEntry = ForgeRegistries.ENTITIES.getValue(this.entityId);

		if (this.entityEntry == null)
		{
			throw new RuntimeException("Entity entry cannot be found with given entityId: " + this.entityId);
		}

		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onLivingEntityUpdate(final LivingEvent.LivingUpdateEvent event)
	{
		final Entity entity = event.getEntity();

		if (!entity.world.isRemote && entity instanceof EntityPlayer)
		{
			final EntityPlayer player = ((EntityPlayer) entity);
			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			final PlayerTGModule module = playerAether.getTGModule();

			if (module.isConditionFlagged(this.getUniqueIdentifier()))
			{
				return;
			}

			final float scale = 5;

			final double minX = entity.posX - scale;
			final double minY = entity.posY - scale;
			final double minZ = entity.posZ - scale;

			final double maxX = entity.posX + scale;
			final double maxY = entity.posY + scale;
			final double maxZ = entity.posZ + scale;

			final AxisAlignedBB checkingArea = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);

			final List<Entity> entitiesInSight = entity.world.getEntitiesWithinAABB(this.entityEntry.getEntityClass(), checkingArea);

			for (final Entity e : entitiesInSight)
			{
				if (player.canEntityBeSeen(e))
				{
					this.triggerCondition(player);

					break;
				}
			}
		}
	}

	@Override
	public String getUniqueIdentifier()
	{
		return "seeEntity:" + this.entityId;
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().append("seeEntity:").append(this.entityId).toHashCode();
	}

	public static class Deserializer implements JsonDeserializer<TGConditionSeeEntity>
	{
		@Override
		public TGConditionSeeEntity deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
				throws JsonParseException
		{
			return new TGConditionSeeEntity(new ResourceLocation(json.getAsJsonObject().get("entityId").getAsString()));
		}
	}
}
