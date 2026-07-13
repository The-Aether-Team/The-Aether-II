package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.*;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaModel;
import com.aetherteam.aetherii.client.renderer.entity.state.MoaRenderState;
import com.aetherteam.aetherii.entity.passive.Moa;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

public class MoaRenderer extends AgeableMobRenderer<Moa, MoaRenderState, EntityModel<MoaRenderState>> {
    private static final Identifier MOA_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/moa_base.png");
    private static final Identifier MOA_BABY_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/moa_baby_base.png");

    public MoaRenderer(EntityRendererProvider.Context context) {
        super(context, new MoaModel(context.bakeLayer(AetherIIModelLayers.MOA)), new MoaBabyModel(context.bakeLayer(AetherIIModelLayers.MOA_BABY)), 0.5F);
        this.addLayer(new MoaKeratinLayer(this, context));
        this.addLayer(new MoaFeathersLayer(this, context));
        this.addLayer(new MoaEyesLayer(this, context));
        this.addLayer(new MoaSaddleLayer(this, context.getEquipmentAssets()));
        this.addLayer(new MoaSaddlebagLayer(this, context.getEquipmentAssets()));
    }

    @Override
    public MoaRenderState createRenderState() {
        return new MoaRenderState();
    }

    @Override
    public void extractRenderState(Moa moa, MoaRenderState renderState, float partialTick) {
        super.extractRenderState(moa, renderState, partialTick);
        renderState.sitting = moa.isSitting();
        renderState.saddle = moa.getItemBySlot(EquipmentSlot.SADDLE).copy();
        renderState.saddlebag = moa.getSaddlebagStack();
        renderState.flyAmount = moa.getFlyAmount(partialTick);
        renderState.featherColor = moa.getFeatherColor();
        renderState.keratinColor = moa.getKeratinColor();
        renderState.eyeColor = moa.getEyeColor();
        renderState.featherShape = moa.getFeatherShape();
        renderState.specialVariant = moa.getSpecialVariant().orElse(null);
        renderState.vehicleReference = moa.getRider();
        renderState.opacity = calculateOpacity(renderState);
    }

    @Override
    protected int getModelTint(MoaRenderState renderState) {
        float opacity = renderState.opacity;
        if (opacity < 1.0F) {
            return ARGB.colorFromFloat(opacity, 1.0F, 1.0F, 1.0F);
        }
        return super.getModelTint(renderState);
    }

    @Override
    public Identifier getTextureLocation(MoaRenderState renderState) {
        return renderState.isBaby ? renderState.getSpecialBabyTextureOr(MOA_BABY_LOCATION) : renderState.getSpecialDefaultTextureOr(MOA_LOCATION);
    }

    protected float calculateOpacity(MoaRenderState renderState) {
        if (Minecraft.getInstance().getCameraEntity() instanceof Player player && Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON) {
            if (renderState.vehicleReference != null && renderState.vehicleReference.matches(player)) {
                return 0.5F;
            }
        }
        return 1.0F;
    }
}