package com.gildedgames.aether.client.ui.util;

import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.AssetLocation;
import com.gildedgames.aether.client.ui.data.rect.Dim2D;
import com.gildedgames.aether.client.ui.graphics.Sprite;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.minecraft.util.GuiFactory;
import com.gildedgames.aether.client.ui.minecraft.util.MinecraftAssetLocation;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.Map;
import java.util.UUID;

public class SkinButton extends GuiFrame
{
	private final GameProfile profile;

	public SkinButton(GameProfile profile, int x, int y)
	{
		super(Dim2D.build().area(8, 8).pos(x, y).flush());
		this.profile = profile;
	}

	public SkinButton(GameProfile profile)
	{
		super(Dim2D.build().area(8, 8).flush());
		this.profile = profile;
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		Minecraft minecraft = Minecraft.getMinecraft();
		Map map = minecraft.getSkinManager().loadSkinFromCache(this.profile);
		ResourceLocation location;

		if (map.containsKey(Type.SKIN))
		{
			location = minecraft.getSkinManager().loadSkin((MinecraftProfileTexture) map.get(Type.SKIN), Type.SKIN);
		}
		else
		{
			UUID uuid = EntityPlayer.getUUID(this.profile);
			location = DefaultPlayerSkin.getDefaultSkin(uuid);
		}

		AssetLocation asset = new MinecraftAssetLocation(location);
		Sprite sprite = Sprite.createWithArea(asset, Sprite.UV.build().min(8f, 8f).max(16, 16f).flush(), 64, 64);
		this.content().set("head", GuiFactory.texture(sprite));
	}

}
