package com.gildedgames.aether.client.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelBaseAether extends ModelBase
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
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
