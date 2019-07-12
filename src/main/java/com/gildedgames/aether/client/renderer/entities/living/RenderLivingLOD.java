package com.gildedgames.aether.client.renderer.entities.living;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.MobEntity;

public abstract class RenderLivingLOD<T extends MobEntity, M extends EntityModel<T>> extends LivingRenderer<T, M>
{
	private final EntityModel lowDetailModel;

	protected boolean isLowDetail;

	public RenderLivingLOD(EntityRendererManager rendermanagerIn, M highDetailModel, M lowDetailModel, float shadowsizeIn)
	{
		super(rendermanagerIn, highDetailModel, shadowsizeIn);

		this.lowDetailModel = lowDetailModel;
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		boolean forceLowDetail = false;
		boolean lowDetail =  forceLowDetail || Minecraft.getInstance().player.getDistanceSq(entity) > this.getHighLODMinDistanceSq();

		this.isLowDetail = lowDetail;

		M prev = this.getEntityModel();

		if (lowDetail)
		{
			this.mainModel = this.lowDetailModel;
		}

		super.doRender(entity, x, y, z, entityYaw, partialTicks);

		this.mainModel = prev;

		this.isLowDetail = false;
	}

	/**
	 * @return The minimum distance needed to use the high level of detail model.
	 */
	protected double getHighLODMinDistanceSq()
	{
		return 30.0 * 30.0;
	}
}
