package com.gildedgames.aether.common.network.api;

import com.gildedgames.aether.api.net.IGildedGamesAccountApi;
import com.gildedgames.aether.api.net.data.UserFeatures;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GildedGamesAccountApiImpl implements IGildedGamesAccountApi
{
	private static final String API_BASE = "https://api.gildedgames.com";

	private final ListeningExecutorService service = MoreExecutors.listeningDecorator(new ThreadPoolExecutor(0, 2, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<>()));

	private final HttpClient client = HttpClientBuilder.create().build();

	private final Gson gson = new Gson();

	public Future<UserFeatures> retrieveUserFeatures(UUID uuid)
	{
		return this.service.submit(() -> {
			HttpGet request = new HttpGet(GildedGamesAccountApiImpl.API_BASE + "/minecraft/user/" + uuid.toString() + "/features");
			HttpResponse response = this.client.execute(request);

			try (InputStream stream = response.getEntity().getContent()) {
				try (InputStreamReader reader = new InputStreamReader(stream)) {
					UserFeatures features = this.gson.fromJson(reader, UserFeatures.class);

					return features;
				}
			}
		});
	}
}
