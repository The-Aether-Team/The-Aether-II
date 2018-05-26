package com.gildedgames.aether.common.world.aether.island.data;

import com.gildedgames.aether.api.world.islands.IIslandDataPartial;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import com.gildedgames.aether.api.world.islands.precipitation.PrecipitationStrength;
import com.gildedgames.aether.api.world.islands.precipitation.PrecipitationType;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.IBlockSnowy;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketUpdatePrecipitation;
import com.gildedgames.aether.common.util.helpers.WorldUtil;
import com.gildedgames.aether.common.world.aether.prep.PrepSectorDataAether;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

import java.util.Iterator;
import java.util.Random;

public class IslandPrecipitationManager implements IPrecipitationManager
{
	private final World world;

	private final IIslandDataPartial parent;

	/**
	 * Contains the current Linear Congruential Generator seed for block updates. Used with an A value of 3 and a C
	 * value of 0x3c6ef35f, producing a highly planar series of values ill-suited for choosing random blocks in a
	 * 16x128x16 field.
	 */
	protected int updateLCG = (new Random()).nextInt();

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

		// Precipitate for 7000 + rand(30000) ticks
		this.duration = 7000 + this.world.rand.nextInt(30000);

		this.sendUpdates();
	}

	@Override
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
				this.startPrecipitation(this.getPrecipitationTypeForBiome(), PrecipitationStrength.LIGHT);
			}
		}
		// If it is precipitating
		else
		{
			// Make weather clear for 20000 + rand(6000) ticks if the precipitation is ending
			if (this.getRemainingDuration() <= 0)
			{
				this.endPrecipitation(this.world.rand.nextInt(45000) + 15000);
			}
			// Otherwise, if we haven't changed intensity in 4000 ticks and there's still 4000 ticks remaining in the precipitation
			else if (this.getRemainingDuration() > 1000 && this.getTimeSinceLastStrengthChange() > 4000)
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

			// Only iterate through persistent chunks
			for (Iterator<Chunk> iterator = this.world.getPersistentChunkIterable(((WorldServer) this.world).getPlayerChunkMap().getChunkIterator()); iterator
					.hasNext(); )
			{
				Chunk chunk = iterator.next();

				int j = chunk.x * 16;
				int k = chunk.z * 16;

				// Make sure we're in this manager's island bounds
				if (!(this.parent.getBounds().getMinX() <= j && this.parent.getBounds().getMinZ() <= k && this.parent.getBounds().getMaxX() >= j
						&& this.parent.getBounds().getMaxZ() >= k))
				{
					continue;
				}

				// If storm, generate lightning at random positions, closer to entities
				if (this.getStrength() == PrecipitationStrength.STORM && this.world.rand.nextInt(100000) == 0)
				{
					// Random position code I don't quite understand, vanilla does this for random block ticks
					this.updateLCG = this.updateLCG * 3 + 1013904223;
					int l = this.updateLCG >> 2;

					BlockPos blockpos = WorldUtil.adjustPosToNearbyEntity(this.world, new BlockPos(j + (l & 15), 0, k + (l >> 8 & 15)));

					this.world.addWeatherEffect(
							new EntityLightningBolt(this.world, (double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), false));
				}

				if (this.world.rand.nextInt(16) == 0)
				{
					// Random position code I don't quite understand, vanilla does this for random block ticks
					this.updateLCG = this.updateLCG * 3 + 1013904223;
					int j2 = this.updateLCG >> 2;

					BlockPos blockpos1 = this.world.getPrecipitationHeight(new BlockPos(j + (j2 & 15), 0, k + (j2 >> 8 & 15)));
					BlockPos blockpos2 = blockpos1.down();

					// Freeze water
					if (this.world.isAreaLoaded(blockpos2, 1))
					{
						if (this.world.canBlockFreezeNoWater(blockpos2))
						{
							this.world.setBlockState(blockpos2, BlocksAether.highlands_ice.getDefaultState());
						}
					}

					// Generate snow layers
					if (this.getType() == PrecipitationType.SNOW)
					{
						IBlockState state = this.world.getBlockState(blockpos1);

						if (this.world.canSnowAt(blockpos1, true))
						{
							this.world.setBlockState(blockpos1, BlocksAether.highlands_snow_layer.getDefaultState());
						}
						else if (state.getBlock() instanceof IBlockSnowy)
						{
							final IBlockState newState = state.withProperty(IBlockSnowy.PROPERTY_SNOWY, Boolean.TRUE);

							this.world.setBlockState(blockpos1, newState, 2);
						}
					}

					// Fill random blocks with rain, used by Cauldrons.
					if (this.getType() == PrecipitationType.RAIN)
					{
						this.world.getBlockState(blockpos2).getBlock().fillWithRain(this.world, blockpos2);
					}
				}
			}
		}
	}

	@Override
	public float getStrength(float partialTicks)
	{
		float progress;

		if ((this.world.getTotalWorldTime() + partialTicks) - this.start < 200)
		{
			progress = ((this.world.getTotalWorldTime() + partialTicks) - this.start) / 200.0f;
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
				return 0.6f;
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

	@Override
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
			NetworkingAether.sendPacketToDimension(new PacketUpdatePrecipitation((PrepSectorDataAether) this.parent.getParentSectorData()),
					this.world.provider.getDimension());

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
