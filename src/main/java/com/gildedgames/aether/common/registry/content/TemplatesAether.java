package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class TemplatesAether
{
	private static final TemplateManager MANAGER = new TemplateManager("structures", DataFixesManager.createFixer());

	public static Template blue_skyroot_tree_1, blue_skyroot_tree_2, blue_skyroot_tree_3;

	public static Template green_skyroot_tree_1, green_skyroot_tree_2, green_skyroot_tree_3;

	public static Template green_skyroot_oak_1, green_skyroot_oak_2;

	public static Template golden_oak_1, golden_oak_2;

	public static Template green_skyroot_windswept_1, green_skyroot_windswept_2, green_skyroot_windswept_3, green_skyroot_windswept_4, green_skyroot_windswept_5, green_skyroot_windswept_6, green_skyroot_windswept_7;

	public static Template dark_blue_skyroot_tree_1, dark_blue_skyroot_tree_2, dark_blue_skyroot_tree_3;

	public static Template dark_blue_skyroot_oak_1, dark_blue_skyroot_oak_2;

	public static Template large_green_skyroot_pine_1, large_green_skyroot_pine_2;

	public static Template green_skyroot_pine_1, green_skyroot_pine_2, green_skyroot_pine_3, green_skyroot_pine_4, green_skyroot_pine_5;

	public static Template skyroot_moa_nest_tree_1, skyroot_moa_nest_1, skyroot_moa_nest_2;

	public static Template aether_portal;

	public static Template mysterious_henge, outpost_a, outpost_b, outpost_c;

	private TemplatesAether()
	{

	}

	public static void init()
	{
		blue_skyroot_tree_1 = registerTemplate("highlands/trees/normal/blue/blue_skyroot_tree_1");
		blue_skyroot_tree_2 = registerTemplate("highlands/trees/normal/blue/blue_skyroot_tree_2");
		blue_skyroot_tree_3 = registerTemplate("highlands/trees/normal/blue/blue_skyroot_tree_3");

		green_skyroot_tree_1 = registerTemplate("highlands/trees/normal/green/green_skyroot_tree_1");
		green_skyroot_tree_2 = registerTemplate("highlands/trees/normal/green/green_skyroot_tree_2");
		green_skyroot_tree_3 = registerTemplate("highlands/trees/normal/green/green_skyroot_tree_3");

		green_skyroot_oak_1 = registerTemplate("highlands/trees/oak/green/green_skyroot_oak_1");
		green_skyroot_oak_2 = registerTemplate("highlands/trees/oak/green/green_skyroot_oak_2");

		golden_oak_1 = registerTemplate("highlands/trees/oak/golden/golden_oak_1");
		golden_oak_2 = registerTemplate("highlands/trees/oak/golden/golden_oak_2");

		green_skyroot_windswept_1 = registerTemplate("highlands/trees/windswept/green/green_skyroot_windswept_1");
		green_skyroot_windswept_2 = registerTemplate("highlands/trees/windswept/green/green_skyroot_windswept_2");
		green_skyroot_windswept_3 = registerTemplate("highlands/trees/windswept/green/green_skyroot_windswept_3");
		green_skyroot_windswept_4 = registerTemplate("highlands/trees/windswept/green/green_skyroot_windswept_4");
		green_skyroot_windswept_5 = registerTemplate("highlands/trees/windswept/green/green_skyroot_windswept_5");
		green_skyroot_windswept_6 = registerTemplate("highlands/trees/windswept/green/green_skyroot_windswept_6");
		green_skyroot_windswept_7 = registerTemplate("highlands/trees/windswept/green/green_skyroot_windswept_7");

		skyroot_moa_nest_tree_1 = registerTemplate("moa_nest/skyroot_moa_nest_tree_1");
		skyroot_moa_nest_1 = registerTemplate("moa_nest/skyroot_moa_nest_1");
		skyroot_moa_nest_2 = registerTemplate("moa_nest/skyroot_moa_nest_2");

		large_green_skyroot_pine_1 = registerTemplate("highlands/trees/pine/green/large_green_skyroot_pine_1");
		large_green_skyroot_pine_2 = registerTemplate("highlands/trees/pine/green/large_green_skyroot_pine_2");

		green_skyroot_pine_1 = registerTemplate("highlands/trees/pine/green/green_skyroot_pine_1");
		green_skyroot_pine_2 = registerTemplate("highlands/trees/pine/green/green_skyroot_pine_2");
		green_skyroot_pine_3 = registerTemplate("highlands/trees/pine/green/green_skyroot_pine_3");
		green_skyroot_pine_4 = registerTemplate("highlands/trees/pine/green/green_skyroot_pine_4");
		green_skyroot_pine_5 = registerTemplate("highlands/trees/pine/green/green_skyroot_pine_5");

		dark_blue_skyroot_tree_1 = registerTemplate("highlands/trees/normal/dark_blue/dark_blue_skyroot_tree_1");
		dark_blue_skyroot_tree_2 = registerTemplate("highlands/trees/normal/dark_blue/dark_blue_skyroot_tree_2");
		dark_blue_skyroot_tree_3 = registerTemplate("highlands/trees/normal/dark_blue/dark_blue_skyroot_tree_3");

		dark_blue_skyroot_oak_1 = registerTemplate("highlands/trees/oak/dark_blue/dark_blue_skyroot_oak_1");
		dark_blue_skyroot_oak_2 = registerTemplate("highlands/trees/oak/dark_blue/dark_blue_skyroot_oak_2");

		aether_portal = registerTemplate("aether_portal");

		mysterious_henge = registerTemplate("mysterious_henge");

		outpost_a = registerTemplate("outpost_a");
		outpost_b = registerTemplate("outpost_b");
		outpost_c = registerTemplate("outpost_c");
	}

	private static Template registerTemplate(final String resource)
	{
		return registerTemplate(AetherCore.getResource(resource));
	}

	public static Template registerTemplate(final ResourceLocation resourceLocation)
	{
		return TemplatesAether.MANAGER.getTemplate(null, resourceLocation);
	}

}
