package com.gildedgames.aether.common.capabilities.entity.spawning;

import com.gildedgames.aether.api.entity.spawning.EntitySpawn;
import com.gildedgames.aether.api.entity.spawning.ISpawningInfo;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
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
		public INBT writeNBT(Capability<ISpawningInfo> capability, ISpawningInfo instance, Direction side)
		{
			CompoundNBT tag = new CompoundNBT();

			boolean hasSpawnArea = instance.getSpawnArea() != null;

			tag.putBoolean("hasSpawnArea", hasSpawnArea);

			if (hasSpawnArea)
			{
				tag.putString("uniqueID", instance.getSpawnArea().getSpawnHandlerUniqueID());
				tag.putInt("dim", instance.getSpawnArea().getDim());
				tag.putInt("areaX", instance.getSpawnArea().getAreaX());
				tag.putInt("areaZ", instance.getSpawnArea().getAreaZ());
			}

			return tag;
		}

		@Override
		public void readNBT(Capability<ISpawningInfo> capability, ISpawningInfo instance, Direction side, INBT nbt)
		{
			CompoundNBT tag = (CompoundNBT) nbt;

			if (tag.getBoolean("hasSpawnArea"))
			{
				EntitySpawn spawnArea = new EntitySpawn(tag.getString("uniqueID"), tag.getInt("dim"), tag.getInt("areaX"), tag.getInt("areaZ"));

				instance.setSpawnArea(spawnArea);
			}
		}
	}

}
