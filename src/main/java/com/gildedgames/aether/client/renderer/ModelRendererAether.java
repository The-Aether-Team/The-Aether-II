package com.gildedgames.aether.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

public class ModelRendererAether extends RendererModel
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
	@OnlyIn(Dist.CLIENT)
	public void render(float scale)
	{
		this.render(scale, false, false);
	}

	/**
	 *
	 * @param scale The scale factor of the model
	 * @param single Whether or not to render child models
	 * @param isOnlyChild Whether or not this is the only child of the parent drawing it. Optimization hint
	 */
	public void render(float scale, boolean single, boolean isOnlyChild)
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
							for (RendererModel childModel : this.childModels)
							{
								((ModelRendererAether) childModel).consumeDisplayFlag();
							}
						}
					}
				}

				if (!isOnlyChild)
				{
					GL11.glPushMatrix();
				}

				float translateX = this.offsetX + (this.rotationPointX * scale);
				float translateY = this.offsetY + (this.rotationPointY * scale);
				float translateZ = this.offsetZ + (this.rotationPointZ * scale);

				if (translateX != 0.0F || translateY != 0.0F || translateZ != 0.0F)
				{
					GlStateManager.translatef(translateX, translateY, translateZ);
				}

				if (this.rotateAngleZ != 0.0F)
				{
					GlStateManager.rotatef(this.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
				}

				if (this.rotateAngleY != 0.0F)
				{
					GlStateManager.rotatef(this.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
				}

				if (this.rotateAngleX != 0.0F)
				{
					GlStateManager.rotatef(this.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
				}

				boolean drawSelf = single || this.consumeDisplayFlag();

				if (drawSelf)
				{
					GlStateManager.callList(this.displayList);
				}

				if (displayChildren && this.childModels != null)
				{
					for (RendererModel childModel : this.childModels)
					{
						((ModelRendererAether) childModel).render(scale, false, false);
					}
				}

				if (!single && this.callback != null)
				{
					Runnable callback = this.callback;
					callback.run();

					this.callback = null;
				}

				if (!isOnlyChild)
				{
					GL11.glPopMatrix();
				}
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
			for (RendererModel childModel : this.childModels)
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
			for (RendererModel childModel : this.childModels)
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
