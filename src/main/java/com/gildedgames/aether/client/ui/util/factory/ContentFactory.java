package com.gildedgames.aether.client.ui.util.factory;

import com.gildedgames.aether.client.ui.common.Ui;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.google.common.collect.ImmutableMap;

import java.util.LinkedHashMap;

public interface ContentFactory<T extends Ui>
{

	LinkedHashMap<String, T> provideContent(ImmutableMap<String, Ui> currentContent, Rect contentArea);

}
