package com.gildedgames.aether.common.network;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.packets.PacketOpenContainer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NetworkingAether
{
	private static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(AetherCore.MOD_ID);

	private static int discriminant = 0;

	public static void preInit()
	{
		registerBiPacket(PacketOpenContainer.class, PacketOpenContainer.class);
	}

	private static <REQ extends IMessage, REPLY extends IMessage> void registerBiPacket(Class<? extends IMessageHandler<REQ, REPLY>> handler, Class<REQ> type)
	{
		NetworkingAether.instance.registerMessage(handler, type, discriminant++, Side.CLIENT);
		NetworkingAether.instance.registerMessage(handler, type, discriminant++, Side.SERVER);
	}

	public static void sendPacketToPlayer(IMessage message, EntityPlayerMP player)
	{
		NetworkingAether.instance.sendTo(message, player);
	}

	@SideOnly(Side.CLIENT)
	public static void sendPacketToServer(IMessage message)
	{
		NetworkingAether.instance.sendToServer(message);
	}
}
