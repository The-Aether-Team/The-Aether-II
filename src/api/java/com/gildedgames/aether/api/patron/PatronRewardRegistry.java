package com.gildedgames.aether.api.patron;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.Collection;

public class PatronRewardRegistry
{
	private final BiMap<Integer, IPatronReward> rewards = HashBiMap.create();

	public PatronRewardRegistry()
	{

	}

	public void register(int id, IPatronReward reward)
	{
		this.rewards.put(id, reward);
	}

	public <T extends IPatronReward> T get(int id)
	{
		return (T) this.rewards.get(id);
	}

	public int get(IPatronReward reward)
	{
		return this.rewards.inverse().get(reward);
	}

	public Collection<IPatronReward> getRewards()
	{
		return this.rewards.values();
	}
}
