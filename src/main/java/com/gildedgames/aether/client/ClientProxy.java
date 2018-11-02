package com.gildedgames.aether.client;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.client.gui.misc.CustomLoadingRenderer;
import com.gildedgames.aether.client.gui.tab.TabEquipment;
import com.gildedgames.aether.client.models.blocks.AetherModelWrapper;
import com.gildedgames.aether.client.models.blocks.ModelBakedAether;
import com.gildedgames.aether.client.renderer.AetherRenderers;
import com.gildedgames.aether.client.renderer.ClientRenderHandler;
import com.gildedgames.aether.client.texture.IMetadataSectionAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.CommonProxy;
import com.gildedgames.aether.common.analytics.GameAnalytics;
import com.gildedgames.aether.common.util.helpers.PerfHelper;
import com.google.common.collect.Sets;
import com.google.gson.JsonParseException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ClientProxy extends CommonProxy
{
	private static final Map<ResourceLocation, IMetadataSectionAether> metadataCache = new HashMap<>();

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

		Minecraft.getMinecraft().metadataSerializer_.registerMetadataSectionType(new IMetadataSectionAether.MetadataSerializer(), IMetadataSectionAether.class);
		MinecraftForge.EVENT_BUS.register(this);

		PerfHelper.measure("Pre-initialize special renders", AetherRenderers::preInit);
	}

	private static final List<ModelResourceLocation> checkedLocations = new ArrayList<>();

	@SubscribeEvent
	public void onModelBake(ModelBakeEvent event)
	{
		Map<ModelResourceLocation, IModel> stateModels = ReflectionHelper.getPrivateValue(ModelLoader.class, event.getModelLoader(), "stateModels");
		for (ModelResourceLocation location : event.getModelRegistry().getKeys())
		{
			if (checkedLocations.contains(location))
				continue;

			IModel model = stateModels.get(location);
			if (model != null && !(model instanceof AetherModelWrapper))
			{
				Set<ResourceLocation> textures = Sets.newHashSet(model.getTextures());

				for (ResourceLocation tex : textures)
				{
					IMetadataSectionAether meta = null;
					try
					{
						meta = getMetadata(toSpritePath(tex));
					} catch (IOException e)
					{
						e.printStackTrace();
					}
					if (meta != null)
					{
						try
						{
							event.getModelRegistry().putObject(location, wrapModel(model, event.getModelRegistry().getObject(location)));
						} catch (IOException e)
						{
							AetherCore.LOGGER.error("Could not wrap model " + location + ". Aborting...", e);
						}
						break;
					}
				}
			}
		}
	}

	private IBakedModel wrapModel(IModel model, IBakedModel object) throws IOException
	{
		AetherModelWrapper newModel = new AetherModelWrapper(model);
		return new ModelBakedAether(newModel, object);
	}

	public static ResourceLocation toSpritePath(ResourceLocation sprite)
	{
		if (!sprite.getResourcePath().startsWith("textures/"))
		{
			sprite = new ResourceLocation(sprite.getResourceDomain(), "textures/" + sprite.getResourcePath());
		}
		if (!sprite.getResourcePath().endsWith(".png"))
		{
			sprite = new ResourceLocation(sprite.getResourceDomain(), sprite.getResourcePath() + ".png");
		}
		return sprite;
	}

	public static IResource getResource(ResourceLocation res) throws IOException
	{
		return Minecraft.getMinecraft().getResourceManager().getResource(res);
	}

	public static IMetadataSectionAether getMetadata(TextureAtlasSprite sprite) throws IOException
	{
		return getMetadata(toSpritePath(toResourceLocation(sprite)));
	}

	public static ResourceLocation toResourceLocation(TextureAtlasSprite sprite)
	{
		return new ResourceLocation(sprite.getIconName());
	}

	public static IMetadataSectionAether getMetadata(ResourceLocation res) throws IOException
	{
		if (metadataCache.containsKey(res))
		{
			return metadataCache.get(res);
		}
		IMetadataSectionAether ret;
		try (IResource resource = getResource(res))
		{
			ret = resource.getMetadata(IMetadataSectionAether.NAME);
		} catch (FileNotFoundException e)
		{
			ret = null;
		} catch (JsonParseException e)
		{
			throw new IOException("Error loading aether metadata for texture " + res, e);
		}
		metadataCache.put(res, ret);
		return ret;
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
			} else
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

		AetherAPI.content().dialog().attachReloadListener();
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
