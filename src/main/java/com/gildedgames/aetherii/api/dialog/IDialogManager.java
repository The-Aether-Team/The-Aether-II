package com.gildedgames.aetherii.api.dialog;

import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public interface IDialogManager {
	Optional<IDialogTalker> getTalker(ResourceLocation resource);

	Optional<IDialogScene> getScene(ResourceLocation resource);

	Optional<IDialog> findDialog(String slideAddress, IDialogTalker speaker);

	Optional<IDialogRenderer> findRenderer(String type);
}
