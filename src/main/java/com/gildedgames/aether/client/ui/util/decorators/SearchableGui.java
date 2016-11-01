package com.gildedgames.aether.client.ui.util.decorators;

import com.gildedgames.aether.client.ui.common.GuiDecorator;
import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.rect.Dim2D;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.util.input.DataInputListener;
import com.gildedgames.aether.client.ui.util.input.GuiInput;
import com.gildedgames.aether.client.ui.util.input.StringInput;

public class SearchableGui extends GuiDecorator<GuiFrame>
{

	private GuiInput<String> searchField;

	private StringInput searchFieldInput;

	private DataInputListener<String> searcher;

	public SearchableGui(GuiFrame element)
	{
		this(element, null);
	}

	public SearchableGui(GuiFrame element, DataInputListener<String> searcher)
	{
		super(element);

		this.searcher = searcher;
	}

	public void setSearcher(DataInputListener<String> searcher)
	{
		this.searcher = searcher;
	}

	@Override
	protected void preInitContent(InputProvider input)
	{

	}

	@Override
	protected void postInitContent(InputProvider input)
	{
		this.searchFieldInput = new StringInput();
		this.searchField = new GuiInput<>(this.searchFieldInput, Dim2D.build().area(this.dim().width(), 17).addY(-27).flush(), "Search:");

		this.content().set("searchField", this.searchField);

		if (this.searcher != null)
		{
			this.searchFieldInput.addListener(this.searcher);
		}
	}

}
