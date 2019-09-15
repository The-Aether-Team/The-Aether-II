package com.gildedgames.aether.common.travellers_guidebook;

import com.gildedgames.aether.api.player.conditions.*;
import com.gildedgames.aether.api.player.conditions.resolutions.ConditionResolutionRequireAll;
import com.gildedgames.aether.api.player.conditions.resolutions.ConditionResolutionRequireAny;
import com.gildedgames.aether.api.travellers_guidebook.ITGDefinition;
import com.gildedgames.aether.api.travellers_guidebook.ITGEntry;
import com.gildedgames.aether.api.travellers_guidebook.ITGManager;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.player_conditions.PlayerConditionDeserializer;
import com.gildedgames.aether.common.player_conditions.types.PlayerConditionFeedEntity;
import com.gildedgames.aether.common.player_conditions.types.PlayerConditionKillEntity;
import com.gildedgames.aether.common.player_conditions.types.PlayerConditionSeeEntity;
import com.gildedgames.aether.common.travellers_guidebook.entries.TGEntryBestiaryPage;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import java.util.*;

/**
 * Manages all data serialization and deserialization of traveller's guidebook
 * entries. This also acts as an entry point to retrieve entries with keys/tags.
 */
public class TGManager implements ITGManager
{
	private final Gson gson;

	private final Map<String, List<ITGEntry>> tagToEntries = Maps.newHashMap();

	private final Map<String, ITGEntry> idToEntries = Maps.newHashMap();

	private final List<ResourceLocation> definitionsToLoad = Lists.newArrayList();

	private final IPlayerConditionTracker playerConditionTracker;

	public TGManager(final IPlayerConditionTracker playerConditionTracker)
	{
		this.gson = this.buildDeserializer().create();
		this.playerConditionTracker = playerConditionTracker;
	}

	private GsonBuilder buildDeserializer()
	{
		final GsonBuilder builder = new GsonBuilder();

		builder.registerTypeAdapter(IPlayerCondition.class, new PlayerConditionDeserializer());
		builder.registerTypeAdapter(IConditionResolution.class, new ConditionResolutionDeserializer());
		builder.registerTypeAdapter(ITGEntry.class, new TGEntryDeserializer());

		builder.registerTypeAdapter(PlayerConditionSeeEntity.class, new PlayerConditionSeeEntity.Deserializer());
		builder.registerTypeAdapter(PlayerConditionFeedEntity.class, new PlayerConditionFeedEntity.Deserializer());
		builder.registerTypeAdapter(PlayerConditionKillEntity.class, new PlayerConditionKillEntity.Deserializer());

		builder.registerTypeAdapter(ConditionResolutionRequireAll.class, new ConditionResolutionRequireAll.Deserializer());
		builder.registerTypeAdapter(ConditionResolutionRequireAny.class, new ConditionResolutionRequireAny.Deserializer());

		builder.registerTypeAdapter(TGEntryBestiaryPage.class, new TGEntryBestiaryPage.Deserializer());

		return builder;
	}

	@Override
	public void registerEntry(final ResourceLocation entry)
	{
		this.definitionsToLoad.add(entry);
	}

	@Override
	public void load()
	{
		for (final ResourceLocation location : this.definitionsToLoad)
		{
			this.loadDefinitions(location);
		}
	}

	private void loadDefinitions(final ResourceLocation location)
	{
		try
		{
			final String path = location.getPath();

			final String definitionPath = "/assets/" + location.getNamespace() + "/travellers_guidebook/definitions/" + path + ".json";

			AetherCore.LOGGER.info("Loading definitions from file {}", definitionPath);

			try (final InputStream stream = MinecraftServer.class.getResourceAsStream(definitionPath))
			{
				try (final InputStreamReader reader = new InputStreamReader(stream))
				{
					final TGDefinition[] definitions = this.gson.fromJson(reader, TGDefinition[].class);

					for (final ITGDefinition def : definitions)
					{
						this.registerDefinition(def);
					}
				}
			}
		}
		catch (final IOException e)
		{
			AetherCore.LOGGER.error("Failed to load definitions: {}", location, e);
		}
	}

	private void registerDefinition(final ITGDefinition def)
	{
		final Collection<ResourceLocation> conditionIDs = PlayerConditionUtils.getIDs(def.conditions());
		this.playerConditionTracker.trackConditions(def.conditions());

		for (final Map.Entry<String, ITGEntry> e : def.entries().entrySet())
		{
			final ITGEntry entryDef = e.getValue();
			final String entryId = e.getKey();

			if (this.idToEntries.containsKey(entryId))
			{
				throw new RuntimeException("An entry with an existing id is trying to be registered: " + entryId);
			}

			entryDef.setConditionResolution(def.resolution());

			// Provide player conditions required by its sub data
			this.playerConditionTracker.trackConditions(entryDef.providePlayerConditions());
			entryDef.setConditionIDs(conditionIDs);

			this.idToEntries.put(entryId, entryDef);

			final String tag = entryDef.getTag();

			// If tag doesn't exist, don't add to tag list
			if (tag == null || tag.isEmpty())
			{
				continue;
			}

			if (!this.tagToEntries.containsKey(tag))
			{
				this.tagToEntries.put(tag, Lists.newArrayList());
			}

			final List<ITGEntry> tagList = this.tagToEntries.get(tag);

			tagList.add(entryDef);
		}
	}

	@Override
	public void unload()
	{
		this.tagToEntries.clear();
		this.idToEntries.clear();

		this.playerConditionTracker.unload();
	}

	@Override
	public List<ITGEntry> getEntriesWithTag(final String tag)
	{
		if (this.tagToEntries.containsKey(tag))
		{
			return this.tagToEntries.get(tag);
		}

		return Collections.emptyList();
	}

	@Override
	public <T extends ITGEntry> List<T> getEntriesWithTagAndClass(final String tag, final Class<T> clazzType)
	{
		if (this.tagToEntries.containsKey(tag))
		{
			final List<T> entries = Lists.newArrayList();

			for (final ITGEntry entry : this.tagToEntries.get(tag))
			{
				if (entry.getClass().isAssignableFrom(clazzType))
				{
					entries.add((T) entry);
				}
			}

			return entries;
		}

		return Collections.emptyList();
	}

	@Override
	public <T extends ITGEntry> Optional<T> getEntry(final String entryId, final Class<T> clazzType)
	{
		if (this.idToEntries.containsKey(entryId))
		{
			return Optional.of((T) this.idToEntries.get(entryId));
		}

		return Optional.empty();
	}

	@Override
	public void attachReloadListener()
	{
		final IResourceManager resManager = Minecraft.getMinecraft().getResourceManager();

		if (resManager instanceof IReloadableResourceManager)
		{
			((IReloadableResourceManager) resManager).registerReloadListener(new TGManager.ReloadListener(this));
		}
	}

	@SideOnly(Side.CLIENT)
	public static class ReloadListener implements IResourceManagerReloadListener
	{
		private final TGManager manager;

		public ReloadListener(final TGManager manager)
		{
			this.manager = manager;
		}

		@Override
		public void onResourceManagerReload(final IResourceManager resourceManager)
		{
			this.manager.unload();
			this.manager.load();
		}
	}
}
