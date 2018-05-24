package com.gildedgames.aether.common.world.aether.island.data;

import com.gildedgames.aether.api.world.islands.IIslandDataPartial;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import com.gildedgames.aether.api.world.islands.precipitation.PrecipitationStrength;
import com.gildedgames.aether.api.world.islands.precipitation.PrecipitationType;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketUpdatePrecipitation;
import com.gildedgames.aether.common.world.aether.prep.PrepSectorDataAether;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class IslandPrecipitationManager implements IPrecipitationManager
{
	private final World world;

	private final IIslandDataPartial parent;

	private PrecipitationType type;

	private PrecipitationStrength strength;

	private long start, duration, lastStrengthChange;

	public IslandPrecipitationManager(World world, IIslandDataPartial parent)
	{
		this.world = world;
		this.parent = parent;

		this.type = PrecipitationType.NONE;
		this.strength = PrecipitationStrength.LIGHT;

		this.start = this.world.getTotalWorldTime();
		this.lastStrengthChange = this.world.getTotalWorldTime();
		this.duration = 20000;

		this.lastStrengthChange = this.start;
	}

	@Override
	public void startPrecipitation(PrecipitationType type, PrecipitationStrength strength)
	{
		this.type = type;
		this.strength = strength;

		this.start = this.world.getTotalWorldTime();
		this.lastStrengthChange = this.start;

		// Precipitate for 3600 + rand(16000) ticks
		this.duration = 3600 + this.world.rand.nextInt(16000);

		this.sendUpdates();
	}

	public void tick()
	{
		// Clients never update their own weather
		if (this.world.isRemote)
		{
			return;
		}

		// If it's not precipitating
		if (this.type == PrecipitationType.NONE)
		{
			// And it's time to change weather
			if (this.getRemainingDuration() <= 0)
			{
				// Set to light precipitate with a 60% chance
				if (this.world.rand.nextInt(100) > 40)
				{
					this.startPrecipitation(this.getPrecipitationTypeForBiome(), PrecipitationStrength.LIGHT);
				}
				// Or wait another 6000 + rand(2000) ticks
				else
				{
					this.endPrecipitation(this.world.rand.nextInt(6000) + 2000);
				}
			}
		}
		// If it is precipitating
		else
		{
			// Make weather clear for 20000 + rand(6000) ticks if the precipitation is ending
			if (this.getRemainingDuration() <= 0)
			{
				this.endPrecipitation(this.world.rand.nextInt(20000) + 6000);
			}
			// Otherwise, if we haven't changed intensity in 4000 ticks and there's still 4000 ticks remaining in the precipitation
			else if (this.getRemainingDuration() > 4000 && this.getTimeSinceLastStrengthChange() > 4000)
			{
				// Intensify with a 30% chance
				boolean intensify = this.world.rand.nextInt(100) > 70;

				// Only intensify if we're not already at the highest intensity
				if (this.getStrength() != PrecipitationStrength.STORM && intensify)
				{
					this.modifyStrength(1);
				}
				// Only weaken if we're not already at the lowest intensity
				else if (this.getStrength() != PrecipitationStrength.LIGHT)
				{
					this.modifyStrength(-1);
				}
			}
		}
	}

	@Override
	public float getStrength(float partialTicks)
	{
		float progress;

		if ((this.world.getTotalWorldTime() + partialTicks) - start < 200)
		{
			progress = ((this.world.getTotalWorldTime() + partialTicks) - start) / 200.0f;
		}
		else if ((this.start + this.duration) - (this.world.getTotalWorldTime() + partialTicks) < 200)
		{
			progress = ((this.start + this.duration) - (this.world.getTotalWorldTime() + partialTicks)) / 200.0f;
		}
		else
		{
			progress = 1.0f;
		}

		return progress;
	}

	@Override
	public float getSkyDarkness()
	{
		switch (this.getStrength())
		{
			case LIGHT:
				return 0.1f;
			case HEAVY:
				return 0.3f;
			case STORM:
				return 0.7f;
		}

		return 0.0f;
	}


	private void endPrecipitation(int duration)
	{
		this.type = PrecipitationType.NONE;
		this.strength = PrecipitationStrength.LIGHT;

		this.duration = duration;

		this.start = this.world.getTotalWorldTime();
		this.lastStrengthChange = this.start;

		this.sendUpdates();
	}

	public long getRemainingDuration()
	{
		return (this.start + this.duration) - this.world.getTotalWorldTime();
	}

	public long getTimeSinceLastStrengthChange()
	{
		return this.world.getTotalWorldTime() - this.lastStrengthChange;
	}

	private void modifyStrength(int mod)
	{
		this.strength = PrecipitationStrength.VALUES[(this.strength.ordinal() + mod) % PrecipitationStrength.VALUES.length];
		this.lastStrengthChange = this.world.getTotalWorldTime();

		this.sendUpdates();
	}

	private void sendUpdates()
	{
		if (!this.world.isRemote)
		{
			NetworkingAether.sendPacketToDimension(new PacketUpdatePrecipitation((PrepSectorDataAether) this.parent.getParentSectorData()), this.world.provider.getDimension());

			this.parent.getParentSectorData().markDirty();
		}
	}

	private PrecipitationType getPrecipitationTypeForBiome()
	{
		return this.parent.getBiome().isSnowyBiome() ? PrecipitationType.SNOW : PrecipitationType.RAIN;
	}

	@Override
	public PrecipitationType getType()
	{
		return this.type;
	}

	@Override
	public PrecipitationStrength getStrength()
	{
		return this.strength;
	}

	@Override
	public long getTimeStart()
	{
		return this.start;
	}

	@Override
	public long getTimeEnd()
	{
		return this.start + this.duration;
	}

	@Override
	public long getDuration()
	{
		return this.duration;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("Type", this.type.name());
		tag.setString("Strength", this.strength.name());
		tag.setLong("Duration", this.duration);
		tag.setLong("Start", this.start);
		tag.setLong("LastStrengthChange", this.lastStrengthChange);

		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		this.type = PrecipitationType.lookup(nbt.getString("Type"));
		this.strength = PrecipitationStrength.lookup(nbt.getString("Strength"));
		this.start = nbt.getLong("Start");
		this.duration = nbt.getLong("Duration");
		this.lastStrengthChange = nbt.getLong("LastStrengthChange");
	}
}
