package com.gildedgames.aether.client.ui.data;

import com.gildedgames.aether.client.ui.common.Ui;
import com.gildedgames.aether.client.ui.data.rect.RectHolder;
import com.gildedgames.aether.client.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.aether.client.ui.util.RectangleElement;
import com.gildedgames.aether.common.util.ObjectFilter;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UIContainerMutable extends UIContainer
{

	public UIContainerMutable(Ui attachedUi)
	{
		super(attachedUi);
	}

	public void displayDim(RectHolder holder)
	{
		this.elements.put("displayDim", new RectangleElement(holder.dim(), new DrawingData(Color.PINK)));
	}

	public void set(String key, Ui element)
	{
		if (element == null)
		{
			this.elements.remove(key);

			return;
		}

		RectHolder gui = ObjectFilter.cast(element, RectHolder.class);
		RectHolder parentModifier = ObjectFilter.cast(this.getAttachedUi(), RectHolder.class);

		element.seekContent().parentUi = this.getAttachedUi();

		if (gui != null && gui.dim().mod() != null && parentModifier != null)
		{
			gui.dim().add(parentModifier, ModifierType.POS, ModifierType.SCALE);
		}

		this.elements.put(key, element);
	}

	public void remove(String key)
	{
		this.elements.remove(key);
	}

	public void remove(Ui element)
	{
		List<String> keysToRemove = new ArrayList<>();

		for (Map.Entry<String, Ui> entry : this.elements.entrySet())
		{
			String key = entry.getKey();
			Ui elem = entry.getValue();

			if (elem.equals(element))
			{
				keysToRemove.add(key);
			}
		}

		for (String key : keysToRemove)
		{
			this.elements.remove(key);
		}
	}

	public void clear(Class<? extends Ui> classToRemove)
	{
		Map<String, Ui> objectsToRemove = ObjectFilter.getTypesFromValues(this.elements, String.class, classToRemove);

		for (Map.Entry<String, Ui> entry : objectsToRemove.entrySet())
		{
			String key = entry.getKey();

			this.remove(key);
		}
	}

	public void setAll(Map<String, ? extends Ui> elements)
	{
		for (Map.Entry<String, ? extends Ui> entry : elements.entrySet())
		{
			this.set(entry.getKey(), entry.getValue());
		}
	}

	public void clear()
	{
		this.elements.clear();
	}

	public UIContainer immutable()
	{
		return this.clone();
	}

	@Override
	public UIContainer clone()
	{
		UIContainerMutable clone = new UIContainerMutable(this.attachedUi);

		clone.elements = new LinkedHashMap<>(this.elements);

		return clone;
	}

}
