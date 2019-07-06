package com.gildedgames.aether.common.network.packets.dialog;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.api.dialog.IDialogButton;
import com.gildedgames.aether.api.dialog.IDialogController;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerDialogModule;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.google.common.base.Objects;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.Collection;

public class PacketActivateButton implements IMessage
{
	private String label;

	public PacketActivateButton()
	{
	}

	public PacketActivateButton(final String label)
	{
		this.label = label;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.label = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.label);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketActivateButton, PacketActivateButton>
	{
		@Override
		public PacketActivateButton onMessage(final PacketActivateButton message, final EntityPlayer player)
		{
			final IPlayerAether aePlayer = PlayerAether.getPlayer(player);
			final IDialogController dialogController = aePlayer.getModule(PlayerDialogModule.class);

			if (dialogController.getCurrentSceneInstance() == null)
			{
				return null;
			}

			IDialogButton found = null;

			for (final IDialogButton b : dialogController.getCurrentNode().getButtons())
			{
				if (Objects.equal(message.label, b.getLabel()))
				{
					found = b;
					break;
				}
			}

			if (found != null && dialogController.conditionsMet(found))
			{
				final Collection<IDialogAction> actions = found.getActions();

				for (final IDialogAction action : actions)
				{
					action.performAction(dialogController);
				}
			}

			return null;
		}
	}

	public static class HandlerServer extends MessageHandlerServer<PacketActivateButton, PacketActivateButton>
	{
		@Override
		public PacketActivateButton onMessage(final PacketActivateButton message, final EntityPlayer player)
		{
			final IPlayerAether aePlayer = PlayerAether.getPlayer(player);
			final IDialogController dialogController = aePlayer.getModule(PlayerDialogModule.class);

			if (dialogController.getCurrentSceneInstance() == null)
			{
				return null;
			}

			IDialogButton found = null;

			for (final IDialogButton b : dialogController.getCurrentNode().getButtons())
			{
				if (Objects.equal(message.label, b.getLabel()))
				{
					found = b;
					break;
				}
			}

			if (found != null)
			{
				dialogController.activateButton(found);
			}

			return null;
		}
	}
}
