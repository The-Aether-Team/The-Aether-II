package com.gildedgames.aether.common.network.packets.party;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.party.Party;
import com.gildedgames.aether.common.party.PartyController;
import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.util.core.io.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketCreateParty implements IMessage
{
	private String name;

	public PacketCreateParty() { }

	public PacketCreateParty(String name)
	{
		this.name = name;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.name = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.name);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketCreateParty, PacketJoinParty>
	{
		@Override
		public PacketJoinParty onMessage(PacketCreateParty message, EntityPlayer player)
		{
			PlayerAether aePlayer = PlayerAether.get(player);

			if (aePlayer.getCurrentParty() != null)
			{
				AetherCore.LOGGER.warn("Player tried to create a party, but they're still in a party!");

				return null;
			}

			PartyController controller = PartyController.INSTANCE;

			Party party = controller.createParty(aePlayer, message.name);
			aePlayer.setCurrentParty(party);

			controller.addParty(party);

			AetherCore.LOGGER.info("Created new party with name " + message.name + " successfully!");

			return new PacketJoinParty(party);
		}
	}
}
