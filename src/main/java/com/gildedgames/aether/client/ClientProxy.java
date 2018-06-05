package com.gildedgames.aether.client;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.client.gui.misc.CustomLoadingRenderer;
import com.gildedgames.aether.client.gui.tab.TabEquipment;
import com.gildedgames.aether.client.renderer.AetherRenderers;
import com.gildedgames.aether.client.renderer.ClientRenderHandler;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.CommonProxy;
import com.gildedgames.aether.common.analytics.GameAnalytics;
import com.gildedgames.aether.common.util.helpers.PerfHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Random;

public class ClientProxy extends CommonProxy
{

	@Override
	public void preInit(final FMLPreInitializationEvent event)
	{
		super.preInit(event);

		Minecraft.getMinecraft().loadingScreen = new CustomLoadingRenderer(Minecraft.getMinecraft(), Minecraft.getMinecraft().loadingScreen);

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

			if (Minecraft.getMinecraft().isSnooperEnabled() && AetherCore.CONFIG.isAnalyticsEnabled())
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

		ClientRenderHandler.init();

		AetherAPI.content().tabs().getInventoryGroup().registerClientTab(new TabEquipment.Client());
		//AetherAPI.content().tabs().getInventoryGroup().registerClientTab(new TabBugReport.Client());
		//AetherAPI.content().tabs().getInventoryGroup().registerClientTab(new TabPatronRewards.Client());
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
	public void spawnCampfireStartParticles(World world, double x, double y, double z)
	{
		Random r = world.rand;

		for (int i = 0; i < 50; i++)
		{
			double range = r.nextDouble() * 0.9;

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
	public void spawnCampfireParticles(World world, double x, double y, double z)
	{
		Random r = world.rand;

		for (int i = 0; i < 10; i++)
		{
			double range = r.nextDouble() * 0.75;

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
}
