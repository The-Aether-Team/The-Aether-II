package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketPartialSectorData;
import com.gildedgames.aether.common.network.packets.PacketUnloadSector;
import com.gildedgames.aether.common.world.aether.prep.PrepSectorDataAether;
import com.gildedgames.orbis_api.preparation.IPrepSector;
import com.gildedgames.orbis_api.preparation.impl.util.PrepHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerSectorModule extends PlayerAetherModule
{
	private IPrepSector lastSector;

	public PlayerSectorModule(PlayerAether playerAether)
	{
		super(playerAether);
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void tickEnd(TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void onUpdate()
	{
		if (this.getWorld().isRemote)
		{
			return;
		}

		IPrepSector sector = PrepHelper.getSector(this.getWorld(), this.getEntity().chunkCoordX, this.getEntity().chunkCoordZ);

		if (this.lastSector != sector)
		{
			if (this.lastSector != null)
			{
				NetworkingAether.sendPacketToPlayer(new PacketUnloadSector(this.lastSector.getData()), (EntityPlayerMP) this.getEntity());
			}

			if (sector != null)
			{
				NetworkingAether.sendPacketToPlayer(new PacketPartialSectorData((PrepSectorDataAether) sector.getData()), (EntityPlayerMP) this.getEntity());
			}

			this.lastSector = sector;
		}
	}

	@Override
	public void write(NBTTagCompound tag)
	{

	}

	@Override
	public void read(NBTTagCompound tag)
	{

	}
}
