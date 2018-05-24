package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.world.aether.prep.PrepSectorDataAether;
import com.gildedgames.aether.common.world.aether.prep.PrepSectorDataPartialAether;
import com.gildedgames.orbis_api.preparation.IPrepManager;
import com.gildedgames.orbis_api.preparation.impl.PrepSector;
import com.gildedgames.orbis_api.preparation.impl.util.PrepHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.io.IOException;

public class PacketPartialSectorData implements IMessage
{
	private NBTTagCompound tag;

	@SuppressWarnings("unused")
	public PacketPartialSectorData()
	{

	}

	public PacketPartialSectorData(PrepSectorDataAether data)
	{
		NBTTagCompound tag = new NBTTagCompound();
		data.writePartial(tag);

		this.tag = tag;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
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
		try (ByteBufOutputStream stream = new ByteBufOutputStream(buf))
		{
			CompressedStreamTools.writeCompressed(this.tag, stream);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static class HandlerClient extends MessageHandlerClient<PacketPartialSectorData,IMessage>
	{
		@Override
		public IMessage onMessage(PacketPartialSectorData message, EntityPlayer player)
		{
			final IPrepManager manager = PrepHelper.getManager(player.world);

			if (manager != null)
			{
				final PrepSectorDataPartialAether data = new PrepSectorDataPartialAether(player.world, message.tag);

				if (!manager.getClientAccess().getLoadedSector(data.getSectorX(), data.getSectorY()).isPresent())
				{
					PrepSector sector = new PrepSector(data);

					data.setParent(sector);

					manager.getClientAccess().addSector(sector);
				}
			}

			return null;
		}
	}
}
