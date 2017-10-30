package com.gildedgames.aether.client;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.entity.IMount;
import com.gildedgames.aether.api.entity.IMountProcessor;
import com.gildedgames.aether.api.items.IItemProperties;
import com.gildedgames.aether.api.items.ItemRarity;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.items.equipment.effects.EffectInstance;
import com.gildedgames.aether.api.items.equipment.effects.IEffectFactory;
import com.gildedgames.aether.api.items.equipment.effects.IEffectPool;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.api.orbis.IWorldObjectManager;
import com.gildedgames.aether.client.gui.PerformanceIngame;
import com.gildedgames.aether.client.renderer.ChunkRendererManager;
import com.gildedgames.aether.client.sound.AetherMusicManager;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.aether.common.containers.slots.SlotAmbrosium;
import com.gildedgames.aether.common.containers.slots.SlotEquipment;
import com.gildedgames.aether.common.containers.slots.SlotFlintAndSteel;
import com.gildedgames.aether.common.containers.slots.SlotMoaEgg;
import com.gildedgames.aether.common.entities.util.mounts.FlyingMount;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketSpecialMovement;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import com.gildedgames.orbis.common.OrbisCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class ClientEventHandler
{
	public static final ChunkRendererManager CHUNK_RENDERER_MANAGER = new ChunkRendererManager();

	private final PerformanceIngame performanceLogger = new PerformanceIngame();

	private boolean prevJumpBindState;

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderGui(final RenderGameOverlayEvent event)
	{
		final Minecraft mc = Minecraft.getMinecraft();

		final ScaledResolution scaledRes = new ScaledResolution(mc);

		if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR)
		{
			if (mc.player.isRiding())
			{
				if (mc.player.getRidingEntity() instanceof IMount)
				{
					final IMount mount = (IMount) mc.player.getRidingEntity();
					final IMountProcessor processor = mount.getMountProcessor();

					if (processor instanceof FlyingMount)
					{
						final FlyingMount flyingMount = (FlyingMount) processor;

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
	public void onTooltipConstruction(final ItemTooltipEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntityPlayer());

		final IItemProperties properties = AetherAPI.content().items().getProperties(event.getItemStack().getItem());

		// Equipment Properties
		if (properties.getEquipmentSlot() != ItemEquipmentSlot.NONE)
		{
			final ItemEquipmentSlot slot = properties.getEquipmentSlot();

			// Equipment Effects
			for (final IEffectProvider provider : properties.getEffectProviders())
			{
				final IEffectFactory<IEffectProvider> factory = AetherAPI.content().effects().getFactory(provider.getFactory());

				final EffectPoolTemporary pool = new EffectPoolTemporary(event.getItemStack(), provider);
				factory.createInstance(pool).addInformation(event.getToolTip());
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
	public void onClientTick(final ClientTickEvent event)
	{
		if (event.phase != TickEvent.Phase.END)
		{
			return;
		}

		final Minecraft mc = FMLClientHandler.instance().getClient();

		final World world = FMLClientHandler.instance().getWorldClient();

		final EntityPlayerSP player = FMLClientHandler.instance().getClientPlayerEntity();

		if (world != null)
		{
			final IWorldObjectManager manager = WorldObjectManager.get(world);

			if (!manager.containsObserver(CHUNK_RENDERER_MANAGER))
			{
				manager.addObserver(CHUNK_RENDERER_MANAGER);
			}
		}
		else
		{
			OrbisCore.stopProjectManager();
			OrbisCore.stopWorldObjectManagerProvider(false);
			CHUNK_RENDERER_MANAGER.unload();
		}

		if (world != null && player != null)
		{
			final PlayerAether aePlayer = PlayerAether.getPlayer(player);

			if (aePlayer != null)
			{
				if (!aePlayer.containsObserver(CHUNK_RENDERER_MANAGER))
				{
					aePlayer.addObserver(CHUNK_RENDERER_MANAGER);
				}

				if (aePlayer.getAbilitiesModule().getMidAirJumpsAllowed() > 0)
				{
					if (mc.gameSettings.keyBindJump.isKeyDown() && !this.prevJumpBindState)
					{
						if (!player.isInWater() && aePlayer.getAbilitiesModule().getTicksAirborne() > 2
								&& !player.capabilities.isCreativeMode)
						{
							if (aePlayer.getAbilitiesModule().performMidAirJump())
							{
								NetworkingAether.sendPacketToServer(new PacketSpecialMovement(PacketSpecialMovement.Action.EXTRA_JUMP));

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
	public void onTextureStitchPre(final TextureStitchEvent.Pre event)
	{
		SlotEquipment.registerIcons(event);
		SlotAmbrosium.registerIcons(event);
		SlotMoaEgg.registerIcons(event);
		SlotFlintAndSteel.registerIcons(event);
	}

	@SubscribeEvent
	public void onRenderWorldLast(final RenderWorldLastEvent event)
	{
		final World world = Minecraft.getMinecraft().world;

		CHUNK_RENDERER_MANAGER.render(world, event.getPartialTicks());
	}

	private class EffectPoolTemporary<T extends IEffectProvider> implements IEffectPool<T>
	{
		private final ItemStack stack;

		private final T provider;

		public EffectPoolTemporary(final ItemStack stack, final T provider)
		{
			this.stack = stack;
			this.provider = provider;
		}

		@Override
		public ItemStack getProvider(final IEffectProvider instance)
		{
			return this.provider == instance ? this.stack : ItemStack.EMPTY;
		}

		@Override
		public Collection<T> getActiveProviders()
		{
			return Collections.singleton(this.provider);
		}

		@Override
		public Optional<EffectInstance> getInstance()
		{
			return Optional.empty();
		}

		@Override
		public boolean isEmpty()
		{
			return false;
		}
	}

}
