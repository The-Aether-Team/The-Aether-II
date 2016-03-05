package com.gildedgames.aether.common.network.packets.party;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.party.Party;
import com.gildedgames.aether.common.party.PartyMember;
import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.util.core.io.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketJoinParty implements IMessage
{
	private Party party;

	public PacketJoinParty() { }

	public PacketJoinParty(Party party)
	{
		this.party = party;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.party = Party.read(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		this.party.write(buf);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketJoinParty, IMessage>
	{
		@Override
		public IMessage onMessage(PacketJoinParty message, EntityPlayer player)
		{
			PlayerAether aePlayer = PlayerAether.get(player);

			aePlayer.setCurrentParty(message.party);

			AetherCore.LOGGER.info("Joined party " + message.party.getName());

			for (PartyMember member : message.party.getMembers())
			{
				AetherCore.LOGGER.info(member.toString());
			}

			return null;
		}
	}
}
