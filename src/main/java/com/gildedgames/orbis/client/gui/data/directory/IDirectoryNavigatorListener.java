package com.gildedgames.orbis.client.gui.data.directory;

import java.io.File;

public interface IDirectoryNavigatorListener
{

	void onNodeOpen(IDirectoryNavigator navigator, IDirectoryNode node);

	void onDirectoryOpen(IDirectoryNavigator navigator, File file);

	void onBack(IDirectoryNavigator navigator);

	void onForward(IDirectoryNavigator navigator);

	void onRefresh(IDirectoryNavigator navigator);

}
