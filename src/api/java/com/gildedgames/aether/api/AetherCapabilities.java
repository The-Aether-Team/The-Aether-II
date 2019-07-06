package com.gildedgames.aether.api;

import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.effects_system.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.world.ISpawnSystem;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

public class AetherCapabilities
{
	@CapabilityInject(IPlayerAether.class)
	public static final Capability<IPlayerAether> PLAYER_DATA = AetherCapabilities.getDefault();

	@CapabilityInject(ISpawningInfo.class)
	public static final Capability<ISpawningInfo> ENTITY_SPAWNING_INFO = AetherCapabilities.getDefault();

	@CapabilityInject(IPlacementFlagCapability.class)
	public static final Capability<IPlacementFlagCapability> CHUNK_PLACEMENT_FLAG = AetherCapabilities.getDefault();

	@CapabilityInject(ISpawnSystem.class)
	public static final Capability<ISpawnSystem> SPAWN_SYSTEM = AetherCapabilities.getDefault();

	@CapabilityInject(IPrecipitationManager.class)
	public static final Capability<IPrecipitationManager> PRECIPITATION_MANAGER = AetherCapabilities.getDefault();

	@CapabilityInject(IAetherStatusEffectPool.class)
	public static final Capability<IAetherStatusEffectPool> STATUS_EFFECT_POOL = AetherCapabilities.getDefault();

	/**
	 * This method doesn't make much sense at first glance. We're using it to trick IDEs into thinking
	 * that the fields above will *not* be set to null by annotating this method as never to be null.
	 */
	@SuppressWarnings("ConstantConditions")
	@Nonnull
	private static <T> Capability<T> getDefault()
	{
		return null;
	}

	/**
	 * Validates that all declared fields in this class are non-null.
	 */
	public static void validateInjections()
	{
		for (Field field : AetherCapabilities.class.getFields())
		{
			// We only care about fields which define a Capability.
			if (field.getType() != Capability.class)
			{
				continue;
			}

			try
			{
				if (field.get(null) == null)
				{
					throw new IllegalStateException("Field " + field.getName() + " is null (was it not registered?)");
				}
			}
			catch (IllegalAccessException e)
			{
				throw new RuntimeException("Could not use reflection to access field: " + field.getName(), e);
			}
		}
	}

}
