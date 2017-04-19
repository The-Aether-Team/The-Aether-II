package com.gildedgames.aether.common.dialog;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.api.dialog.IDialogManager;
import com.gildedgames.aether.api.dialog.IDialogScene;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.dialog.data.DialogActionDeserializer;
import com.gildedgames.aether.common.dialog.data.DialogSchema;
import com.gildedgames.aether.common.dialog.data.actions.DialogActionExit;
import com.gildedgames.aether.common.dialog.data.actions.DialogActionNavigate;
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

	private final Gson gson;

	private boolean allowCaching;

	public DialogManager()
	{
		this(true);
	}

	/**
	 * Creates an instance of this dialog manager with caching enabled or disabled
	 * @param allowCaching Controls whether or not caching is enabled. Useful for debugging.
	 */
	public DialogManager(boolean allowCaching)
	{
		this.allowCaching = allowCaching;

		this.gson = this.buildDeserializer().create();
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
		builder.registerTypeAdapter(DialogActionExit.class, new DialogActionExit.Deserializer());
		builder.registerTypeAdapter(DialogActionNavigate.class, new DialogActionNavigate.Deserializer());

		return builder;
	}

	@Override
	public Optional<IDialogScene> getScene(ResourceLocation resource)
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
		catch (IOException e)
		{
			AetherCore.LOGGER.error("Failed to load dialog scene: {}", resource, e);

			return Optional.empty();
		}

		return Optional.of(scene);
	}

	private IDialogScene loadScene(ResourceLocation resource) throws IOException
	{
		String path = "/assets/" + resource.getResourceDomain() + "/dialog/" + resource.getResourcePath() + ".json";

		AetherCore.LOGGER.info("Loading dialog scene from file {}", path);

		try (InputStream stream = MinecraftServer.class.getResourceAsStream(path))
		{
			try (InputStreamReader reader = new InputStreamReader(stream))
			{
				return this.gson.fromJson(reader, DialogSchema.class);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void attachReloadListener()
	{
		IResourceManager resManager = Minecraft.getMinecraft().getResourceManager();

		if (resManager instanceof IReloadableResourceManager)
		{
			((IReloadableResourceManager) resManager).registerReloadListener(new ReloadListener(this));
		}
	}

	@SideOnly(Side.CLIENT)
	public static class ReloadListener implements IResourceManagerReloadListener
	{
		private final DialogManager manager;

		public ReloadListener(DialogManager manager)
		{
			this.manager = manager;
		}

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager)
		{
			this.manager.cachedScenes.clear();
		}
	}

}
