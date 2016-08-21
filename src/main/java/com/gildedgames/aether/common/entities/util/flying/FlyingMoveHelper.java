package com.gildedgames.aether.common.entities.util.flying;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.MathHelper;

public class FlyingMoveHelper extends EntityMoveHelper
{
    private final EntityFlying entity;

    public FlyingMoveHelper(EntityFlying entity)
    {
        super(entity);
        this.entity = entity;
    }

    public void onUpdateMoveHelper()
    {
        if (this.action == EntityMoveHelper.Action.MOVE_TO && !this.entity.getNavigator().noPath())
        {
            double d0 = this.posX - this.entity.posX;
            double d1 = this.posY - this.entity.posY;
            double d2 = this.posZ - this.entity.posZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            d3 = (double) MathHelper.sqrt_double(d3);
            d1 = d1 / d3;
            float f = (float)(MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
            this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, f, 90.0F);
            this.entity.renderYawOffset = this.entity.rotationYaw;

            float f1 = (float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
            this.entity.setAIMoveSpeed(this.entity.getAIMoveSpeed() + (f1 - this.entity.getAIMoveSpeed()) * 0.125F);
            double d4 = 0.007F;
            double d5 = Math.cos((double)(this.entity.rotationYaw * 0.017453292F));
            double d6 = Math.sin((double)(this.entity.rotationYaw * 0.017453292F));
            this.entity.motionX += d4 * d5;
            this.entity.motionZ += d4 * d6;
            //d4 = Math.sin((double)(this.entity.ticksExisted + this.entity.getEntityId()) * 0.75D) * 0.05D;
            this.entity.motionY += d4 * (d6 + d5) * 0.25D;
            this.entity.motionY += (double)this.entity.getAIMoveSpeed() * d1 * 0.1D;

            EntityLookHelper entitylookhelper = this.entity.getLookHelper();
            double d7 = this.entity.posX + d0 / d3 * 2.0D;
            double d8 = (double)this.entity.getEyeHeight() + this.entity.posY + d1 / d3;
            double d9 = this.entity.posZ + d2 / d3 * 2.0D;
            double d10 = entitylookhelper.getLookPosX();
            double d11 = entitylookhelper.getLookPosY();
            double d12 = entitylookhelper.getLookPosZ();

            if (!entitylookhelper.getIsLooking())
            {
                d10 = d7;
                d11 = d8;
                d12 = d9;
            }

            this.entity.getLookHelper().setLookPosition(d10 + (d7 - d10) * 0.125D, d11 + (d8 - d11) * 0.125D, d12 + (d9 - d12) * 0.125D, 10.0F, 40.0F);
            this.entity.setMoving(true);
        }
        else
        {
            this.entity.setAIMoveSpeed(0.0F);
            this.entity.setMoving(false);
        }
    }
}
