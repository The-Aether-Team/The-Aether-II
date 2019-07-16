package com.gildedgames.aether.common.network.packets.dialog;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerDialogModule;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketNavigateNode implements IMessage
{
	private String nodeId;

	public PacketNavigateNode(final String label)
	{
		this.nodeId = label;
	}

	@Override
	public void fromBytes(final PacketBuffer buf)
	{
		this.nodeId = buf.readString();
	}

	@Override
	public void toBytes(final PacketBuffer buf)
	{
		buf.writeString(this.nodeId);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketNavigateNode>
	{
		@Override
		protected void onMessage(PacketNavigateNode message, ClientPlayerEntity player)
		{
			final IPlayerAether aePlayer = PlayerAether.getPlayer(player);
			final PlayerDialogModule module = aePlayer.getModule(PlayerDialogModule.class);

			module.navigateNodeClient(message.nodeId);
		}
	}
}
