package com.gildedgames.aether.api.net.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserFeatures
{
	@SerializedName("skins")
	public List<String> skins = new ArrayList<>();
}
