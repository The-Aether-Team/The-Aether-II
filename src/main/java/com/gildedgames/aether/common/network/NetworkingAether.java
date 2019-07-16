package com.gildedgames.aether.common.network;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.packets.*;
import com.gildedgames.aether.common.network.packets.dialog.*;
import com.gildedgames.aether.common.network.packets.effects.PacketStatusEffect;
import com.gildedgames.aether.common.network.packets.trade.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.management.PlayerList;
import net.minecraft.world.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class NetworkingAether
{
	private static SimpleNetworkWrapper instance;

	private static int discriminant;

	public static void preInit()
	{
		instance = NetworkRegistry.INSTANCE.newSimpleChannel(AetherCore.MOD_ID);

		// S E R V E R
		instance.registerMessage(PacketSpecialMovement.HandlerServer.class, PacketSpecialMovement.class, discriminant++, Dist.SERVER);
		instance.registerMessage(PacketOpenTab.HandlerServer.class, PacketOpenTab.class, discriminant++, Dist.SERVER);
		instance.registerMessage(PacketCloseScene.HandlerServer.class, PacketCloseScene.class, discriminant++, Dist.SERVER);
		instance.registerMessage(PacketSetPlayedIntro.HandlerServer.class, PacketSetPlayedIntro.class, discriminant++, Dist.SERVER);
		instance.registerMessage(PacketActivateButton.HandlerServer.class, PacketActivateButton.class, discriminant++, Dist.SERVER);
		instance.registerMessage(PacketMasonryRecipeChanged.HandlerServer.class, PacketMasonryRecipeChanged.class, discriminant++, Dist.SERVER);
		instance.registerMessage(PacketMasonryInputCountChanged.HandlerServer.class, PacketMasonryInputCountChanged.class, discriminant++, Dist.SERVER);
		instance.registerMessage(PacketCancelIntro.HandlerServer.class, PacketCancelIntro.class, discriminant++, Dist.SERVER);
		instance.registerMessage(PacketSetShouldRespawnAtCampfire.HandlerServer.class, PacketSetShouldRespawnAtCampfire.class, discriminant++, Dist.SERVER);
		instance.registerMessage(PacketSetPlayerConfig.HandlerServer.class, PacketSetPlayerConfig.class, discriminant++, Dist.SERVER);
		instance.registerMessage(PacketAdvance.HandlerServer.class, PacketAdvance.class, discriminant++, Dist.SERVER);
		instance.registerMessage(PacketShopSell.HandlerServer.class, PacketShopSell.class, discriminant++, Dist.SERVER);
		instance.registerMessage(PacketShopBack.HandlerServer.class, PacketShopBack.class, discriminant++, Dist.SERVER);
		instance.registerMessage(PacketShopBuy.HandlerServer.class, PacketShopBuy.class, discriminant++, Dist.SERVER);
		instance.registerMessage(PacketChangeCoinAmount.HandlerServer.class, PacketChangeCoinAmount.class, discriminant++, Dist.SERVER);
		instance.registerMessage(PacketTradeState.HandlerServer.class, PacketTradeState.class, discriminant++, Dist.SERVER);
		instance.registerMessage(PacketTradeMessage.HandlerServer.class, PacketTradeMessage.class, discriminant++, Dist.SERVER);

		// C L I E N T
		instance.registerMessage(PacketEquipment.HandlerClient.class, PacketEquipment.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketMarkPlayerDeath.HandlerClient.class, PacketMarkPlayerDeath.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketMoaJump.HandlerClient.class, PacketMoaJump.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketOpenDialog.HandlerClient.class, PacketOpenDialog.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketLatchSwet.HandlerClient.class, PacketLatchSwet.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketDetachSwet.HandlerClient.class, PacketDetachSwet.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketSetPlayedIntro.HandlerClient.class, PacketSetPlayedIntro.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketCloseLoadingScreen.HandlerClient.class, PacketCloseLoadingScreen.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketCampfires.HandlerClient.class, PacketCampfires.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketAerbunnySetRiding.HandlerClient.class, PacketAerbunnySetRiding.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketRequestClientInfo.HandlerClient.class, PacketRequestClientInfo.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketProgressModule.HandlerClient.class, PacketProgressModule.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketTalkedTo.HandlerClient.class, PacketTalkedTo.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketConditionsMetData.HandlerClient.class, PacketConditionsMetData.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketActivateButton.HandlerClient.class, PacketActivateButton.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketNavigateNode.HandlerClient.class, PacketNavigateNode.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketNavigateBack.HandlerClient.class, PacketNavigateBack.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketAdvance.HandlerClient.class, PacketAdvance.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketCurrencyModule.HandlerClient.class, PacketCurrencyModule.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketUpdatePrecipitation.HandlerClient.class, PacketUpdatePrecipitation.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketProgressBooleanData.HandlerClient.class, PacketProgressBooleanData.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketParticles.HandlerClient.class, PacketParticles.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketStatusEffect.HandlerClient.class, PacketStatusEffect.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketChangeCoinAmount.HandlerClient.class, PacketChangeCoinAmount.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketCloseScreen.HandlerClient.class, PacketCloseScreen.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketSendInventorySize.HandlerClient.class, PacketSendInventorySize.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketTradeState.HandlerClient.class, PacketTradeState.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketTradeInventory.HandlerClient.class, PacketTradeInventory.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketTradeMessage.HandlerClient.class, PacketTradeMessage.class, discriminant++, Dist.CLIENT);
		instance.registerMessage(PacketTradeInitial.HandlerClient.class, PacketTradeInitial.class, discriminant++, Dist.CLIENT);

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

	public static void sendPacketToAllPlayersExcept(final IMessage message, final ServerPlayerEntity player)
	{
		final PlayerList playerList = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();

		for (final ServerPlayerEntity p : playerList.getPlayers())
		{
			if (p != player)
			{
				NetworkingAether.instance.sendTo(message, p);
			}
		}
	}

	public static void sendPacketToPlayer(final IMessage message, final ServerPlayerEntity player)
	{
		NetworkingAether.instance.sendTo(message, player);
	}

	public static void sendPacketToWatching(final IMessage message, final LivingEntity entity, final boolean includeSelf)
	{
		final ServerWorld world = (ServerWorld) entity.world;

		final EntityTracker tracker = world.getEntityTracker();

		for (final PlayerEntity player : tracker.getTrackingPlayers(entity))
		{
			NetworkingAether.sendPacketToPlayer(message, (ServerPlayerEntity) player);
		}

		// Entities don't watch themselves, take special care here
		if (includeSelf && entity instanceof PlayerEntity)
		{
			NetworkingAether.sendPacketToPlayer(message, (ServerPlayerEntity) entity);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void sendPacketToServer(final IMessage message)
	{
		NetworkingAether.instance.sendToServer(message);
	}
}
