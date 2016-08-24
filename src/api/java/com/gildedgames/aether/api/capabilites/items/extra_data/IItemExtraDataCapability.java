package com.gildedgames.aether.api.capabilites.items.extra_data;

import com.gildedgames.util.io_manager.io.NBT;

import java.util.Map;

public interface IItemExtraDataCapability
{

	Map<String, NBT> data();

	boolean has(String key);

	<T extends NBT> T get(String key);

	<T extends NBT> void set(String key, T obj);

}
