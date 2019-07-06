package com.gildedgames.aether.common.network.packets.dialog;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerDialogModule;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketNavigateNode implements IMessage
{
	private String nodeId;

	public PacketNavigateNode()
	{
	}

	public PacketNavigateNode(final String label)
	{
		this.nodeId = label;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.nodeId = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.nodeId);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketNavigateNode, PacketNavigateNode>
	{
		@Override
		public PacketNavigateNode onMessage(final PacketNavigateNode message, final EntityPlayer player)
		{
			final IPlayerAether aePlayer = PlayerAether.getPlayer(player);
			final PlayerDialogModule module = aePlayer.getModule(PlayerDialogModule.class);

			module.navigateNodeClient(message.nodeId);

			return null;
		}
	}
}
