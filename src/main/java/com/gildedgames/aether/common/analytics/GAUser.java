package com.gildedgames.aether.common.analytics;

import com.gildedgames.aether.common.analytics.events.GASessionEndEvent;
import com.gildedgames.aether.common.analytics.events.GASessionStartEvent;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class GAUser
{
	@Expose
	@SerializedName("persistent_id")
	private final UUID persistentId;

	@Expose
	@SerializedName("session_count")
	private int sessionCount;

	private final UUID sessionId;

	private long sessionStart;

	/**
	 * Creates a pre-existing GameAnalytics user and starts a new session. Used
	 * for de-serialization by GSON.
	 *
	 * @param persistentId The user's persistent ID
	 * @param sessionCount The user's session count
	 */
	protected GAUser(UUID persistentId, int sessionCount)
	{
		this.persistentId = persistentId;
		this.sessionCount = sessionCount;

		this.sessionId = UUID.randomUUID();
	}

	/**
	 * Creates a new GameAnalytics user and starts a new session.
	 */
	protected GAUser()
	{
		this.persistentId = UUID.randomUUID();
		this.sessionId = UUID.randomUUID();
	}

	/**
	 * Starts the user's session and records an event for it.
	 */
	public void startSession(GAReporter reporter)
	{
		this.sessionStart = reporter.getEpochTimestamp();
		this.sessionCount++;

		reporter.schedule(new GASessionStartEvent());
	}

	/**
	 * Ends the user's session and records an event for it.
	 */
	public void endSession(GAReporter reporter)
	{
		long duration = reporter.getEpochTimestamp() - this.sessionStart;

		reporter.schedule(new GASessionEndEvent(duration));
	}

	/**
	 * Returns the number of sessions this users has had in total, including the current one.
	 *
	 * @return The number of sessions, always positive
	 */
	public int getSessionCount()
	{
		return this.sessionCount;
	}

	/**
	 * Returns the user's persistent identifier, which doesn't change across sessions.
	 *
	 * @return The user's persistent ID
	 */
	public UUID getPersistentId()
	{
		return this.persistentId;
	}

	/**
	 * Returns the user's current session ID
	 *
	 * @return The user's session ID
	 */
	public UUID getSessionId()
	{
		return this.sessionId;
	}
}
