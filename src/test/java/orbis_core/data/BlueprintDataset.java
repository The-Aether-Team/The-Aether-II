package orbis_core.data;

import com.gildedgames.aether.api.orbis_core.block.BlockDataContainer;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;

import java.util.ArrayList;
import java.util.List;

public class BlueprintDataset
{
	public static ScheduleData schedule1()
	{
		List<BlueprintData> b = new ArrayList<>();
		b.add(blueprint1());
		b.add(blueprint2());
		b.add(blueprint3());
		return new ScheduleData(b);
	}

	private static BlueprintData blueprint1;

	public static BlueprintData blueprint1()
	{
		if (blueprint1 != null)
		{
			return blueprint1;
		}
		final BlockDataContainer container = new BlockDataContainer(5, 2, 5);
		blueprint1 = new BlueprintData(container);

		return blueprint1;
	}

	private static BlueprintData blueprint2;

	public static BlueprintData blueprint2()
	{
		if (blueprint2 != null)
		{
			return blueprint2;
		}
		final BlockDataContainer container = new BlockDataContainer(4, 5, 12);
		blueprint2 = new BlueprintData(container);

		return blueprint2;
	}

	private static BlueprintData blueprint3;

	public static BlueprintData blueprint3()
	{
		if (blueprint3 != null)
		{
			return blueprint3;
		}
		final BlockDataContainer container = new BlockDataContainer(4, 5, 10);
		blueprint3 = new BlueprintData(container);

//		blueprint3.addEntrance(new BlockPos(2, 1, 0), PathwayDataset.pathway1());
//		blueprint3.addEntrance(new BlockPos(2, 1, 9), PathwayDataset.pathway1());
//		blueprint3.addEntrance(new BlockPos(3, 1, 4), PathwayDataset.pathway1());
//		blueprint3.addEntrance(new BlockPos(3, 1, 8), PathwayDataset.pathway1());

		return blueprint3;
	}
}
