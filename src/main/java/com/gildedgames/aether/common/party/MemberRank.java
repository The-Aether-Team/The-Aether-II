package com.gildedgames.aether.common.party;

import net.minecraft.util.EnumChatFormatting;

public enum MemberRank
{
	OWNER(EnumChatFormatting.AQUA),
	LEADER(EnumChatFormatting.GREEN),
	NORMAL(EnumChatFormatting.WHITE),
	INVITED(EnumChatFormatting.GRAY);

	private final EnumChatFormatting color;

	MemberRank(EnumChatFormatting color)
	{
		this.color = color;
	}

	public boolean canDisbandParty()
	{
		return this == MemberRank.OWNER;
	}

	public boolean canStartDungeon()
	{
		return this == MemberRank.OWNER || this == MemberRank.LEADER;
	}

	public EnumChatFormatting getColor()
	{
		return this.color;
	}
}
