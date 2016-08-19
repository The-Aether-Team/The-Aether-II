package com.gildedgames.aether.common.entities.ai.astar;

public interface Heuristic
{

	int getDistance(Node start, Node finish);

}
