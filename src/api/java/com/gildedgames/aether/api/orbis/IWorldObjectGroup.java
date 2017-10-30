package com.gildedgames.aether.api.orbis;

import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.api.util.NBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;

public interface IWorldObjectGroup extends NBT
{

	void setWorld(World world);

	<T extends IWorldObject> int getID(T object);

	<T extends IWorldObject> T getObject(int id);

	/**
	 * Sets an object to the group with a chosen id.
	 * SHOULD ONLY BE USED IF YOU UNDERSTAND THE INTERNAL
	 * FUNCTION OF THIS IMPLEMENTATION.
	 * @param object
	 * @param <T>
	 */
	<T extends IWorldObject> void setObject(int id, T object);

	/**
	 * Adds an object to the group with an auto-assigned
	 * id. Use with caution, only adding when the object is new,
	 * not when it is re-added from a loaded state.
	 * @param object
	 * @param <T>
	 */
	<T extends IWorldObject> int addObject(T object);

	<T extends IWorldObject> boolean removeObject(T object);

	boolean removeObject(int id);

	Collection<IWorldObject> getObjects();

	void addObserver(IWorldObjectGroupObserver observer);

	boolean removeObserver(IWorldObjectGroupObserver observer);

	boolean containsObserver(IWorldObjectGroupObserver observer);

	IShape getIntersectingShape(BlockPos pos);

	<T extends IShape> T getIntersectingShape(Class<T> shapeType, BlockPos pos);

	IShape getIntersectingShape(IShape shape);

	<T extends IShape> T getIntersectingShape(Class<T> shapeType, IShape shape);

	IShape getContainedShape(IShape shape);

	IShape getContainedShape(Class<? extends IShape> shapeType, IShape shape);

	boolean isIntersectingShapes(IShape shape);

	boolean isContainedInShape(IShape shape);

}
