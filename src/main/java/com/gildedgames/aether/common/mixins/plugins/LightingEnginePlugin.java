package com.gildedgames.aether.common.mixins.plugins;

import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class LightingEnginePlugin implements IMixinConfigPlugin
{
	@Override
	public void onLoad(String mixinPackage)
	{

	}

	@Override
	public String getRefMapperConfig()
	{
		String refmap = "mixins.aether.refmap.json";

		if (LightingEnginePlugin.class.getResourceAsStream(refmap) != null)
		{
			return refmap;
		}

		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName)
	{
		return true;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets)
	{

	}

	@Override
	public List<String> getMixins()
	{
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo)
	{

	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo)
	{

	}
}
