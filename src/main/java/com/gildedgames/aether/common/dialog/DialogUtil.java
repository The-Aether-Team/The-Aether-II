package com.gildedgames.aether.common.dialog;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.dialog.IDialogController;
import com.gildedgames.aether.api.dialog.IDialogSlide;
import com.gildedgames.aether.api.dialog.IDialogSlideRenderer;
import com.gildedgames.aether.api.dialog.IDialogSpeaker;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class DialogUtil
{
	public static IDialogSpeaker getCurrentSpeaker(IDialogController controller)
	{
		if (!controller.getCurrentLine().getSpeaker().isPresent())
		{
			return null;
		}

		final ResourceLocation speakerPath = controller.getCurrentLine().getSpeaker().get();

		return AetherAPI.content().dialog().getSpeaker(speakerPath).orElseThrow(() ->
				new IllegalArgumentException("Couldn't getByte speaker: " + speakerPath));
	}

	public static IDialogSlide getSlide(IDialogController controller)
	{
		if (!controller.getCurrentLine().getSpeaker().isPresent())
		{
			return null;
		}

		final ResourceLocation speakerPath = controller.getCurrentLine().getSpeaker().get();

		IDialogSpeaker speaker = AetherAPI.content().dialog().getSpeaker(speakerPath).orElseThrow(() ->
				new IllegalArgumentException("Couldn't getByte speaker: " + speakerPath));

		final String address;
		IDialogSlide slide = null;

		// Check if the speaker resourcelocation has a slide address
		if (speakerPath.getPath().contains("#"))
		{
			// Obtain the slide address from the Speaker resourcelocation
			address = speakerPath.getPath().substring(speakerPath.getPath().indexOf("#") + 1);

			slide = AetherAPI.content().dialog().findSlide(address, speaker).orElseThrow(() ->
					new IllegalArgumentException("Couldn't find slide: " + address));
		}
		else if (speaker.getSlides().isPresent())
		{
			final Map<String, IDialogSlide> slides = speaker.getSlides().get();

			if (!slides.isEmpty() && slides.containsKey("default"))
			{
				slide = slides.get("default");
			}
		}

		return slide;
	}

	public static IDialogSlideRenderer getRenderer(IDialogSlide slide)
	{
		IDialogSlideRenderer renderer = null;

		if (slide != null && slide.getRenderer().isPresent())
		{
			final String renderType = slide.getRenderer().get();

			renderer = AetherAPI.content().dialog().findRenderer(renderType).orElseThrow(() ->
					new IllegalArgumentException("Couldn't find slide renderer: " + renderType));

			renderer.setup(slide);
		}

		return renderer;
	}

}
