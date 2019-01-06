package com.gildedgames.aether.common.asm;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.util.Map;

@SuppressWarnings("unused")
@IFMLLoadingPlugin.SortingIndex(0)
public class AetherFMLLoadingPlugin implements IFMLLoadingPlugin
{
	public AetherFMLLoadingPlugin()
	{
		MixinBootstrap.init();

		Mixins.addConfiguration("mixins.aether.lighting.json");
	}

	@Override
	public String[] getASMTransformerClass()
	{
		return new String[0];
	}

	@Override
	public String getModContainerClass()
	{
		return AetherFMLModContainer.class.getName();
	}

	@Nullable
	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{

	}

	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}
}
