package com.gildedgames.aether.common.party;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class Party
{
	protected final UUID uniqueId;

	protected final HashMap<UUID, PartyMember> members = new HashMap<>();

	protected String name;

	public Party(UUID uniqueId)
	{
		this.uniqueId = uniqueId;
	}

	public void addMember(PartyMember member)
	{
		this.members.put(member.getPlayerUUID(), member);
	}

	public void removeMember(PartyMember member)
	{
		this.members.remove(member.getPlayerUUID());
	}

	public PartyMember getMember(UUID uniqueId)
	{
		return this.members.get(uniqueId);
	}

	public Collection<PartyMember> getMembers()
	{
		return this.members.values();
	}

	public boolean canPlayerJoin(EntityPlayer player)
	{
		if (this.members.containsKey(player.getUniqueID()))
		{
			PartyMember member = this.members.get(player.getUniqueID());

			if (member.getMemberRank() == MemberRank.INVITED)
			{
				return true;
			}
		}

		return false;
	}

	public UUID getUniqueId()
	{
		return this.uniqueId;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void write(ByteBuf buf)
	{
		buf.writeLong(this.uniqueId.getMostSignificantBits());
		buf.writeLong(this.uniqueId.getLeastSignificantBits());

		ByteBufUtils.writeUTF8String(buf, this.name);

		buf.writeInt(this.members.size());

		for (PartyMember member : this.members.values())
		{
			member.write(buf);
		}
	}

	public static Party read(ByteBuf buf)
	{
		Party party = new Party(new UUID(buf.readLong(), buf.readLong()));
		party.setName(ByteBufUtils.readUTF8String(buf));

		int memberCount = buf.readInt();

		for (int i = 0; i < memberCount; i++)
		{
			PartyMember member = PartyMember.read(buf);

			party.addMember(member);
		}

		return party;
	}
}
