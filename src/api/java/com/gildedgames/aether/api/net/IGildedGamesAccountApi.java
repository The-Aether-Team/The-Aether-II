package com.gildedgames.aether.api.net;

import com.gildedgames.aether.api.net.data.UserFeatures;

import java.util.UUID;
import java.util.concurrent.Future;

public interface IGildedGamesAccountApi
{
	Future<UserFeatures> retrieveUserFeatures(UUID uuid);
}