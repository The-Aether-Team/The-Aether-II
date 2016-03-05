package com.gildedgames.aether.common.network.packets.party;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.party.MemberRank;
import com.gildedgames.aether.common.party.Party;
import com.gildedgames.aether.common.party.PartyController;
import com.gildedgames.aether.common.party.PartyMember;
import com.gildedgames.aether.common.party.PartyMemberInfo;
import com.gildedgames.util.core.io.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.UUID;

public class PacketAcceptInvite implements IMessage
{
	private UUID partyId;

	public PacketAcceptInvite() { }

	public PacketAcceptInvite(UUID partyId)
	{
		this.partyId = partyId;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.partyId = new UUID(buf.readLong(), buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeLong(this.partyId.getMostSignificantBits());
		buf.writeLong(this.partyId.getLeastSignificantBits());
	}

	public static class HandlerServer extends MessageHandlerServer<PacketAcceptInvite, IMessage>
	{
		@Override
		public IMessage onMessage(PacketAcceptInvite message, EntityPlayer player)
		{
			Party party = PartyController.INSTANCE.getParty(message.partyId);

			if (party == null)
			{
				AetherCore.LOGGER.warn("Player " + player.getName() + " tried to join a non-existent party.");
				return null;
			}

			if (party.canPlayerJoin(player))
			{
				AetherCore.LOGGER.warn("Player " + player.getName() + " tried to join a party they weren't invited to!");
				return null;
			}

			PartyMember member = new PartyMember(player.getUniqueID());
			member.setInfo(PartyMemberInfo.create(player));
			member.setMemberRank(MemberRank.NORMAL);

			party.addMember(member);

			return null;
		}
	}
}
