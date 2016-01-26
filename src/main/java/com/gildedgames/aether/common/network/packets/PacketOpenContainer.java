package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.util.core.io.CustomPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.util.BlockPos;

public class PacketOpenContainer extends CustomPacket<PacketOpenContainer>
{
	private int containerId;

	public PacketOpenContainer() { }

	public PacketOpenContainer(int containerId)
	{
		this.containerId = containerId;
	}

	@Override
	public void handleClientSide(PacketOpenContainer message, EntityPlayer player)
	{
		Minecraft.getMinecraft().thePlayer.openContainer.windowId = message.containerId;
	}

	@Override
	public void handleServerSide(PacketOpenContainer message, EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP)
		{
			BlockPos pos = player.getPosition();

			Container container = AetherCore.PROXY.getGuiHandler().getServerGuiElement(message.containerId, player, player.worldObj, pos.getX(), pos.getY(), pos.getZ());

			if (container == null)
			{
				AetherCore.LOGGER.error("Player " + player.getDisplayNameString() + " tried to open a non-existent container (id: " + message.containerId + "), ignoring.");

				return;
			}

			EntityPlayerMP mpPlayer = (EntityPlayerMP) player;

			mpPlayer.getNextWindowId();
			mpPlayer.closeContainer();

			mpPlayer.openContainer = container;
			mpPlayer.openContainer.windowId = mpPlayer.currentWindowId;

			NetworkingAether.sendPacketToPlayer(new PacketOpenContainer(mpPlayer.openContainer.windowId), mpPlayer);
		}
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.containerId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.containerId);
	}
}
