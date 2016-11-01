package com.gildedgames.aether.client.ui.util;

import com.gildedgames.aether.client.ui.common.Gui;
import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.common.Ui;
import com.gildedgames.aether.client.ui.data.Pos2D;
import com.gildedgames.aether.client.ui.data.rect.Dim2D;
import com.gildedgames.aether.client.ui.data.rect.RectHolder;
import com.gildedgames.aether.client.ui.data.rect.RectListener;
import com.gildedgames.aether.client.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.util.factory.ContentFactory;
import com.gildedgames.aether.client.ui.util.transform.GuiPositioner;
import com.gildedgames.aether.client.ui.util.transform.GuiSorter;
import com.gildedgames.aether.common.util.ObjectFilter;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class GuiCollection<T extends Ui> extends GuiFrame
{

	protected GuiPositioner positioner;

	protected GuiSorter sorter;

	protected List<ContentFactory<T>> contentProviders = new ArrayList<>();

	private boolean isSorting;

	public GuiCollection(GuiPositioner positioner, ContentFactory<T>... contentProviders)
	{
		this(Pos2D.flush(), 0, positioner, contentProviders);
	}

	public GuiCollection(Pos2D pos, int width, GuiPositioner positioner, ContentFactory<T>... contentProviders)
	{
		super(Dim2D.build().pos(pos).width(width).flush());

		this.positioner = positioner;

		this.contentProviders.addAll(Arrays.asList(contentProviders));
	}

	public void setPositioner(GuiPositioner positioner)
	{
		this.positioner = positioner;
	}

	public GuiPositioner getPositioner()
	{
		return this.positioner;
	}

	public void setSorter(GuiSorter sorter)
	{
		this.sorter = sorter;
	}

	public GuiSorter getSorter()
	{
		return this.sorter;
	}

	public void clearAndProvideContent()
	{
		this.events().clear(Gui.class);

		for (ContentFactory<T> contentProvider : this.contentProviders)
		{
			if (contentProvider != null)
			{
				LinkedHashMap<String, T> elements = contentProvider.provideContent(ImmutableMap.copyOf(this.events().map()), this.dim());
				for (Entry<String, T> entry : elements.entrySet())
				{
					RectHolder holder = ObjectFilter.cast(entry.getValue(), RectHolder.class);
					if (holder != null)
					{
						holder.dim().addListener(new RectListener()
						{
							@Override
							public void notifyDimChange(List<ModifierType> modifier)
							{
								if (!modifier.isEmpty() && !modifier.contains(ModifierType.X) && !modifier.contains(ModifierType.Y) && GuiCollection.this.isSorting)
								{
									GuiCollection.this.sortAndPositionContent();
								}
							}
						});
					}
					this.onElementAdded(entry.getValue());
				}
				this.events().setAll(elements);
			}
		}
	}

	protected void onElementAdded(T element)
	{

	}

	private void positionContent(List<Gui> views)
	{
		this.positioner.positionList(views, this.dim());

		float totalContentHeight = 0;

		for (Gui gui : views)
		{
			if (gui != null)
			{
				//gui.dim().mod().addModifier(this, ModifierType.POS).flush();

				totalContentHeight = Math.max(totalContentHeight, gui.dim().y() + gui.dim().height());
			}
		}

		this.dim().mod().height(totalContentHeight).flush();
	}

	private void sortContent()
	{
		List<Gui> filteredViews = ObjectFilter.getTypesFrom(this.events().elements(), Gui.class);
		List<Gui> sortedViews = this.sorter != null ? this.sorter.sortList(filteredViews) : filteredViews;

		for (Gui view : filteredViews)
		{
			view.setVisible(false);
		}

		for (Gui view : sortedViews)
		{
			view.setVisible(true);
		}
	}

	public void sortAndPositionContent()
	{
		this.isSorting = true;

		this.sortContent();
		this.positionContent(this.getSortedViews());

		this.isSorting = false;
	}

	private List<Gui> getSortedViews()
	{
		List<Gui> filteredViews = ObjectFilter.getTypesFrom(this.events().elements(), Gui.class);

		return this.sorter != null ? this.sorter.sortList(filteredViews) : filteredViews;
	}

	@Override
	public void initContent(InputProvider input)
	{
		this.clearAndProvideContent();
		this.sortAndPositionContent();

		super.initContent(input);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
	}

}
