package com.gildedgames.aetherii.dialog;

import com.gildedgames.aetherii.AetherII;
import com.gildedgames.aetherii.api.dialog.IDialog;
import com.gildedgames.aetherii.api.dialog.IDialogAction;
import com.gildedgames.aetherii.api.dialog.IDialogCondition;
import com.gildedgames.aetherii.api.dialog.IDialogManager;
import com.gildedgames.aetherii.api.dialog.IDialogRenderer;
import com.gildedgames.aetherii.api.dialog.IDialogScene;
import com.gildedgames.aetherii.api.dialog.IDialogTalker;
import com.gildedgames.aetherii.dialog.data.DialogActionDeserializer;
import com.gildedgames.aetherii.dialog.data.DialogConditionDeserializer;
import com.gildedgames.aetherii.dialog.data.DialogDeserializer;
import com.gildedgames.aetherii.dialog.data.DialogRendererDeserializer;
import com.gildedgames.aetherii.dialog.data.actions.DialogActionExit;
import com.gildedgames.aetherii.dialog.data.render.DialogRendererNOOP;
import com.gildedgames.aetherii.dialog.data.render.DialogRendererStatic;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Optional;

public class DialogManager implements IDialogManager {
	private final HashMap<ResourceLocation, IDialogScene> cachedScenes = new HashMap<>();
	private final HashMap<ResourceLocation, IDialogTalker> cachedTalkers = new HashMap<>();
	private final HashMap<String, IDialogRenderer> registeredRenders = new HashMap<>();


	private final Gson gson;
	private final boolean allowCaching;


	public DialogManager(final boolean allowCaching) {
		this.allowCaching = allowCaching;

		this.gson = this.buildDeserializer().create();
		this.registerRenders();
	}

	private GsonBuilder buildDeserializer() {
		final GsonBuilder builder = new GsonBuilder();

		builder.registerTypeAdapter(IDialogAction.class, new DialogActionDeserializer());
		builder.registerTypeAdapter(IDialogCondition.class, new DialogConditionDeserializer());

		builder.registerTypeAdapter(DialogActionExit.class, new DialogActionExit.Deserializer());

		builder.registerTypeAdapter(IDialogRenderer.class, new DialogRendererDeserializer());
		builder.registerTypeAdapter(DialogRendererStatic.class, new DialogRendererStatic.Deserializer());
		builder.registerTypeAdapter(DialogRendererNOOP.class, new DialogRendererNOOP.Deserializer());
		builder.registerTypeAdapter(IDialog.class, new DialogDeserializer());
		return builder;
	}

	protected void registerRenders() {
		this.registeredRenders.put("static", new DialogRendererStatic());
		this.registeredRenders.put("noop", new DialogRendererNOOP());
	}

	@Override
	public Optional<IDialogTalker> getTalker(final ResourceLocation resource) {
		if (this.allowCaching && this.cachedTalkers.containsKey(resource)) {
			return Optional.of(this.cachedTalkers.get(resource));
		}

		final IDialogTalker talker;

		try {
			talker = this.loadTalker(resource);

			if (this.allowCaching) {
				this.cachedTalkers.put(resource, talker);
			}
		} catch (final IOException e) {
			AetherII.LOGGER.error("Failed to load dialog talker: {}", resource, e);

			return Optional.empty();
		}

		return Optional.of(talker);
	}

	@Override
	public Optional<IDialogScene> getScene(final ResourceLocation resource) {
		if (this.allowCaching && this.cachedScenes.containsKey(resource)) {
			return Optional.of(this.cachedScenes.get(resource));
		}

		final IDialogScene scene;

		try {
			scene = this.loadScene(resource);

			if (this.allowCaching) {
				this.cachedScenes.put(resource, scene);
			}
		} catch (final IOException e) {
			AetherII.LOGGER.error("Failed to load dialog scene: {}", resource, e);

			return Optional.empty();
		}

		return Optional.of(scene);
	}


	public IDialogTalker loadTalker(ResourceLocation resource) throws IOException {
		final String path = resource.getPath();
		String pathWithoutSlide = path;

		if (path.contains("_")) {
			pathWithoutSlide = path.replace(path.substring(path.indexOf("_")), "");
		}

		final String talkerPath = "dialog/talkers/" + pathWithoutSlide + ".json";

		AetherII.LOGGER.info("Loading dialog talker from file {}", resource.getNamespace() + ":" + talkerPath);

		Optional<Resource> rawResource = Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation(resource.getNamespace(), talkerPath));

		if (rawResource.isEmpty()) {
			throw new IllegalArgumentException("Couldn't get talker: " + resource.getNamespace() + ":" + talkerPath);
		}

		InputStream inputstream = rawResource.get().open();

		try (InputStreamReader reader = new InputStreamReader(inputstream, StandardCharsets.UTF_8)) {
			return this.gson.fromJson(reader, DialogTalker.class);
		}
	}

	private IDialogScene loadScene(final ResourceLocation resource) throws IOException {
		final String path = "dialog/" + resource.getPath() + ".json";

		AetherII.LOGGER.info("Loading dialog scene from file {}", resource.getNamespace() + ":" + path);

		Optional<Resource> rawResource = Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation(resource.getNamespace(), path));

		if (rawResource.isEmpty()) {
			throw new IllegalArgumentException("Couldn't get Scene: " + resource.getNamespace() + ":" + path);
		}

		InputStream inputstream = rawResource.get().open();

		try (InputStreamReader reader = new InputStreamReader(inputstream, StandardCharsets.UTF_8)) {
			return this.gson.fromJson(reader, DialogSchema.class);
		}
	}

	@Override
	public Optional<IDialog> findDialog(final String slideAddress, final IDialogTalker speaker) {
		if (speaker == null || slideAddress == null || !speaker.getDialogs().isPresent() || !speaker.getDialogs().get().containsKey(slideAddress)) {
			return Optional.empty();
		}

		return Optional.of(speaker.getDialogs().get().get(slideAddress));
	}

	@Override
	public Optional<IDialogRenderer> findRenderer(final String type) {
		if (type == null || !this.registeredRenders.containsKey(type)) {
			return Optional.empty();
		}

		return Optional.of(this.registeredRenders.get(type));
	}

	@OnlyIn(Dist.CLIENT)
	public void attachReloadListener() {
		final ResourceManager resManager = Minecraft.getInstance().getResourceManager();

		if (resManager instanceof ReloadableResourceManager) {
			((ReloadableResourceManager) resManager).registerReloadListener(new ReloadListener(this));
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class ReloadListener implements ResourceManagerReloadListener {
		private final DialogManager manager;

		public ReloadListener(final DialogManager manager) {
			this.manager = manager;
		}

		@Override
		public void onResourceManagerReload(final ResourceManager resourceManager) {
			this.manager.cachedScenes.clear();
		}
	}
}
