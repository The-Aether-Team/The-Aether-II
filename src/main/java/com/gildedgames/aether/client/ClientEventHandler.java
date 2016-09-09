package com.gildedgames.aether.client;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.api.capabilites.items.effects.IItemEffectsCapability;
import com.gildedgames.aether.api.capabilites.items.properties.IItemPropertiesCapability;
import com.gildedgames.aether.api.capabilites.items.properties.ItemRarity;
import com.gildedgames.aether.api.entity.IMount;
import com.gildedgames.aether.api.entity.IMountProcessor;
import com.gildedgames.aether.client.sound.AetherMusicManager;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLeaves;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.capabilities.player.modules.TeleportingModule;
import com.gildedgames.aether.common.containers.slots.SlotEquipment;
import com.gildedgames.aether.common.entities.util.mounts.FlyingMount;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.AetherMovementPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Timer;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ClientEventHandler
{

	private boolean prevJumpBindState;

	private Timer timer = new Timer(20.0F);

	private final Gui DUMMY_GUI = new Gui();

	private boolean prevFancyGraphics;

	@SideOnly(Side.CLIENT)
	public void renderAetherPortalHUD(float timeInPortal, ScaledResolution scaledRes)
	{
		if (timeInPortal < 1.0F)
		{
			timeInPortal = timeInPortal * timeInPortal;
			timeInPortal = timeInPortal * timeInPortal;
			timeInPortal = timeInPortal * 0.8F + 0.2F;
		}

		Minecraft mc = Minecraft.getMinecraft();

		GlStateManager.disableAlpha();
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.color(1.0F, 1.0F, 1.0F, timeInPortal);
		mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		TextureAtlasSprite textureatlassprite = mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(BlocksAether.aether_portal.getDefaultState());
		float f = textureatlassprite.getMinU();
		float f1 = textureatlassprite.getMinV();
		float f2 = textureatlassprite.getMaxU();
		float f3 = textureatlassprite.getMaxV();
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(0.0D, (double)scaledRes.getScaledHeight(), -90.0D).tex((double)f, (double)f3).endVertex();
		vertexbuffer.pos((double)scaledRes.getScaledWidth(), (double)scaledRes.getScaledHeight(), -90.0D).tex((double)f2, (double)f3).endVertex();
		vertexbuffer.pos((double)scaledRes.getScaledWidth(), 0.0D, -90.0D).tex((double)f2, (double)f1).endVertex();
		vertexbuffer.pos(0.0D, 0.0D, -90.0D).tex((double)f, (double)f1).endVertex();
		tessellator.draw();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderGui(RenderGameOverlayEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();

		ScaledResolution scaledRes = new ScaledResolution(mc);

		if (event.getType() == RenderGameOverlayEvent.ElementType.PORTAL)
		{
			if (!mc.thePlayer.isPotionActive(MobEffects.NAUSEA))
			{
				PlayerAetherImpl playerAether = PlayerAetherImpl.getPlayer(mc.thePlayer);
				TeleportingModule teleporter = playerAether.getTeleportingModule();

				float timeInPortal = teleporter.getPrevTimeInPortal() + (teleporter.getTimeInPortal() - teleporter.getPrevTimeInPortal()) * this.timer.renderPartialTicks;

				if (timeInPortal > 0.0F)
				{
					this.renderAetherPortalHUD(timeInPortal, scaledRes);
				}
			}
		}
		else if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR)
		{
			if (mc.thePlayer.isRiding())
			{
				if (mc.thePlayer.getRidingEntity() instanceof IMount)
				{
					IMount mount = (IMount)mc.thePlayer.getRidingEntity();
					IMountProcessor processor = mount.getMountProcessor();

					if (processor instanceof FlyingMount)
					{
						FlyingMount flyingMount = (FlyingMount)processor;

						DUMMY_GUI.drawCenteredString(mc.fontRendererObj, String.valueOf((int)(flyingMount.getData().getRemainingAirborneTime())), scaledRes.getScaledWidth() / 2, scaledRes.getScaledHeight() - 30, 0xFFFFFF);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onTooltipConstruction(ItemTooltipEvent event)
	{
		ItemStack stack = event.getItemStack();

		if (stack != null)
		{
			List<String> oldTooltip = new LinkedList<>(event.getToolTip());
			oldTooltip.remove(0);

			String name = event.getToolTip().get(0);

			event.getToolTip().clear();

			event.getToolTip().add(name);

			if (stack.hasCapability(AetherCapabilities.ITEM_PROPERTIES, null))
			{
				IItemPropertiesCapability props = stack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null);

				if (props != null && props.getRarity() != ItemRarity.NONE)
				{
					event.getToolTip().add(I18n.format(props.getRarity().getUnlocalizedName()));
				}
			}

			if (stack.hasCapability(AetherCapabilities.ITEM_EFFECTS, null))
			{
				IItemEffectsCapability effects = stack.getCapability(AetherCapabilities.ITEM_EFFECTS, null);

				if (effects.getEffectPairs() == null || effects.getEffectPairs().size() <= 0)
				{

				}
				else
				{
					for (Pair<EntityEffectProcessor, EntityEffectInstance> effect : effects.getEffectPairs())
					{
						EntityEffectProcessor processor = effect.getLeft();
						EntityEffectInstance instance = effect.getRight();

						List<String> localizedDesc = new ArrayList<>();

						for (String line : processor.getUnlocalizedDesc(event.getEntityPlayer(), instance))
						{
							localizedDesc.add(I18n.format(line, (Object[]) processor.getFormatParameters(event.getEntityPlayer(), instance)));
						}

						event.getToolTip().addAll(localizedDesc);

						for (EntityEffectRule rule : instance.getRules())
						{
							for (String line : rule.getUnlocalizedDesc())
							{
								event.getToolTip().add(TextFormatting.GRAY + "\u2022 " + I18n.format(line));
							}
						}
					}
				}
			}

			event.getToolTip().addAll(oldTooltip);

			if (stack.hasCapability(AetherCapabilities.ITEM_PROPERTIES, null))
			{
				IItemPropertiesCapability props = stack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null);

				if (props != null)
				{
					if (props.isEquippable())
					{
						event.getToolTip().add("");

						event.getToolTip().add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + I18n.format(props.getEquipmentType().getUnlocalizedName()));
					}
				}
			}
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

		boolean fancyGraphics = Minecraft.getMinecraft().gameSettings.fancyGraphics;

		if (world != null && player != null)
		{
			if (this.prevFancyGraphics != fancyGraphics)
			{
				BlockAetherLeaves.setGraphicsLevel(fancyGraphics);
			}

			PlayerAetherImpl aePlayer = PlayerAetherImpl.getPlayer(player);

			if (aePlayer != null)
			{
				if (aePlayer.getAbilitiesModule().getMidAirJumpsAllowed() > 0)
				{
					if (mc.gameSettings.keyBindJump.isKeyDown() && !this.prevJumpBindState)
					{
						if (!player.isInWater() && aePlayer.getTicksAirborne() > 2 && !player.capabilities.isCreativeMode)
						{
							if (aePlayer.performMidAirJump())
							{
								NetworkingAether.sendPacketToServer(new AetherMovementPacket(AetherMovementPacket.Action.EXTRA_JUMP));

								world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.PLAYERS, 0.4f, 0.8f + (world.rand.nextFloat() * 0.6f), false);
							}
						}
					}
				}

				this.prevJumpBindState = mc.gameSettings.keyBindJump.isKeyDown();

				AetherMusicManager.INSTANCE.update(aePlayer);
			}
		}

		this.prevFancyGraphics = fancyGraphics;
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event)
	{
		if (event.getWorld() instanceof WorldClient)
		{
			Minecraft mc = Minecraft.getMinecraft();

			if (!(mc.playerController instanceof PlayerControllerAetherMP))
			{
				Minecraft.getMinecraft().playerController = PlayerControllerAetherMP.create(mc.playerController);
			}

			ClientProxy.clientPlayerController = (PlayerControllerAetherMP) mc.playerController;
		}
	}

	@SubscribeEvent
	public void onTextureStitchPre(TextureStitchEvent.Pre event)
	{
		SlotEquipment.registerIcons(event);
	}
}
