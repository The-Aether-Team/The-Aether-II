package com.gildedgames.aether.common.capabilities.item.extra_data;

import com.gildedgames.aether.api.capabilites.items.extra_data.IItemExtraDataCapability;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.util.core.nbt.NBTHelper;
import com.gildedgames.util.io_manager.io.NBT;
import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import java.util.Map;

public class ItemExtraDataImpl implements IItemExtraDataCapability
{

	private Map<String, NBT> dataMap = Maps.newHashMap();

	public ItemExtraDataImpl()
	{

	}

	@Override
	public Map<String, NBT> data()
	{
		return this.dataMap;
	}

	@Override
	public boolean has(String key)
	{
		return this.dataMap.containsKey(key);
	}

	@Override
	public <T extends NBT> T get(String key)
	{
		return (T) this.dataMap.get(key);
	}

	@Override
	public <T extends NBT> void set(String key, T obj)
	{
		this.dataMap.put(key, obj);
	}

	public static class Storage implements Capability.IStorage<IItemExtraDataCapability>
	{
		@Override
		public NBTBase writeNBT(Capability<IItemExtraDataCapability> capability, IItemExtraDataCapability instance, EnumFacing side)
		{
			NBTTagCompound tag = new NBTTagCompound();

			tag.setBoolean("hasWritten", instance.data().size() > 0);

			if (instance.data().size() <= 0)
			{
				return tag;
			}

			NBTTagList data = new NBTTagList();

			for (Map.Entry<String, NBT> entry : instance.data().entrySet())
			{
				NBT nbt = entry.getValue();
				NBTTagCompound newTag = new NBTTagCompound();
				newTag.setString("key", entry.getKey());
				newTag.setInteger("srlNumber", AetherCore.srl().getSerialNumber(nbt));

				nbt.write(newTag);
				data.appendTag(newTag);
			}

			tag.setTag("data", data);

			return tag;
		}

		@Override
		public void readNBT(Capability<IItemExtraDataCapability> capability, IItemExtraDataCapability instance, EnumFacing side, NBTBase nbt)
		{
			NBTTagCompound compound = (NBTTagCompound) nbt;

			boolean hasWrittenInstances = compound.getBoolean("hasWritten");

			if (!hasWrittenInstances)
			{
				return;
			}

			for (NBTTagCompound tag : NBTHelper.getIterator(compound, "data"))
			{
				String key = tag.getString("key");

				NBT value = AetherCore.srl().instantiate(tag.getInteger("srlNumber"));
				value.read(tag);

				instance.set(key, value);
			}
		}
	}
}

