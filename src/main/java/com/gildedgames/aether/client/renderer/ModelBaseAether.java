package com.gildedgames.aether.client.renderer;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;

public class ModelBaseAether<T extends Entity> extends EntityModel<T>
{
	private boolean displayState;

	public void setDefaultDisplayState(boolean displayState)
	{
		this.displayState = displayState;
	}

	public boolean getDefaultDisplayState()
	{
		return this.displayState;
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
