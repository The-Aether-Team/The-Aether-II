package com.gildedgames.aether.api.util;

import net.minecraft.world.World;

import java.util.function.Function;

public interface IClassSerializer
{

	<T extends NBT> void register(int id, Class<T> clazz, Function<World, T> objectCreation);

	int serialize(Class<?> obj);

	int serialize(Object obj);

	<T extends NBT> T deserialize(World world, int id);

	<T extends NBT> T deserialize(int id);

}
