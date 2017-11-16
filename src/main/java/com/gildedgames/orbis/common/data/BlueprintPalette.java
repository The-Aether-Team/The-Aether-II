package com.gildedgames.orbis.common.data;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingDataException;
import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingProjectException;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.aether.api.orbis_core.data.DataCondition;
import com.gildedgames.aether.api.orbis_core.data.management.IDataIdentifier;
import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.orbis.common.Orbis;
import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * TODO: MOVE THIS INTO API PACKAGE AND HAVE A UNIVERSAL PROJECT MANAGER. VERY IMPORTANT.
 *
 * Right now project management is only a part of the Orbis interface itself
 * (the tool), instead of the API. This means that palettes and other objects
 * from the API package cannot attempt to find data.
 */
public class BlueprintPalette implements NBT
{

	private final Map<IDataIdentifier, BlueprintData> data = Maps.newHashMap();

	private LinkedHashMap<IDataIdentifier, DataCondition> idToConditions = Maps.newLinkedHashMap();

	private BlueprintData largestInArea;

	public BlueprintPalette()
	{

	}

	public Collection<IDataIdentifier> getIDs()
	{
		return this.idToConditions.keySet();
	}

	public Map<IDataIdentifier, DataCondition> getIDToConditions()
	{
		return this.idToConditions;
	}

	public BlueprintData fetchRandom(final World world, final Random rand)
	{
		final float randomValue = rand.nextFloat() * this.totalChance();
		float chanceSum = 0.0f;

		for (final Map.Entry<IDataIdentifier, DataCondition> pair : this.idToConditions.entrySet())
		{
			final DataCondition condition = pair.getValue();

			if (condition.isMet(randomValue, chanceSum, rand, world))
			{
				return this.data.get(pair.getKey());
			}

			chanceSum += condition.getWeight();
		}

		return null;
	}

	public float totalChance()
	{
		float total = 0f;

		for (final Map.Entry<IDataIdentifier, DataCondition> pair : this.idToConditions.entrySet())
		{
			final DataCondition condition = pair.getValue();

			total += condition.getWeight();
		}

		return total;
	}

	public Collection<BlueprintData> getData()
	{
		return this.data.values();
	}

	public BlueprintData getLargestInArea()
	{
		return this.largestInArea;
	}

	private void evaluateLargestInArea()
	{
		BlueprintData largestInArea = null;

		for (final BlueprintData blueprint : this.data.values())
		{
			if (largestInArea == null || (blueprint.getHeight() >= largestInArea.getHeight() && blueprint.getHeight() >= largestInArea.getHeight()
					&& blueprint.getLength() >= largestInArea.getLength()))
			{
				largestInArea = blueprint;
			}
		}

		this.largestInArea = largestInArea;
	}

	public void add(final BlueprintData data, final DataCondition condition)
	{
		this.idToConditions.put(data.getMetadata().getIdentifier(), condition);
		this.data.put(data.getMetadata().getIdentifier(), data);

		this.evaluateLargestInArea();
	}

	public void remove(final BlueprintData data)
	{
		IDataIdentifier toRemove = null;

		for (final Map.Entry<IDataIdentifier, DataCondition> pair : this.idToConditions.entrySet())
		{
			if (pair.getKey().equals(data.getMetadata().getIdentifier()))
			{
				toRemove = pair.getKey();
				break;
			}
		}

		if (toRemove == null)
		{
			return;
		}

		this.data.remove(toRemove);
		this.idToConditions.remove(toRemove);

		this.evaluateLargestInArea();
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		funnel.setMap("idToConditions", this.idToConditions);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		this.idToConditions = Maps.newLinkedHashMap(funnel.getMap("idToConditions"));

		for (final Map.Entry<IDataIdentifier, DataCondition> pair : this.idToConditions.entrySet())
		{
			try
			{
				final IDataIdentifier id = pair.getKey();
				final BlueprintData data = Orbis.getProjectManager().findData(id);

				this.data.put(id, data);
			}
			catch (final OrbisMissingDataException | OrbisMissingProjectException e)
			{
				OrbisCore.LOGGER.error(e);
			}
		}

		this.evaluateLargestInArea();
	}

	@Override
	public int hashCode()
	{
		final HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(this.idToConditions);

		return builder.toHashCode();
	}
}
