package com.gildedgames.aether.client.lang;

import com.gildedgames.aether.common.AetherCore;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FileResourcePack;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

public class AetherLanguageManager implements IResourceManagerReloadListener
{
	public static final AetherLanguageManager INSTANCE = new AetherLanguageManager();

	private static final String SERVICE_URL = "http://files.gildedgames.com/projects/aether/lang";

	private final Gson gson = new Gson();

	private LanguageInfo retrieveRemoteInfo() throws IOException
	{
		HttpURLConnection connection = this.openHTTPConnection(SERVICE_URL + "/info.json");

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8"))))
		{
			return this.gson.fromJson(reader, LanguageInfo.class);
		}
	}

	private LanguageInfo retrieveLocalInfo()
	{
		File localInfo = new File(this.getLanguageDir(), "info.json");

		if (localInfo.exists())
		{
			try (BufferedReader reader = new BufferedReader(new FileReader(localInfo)))
			{
				return this.gson.fromJson(reader, LanguageInfo.class);
			}
			catch (IOException e)
			{
				AetherCore.LOGGER.error("Error reading local language info", e);
			}
		}

		return new LanguageInfo();
	}

	public void installPendingUpdates()
	{
		long start = System.currentTimeMillis();

		LanguageInfo remote, local;

		try
		{
			remote = this.retrieveRemoteInfo();
		}
		catch (IOException e)
		{
			AetherCore.LOGGER.error("Unable to download remote language info", e);

			return;
		}

		local = this.retrieveLocalInfo();

		if (remote.getVersion() > local.getVersion())
		{
			try
			{
				this.installUpdate(remote);
			}
			catch (IOException e)
			{
				AetherCore.LOGGER.error("Failed to install language update", e);
			}

			AetherCore.LOGGER.info("Installed language update in " + (System.currentTimeMillis() - start) + "ms");
		}
		else
		{
			AetherCore.LOGGER.info("Checked for language updates in " + (System.currentTimeMillis() - start) + "ms");
		}

		this.refreshLocalizations();
	}

	public void installUpdate(LanguageInfo info) throws IOException
	{
		// Download ZIP
		FileUtils.copyURLToFile(new URL(SERVICE_URL + "/latest.zip"), new File(this.getLanguageDir(), "latest.zip"));

		// Write new metadata
		FileUtils.writeStringToFile(new File(this.getLanguageDir(), "info.json"), this.gson.toJson(info));

		this.reloadLanguagePack();
	}

	private void reloadLanguagePack()
	{
		List<IResourcePack> packs = this.getDefaultResourcePacks();

		IResourcePack langPack = null;

		for (IResourcePack pack : packs)
		{
			if (pack instanceof LanguageResourcePack)
			{
				langPack = pack;
			}
		}

		SimpleReloadableResourceManager manager = (SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();

		if (langPack != null)
		{
			 manager.reloadResourcePack(langPack);
		}
		else
		{
			packs.add(new FileResourcePack(new File(this.getLanguageDir(), "latest.zip")));

			this.setDefaultResourcePacks(packs);
		}
	}

	private void refreshLocalizations()
	{
		ResourcePackRepository repo = Minecraft.getMinecraft().getResourcePackRepository();

		List<IResourcePack> allPacks = Lists.newArrayList(this.getDefaultResourcePacks());

		for (ResourcePackRepository.Entry entry : repo.getRepositoryEntries())
		{
			allPacks.add(entry.getResourcePack());
		}

		if (repo.getResourcePackInstance() != null)
		{
			allPacks.add(repo.getResourcePackInstance());
		}

		Minecraft.getMinecraft().getLanguageManager().parseLanguageMetadata(allPacks);
	}

	private List<IResourcePack> getDefaultResourcePacks()
	{
		return ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "defaultResourcePacks");
	}

	private void setDefaultResourcePacks(List<IResourcePack> packs)
	{
		ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getMinecraft(), packs, "defaultResourcePacks");
	}

	private File getLanguageDir()
	{
		return new File(AetherCore.PROXY.getAetherStorageDir(), "lang_packs/");
	}

	private HttpURLConnection openHTTPConnection(String url) throws IOException
	{
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setConnectTimeout(7000);
		connection.setReadTimeout(3000);

		return connection;
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager)
	{
		this.installPendingUpdates();
	}
}
