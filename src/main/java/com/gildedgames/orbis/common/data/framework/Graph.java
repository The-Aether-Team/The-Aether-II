package com.gildedgames.orbis.common.data.framework;

import net.minecraft.util.Tuple;

import java.util.*;

public class Graph<NODE,EDGE>
{
	Set<NODE> vertices = new HashSet<>();

	Set<EDGE> edges = new HashSet<>();

	Map<NODE, Set<EDGE>> connections = new HashMap<>();

	Map<EDGE, Tuple<NODE, NODE>> edge_conn = new HashMap<>();

	public Set<NODE> vertexSet()
	{
		return vertices;
	}

	public Set<EDGE> edgesOf(NODE n)
	{
		return this.connections.get(n);
	}

	public Set<EDGE> edgeSet()
	{
		return edges;
	}

	public void removeEdge(EDGE edge1)
	{
		edges.remove(edge1);
		Tuple<NODE, NODE> nodes = this.edge_conn.get(edge1);
		this.connections.get(nodes.getFirst()).remove(edge1);
		this.connections.get(nodes.getSecond()).remove(edge1);
		this.edge_conn.remove(edge1);
	}

	public void addVertex(NODE n)
	{
		vertices.add(n);
		this.connections.put(n, new HashSet<>());
	}

	public void addEdge(NODE node, NODE fdgdNode, EDGE nEdge1)
	{
		this.addVertex(node);
		this.connections.get(node).add(nEdge1);
		this.addVertex(fdgdNode);
		this.connections.get(fdgdNode).add(nEdge1);
		this.edges.add(nEdge1);
		this.edge_conn.put(nEdge1, new Tuple<>(node, fdgdNode));

	}

	public boolean containsVertex(NODE node1)
	{
		return vertices.contains(node1);
	}

	public EDGE getEdge(NODE node1, NODE node2)
	{
		for(EDGE e1 : this.connections.get(node1))
		{
			for(EDGE e2 : this.connections.get(node2))
			{
				if(e1 == e2)
				{
					return e1;
				}
			}
		}
		return null;
	}
}
