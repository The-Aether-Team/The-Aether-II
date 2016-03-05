package com.gildedgames.aether.common.network.packets.party;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.party.MemberRank;
import com.gildedgames.aether.common.party.Party;
import com.gildedgames.aether.common.party.PartyMember;
import com.gildedgames.aether.common.party.PartyMemberInfo;
import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.util.core.io.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.UUID;

public class PacketMemberChange implements IMessage
{
	public enum MemberAction
	{
		ADDED, REMOVED, INVITIATION_SENT
	}

	private UUID partyId, memberId;

	private PartyMemberInfo memberInfo;

	private MemberAction action;

	public PacketMemberChange() { }

	public PacketMemberChange(Party party, PartyMember member, MemberAction action)
	{
		this.partyId = party.getUniqueId();
		this.memberId = member.getPlayerUUID();

		this.action = action;
		this.memberInfo = member.getInfo();
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.partyId = new UUID(buf.readLong(), buf.readLong());
		this.memberId = new UUID(buf.readLong(), buf.readLong());

		this.action = MemberAction.values()[buf.readByte()];

		if (this.action == MemberAction.ADDED || this.action == MemberAction.INVITIATION_SENT)
		{
			this.memberInfo = new PartyMemberInfo();
			this.memberInfo.read(buf);
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeLong(this.partyId.getMostSignificantBits());
		buf.writeLong(this.partyId.getLeastSignificantBits());

		buf.writeLong(this.memberId.getMostSignificantBits());
		buf.writeLong(this.memberId.getLeastSignificantBits());

		buf.writeByte(this.action.ordinal());

		if (this.action == MemberAction.ADDED || this.action == MemberAction.INVITIATION_SENT)
		{
			this.memberInfo.write(buf);
		}
	}

	public static class HandlerClient extends MessageHandlerClient<PacketMemberChange, IMessage>
	{
		@Override
		public IMessage onMessage(PacketMemberChange message, EntityPlayer player)
		{
			PlayerAether aePlayer = PlayerAether.get(player);

			Party party = aePlayer.getCurrentParty();

			if (!party.getUniqueId().equals(message.partyId))
			{
				AetherCore.LOGGER.warn("Received PacketMemberChange for a party we're not in!");
				return null;
			}

			if (message.action == MemberAction.ADDED)
			{
				PartyMember member = new PartyMember(message.memberId);
				member.setInfo(message.memberInfo);

				party.addMember(member);
			}
			else if (message.action == MemberAction.REMOVED)
			{
				PartyMember member = party.getMember(message.memberId);

				if (member == null)
				{
					AetherCore.LOGGER.warn("Server asked us to remove a member from our party that isn't there!");
					return null;
				}

				party.removeMember(member);
			}
			else if (message.action == MemberAction.INVITIATION_SENT)
			{
				PartyMember member = new PartyMember(message.memberId);
				member.setInfo(message.memberInfo);
				member.setMemberRank(MemberRank.INVITED);

				party.addMember(member);
			}

			return null;
		}
	}
}
