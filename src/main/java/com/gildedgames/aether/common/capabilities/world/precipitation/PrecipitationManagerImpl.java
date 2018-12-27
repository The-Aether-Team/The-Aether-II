package com.gildedgames.aether.common.capabilities.world.precipitation;

import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import com.gildedgames.aether.api.world.islands.precipitation.PrecipitationStrength;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketUpdatePrecipitation;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.biomes.ISnowyBiome;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Random;

// TODO: Handle rain start/end events better
public class PrecipitationManagerImpl implements IPrecipitationManager
{
	private int updateLCG = (new Random()).nextInt();

	@Nonnull
	private final World world;

	private PrecipitationStrength strength;

	private long ticksUntilStrengthChange;

	private boolean prevIsRaining;

	public PrecipitationManagerImpl()
	{
		this.world = null;
	}

	public PrecipitationManagerImpl(World world)
	{
		this.world = world;

		this.strength = PrecipitationStrength.LIGHT;
	}

	@Override
	public void tick()
	{
		// Clients never update
		if (this.world.isRemote)
		{
			return;
		}

		this.updateWeather();
		this.updateWorld();
		this.updateBlocks();
	}

	private void updateWeather()
	{
		WorldInfo info = this.world.getWorldInfo();

		int ticksUntilRain = info.getRainTime();
		int ticksUntilClear = info.getCleanWeatherTime();

		if (ticksUntilClear > 0)
		{
			info.setCleanWeatherTime(ticksUntilClear - 1);
		}

		if (ticksUntilRain > 0)
		{
			info.setRainTime(ticksUntilRain - 1);
		}

		if (this.ticksUntilStrengthChange > 0)
		{
			this.ticksUntilStrengthChange--;
		}

		// The world just started raining and we need to setup NOW
		if (info.isRaining() && !this.prevIsRaining)
		{
			this.startPrecipitation();
		}
		// If it's not already precipitating
		else if (!info.isRaining())
		{
			// And it's time to change weather
			if (ticksUntilRain <= 0)
			{
				this.startPrecipitation();
			}
		}

		// If it is precipitating
		if (info.isRaining())
		{
			// Make weather clear for 20000 + rand(6000) ticks if the precipitation is ending
			if (ticksUntilClear <= 0)
			{
				this.endPrecipitation();
			}
			// Otherwise, if we haven't changed intensity in 4000 ticks and there's still 4000 ticks remaining in the precipitation
			else if (this.ticksUntilStrengthChange <= 0 && ticksUntilClear > 4000)
			{
				// Intensify with a 30% chance
				int rng = this.world.rand.nextInt(100);

				// Only intensify if we're not already at the highest intensity
				if (this.getStrength() != PrecipitationStrength.STORM && rng > 75)
				{
					this.modifyStrength(1);
				}
				// Only weaken if we're not already at the lowest intensity
				else if (this.getStrength() != PrecipitationStrength.LIGHT && rng > 50)
				{
					this.modifyStrength(-1);
				}
				else
				{
					this.modifyStrength(0);
				}
			}
		}

		this.prevIsRaining = info.isRaining();
	}

	private void updateWorld()
	{
		WorldInfo info = this.world.getWorldInfo();

		this.world.prevThunderingStrength = this.world.thunderingStrength;

		if (info.isThundering())
		{
			this.world.thunderingStrength = (float) ((double) this.world.thunderingStrength + 0.01D);
		}
		else
		{
			this.world.thunderingStrength = (float) ((double) this.world.thunderingStrength - 0.01D);
		}

		this.world.thunderingStrength = MathHelper.clamp(this.world.thunderingStrength, 0.0F, 1.0F);
		this.world.prevRainingStrength = this.world.rainingStrength;

		if (info.isRaining())
		{
			this.world.rainingStrength = (float) ((double) this.world.rainingStrength + 0.01D);
		}
		else
		{
			this.world.rainingStrength = (float) ((double) this.world.rainingStrength - 0.01D);
		}

		this.world.rainingStrength = MathHelper.clamp(this.world.rainingStrength, 0.0F, 1.0F);
	}

	private void updateBlocks()
	{
		WorldInfo info = this.world.getWorldInfo();

		if (!info.isRaining())
		{
			return;
		}

		Iterator<Chunk> iterator = this.world.getPersistentChunkIterable(((WorldServer) this.world).getPlayerChunkMap().getChunkIterator());

		while (iterator.hasNext())
		{
			Chunk chunk = iterator.next();

			this.updateBlocksForChunk(chunk);

			this.world.profiler.endSection();
		}
	}

	private void updateBlocksForChunk(Chunk chunk)
	{
		int j = chunk.x * 16;
		int k = chunk.z * 16;

		if (this.world.rand.nextInt(16) == 0)
		{
			this.updateLCG = this.updateLCG * 3 + 1013904223;

			int j2 = this.updateLCG >> 2;

			BlockPos column = new BlockPos(j + (j2 & 15), 0, k + (j2 >> 8 & 15));

			BlockPos topBlockPos = this.world.getPrecipitationHeight(column);
			BlockPos blockPos = topBlockPos.down();

			Biome biome = this.world.getBiome(column);

			if (biome instanceof ISnowyBiome)
			{
				ISnowyBiome snowyBiome = (ISnowyBiome) biome;

				if (this.world.isAreaLoaded(blockPos, 1))
				{
					if (this.world.canBlockFreezeNoWater(blockPos))
					{
						this.world.setBlockState(blockPos, snowyBiome.getFrozenWaterBlock());
					}
				}

				if (this.world.canSnowAt(topBlockPos, true))
				{
					this.world.setBlockState(topBlockPos, snowyBiome.getSnowBlock());
				}
			}

			if (biome instanceof BiomeAetherBase && this.world.getBiome(blockPos).canRain())
			{
				this.world.getBlockState(blockPos).getBlock().fillWithRain(this.world, blockPos);
			}
		}
	}

	private void startPrecipitation()
	{
		int duration = this.world.rand.nextInt(20000) + 10000;

		this.strength = PrecipitationStrength.VALUES[this.world.rand.nextInt(PrecipitationStrength.VALUES.length)];

		WorldInfo info = this.world.getWorldInfo();
		info.setRaining(true);
		info.setCleanWeatherTime(duration);

		this.sendUpdates();
	}

	private void endPrecipitation()
	{
		int duration = this.world.rand.nextInt(20000) + 6000;

		this.strength = PrecipitationStrength.LIGHT;

		WorldInfo info = this.world.getWorldInfo();
		info.setRaining(false);
		info.setRainTime(duration);

		this.sendUpdates();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float getSkyDarkness()
	{
		switch (this.getStrength())
		{
			case LIGHT:
				return 0.15f;
			case HEAVY:
				return 0.35f;
			case STORM:
				return 0.7f;
		}

		return 0.0f;
	}

	private void modifyStrength(int mod)
	{
		this.strength = PrecipitationStrength.VALUES[(this.strength.ordinal() + mod) % PrecipitationStrength.VALUES.length];
		this.ticksUntilStrengthChange = this.world.rand.nextInt(2000) + 1000;

		this.sendUpdates();
	}

	private void sendUpdates()
	{
		if (!this.world.isRemote)
		{
			NetworkingAether.sendPacketToDimension(new PacketUpdatePrecipitation(this), this.world.provider.getDimension());
		}
	}

	@Override
	public PrecipitationStrength getStrength()
	{
		return this.strength;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("Strength", this.strength.name());
		tag.setLong("TicksUntilStrengthChange", this.ticksUntilStrengthChange);

		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		this.strength = PrecipitationStrength.lookup(nbt.getString("Strength"));
		this.ticksUntilStrengthChange = nbt.getLong("TicksUntilStrengthChange");
	}

	public static class Storage implements Capability.IStorage<IPrecipitationManager>
	{
		@Nullable
		@Override
		public NBTBase writeNBT(Capability<IPrecipitationManager> capability, IPrecipitationManager instance, EnumFacing side)
		{
			return instance.serializeNBT();
		}

		@Override
		public void readNBT(Capability<IPrecipitationManager> capability, IPrecipitationManager instance, EnumFacing side, NBTBase nbt)
		{
			instance.deserializeNBT((NBTTagCompound) nbt);
		}
	}
}
