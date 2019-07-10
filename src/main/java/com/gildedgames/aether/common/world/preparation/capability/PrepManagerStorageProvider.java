package com.gildedgames.aether.common.world.preparation.capability;

import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.api.world.preparation.IPrepManager;
import com.gildedgames.aether.api.world.preparation.IPrepRegistryEntry;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PrepManagerStorageProvider implements ICapabilityProvider
{
	private final World world;

	private IPrepRegistryEntry entry;

	private IPrepManager manager;

	public PrepManagerStorageProvider(World world, IPrepRegistryEntry entry)
	{
		this.world = world;
		this.entry = entry;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
	{
		return capability == CapabilitiesAether.PREP_MANAGER;
	}

	@Nullable
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
	{
		if (this.hasCapability(capability, facing))
		{
			if (this.manager == null)
			{
				this.manager = new PrepManager(this.world, this.entry);
			}

			return (T) this.manager;
		}

		return null;
	}
}