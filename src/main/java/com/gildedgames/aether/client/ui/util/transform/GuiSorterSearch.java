package com.gildedgames.aether.client.ui.util.transform;

import com.gildedgames.aether.client.ui.common.Gui;

import java.util.ArrayList;
import java.util.List;

public class GuiSorterSearch implements GuiSorter
{

	protected String searchText = "";

	public GuiSorterSearch()
	{

	}

	public void setSearchText(String searchText)
	{
		this.searchText = searchText;
	}

	@Override
	public List<Gui> sortList(List<Gui> list)
	{
		if (this.searchText == null || this.searchText.isEmpty())
		{
			return list;
		}

		List<Gui> result = new ArrayList<>();

		for (Gui view : list)
		{
			if (view != null && view.query(this.searchText))
			{
				result.add(view);
			}
		}

		return result;
	}

}
