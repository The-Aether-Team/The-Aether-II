package com.gildedgames.aether.client.ui.util.rect;

import com.gildedgames.aether.client.ui.data.rect.BuildIntoRectHolder;
import com.gildedgames.aether.client.ui.data.rect.RectHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RectFunnel
{

	protected List<BuildIntoRectHolder> modifiers;

	private RectFunnel(List<BuildIntoRectHolder> modifiers)
	{
		this.modifiers = modifiers;
	}

	public static RectFunnel collect(List<RectHolder> holders)
	{
		return RectFunnel.collect(holders.toArray(new RectHolder[holders.size()]));
	}

	public static RectFunnel collect(RectHolder... holders)
	{
		List<BuildIntoRectHolder> modifiers = new ArrayList<>();

		for (RectHolder holder : holders)
		{
			modifiers.add(new BuildIntoRectHolder(holder.dim()));
		}

		return new RectFunnel(modifiers);
	}

	public static RectFunnel collect(BuildIntoRectHolder... modifiers)
	{
		return new RectFunnel(Arrays.asList(modifiers));
	}

	public RectFunnel resetPos()
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.resetPos();
		}

		return this;
	}

	public RectFunnel scale(float scale)
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.scale(scale);
		}

		return this;
	}

	public RectFunnel height(int height)
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.height(height);
		}

		return this;
	}

	public RectFunnel width(int width)
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.width(width);
		}

		return this;
	}

	public RectFunnel pos(int x, int y)
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.pos(x, y);
		}

		return this;
	}

	public RectFunnel center(boolean centeredX, boolean centeredY)
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.center(centeredX, centeredY);
		}

		return this;
	}

	public RectFunnel centerX(boolean centeredX)
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.centerX(centeredX);
		}

		return this;
	}

	public RectFunnel centerY(boolean centeredY)
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.centerY(centeredY);
		}

		return this;
	}

	public RectFunnel area(int width, int height)
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.area(width, height);
		}

		return this;
	}

	public RectFunnel y(int y)
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.y(y);
		}

		return this;
	}

	public RectFunnel x(int x)
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.x(x);
		}

		return this;
	}

	public RectFunnel center(boolean centered)
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.center(centered);
		}

		return this;
	}

	public RectFunnel addScale(float scale)
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.addScale(scale);
		}

		return this;
	}

	public RectFunnel addWidth(int width)
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.addWidth(width);
		}

		return this;
	}

	public RectFunnel addHeight(int height)
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.addHeight(height);
		}

		return this;
	}

	public RectFunnel addArea(int width, int height)
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.addArea(width, height);
		}

		return this;
	}

	public RectFunnel addX(int x)
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.addX(x);
		}

		return this;
	}

	public RectFunnel addY(int y)
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.addY(y);
		}

		return this;
	}

	public RectFunnel addPos(float x, float y)
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.addPos(x, y);
		}

		return this;
	}

	public RectFunnel flush()
	{
		for (BuildIntoRectHolder modifier : this.modifiers)
		{
			modifier.flush();
		}

		return this;
	}

}
