package com.gildedgames.aether.client.ui.data;

import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.client.ui.common.Gui;
import com.gildedgames.aether.client.ui.common.Ui;
import com.gildedgames.aether.client.ui.data.rect.Dim2D;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.common.util.ObjectFilter;
import net.minecraft.nbt.NBTTagCompound;

import java.util.*;

public class UIContainer implements Iterable<Ui>, NBT
{

	protected Map<String, Ui> elements = new LinkedHashMap<>();

	protected Ui attachedUi;

	protected Ui parentUi;

	public UIContainer(Ui attachedUi)
	{
		this.attachedUi = attachedUi;
	}

	public Ui getAttachedUi()
	{
		return this.attachedUi;
	}

	public Ui getParentUi()
	{
		return this.parentUi;
	}

	public Ui get(String key)
	{
		return this.elements.get(key);
	}

	public <T> T get(String key, Class<? extends T> clazz)
	{
		return (T) this.elements.get(key);
	}

	public Collection<Ui> elements()
	{
		return this.elements.values();
	}

	public Map<String, Ui> map()
	{
		return new LinkedHashMap<>(this.elements);
	}

	public boolean isEmpty()
	{
		return this.elements.isEmpty();
	}

	public List<Gui> queryAll(Object... input)
	{
		List<Gui> guis = new ArrayList<>();

		if (this.getAttachedUi() instanceof Gui)
		{
			Gui gui = (Gui) this.getAttachedUi();

			if (input.length == 0 || gui.query(input))
			{
				guis.add(gui);
			}
		}

		for (Gui element : ObjectFilter.getTypesFrom(this.elements(), Gui.class))
		{
			if (element == null)
			{
				continue;
			}

			for (UIContainer container : element.seekAllContent())
			{
				guis.addAll(container.queryAll(input));
			}

			if (input.length == 0 || element.query(input))
			{
				guis.add(element);
			}
		}

		return guis;
	}

	public boolean contains(String key)
	{
		return this.map().containsKey(key);
	}

	public boolean contains(Ui element)
	{
		return this.map().containsValue(element);
	}

	public boolean contains(Class<? extends Ui> clazz)
	{
		for (Ui ui : this.elements.values())
		{
			if (ui.getClass().isAssignableFrom(clazz))
			{
				return true;
			}
		}

		return false;
	}

	public int size()
	{
		return this.elements.size();
	}

	public Rect getCombinedDimensions()
	{
		List<Rect> areas = new ArrayList<>();

		this.addCombinedDimensions(this, areas);

		return Dim2D.combine(areas);
	}

	private void addCombinedDimensions(UIContainer container, List<Rect> areas)
	{
		for (Map.Entry<String, Ui> entry : container.map().entrySet())
		{
			String key = entry.getKey();
			Ui element = entry.getValue();

			if (element instanceof Gui)
			{
				Gui view = (Gui) element;

				areas.add(view.dim());

				this.addCombinedDimensions(view.seekContent(), areas);
			}
		}
	}

	@Override
	public UIContainer clone()
	{
		UIContainer clone = new UIContainer(this.attachedUi);

		clone.elements = new LinkedHashMap<>(this.elements);

		return clone;
	}

	public UIContainer merge(UIContainer first, UIContainer... rest)
	{
		return this.merge(false, first, rest);
	}

	public UIContainer merge(boolean newContentFirst, UIContainer first, UIContainer... rest)
	{
		UIContainer clone = this.clone();
		UIContainer merged = new UIContainer(this.attachedUi);

		if (!newContentFirst)
		{
			merged.elements = clone.elements;
		}

		merged.elements.putAll(first.elements);

		for (UIContainer container : rest)
		{
			merged.elements.putAll(container.elements);
		}

		if (newContentFirst)
		{
			merged.elements.putAll(clone.elements);
		}

		return merged;
	}

	@Override
	public Iterator<Ui> iterator()
	{
		return this.elements().iterator();
	}

	@Override
	public void write(NBTTagCompound output)
	{

	}

	@Override
	public void read(NBTTagCompound input)
	{

	}

	public UIContainer getTopParent()
	{
		UIContainer parent = this.getParentUi().seekContent();

		while (parent != null)
		{
			if (parent.getParentUi() == null)
			{
				return parent;
			}

			if (parent.getParentUi().seekContent() == null)
			{
				return parent;
			}

			parent = parent.getParentUi().seekContent();
		}

		return null;
	}

}
