package com.gildedgames.orbis.client.gui.data.directory;

import java.io.File;
import java.util.List;

/**
 * An interface that will dictate/control the directory viewing data.
 * This is all non-graphical, while a Gui will listen in to this navigator
 * and update the interface based on the data this navigator provides.
 */
public interface IDirectoryNavigator
{

	/**
	 * Adds a listener to the navigator. An example use is a
	 * Gui which will display the data this navigator provides.
	 * @param listener
	 */
	void addListener(IDirectoryNavigatorListener listener);

	/**
	 * Removes a listener from the navigator.
	 * @param listener
	 * @return Whether the listener was removed or not.
	 */
	boolean removeListener(IDirectoryNavigatorListener listener);

	void onOpenNode(IDirectoryNode node);

	/**
	 * Opens the provided file if it is a directory.
	 * @param file
	 */
	void openDirectory(File file);

	/**
	 * @return Whether or not the navigator has any history to
	 * go back to.
	 */
	boolean canGoBack();

	/**
	 * @return Whether or not the navigator has any history to
	 * go forward to.
	 */
	boolean canGoForward();

	/**
	 * If the implementation supports history, this method will
	 * navigate backwards if possible.
	 */
	void back();

	/**
	 * If the implementation supports history, this method will
	 * navigate forwards if possible.
	 */
	void forward();

	/**
	 * This will refresh the data within the currently navigated directory.
	 */
	void refresh();

	/**
	 * @return The current directory this navigator has navigated to.
	 */
	File currentDirectory();

	/**
	 * Provides a list of navigator nodes within the current directory.
	 */
	List<IDirectoryNode> getNodes();

}
