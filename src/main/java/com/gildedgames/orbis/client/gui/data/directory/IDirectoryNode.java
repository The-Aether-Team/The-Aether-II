package com.gildedgames.orbis.client.gui.data.directory;

import com.gildedgames.orbis.client.gui.data.IDropdownElement;
import com.gildedgames.orbis.client.gui.util.GuiTexture;

import java.io.File;
import java.util.Collection;

/**
 * A non-graphic interface for defining the characteristics of a
 * file type, such as directories, blueprints (in OrbisCore case), or whatever.
 */
public interface IDirectoryNode
{

	/**
	 * @return The file associated with this node.
	 */
	File getFile();

	/**
	 * The graphical icon for this file. Will be used be a
	 * wrapper GuiDirectoryNode class that acts as a sort of button in
	 * the directory viewer.
	 * @return
	 */
	GuiTexture getIcon();

	/**
	 * For interaction with server directories.
	 *
	 * Will determine whether or not the node is present
	 * on the client (downloaded). If not, it will display
	 * a cross icon next to the node. Alternatively, it'll show
	 * a tick if it is present.
	 * @return Whether or not the node is present on the client.
	 */
	boolean isOnClient();

	/**
	 * For interaction with server directories.
	 *
	 * Will determine whether or not the node is downloading currently.
	 * If not, it will display a cross icon next to the node.
	 * Alternatively, it'll show a down arrow if it is downloading.
	 * @return Whether or not the node is downloading currently.
	 */
	boolean isDownloading();

	/**
	 * Listener method for when a directory controller opens this
	 * Ui File.
	 * @param navigator The directory controller that is opening this file
	 */
	void onOpen(IDirectoryNavigator navigator);

	/**
	 * Listener method for when a directory controller deletes this
	 * Ui File.
	 * @param navigator The directory controller this is deleting this file
	 */
	void onDelete(IDirectoryNavigator navigator);

	/**
	 * @return The dropdown elements for this node
	 */
	Collection<IDropdownElement> getRightClickElements(IDirectoryNavigator navigator);

}
