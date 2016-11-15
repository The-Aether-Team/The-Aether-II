package com.gildedgames.aether.common.network;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.packets.*;
import com.gildedgames.aether.common.util.structure.StructureInjectionPacket;
import com.gildedgames.aether.common.network.packets.PacketOpenTab;
import com.gildedgames.aether.common.network.packets.PacketRegisterDimension;
import com.gildedgames.aether.common.network.packets.PacketRegisterInstance;
import com.gildedgames.aether.common.network.packets.PacketUnregisterDimension;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
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

		instance.registerMessage(AetherMovementPacket.Handler.class, AetherMovementPacket.class, discriminant++, Side.SERVER);
		instance.registerMessage(EquipmentChangedPacket.Handler.class, EquipmentChangedPacket.class, discriminant++, Side.CLIENT);
		instance.registerMessage(CompanionChangedPacket.Handler.class, CompanionChangedPacket.class, discriminant++, Side.CLIENT);
		instance.registerMessage(MidAirJumpsChangedPacket.Handler.class, MidAirJumpsChangedPacket.class, discriminant++, Side.CLIENT);
		instance.registerMessage(StructureInjectionPacket.Handler.class, StructureInjectionPacket.class, discriminant++, Side.SERVER);
		instance.registerMessage(BossChangePacket.Handler.class, BossChangePacket.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketOpenTab.HandlerServer.class, PacketOpenTab.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketRegisterDimension.Handler.class, PacketRegisterDimension.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketUnregisterDimension.Handler.class, PacketUnregisterDimension.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketRegisterInstance.Handler.class, PacketRegisterInstance.class, discriminant++, Side.CLIENT);
		instance.registerMessage(SimpleRecipeChangedPacket.HandlerServer.class, SimpleRecipeChangedPacket.class, discriminant++, Side.SERVER);

		NetworkRegistry.INSTANCE.registerGuiHandler(AetherCore.INSTANCE, new AetherGuiHandler());
	}

	public static void sendPacketToAllPlayers(IMessage message)
	{
		NetworkingAether.instance.sendToAll(message);
	}

	public static void sendPacketToPlayer(IMessage message, EntityPlayerMP player)
	{
		NetworkingAether.instance.sendTo(message, player);
	}

	public static void sendPacketToWatching(IMessage message, EntityLivingBase entity)
	{
		WorldServer world = (WorldServer) entity.worldObj;

		EntityTracker tracker = world.getEntityTracker();

		for (EntityPlayer player : tracker.getTrackingPlayers(entity))
		{
			NetworkingAether.sendPacketToPlayer(message, (EntityPlayerMP) player);
		}

		// Entities don't watch themselves, take special care here
		if (entity instanceof EntityPlayer)
		{
			NetworkingAether.sendPacketToPlayer(message, (EntityPlayerMP) entity);
		}
	}

	@SideOnly(Side.CLIENT)
	public static void sendPacketToServer(IMessage message)
	{
		NetworkingAether.instance.sendToServer(message);
	}
}
