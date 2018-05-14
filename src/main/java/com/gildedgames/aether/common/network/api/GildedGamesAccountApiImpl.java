package com.gildedgames.aether.common.network.api;

import com.gildedgames.aether.api.net.IGildedGamesAccountApi;
import com.gildedgames.aether.api.net.data.UserFeatures;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.*;

public class GildedGamesAccountApiImpl implements IGildedGamesAccountApi
{
	private static final String API_BASE = "https://api.gildedgames.com";

	private final ExecutorService service = new ThreadPoolExecutor(0, 2, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

	private final Gson gson = new Gson();

	@Override
	public Future<UserFeatures> retrieveUserFeatures(UUID uuid)
	{
		return this.service.submit(() -> {
			URL url = new URL(GildedGamesAccountApiImpl.API_BASE + "/minecraft/user/" + uuid.toString() + "/features");

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(15000);
			connection.setReadTimeout(15000);
			connection.connect();

			if (connection.getResponseCode() != 200)
			{
				throw new IOException("Expected 200 OK response");
			}

			UserFeatures features;

			try (InputStream stream = connection.getInputStream())
			{
				try (InputStreamReader reader = new InputStreamReader(stream))
				{
					features = this.gson.fromJson(reader, UserFeatures.class);
				}
			}

			return features;
		});
	}
}
