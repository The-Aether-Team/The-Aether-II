package com.gildedgames.aetherii.dialog.data.render;

import com.gildedgames.aetherii.api.dialog.IDialog;
import com.gildedgames.aetherii.api.dialog.IDialogRenderer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Type;
import java.util.Map;

public class DialogRendererStatic implements IDialogRenderer {
	private ResourceLocation slideTexture;

	private float scale = 1.0F;

	private int width, height;

	@Override
	public void setup(IDialog slide) {
		if (!slide.getDialogData().isPresent()) {
			return;
		}

		Map<String, String> data = slide.getDialogData().get();

		if (data.containsKey("resource")) {
			this.slideTexture = new ResourceLocation(data.get("resource"));
		}

		if (data.containsKey("scale")) {
			this.scale = Float.valueOf(data.get("scale"));
		}

		if (data.containsKey("width")) {
			this.width = Integer.valueOf(data.get("width"));
		}

		if (data.containsKey("height")) {
			this.height = Integer.valueOf(data.get("height"));
		}
	}

	@Override
	public void draw(IDialog slide, PoseStack poseStack, double screenWidth, double screenHeight, int mouseX, int mouseY, float partialTicks) {
		if (this.slideTexture == null) {
			return;
		}
		double scaledWidth = this.width * this.scale;
		double scaledHeight = this.height * this.scale;

		poseStack.pushPose();

		poseStack.translate((screenWidth / 2) - (scaledWidth / 2), screenHeight - 90 - scaledHeight, 0);
		poseStack.scale(this.scale, this.scale, this.scale);

		RenderSystem.setShaderTexture(0, this.slideTexture);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

		Gui.blit(poseStack, 0, 0, 0, 0, this.width, this.height, this.width, this.height);
		poseStack.popPose();
	}

	public static class Deserializer implements JsonDeserializer<DialogRendererStatic> {
		@Override
		public DialogRendererStatic deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			return new DialogRendererStatic();
		}
	}
}