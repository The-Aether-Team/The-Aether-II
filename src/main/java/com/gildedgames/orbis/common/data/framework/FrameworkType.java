package com.gildedgames.orbis.common.data.framework;

/**
 * Determines the type of generation for the {@link FrameworkData FrameworkData} it's in.
 * The two types are {@link #CUBES cubes} and {@link #RECTANGLES rectangles}.
 * @author Emile
 *
 */
public enum FrameworkType
{

	/**
	 * <p> Rectangles is the 2D case of the algorithm. This means that the algorithm is done
	 * from a top down perspective, and two nodes cannot generate on top of each other.
	 * This type is used for cities and other types of generations like it.
	 * 
	 * <p> This one is a lot more computationally expensive. It has to deal with pathway 
	 * intersections and with the structure of the terrain it generates on. Furthermore,
	 * it has one less dimension, so it's more difficult to escape from its neighbors.
	 */
	RECTANGLES,
	/**
	 * <p> Cubes is the 3D case of the algorithm. Nodes can be generated above each other
	 * as well as next. This is used for generations such as dungeons or non-traditional
	 * cities. It is a lot cheaper to compute than {@link #RECTANGLES rectangles}, and
	 * as such recommended if possible. 
	 */
	CUBES
}
