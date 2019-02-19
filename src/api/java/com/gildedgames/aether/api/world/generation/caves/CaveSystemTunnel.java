package com.gildedgames.aether.api.world.generation.caves;

public class CaveSystemTunnel
{
	public final double posX, posY, posZ;

	public final boolean isRoom;

	public final double nodeWidthRadius, nodeHeightRadius;

	public final double nodeSizeMultiplier;

	public final int nodesLeft;

	public CaveSystemTunnel(double posX, double posY, double posZ, boolean isRoom, double nodeWidthRadius, double nodeHeightRadius, double nodeSizeMultiplier,
			int noOfNodes, int nodeIndex)
	{
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.isRoom = isRoom;
		this.nodeWidthRadius = nodeWidthRadius;
		this.nodeHeightRadius = nodeHeightRadius;
		this.nodeSizeMultiplier = nodeSizeMultiplier;
		this.nodesLeft = noOfNodes - nodeIndex;
	}
}
