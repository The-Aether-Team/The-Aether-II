package com.gildedgames.aether.client.models.entities.player;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;

import java.util.Map;
import java.util.UUID;

public class LayerHeadShadow extends LayerBipedArmor
{
	private final RenderLivingBase<?> renderer;

	private ModelPlayer modelArmorSlim;

	public LayerHeadShadow(RenderLivingBase<?> rendererIn)
	{
		super(rendererIn);

		this.renderer = rendererIn;
		this.modelArmor = new ModelPlayer(0.15f, false);
		this.modelArmorSlim = new ModelPlayer(0.15f, true);
	}

	@Override
	public void doRenderLayer(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
			float netHeadYaw, float headPitch, float scale)
	{
		if (!AetherCore.CONFIG.hasHelmetShadow())
		{
			return;
		}

		PlayerAether player = PlayerAether.getPlayer(entity);

		if (player.getEntity().getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty())
		{
			return;
		}

		GameProfile profile = player.getEntity().getGameProfile();

		ResourceLocation texture = DefaultPlayerSkin.getDefaultSkinLegacy();

		if (profile != null)
		{
			Minecraft minecraft = Minecraft.getMinecraft();
			Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(profile);

			if (map.containsKey(MinecraftProfileTexture.Type.SKIN))
			{
				texture = minecraft.getSkinManager().loadSkin(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
			}
			else
			{
				UUID uuid = EntityPlayer.getUUID(profile);
				texture = DefaultPlayerSkin.getDefaultSkin(uuid);
			}
		}

		String skinType = DefaultPlayerSkin.getSkinType(entity.getUniqueID());

		ModelBiped t = skinType.equals("slim") ? this.modelArmorSlim : this.modelArmor;

		t.bipedHead.showModel = true;
		t.bipedHeadwear.showModel = true;

		t.bipedRightArm.showModel = false;
		t.bipedLeftArm.showModel = false;
		t.bipedBody.showModel = false;

		GlStateManager.pushMatrix();

		t.setModelAttributes(this.renderer.getMainModel());
		t.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);

		this.renderer.bindTexture(texture);

		GlStateManager.color(0.25F, 0.25F, 0.25F, 1.0F);

		t.render(player.getEntity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		GlStateManager.popMatrix();
	}
}
