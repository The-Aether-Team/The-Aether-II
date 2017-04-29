package com.gildedgames.aether.common.capabilities.item.effects;

import com.gildedgames.aether.api.items.equipment.effects.EffectInstance;
import com.gildedgames.aether.api.items.equipment.effects.IEffectFactory;
import com.gildedgames.aether.api.items.equipment.effects.IEffectPool;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.companions.EntityCompanion;
import com.gildedgames.aether.common.items.companions.ItemCompanion;
import com.gildedgames.aether.common.world.spawning.SpawnHandler;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class CompanionEffectFactory implements IEffectFactory<CompanionEffectFactory.CompanionEffectProvider>
{
	private static final ResourceLocation NAME = new ResourceLocation(AetherCore.MOD_ID, "companion_effect");

	@Override
	public EffectInstance createInstance(IEffectPool<CompanionEffectProvider> pool)
	{
		return new CompanionEffectInstance(pool);
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return NAME;
	}

	public static class CompanionEffectProvider implements IEffectProvider
	{
		private final ResourceLocation name;

		public CompanionEffectProvider(Class<? extends EntityCompanion> clazz)
		{
			this(EntityList.getKey(clazz));
		}

		public CompanionEffectProvider(ResourceLocation id)
		{
			Validate.notNull(id, "Entity resource name is null");

			this.name = id;
		}

		@Override
		public ResourceLocation getFactory()
		{
			return NAME;
		}

		@Override
		public IEffectProvider copy()
		{
			return new CompanionEffectProvider(this.name);
		}
	}

	private class CompanionEffectInstance extends EffectInstance
	{
		private final IEffectPool<CompanionEffectProvider> pool;

		private final HashMap<CompanionEffectProvider, EntityCompanion> entities;

		public CompanionEffectInstance(IEffectPool<CompanionEffectProvider> pool)
		{
			this.pool = pool;
			this.entities = new HashMap<>();
		}

		@Override
		public void onEntityUpdate(IPlayerAether player)
		{
			this.entities.forEach((provider, companion) -> {
				if (companion.isDead)
				{
					if (!companion.wasDespawned())
					{
						ItemStack stack = this.pool.getProvider(provider);

						if (stack.getItem() instanceof ItemCompanion)
						{
							ItemCompanion.setRespawnTimer(stack, companion.world, 20 * 60 * 3);
						}
					}
				}
			});
		}

		@Override
		public void onInstanceAdded(IPlayerAether player)
		{
			this.pool.getActiveProviders().forEach(provider -> this.spawnCompanion(player, provider));
		}

		private void spawnCompanion(IPlayerAether player, CompanionEffectProvider provider)
		{
			ItemStack stack = this.pool.getProvider(provider);

			EntityCompanion companion = (EntityCompanion) EntityList.createEntityByIDFromName(provider.name, player.getEntity().getEntityWorld());

			Validate.notNull(companion, "Failed to create companion entity");

			companion.setOwner(player.getEntity());

			if (stack.hasDisplayName())
			{
				companion.setCustomNameTag(stack.getDisplayName());
			}

			this.attemptCompanionPlacement(player, companion);

			player.getEntity().getEntityWorld().spawnEntity(companion);

			this.entities.put(provider, companion);
		}

		@Override
		public void onInstanceRemoved(IPlayerAether player)
		{
			for (EntityCompanion companion : this.entities.values())
			{
				companion.setOwner(null);
				companion.setDespawned(true);
				companion.setDead();
			}

			this.entities.clear();
		}

		private void attemptCompanionPlacement(IPlayerAether player, EntityCompanion companion)
		{
			BlockPos origin = player.getEntity().getPosition();

			Random rand = companion.getRNG();

			// Attempt to find a suitable location for the companion to spawn
			for (int attempts = 0; attempts < 20; attempts++)
			{
				double xOffset = -1.5D + (rand.nextDouble() * 3.0D);
				double zOffset = -1.5D + (rand.nextDouble() * 3.0D);

				companion.setPosition(origin.getX() + xOffset, origin.getY(), origin.getZ() + zOffset);

				if (!companion.getEntityWorld().getBlockState(companion.getPosition().down()).isFullBlock())
				{
					continue;
				}

				if (SpawnHandler.isNotColliding(companion.getEntityWorld(), companion))
				{
					companion.getEntityWorld().updateEntityWithOptionalForce(companion, true);

					return;
				}
			}

			companion.setPositionAndUpdate(player.getEntity().posX, player.getEntity().posY, player.getEntity().posZ);
			companion.getEntityWorld().updateEntityWithOptionalForce(companion, true);
		}

		@Override
		public void addInformation(Collection<String> label)
		{

		}
	}
}
