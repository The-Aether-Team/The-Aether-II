package com.gildedgames.aether.common;

import com.gildedgames.aether.api.IAetherServices;
import com.gildedgames.aether.api.io.Instantiator;
import com.gildedgames.aether.api.orbis.region.Region;
import com.gildedgames.aether.api.registry.IContentRegistry;
import com.gildedgames.aether.api.util.IClassSerializer;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherHooks;
import com.gildedgames.aether.common.capabilities.world.WorldObjectGroup;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.aether.common.entities.util.MountEventHandler;
import com.gildedgames.aether.common.entities.util.QuicksoilProcessor;
import com.gildedgames.aether.common.items.tools.ItemToolHandler;
import com.gildedgames.aether.common.items.weapons.swords.ItemSkyrootSword;
import com.gildedgames.aether.common.registry.ContentRegistry;
import com.gildedgames.aether.common.world.SectorEventHandler;
import com.gildedgames.orbis.client.renderers.RenderShape;
import com.gildedgames.orbis.common.OrbisDeveloperModeEvents;
import com.gildedgames.orbis.common.block.BlockDataContainer;
import com.gildedgames.orbis.common.data.BlueprintData;
import com.gildedgames.orbis.common.data.shapes.EllipsoidShape;
import com.gildedgames.orbis.common.data.shapes.LineShape;
import com.gildedgames.orbis.common.data.shapes.SphereShape;
import com.gildedgames.orbis.common.player.godmode.selection_types.SelectionTypeCuboid;
import com.gildedgames.orbis.common.player.godmode.selection_types.SelectionTypeEllipsoid;
import com.gildedgames.orbis.common.player.godmode.selection_types.SelectionTypeLine;
import com.gildedgames.orbis.common.player.godmode.selection_types.SelectionTypeSphere;
import com.gildedgames.orbis.common.world_objects.Blueprint;
import com.gildedgames.orbis.common.world_objects.WorldRegion;
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
			throw new RuntimeException("Couldn't create configuration directory");
		}

		this.contentRegistry.preInit();

		final IClassSerializer s = AetherCore.io().getSerializer();

		s.register(0, Region.class, new Instantiator<>(Region.class));
		s.register(1, BlueprintData.class, new Instantiator<>(BlueprintData.class));
		s.register(2, RenderShape.class, new Instantiator<>(RenderShape.class));
		s.register(3, WorldRegion.class, new Instantiator<>(WorldRegion.class));
		s.register(4, WorldObjectGroup.class, new Instantiator<>(WorldObjectGroup.class));
		s.register(5, WorldObjectManager.class, new Instantiator<>(WorldObjectManager.class));
		s.register(6, Blueprint.class, new Instantiator<>(Blueprint.class));
		s.register(7, BlockDataContainer.class, new Instantiator<>(BlockDataContainer.class));
		s.register(8, SelectionTypeCuboid.class, new Instantiator<>(SelectionTypeCuboid.class));
		s.register(9, SelectionTypeSphere.class, new Instantiator<>(SelectionTypeSphere.class));
		s.register(10, SelectionTypeEllipsoid.class, new Instantiator<>(SelectionTypeEllipsoid.class));
		s.register(11, SelectionTypeLine.class, new Instantiator<>(SelectionTypeLine.class));
		s.register(12, SphereShape.class, new Instantiator<>(SphereShape.class));
		s.register(13, EllipsoidShape.class, new Instantiator<>(EllipsoidShape.class));
		s.register(14, LineShape.class, new Instantiator<>(LineShape.class));
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

		MinecraftForge.EVENT_BUS.register(OrbisDeveloperModeEvents.class);
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
