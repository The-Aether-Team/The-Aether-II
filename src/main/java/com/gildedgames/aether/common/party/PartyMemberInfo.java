package com.gildedgames.aether.common.party;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

/**
 * Information about a party member which depends on the player entity.
 */
public class PartyMemberInfo
{
	protected String name;

	// TODO: Avatar

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public static PartyMemberInfo create(EntityPlayer player)
	{
		PartyMemberInfo info = new PartyMemberInfo();
		info.setName(player.getDisplayNameString());

		return info;
	}

	public void read(ByteBuf buf)
	{
		this.setName(ByteBufUtils.readUTF8String(buf));
	}

	public void write(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.getName());
	}

	public String toString()
	{
		return String.format("name=%s", this.getName());
	}
}
