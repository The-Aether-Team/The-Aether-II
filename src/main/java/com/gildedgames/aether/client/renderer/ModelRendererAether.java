package com.gildedgames.aether.client.renderer;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModelRendererAether extends ModelRenderer
{
	private final ModelBaseAether model;

	/** Sets a callback to be executed after the model renders itself. Automatically nulled after drawing. **/
	public Runnable callback = null;

	/** Sets whether or not this model will be rendered regardless of the model's global flag. Automatically reset after drawing. **/
	public boolean forceDisplayFlag = false;

	public ModelRendererAether(ModelBaseAether model, String boxNameIn)
	{
		super(model, boxNameIn);

		this.model = model;
	}

	public ModelRendererAether(ModelBaseAether model)
	{
		super(model);

		this.model = model;
	}

	public ModelRendererAether(ModelBaseAether model, int texOffX, int texOffY)
	{
		super(model, texOffX, texOffY);

		this.model = model;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void render(float scale)
	{
		this.render(scale, false);
	}

	public void render(float scale, boolean single)
	{
		if (!this.isHidden)
		{
			if (this.showModel)
			{
				if (!this.compiled)
				{
					this.compileDisplayList(scale);
				}

				boolean displayChildren = !single && this.areChildrenDisplaying();

				if (!single)
				{
					if (!displayChildren)
					{
						if (this.childModels != null)
						{
							for (ModelRenderer childModel : this.childModels)
							{
								((ModelRendererAether) childModel).consumeDisplayFlag();
							}
						}
					}
				}

				GlStateManager.pushMatrix();

				GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);

				if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F)
				{
					if (this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F)
					{
						GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
					}
				}
				else
				{
					GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);

					if (this.rotateAngleZ != 0.0F)
					{
						GlStateManager.rotate(this.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
					}

					if (this.rotateAngleY != 0.0F)
					{
						GlStateManager.rotate(this.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
					}

					if (this.rotateAngleX != 0.0F)
					{
						GlStateManager.rotate(this.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
					}
				}

				boolean drawSelf = single || this.consumeDisplayFlag();

				if (drawSelf)
				{
					GlStateManager.callList(this.displayList);
				}

				if (displayChildren && this.childModels != null)
				{
					for (ModelRenderer childModel : this.childModels)
					{
						childModel.render(scale);
					}
				}

				if (!single && this.callback != null)
				{
					Runnable callback = this.callback;
					callback.run();

					this.callback = null;
				}

				GlStateManager.popMatrix();
			}
		}
	}

	private boolean consumeDisplayFlag()
	{
		boolean ret = this.model.getDefaultDisplayState() || this.forceDisplayFlag;

		this.forceDisplayFlag = false;

		return ret;
	}

	private boolean areChildrenDisplaying()
	{
		if (this.childModels != null)
		{
			for (ModelRenderer childModel : this.childModels)
			{
				if (((ModelRendererAether) childModel).isBranchNecessary())
				{
					return true;
				}
			}
		}

		return false;
	}

	private boolean isBranchNecessary()
	{
		if (this.model.getDefaultDisplayState() || this.forceDisplayFlag)
		{
			return true;
		}

		if (this.childModels != null)
		{
			for (ModelRenderer childModel : this.childModels)
			{
				if (((ModelRendererAether) childModel).isBranchNecessary())
				{
					return true;
				}
			}
		}

		return false;
	}
}
