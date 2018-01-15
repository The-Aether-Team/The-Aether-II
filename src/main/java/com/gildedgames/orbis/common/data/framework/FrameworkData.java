package com.gildedgames.orbis.common.data.framework;

import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.orbis.common.data.framework.generation.ComputedParamFac;
import com.gildedgames.orbis.common.data.framework.interfaces.IFrameworkNode;
import com.gildedgames.orbis.common.data.pathway.PathwayData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

/**
 * <p> A Framework is a <strong>connected graph based data structure</strong> that can be generated using
 * Orbis's special Framework algorithm. This can be used to model large
 * collections of structures, such as cities or dungeons.
 * 
 * <p> The graph is build up off of <strong>nodes and edges</strong> connecting the nodes. 
 * Edges represent that a pathway will be generated between the buildings 
 * represented by the nodes. The nodes then are 
 * saved in the {@link FrameworkNode FrameworkNode} class. First of 
 * all, they have some sort of data inside of them. Right now, this can
 * be a <tt>ScheduleData</tt> or another <tt>FrameworkData</tt>.
 * The nodes also have an approximate position. This gives their position
 * in 3D space at the start of the algorithm. If these positions are
 * accurate, in that they form an optimally drawn graph, it can greatly 
 * improve the performance and quality of the end result.
 * 
 * <p> This class contains Conditions on how the various nodes are going to
 * turn out. This can be used to model relations such as that each node
 * needs to choose a different <tt>BlueprintData</tt>.
 * 
 * <p> There are two {@link FrameworkType types} of Frameworks, a 2D one
 * called rectangles and a 3D one called cubes.  
 * 
 * <p> When the FrameworkData generates, it's possible that there are <strong>intersections</strong>
 * between two edges in the 2D case. When this happens, the algorithm adds a new node, 
 * called an intersection. What Schedule is behind this intersection 
 * also needs to be chosen.
 * 
 * <p> <tt>FrameworkData</tt> also contains a lot of parameters. They are
 * created with the <tt>paramFac</tt> as an <tt>IFrameworkParams</tt>. 
 * It changes strongly how the <tt>FrameworkAlgorithm</tt> is going to run.
 * 
 * <p> It is very important that at all times the graph behind the Framework 
 * is <strong>connected</strong>. This means that there is a path over the
 * Edges between each node
 * 
 * @author Emile
 * 
 * @see FrameworkAlgorithm
 * @see FrameworkNode
 * @see FrameworkType
 * @see PathwayData
 *
 */
public class FrameworkData implements IFrameworkNode
{

	private final FrameworkType type = FrameworkType.RECTANGLES;

	/**
	 * The underlying graph of a Framework. It is an undirected graph with at most
	 * one edge between its nodes.  
	 */
	protected final Graph<FrameworkNode, FrameworkEdge> graph = new Graph<FrameworkNode, FrameworkEdge>();

	/**
	 * The list of all conditions on the nodes.
	 */
//	protected final List<Condition<Object>> conditions = new ArrayList<Condition<Object>>();

	/**
	 * Creates the parameters for the algorithm.
	 */
	private final ComputedParamFac paramFac = new ComputedParamFac();

	/**
	 * A map that contains what blueprint to use when two pathways intersect. This is only necessary
	 * when {@link #type the FrameworkType} is {@link FrameworkType#RECTANGLES Rectangles}.
	 * All <tt>BlueprintData</tt> in this map need at least 4 {@link Entrance connections}.
	 */
//	private final DoubleKeyMap<PathwayData, BlueprintData> intersections = new DoubleKeyMap<PathwayData, BlueprintData>();

	/**
	 * Executes the Framework algorithm and returns a list with blueprints and positions
	 * in a <tt>GeneratedFramework</tt>.
	 * 
	 * @see FrameworkAlgorithm
	 *
	 * @param world The world we want to generate this Framework in. Used for checking conditions
	 * and as the height map.
	 * @param pos Not sure yet :/
	 * @param options The options for generation.
	 * @return The list with chosen blueprints and positions for them.
	 */
	public GeneratedFramework prepare(World world, BlockPos pos)
	{
		//TODO: What really does the pos here represent? We need the pos to make sure the Framework
		//shapes well around the terrain, but where can it actually generate at all?

		final FrameworkAlgorithm algorithm = new FrameworkAlgorithm(this, this.paramFac, world);
		return algorithm.computeFully();
	}

	/**
	 * Finds the node with the given BlockPos as its approximate position. 
	 * @see FrameworkNode#approxPosition()
	 */
	public FrameworkNode nodeAt(BlockPos approxPos)
	{
		for (final FrameworkNode node : this.graph.vertexSet())
		{
			if (node.approxPosition().equals(approxPos))
			{
				return node;
			}
		}
		return null;
	}

	public FrameworkEdge edgeBetween(FrameworkNode node1, FrameworkNode node2)
	{
		return this.graph.getEdge(node1, node2);
	}

	/**
	 * <p>Adds a node to the Framework. Throws an <tt>IllegalStateException</tt>
	 * when there is already a node on the given position. Nodes should
	 * always be added to the Framework before the edges.
	 * 
	 * <p>Note that adding a node destroys the connectivity property of the graph,
	 * unless it is the very first one. After the node is added, 
	 * 
	 * @param data The data inside of this node. Right now, this can be
	 * a {@link ScheduleData ScheduleData} or another Framework.
	 * @param approxPos See {@link FrameworkNode#approxPosition() here}.
	 * @return The created FrameworkNode
	 */
	public FrameworkNode addNode(IFrameworkNode data, BlockPos approxPos)
	{
		final FrameworkNode oldNode = this.nodeAt(approxPos);
		if (oldNode != null)
		{
			throw new IllegalStateException("Tried to add a node on a position that's already taken");
		}
		final FrameworkNode newNode = new FrameworkNode(data, approxPos);
		this.graph.addVertex(newNode);
		return newNode;
	}

	/**
	 * Adds an edge between two nodes. When the two nodes given are not
	 * yet added to the framework, this returns false. Furthermore, if no
	 * more edges are allowed for one of the two nodes, it does the same.
	 * @return True if the edge was successfully added
	 */
	public boolean addEdge(FrameworkNode node1, FrameworkNode node2)
	{
		if (!this.graph.containsVertex(node1) || !this.graph.containsVertex(node2))
		{
			return false;
		}
		if (this.graph.edgesOf(node1).size() >= node1.schedule().maxEdges() || this.graph.edgesOf(node2).size() >= node2.schedule().maxEdges())
		{
			return false;
		}
		final FrameworkEdge edge = new FrameworkEdge(node1, node2);
		this.graph.addEdge(node1, node2, edge);
		return true;
	}

	/**
	 * Adds an intersection blueprint for when the two given Pathways
	 * intersect. The blueprint needs at least 4 Entrances, otherwise
	 * this will throw an IllegalArgumentException.
	 */
	public void addIntersection(PathwayData pathway1, PathwayData pathway2, BlueprintData blueprint)
	{
//		if (blueprint.entrances().size() < 4)
//		{
//			throw new IllegalArgumentException("Can only have intersection blueprints with 4 or more entrances");
//		}
//		this.intersections.put(pathway1, pathway2, blueprint);
	}

	public FrameworkType getType()
	{
		return this.type;
	}

	@Override
	public int maxEdges()
	{
		// TODO Auto-generated method stub
		return 100;
	}

//	public BlueprintData getIntersection(PathwayData pathway1, PathwayData pathway2)
//	{
//		return this.intersections.get(pathway1, pathway2);
//	}

	private final static Object stub = "aweoigh";

	@Override
	public List<BlueprintData> possibleValues(Random random)
	{
		//TODO
		return new ArrayList<BlueprintData>();
	}

	@Override
	public Collection<PathwayData> pathways()
	{
		final Set<PathwayData> schedules = new HashSet<PathwayData>();
		for (final FrameworkNode node : this.graph.vertexSet())
		{
			schedules.addAll(node.pathways());
		}
		return schedules;
	}

}
