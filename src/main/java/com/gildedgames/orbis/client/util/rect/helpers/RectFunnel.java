package com.gildedgames.orbis.client.util.rect.helpers;

import com.gildedgames.orbis.client.util.rect.BuildIntoRectHolder;
import com.gildedgames.orbis.client.util.rect.RectHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RectFunnel
{

	protected List<BuildIntoRectHolder> modifiers;

	private RectFunnel(final List<BuildIntoRectHolder> modifiers)
	{
		this.modifiers = modifiers;
	}

	public static RectFunnel collect(final List<RectHolder> holders)
	{
		return RectFunnel.collect(holders.toArray(new RectHolder[holders.size()]));
	}

	public static RectFunnel collect(final RectHolder... holders)
	{
		final List<BuildIntoRectHolder> modifiers = new ArrayList<BuildIntoRectHolder>();

		for (final RectHolder holder : holders)
		{
			modifiers.add(new BuildIntoRectHolder(holder.dim()));
		}

		return new RectFunnel(modifiers);
	}

	public static RectFunnel collect(final BuildIntoRectHolder... modifiers)
	{
		return new RectFunnel(Arrays.asList(modifiers));
	}

	public RectFunnel resetPos()
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.resetPos();
		}

		return this;
	}

	public RectFunnel scale(final float scale)
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.scale(scale);
		}

		return this;
	}

	public RectFunnel height(final int height)
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.height(height);
		}

		return this;
	}

	public RectFunnel width(final int width)
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.width(width);
		}

		return this;
	}

	public RectFunnel pos(final int x, final int y)
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.pos(x, y);
		}

		return this;
	}

	public RectFunnel center(final boolean centeredX, final boolean centeredY)
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.center(centeredX, centeredY);
		}

		return this;
	}

	public RectFunnel centerX(final boolean centeredX)
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.centerX(centeredX);
		}

		return this;
	}

	public RectFunnel centerY(final boolean centeredY)
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.centerY(centeredY);
		}

		return this;
	}

	public RectFunnel area(final int width, final int height)
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.area(width, height);
		}

		return this;
	}

	public RectFunnel y(final int y)
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.y(y);
		}

		return this;
	}

	public RectFunnel x(final int x)
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.x(x);
		}

		return this;
	}

	public RectFunnel center(final boolean centered)
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.center(centered);
		}

		return this;
	}

	public RectFunnel addScale(final float scale)
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.addScale(scale);
		}

		return this;
	}

	public RectFunnel addWidth(final int width)
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.addWidth(width);
		}

		return this;
	}

	public RectFunnel addHeight(final int height)
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.addHeight(height);
		}

		return this;
	}

	public RectFunnel addArea(final int width, final int height)
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.addArea(width, height);
		}

		return this;
	}

	public RectFunnel addX(final int x)
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.addX(x);
		}

		return this;
	}

	public RectFunnel addY(final int y)
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.addY(y);
		}

		return this;
	}

	public RectFunnel addPos(final float x, final float y)
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.addPos(x, y);
		}

		return this;
	}

	public RectFunnel flush()
	{
		for (final BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.flush();
		}

		return this;
	}

}
