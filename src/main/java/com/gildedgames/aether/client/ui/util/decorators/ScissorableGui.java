package com.gildedgames.aether.client.ui.util.decorators;

import com.gildedgames.aether.client.ui.common.Gui;
import com.gildedgames.aether.client.ui.common.GuiDecorator;
import com.gildedgames.aether.client.ui.common.Ui;
import com.gildedgames.aether.client.ui.data.UIContainer;
import com.gildedgames.aether.client.ui.data.rect.ModDim2D;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.util.rect.RectSeekable;
import com.gildedgames.aether.client.ui.util.rect.RectSeeker;
import com.google.common.collect.ImmutableList;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;

public class ScissorableGui extends GuiDecorator<Gui> implements RectSeekable
{

	protected ModDim2D scissoredArea;

	private ImmutableList<RectSeeker> seekers = ImmutableList.<RectSeeker>of(new Seeker(this));

	public ScissorableGui(Rect scissoredArea, Gui gui)
	{
		super(gui);

		this.scissoredArea = (new ModDim2D()).set(scissoredArea);
	}

	public ModDim2D getScissoredArea()
	{
		return this.scissoredArea;
	}

	@Override
	protected void preInitContent(InputProvider input)
	{

	}

	@Override
	protected void postInitContent(InputProvider input)
	{

	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		for (UIContainer container : this.getDecoratedElement().seekAllContent())
		{
			for (Ui ui : container)
			{
				if (ui instanceof Gui)
				{
					Gui gui = (Gui) ui;

					if (!gui.dim().intersects(this.getScissoredArea()))
					{
						gui.setEnabled(false);
						gui.setVisible(false);
					}
					else
					{
						gui.setEnabled(true);
						gui.setVisible(true);
					}
				}
			}
		}

		GL11.glPushMatrix();

		double lowerLeftCornerY = this.getScissoredArea().y() + this.getScissoredArea().height();

		double cornerX = (this.getScissoredArea().x() * input.getScaleFactor());
		double cornerY = (input.getScreenHeight() - lowerLeftCornerY) * input.getScaleFactor();

		double cutWidth = this.getScissoredArea().width() * input.getScaleFactor();
		double cutHeight = this.getScissoredArea().height() * input.getScaleFactor();

		GL11.glEnable(GL_SCISSOR_TEST);

		GL11.glScissor((int) cornerX, (int) cornerY, (int) cutWidth, (int) cutHeight);

		super.draw(graphics, input);

		GL11.glDisable(GL_SCISSOR_TEST);

		GL11.glPopMatrix();
	}

	@Override
	public ImmutableList<RectSeeker> getRectSeekers()
	{
		return this.seekers;
	}

	public static class Seeker extends RectSeeker<ScissorableGui>
	{

		public Seeker()
		{

		}

		public Seeker(ScissorableGui seekFrom)
		{
			super(seekFrom);
		}

		@Override
		public ModDim2D dim()
		{
			return this.seekFrom.getScissoredArea();
		}

	}

}
