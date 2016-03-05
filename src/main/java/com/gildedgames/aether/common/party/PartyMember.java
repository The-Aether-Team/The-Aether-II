package com.gildedgames.aether.common.party;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

/**
 * Contains information about a player within a party.
 * This information never changes, and can be used while offline.
 *
 * Information that changes should be in PartyMemberInfo.
 */
public class PartyMember
{
	protected final UUID playerId;

	protected MemberRank rank;

	protected PartyMemberInfo info;

	public PartyMember(UUID playerId)
	{
		this.playerId = playerId;
	}

	public UUID getPlayerUUID()
	{
		return this.playerId;
	}

	public PartyMemberInfo getInfo()
	{
		return this.info;
	}

	public void setInfo(PartyMemberInfo info)
	{
		this.info = info;
	}

	public MemberRank getMemberRank()
	{
		return this.rank;
	}

	public void setMemberRank(MemberRank rank)
	{
		this.rank = rank;
	}

	public void write(ByteBuf buf)
	{
		buf.writeLong(this.getPlayerUUID().getMostSignificantBits());
		buf.writeLong(this.getPlayerUUID().getLeastSignificantBits());

		buf.writeByte(this.getMemberRank().ordinal());

		this.getInfo().write(buf);
	}

	public static PartyMember read(ByteBuf buf)
	{
		PartyMember member = new PartyMember(new UUID(buf.readLong(), buf.readLong()));
		member.setMemberRank(MemberRank.values()[buf.readByte()]);
		member.setInfo(new PartyMemberInfo());

		member.getInfo().read(buf);

		return member;
	}

	public String toString()
	{
		return String.format("%s[rank=%s, info=(%s)]", this.getPlayerUUID(), this.getMemberRank().name(), this.getInfo());
	}
}
