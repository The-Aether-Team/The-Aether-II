package com.gildedgames.aether.common.cache;

import com.gildedgames.aether.api.cache.IEntityStats;
import com.gildedgames.aether.api.cache.IEntityStatsCache;
import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Map;

public class EntityStatsCache implements IEntityStatsCache
{
	private final Map<ResourceLocation, IEntityStats> entityIdToStats = Maps.newHashMap();

	public EntityStatsCache()
	{

	}

	@Override
	public IEntityStats getStats(final ResourceLocation entityId)
	{
		if (!this.entityIdToStats.containsKey(entityId))
		{
			final EntityEntry entityEntry = ForgeRegistries.ENTITIES.getValue(entityId);

			if (entityEntry == null)
			{
				throw new RuntimeException("Entity entry cannot be found with given entityId: " + entityId);
			}

			// Create entity instance then retrieve stats
			final Entity entity = entityEntry.newInstance(null);

			if (entity instanceof EntityLivingBase)
			{
				final EntityLivingBase living = (EntityLivingBase) entity;

				final double slashDefenseLevel = living.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue();
				final double pierceDefenseLevel = living.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue();
				final double impactDefenseLevel = living.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue();

				final EntityStats stats = EntityStats.build()
						.maxHealth(living.getMaxHealth())
						.slashDefenseLevel(slashDefenseLevel)
						.pierceDefenseLevel(pierceDefenseLevel)
						.impactDefenseLevel(impactDefenseLevel)
						.flush();

				this.entityIdToStats.put(entityId, stats);
			}
		}

		return this.entityIdToStats.get(entityId);
	}
}
