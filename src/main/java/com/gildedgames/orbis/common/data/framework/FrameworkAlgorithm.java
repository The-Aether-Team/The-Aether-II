package com.gildedgames.orbis.common.data.framework;

import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.aether.api.orbis_core.util.RegionHelp;
import com.gildedgames.orbis.common.data.framework.generation.*;
import com.gildedgames.orbis.common.data.pathway.PathwayData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.*;

public class FrameworkAlgorithm
{
	private final FrameworkData framework;

	private Graph<FDGDNode, FDGDEdge> fdgdGraph;

	private boolean finished;

	private Phase phase = Phase.CSP;

	private boolean escapePhase;

	private int fdgdIterations = 0;

	private World world;

//	private StepAStar<PathwayNode> pathfindingSolver;

	private Iterator<FDGDEdge> edgeIterator;

//	private List<FrameworkFragment> fragments;

	private FrameworkParams params;

	private ComputedParamFac paramFac;

	private Random random;

	public FrameworkAlgorithm(FrameworkData data, ComputedParamFac params, World world)
	{
		this.framework = data;
		this.paramFac = params;
		this.world = world;
		if (world == null)
		{
			this.random = new Random();
		}
		else
		{
			this.random = world.rand;
		}
	}

	public GeneratedFramework computeFully() throws FailedToGenerateException
	{
		while (!this.step())
		{
			;
		}
		return this.getResult();
	}

	/**
	 * Can only be called after the algorithm is finished!
	 */
	public GeneratedFramework getResult()
	{
		return null;
	}

	/**
	 * Step the algorithm. Returns true if it has finished
	 */
	public boolean step() throws FailedToGenerateException
	{
		if (this.phase == Phase.CSP)
		{
//			this.solveCSP();
			this.initialGraph();
//			this.assignConnections();
			this.phase = Phase.FDGD;
			this.params = this.paramFac.createParams(this.fdgdGraph, this.framework.getType());
			return false;
		}

		if (this.phase == Phase.FDGD)
		{
			this.iterationFDGD(this.fdgdGraph, this.framework.getType(), false);
			this.fdgdIterations++;

			if (this.fdgdIterations < this.params.fdgdMaxIterations())
			{
				//Maximum distance between a node in the previous step and in the current one.
				float equilibriumState = 0;
				for (final FDGDNode node : this.fdgdGraph.vertexSet())
				{
					equilibriumState = Math.max(equilibriumState, FDGenUtil.euclidian(
							node.getPrevX(), node.getPrevY(), node.getPrevZ(), node.getX(), node.getY(), node.getZ()));
				}

				final boolean inEquilibrium = this.escapePhase && equilibriumState < this.params.acceptEquilibriumEsc()
						|| equilibriumState < this.params.acceptEquilibrium();
				final boolean checkCollis = equilibriumState > 0.15f || this.fdgdIterations > this.params.iterationsToEscape();

				if (inEquilibrium && (!checkCollis || !FDGenUtil.hasCollision(this.fdgdGraph)))
				{
					if (this.framework.getType() == FrameworkType.CUBES || !FDGenUtil.hasEdgeIntersections(this.fdgdGraph))
					{
						//TODO: Might need to be uncommented? Seeing issues
						for (final FDGDNode node : this.fdgdGraph.vertexSet())
						{
							node.assignConnectionsFixRot(this.fdgdGraph.edgesOf(node));
						}

						this.phase = Phase.PATHWAYS;

//						this.fragments = new ArrayList<FrameworkFragment>(this.fdgdGraph.vertexSet().size());
//						for (FDGDNode node : this.fdgdGraph.vertexSet())
//						{
//							this.fragments.add(new FrameworkFragment(node.getData(), node.getRotation(), node.getMin()));
//						}
//						this.edgeIterator = this.fdgdGraph.edgeSet().iterator();
//
//						FDGDEdge edge = this.edgeIterator.next();
//						PathwayProblem problem = new PathwayProblem(edge.entrance1(), edge.node1(), edge.entrance2(), edge.pathway().pieces(), this.fragments, this.params);
//						this.pathfindingSolver = new StepAStar<PathwayNode>(problem, this.params.heuristicWeight());
//						this.pathfindingSolver.step();
					}
					else
					{
						this.phase = Phase.REBUILD1;
					}
				}

				this.escapePhase = this.escapePhase || equilibriumState < this.params.toEscapeEquilibrium() || this.fdgdIterations > this.params.iterationsToEscape();
			}
			else
			{
				this.phase = Phase.REBUILD1;
			}
			return false;
		}

		if (this.phase == Phase.REBUILD1)
		{
			this.assignConnections();
			this.phase = Phase.REBUILD2;
			return false;
		}

		if (this.phase == Phase.REBUILD2)
		{
			this.doSpiderWeb();
			this.phase = Phase.REBUILD3;
			return false;
		}

		if (this.phase == Phase.REBUILD3)
		{
			//TODO: Make sure this second connection assignment is helpful at all
			this.assignConnections();
			this.phase = Phase.FDGD;
			this.fdgdIterations = 0;
			return false;
		}

		//Pathways phase

//		if (this.pathfindingSolver == null || this.pathfindingSolver.isTerminated())
//		{
//			if (this.edgeIterator.hasNext())
//			{
//				for (PathwayNode node : this.pathfindingSolver.currentState().fullPath())
//				{
//					if (node.getFragment() != null)
//					{
//						this.fragments.add(node.getFragment());
//					}
//				}
//				FDGDEdge edge = this.edgeIterator.next();
//				PathwayProblem problem = new PathwayProblem(edge.entrance1(), edge.node1(), edge.entrance2(), edge.pathway().pieces(), this.fragments, this.params);
//				this.pathfindingSolver = new StepAStar<PathwayNode>(problem, this.params.heuristicWeight());
//			}
//			else
//			{
//				this.finished = true;
//				return true;
//			}
//		}
//
//		this.pathfindingSolver.step();

		return false;
	}


	private void initialGraph() throws FailedToGenerateException
	{
		this.fdgdGraph = new Graph<>();
		final Map<FrameworkNode, FDGDNode> nodeLookup = new HashMap<>(this.framework.graph.vertexSet().size());

		//Create all nodes, which are now fixed size
		for (FrameworkNode node : this.framework.graph.vertexSet())
		{
				BlueprintData data = node.possibleValues(this.random).get(0);
				if (data != null)
				{
					final FDGDNode newNode = new FDGDNode(data, node.approxPosition(), this.world);
					this.fdgdGraph.addVertex(newNode);
					nodeLookup.put(node, newNode);
				}
		}

		//Create the edges between them, which now have a chosen PathwayData for them
		for (FrameworkEdge edge : this.framework.graph.edgeSet())
		{
			final PathwayData pathway = new PathwayData();  // TODO: Proper init
			//edge.node1().pathways().iterator().next();
			if (pathway != null)
			{
				final FDGDNode node1 = nodeLookup.get(edge.node1());
				final FDGDNode node2 = nodeLookup.get(edge.node2());
				final FDGDEdge newEdge = new FDGDEdge(node1, node2, pathway);
				this.fdgdGraph.addEdge(node1, node2, newEdge);
			}
		}
	}

	//	private void solveCSP() throws FailedToGenerateException
//	{
//		//Solve all the conditions in the framework
//		final FrameworkCSP csp = new FrameworkCSP(this.options.getRandom(), this.framework.conditions, this.framework.graph, this.framework.pathways());
//		final Map<Object, Object> assignments = CSPSolver.solve(csp);
//
//		if (assignments == null)
//		{
//			throw new FailedToGenerateException("Couldn't generate framework because it couldn't satisfy its conditions. This is likely an error in how you build your framework up!");
//		}
//
//		//From the solution of the conditions, generate a new graph with the chosen
//		//objects in place so we can start shaping it
//		this.fdgdGraph = new SimpleGraph<FDGDNode, FDGDEdge>(FDGDEdge.class);
//		final Map<FrameworkNode, FDGDNode> nodeLookup = new HashMap<FrameworkNode, FDGDNode>(this.framework.graph.vertexSet().size());
//
//		//Create all nodes, which are now fixed size
//		for (final Entry<Object, Object> entry : assignments.entrySet())
//		{
//			final Object key = entry.getKey();
//			if (key instanceof FrameworkNode)
//			{
//				final FrameworkNode itfd = (FrameworkNode) key;
//				final IConnectable result = (IConnectable) entry.getValue();
//				if (result != null)
//				{
//					final FDGDNode newNode = new FDGDNode(result, itfd.approxPosition());
//					this.fdgdGraph.addVertex(newNode);
//					nodeLookup.put(itfd, newNode);
//				}
//			}
//		}
//
//		//Create the edges between them, which now have a chosen PathwayData for them
//		for (final Entry<Object, Object> entry : assignments.entrySet())
//		{
//			final Object key = entry.getKey();
//			if (key instanceof FrameworkEdge)
//			{
//				final FrameworkEdge edge = (FrameworkEdge) key;
//				final PathwayData pathway = (PathwayData) entry.getValue();
//				if (pathway != null)
//				{
//					final FDGDNode node1 = nodeLookup.get(edge.node1());
//					final FDGDNode node2 = nodeLookup.get(edge.node2());
//					final FDGDEdge newEdge = new FDGDEdge(node1, node2, pathway);
//					this.fdgdGraph.addEdge(node1, node2, newEdge);
//				}
//			}
//		}
//	}

	private void iterationFDGD(Graph<FDGDNode, FDGDEdge> graph, FrameworkType type, boolean escapePhase)
	{
		final float repulsion = params.repulsion();
		final float stiffness = params.stiffness();
		final float naturalLength = params.naturalLength();
		final int nodeDistance = params.nodeDistance();
		final float collisionEsc = params.collisionEscape();
		final float maxForce = 1000;
		final float c = params.C();
		for (final FDGDNode v : graph.vertexSet())
		{
			float forceX = 0, forceY = 0, forceZ = 0;

			if (!escapePhase)
			{
				final Set<FDGDEdge> adjacentEdges = graph.edgesOf(v);
				for (final FDGDEdge edge : adjacentEdges)
				{
					final FDGDNode u = edge.getOpposite(v);
					final float dx = edge.xOf(u) - edge.xOf(v);
					final float dz = edge.zOf(u) - edge.zOf(v);
					float stiffModifier;

					if (type == FrameworkType.RECTANGLES)
					{
						float duv = Math.abs(dx) + Math.abs(dz);
						if (duv == 0)
						{
							duv = this.random.nextBoolean() ? 100 : -100;
						}
						stiffModifier = stiffness * (duv - naturalLength) / duv;
					}
					else
					{
						final float dy = edge.yOf(u) - edge.yOf(v);
						float duv = Math.abs(dx) + Math.abs(dy) + Math.abs(dz);
						if (duv == 0)
						{
							duv = this.random.nextBoolean() ? 100 : -100;
						}
						stiffModifier = stiffness * (duv - naturalLength) / duv;
						forceY += stiffModifier * dy;
					}
					forceX += stiffModifier * dx;
					forceZ += stiffModifier * dz;
				}
			}

			final IRegion rect1 = RegionHelp.expand(v, nodeDistance);

			for (final FDGDNode u : graph.vertexSet())
			{
				if (u.equals(v))
				{
					continue;
				}
				final float dx = u.getX() - v.getX();
				final float dz = u.getZ() - v.getZ();
				if (type == FrameworkType.RECTANGLES)
				{
					final float duv = Math.abs(dx) + Math.abs(dz);
					float trepulsion = repulsion / (float) Math.pow(duv, 3);
					if (escapePhase && RegionHelp.intersects2D(rect1, RegionHelp.expand(u, nodeDistance)))
					{
						continue;
					}
					else if (escapePhase)
					{
						trepulsion *= collisionEsc;
					}
					forceX -= MathHelper.clamp(trepulsion * dx, -maxForce, maxForce);
					forceZ -= MathHelper.clamp(trepulsion * dz, -maxForce, maxForce);
				}
				else
				{
					final float dy = u.getY() - v.getY();
					final float duv = Math.abs(dx) + Math.abs(dy) + Math.abs(dz);
					float trepulsion = repulsion / (float) Math.pow(duv, 3);
					if (escapePhase && RegionHelp.intersects(rect1, RegionHelp.expand(u, nodeDistance)))
					{
						continue;
					}
					else if (escapePhase)
					{
						trepulsion *= collisionEsc;
					}
					forceX -= MathHelper.clamp(trepulsion * dx, -maxForce, maxForce);
					forceY -= MathHelper.clamp(trepulsion * dy, -maxForce, maxForce);
					forceZ -= MathHelper.clamp(trepulsion * dz, -maxForce, maxForce);
				}
			}

			if (Float.isNaN(forceX) || Float.isNaN(forceY) || Float.isNaN(forceZ) || Float.isInfinite(forceX) || Float.isInfinite(forceY) || Float.isInfinite(forceZ))
			{
				forceX = 0;
				forceY = 0;
				forceZ = 0;
			}

			v.setForce(forceX * c, forceY * c, forceZ * c);
		}

		for (final FDGDNode v : graph.vertexSet())
		{
			v.applyForce();
		}

		for (final FDGDEdge e : graph.edgeSet())
		{
			e.applyForce();
		}
	}

	private void assignConnections()
	{
		for (final FDGDNode node : this.fdgdGraph.vertexSet())
		{
			node.assignConnections(this.fdgdGraph.edgesOf(node));
			//DEBUG
			for (FDGDEdge edge : this.fdgdGraph.edgesOf(node))
			{
				BlockPos min1 = node.getMin();
				BlockPos max1 = node.getMax();

				if (edge.xOf(node) != min1.getX() && edge.zOf(node) != min1.getZ() && edge.xOf(node) != max1.getX() && edge.zOf(node) != max1.getZ())
				{
//					OrbisCore.debugPrint("Entrance was not placed on an edge.");
				}
				if (edge.xOf(node) < min1.getX() || edge.zOf(node) < min1.getZ() || edge.xOf(node) > max1.getX() || edge.zOf(node) > max1.getZ())
				{
//					OrbisCore.debugPrint("Entrance was not placed on an edge.");
				}

			}
		}
	}

	private void doSpiderWeb()
	{
//		final boolean sFinished = false;
//		int i = 0;
//
//		final List<FDGDEdge> edges = new ArrayList<>(this.fdgdGraph.edgeSet());
//		final int maxAmount = (int) Math.pow(edges.size(), this.params.graphGrowth());
//
//		while (i < maxAmount)
//		{
//			i++;
//			for (int p = 0; p < edges.size(); p++)
//			{
//				for (int q = 0; q < edges.size(); q++)
//				{
//					final FDGDEdge edge1 = edges.get(p);
//					final FDGDEdge edge2 = edges.get(q);
//
//					final FDGDNode e1S = edge1.node1();
//					final FDGDNode e1T = edge1.node2();
//
//					final FDGDNode e2S = edge2.node1();
//					final FDGDNode e2T = edge2.node2();
//
//					if (e1T == e2T || e1T == e2S || e1S == e2T || e1S == e2S)
//					{
//						continue;
//					}
//
//					BlockPos connE1S = edge1.connectionOf1().getPos();
//					BlockPos connE1T = edge1.connectionOf2().getPos();
//
//					BlockPos connE2S = edge2.connectionOf1().getPos();
//					BlockPos connE2T = edge2.connectionOf2().getPos();
//
//					if (!FDGenUtil.isIntersecting(edge1, edge2))
//					{
//						continue;
//					}
//
//					//Find intersection point of the two lines
//					if (connE1S.getX() > connE1T.getX())
//					{
//						final BlockPos temp = connE1S;
//						connE1S = connE1T;
//						connE1T = temp;
//					}
//
//					if (connE2S.getX() > connE2T.getX())
//					{
//						final BlockPos temp = connE2S;
//						connE2S = connE2T;
//						connE2T = temp;
//					}
//					final long x1 = connE1S.getX();
//					final long z1 = connE1S.getZ();
//
//					final long x2 = connE1T.getX();
//					final long z2 = connE1T.getZ();
//
//					final long x3 = connE2S.getX();
//					final long z3 = connE2S.getZ();
//
//					final long x4 = connE2T.getX();
//					final long z4 = connE2T.getZ();
//
//					final long product1 = x1 * z2 - z1 * x2;
//					final long product2 = x3 * z4 - z3 * x4;
//
//					final float denominator = (x1 - x2) * (z3 - z4) - (z1 - z2) * (x3 - x4);
//
//					final long nominX = product1 * (x3 - x4) - (x1 - x2) * product2;
//					final long nominZ = product1 * (z3 - z4) - (z1 - z2) * product2;
//
//					final float x = nominX / denominator;
//					final float y = (connE1S.getY() + connE1T.getY() + connE2S.getY() + connE2T.getY()) / 4f;
//					final float z = nominZ / denominator;
//
//					// Find the intersection blueprint
//					final BlueprintData intersectionTFD = this.framework.getIntersection(edge1.pathway(), edge2.pathway());
//
//					//Remove old edges
//					edges.remove(edge1);
//					edges.remove(edge2);
//
//					this.fdgdGraph.removeEdge(edge1);
//					this.fdgdGraph.removeEdge(edge2);
//
//					//Add new node and edges, with the intersection blueprint as data.
//					final FDGDNode node = new FDGDNode(intersectionTFD, new BlockPos(x, y, z));
//					this.fdgdGraph.addVertex(node);
//
//					final FDGDEdge nEdge1 = new FDGDEdge(node, edge1.node1(), edge1.pathway());
//					this.fdgdGraph.addEdge(node, edge1.node1(), nEdge1);
//					final FDGDEdge nEdge2 = new FDGDEdge(node, edge1.node2(), edge1.pathway());
//					this.fdgdGraph.addEdge(node, edge1.node2(), nEdge2);
//
//					final FDGDEdge nEdge3 = new FDGDEdge(node, edge2.node1(), edge2.pathway());
//					this.fdgdGraph.addEdge(node, edge2.node1(), nEdge3);
//					final FDGDEdge nEdge4 = new FDGDEdge(node, edge2.node2(), edge2.pathway());
//					this.fdgdGraph.addEdge(node, edge2.node2(), nEdge4);
//
//					//TODO: Should I add the edges to the open edge list?
//
//					//Reasoning behind this: p will be the lowest of the two. The edge it was currently
//					//looking at with index p is now removed so on index p there's the next edge.
//					//We want to continue by looking from that edge.
//					p--;
//					break;
//				}
//			}
//		}
	}

	public Graph<FDGDNode, FDGDEdge> getFDGDDebug()
	{
		return this.fdgdGraph;
	}

	public int getIterations()
	{
		return this.fdgdIterations;
	}

//	public List<FrameworkFragment> getFragments()
//	{
//		return this.fragments;
//	}

//	public Iterable<PathwayNode> getPathfindingDebug()
//	{
//		return this.pathfindingSolver.currentState().fullPath();
//	}

	public Phase getPhase()
	{
		return this.phase;
	}

	public enum Phase
	{
		CSP, FDGD, PATHWAYS, REBUILD1, REBUILD2, REBUILD3
	}
}
