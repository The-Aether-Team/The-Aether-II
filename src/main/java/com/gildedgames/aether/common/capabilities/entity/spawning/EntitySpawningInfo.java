package com.gildedgames.aether.common.capabilities.entity.spawning;

import com.gildedgames.aether.api.entity.spawning.EntitySpawn;
import com.gildedgames.aether.api.entity.spawning.ISpawningInfo;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class EntitySpawningInfo implements ISpawningInfo
{

	private EntitySpawn spawnArea;

	public EntitySpawningInfo()
	{

	}

	@Override
	public EntitySpawn getSpawnArea()
	{
		return this.spawnArea;
	}

	@Override
	public void setSpawnArea(EntitySpawn area)
	{
		this.spawnArea = area;
	}

	public static class Storage implements Capability.IStorage<ISpawningInfo>
	{
		@Override
		public NBTBase writeNBT(Capability<ISpawningInfo> capability, ISpawningInfo instance, EnumFacing side)
		{
			NBTTagCompound tag = new NBTTagCompound();

			boolean hasSpawnArea = instance.getSpawnArea() != null;

			tag.setBoolean("hasSpawnArea", hasSpawnArea);

			if (hasSpawnArea)
			{
				tag.setString("uniqueID", instance.getSpawnArea().getSpawnHandlerUniqueID());
				tag.setInteger("dim", instance.getSpawnArea().getDim());
				tag.setInteger("areaX", instance.getSpawnArea().getAreaX());
				tag.setInteger("areaZ", instance.getSpawnArea().getAreaZ());
			}

			return tag;
		}

		@Override
		public void readNBT(Capability<ISpawningInfo> capability, ISpawningInfo instance, EnumFacing side, NBTBase nbt)
		{
			NBTTagCompound tag = (NBTTagCompound) nbt;

			if (tag.getBoolean("hasSpawnArea"))
			{
				EntitySpawn spawnArea = new EntitySpawn(tag.getString("uniqueID"), tag.getInteger("dim"), tag.getInteger("areaX"), tag.getInteger("areaZ"));

				instance.setSpawnArea(spawnArea);
			}
		}
	}

}
