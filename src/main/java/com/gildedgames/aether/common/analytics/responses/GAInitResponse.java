package com.gildedgames.aether.common.analytics.responses;

import com.google.gson.annotations.SerializedName;

public class GAInitResponse
{
	@SerializedName("server_ts")
	public final long serverTimestamp;

	@SerializedName("enabled")
	public final boolean enabled;

	public GAInitResponse(long serverTimestamp, boolean enabled)
	{
		this.serverTimestamp = serverTimestamp;
		this.enabled = enabled;
	}
}
