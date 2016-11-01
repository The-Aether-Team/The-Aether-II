package com.gildedgames.aether.client.ui.util.transform;

import com.gildedgames.aether.client.ui.common.Gui;
import com.gildedgames.aether.client.ui.data.rect.Rect;

import java.util.List;

public interface GuiPositioner
{

	List<Gui> positionList(List<Gui> guis, Rect collectionDim);

}
