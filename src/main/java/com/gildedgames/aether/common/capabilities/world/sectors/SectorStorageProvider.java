package com.gildedgames.aether.common.capabilities.world.sectors;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.world.ISectorAccess;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;

public class SectorStorageProvider implements ICapabilityProvider
{
	private final ISectorAccess access;

	public SectorStorageProvider(World world)
	{
		// Not a great way to go about this
		File dir = new File(world.getSaveHandler().getWorldDirectory(), world.provider.getSaveFolder() + "/data/aether/islands");

		this.access = new IslandSectorAccessFlatFile(world, dir);
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
	{
		return capability == AetherCapabilities.SECTOR_ACCESS;
	}

	@Nullable
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
	{
		if (this.hasCapability(capability, facing))
		{
			return (T) this.access;
		}

		return null;
	}
}
