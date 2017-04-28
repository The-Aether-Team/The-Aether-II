package com.gildedgames.aether.client;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.entity.IMount;
import com.gildedgames.aether.api.entity.IMountProcessor;
import com.gildedgames.aether.api.items.IItemProperties;
import com.gildedgames.aether.api.items.ItemRarity;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.items.equipment.effects.IEffectFactory;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.client.gui.PerformanceIngame;
import com.gildedgames.aether.client.sound.AetherMusicManager;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.containers.slots.SlotAmbrosium;
import com.gildedgames.aether.common.containers.slots.SlotEquipment;
import com.gildedgames.aether.common.containers.slots.SlotFlintAndSteel;
import com.gildedgames.aether.common.containers.slots.SlotMoaEgg;
import com.gildedgames.aether.common.entities.util.mounts.FlyingMount;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.AetherMovementPacket;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

import java.util.Collections;

public class ClientEventHandler
{
	private final PerformanceIngame performanceLogger = new PerformanceIngame();

	private boolean prevJumpBindState;

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderGui(RenderGameOverlayEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();

		ScaledResolution scaledRes = new ScaledResolution(mc);

		if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR)
		{
			if (mc.player.isRiding())
			{
				if (mc.player.getRidingEntity() instanceof IMount)
				{
					IMount mount = (IMount) mc.player.getRidingEntity();
					IMountProcessor processor = mount.getMountProcessor();

					if (processor instanceof FlyingMount)
					{
						FlyingMount flyingMount = (FlyingMount) processor;

						mc.ingameGUI.drawCenteredString(mc.fontRenderer, String.valueOf((int) (flyingMount.getData().getRemainingAirborneTime())),
								scaledRes.getScaledWidth() / 2, scaledRes.getScaledHeight() - 30, 0xFFFFFF);
					}
				}
			}

			this.performanceLogger.renderIcon();
		}
	}

	@SubscribeEvent
	@SuppressWarnings("unchecked")
	public void onTooltipConstruction(ItemTooltipEvent event)
	{
		IItemProperties properties = AetherAPI.content().items().getProperties(event.getItemStack().getItem());

		// Equipment Properties
		if (properties.getEquipmentSlot() != ItemEquipmentSlot.NONE)
		{
			ItemEquipmentSlot slot = properties.getEquipmentSlot();

			// Equipment Effects
			for (IEffectProvider instance : properties.getEffectProviders())
			{
				IEffectFactory<IEffectProvider> factory = AetherAPI.content().effects().getFactory(instance.getFactory());
				factory.createInstance(Collections.singleton(instance)).addInformation(event.getToolTip());
			}

			// Slot Type
			event.getToolTip().add("");
			event.getToolTip().add(I18n.format(slot.getUnlocalizedName()));
		}

		// Rarity
		if (properties.getRarity() != ItemRarity.NONE)
		{
			event.getToolTip().add(I18n.format(properties.getRarity().getUnlocalizedName()));
		}
	}

	@SubscribeEvent
	public void onClientTick(ClientTickEvent event)
	{
		if (event.phase != TickEvent.Phase.END)
		{
			return;
		}

		Minecraft mc = FMLClientHandler.instance().getClient();

		World world = FMLClientHandler.instance().getWorldClient();

		EntityPlayerSP player = FMLClientHandler.instance().getClientPlayerEntity();

		if (world != null && player != null)
		{
			PlayerAether aePlayer = PlayerAether.getPlayer(player);

			if (aePlayer != null)
			{
				if (aePlayer.getAbilitiesModule().getMidAirJumpsAllowed() > 0)
				{
					if (mc.gameSettings.keyBindJump.isKeyDown() && !this.prevJumpBindState)
					{
						if (!player.isInWater() && aePlayer.getAbilitiesModule().getTicksAirborne() > 2
								&& !player.capabilities.isCreativeMode)
						{
							if (aePlayer.getAbilitiesModule().performMidAirJump())
							{
								NetworkingAether.sendPacketToServer(new AetherMovementPacket(AetherMovementPacket.Action.EXTRA_JUMP));

								world.playSound(player.posX, player.posY, player.posZ, SoundsAether.generic_wing_flap, SoundCategory.PLAYERS, 0.4f,
										0.8f + (world.rand.nextFloat() * 0.6f), false);
							}
						}
					}
				}

				this.prevJumpBindState = mc.gameSettings.keyBindJump.isKeyDown();

				AetherMusicManager.INSTANCE.update(aePlayer);
			}
		}
	}

	@SubscribeEvent
	public void onTextureStitchPre(TextureStitchEvent.Pre event)
	{
		SlotEquipment.registerIcons(event);
		SlotAmbrosium.registerIcons(event);
		SlotMoaEgg.registerIcons(event);
		SlotFlintAndSteel.registerIcons(event);
	}
}
