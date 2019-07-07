package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.io.IOException;

public class PacketUpdatePrecipitation implements IMessage
{
	private NBTTagCompound tag;

	private IPrecipitationManager precipitation;

	public PacketUpdatePrecipitation()
	{

	}

	public PacketUpdatePrecipitation(IPrecipitationManager precipitation)
	{
		this.precipitation = precipitation;
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
			IPrecipitationManager precip = player.world.getCapability(CapabilitiesAether.PRECIPITATION_MANAGER, null);

			if (precip != null)
			{
				precip.deserializeNBT(message.tag);
			}

			return null;
		}
	}
}
