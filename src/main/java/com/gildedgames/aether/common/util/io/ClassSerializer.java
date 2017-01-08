package com.gildedgames.aether.common.util.io;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.nbt.NBTTagCompound;

import java.util.concurrent.Callable;

@Deprecated
// Will be removed/changed.
public class ClassSerializer
{

	private BiMap<Integer, RegisteredEntry> registeredSerializations = HashBiMap.create();

	private static BiMap<String, ClassSerializer> locators = HashBiMap.create();

	private interface RegisteredEntry
	{

		Callable<?> serializer();

		Class<?> serializedClass();

	}

	/**
	 * @param uniqueLocationKey MUST BE UNIQUE or else it will break serialization process.
	 */
	public ClassSerializer(final String uniqueLocationKey)
	{
		ClassSerializer.locators.put(uniqueLocationKey, this);
	}

	/**
	 * @param serialNumber MUST BE UNIQUE for each ClassSerializer instances. You should never change this number once registered
	 * in a previous version.
	 * @param serializer
	 */
	public void registerSerialization(final int serialNumber, final Class<?> clazz, final Callable<?> serializer)
	{
		this.registeredSerializations.put(serialNumber, new RegisteredEntry()
		{

			@Override
			public Callable<?> serializer()
			{
				return serializer;
			}

			@Override
			public Class<?> serializedClass()
			{
				return clazz;
			}

		});
	}

	public <T> T instantiate(int serialNumber)
	{
		try
		{
			return (T) this.registeredSerializations.get(serialNumber).serializer().call();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public boolean hasSerialNumber(Class<?> clazz)
	{
		for (RegisteredEntry entry : this.registeredSerializations.values())
		{
			if (entry.serializedClass() == clazz)
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * @param clazz
	 * @return serialNumber
	 */
	public int getSerialNumber(Class<?> clazz)
	{
		if (clazz == null)
		{
			throw new NullPointerException();
		}

		for (RegisteredEntry entry : this.registeredSerializations.values())
		{
			if (entry.serializedClass() == clazz)
			{
				return this.registeredSerializations.inverse().get(entry);
			}
		}

		throw new IllegalArgumentException("Object's class isn't registered as a serialization! Class: " + clazz.getCanonicalName()
				+ ". Register with ClassSerializer.registerSerialization()");
	}

	public int getSerialNumber(Object obj)
	{
		if (obj == null)
		{
			throw new NullPointerException();
		}

		return this.getSerialNumber(obj.getClass());
	}

	public static void writeSerialNumber(String key, Object obj, NBTTagCompound nbt)
	{
		if (obj == null)
		{
			return;
		}

		ClassSerializer.writeSerialNumber(key, obj.getClass(), nbt);
	}

	public static void writeSerialNumber(String key, Class<?> clazz, NBTTagCompound nbt)
	{
		ClassSerializer srl = ClassSerializer.locateSerializer(clazz);

		if (srl != null)
		{
			nbt.setString(key + "_srl", ClassSerializer.findSerializerLocationKey(srl));
			nbt.setInteger(key + "_srlNumber", srl.getSerialNumber(clazz));
		}
	}

	public static Object instantiate(String key, NBTTagCompound nbt)
	{
		ClassSerializer srl = ClassSerializer.locateSerializer(nbt.getString(key + "_srl"));

		return srl.instantiate(nbt.getInteger(key + "_srlNumber"));
	}

	public static String findSerializerLocationKey(Object obj)
	{
		return ClassSerializer.locators.inverse().get(obj);
	}

	public static ClassSerializer locateSerializer(String locationKey)
	{
		return ClassSerializer.locators.get(locationKey);
	}

	public static ClassSerializer locateSerializer(Object obj)
	{
		if (obj == null)
		{
			return null;
		}

		return ClassSerializer.locateSerializer(obj.getClass());
	}

	/**
	 * @param clazz A serialized entry registered in one of the serializers.
	 * @return The located serializer, or null if an associated serialize was not found.
	 */
	public static ClassSerializer locateSerializer(Class<?> clazz)
	{
		if (clazz == null)
		{
			return null;
		}

		for (Object obj : ClassSerializer.locators.values())
		{
			if (obj instanceof ClassSerializer)
			{
				ClassSerializer srl = (ClassSerializer) obj;

				if (srl.hasSerialNumber(clazz))
				{
					return srl;
				}
			}
		}

		return null;
	}

}
