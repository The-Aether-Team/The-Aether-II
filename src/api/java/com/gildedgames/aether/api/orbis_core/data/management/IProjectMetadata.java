package com.gildedgames.aether.api.orbis_core.data.management;

import java.time.LocalDateTime;

public interface IProjectMetadata extends IMetadata
{
	/**
	 * @return Whether or not the project has been downloaded to the client.
	 */
	boolean isDownloaded();

	/**
	 * @param downloaded Whether or not the project has been downloaded to the client.
	 */
	void setDownloaded(boolean downloaded);

	/**
	 * @return Whether or not the project is in the process of downloading to the client.
	 */
	boolean isDownloading();

	/**
	 * @param downloading Whether or not the project is in the process of downloading to the client.
	 */
	void setDownloading(boolean downloading);

	/**
	 * Very important that this is managed properly so that clients
	 * that connect to a server know when their local projects are
	 * no longer up to date.
	 *
	 * Whenever a change is made to the project, the date and time
	 * should be set.
	 *
	 * @return The date and time this project was last changed.
	 */
	LocalDateTime getLastChanged();

	/**
	 * Sets the internal "last changed" date.
	 *
	 * @param lastChanged The last time this project was changed.
	 */
	void setLastChanged(LocalDateTime lastChanged);
}
