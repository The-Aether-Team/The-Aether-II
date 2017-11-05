package com.gildedgames.aether.api.orbis_core.api.util;

import com.gildedgames.aether.api.orbis_core.api.BlueprintDefinition;
import com.gildedgames.aether.api.orbis_core.api.ICreationData;
import com.gildedgames.aether.api.orbis_core.api.PostPlacement;
import com.gildedgames.aether.api.orbis_core.processing.DataPrimer;
import com.gildedgames.aether.api.util.BlockAccessExtendedWrapper;
import com.google.common.collect.Lists;
import net.minecraft.util.Rotation;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlueprintPlacer
{

	public static final Rotation[] ROTATIONS = Rotation.values();

	/** Filled with block access instances for each world
	 */
	private static final List<DataPrimer> primers = Lists.newArrayList();

	public static Rotation getRandomRotation(final Random rand)
	{
		return BlueprintPlacer.ROTATIONS[rand.nextInt(BlueprintPlacer.ROTATIONS.length)];
	}

	public static boolean place(final World placeWith, final BlueprintDefinition def, final ICreationData data, final Random rand)
	{
		DataPrimer chosen = null;

		for (final DataPrimer primer : BlueprintPlacer.primers)
		{
			if (primer.getAccess().getWorld() == placeWith)
			{
				chosen = primer;
				break;
			}
		}

		if (chosen == null)
		{
			chosen = new DataPrimer(new BlockAccessExtendedWrapper(placeWith));

			BlueprintPlacer.primers.add(chosen);
		}

		return BlueprintPlacer.place(chosen, def, data, rand);
	}

	public static void placeForced(final DataPrimer placeWith, final BlueprintDefinition def, final ICreationData data, final Random rand)
	{
		placeWith.create(def.getData().getBlockDataContainer(), data);

		if (placeWith.getWorld() != null)
		{
			for (final PostPlacement post : def.getPostPlacements())
			{
				post.postGenerate(placeWith.getWorld(), rand, data, def.getData().getBlockDataContainer());
			}
		}
	}

	public static boolean place(final DataPrimer placeWith, final BlueprintDefinition def, final ICreationData data, final Random rand)
	{
		final boolean result = placeWith.canGenerate(def, data);

		if (result)
		{
			placeWith.create(def.getData().getBlockDataContainer(), data);

			if (placeWith.getWorld() != null)
			{
				for (final PostPlacement post : def.getPostPlacements())
				{
					post.postGenerate(placeWith.getWorld(), rand, data, def.getData().getBlockDataContainer());
				}
			}
		}

		return result;
	}

	public static boolean findPlace(final DataPrimer placeWith, final BlueprintDefinition def, final ICreationData data, final Random rand)
	{
		final Rotation rotation = def.hasRandomRotation() ? ROTATIONS[rand.nextInt(ROTATIONS.length)] : ROTATIONS[0];

		data.set(rotation);

		return placeWith.canGenerate(def, data);
	}

}
