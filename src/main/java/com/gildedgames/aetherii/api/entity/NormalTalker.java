package com.gildedgames.aetherii.api.entity;

import com.gildedgames.aetherii.api.dialog.IDialogChangeListener;
import com.gildedgames.aetherii.api.dialog.IDialogScene;
import com.gildedgames.aetherii.api.dialog.scene.ISceneInstance;
import net.minecraft.resources.ResourceLocation;

public interface NormalTalker {
	IDialogScene getCurrentScene();

	ISceneInstance getCurrentSceneInstance();


	void addListener(IDialogChangeListener listener);

	ResourceLocation getTalker();
}
