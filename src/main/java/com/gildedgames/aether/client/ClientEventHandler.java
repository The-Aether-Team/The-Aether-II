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
import com.gildedgames.aether.client.gui.GuiUtils;
import com.gildedgames.aether.client.gui.PerformanceIngame;
import com.gildedgames.aether.client.gui.misc.CustomLoadingRenderer;
import com.gildedgames.aether.client.gui.misc.GuiAetherLoading;
import com.gildedgames.aether.client.gui.misc.GuiBlackScreen;
import com.gildedgames.aether.client.sound.AetherMusicManager;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.containers.slots.SlotAmbrosium;
import com.gildedgames.aether.common.containers.slots.SlotEquipment;
import com.gildedgames.aether.common.containers.slots.SlotFlintAndSteel;
import com.gildedgames.aether.common.containers.slots.SlotMoaEgg;
import com.gildedgames.aether.common.entities.util.mounts.FlyingMount;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketSpecialMovement;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import com.gildedgames.orbis_api.client.gui.util.GuiFrameUtils;
import com.gildedgames.orbis_api.util.InputHelper;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientEventHandler
{
	private static final PerformanceIngame PERFORMANCE_LOGGER = new PerformanceIngame();

	private static boolean DRAW_BLACK_SCREEN = false;

	private static boolean DRAW_LOADING_SCREEN = false;

	private static boolean DRAWING_BLACK_FADE = false;

	private static double TIME_STARTED_FADE = -1;

	private static boolean PREV_JUMP_BIND_STATE;

	private static CustomLoadingRenderer.ICustomLoading BLACK_LOADING = () -> drawGradientRect(0, 0, (int) InputHelper.getScreenWidth(),
			(int) InputHelper.getScreenHeight(), 0xFF000000, 0xFF000000);

	private static Minecraft mc = Minecraft.getMinecraft();

	private static GuiAetherLoading LOADING = new GuiAetherLoading();

	public static void drawBlackFade()
	{
		DRAWING_BLACK_FADE = true;
	}

	public static boolean isLoadingScreen()
	{
		return DRAW_LOADING_SCREEN;
	}

	public static void setDrawBlackScreen(boolean flag)
	{
		DRAW_BLACK_SCREEN = flag;

		if (flag)
		{
			CustomLoadingRenderer.CURRENT = BLACK_LOADING;
		}
		else if (CustomLoadingRenderer.CURRENT == BLACK_LOADING)
		{
			CustomLoadingRenderer.CURRENT = null;
		}
	}

	public static void setDrawLoading(boolean flag)
	{
		DRAW_LOADING_SCREEN = flag;

		if (flag)
		{
			LOADING = new GuiAetherLoading();

			LOADING.setWorldAndResolution(Minecraft.getMinecraft(), MathHelper.floor(InputHelper.getScreenWidth()),
					MathHelper.floor(InputHelper.getScreenHeight()));
			LOADING.initGui();

			CustomLoadingRenderer.CURRENT = LOADING;
		}
		else if (CustomLoadingRenderer.CURRENT == LOADING)
		{
			CustomLoadingRenderer.CURRENT = null;
		}
	}

	protected static void drawGradientRect(final int left, final int top, final int right, final int bottom, final int startColor, final int endColor)
	{
		final float f = (float) (startColor >> 24 & 255) / 255.0F;
		final float f1 = (float) (startColor >> 16 & 255) / 255.0F;
		final float f2 = (float) (startColor >> 8 & 255) / 255.0F;
		final float f3 = (float) (startColor & 255) / 255.0F;
		final float f4 = (float) (endColor >> 24 & 255) / 255.0F;
		final float f5 = (float) (endColor >> 16 & 255) / 255.0F;
		final float f6 = (float) (endColor >> 8 & 255) / 255.0F;
		final float f7 = (float) (endColor & 255) / 255.0F;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager
				.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
						GlStateManager.DestFactor.ZERO);
		GlStateManager.shadeModel(7425);
		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos((double) right, (double) top, 0).color(f1, f2, f3, f).endVertex();
		bufferbuilder.pos((double) left, (double) top, 0).color(f1, f2, f3, f).endVertex();
		bufferbuilder.pos((double) left, (double) bottom, 0).color(f5, f6, f7, f4).endVertex();
		bufferbuilder.pos((double) right, (double) bottom, 0).color(f5, f6, f7, f4).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent event)
	{
		if (mc.world != null)
		{
			boolean necro = mc.world.provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER;

			if (mc.world.provider.getDimensionType() == DimensionsAether.AETHER || necro)
			{
				if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE)
				{
					event.setCanceled(true);
				}

				if (necro && (event.getType() == RenderGameOverlayEvent.ElementType.AIR || event.getType() == RenderGameOverlayEvent.ElementType.HEALTH
						|| event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT || event.getType() == RenderGameOverlayEvent.ElementType.ARMOR
						|| event.getType() == RenderGameOverlayEvent.ElementType.FOOD || event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR))
				{
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent.Pre event)
	{
		if (mc.world != null)
		{
			if (mc.world.provider.getDimensionType() == DimensionsAether.AETHER)
			{
				if (event.getType() == RenderGameOverlayEvent.ElementType.AIR || event.getType() == RenderGameOverlayEvent.ElementType.HEALTH
						|| event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT || event.getType() == RenderGameOverlayEvent.ElementType.ARMOR
						|| event.getType() == RenderGameOverlayEvent.ElementType.FOOD)
				{
					GlStateManager.translate(0.0, 6D, 0.0);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event)
	{
		if (mc.world != null)
		{
			if (mc.world.provider.getDimensionType() == DimensionsAether.AETHER)
			{
				if (event.getType() == RenderGameOverlayEvent.ElementType.AIR || event.getType() == RenderGameOverlayEvent.ElementType.HEALTH
						|| event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT || event.getType() == RenderGameOverlayEvent.ElementType.ARMOR
						|| event.getType() == RenderGameOverlayEvent.ElementType.FOOD)
				{
					GlStateManager.translate(0.0, -6D, 0.0);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onOpenGui(final GuiOpenEvent event)
	{
		if (mc.world != null && event.getGui() instanceof GuiInventory)
		{
			boolean necro = mc.world.provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER;

			if (necro && !mc.player.isCreative())
			{
				event.setCanceled(true);
			}
		}

		if (DRAW_BLACK_SCREEN && event.getGui() instanceof GuiDownloadTerrain)
		{
			event.setGui(new GuiBlackScreen());
		}
	}

	private static double getSecondsSinceStart()
	{
		return (System.currentTimeMillis() - TIME_STARTED_FADE) / 1000.0D;
	}

	@SubscribeEvent
	public static void onRenderTick(final TickEvent.RenderTickEvent event)
	{
		if (event.phase == TickEvent.Phase.END)
		{
			if (Minecraft.getMinecraft().loadingScreen.getClass() == LoadingScreenRenderer.class)
			{
				Minecraft.getMinecraft().loadingScreen = new CustomLoadingRenderer(Minecraft.getMinecraft(), Minecraft.getMinecraft().loadingScreen);
			}

			if (DRAW_LOADING_SCREEN)
			{
				if (Minecraft.getMinecraft().world != null)
				{
					Minecraft.getMinecraft().getSoundHandler().stopSounds();
				}

				CustomLoadingRenderer.CURRENT = LOADING;

				GlStateManager.pushMatrix();

				GlStateManager.disableDepth();

				LOADING.drawScreen((int) InputHelper.getMouseX(), (int) InputHelper.getMouseY(), event.renderTickTime);

				GlStateManager.enableDepth();

				GlStateManager.popMatrix();
			}

			if (DRAW_BLACK_SCREEN)
			{
				CustomLoadingRenderer.CURRENT = BLACK_LOADING;

				GlStateManager.pushMatrix();

				GlStateManager.disableDepth();

				GuiUtils.drawGradientRect(0, 0, MathHelper.floor(InputHelper.getScreenWidth()),
						MathHelper.floor(InputHelper.getScreenHeight()), 0xFF000000, 0xFF000000);

				GlStateManager.enableDepth();

				GlStateManager.popMatrix();
			}

			if (DRAWING_BLACK_FADE)
			{
				if (TIME_STARTED_FADE == -1)
				{
					TIME_STARTED_FADE = System.currentTimeMillis();
				}

				final float bgAlpha = Math.max(0.0F, 1.0F - (float) (getSecondsSinceStart() / 10.0D));

				final int bg = GuiFrameUtils.changeAlpha(0xFF000000, (int) (bgAlpha * 255));

				GlStateManager.pushMatrix();

				GlStateManager.disableDepth();

				GuiUtils.drawGradientRect(0, 0, MathHelper.floor(InputHelper.getScreenWidth()),
						MathHelper.floor(InputHelper.getScreenHeight()), bg, bg);

				GlStateManager.enableDepth();

				GlStateManager.popMatrix();

				if (getSecondsSinceStart() >= 10.0D)
				{
					DRAWING_BLACK_FADE = false;
					TIME_STARTED_FADE = -1;
				}
			}
		}
	}

	@SubscribeEvent
	public static void onTick(final ClientTickEvent event)
	{
		if (event.phase == TickEvent.Phase.END)
		{
			if (Minecraft.getMinecraft().loadingScreen.getClass() == LoadingScreenRenderer.class)
			{
				Minecraft.getMinecraft().loadingScreen = new CustomLoadingRenderer(Minecraft.getMinecraft(), Minecraft.getMinecraft().loadingScreen);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void onRenderGui(final RenderGameOverlayEvent event)
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

			PERFORMANCE_LOGGER.renderIcon();
		}
	}

	@SubscribeEvent
	@SuppressWarnings("unchecked")
	public static void onTooltipConstruction(final ItemTooltipEvent event)
	{
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
	public static void onClientTick(final ClientTickEvent event)
	{
		if (event.phase != TickEvent.Phase.END)
		{
			return;
		}

		final Minecraft mc = FMLClientHandler.instance().getClient();

		final World world = FMLClientHandler.instance().getWorldClient();

		final EntityPlayerSP player = FMLClientHandler.instance().getClientPlayerEntity();

		if (world != null && player != null)
		{
			final PlayerAether aePlayer = PlayerAether.getPlayer(player);

			if (aePlayer != null)
			{
				if (aePlayer.getAbilitiesModule().getMidAirJumpsAllowed() > 0)
				{
					if (mc.gameSettings.keyBindJump.isKeyDown() && !PREV_JUMP_BIND_STATE)
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

				PREV_JUMP_BIND_STATE = mc.gameSettings.keyBindJump.isKeyDown();

				AetherMusicManager.INSTANCE.update(aePlayer);
			}
		}
	}

	@SubscribeEvent
	public static void onTextureStitchPre(final TextureStitchEvent.Pre event)
	{
		SlotEquipment.registerIcons(event);
		SlotAmbrosium.registerIcons(event);
		SlotMoaEgg.registerIcons(event);
		SlotFlintAndSteel.registerIcons(event);
	}

	private static class EffectPoolTemporary<T extends IEffectProvider> implements IEffectPool<T>
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
