package com.gildedgames.aether.common.network.packets.dialog;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.api.dialog.IDialogButton;
import com.gildedgames.aether.api.dialog.IDialogController;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerDialogModule;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.google.common.base.Objects;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.Collection;

public class PacketActivateButton implements IMessage
{
	private String label;

	public PacketActivateButton(final String label)
	{
		this.label = label;
	}

	@Override
	public void fromBytes(final PacketBuffer buf)
	{
		this.label = buf.readString();
	}

	@Override
	public void toBytes(final PacketBuffer buf)
	{
		buf.writeString(this.label);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketActivateButton>
	{
		@Override
		protected void onMessage(PacketActivateButton message, ClientPlayerEntity player)
		{
			final IPlayerAether aePlayer = PlayerAether.getPlayer(player);
			final IDialogController dialogController = aePlayer.getModule(PlayerDialogModule.class);

			if (dialogController.getCurrentSceneInstance() == null)
			{
				return;
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
		}
	}

	public static class HandlerServer extends MessageHandlerServer<PacketActivateButton>
	{
		@Override
		protected void onMessage(PacketActivateButton message, ServerPlayerEntity player)
		{
			final IPlayerAether aePlayer = PlayerAether.getPlayer(player);
			final IDialogController dialogController = aePlayer.getModule(PlayerDialogModule.class);

			if (dialogController.getCurrentSceneInstance() == null)
			{
				return;
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

			return;
		}
	}
}
