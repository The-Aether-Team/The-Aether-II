package com.gildedgames.orbis.client.gui.data;

import com.gildedgames.aether.api.util.IText;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class Text implements IText
{

	private static final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

	private ITextComponent component;

	private float scale;

	public Text(final ITextComponent component, final float scale)
	{
		this.component = component;
		this.scale = scale;
	}

	@Override
	public ITextComponent component()
	{
		return this.component;
	}

	@Override
	public float scaledHeight()
	{
		return (int) (this.height() * this.scale);
	}

	@Override
	public float scaledWidth()
	{
		return (int) (this.width() * this.scale);
	}

	@Override
	public float scale()
	{
		return this.scale;
	}

	@Override
	public float width()
	{
		return fontRenderer.getStringWidth(this.component.getFormattedText());
	}

	@Override
	public float height()
	{
		return fontRenderer.FONT_HEIGHT;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		tag.setFloat("scale", this.scale);
		tag.setString("text", this.component.getUnformattedText());
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		this.scale = tag.getFloat("Scale");
		this.component = new TextComponentString(tag.getString("text"));
	}
}
