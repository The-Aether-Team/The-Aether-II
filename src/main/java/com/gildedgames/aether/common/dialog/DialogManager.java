package com.gildedgames.aether.common.dialog;

import com.gildedgames.aether.api.dialog.*;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.dialog.data.*;
import com.gildedgames.aether.common.dialog.data.actions.*;
import com.gildedgames.aether.common.dialog.data.conditions.DialogConditionHasSleptInBed;
import com.gildedgames.aether.common.dialog.data.conditions.DialogConditionReturningToOutpost;
import com.gildedgames.aether.common.dialog.data.slide_renderers.DialogSlideRendererNOOP;
import com.gildedgames.aether.common.dialog.data.slide_renderers.DialogSlideRendererStatic;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Optional;

/**
 * Default implementation of {@link IDialogManager} that performs basic caching of scenes
 * to speed up access times.
 */
public class DialogManager implements IDialogManager
{
	private final HashMap<ResourceLocation, IDialogScene> cachedScenes = new HashMap<>();

	private final HashMap<ResourceLocation, IDialogSpeaker> cachedSpeakers = new HashMap<>();

	private final HashMap<String, IDialogSlideRenderer> registeredRenders = new HashMap<>();

	private final Gson gson;

	private final boolean allowCaching;

	public DialogManager()
	{
		this(true);
	}

	/**
	 * Creates an instance of this dialog manager with caching enabled or disabled
	 * @param allowCaching Controls whether or not caching is enabled. Useful for debugging.
	 */
	public DialogManager(final boolean allowCaching)
	{
		this.allowCaching = allowCaching;

		this.gson = this.buildDeserializer().create();
		this.registerRenders();
	}

	/**
	 * Creates the JSON deserializer that converts a scene file into a {@link IDialogScene}.
	 *
	 * If you need to register additional action types, implement your own {@link IDialogManager}
	 * and override this method.
	 *
	 * @return A {@link GsonBuilder} that will construct an appropriate {@link Gson} object.
	 */
	protected GsonBuilder buildDeserializer()
	{
		final GsonBuilder builder = new GsonBuilder();

		builder.registerTypeAdapter(IDialogAction.class, new DialogActionDeserializer());
		builder.registerTypeAdapter(IDialogCondition.class, new DialogConditionDeserializer());

		builder.registerTypeAdapter(DialogActionExit.class, new DialogActionExit.Deserializer());
		builder.registerTypeAdapter(DialogActionNavigateToStart.class, new DialogActionNavigateToStart.Deserializer());
		builder.registerTypeAdapter(DialogActionNavigateBack.class, new DialogActionNavigateBack.Deserializer());
		builder.registerTypeAdapter(DialogActionNavigate.class, new DialogActionNavigate.Deserializer());
		builder.registerTypeAdapter(DialogActionNavigateScene.class, new DialogActionNavigateScene.Deserializer());
		builder.registerTypeAdapter(DialogActionTravelToBed.class, new DialogActionTravelToBed.Deserializer());
		builder.registerTypeAdapter(DialogActionTravelToLastOutpost.class, new DialogActionTravelToLastOutpost.Deserializer());
		builder.registerTypeAdapter(DialogActionOpenShop.class, new DialogActionOpenShop.Deserializer());

		builder.registerTypeAdapter(DialogConditionReturningToOutpost.class, new DialogConditionReturningToOutpost.Deserializer());
		builder.registerTypeAdapter(DialogConditionHasSleptInBed.class, new DialogConditionHasSleptInBed.Deserializer());

		builder.registerTypeAdapter(IDialogSlideRenderer.class, new DialogSlideRendererDeserializer());

		builder.registerTypeAdapter(DialogSlideRendererStatic.class, new DialogSlideRendererStatic.Deserializer());
		builder.registerTypeAdapter(DialogSlideRendererNOOP.class, new DialogSlideRendererNOOP.Deserializer());

		builder.registerTypeAdapter(IDialogSlide.class, new DialogSlideDeserializer());

		return builder;
	}

	protected void registerRenders()
	{
		this.registeredRenders.put("static", new DialogSlideRendererStatic());
		this.registeredRenders.put("noop", new DialogSlideRendererNOOP());
	}

	@Override
	public Optional<IDialogSpeaker> getSpeaker(final ResourceLocation resource)
	{
		if (this.allowCaching && this.cachedSpeakers.containsKey(resource))
		{
			return Optional.of(this.cachedSpeakers.get(resource));
		}

		final IDialogSpeaker speaker;

		try
		{
			speaker = this.loadSpeaker(resource);

			if (this.allowCaching)
			{
				this.cachedSpeakers.put(resource, speaker);
			}
		}
		catch (final IOException e)
		{
			AetherCore.LOGGER.error("Failed to load dialog speaker: {}", resource, e);

			return Optional.empty();
		}

		return Optional.of(speaker);
	}

	@Override
	public Optional<IDialogScene> getScene(final ResourceLocation resource)
	{
		if (this.allowCaching && this.cachedScenes.containsKey(resource))
		{
			return Optional.of(this.cachedScenes.get(resource));
		}

		final IDialogScene scene;

		try
		{
			scene = this.loadScene(resource);

			if (this.allowCaching)
			{
				this.cachedScenes.put(resource, scene);
			}
		}
		catch (final IOException e)
		{
			AetherCore.LOGGER.error("Failed to load dialog scene: {}", resource, e);

			return Optional.empty();
		}

		return Optional.of(scene);
	}

	@Override
	public Optional<IDialogSlide> findSlide(final String slideAddress, final IDialogSpeaker speaker)
	{
		if (speaker == null || slideAddress == null || !speaker.getSlides().isPresent() || !speaker.getSlides().get().containsKey(slideAddress))
		{
			return Optional.empty();
		}

		return Optional.of(speaker.getSlides().get().get(slideAddress));
	}

	@Override
	public Optional<IDialogSlideRenderer> findRenderer(final String type)
	{
		if (type == null || !this.registeredRenders.containsKey(type))
		{
			return Optional.empty();
		}

		return Optional.of(this.registeredRenders.get(type));
	}

	private IDialogScene loadScene(final ResourceLocation resource) throws IOException
	{
		final String path = "/assets/" + resource.getResourceDomain() + "/dialog/" + resource.getResourcePath() + ".json";

		AetherCore.LOGGER.info("Loading dialog scene from file {}", path);

		try (InputStream stream = MinecraftServer.class.getResourceAsStream(path))
		{
			try (InputStreamReader reader = new InputStreamReader(stream))
			{
				return this.gson.fromJson(reader, DialogSchema.class);
			}
		}
	}

	private IDialogSpeaker loadSpeaker(final ResourceLocation resource) throws IOException
	{
		final String path = resource.getResourcePath();
		String pathWithoutSlide = path;

		if (path.contains("#"))
		{
			pathWithoutSlide = path.replace(path.substring(path.indexOf("#"), path.length()), "");
		}

		final String speakerPath = "/assets/" + resource.getResourceDomain() + "/dialog/speakers/" + pathWithoutSlide + ".json";

		AetherCore.LOGGER.info("Loading dialog speaker from file {}", speakerPath);

		try
		{
			try (InputStream stream = MinecraftServer.class.getResourceAsStream(speakerPath))
			{
				try (InputStreamReader reader = new InputStreamReader(stream))
				{
					return this.gson.fromJson(reader, DialogSpeaker.class);
				}
			}
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void attachReloadListener()
	{
		final IResourceManager resManager = Minecraft.getMinecraft().getResourceManager();

		if (resManager instanceof IReloadableResourceManager)
		{
			((IReloadableResourceManager) resManager).registerReloadListener(new ReloadListener(this));
		}
	}

	@SideOnly(Side.CLIENT)
	public static class ReloadListener implements IResourceManagerReloadListener
	{
		private final DialogManager manager;

		public ReloadListener(final DialogManager manager)
		{
			this.manager = manager;
		}

		@Override
		public void onResourceManagerReload(final IResourceManager resourceManager)
		{
			this.manager.cachedScenes.clear();
		}
	}

}
