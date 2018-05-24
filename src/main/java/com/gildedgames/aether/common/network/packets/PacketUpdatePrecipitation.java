package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.world.aether.prep.PrepSectorDataAether;
import com.gildedgames.aether.common.world.aether.prep.PrepSectorDataPartialAether;
import com.gildedgames.orbis_api.preparation.IPrepManager;
import com.gildedgames.orbis_api.preparation.IPrepSector;
import com.gildedgames.orbis_api.preparation.impl.util.PrepHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.io.IOException;
import java.util.Optional;

public class PacketUpdatePrecipitation implements IMessage
{
	private int x, z;

	private NBTTagCompound tag;

	private IPrecipitationManager precipitation;

	@SuppressWarnings("unchecked")
	public PacketUpdatePrecipitation()
	{

	}

	public PacketUpdatePrecipitation(PrepSectorDataAether data)
	{
		this.precipitation = data.getIslandData().getPrecipitation();

		this.x = data.getSectorX();
		this.z = data.getSectorY();
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.x = buf.readInt();
		this.z = buf.readInt();

		try (ByteBufInputStream stream = new ByteBufInputStream(buf))
		{
			this.tag = CompressedStreamTools.readCompressed(stream);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.x);
		buf.writeInt(this.z);

		NBTTagCompound tag = this.precipitation.serializeNBT();

		try (ByteBufOutputStream stream = new ByteBufOutputStream(buf))
		{
			CompressedStreamTools.writeCompressed(tag, stream);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static class HandlerClient extends MessageHandlerClient<PacketUpdatePrecipitation, IMessage>
	{
		@Override
		public IMessage onMessage(PacketUpdatePrecipitation message, EntityPlayer player)
		{
			final IPrepManager manager = PrepHelper.getManager(player.world);

			if (manager != null)
			{
				final Optional<IPrepSector> sector = manager.getClientAccess().getLoadedSector(message.x, message.z);

				if (!sector.isPresent())
				{
					AetherCore.LOGGER.info("Tried to update precipitation for sector (" + message.x + ", " + message.z + "), but it's not loaded");

					return null;
				}

				((PrepSectorDataPartialAether) sector.get().getData()).getIslandData()
						.getPrecipitation().deserializeNBT(message.tag);
			}


			return null;
		}
	}
}
