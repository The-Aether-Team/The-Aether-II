package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.animals.EntityGlactrix;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;

/**
 * NewProject - Undefined
 * Created using Tabula 5.1.0
 */

public class ModelGlactrixCrystals extends EntityModel<EntityGlactrix>
{
    public RendererModel crystal_main1;
    public RendererModel crystal_main2;
    public RendererModel crystal_main3;
    public RendererModel crystal_sub1;
    public RendererModel crystal_sub2;
    public RendererModel crystal_sub4;
    public RendererModel crystal_sub5;
    public RendererModel crystal_sub6;
    public RendererModel crystal_main4;

    public ModelGlactrixCrystals() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.crystal_sub1 = new RendererModel(this, 32, 0);
        this.crystal_sub1.setRotationPoint(-2.1F, 18.5F, -4.2F);
        this.crystal_sub1.addBox(0.0F, -2.0F, 0.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(crystal_sub1, 0.6108652381980153F, 0.0F, 0.0F);
        this.crystal_main1 = new RendererModel(this, 16, 0);
        this.crystal_main1.setRotationPoint(-0.5F, 18.0F, -2.0F);
        this.crystal_main1.addBox(0.0F, -5.0F, 0.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(crystal_main1, 0.2617993877991494F, 0.0F, 0.17453292519943295F);
        this.crystal_main4 = new RendererModel(this, 24, 0);
        this.crystal_main4.mirror = true;
        this.crystal_main4.setRotationPoint(1.0F, 18.0F, 3.0F);
        this.crystal_main4.addBox(-2.0F, -4.5F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(crystal_main4, -0.7740535232594852F, 0.0F, -0.045553093477052F);
        this.crystal_sub4 = new RendererModel(this, 24, 0);
        this.crystal_sub4.setRotationPoint(-4.7F, 18.5F, 1.8F);
        this.crystal_sub4.addBox(0.0F, -3.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(crystal_sub4, -0.6829473363053812F, 0.0F, -0.6829473363053812F);
        this.crystal_main3 = new RendererModel(this, 8, 0);
        this.crystal_main3.setRotationPoint(0.0F, 18.0F, 0.0F);
        this.crystal_main3.addBox(0.0F, -6.0F, 0.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(crystal_main3, -0.4553564018453205F, 0.0F, 0.4553564018453205F);
        this.crystal_sub6 = new RendererModel(this, 16, 0);
        this.crystal_sub6.mirror = true;
        this.crystal_sub6.setRotationPoint(5.3F, 18.5F, 1.8F);
        this.crystal_sub6.addBox(-2.0F, -3.0F, 0.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(crystal_sub6, -0.9560913642424937F, 0.0F, 0.8651597102135892F);
        this.crystal_main2 = new RendererModel(this, 0, 0);
        this.crystal_main2.mirror = true;
        this.crystal_main2.setRotationPoint(-0.5F, 18.0F, -1.0F);
        this.crystal_main2.addBox(-2.0F, -7.0F, 0.0F, 2, 7, 2, 0.0F);
        this.setRotateAngle(crystal_main2, -0.2617993877991494F, 0.0F, -0.17453292519943295F);
        this.crystal_sub5 = new RendererModel(this, 24, 0);
        this.crystal_sub5.setRotationPoint(2.3F, 17.5F, -3.2F);
        this.crystal_sub5.addBox(0.0F, -3.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(crystal_sub5, -0.17453292519943295F, 0.0F, 0.8726646259971648F);
        this.crystal_sub2 = new RendererModel(this, 24, 0);
        this.crystal_sub2.setRotationPoint(-4.7F, 19.0F, -2.2F);
        this.crystal_sub2.addBox(0.0F, -3.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(crystal_sub2, -0.17453292519943295F, 0.0F, -0.5235987755982988F);
    }

    @Override
    public void render(EntityGlactrix entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.crystal_sub1.render(f5);
        this.crystal_main1.render(f5);
        this.crystal_main4.render(f5);
        this.crystal_sub4.render(f5);
        this.crystal_main3.render(f5);
        this.crystal_sub6.render(f5);
        this.crystal_main2.render(f5);
        this.crystal_sub5.render(f5);
        this.crystal_sub2.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
