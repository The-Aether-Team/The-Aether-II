package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selection_input.ISelectionInput;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketChangeSelectionInput implements IMessage
{
	private int selectionInputIndex;

	public PacketChangeSelectionInput()
	{

	}

	public PacketChangeSelectionInput(final PlayerOrbisModule module, final ISelectionInput selectionInput)
	{
		this.selectionInputIndex = module.selectionInputs().getSelectionInputIndex(selectionInput);
	}

	public PacketChangeSelectionInput(final int powerIndex)
	{
		this.selectionInputIndex = powerIndex;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.selectionInputIndex = buf.readInt();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeInt(this.selectionInputIndex);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketChangeSelectionInput, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketChangeSelectionInput message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerOrbisModule module = PlayerOrbisModule.get(player);

			if (module.inDeveloperMode())
			{
				module.selectionInputs().setCurrentSelectionInput(message.selectionInputIndex);
			}

			return null;
		}
	}
}
