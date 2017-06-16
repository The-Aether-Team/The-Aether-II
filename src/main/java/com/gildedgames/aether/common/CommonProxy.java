package com.gildedgames.aether.common;

import com.gildedgames.aether.api.IAetherServices;
import com.gildedgames.aether.api.registry.IContentRegistry;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherHooks;
import com.gildedgames.aether.common.entities.util.MountEventHandler;
import com.gildedgames.aether.common.entities.util.QuicksoilProcessor;
import com.gildedgames.aether.common.items.tools.ItemToolHandler;
import com.gildedgames.aether.common.items.weapons.swords.ItemSkyrootSword;
import com.gildedgames.aether.common.registry.ContentRegistry;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandSectorAccess;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.Random;

public class CommonProxy implements IAetherServices
{
	private File configDir;

	private ContentRegistry contentRegistry = new ContentRegistry();

	public void preInit(FMLPreInitializationEvent event)
	{
		this.configDir = new File(event.getModConfigurationDirectory(), "Aether/");

		if (!this.configDir.exists() && !this.configDir.mkdir())
		{
			throw new RuntimeException("Couldn't create configuration directory");
		}

		this.contentRegistry.preInit();
	}

	public void init(FMLInitializationEvent event)
	{
		this.contentRegistry.init();

		MinecraftForge.EVENT_BUS.register(AetherCore.CONFIG);

		MinecraftForge.EVENT_BUS.register(CommonEvents.class);
		MinecraftForge.EVENT_BUS.register(PlayerAetherHooks.class);
		MinecraftForge.EVENT_BUS.register(MountEventHandler.class);
		MinecraftForge.EVENT_BUS.register(ItemToolHandler.class);
		MinecraftForge.EVENT_BUS.register(ItemSkyrootSword.class);
		MinecraftForge.EVENT_BUS.register(QuicksoilProcessor.class);

		MinecraftForge.EVENT_BUS.register(IslandSectorAccess.inst());
	}

	public void spawnJumpParticles(World world, double x, double y, double z, double radius, int quantity)
	{
		Random random = world.rand;

		for (int i = 0; i < quantity; i++)
		{
			double x2 = x + (random.nextDouble() * radius) - (radius * 0.5D);
			double y2 = y + (random.nextDouble() * 0.4D);
			double z2 = z + (random.nextDouble() * radius) - (radius * 0.5D);

			world.spawnParticle(EnumParticleTypes.CLOUD, x2, y2, z2, 0.0D, random.nextDouble() * 0.03D, 0.0D);
		}
	}

	public File getConfigDir()
	{
		return this.configDir;
	}

	public void displayDismountMessage(EntityPlayer player)
	{

	}

	public void modifyEntityQuicksoil(EntityLivingBase entity)
	{
		entity.motionX *= 1.7D;
		entity.motionZ *= 1.7D;

		double maxMotion = 0.7D;

		entity.motionX = MathHelper.clamp(entity.motionX, -maxMotion, maxMotion);
		entity.motionZ = MathHelper.clamp(entity.motionZ, -maxMotion, maxMotion);
	}

	@Override
	public IContentRegistry content()
	{
		return this.contentRegistry;
	}
}
