package com.gildedgames.aether.common.network;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.packets.*;
import com.gildedgames.aether.common.network.packets.dialog.PacketCloseDialog;
import com.gildedgames.aether.common.network.packets.dialog.PacketOpenDialog;
import com.gildedgames.aether.common.network.util.IMessageMultipleParts;
import com.gildedgames.orbis.common.network.packets.*;
import com.google.common.collect.Maps;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NetworkingAether
{
	private static final HashMap<Integer, ArrayList<byte[]>> packetParts = Maps.newHashMap();

	private static SimpleNetworkWrapper instance;

	private static int discriminant;

	public static Map<Integer, ArrayList<byte[]>> getPacketParts()
	{
		return packetParts;
	}

	public static void preInit()
	{
		instance = NetworkRegistry.INSTANCE.newSimpleChannel(AetherCore.MOD_ID);

		// S E R V E R
		instance.registerMessage(PacketSpecialMovement.HandlerServer.class, PacketSpecialMovement.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketOpenTab.HandlerServer.class, PacketOpenTab.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketCloseDialog.HandlerServer.class, PacketCloseDialog.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketUpdateStructure.HandlerServer.class, PacketUpdateStructure.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketSaveStructure.HandlerServer.class, PacketSaveStructure.class, discriminant++, Side.SERVER);

		// O R B I S  S E R V E R
		instance.registerMessage(PacketOrbisDeveloperReach.HandlerServer.class, PacketOrbisDeveloperReach.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketOrbisWorldObjectAdd.HandlerServer.class, PacketOrbisWorldObjectAdd.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketOrbisWorldObjectRemove.HandlerServer.class, PacketOrbisWorldObjectRemove.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketOrbisActiveSelection.HandlerServer.class, PacketOrbisActiveSelection.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketOrbisChangePower.HandlerServer.class, PacketOrbisChangePower.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketOrbisOpenGui.HandlerServer.class, PacketOrbisOpenGui.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketSetItemStack.HandlerServer.class, PacketSetItemStack.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketOrbisChangeSelectionType.HandlerServer.class, PacketOrbisChangeSelectionType.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketOrbisFilterShape.HandlerServer.class, PacketOrbisFilterShape.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketOrbisRequestProject.HandlerServer.class, PacketOrbisRequestProject.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketOrbisRequestProjectListing.HandlerServer.class, PacketOrbisRequestProjectListing.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketSaveWorldObjectToProject.HandlerServer.class, PacketSaveWorldObjectToProject.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketRequestCreateProject.HandlerServer.class, PacketRequestCreateProject.class, discriminant++, Side.SERVER);
		instance.registerMessage(PacketDeleteFile.HandlerServer.class, PacketDeleteFile.class, discriminant++, Side.SERVER);

		// C L I E N T
		instance.registerMessage(PacketEquipment.HandlerClient.class, PacketEquipment.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketMarkPlayerDeath.HandlerClient.class, PacketMarkPlayerDeath.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketMoaJump.HandlerClient.class, PacketMoaJump.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketOpenDialog.HandlerClient.class, PacketOpenDialog.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketUpdateStructure.HandlerClient.class, PacketUpdateStructure.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketLatchSwet.HandlerClient.class, PacketLatchSwet.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketDetachSwet.HandlerClient.class, PacketDetachSwet.class, discriminant++, Side.CLIENT);

		// O R B I S  C L I E N T
		instance.registerMessage(PacketOrbisDeveloperMode.HandlerClient.class, PacketOrbisDeveloperMode.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketOrbisWorldObjectManager.HandlerClient.class, PacketOrbisWorldObjectManager.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketOrbisDeveloperReach.HandlerClient.class, PacketOrbisDeveloperReach.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketOrbisSendProject.HandlerClient.class, PacketOrbisSendProject.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketOrbisSendProjectListing.HandlerClient.class, PacketOrbisSendProjectListing.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketOrbisWorldObjectRemove.HandlerClient.class, PacketOrbisWorldObjectRemove.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketOrbisWorldObjectAdd.HandlerClient.class, PacketOrbisWorldObjectAdd.class, discriminant++, Side.CLIENT);
		instance.registerMessage(PacketDeleteFile.HandlerClient.class, PacketDeleteFile.class, discriminant++, Side.CLIENT);

		NetworkRegistry.INSTANCE.registerGuiHandler(AetherCore.INSTANCE, new AetherGuiHandler());
	}

	private static IMessage[] fetchParts(final IMessage message)
	{
		final IMessage[] parts;

		if (message instanceof IMessageMultipleParts)
		{
			final IMessageMultipleParts multipleParts = (IMessageMultipleParts) message;
			parts = multipleParts.getParts();
		}
		else
		{
			parts = new IMessage[1];

			parts[0] = message;
		}

		return parts;
	}

	public static void sendPacketToDimension(final IMessage message, final int dimension)
	{
		for (final IMessage part : fetchParts(message))
		{
			NetworkingAether.instance.sendToDimension(part, dimension);
		}
	}

	public static void sendPacketToAllPlayers(final IMessage message)
	{
		for (final IMessage part : fetchParts(message))
		{
			NetworkingAether.instance.sendToAll(part);
		}
	}

	public static void sendPacketToPlayer(final IMessage message, final EntityPlayerMP player)
	{
		for (final IMessage part : fetchParts(message))
		{
			NetworkingAether.instance.sendTo(part, player);
		}
	}

	public static void sendPacketToWatching(final IMessage message, final EntityLivingBase entity, final boolean includeSelf)
	{
		for (final IMessage part : fetchParts(message))
		{
			final WorldServer world = (WorldServer) entity.world;

			final EntityTracker tracker = world.getEntityTracker();

			for (final EntityPlayer player : tracker.getTrackingPlayers(entity))
			{
				NetworkingAether.sendPacketToPlayer(part, (EntityPlayerMP) player);
			}

			// Entities don't watch themselves, take special care here
			if (includeSelf && entity instanceof EntityPlayer)
			{
				NetworkingAether.sendPacketToPlayer(part, (EntityPlayerMP) entity);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static void sendPacketToServer(final IMessage message)
	{
		for (final IMessage part : fetchParts(message))
		{
			NetworkingAether.instance.sendToServer(part);
		}
	}
}
