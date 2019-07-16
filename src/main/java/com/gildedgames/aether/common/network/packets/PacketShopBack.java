package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerDialogModule;
import com.gildedgames.aether.common.containers.ContainerShop;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class PacketShopBack implements IMessage
{
	public static class HandlerServer extends MessageHandlerServer<PacketShopBack>
	{
		@Override
		protected void onMessage(PacketShopBack message, ServerPlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return;
			}

			if (player.openContainer instanceof ContainerShop)
			{
				PlayerAether playerAether = PlayerAether.getPlayer(player);

				PlayerDialogModule dialogModule = playerAether.getModule(PlayerDialogModule.class);
				dialogModule.navigateNode(dialogModule.getCurrentScene().getStartingNode().getIdentifier());

				BlockPos pos = player.getPosition();

				player.openGui(AetherCore.MOD_ID, AetherGuiHandler.DIALOG_VIEWER_ID, player.world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
	}

}
