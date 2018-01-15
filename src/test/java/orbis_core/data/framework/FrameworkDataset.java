package orbis_core.data.framework;

import com.gildedgames.orbis.common.data.framework.FrameworkData;
import com.gildedgames.orbis.common.data.framework.FrameworkNode;
import net.minecraft.util.math.BlockPos;
import orbis_core.data.BlueprintDataset;

public class FrameworkDataset
{
	public static FrameworkData framework1()
	{
		final FrameworkData frameworkData = new FrameworkData();
		final FrameworkNode node1 = frameworkData.addNode(BlueprintDataset.schedule1(), new BlockPos(1, 0, 1));
		final FrameworkNode node2 = frameworkData.addNode(BlueprintDataset.schedule1(), new BlockPos(3, 0, 1));
		final FrameworkNode node3 = frameworkData.addNode(BlueprintDataset.schedule1(), new BlockPos(1, 0, 3));
		final FrameworkNode node4 = frameworkData.addNode(BlueprintDataset.schedule1(), new BlockPos(3, 0, 3));
		final FrameworkNode node5 = frameworkData.addNode(BlueprintDataset.schedule1(), new BlockPos(2, 0, 5));
		final FrameworkNode node6 = frameworkData.addNode(BlueprintDataset.schedule1(), new BlockPos(4, 0, 4));
		final FrameworkNode node7 = frameworkData.addNode(BlueprintDataset.schedule1(), new BlockPos(4, 0, 3));
		final FrameworkNode node8 = frameworkData.addNode(BlueprintDataset.schedule1(), new BlockPos(4, 0, 2));
		final FrameworkNode node9 = frameworkData.addNode(BlueprintDataset.schedule1(), new BlockPos(5, 0, 3));

		frameworkData.addEdge(node1, node2);
		frameworkData.addEdge(node1, node3);
		frameworkData.addEdge(node4, node2);
		frameworkData.addEdge(node3, node4);
		frameworkData.addEdge(node5, node3);
		frameworkData.addEdge(node5, node6);
		frameworkData.addEdge(node4, node6);
		frameworkData.addEdge(node6, node7);
		frameworkData.addEdge(node4, node7);
		frameworkData.addEdge(node7, node9);
		frameworkData.addEdge(node7, node8);
		frameworkData.addEdge(node9, node8);

		//frameworkData.addIntersection(PathwayDataset.pathway1(), PathwayDataset.pathway1(), BlueprintDataset.blueprint1());

		return frameworkData;
	}

}
