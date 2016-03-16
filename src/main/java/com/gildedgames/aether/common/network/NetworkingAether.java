package com.gildedgames.aether.common.network;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.packets.party.PacketAcceptInvite;
import com.gildedgames.aether.common.network.packets.party.PacketCreateParty;
import com.gildedgames.aether.common.network.packets.party.PacketJoinParty;
import com.gildedgames.aether.common.network.packets.party.PacketMemberChange;
import com.gildedgames.aether.common.network.packets.player.PacketOpenContainer;
import com.gildedgames.util.core.UtilModule;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NetworkingAether
{
	private static SimpleNetworkWrapper instance;

	private static int discriminant = 0;

	public static void preInit()
	{
		instance = NetworkRegistry.INSTANCE.newSimpleChannel(AetherCore.MOD_ID);

		// CLIENT -> SERVER
		instance.registerMessage(PacketOpenContainer.HandlerServer.class, PacketOpenContainer.class, discriminant++, Side.SERVER);

		instance.registerMessage(PacketAcceptInvite.HandlerServer.class, PacketAcceptInvite.class, discriminant++, Side.SERVER);

		instance.registerMessage(PacketCreateParty.HandlerServer.class, PacketCreateParty.class, discriminant++, Side.SERVER);

		// SERVER -> CLIENT
		instance.registerMessage(PacketMemberChange.HandlerClient.class, PacketMemberChange.class, discriminant++, Side.CLIENT);

		instance.registerMessage(PacketJoinParty.HandlerClient.class, PacketJoinParty.class, discriminant++, Side.CLIENT);
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
