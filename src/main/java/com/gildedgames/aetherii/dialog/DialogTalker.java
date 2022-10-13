package com.gildedgames.aetherii.dialog;

import com.gildedgames.aetherii.api.dialog.IDialog;
import com.gildedgames.aetherii.api.dialog.IDialogTalker;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;

public class DialogTalker implements IDialogTalker {
	@SerializedName("name")
	private String name;

	@SerializedName("dialogs")
	private Map<String, IDialog> dialogs;

	@Nonnull
	@Override
	public String getUnlocalizedName() {
		return this.name != null ? this.name : "";
	}

	@Override
	public Optional<Map<String, IDialog>> getDialogs() {
		return this.dialogs != null ? Optional.of(this.dialogs) : Optional.empty();
	}
}
