package com.gildedgames.aether.common.capabilities.instances;

import com.gildedgames.aether.api.capabilites.instances.IPlayerInstances;
import com.gildedgames.aether.api.capabilites.instances.Instance;
import com.gildedgames.aether.api.util.WorldPos;
import com.gildedgames.aether.common.util.io.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class PlayerInstances implements IPlayerInstances
{
	private Instance activeInstance;

	private WorldPos outside;

	private EntityPlayer player;

	public PlayerInstances(EntityPlayer player)
	{
		this.player = player;
	}

	@Override
	public Instance getInstance()
	{
		return this.activeInstance;
	}

	@Override
	public void setInstance(Instance instance)
	{
		this.activeInstance = instance;

		if (!this.player.world.isRemote)
		{
			//UtilModule.NETWORK.sendTo(new PacketRegisterInstance(this.activeInstance), (EntityPlayerMP) this.player);
		}
	}

	@Override
	public WorldPos outside()
	{
		return this.outside;
	}

	@Override
	public void setOutside(WorldPos pos)
	{
		this.outside = pos;
	}

	public static class Storage implements Capability.IStorage<IPlayerInstances>
	{

		@Override
		public NBTBase writeNBT(Capability<IPlayerInstances> capability, IPlayerInstances instance, EnumFacing side)
		{
			NBTTagCompound compound = new NBTTagCompound();

			compound.setTag("outside", NBTHelper.serializeBlockPosDimension(instance.outside()));

			NBTHelper.fullySerialize("activeInstance", instance.getInstance(), compound);

			return compound;
		}

		@Override
		public void readNBT(Capability<IPlayerInstances> capability, IPlayerInstances instance, EnumFacing side, NBTBase nbt)
		{
			NBTTagCompound compound = (NBTTagCompound) nbt;

			instance.setOutside(NBTHelper.getBlockPosDimension(compound.getCompoundTag("outside")));
			instance.setInstance(NBTHelper.fullyDeserialize("activeInstance", compound, null));
		}
	}

}
