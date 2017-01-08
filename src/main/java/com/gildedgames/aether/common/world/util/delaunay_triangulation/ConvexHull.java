package com.gildedgames.aether.common.world.util.delaunay_triangulation;

import java.util.*;

public class ConvexHull
{
	public List<int[]> fullInput; //used for finding the indexes of the objects

	public List<int[]> currentInput;

	public List<int[]> convexHull;

	public boolean[] marked;

	private double maxDistance;

	private int[] furthestVertex;

	private List<ConvexFace> convexFaces;

	private FaceList unprocessedFaces;

	private int[] currentVertex;

	private List<ConvexFace> affectedFaceBuffer;

	private Stack<ConvexFace> traverseStack;

	private HashSet<int[]> singularVertices;

	private List<DeferredFace> coneFaceBuffer;

	private ConvexFace[] updateBuffer;

	private int[] updateIndices;

	private ConvexObjectManager objectManager;

	private ConnectorList[] connectorTable;

	private VertexBuffer beyondBuffer;

	private VertexBuffer emptyBuffer;

	public TriangulationCell[] getDelaunayTriangulation(List<int[]> points)
	{
		List<ConvexFace> faces = this.getConvexHull(points);

		for (int i = faces.size() - 1; i >= 0; i--)
		{
			ConvexFace candidate = faces.get(i);
			if (candidate.normal[2] >= 0.0)
			{
				for (int fi = 0; fi < candidate.adjacentFaces.length; fi++)
				{
					ConvexFace f = candidate.adjacentFaces[fi];
					if (f != null)
					{
						for (int j = 0; j < f.adjacentFaces.length; j++)
						{
							if (f.adjacentFaces[j] == candidate)
							{
								f.adjacentFaces[j] = null;
							}
						}
					}
				}
				int li = faces.size() - 1;
				faces.set(i, faces.get(li));
				faces.remove(li);
			}
		}

		int cellCount = faces.size();
		TriangulationCell[] cells = new TriangulationCell[cellCount];

		for (int i = 0; i < cellCount; i++)
		{
			ConvexFace face = faces.get(i);
			List<int[]> vertices = new ArrayList<>();
			for (int j = 0; j <= 2; j++)
			{
				vertices.add(face.vertices.get(j));
			}
			cells[i] = new TriangulationCell(vertices, new TriangulationCell[3]);
			face.tag = i;
		}

		for (int i = 0; i < cellCount; i++)
		{
			ConvexFace face = faces.get(i);
			TriangulationCell cell = cells[i];
			for (int j = 0; j <= 2; j++)
			{
				if (face.adjacentFaces[j] == null)
				{
					continue;
				}
				cell.adjacency[j] = cells[face.adjacentFaces[j].tag];
			}
		}
		return cells;
	}

	public List<ConvexFace> getConvexHull(List<int[]> points)
	{
		this.fullInput = new ArrayList<>(points);
		this.currentInput = points;
		this.convexHull = new ArrayList<>();
		this.marked = new boolean[points.size()];

		this.convexFaces = new ArrayList<>();
		this.affectedFaceBuffer = new ArrayList<>();
		this.unprocessedFaces = new FaceList();
		this.traverseStack = new Stack<>();
		this.singularVertices = new HashSet<>();
		this.coneFaceBuffer = new ArrayList<>();
		this.updateBuffer = new ConvexFace[3];
		this.updateIndices = new int[3];
		this.objectManager = new ConvexObjectManager();
		this.beyondBuffer = new VertexBuffer();
		this.emptyBuffer = new VertexBuffer();

		this.connectorTable = new ConnectorList[2017];
		for (int i = 0; i < 2017; i++)
		{
			this.connectorTable[i] = new ConnectorList();
		}

		List<int[]> extremes = this.findExtremes();
		List<int[]> initialPoints = this.findInitialPoints(extremes);
		double[] center = new double[3];

		for (int[] initialPoint : initialPoints)
		{
			this.currentVertex = initialPoint;
			this.updateCenter(center);
			this.convexHull.add(this.currentVertex);
			this.currentInput.remove(this.currentVertex);
			extremes.remove(this.currentVertex);
		}

		ConvexFace[] faces = this.initiateFaceDatabase(center);

		for (ConvexFace face : faces)
		{
			this.findBeyondVertices(face);
			if (face.verticesBeyond.size() == 0)
			{
				this.convexFaces.add(face);
			}
			else
			{
				this.unprocessedFaces.add(face);
			}
		}

		while (this.unprocessedFaces.first != null)
		{
			ConvexFace currentFace = this.unprocessedFaces.first;
			this.currentVertex = currentFace.furthestVertex;

			this.updateCenter(center);

			this.tagAffectedFaces(currentFace);

			if (!this.singularVertices.contains(this.currentVertex) && this.createCone(center))
			{
				this.commitCone();
			}
			else
			{
				this.handleSingular(center);
			}
			int count = this.affectedFaceBuffer.size();
			for (ConvexFace anAffectedFaceBuffer : this.affectedFaceBuffer)
			{
				anAffectedFaceBuffer.tag = 0;
			}
		}

		return this.convexFaces;
	}

	protected List<int[]> findExtremes()
	{
		List<int[]> extremes = new ArrayList<>(6);//Get the extremes of the input data

		for (int i = 0; i < 3; i++)
		{
			int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
			int minInd = 0, maxInd = 0;
			for (int j = 0; j < this.currentInput.size(); j++)
			{
				int v = this.currentInput.get(j)[i];
				if (v < min)
				{
					min = v;
					minInd = j;
				}
				if (v > max)
				{
					max = v;
					maxInd = j;
				}
			}
			if (minInd != maxInd)
			{
				extremes.add(this.currentInput.get(minInd));
				extremes.add(this.currentInput.get(maxInd));
			}
			else
			{
				extremes.add(this.currentInput.get(minInd));
			}
		}
		return extremes;
	}

	protected List<int[]> findInitialPoints(List<int[]> extremes)
	{
		//Find 3 initial points
		List<int[]> initialPoints = new ArrayList<>();
		int[] first = null, second = null;//Find the two points furthest away from eachother
		int maxDist = 0;
		int[] temp = new int[3];

		for (int i = 0; i < extremes.size() - 1; i++)
		{
			int[] a = extremes.get(i);
			for (int j = i + 1; j < extremes.size(); j++)
			{
				int[] b = extremes.get(j);
				this.subtractFast(a, b, temp);
				int dist = 0;
				for (int k = 0; k < 3; k++)
				{
					dist += temp[k] * temp[k];
				}
				if (dist > maxDist)
				{
					first = a;
					second = b;
					maxDist = dist;
				}
			}
		}

		initialPoints.add(first);
		initialPoints.add(second);

		for (int i = 2; i <= 3; i++)
		{
			double maximum = 0.000001;
			int[] maxPoint = null;
			for (int[] extreme : extremes)
			{
				if (initialPoints.contains(extreme))
				{
					continue;
				}

				int val = 0;
				for (int[] initPt : initialPoints)
				{
					for (int l = 0; l < 3; l++)
					{
						int t = initPt[l] - extreme[l];//Check nullpointer crash :(
						val += t * t;//squared distance sum of initPt and extreme
					}
				}

				if (val > maximum)
				{
					maximum = val;
					maxPoint = extreme;
				}
			}
			if (maxPoint != null)
			{
				initialPoints.add(maxPoint);
			}
			else
			{
				for (int[] point : this.currentInput)
				{
					if (initialPoints.contains(point))
					{
						continue;
					}
					int val = 0;
					for (int[] initPt : initialPoints)
					{
						for (int l = 0; l < 3; l++)
						{
							int t = initPt[l] - point[l];
							val += t * t;//squared distance sum of initPt and extreme
						}
					}

					if (val > maximum)
					{
						maximum = val;
						maxPoint = point;
					}

				}

				if (maxPoint != null)
				{
					initialPoints.add(maxPoint);
				}
				//else TODO: this is when the generation is on one line.
			}
		}

		return initialPoints;
	}

	protected void updateCenter(double[] center)
	{
		int count = this.convexHull.size() + 1;
		for (int i = 0; i < 3; i++)
		{
			center[i] *= (count - 1);
		}
		double f = 1.0 / count;
		for (int i = 0; i < 3; i++)
		{
			center[i] = (f * (center[i] + this.currentVertex[i]));
		}
	}

	protected ConvexFace[] initiateFaceDatabase(double[] center)
	{
		ConvexFace[] faces = new ConvexFace[4];

		for (int i = 0; i < 4; i++)
		{
			List<int[]> vertices = new ArrayList<>();
			for (int j = 0; j < this.convexHull.size(); j++)
			{
				if (i != j)
				{
					vertices.add(this.convexHull.get(j));
				}
			}

			ConvexFace newFace = new ConvexFace(new VertexBuffer());
			newFace.vertices = vertices;
			Collections.sort(vertices, new VertexComparer(this.fullInput));
			this.calculateFacePlane(newFace, center);
			faces[i] = newFace;
		}

		for (int i = 0; i < 3; i++)
		{
			for (int j = i + 1; j < 4; j++)
			{
				this.updateAdjacency(faces[i], faces[j]);
			}
		}

		return faces;
	}

	protected boolean calculateFacePlane(ConvexFace face, double[] center)
	{
		List<int[]> vertices = face.vertices;
		double[] normal = face.normal;
		this.findNormalVector(vertices, normal);

		if (Double.isNaN(normal[0]))
		{
			return false;
		}

		double offset = 0.0;
		double centerDistance = 0.0;
		int[] fi = vertices.get(0);

		for (int i = 0; i < 3; i++)
		{
			double n = normal[i];
			offset += n * fi[i];
			centerDistance += n * center[i];
		}

		face.offset = -offset;
		centerDistance -= offset;

		if (centerDistance > 0)
		{
			for (int i = 0; i < 3; i++)
			{
				normal[i] = -normal[i];
			}
			face.offset = offset;
			face.isNormalFlipped = true;
		}
		else
		{
			face.isNormalFlipped = false;
		}
		return true;
	}

	public void findNormalVector(List<int[]> vertices, double[] normal)
	{
		int[] x = new int[3], y = new int[3];
		this.subtractFast(vertices.get(1), vertices.get(0), x);
		this.subtractFast(vertices.get(2), vertices.get(1), y);

		double nx = x[1] * y[2] - x[2] * y[1];
		double ny = x[2] * y[0] - x[0] * y[2];
		double nz = x[0] * y[1] - x[1] * y[0];

		double norm = Math.sqrt(nx * nx + ny * ny + nz * nz);
		double f = 1.0 / norm;
		normal[0] = f * nx;
		normal[1] = f * ny;
		normal[2] = f * nz;
	}

	public void subtractFast(int[] x, int[] y, int[] target)
	{
		for (int i = 0; i < 3; i++)
		{
			target[i] = x[i] - y[i];
		}
	}

	protected void updateAdjacency(ConvexFace l, ConvexFace r)
	{
		List<int[]> lv = l.vertices;
		List<int[]> rv = r.vertices;
		int i;

		for (i = 0; i < 3; i++)
		{
			this.setMarked(lv.get(i), false);
		}

		for (i = 0; i < 3; i++)
		{
			this.setMarked(rv.get(i), true);
		}

		//Find the first false index
		for (i = 0; i < 3; i++)
		{
			if (!this.getMarked(lv.get(i)))
			{
				break;
			}
		}

		if (i == 3)
		{
			return;
		}

		for (int j = i + 1; j < 3; j++)
		{
			if (!this.getMarked(lv.get(j)))
			{
				return;
			}
		}

		//If this somehow is used, it means that two faces share an edge
		l.adjacentFaces[i] = r;
		for (i = 0; i < 3; i++)
		{
			this.setMarked(lv.get(i), false);
		}
		for (i = 0; i < 3; i++)
		{
			if (this.getMarked(rv.get(i)))
			{
				break;
			}
		}
		r.adjacentFaces[i] = l;
	}

	protected boolean getMarked(int[] vertex)
	{
		return this.marked[this.fullInput.indexOf(vertex)];
	}

	protected void setMarked(int[] vertex, boolean markud)
	{
		this.marked[this.fullInput.indexOf(vertex)] = markud;
	}

	public int getIndex(int[] vertex)
	{
		return this.fullInput.indexOf(vertex);
	}

	protected void findBeyondVertices(ConvexFace face)
	{
		VertexBuffer beyondVertices = face.verticesBeyond;

		this.maxDistance = Double.NEGATIVE_INFINITY;
		this.furthestVertex = null;

		for (int[] aCurrentInput : this.currentInput)
		{
			this.isBeyond(face, beyondVertices, aCurrentInput);
		}
		face.furthestVertex = this.furthestVertex;
	}

	protected void isBeyond(ConvexFace face, VertexBuffer beyondVertices, int[] v)
	{
		double distance = this.getVertexDistance(v, face);
		if (distance >= 0.0000001)
		{
			if (distance > this.maxDistance)
			{
				this.maxDistance = distance;
				this.furthestVertex = v;
			}
			beyondVertices.add(v);
		}
	}

	protected double getVertexDistance(int[] v, ConvexFace f)
	{
		double[] normal = f.normal;
		double distance = f.offset;
		for (int i = 0; i < 3; i++)
		{
			distance += normal[i] * v[i];
		}
		return distance;
	}

	protected void tagAffectedFaces(ConvexFace currentFace)
	{
		this.affectedFaceBuffer.clear();
		this.affectedFaceBuffer.add(currentFace);
		this.traverseAffectedFaces(currentFace);
	}

	protected void traverseAffectedFaces(ConvexFace currentFace)
	{
		this.traverseStack.clear();
		this.traverseStack.push(currentFace);
		currentFace.tag = 1;

		while (this.traverseStack.size() > 0)
		{
			ConvexFace top = this.traverseStack.pop();
			for (int i = 0; i < 3; i++)
			{
				ConvexFace adjFace = top.adjacentFaces[i];

				if (adjFace.tag == 0 && this.getVertexDistance(this.currentVertex, adjFace) >= 0.0000001)
				{
					this.affectedFaceBuffer.add(adjFace);
					adjFace.tag = 1;
					this.traverseStack.push(adjFace);
				}
			}
		}
	}

	protected boolean createCone(double[] center)
	{
		int currentVertexIndex = this.getIndex(this.currentVertex);
		this.coneFaceBuffer.clear();

		for (ConvexFace oldFace : this.affectedFaceBuffer)
		{
			int updateCount = 0;
			for (int i = 0; i < 3; i++)
			{
				ConvexFace af = oldFace.adjacentFaces[i];
				if (af.tag == 0)
				{
					this.updateBuffer[updateCount] = af;
					this.updateIndices[updateCount] = i;
					++updateCount;
				}
			}

			for (int i = 0; i < updateCount; i++)
			{
				ConvexFace adjacentFace = this.updateBuffer[i];

				int oldFaceAdjacentIndex = 0;
				ConvexFace[] adjFaceAdjacency = adjacentFace.adjacentFaces;
				for (int j = 0; j < 3; j++)
				{
					if (oldFace == adjFaceAdjacency[j])
					{
						oldFaceAdjacentIndex = j;
						break;
					}
				}
				int forbidden = this.updateIndices[i];

				ConvexFace newFace;

				int oldVertexIndex;
				List<int[]> vertices;

				newFace = this.objectManager.getFace();
				vertices = newFace.vertices;
				if (vertices.size() < 3)
				{
					for (int j = 0; j < 3; j++)
					{
						vertices.add(oldFace.vertices.get(j));
					}
				}
				else
				{
					for (int j = 0; j < 3; j++)
					{
						vertices.set(j, oldFace.vertices.get(j));
					}
				}
				oldVertexIndex = this.getIndex(vertices.get(forbidden));

				int orderedPivotIndex;

				if (currentVertexIndex < oldVertexIndex)
				{
					orderedPivotIndex = 0;
					for (int j = forbidden - 1; j >= 0; j--)
					{
						if (this.getIndex(vertices.get(j)) > currentVertexIndex)
						{
							vertices.set(j + 1, vertices.get(j));
						}
						else
						{
							orderedPivotIndex = j + 1;
							break;
						}
					}
				}
				else
				{
					orderedPivotIndex = 2;
					for (int j = forbidden + 1; j < 3; j++)
					{
						if (this.getIndex(vertices.get(j)) < currentVertexIndex)
						{
							vertices.set(j - 1, vertices.get(j));
						}
						else
						{
							orderedPivotIndex = j - 1;
							break;
						}
					}
				}

				vertices.set(orderedPivotIndex, this.currentVertex);

				if (!this.calculateFacePlane(newFace, center))
				{
					return false;
				}
				this.coneFaceBuffer.add(this.makeDeferredFace(newFace, orderedPivotIndex, adjacentFace, oldFaceAdjacentIndex, oldFace));
			}
		}
		return true;
	}

	protected DeferredFace makeDeferredFace(ConvexFace face, int faceIndex, ConvexFace pivot, int pivotIndex, ConvexFace oldFace)
	{
		DeferredFace ret = this.objectManager.getDeferredFace();

		ret.face = face;
		ret.faceIndex = faceIndex;
		ret.pivot = pivot;
		ret.pivotIndex = pivotIndex;
		ret.oldFace = oldFace;

		return ret;
	}

	protected void commitCone()
	{
		this.convexHull.add(this.currentVertex);

		for (DeferredFace face : this.coneFaceBuffer)
		{
			ConvexFace newFace = face.face;
			ConvexFace adjacentFace = face.pivot;
			ConvexFace oldFace = face.oldFace;
			int orderedPivotIndex = face.faceIndex;

			newFace.adjacentFaces[orderedPivotIndex] = adjacentFace;
			adjacentFace.adjacentFaces[face.pivotIndex] = newFace;

			for (int j = 0; j < 3; j++)
			{
				if (j == orderedPivotIndex)
				{
					continue;
				}
				FaceConnector connector = this.objectManager.getConnector();
				connector.update(newFace, j, this.fullInput);
				this.connectFace(connector);
			}

			if (adjacentFace.verticesBeyond.size() < oldFace.verticesBeyond.size())
			{
				this.findBeyondVertices(newFace, adjacentFace.verticesBeyond, oldFace.verticesBeyond);
			}
			else
			{
				this.findBeyondVertices(newFace, oldFace.verticesBeyond, adjacentFace.verticesBeyond);
			}

			if (newFace.verticesBeyond.size() == 0)
			{
				this.convexFaces.add(newFace);
				this.unprocessedFaces.remove(newFace);
				this.objectManager.depositVertexBuffer(newFace.verticesBeyond);
				newFace.verticesBeyond = this.emptyBuffer;
			}
			else
			{
				this.unprocessedFaces.add(newFace);
			}

			this.objectManager.depositDeferredFace(face);
		}

		for (ConvexFace face : this.affectedFaceBuffer)
		{
			this.unprocessedFaces.remove(face);
			this.objectManager.depositFace(face);
		}
	}

	protected void connectFace(FaceConnector connector)
	{
		int index = connector.hashCode % 2017;
		ConnectorList list = this.connectorTable[index];

		for (FaceConnector current = list.first; current != null; current = current.next)
		{
			if (FaceConnector.AreConnectable(connector, current))
			{
				list.remove(current);
				FaceConnector.Connect(current, connector);
				current.face = null;
				connector.face = null;
				this.objectManager.depositConnector(current);
				this.objectManager.depositConnector(connector);
				return;
			}
		}

		list.add(connector);
	}

	protected void findBeyondVertices(ConvexFace face, VertexBuffer beyond, VertexBuffer beyond1)
	{
		VertexBuffer beyondVertices = this.beyondBuffer;

		this.maxDistance = Double.NEGATIVE_INFINITY;
		this.furthestVertex = null;
		int[] v;

		int count = beyond1.size();
		for (int i = 0; i < count; i++)
		{
			this.setMarked(beyond1.get(i), true);
		}
		this.setMarked(this.currentVertex, false);
		count = beyond.size();
		for (int i = 0; i < count; i++)
		{
			v = beyond.get(i);
			if (v == this.currentVertex)
			{
				continue;
			}
			this.setMarked(v, false);
			this.isBeyond(face, beyondVertices, v);
		}

		count = beyond1.size();
		for (int i = 0; i < count; i++)
		{
			v = beyond1.get(i);
			if (this.getMarked(v))
			{
				this.isBeyond(face, beyondVertices, v);
			}
		}

		face.furthestVertex = this.furthestVertex;
		VertexBuffer temp = face.verticesBeyond;
		face.verticesBeyond = beyondVertices;
		if (temp.size() > 0)
		{
			temp.clear();
		}
		this.beyondBuffer = temp;
	}

	protected void handleSingular(double[] center)
	{
		this.rollbackCenter(center);
		this.singularVertices.add(this.currentVertex);

		for (ConvexFace face : this.affectedFaceBuffer)
		{
			VertexBuffer vb = face.verticesBeyond;
			for (int i = 0; i < vb.size(); i++)
			{
				this.singularVertices.add(vb.get(i));
			}

			this.convexFaces.add(face);
			this.unprocessedFaces.remove(face);
			this.objectManager.depositVertexBuffer(face.verticesBeyond);
			face.verticesBeyond = this.emptyBuffer;
		}

	}

	protected void rollbackCenter(double[] center)
	{
		int count = this.convexHull.size() + 1;
		for (int i = 0; i < 3; i++)
		{
			center[i] *= count;
		}
		double f = 1.0 / (count - 1);
		for (int i = 0; i < 3; i++)
		{
			center[i] = (f * (center[i] - this.currentVertex[i]));
		}
	}

}
