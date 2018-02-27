package com.gildedgames.aether.common.capabilities.world.instances;

import com.gildedgames.aether.api.util.BlockPosDimension;
import com.gildedgames.aether.api.world.instances.IInstance;
import com.gildedgames.aether.api.world.instances.IPlayerInstances;
import com.gildedgames.orbis.api.util.mc.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class PlayerInstances implements IPlayerInstances
{
	private final EntityPlayer player;

	private IInstance activeInstance;

	private BlockPosDimension outside;

	public PlayerInstances()
	{
		this.player = null;
	}

	public PlayerInstances(final EntityPlayer player)
	{
		this.player = player;
	}

	@Override
	public IInstance getInstance()
	{
		return this.activeInstance;
	}

	@Override
	public void setInstance(final IInstance instance)
	{
		if (this.getInstance() != null)
		{
			this.getInstance().onLeave(this.player);
		}

		this.activeInstance = instance;

		if (!this.player.world.isRemote)
		{
			//UtilModule.NETWORK.sendTo(new PacketRegisterInstance(this.activeInstance), (EntityPlayerMP) this.player);
		}
	}

	@Override
	public BlockPosDimension outside()
	{
		return this.outside;
	}

	@Override
	public void setOutside(final BlockPosDimension pos)
	{
		this.outside = pos;
	}

	public static class Storage implements Capability.IStorage<IPlayerInstances>
	{

		@Override
		public NBTBase writeNBT(final Capability<IPlayerInstances> capability, final IPlayerInstances instance, final EnumFacing side)
		{
			final NBTTagCompound compound = new NBTTagCompound();

			compound.setTag("outside", NBTHelper.write(instance.outside()));
			compound.setTag("activeInstances", NBTHelper.write(instance.getInstance()));

			return compound;
		}

		@Override
		public void readNBT(final Capability<IPlayerInstances> capability, final IPlayerInstances instance, final EnumFacing side, final NBTBase nbt)
		{
			final NBTTagCompound compound = (NBTTagCompound) nbt;

			instance.setOutside(NBTHelper.read(compound.getCompoundTag("outside")));
			instance.setInstance(NBTHelper.read(compound.getCompoundTag("activeInstances")));
		}
	}

}
