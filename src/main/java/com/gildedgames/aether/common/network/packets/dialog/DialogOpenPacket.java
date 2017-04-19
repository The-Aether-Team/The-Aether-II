package com.gildedgames.aether.common.network.packets.dialog;

import com.gildedgames.aether.api.capabilites.entity.IPlayerAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class DialogOpenPacket implements IMessage
{
	private ResourceLocation name;

	public DialogOpenPacket() { }

	public DialogOpenPacket(ResourceLocation res)
	{
		this.name = res;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.name = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.name.toString());
	}

	public static class HandlerClient extends MessageHandlerClient<DialogOpenPacket, DialogOpenPacket>
	{
		@Override
		public DialogOpenPacket onMessage(DialogOpenPacket message, EntityPlayer player)
		{
			AetherCore.LOGGER.info("Server requested player to open dialog file {}", message.name);

			IPlayerAether aePlayer = PlayerAether.getPlayer(player);
			aePlayer.getDialogController().openScene(message.name);

			return null;
		}
	}
}
