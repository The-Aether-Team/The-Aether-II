package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class PacketUpdatePrecipitation implements IMessage
{
	private CompoundNBT tag;

	private IPrecipitationManager precipitation;

	public PacketUpdatePrecipitation(IPrecipitationManager precipitation)
	{
		this.precipitation = precipitation;
	}

	@Override
	public void fromBytes(PacketBuffer buf)
	{
		this.tag = buf.readCompoundTag();
	}

	@Override
	public void toBytes(PacketBuffer buf)
	{
		buf.writeCompoundTag(this.precipitation.serializeNBT());
	}

	public static class HandlerClient extends MessageHandlerClient<PacketUpdatePrecipitation>
	{
		@Override
		protected void onMessage(PacketUpdatePrecipitation message, ClientPlayerEntity player)
		{
			IPrecipitationManager precip = player.world.getCapability(CapabilitiesAether.PRECIPITATION_MANAGER, null)
					.orElseThrow(NullPointerException::new);
			precip.deserializeNBT(message.tag);
		}
	}
}
