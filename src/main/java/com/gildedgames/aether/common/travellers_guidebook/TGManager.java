package com.gildedgames.aether.common.travellers_guidebook;

import com.gildedgames.aether.api.travellers_guidebook.*;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTGModule;
import com.gildedgames.aether.common.travellers_guidebook.conditions.TGConditionSeeEntity;
import com.gildedgames.aether.common.travellers_guidebook.entries.TGEntryBestiaryPage;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.entity.player.EntityPlayer;
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
public class TGManager implements ITGManager, ITGConditionListener
{
	private final Gson gson;

	private final Map<String, List<ITGEntryDefinition>> tagToEntries = Maps.newHashMap();

	private final Map<String, ITGEntryDefinition> idToEntries = Maps.newHashMap();

	private final HashSet<ITGCondition> conditions = Sets.newHashSet();

	private final List<ResourceLocation> definitionsToLoad = Lists.newArrayList();

	public TGManager()
	{
		this.gson = this.buildDeserializer().create();
	}

	private GsonBuilder buildDeserializer()
	{
		final GsonBuilder builder = new GsonBuilder();

		builder.registerTypeAdapter(ITGCondition.class, new TGConditionDeserializer());
		builder.registerTypeAdapter(ITGEntryDefinition.class, new TGEntryDeserializer());

		builder.registerTypeAdapter(TGConditionSeeEntity.class, new TGConditionSeeEntity.Deserializer());
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
							/**
							 * Assemble all ids of conditions and set them to each
							 * entry definition so that the manager can look up if
							 * those conditions have been met.
							 */
							final List<String> conditionIDs = Lists.newArrayList();

							for (final ITGCondition condition : def.conditions())
							{
								if (!this.conditions.contains(condition))
								{
									condition.subscribe(this);
									this.conditions.add(condition);
								}

								conditionIDs.add(condition.getUniqueIdentifier());
							}

							for (final Map.Entry<String, ITGEntryDefinition> e : def.entries().entrySet())
							{
								final ITGEntryDefinition entryDef = e.getValue();
								final String entryId = e.getKey();

								if (this.idToEntries.containsKey(entryId))
								{
									throw new RuntimeException("An entry with an existing id is trying to be registered: " + entryId);
								}

								entryDef.setConditionIDs(conditionIDs);

								this.idToEntries.put(entryId, entryDef);
							}
						}
					}
				}
			}
			catch (final IOException e)
			{
				AetherCore.LOGGER.error("Failed to load definitions: {}", location, e);
			}
		}
	}

	@Override
	public List<ITGEntryDefinition> getEntriesWithTag(final String tag)
	{
		if (this.tagToEntries.containsKey(tag))
		{
			return this.tagToEntries.get(tag);
		}

		return Collections.emptyList();
	}

	@Override
	public <T extends ITGEntryDefinition> Optional<T> getEntry(final String entryId, final Class<T> clazzType)
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

	@Override
	public void onTriggered(final ITGCondition condition, final EntityPlayer player)
	{
		final PlayerAether playerAether = PlayerAether.getPlayer(player);
		final PlayerTGModule module = playerAether.getTGModule();

		module.flagCondition(condition.getUniqueIdentifier());
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
			this.manager.tagToEntries.clear();
			this.manager.conditions.clear();
			this.manager.idToEntries.clear();

			this.manager.load();
		}
	}
}
