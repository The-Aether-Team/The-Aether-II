package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerDialogModule;
import com.gildedgames.aether.common.containers.ContainerShop;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketShopBack implements IMessage
{

	public PacketShopBack()
	{

	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{

	}

	@Override
	public void toBytes(final ByteBuf buf)
	{

	}

	public static class HandlerServer extends MessageHandlerServer<PacketShopBack, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketShopBack message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			if (player.openContainer instanceof ContainerShop)
			{
				PlayerAether playerAether = PlayerAether.getPlayer(player);

				PlayerDialogModule dialogModule = playerAether.getModule(PlayerDialogModule.class);
				dialogModule.navigateNode(dialogModule.getCurrentScene().getStartingNode().getIdentifier());

				BlockPos pos = player.getPosition();

				player.openGui(AetherCore.MOD_ID, AetherGuiHandler.DIALOG_VIEWER_ID, player.world, pos.getX(), pos.getY(), pos.getZ());
			}

			return null;
		}
	}

}
