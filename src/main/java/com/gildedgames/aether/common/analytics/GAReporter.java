package com.gildedgames.aether.common.analytics;

import com.gildedgames.aether.common.analytics.events.GAEvent;

import javax.annotation.Nonnull;

/**
 * Thread-safe reporter for GameAnalytics events.
 */
public interface GAReporter
{
	/**
	 * Tries to enable the reporter. If the remote server has disabled reporting,
	 * this will immediately disable the reporter. May be performed async.
	 */
	void setup();

	/**
	 * Disables the reporter and destroys any queued or scheduled events. While
	 * disabled, the reporter will not schedule events. May be performed async.
	 */
	void disable();

	/**
	 * Flushes queued events to disk. Should be used before the game shuts down
	 * to ensure events will be recorded.
	 */
	void flush();

	/**
	 * Schedules an event for uploading later if this reporter is active.
	 *
	 * @param event The event to report
	 */
	void schedule(@Nonnull GAEvent event);

	/**
	 * Returns the {@link GAUser} this reporter belongs to.
	 *
	 * @return A unique {@link GAUser} instance
	 */
	@Nonnull
	GAUser getUser();

	/**
	 * Creates an timestamp based off the current system time, including the
	 * server time offset.
	 *
	 * @return An epoch timestamp
	 */
	long getEpochTimestamp();
}
