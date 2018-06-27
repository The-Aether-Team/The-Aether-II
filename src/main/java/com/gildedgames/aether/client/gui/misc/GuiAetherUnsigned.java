package com.gildedgames.aether.client.gui.misc;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiCheckBox;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URI;

public class GuiAetherUnsigned extends GuiScreen
{
	private static final String CURSEFORGE_URL = "https://go.aetherii.com/curseforge";

	private final GuiScreen parent;

	private GuiCheckBox checkbox;

	private GuiButton dlButton, ackButton;

	public GuiAetherUnsigned(@Nullable GuiScreen parent)
	{
		this.parent = parent;
	}

	@Override
	public void initGui()
	{
		ScaledResolution sr = new ScaledResolution(this.mc);

		this.checkbox = new GuiCheckBox(0, (sr.getScaledWidth() / 2) - 140, 180, "I understand that this version won't receive support.", false);

		this.ackButton = new GuiButton(1, (sr.getScaledWidth() / 2) - 60, 200, 120, 20, "Acknowledge");
		this.ackButton.enabled = false;

		this.dlButton = new GuiButton(10, (sr.getScaledWidth() / 2) - 90, 108, 180, 20, "Download from CurseForge");

		this.buttonList.add(this.checkbox);
		this.buttonList.add(this.ackButton);
		this.buttonList.add(this.dlButton);

		super.initGui();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		String text1 = "Integrity check failed!";
		String[] desc = ("You have downloaded a corrupted or unofficial release of the Aether II!\n\n"
				+ "If you weren't expecting this warning, you should immediately re-download\n"
				+ "the Aether II from our official CurseForge page. Otherwise, you may\n"
				+ "run into game-breaking bugs or other cryptic issues.\n\n\n\n\n\n"
				+ "If you are a developer hacking on the code or otherwise know what\n"
				+ "you're doing, then you can safely ignore this warning.").split("\n");

		ScaledResolution sr = new ScaledResolution(this.mc);

		this.drawDefaultBackground();
		this.drawString(this.fontRenderer, text1, (sr.getScaledWidth() / 2) - (this.fontRenderer.getStringWidth(text1) / 2), 30, 0xFF0000);

		for (int i = 0; i < desc.length; i++)
		{
			String str = desc[i];

			this.drawString(this.fontRenderer, str, (sr.getScaledWidth() / 2) - (this.fontRenderer.getStringWidth(str) / 2), 50 + (i * 10), 0xFFFFFF);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);

		// Backup option in case the platform doesn't support opening links
		if (this.dlButton.isMouseOver())
		{
			this.drawHoveringText("Opens link in new window:\n" + TextFormatting.BLUE + CURSEFORGE_URL, mouseX, mouseY);
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button.id == this.checkbox.id)
		{
			this.ackButton.enabled = this.checkbox.isChecked();
		}
		else if (button.id == this.ackButton.id && this.ackButton.enabled)
		{
			AetherCore.CONFIG.markFingerprintViolationAck();

			this.mc.displayGuiScreen(this.parent);
		}
		else if (button.id == this.dlButton.id)
		{
			// Prevents crashes on unsupported platforms
			try
			{
				Class<?> oclass = Class.forName("java.awt.Desktop");

				Object object = oclass.getMethod("getDesktop").invoke(null);
				oclass.getMethod("browse", URI.class).invoke(object, new URI(CURSEFORGE_URL));
			}
			catch (Throwable throwable)
			{
				AetherCore.LOGGER.error("Couldn't open link", throwable);
			}
		}

		super.actionPerformed(button);
	}
}
