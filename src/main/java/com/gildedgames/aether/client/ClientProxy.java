package com.gildedgames.aether.client;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.client.events.listeners.gui.GuiOverlayListener;
import com.gildedgames.aether.client.renderer.AetherRenderers;
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
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Random;

public class ClientProxy extends CommonProxy
{
	@Override
	public void spawnSlashParticleFrom(World world, double x, double y, double z, double offsetX, double offsetY, double offsetZ)
	{
		ParticleSlash effect = new ParticleSlash(Minecraft.getInstance().getTextureManager(), world, x,
				y, z, offsetX, 0.0D, offsetZ);

		Minecraft.getInstance().effectRenderer.addEffect(effect);
	}

	@Override
	public void spawnPierceParticleFrom(World world, double x, double y, double z, double offsetX, double offsetY, double offsetZ)
	{
		ParticlePierce effect = new ParticlePierce(Minecraft.getInstance().getTextureManager(), world, x,
				y, z, offsetX, 0.0D, offsetZ);

		Minecraft.getInstance().effectRenderer.addEffect(effect);
	}

	@Override
	public void spawnImpactParticleFrom(World world, double x, double y, double z, double offsetX, double offsetY, double offsetZ)
	{
		ParticleImpact effect = new ParticleImpact(Minecraft.getInstance().getTextureManager(), world, x,
				y, z, offsetX, 0.0D, offsetZ);

		Minecraft.getInstance().effectRenderer.addEffect(effect);
	}

	@Override
	public void turnOffScreen()
	{
		Minecraft.getInstance().displayGuiScreen(null);
	}

	@Override
	public void preInit(final FMLPreInitializationEvent event)
	{
		super.preInit(event);

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

			if (Minecraft.getInstance().isSnooperEnabled() && AetherCore.CONFIG.isAnalyticsEnabled())
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
	}

	@Override
	public void displayDismountMessage(final PlayerEntity player)
	{
		if (player == Minecraft.getInstance().player)
		{
			Minecraft.getInstance().ingameGUI
					.setOverlayMessage(I18n.format("mount.onboard", Minecraft.getInstance().gameSettings.keyBindSneak.getDisplayName()), false);
		}
	}

	@Override
	public void modifyEntityQuicksoil(final LivingEntity entity)
	{
		if (entity == Minecraft.getInstance().player)
		{
			KeyBinding.setKeyBindState(Minecraft.getInstance().gameSettings.keyBindSneak.getKeyCode(), false);
		}

		super.modifyEntityQuicksoil(entity);
	}

	@Override
	public void spawnCampfireStartParticles(World world, double x, double y, double z)
	{
		Random r = world.rand;

		for (int i = 0; i < 50; i++)
		{
			double range = r.nextDouble() * 0.9;

			if (r.nextInt(10) == 0)
			{
				world.spawnParticle(ParticleTypes.LAVA, x + (r.nextDouble() * (r.nextBoolean() ? range : -range)), y,
						z + (r.nextDouble() * (r.nextBoolean() ? range : -range)),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.2, 0.075 * r.nextDouble(),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.2);
			}

			if (r.nextInt(4) == 0)
			{
				world.spawnParticle(ParticleTypes.FLAME, x + (r.nextDouble() * (r.nextBoolean() ? range : -range)), y,
						z + (r.nextDouble() * (r.nextBoolean() ? range : -range)),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.1, 0.1 * r.nextDouble(),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.1);
			}

			if (r.nextInt(4) == 0)
			{
				world.spawnParticle(ParticleTypes.SMOKE_NORMAL, x + (r.nextDouble() * (r.nextBoolean() ? range : -range)), y,
						z + (r.nextDouble() * (r.nextBoolean() ? range : -range)),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.1, 0.1 * r.nextDouble(),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.1);
			}
		}
	}

	@Override
	public void spawnCampfireParticles(World world, double x, double y, double z)
	{
		Random r = world.rand;

		for (int i = 0; i < 10; i++)
		{
			double range = r.nextDouble() * 0.75;

			if (r.nextInt(800) == 0)
			{
				world.spawnParticle(ParticleTypes.LAVA, (r.nextDouble() * (r.nextBoolean() ? range : -range)), y,
						z + (r.nextDouble() * (r.nextBoolean() ? range : -range)),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.001, 0.075 * r.nextDouble(),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.001);
			}

			if (r.nextInt(4) == 0)
			{
				world.spawnParticle(ParticleTypes.FLAME, x + (r.nextDouble() * (r.nextBoolean() ? range : -range)), y,
						z + (r.nextDouble() * (r.nextBoolean() ? range : -range)),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.001, 0.04 * r.nextDouble(),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.001);
			}

			if (r.nextInt(4) == 0)
			{
				world.spawnParticle(ParticleTypes.SMOKE_NORMAL, x + (r.nextDouble() * (r.nextBoolean() ? range : -range)), y,
						z + (r.nextDouble() * (r.nextBoolean() ? range : -range)),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.001, 0.075 * r.nextDouble(),
						(r.nextDouble() * (r.nextBoolean() ? range : -range)) * 0.001);
			}
		}
	}

	@Override
	public IThreadListener getMinecraftThread()
	{
		return Minecraft.getInstance();
	}
}
