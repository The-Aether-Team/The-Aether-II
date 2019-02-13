package com.gildedgames.aether.api.world.generation;

import com.gildedgames.orbis_api.core.BlueprintDefinition;
import com.gildedgames.orbis_api.core.CreationData;
import com.gildedgames.orbis_api.core.ICreationData;
import com.gildedgames.orbis_api.core.baking.BakedBlueprint;
import com.gildedgames.orbis_api.core.util.BlueprintPlacer;
import com.gildedgames.orbis_api.processing.BlockAccessWorldSlice;
import com.gildedgames.orbis_api.processing.DataPrimer;
import com.gildedgames.orbis_api.world.WorldSlice;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class BlueprintWorldGen implements WorldDecorationGenerator
{
	private final BlueprintDefinition def;

	private BakedBlueprint baked;

	public BlueprintWorldGen(final BlueprintDefinition def)
	{
		this.def = def;
	}

	@Override
	public boolean generate(WorldSlice slice, Random rand, BlockPos pos)
	{
		if (this.baked == null)
		{
			final Rotation rotation = this.def.hasRandomRotation() ? BlueprintPlacer.getRandomRotation(rand) : Rotation.NONE;

			final ICreationData<CreationData> data = new CreationData(slice.getWorld());
			data.rotation(rotation);

			this.baked = new BakedBlueprint(this.def, data);
		}

		// We should choose a better location for blueprints which generate at an offset
		BlockPos offsetPosition = pos.up(this.def.getFloorHeight());

		DataPrimer primer = new DataPrimer(new BlockAccessWorldSlice(slice));
		boolean result = primer.canGenerate(this.baked, offsetPosition);

		if (result)
		{
			primer.place(this.baked, offsetPosition);

			this.baked = null;
		}

		return result;
	}
}