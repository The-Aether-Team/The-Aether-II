package com.gildedgames.orbis.client.util.rect;

import com.gildedgames.orbis_core.util.ObjectFilter;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * A wrapper around a Rect object to provide a modified state via RectModifiers.
 * @author Brandon Pearce
 */
public class ModDim2D implements Rect
{

	private final BuildIntoRectHolder buildInto;

	private final RectListener ourListener;

	private List<RectModifier> modifiers = Lists.newArrayList();

	private List<RectListener> listeners = Lists.newArrayList();

	/**
	 * Originalstate: Non-modified rectangle value without modifiers, the base values
	 * modifiedState: original state with modifiers applied.
	 */
	private Rect originalState = Dim2D.flush(), modifiedState = Dim2D.flush();

	private boolean preventRecursion = false;

	public ModDim2D()
	{
		this.buildInto = new BuildIntoRectHolder(this);

		this.ourListener = this.createListener();
	}

	public static ModDim2D build()
	{
		return new ModDim2D();
	}

	public static ModDim2D build(final Rect rect)
	{
		final ModDim2D dim = new ModDim2D();
		dim.set(rect);
		return dim;
	}

	public static ModDim2D clone(final RectHolder owner)
	{
		return owner.dim().clone();
	}

	public static List<RectModifier.ModifierType> getChangedTypes(final Rect r1, final Rect r2)
	{
		final List<RectModifier.ModifierType> types = Lists.newArrayList();
		if (r1.x() != r2.x())
		{
			types.add(RectModifier.ModifierType.X);
		}
		if (r1.y() != r2.y())
		{
			types.add(RectModifier.ModifierType.Y);
		}
		if (r1.width() != r2.width())
		{
			types.add(RectModifier.ModifierType.WIDTH);
		}
		if (r1.height() != r2.height())
		{
			types.add(RectModifier.ModifierType.HEIGHT);
		}
		if (r1.degrees() != r2.degrees())
		{
			types.add(RectModifier.ModifierType.ROTATION);
		}
		return types;
	}

	private RectListener createListener()
	{
		return new RectListener()
		{

			@Override
			public void notifyDimChange(final List<RectModifier.ModifierType> types)
			{
				ModDim2D.this.refreshModifiedState();
			}

		};
	}

	public Rect originalState()
	{
		return this.originalState;
	}

	private Rect modifiedState()
	{
		for (final RectModifier modifier : this.modifiers)
		{
			modifier.modifyingWith().updateState();
		}
		return this.modifiedState;
	}

	public BuildIntoRectHolder mod()
	{
		return this.buildInto;
	}

	public RectBuilder copyRect()
	{
		return new RectBuilder(this);
	}

	@Override
	public ModDim2D clone()
	{
		final ModDim2D clone = new ModDim2D();
		clone.set(this);
		return clone;
	}

	public ModDim2D set(final RectHolder holder)
	{
		this.set(holder.dim());
		return this;
	}

	public ModDim2D set(final Rect dim)
	{
		this.originalState = dim;
		this.buildInto.set(this.originalState);

		this.refreshModifiedState();
		return this;
	}

	public ModDim2D set(final ModDim2D modDim)
	{
		this.modifiers = new ArrayList<RectModifier>(modDim.modifiers);
		this.listeners = new ArrayList<RectListener>(modDim.listeners);
		this.originalState = modDim.originalState;
		this.modifiedState = modDim.modifiedState;
		this.buildInto.set(this.originalState);
		return this;
	}

	/**
	 * Calculate the values for the modified state of this Dim2D object, based on the provided pool of Modifiers.
	 */
	protected void refreshModifiedState()
	{
		final Rect oldModifiedState = this.modifiedState;

		float degrees = this.originalState.degrees();

		float scale = this.originalState.scale();

		float posX = this.originalState.x();
		float posY = this.originalState.y();

		float width = this.originalState.width();
		float height = this.originalState.height();

		for (final RectModifier modifier : this.mods())
		{
			if (modifier == null)
			{
				continue;
			}

			final RectHolder modifyingWith = modifier.modifyingWith();

			if (modifyingWith.dim() == null || modifyingWith.dim() == this)
			{
				continue;
			}

			if (modifier.getType().equals(RectModifier.ModifierType.ROTATION))
			{
				degrees += modifyingWith.dim().degrees();
			}

			if (modifier.getType().equals(RectModifier.ModifierType.SCALE))
			{
				scale *= modifyingWith.dim().scale();
			}

			if (modifier.getType().equals(RectModifier.ModifierType.X))
			{
				posX += modifyingWith.dim().x();
			}

			if (modifier.getType().equals(RectModifier.ModifierType.Y))
			{
				posY += modifyingWith.dim().y();
			}

			if (modifier.getType().equals(RectModifier.ModifierType.WIDTH))
			{
				width += modifyingWith.dim().width();
			}

			if (modifier.getType().equals(RectModifier.ModifierType.HEIGHT))
			{
				height += modifyingWith.dim().height();
			}
		}

		final float offsetX = this.originalState.isCenteredX() ? width * this.originalState.scale() / 2 : 0;
		final float offsetY = this.originalState.isCenteredY() ? height * this.originalState.scale() / 2 : 0;
		width *= this.originalState.scale();
		height *= this.originalState.scale();

		posX -= offsetX;
		posY -= offsetY;

		this.modifiedState = Dim2D.build(this.originalState).pos(posX, posY).area(width, height).degrees(degrees).scale(scale).flush();

		if (this.preventRecursion)
		{
			return;
		}

		this.preventRecursion = true;

		final List<RectModifier.ModifierType> changedTypes = ModDim2D.getChangedTypes(oldModifiedState, this.modifiedState);

		for (final RectListener listener : this.listeners)
		{
			listener.notifyDimChange(changedTypes);
		}

		this.preventRecursion = false;
	}

	public Collection<RectModifier> mods()
	{
		return this.modifiers;
	}

	public boolean containsModifier(final RectHolder modifier)
	{
		return this.modifiers.contains(modifier);
	}

	/**
	 * Clears the modifiers that you pass along to the method
	 */
	public ModDim2D clear(final RectModifier.ModifierType... types)
	{
		if (types.length == 0)
		{
			for (final RectModifier modifier : this.modifiers)
			{
				modifier.modifyingWith().dim().removeListener(this.ourListener);
			}
			this.modifiers.clear();
			this.refreshModifiedState();
			return this;
		}

		final List<RectModifier.ModifierType> list = Lists.newArrayList();
		for (final RectModifier.ModifierType type : types)
		{
			list.add(type);
		}

		final List<RectModifier.ModifierType> filteredTypes = this.filterModifierTypes(list);
		this.modifiers = ObjectFilter.getTypesFrom(this.modifiers, new ObjectFilter.FilterCondition<RectModifier>(this.modifiers)
		{
			@Override
			public boolean isType(final RectModifier modifier)
			{
				for (final RectModifier.ModifierType type : ObjectFilter.getTypesFrom(filteredTypes, RectModifier.ModifierType.class))
				{
					if (modifier.getType().equals(type))
					{
						return false;
					}
				}

				return true;
			}
		});

		this.refreshModifiedState();
		return this;
	}

	private List<RectModifier.ModifierType> filterModifierTypes(final List<RectModifier.ModifierType> types)
	{
		if (types.contains(RectModifier.ModifierType.AREA))
		{
			types.remove(RectModifier.ModifierType.AREA);
			if (!types.contains(RectModifier.ModifierType.WIDTH))
			{
				types.add(RectModifier.ModifierType.WIDTH);
			}
			if (!types.contains(RectModifier.ModifierType.HEIGHT))
			{
				types.add(RectModifier.ModifierType.HEIGHT);
			}
		}

		if (types.contains(RectModifier.ModifierType.POS))
		{
			types.remove(RectModifier.ModifierType.POS);
			if (!types.contains(RectModifier.ModifierType.X))
			{
				types.add(RectModifier.ModifierType.X);
			}
			if (!types.contains(RectModifier.ModifierType.Y))
			{
				types.add(RectModifier.ModifierType.Y);
			}
		}

		if (types.contains(RectModifier.ModifierType.ALL))
		{
			types.remove(RectModifier.ModifierType.ALL);
			for (final RectModifier.ModifierType type : RectModifier.ModifierType.values())
			{
				if (!types.contains(type))
				{
					types.add(type);
				}
			}
		}
		return types;
	}

	public ModDim2D add(final RectHolder modifyingWith, final RectModifier.ModifierType mandatoryType, final RectModifier.ModifierType... otherTypes)
	{
		if (modifyingWith.dim().equals(this))
		{
			throw new IllegalArgumentException();
		}

		for (final RectModifier.ModifierType type : this.filterModifierTypes(this.toList(mandatoryType, otherTypes)))
		{
			final RectModifier modifier = new RectModifier(modifyingWith, type);

			if (!this.modifiers.contains(modifier))
			{
				this.modifiers.add(modifier);

				modifyingWith.dim().addListener(this.ourListener);

				this.refreshModifiedState();
			}
		}
		return this;
	}

	private List<RectModifier.ModifierType> toList(final RectModifier.ModifierType manda, final RectModifier.ModifierType... types)
	{
		final List<RectModifier.ModifierType> list = Lists.newArrayList();
		list.add(manda);
		for (final RectModifier.ModifierType type : types)
		{
			list.add(type);
		}
		return list;
	}

	/**
	 * Removes from this modDim the modifiers using the given 
	 * RectHolder for the given types.
	 * @param modifyingWith
	 * @param mandatoryType
	 * @param otherTypes
	 * @return
	 */
	public boolean remove(final RectHolder modifyingWith, final RectModifier.ModifierType mandatoryType, final RectModifier.ModifierType... otherTypes)
	{
		boolean success = true;
		outerloop:
		for (final RectModifier.ModifierType type : this.filterModifierTypes(this.toList(mandatoryType, otherTypes)))
		{
			final Iterator<RectModifier> iter = this.modifiers.iterator();
			while (iter.hasNext())
			{
				final RectModifier modifier = iter.next();
				if (modifier.modifyingWith() == modifyingWith && modifier.getType().equals(type))
				{
					iter.remove();
					continue outerloop;
				}
			}
			success = false;
		}

		if (!this.hasModifiersFor(modifyingWith))
		{
			modifyingWith.dim().removeListener(this.ourListener);
		}

		this.refreshModifiedState();

		return success;
	}

	private boolean hasModifiersFor(final RectHolder holder)
	{
		for (final RectModifier rModifier : this.modifiers)
		{
			if (rModifier.modifyingWith().equals(holder))
			{
				return true;
			}
		}
		return false;
	}

	public void addListener(final RectListener listener)
	{
		if (!this.listeners.contains(listener))
		{
			this.listeners.add(listener);
		}
	}

	public boolean removeListener(final RectListener listener)
	{
		return this.listeners.remove(listener);
	}

	@Override
	public float degrees()
	{
		return this.modifiedState().degrees();
	}

	@Override
	public float originX()
	{
		return this.modifiedState().originX();
	}

	@Override
	public float originY()
	{
		return this.modifiedState().originY();
	}

	@Override
	public float scale()
	{
		return this.modifiedState().scale();
	}

	@Override
	public float maxX()
	{
		return this.modifiedState().maxX();
	}

	@Override
	public float maxY()
	{
		return this.modifiedState().maxY();
	}

	@Override
	public float x()
	{
		return this.modifiedState().x();
	}

	@Override
	public float y()
	{
		return this.modifiedState().y();
	}

	@Override
	public float width()
	{
		return this.modifiedState().width();
	}

	@Override
	public float height()
	{
		return this.modifiedState().height();
	}

	@Override
	public boolean isCenteredX()
	{
		return this.modifiedState().isCenteredX();
	}

	@Override
	public boolean isCenteredY()
	{
		return this.modifiedState().isCenteredY();
	}

	@Override
	public boolean intersects(final float x, final float y)
	{
		return this.modifiedState().intersects(x, y);
	}

	@Override
	public boolean intersects(final Rect dim)
	{
		return this.modifiedState().intersects(dim);
	}

	@Override
	public RectBuilder rebuild()
	{
		return this.modifiedState().rebuild();
	}

	@Override
	public String toString()
	{
		return this.modifiedState.toString();
	}

	@Override
	public float centerX()
	{
		return this.modifiedState.centerX();
	}

	@Override
	public float centerY()
	{
		return this.modifiedState.centerY();
	}

	@Override
	public Pos2D center()
	{
		return this.modifiedState.center();
	}

}
