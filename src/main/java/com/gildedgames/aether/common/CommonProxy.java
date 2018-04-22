package com.gildedgames.aether.common;

import com.gildedgames.aether.api.IAetherServices;
import com.gildedgames.aether.api.registry.IContentRegistry;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherHooks;
import com.gildedgames.aether.common.entities.util.MountEventHandler;
import com.gildedgames.aether.common.entities.util.QuicksoilProcessor;
import com.gildedgames.aether.common.items.tools.ItemToolHandler;
import com.gildedgames.aether.common.items.weapons.swords.ItemSkyrootSword;
import com.gildedgames.aether.common.registry.ContentRegistry;
import com.gildedgames.aether.common.world.SectorEventHandler;
import com.gildedgames.aether.common.world.aether.biomes.irradiated_forests.IrradiatedForestsData;
import com.gildedgames.aether.common.world.aether.biomes.magnetic_hills.MagneticHillPillar;
import com.gildedgames.aether.common.world.aether.biomes.magnetic_hills.MagneticHillsData;
import com.gildedgames.aether.common.world.aether.island.gen.IslandVariables;
import com.gildedgames.aether.common.world.necromancer_tower.NecromancerTowerInstance;
import com.gildedgames.orbis_api.OrbisAPI;
import com.gildedgames.orbis_api.util.io.IClassSerializer;
import com.gildedgames.orbis_api.util.io.Instantiator;
import com.gildedgames.orbis_api.util.io.SimpleSerializer;
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
	private final ContentRegistry contentRegistry = new ContentRegistry();

	private File configDir;

	public void preInit(final FMLPreInitializationEvent event)
	{
		this.configDir = new File(event.getModConfigurationDirectory(), "Aether/");

		if (!this.configDir.exists() && !this.configDir.mkdir())
		{
			throw new RuntimeException("Couldn't createTE configuration directory");
		}

		final IClassSerializer s = new SimpleSerializer("aether");

		s.register(0, NecromancerTowerInstance.class, new Instantiator<>(NecromancerTowerInstance.class));
		s.register(1, MagneticHillsData.class, new Instantiator<>(MagneticHillsData.class));
		s.register(2, MagneticHillPillar.class, new Instantiator<>(MagneticHillPillar.class));
		s.register(3, IrradiatedForestsData.class, new Instantiator<>(IrradiatedForestsData.class));
		s.register(4, IslandVariables.class, new Instantiator<>(IslandVariables.class));

		OrbisAPI.services().io().register(s);

		this.contentRegistry.preInit();
	}

	public void init(final FMLInitializationEvent event)
	{
		this.contentRegistry.init();

		MinecraftForge.EVENT_BUS.register(AetherCore.CONFIG);

		MinecraftForge.EVENT_BUS.register(CommonEvents.class);
		MinecraftForge.EVENT_BUS.register(PlayerAetherHooks.class);
		MinecraftForge.EVENT_BUS.register(MountEventHandler.class);
		MinecraftForge.EVENT_BUS.register(ItemToolHandler.class);
		MinecraftForge.EVENT_BUS.register(ItemSkyrootSword.class);
		MinecraftForge.EVENT_BUS.register(QuicksoilProcessor.class);
		MinecraftForge.EVENT_BUS.register(SectorEventHandler.class);
	}

	public void spawnJumpParticles(final World world, final double x, final double y, final double z, final double radius, final int quantity)
	{
		final Random random = world.rand;

		for (int i = 0; i < quantity; i++)
		{
			final double x2 = x + (random.nextDouble() * radius) - (radius * 0.5D);
			final double y2 = y + (random.nextDouble() * 0.4D);
			final double z2 = z + (random.nextDouble() * radius) - (radius * 0.5D);

			world.spawnParticle(EnumParticleTypes.CLOUD, x2, y2, z2, 0.0D, random.nextDouble() * 0.03D, 0.0D);
		}
	}

	public File getConfigDir()
	{
		return this.configDir;
	}

	public void displayDismountMessage(final EntityPlayer player)
	{

	}

	public void modifyEntityQuicksoil(final EntityLivingBase entity)
	{
		entity.motionX *= 1.7D;
		entity.motionZ *= 1.7D;

		final double maxMotion = 0.7D;

		entity.motionX = MathHelper.clamp(entity.motionX, -maxMotion, maxMotion);
		entity.motionZ = MathHelper.clamp(entity.motionZ, -maxMotion, maxMotion);
	}

	@Override
	public IContentRegistry content()
	{
		return this.contentRegistry;
	}

}
