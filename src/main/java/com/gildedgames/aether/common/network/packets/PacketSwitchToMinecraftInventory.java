package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSwitchToMinecraftInventory implements IMessage
{
	private boolean saveAetherInv;

	public PacketSwitchToMinecraftInventory()
	{

	}

	public PacketSwitchToMinecraftInventory(boolean saveAetherInv)
	{
		this.saveAetherInv = saveAetherInv;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.saveAetherInv = buf.readBoolean();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeBoolean(this.saveAetherInv);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketSwitchToMinecraftInventory, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketSwitchToMinecraftInventory message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			playerAether.getSeparateInventoryModule().switchToMinecraftInventory(message.saveAetherInv);

			return null;
		}
	}

}
