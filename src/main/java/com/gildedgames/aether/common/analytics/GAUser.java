package com.gildedgames.aether.common.analytics;

import com.gildedgames.aether.common.analytics.events.GASessionEndEvent;
import com.gildedgames.aether.common.analytics.events.GASessionStartEvent;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public class GAUser
{
	private final GameAnalytics reporter;

	private final UUID persistentId, sessionId;

	private long sessionStart;

	private int sessionCount;

	protected GAUser(GameAnalytics reporter, NBTTagCompound tag)
	{
		this.reporter = reporter;

		this.persistentId = tag.getUniqueId("PersistentId");
		this.sessionId = UUID.randomUUID();

		this.sessionCount = tag.getInteger("SessionCount");
	}

	protected GAUser(GameAnalytics reporter, UUID id)
	{
		this.reporter = reporter;

		this.persistentId = id;
		this.sessionId = UUID.randomUUID();
	}

	/**
	 * Starts the user's session and records an event for it.
	 */
	public void startSession()
	{
		this.sessionStart = this.reporter.getSystemEpoch();
		this.sessionCount++;

		this.reporter.event(new GASessionStartEvent(this));
	}

	/**
	 * Ends the user's session and records an event for it.
	 */
	public void endSession()
	{
		long duration = this.reporter.getSystemEpoch() - this.sessionStart;

		this.reporter.event(new GASessionEndEvent(this, duration));
	}

	/**
	 * Returns the number of sessions this users has had in total, including the current one
	 * @return The number of sessions, always positive
	 */
	public int getSessionCount()
	{
		return this.sessionCount;
	}

	/**
	 * Returns the user's persistent identifier, which doesn't change across sessions
	 * @return The user's persistent ID
	 */
	public UUID getPersistentId()
	{
		return this.persistentId;
	}

	/**
	 * Returns the ID of the user's current session
	 * @return The user's session ID
	 */
	public UUID getSessionId()
	{
		return this.sessionId;
	}

	public NBTTagCompound write()
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setUniqueId("PersistentId", this.persistentId);
		tag.setInteger("SessionCount", this.sessionCount);

		return tag;
	}
}
