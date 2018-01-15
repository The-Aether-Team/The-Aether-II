package orbis_core.data;

import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.orbis.common.data.framework.interfaces.IFrameworkNode;
import com.gildedgames.orbis.common.data.pathway.PathwayData;

import java.util.*;

public class ScheduleData implements IFrameworkNode
{
	List<BlueprintData> blueprints;

	public ScheduleData(List<BlueprintData> blueprints)
	{
		this.blueprints = blueprints;
	}
	@Override
	public int maxEdges()
	{
		return 100;
	}

	@Override
	public List<BlueprintData> possibleValues(Random random)
	{
		Collections.shuffle(this.blueprints, random);
		return new ArrayList<>(this.blueprints);
	}

	@Override
	public Collection<PathwayData> pathways()
	{
		return new ArrayList<>();
	}
}
