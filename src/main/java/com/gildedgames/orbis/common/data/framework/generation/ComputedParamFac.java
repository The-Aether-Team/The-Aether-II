package com.gildedgames.orbis.common.data.framework.generation;

import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.orbis.common.data.framework.FrameworkType;
import com.gildedgames.orbis.common.data.framework.Graph;
import com.gildedgames.orbis.common.data.pathway.PathwayData;

import java.util.ArrayList;
import java.util.List;

/**
 * Tonnes of parameters to tweak! 
 * @author Emile
 *
 */
public class ComputedParamFac
{
	public float minRepulsion = .7f, maxRepulsion = .8f;

	public float minRepulsionMagnitude = 5.8f, maxRepulsionMagnitude = 7.3f;

	public float minStiffness = .06f, maxStiffness = .82f;

	public float minEscape = 1.3f, maxEscape = 4f;

	public float minNaturalLength = 5, maxNaturalLength = 450;

	public float minC = .08f, maxC = .12f;

	//-------------------------------------------------------------------//

	public static final int minVertexSize = 4, maxVertexSize = 50;

	public static final float maxEdgeDensity = 2.8f;

	public static final int minPathwayLength = 3, maxPathwayLength = 340;

	//-------------------------------------------------------------------//

	public static float verticesRepulsion = 0.2f, edgesRepulsion = 0.2f, areaRepulsion = 0.6f;

	public static float verticesStiff = 0, edgesStiff = 0.667f, areaStiff = 0.333f;

	public static float verticesEscape = 0, edgesEscape = 0, areaEscape = 1;

	public static float verticesNL = 0, edgesNL = 0, areaNL = 1;

	public static float verticesC = 0.4f, edgesC = 0.4f, areaC = 0.2f;

	//-------------------------------------------------------------------//

	public static float relNodeDistance = 1.5f;

	public static int fdgdMaxIterations = 2500;

	public static int escapeIterations = 1500;

	public static float toEscapeEquilibrium = 0.45f;

	public static float acceptEquilibrium = 0.7f;

	public static float acceptEquilibriumEsc = 0.8f;

	public static float graphGrowth = 0.5f;

	public static float heuristicWeight = 3;

	public static int pathwaysBoundingBox = 8;

	public FrameworkParams createParams(Graph<FDGDNode, FDGDEdge> graph, FrameworkType type)
	{
		final float normalizedVertices = this.normalizedVertices(graph);
		final float normalizedEdges = this.normalizedEdges(graph);
		final float averageLength = this.computeAverageLength(graph, type);

		final FrameworkParams params = new FrameworkParams();
		params.repulsion = this.repulsion(normalizedVertices, normalizedEdges, averageLength);
		params.stiffness = this.stiffness(normalizedVertices, normalizedEdges, averageLength);
		params.collisionEscape = this.escape(normalizedVertices, normalizedEdges, averageLength);
		params.naturalLength = this.naturalLength(normalizedVertices, normalizedEdges, averageLength);
		params.c = this.c(normalizedVertices, normalizedEdges, averageLength);
		params.nodeDistance = (int) this.nodeDistance(graph, type);
		params.fdgdMaxIterations = fdgdMaxIterations;
		params.iterationsToEscape = escapeIterations;
		params.toEscapeEquilibrium = toEscapeEquilibrium;
		params.acceptEquilibrium = acceptEquilibrium;
		params.acceptEquilibriumEsc = acceptEquilibriumEsc;
		params.graphGrowth = graphGrowth;
		params.heuristcWeight = heuristicWeight;
		params.pathwaysBoundingBox = pathwaysBoundingBox;

		return params;
	}

	private float normalizedVertices(Graph<FDGDNode, FDGDEdge> graph)
	{
		return this.normalize(minVertexSize, maxVertexSize, graph.vertexSet().size());
	}

	private float normalizedEdges(Graph<FDGDNode, FDGDEdge> graph)
	{
		final int vertexCount = graph.vertexSet().size();
		final float edgesAmount = (float) graph.edgeSet().size() / (float) vertexCount;

		//(vertexCount - 1f) is the minimum amount of edges necessary
		//in a fully connected graph
		return this.normalize((vertexCount - 1f) / vertexCount, maxEdgeDensity, edgesAmount);
	}

	private float computeAverageLength(Graph<FDGDNode, FDGDEdge> graph, FrameworkType type)
	{
		int total = 0;
		for (final FDGDNode node : graph.vertexSet())
		{
			if (type == FrameworkType.CUBES)
			{
				total += node.getWidth() * node.getHeight() * node.getLength();
			}
			else
			{
				total += node.getWidth() * node.getLength();
			}
		}
		final float average = (float) total / graph.vertexSet().size();
		final float averageEdgeLength = (float) (type == FrameworkType.CUBES ? Math.pow(average, 1 / 3) : Math.sqrt(average));
		return this.normalize(minPathwayLength, maxPathwayLength, averageEdgeLength);
	}

	private float repulsion(float vertices, float edges, float area)
	{
		final float normed = vertices * verticesRepulsion + edges * edgesRepulsion + area * areaRepulsion;
		return this.valuelize(this.minRepulsion, this.maxRepulsion, normed) * (float) Math.pow(10, this.valuelize(this.minRepulsionMagnitude, this.maxRepulsionMagnitude, normed));
	}

	private float stiffness(float vertices, float edges, float area)
	{
		return this.inverseValuelize(this.minStiffness, this.maxStiffness, vertices * verticesStiff + edges * edgesStiff + area * areaStiff);
	}

	private float naturalLength(float vertices, float edges, float area)
	{
		return this.valuelize(this.minNaturalLength, this.maxNaturalLength, vertices * verticesNL + edges * edgesNL + area * areaNL);
	}

	private float escape(float vertices, float edges, float area)
	{
		return this.valuelize(this.minEscape, this.maxEscape, vertices * verticesNL + edges * edgesEscape + area * areaEscape);
	}

	private float c(float vertices, float edges, float area)
	{
		return this.inverseValuelize(this.minC, this.maxC, vertices * verticesC + edges * edgesC + area * areaC);
	}

	private float nodeDistance(Graph<FDGDNode, FDGDEdge> graph, FrameworkType type)
	{
		float max = 0;
		final List<PathwayData> checkedPathways = new ArrayList<PathwayData>();
		for (final FDGDEdge edge : graph.edgeSet())
		{
			final PathwayData data = edge.pathway();
			if (!checkedPathways.contains(data))
			{
				checkedPathways.add(data);
				for (final BlueprintData blueprint : data.pieces())
				{
					max = Math.max(max, blueprint.getWidth());
					max = Math.max(max, blueprint.getLength());
					if (type == FrameworkType.CUBES)
					{
						max = Math.max(max, blueprint.getHeight());
					}
				}
			}
		}
		return relNodeDistance * max;
	}

	private float normalize(float minimum, float maximum, float value)
	{
		if (value < minimum)
		{
			return 0;
		}
		if (value > maximum)
		{
			return 1;
		}
		return (value - minimum) / (maximum - minimum);
	}

	float valuelize(float minimum, float maximum, float normal)
	{
		if (normal < 0)
		{
			return minimum;
		}
		if (normal > 1)
		{
			return maximum;
		}
		return minimum + normal * (maximum - minimum);
	}

	float inverseValuelize(float minimum, float maximum, float normal)
	{
		if (normal < 0)
		{
			return maximum;
		}
		if (normal > 1)
		{
			return minimum;
		}
		return maximum - normal * (maximum - minimum);
	}

}
