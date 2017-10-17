package com.gildedgames.aether.common.network;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.packets.*;
import com.gildedgames.aether.common.network.packets.dialog.PacketCloseDialog;
import com.gildedgames.aether.common.network.packets.dialog.PacketOpenDialog;
import com.gildedgames.orbis.common.network.packets.*;
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

	private static int discriminant;

	public static void preInit()
	{
		instance = NetworkRegistry.INSTANCE.newSimpleChannel(AetherCore.MOD_ID);

		// S E R V E R
		instance.registerMessage(PacketSpecialMovement.HandlerServer.class, PacketSpecialMovement.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketOpenTab.HandlerServer.class, PacketOpenTab.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketCloseDialog.HandlerServer.class, PacketCloseDialog.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketUpdateStructure.HandlerServer.class, PacketUpdateStructure.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketSaveStructure.HandlerServer.class, PacketSaveStructure.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketOrbisDeveloperReach.HandlerServer.class, PacketOrbisDeveloperReach.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketOrbisWorldObjectAdd.HandlerServer.class, PacketOrbisWorldObjectAdd.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketOrbisWorldObjectRemove.HandlerServer.class, PacketOrbisWorldObjectRemove.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketOrbisActiveSelection.HandlerServer.class, PacketOrbisActiveSelection.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketOrbisChangePower.HandlerServer.class, PacketOrbisChangePower.class, discriminant++, Side.SERVER);

		// C L I E N T
		instance.registerMessage(PacketEquipment.HandlerClient.class, PacketEquipment.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketMarkPlayerDeath.HandlerClient.class, PacketMarkPlayerDeath.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketMoaJump.HandlerClient.class, PacketMoaJump.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketOpenDialog.HandlerClient.class, PacketOpenDialog.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketUpdateStructure.HandlerClient.class, PacketUpdateStructure.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketLatchSwet.HandlerClient.class, PacketLatchSwet.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketDetachSwet.HandlerClient.class, PacketDetachSwet.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketOrbisDeveloperMode.HandlerClient.class, PacketOrbisDeveloperMode.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketOrbisWorldObjectManager.HandlerClient.class, PacketOrbisWorldObjectManager.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketOrbisDeveloperReach.HandlerClient.class, PacketOrbisDeveloperReach.class, discriminant++, Side.CLIENT);

		NetworkRegistry.INSTANCE.registerGuiHandler(AetherCore.INSTANCE, new AetherGuiHandler());
	}

	public static void sendPacketToDimension(final IMessage message, final int dimension)
	{
		NetworkingAether.instance.sendToDimension(message, dimension);
	}

	public static void sendPacketToAllPlayers(final IMessage message)
	{
		NetworkingAether.instance.sendToAll(message);
	}

	public static void sendPacketToPlayer(final IMessage message, final EntityPlayerMP player)
	{
		NetworkingAether.instance.sendTo(message, player);
	}

	public static void sendPacketToWatching(final IMessage message, final EntityLivingBase entity, final boolean includeSelf)
	{
		final WorldServer world = (WorldServer) entity.world;

		final EntityTracker tracker = world.getEntityTracker();

		for (final EntityPlayer player : tracker.getTrackingPlayers(entity))
		{
			NetworkingAether.sendPacketToPlayer(message, (EntityPlayerMP) player);
		}

		// Entities don't watch themselves, take special care here
		if (includeSelf && entity instanceof EntityPlayer)
		{
			NetworkingAether.sendPacketToPlayer(message, (EntityPlayerMP) entity);
		}
	}

	@SideOnly(Side.CLIENT)
	public static void sendPacketToServer(final IMessage message)
	{
		NetworkingAether.instance.sendToServer(message);
	}
}
