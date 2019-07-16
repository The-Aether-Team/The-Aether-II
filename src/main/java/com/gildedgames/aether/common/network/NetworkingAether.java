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
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkingAether
{
	private static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(AetherCore.getResource("network"),
			NetworkingAether::getNetworkProtocolVersion,
			NetworkingAether::isClientVersionValid,
			NetworkingAether::isServerVersionValid);

	private static int discriminant;

	public static void preInit()
	{
		// S E R V E R
		registerPacket(new PacketSpecialMovement.HandlerServer(), PacketSpecialMovement.class, Dist.DEDICATED_SERVER);
		registerPacket(new PacketOpenTab.HandlerServer(), PacketOpenTab.class, Dist.DEDICATED_SERVER);
		registerPacket(new PacketCloseScene.HandlerServer(), PacketCloseScene.class, Dist.DEDICATED_SERVER);
		registerPacket(new PacketSetPlayedIntro.HandlerServer(), PacketSetPlayedIntro.class, Dist.DEDICATED_SERVER);
		registerPacket(new PacketActivateButton.HandlerServer(), PacketActivateButton.class, Dist.DEDICATED_SERVER);
		registerPacket(new PacketMasonryRecipeChanged.HandlerServer(), PacketMasonryRecipeChanged.class, Dist.DEDICATED_SERVER);
		registerPacket(new PacketMasonryInputCountChanged.HandlerServer(), PacketMasonryInputCountChanged.class, Dist.DEDICATED_SERVER);
		registerPacket(new PacketCancelIntro.HandlerServer(), PacketCancelIntro.class, Dist.DEDICATED_SERVER);
		registerPacket(new PacketSetShouldRespawnAtCampfire.HandlerServer(), PacketSetShouldRespawnAtCampfire.class, Dist.DEDICATED_SERVER);
		registerPacket(new PacketSetPlayerConfig.HandlerServer(), PacketSetPlayerConfig.class, Dist.DEDICATED_SERVER);
		registerPacket(new PacketAdvance.HandlerServer(), PacketAdvance.class, Dist.DEDICATED_SERVER);
		registerPacket(new PacketShopSell.HandlerServer(), PacketShopSell.class, Dist.DEDICATED_SERVER);
		registerPacket(new PacketShopBack.HandlerServer(), PacketShopBack.class, Dist.DEDICATED_SERVER);
		registerPacket(new PacketShopBuy.HandlerServer(), PacketShopBuy.class, Dist.DEDICATED_SERVER);
		registerPacket(new PacketChangeCoinAmount.HandlerServer(), PacketChangeCoinAmount.class, Dist.DEDICATED_SERVER);
		registerPacket(new PacketTradeState.HandlerServer(), PacketTradeState.class, Dist.DEDICATED_SERVER);
		registerPacket(new PacketTradeMessage.HandlerServer(), PacketTradeMessage.class, Dist.DEDICATED_SERVER);

		// C L I E N T
		registerPacket(new PacketEquipment.HandlerClient(), PacketEquipment.class, Dist.CLIENT);
		registerPacket(new PacketMarkPlayerDeath.HandlerClient(), PacketMarkPlayerDeath.class, Dist.CLIENT);
		registerPacket(new PacketMoaJump.HandlerClient(), PacketMoaJump.class, Dist.CLIENT);
		registerPacket(new PacketOpenDialog.HandlerClient(), PacketOpenDialog.class, Dist.CLIENT);
		registerPacket(new PacketLatchSwet.HandlerClient(), PacketLatchSwet.class, Dist.CLIENT);
		registerPacket(new PacketDetachSwet.HandlerClient(), PacketDetachSwet.class, Dist.CLIENT);
		registerPacket(new PacketSetPlayedIntro.HandlerClient(), PacketSetPlayedIntro.class, Dist.CLIENT);
		registerPacket(new PacketCampfires.HandlerClient(), PacketCampfires.class, Dist.CLIENT);
		registerPacket(new PacketAerbunnySetRiding.HandlerClient(), PacketAerbunnySetRiding.class, Dist.CLIENT);
		registerPacket(new PacketRequestClientInfo.HandlerClient(), PacketRequestClientInfo.class, Dist.CLIENT);
		registerPacket(new PacketProgressModule.HandlerClient(), PacketProgressModule.class, Dist.CLIENT);
		registerPacket(new PacketTalkedTo.HandlerClient(), PacketTalkedTo.class, Dist.CLIENT);
		registerPacket(new PacketConditionsMetData.HandlerClient(), PacketConditionsMetData.class, Dist.CLIENT);
		registerPacket(new PacketActivateButton.HandlerClient(), PacketActivateButton.class, Dist.CLIENT);
		registerPacket(new PacketNavigateNode.HandlerClient(), PacketNavigateNode.class, Dist.CLIENT);
		registerPacket(new PacketNavigateBack.HandlerClient(), PacketNavigateBack.class, Dist.CLIENT);
		registerPacket(new PacketAdvance.HandlerClient(), PacketAdvance.class, Dist.CLIENT);
		registerPacket(new PacketCurrencyModule.HandlerClient(), PacketCurrencyModule.class, Dist.CLIENT);
		registerPacket(new PacketUpdatePrecipitation.HandlerClient(), PacketUpdatePrecipitation.class, Dist.CLIENT);
		registerPacket(new PacketProgressBooleanData.HandlerClient(), PacketProgressBooleanData.class, Dist.CLIENT);
		registerPacket(new PacketParticles.HandlerClient(), PacketParticles.class, Dist.CLIENT);
		registerPacket(new PacketStatusEffect.HandlerClient(), PacketStatusEffect.class, Dist.CLIENT);
		registerPacket(new PacketChangeCoinAmount.HandlerClient(), PacketChangeCoinAmount.class, Dist.CLIENT);
		registerPacket(new PacketCloseScreen.HandlerClient(), PacketCloseScreen.class, Dist.CLIENT);
		registerPacket(new PacketSendInventorySize.HandlerClient(), PacketSendInventorySize.class, Dist.CLIENT);
		registerPacket(new PacketTradeState.HandlerClient(), PacketTradeState.class, Dist.CLIENT);
		registerPacket(new PacketTradeInventory.HandlerClient(), PacketTradeInventory.class, Dist.CLIENT);
		registerPacket(new PacketTradeMessage.HandlerClient(), PacketTradeMessage.class, Dist.CLIENT);
		registerPacket(new PacketTradeInitial.HandlerClient(), PacketTradeInitial.class, Dist.CLIENT);

//		TODO: 1.14
//		NetworkRegistry.INSTANCE.registerGuiHandler(AetherCore.INSTANCE, new AetherGuiHandler());
	}
	
	private static <REQ extends IMessage> void registerPacket(MessageHandler<REQ> handler, Class<REQ> messageClass, Dist client)
	{
		NETWORK.registerMessage(discriminant++, messageClass, new MessageEncoder<>(), new MessageDecoder<>(messageClass), handler);
	}

	public static void sendPacketToDimension(final IMessage message, final DimensionType dimension)
	{
		NETWORK.send(PacketDistributor.DIMENSION.with(() -> dimension), message);
	}

	public static void sendPacketToAllPlayers(final IMessage message)
	{
		NETWORK.send(PacketDistributor.ALL.noArg(), message);
	}

	public static void sendPacketToPlayer(final IMessage message, final ServerPlayerEntity player)
	{
		NETWORK.send(PacketDistributor.PLAYER.with(() -> player), message);
	}

	public static void sendPacketToWatching(final IMessage message, final LivingEntity entity, final boolean includeSelf)
	{
		NETWORK.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), message);

		// Entities don't watch themselves, take special care here
		if (includeSelf && entity instanceof ServerPlayerEntity)
		{
			NetworkingAether.sendPacketToPlayer(message, (ServerPlayerEntity) entity);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void sendPacketToServer(final IMessage message)
	{
		NetworkingAether.NETWORK.sendToServer(message);
	}

	private static boolean isServerVersionValid(String version)
	{
		return version.equals(NetworkingAether.getNetworkProtocolVersion());
	}

	private static boolean isClientVersionValid(String version)
	{
		return version.equals(NetworkingAether.getNetworkProtocolVersion());
	}

	private static String getNetworkProtocolVersion()
	{
		return "1.0";
	}
}
