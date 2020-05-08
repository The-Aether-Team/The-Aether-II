package com.gildedgames.aether.client;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.client.events.listeners.gui.GuiOverlayListener;
import com.gildedgames.aether.client.gui.misc.CustomLoadingRenderer;
import com.gildedgames.aether.client.renderer.AetherRenderers;
import com.gildedgames.aether.client.renderer.particles.ParticleAetherStatusEffect;
import com.gildedgames.aether.client.renderer.particles.ParticleImpact;
import com.gildedgames.aether.client.renderer.particles.ParticlePierce;
import com.gildedgames.aether.client.renderer.particles.ParticleSlash;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.CommonProxy;
import com.gildedgames.aether.common.analytics.GameAnalytics;
import com.gildedgames.aether.common.containers.overlays.tabs.guidebook.*;
import com.gildedgames.aether.common.util.helpers.PerfHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Random;

public class ClientProxy extends CommonProxy
{
	@Override
	public void spawnSlashParticleFrom(
			final World world, final double x, final double y, final double z, final double offsetX, final double offsetY, final double offsetZ)
	{
		final ParticleSlash effect = new ParticleSlash(world, x, y, z,1.0F, 1.0F, 1.0F, 1.0F);

		Minecraft.getMinecraft().effectRenderer.addEffect(effect);
	}

	@Override
	public void spawnPierceParticleFrom(
			final World world, final double x, final double y, final double z, final double offsetX, final double offsetY, final double offsetZ)
	{
		final ParticlePierce effect = new ParticlePierce(world, x, y, z,1.0F, 1.0F, 1.0F, 1.0F);

		Minecraft.getMinecraft().effectRenderer.addEffect(effect);
	}

	@Override
	public void spawnImpactParticleFrom(
			final World world, final double x, final double y, final double z, final double offsetX, final double offsetY, final double offsetZ)
	{
		final ParticleImpact effect = new ParticleImpact(world, x, y, z,1.0F, 1.0F, 1.0F, 1.0F);

		Minecraft.getMinecraft().effectRenderer.addEffect(effect);
	}

	@Override
	public void spawnEffectParticles(final World world, final double x, final double y, final double z, final double motionX, final double motionY, final double motionZ,
			final float particleRed, final float particleGreen, final float particleBlue)
	{
		final ParticleAetherStatusEffect particle = new ParticleAetherStatusEffect(world, x, y, z, motionX, motionY, motionZ, particleRed, particleGreen, particleBlue);

		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}

	@Override
	public void turnOffScreen()
	{
		Minecraft.getMinecraft().displayGuiScreen(null);
	}

	@Override
	public void preInit(final FMLPreInitializationEvent event)
	{
		super.preInit(event);

		Minecraft.getMinecraft().loadingScreen = new CustomLoadingRenderer(Minecraft.getMinecraft(), Minecraft.getMinecraft().loadingScreen);

		MinecraftForge.EVENT_BUS.register(this);

		PerfHelper.measure("Pre-initialize special renders", AetherRenderers::preInit);
	}

	@Override
	public void init(final FMLInitializationEvent event)
	{
		super.init(event);

		PerfHelper.measure("Initialize analytics", () ->
		{
			AetherCore.ANALYTICS = AetherCore.isInsideDevEnvironment() ? new GameAnalytics() :
					new GameAnalytics("c8e4d94251ce253e138ae8a702e20301", "1ba3cb91e03cbb578b97c26f872e812dd05f5bbb");

			if (Minecraft.getMinecraft().isSnooperEnabled() && AetherCore.CONFIG.analyticsEnabled)
			{
				AetherCore.ANALYTICS.setup();
			}
			else
			{
				AetherCore.LOGGER.info("GameAnalytics disabled by user preference (by config or vanilla snooper settings)");

				AetherCore.ANALYTICS.disable();
			}
		});

		PerfHelper.measure("Initialize special renders", AetherRenderers::init);

		GuiOverlayListener.init();

		//AetherAPI.content().tabs().getInventoryGroup().registerClientTab(new TabBugReport.Client());
		//AetherAPI.content().tabs().getInventoryGroup().registerClientTab(new TabPatronRewards.Client());

		/* Traveler's Guidebook tabs */
		AetherAPI.content().tabs().getInventoryGroup().registerClientTab(new TabGuidebook.Client());
		AetherAPI.content().tabs().getInventoryGroup().registerClientTab(new TabEquipment.Client());
		AetherAPI.content().tabs().getInventoryGroup().registerClientTab(new TabStatus.Client());
		AetherAPI.content().tabs().getInventoryGroup().registerClientTab(new TabDiscovery.Client());
		AetherAPI.content().tabs().getInventoryGroup().registerClientTab(new TabLoreTome.Client());

		AetherAPI.content().dialog().attachReloadListener();
		AetherAPI.content().tgManager().attachReloadListener();
	}

	@Override
	public void displayDismountMessage(final EntityPlayer player)
	{
		if (player == Minecraft.getMinecraft().player)
		{
			Minecraft.getMinecraft().ingameGUI
					.setOverlayMessage(I18n.format("mount.onboard", Minecraft.getMinecraft().gameSettings.keyBindSneak.getDisplayName()), false);
		}
	}

	@Override
	public void modifyEntityQuicksoil(final EntityLivingBase entity)
	{
		if (entity == Minecraft.getMinecraft().player)
		{
			KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), false);
		}

		super.modifyEntityQuicksoil(entity);
	}

	@Override
	public void spawnCampfireStartParticles(final World world, final double x, final double y, final double z)
	{
		final Random r = world.rand;

		for (int i = 0; i < 50; i++)
		{
			final double range = r.nextDouble() * 0.9;

			if (r.nextInt(10) == 0)
			{
				world.spawnParticle(EnumParticleTypes.LAVA, x + (r.nextDouble() * (r.nextBoolean() ? range : -range)), y,
						z + (r.nextDouble() * (r.nextBoolean() ? range : -range)),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.2, 0.075 * r.nextDouble(),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.2);
			}

			if (r.nextInt(4) == 0)
			{
				world.spawnParticle(EnumParticleTypes.FLAME, x + (r.nextDouble() * (r.nextBoolean() ? range : -range)), y,
						z + (r.nextDouble() * (r.nextBoolean() ? range : -range)),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.1, 0.1 * r.nextDouble(),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.1);
			}

			if (r.nextInt(4) == 0)
			{
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + (r.nextDouble() * (r.nextBoolean() ? range : -range)), y,
						z + (r.nextDouble() * (r.nextBoolean() ? range : -range)),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.1, 0.1 * r.nextDouble(),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.1);
			}
		}
	}

	@Override
	public void spawnCampfireParticles(final World world, final double x, final double y, final double z)
	{
		final Random r = world.rand;

		for (int i = 0; i < 10; i++)
		{
			final double range = r.nextDouble() * 0.75;

			if (r.nextInt(800) == 0)
			{
				world.spawnParticle(EnumParticleTypes.LAVA, (r.nextDouble() * (r.nextBoolean() ? range : -range)), y,
						z + (r.nextDouble() * (r.nextBoolean() ? range : -range)),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.001, 0.075 * r.nextDouble(),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.001);
			}

			if (r.nextInt(4) == 0)
			{
				world.spawnParticle(EnumParticleTypes.FLAME, x + (r.nextDouble() * (r.nextBoolean() ? range : -range)), y,
						z + (r.nextDouble() * (r.nextBoolean() ? range : -range)),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.001, 0.04 * r.nextDouble(),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.001);
			}

			if (r.nextInt(4) == 0)
			{
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + (r.nextDouble() * (r.nextBoolean() ? range : -range)), y,
						z + (r.nextDouble() * (r.nextBoolean() ? range : -range)),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.001, 0.075 * r.nextDouble(),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.001);
			}
		}
	}

	@Override
	public IThreadListener getMinecraftThread()
	{
		return Minecraft.getMinecraft();
	}
}
