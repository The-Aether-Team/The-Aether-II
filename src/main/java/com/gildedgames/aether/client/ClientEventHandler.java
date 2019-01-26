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
import com.gildedgames.aether.client.gui.DamageSystemOverlay;
import com.gildedgames.aether.client.gui.EffectSystemOverlay;
import com.gildedgames.aether.client.gui.GuiUtils;
import com.gildedgames.aether.client.gui.PerformanceIngame;
import com.gildedgames.aether.client.gui.misc.*;
import com.gildedgames.aether.client.gui.util.ToolTipCurrencyHelper;
import com.gildedgames.aether.client.sound.AetherMusicManager;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.containers.slots.SlotAmbrosium;
import com.gildedgames.aether.common.containers.slots.SlotEquipment;
import com.gildedgames.aether.common.containers.slots.SlotFlintAndSteel;
import com.gildedgames.aether.common.containers.slots.SlotMoaEgg;
import com.gildedgames.aether.common.entities.tiles.TileEntitySkyrootSign;
import com.gildedgames.aether.common.entities.util.mounts.FlyingMount;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketSpecialMovement;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import com.gildedgames.aether.common.shop.ShopCurrencyGilt;
import com.gildedgames.orbis_api.client.PartialTicks;
import com.gildedgames.orbis_api.client.gui.util.GuiFrameUtils;
import com.gildedgames.orbis_api.util.InputHelper;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.*;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientEventHandler
{
	private static final PerformanceIngame PERFORMANCE_LOGGER = new PerformanceIngame();

	private static final DamageSystemOverlay DAMAGE_SYSTEM_OVERLAY = new DamageSystemOverlay();

	private static final EffectSystemOverlay EFFECT_SYSTEM_OVERLAY = new EffectSystemOverlay();

	private static final ToolTipCurrencyHelper toolTipHelper = new ToolTipCurrencyHelper();

	private static final Minecraft mc = Minecraft.getMinecraft();

	private static boolean DRAW_BLACK_SCREEN = false;

	private static boolean DRAW_LOADING_SCREEN = false;

	private static boolean DRAWING_BLACK_FADE_OUT = false;

	private static boolean DRAWING_BLACK_FADE_IN = false;

	private static boolean CHANGE_FROM_BLACK_TO_LOAD = false;

	private static double TIME_STARTED_FADE = -1;

	private static boolean PREV_JUMP_BIND_STATE;

	private static double TIME_TO_FADE;

	private static Runnable AFTER_FADE;

	private static GuiAetherLoading LOADING = new GuiAetherLoading();

	private static final CustomLoadingRenderer.ICustomLoading BLACK_LOADING = ClientEventHandler::drawOverlay;

	private static int old_left_height, old_right_height;

	public static boolean isFadingIn()
	{
		return DRAWING_BLACK_FADE_IN;
	}

	public static DamageSystemOverlay getDamageSystemOverlay()
	{
		return DAMAGE_SYSTEM_OVERLAY;
	}

	public static EffectSystemOverlay getEffectSystemOverlay()
	{
		return EFFECT_SYSTEM_OVERLAY;
	}

	public static void drawBlackFade(double time)
	{
		TIME_STARTED_FADE = System.currentTimeMillis();

		DRAWING_BLACK_FADE_IN = false;
		DRAWING_BLACK_FADE_OUT = true;
		TIME_TO_FADE = time;
	}

	public static void drawBlackFadeIn(double time, Runnable after)
	{
		TIME_STARTED_FADE = System.currentTimeMillis();

		DRAWING_BLACK_FADE_OUT = false;
		DRAWING_BLACK_FADE_IN = true;
		TIME_TO_FADE = time;
		AFTER_FADE = after;
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

	public static void setChangeFromBlackToLoad(boolean flag)
	{
		CHANGE_FROM_BLACK_TO_LOAD = flag;
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
		GuiUtils.drawGradientRect(left, top, right, bottom, startColor, endColor);
	}

	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent event)
	{
		if (mc.world != null)
		{
			boolean atNecromancerInstance = mc.world.provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER;

			if (mc.world.provider.getDimensionType() == DimensionsAether.AETHER || atNecromancerInstance)
			{
				if (AetherCore.CONFIG.hideXPBarInAether() && event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE)
				{
					event.setCanceled(true);
				}

				if (atNecromancerInstance && (event.getType() == RenderGameOverlayEvent.ElementType.AIR
						|| event.getType() == RenderGameOverlayEvent.ElementType.HEALTH
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
			if (AetherCore.CONFIG.hideXPBarInAether() && mc.world.provider.getDimensionType() == DimensionsAether.AETHER)
			{
				old_left_height = GuiIngameForge.left_height;
				old_right_height = GuiIngameForge.right_height;

				GuiIngameForge.left_height = 33;
				GuiIngameForge.right_height = 33;
			}

			boolean atNecromancerInstance = mc.world.provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER;

			if (atNecromancerInstance && (event.getType() == RenderGameOverlayEvent.ElementType.AIR
					|| event.getType() == RenderGameOverlayEvent.ElementType.HEALTH
					|| event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT || event.getType() == RenderGameOverlayEvent.ElementType.ARMOR
					|| event.getType() == RenderGameOverlayEvent.ElementType.FOOD || event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR))
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event)
	{
		if (mc.world != null)
		{
			if (AetherCore.CONFIG.hideXPBarInAether() && mc.world.provider.getDimensionType() == DimensionsAether.AETHER)
			{
				GuiIngameForge.left_height = old_left_height;
				GuiIngameForge.right_height = old_right_height;
			}

			boolean atNecromancerInstance = mc.world.provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER;

			if (atNecromancerInstance && (event.getType() == RenderGameOverlayEvent.ElementType.AIR
					|| event.getType() == RenderGameOverlayEvent.ElementType.HEALTH
					|| event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT || event.getType() == RenderGameOverlayEvent.ElementType.ARMOR
					|| event.getType() == RenderGameOverlayEvent.ElementType.FOOD || event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR))
			{
				event.setCanceled(true);
			}
			else if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR)
			{
				DAMAGE_SYSTEM_OVERLAY.renderIcons(mc);
				EFFECT_SYSTEM_OVERLAY.render(mc);
			}
		}
	}

	@SubscribeEvent
	public static void onOpenGui(final GuiOpenEvent event)
	{
		if (event.getGui() instanceof GuiMainMenu && !AetherCore.IS_SIGNED && !AetherCore.isInsideDevEnvironment() && !AetherCore.CONFIG
				.hasAckFingerprintViolation())
		{
			event.setGui(new GuiAetherUnsigned(event.getGui()));

			return;
		}

		GuiScreen gui = event.getGui();

		if (gui instanceof GuiEditSign)
		{
			if (((GuiEditSign) gui).tileSign instanceof TileEntitySkyrootSign)
			{
				event.setGui(new GuiSkyrootSign(((GuiEditSign) gui).tileSign));
			}
		}

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

	public static void drawFade(boolean disableDepth)
	{
		if (DRAWING_BLACK_FADE_OUT || DRAWING_BLACK_FADE_IN)
		{
			final float bgAlpha = Math
					.max(0.0F, DRAWING_BLACK_FADE_OUT ?
							1.0F - (float) (getSecondsSinceStart() / TIME_TO_FADE) :
							(float) (getSecondsSinceStart() / TIME_TO_FADE));

			final int bg = GuiFrameUtils.changeAlpha(0xFF000000, (int) (bgAlpha * 255));

			GlStateManager.pushMatrix();

			if (disableDepth)
			{
				GlStateManager.disableDepth();
			}

			GuiUtils.drawGradientRect(0, 0, MathHelper.floor(InputHelper.getScreenWidth()),
					MathHelper.floor(InputHelper.getScreenHeight()), bg, bg);

			if (disableDepth)
			{
				GlStateManager.enableDepth();
			}

			GlStateManager.popMatrix();

			if (getSecondsSinceStart() >= TIME_TO_FADE)
			{
				DRAWING_BLACK_FADE_OUT = false;
				DRAWING_BLACK_FADE_IN = false;

				if (AFTER_FADE != null)
				{
					AFTER_FADE.run();

					AFTER_FADE = null;
				}
			}
		}
	}

	public static void drawOverlay()
	{
		if (CHANGE_FROM_BLACK_TO_LOAD && DRAW_BLACK_SCREEN)
		{
			setChangeFromBlackToLoad(false);
			setDrawBlackScreen(false);

			drawBlackFade(2.0D);
			setDrawLoading(true);
		}

		if (DRAW_LOADING_SCREEN)
		{
			if (Minecraft.getMinecraft().world != null)
			{
				Minecraft.getMinecraft().getSoundHandler().stopSounds();
			}

			setChangeFromBlackToLoad(false);

			CustomLoadingRenderer.CURRENT = LOADING;

			GlStateManager.pushMatrix();

			if (!DRAWING_BLACK_FADE_OUT)
			{
				//GlStateManager.disableDepth();
			}

			LOADING.drawScreen(InputHelper.getMouseX(), InputHelper.getMouseY(), PartialTicks.get());

			if (!DRAWING_BLACK_FADE_OUT)
			{
				//GlStateManager.enableDepth();
			}

			GlStateManager.popMatrix();
		}
		else
		{
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

			drawFade(true);
		}
	}

	@SubscribeEvent
	public static void onRenderTick(final TickEvent.RenderTickEvent event)
	{
		if (event.phase == TickEvent.Phase.END)
		{
			drawOverlay();
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
	public static void onRenderGuiPre(final RenderGameOverlayEvent.Pre event)
	{

	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void onRenderGui(final RenderGameOverlayEvent event)
	{
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

		//Currency
		final double value = AetherAPI.content().currency().getValue(event.getItemStack(), ShopCurrencyGilt.class);

		if (value != 0)
		{
			event.getToolTip().addAll(toolTipHelper.getText(value));
		}
	}

	@SubscribeEvent
	public static void onToolTipRender(final RenderTooltipEvent.PostText event)
	{
		final double value = AetherAPI.content().currency().getValue(event.getStack(), ShopCurrencyGilt.class);

		toolTipHelper.render(event.getFontRenderer(), event.getX(), event.getY(), event.getHeight(), value);
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
