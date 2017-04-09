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

	public static Template aether_portal, nether_portal, end_portal;

	public static Template mysterious_henge;

	public static Template kura_tree_1, kura_tree_2, kura_tree_3, kura_tree_4, kura_tree_5;

	public static Template large_kura_tree_1;

	public static Template kura_bush_1, kura_bush_2, kura_bush_3, kura_bush_4;

	private TemplatesAether()
	{

	}

	public static void init()
	{
		blue_skyroot_tree_1 = getTemplate("highlands/trees/normal/blue/blue_skyroot_tree_1");
		blue_skyroot_tree_2 = getTemplate("highlands/trees/normal/blue/blue_skyroot_tree_2");
		blue_skyroot_tree_3 = getTemplate("highlands/trees/normal/blue/blue_skyroot_tree_3");

		green_skyroot_tree_1 = getTemplate("highlands/trees/normal/green/green_skyroot_tree_1");
		green_skyroot_tree_2 = getTemplate("highlands/trees/normal/green/green_skyroot_tree_2");
		green_skyroot_tree_3 = getTemplate("highlands/trees/normal/green/green_skyroot_tree_3");

		green_skyroot_oak_1 = getTemplate("highlands/trees/oak/green/green_skyroot_oak_1");
		green_skyroot_oak_2 = getTemplate("highlands/trees/oak/green/green_skyroot_oak_2");

		golden_oak_1 = getTemplate("highlands/trees/oak/golden/golden_oak_1");
		golden_oak_2 = getTemplate("highlands/trees/oak/golden/golden_oak_2");

		green_skyroot_windswept_1 = getTemplate("highlands/trees/windswept/green/green_skyroot_windswept_1");
		green_skyroot_windswept_2 = getTemplate("highlands/trees/windswept/green/green_skyroot_windswept_2");
		green_skyroot_windswept_3 = getTemplate("highlands/trees/windswept/green/green_skyroot_windswept_3");
		green_skyroot_windswept_4 = getTemplate("highlands/trees/windswept/green/green_skyroot_windswept_4");
		green_skyroot_windswept_5 = getTemplate("highlands/trees/windswept/green/green_skyroot_windswept_5");
		green_skyroot_windswept_6 = getTemplate("highlands/trees/windswept/green/green_skyroot_windswept_6");
		green_skyroot_windswept_7 = getTemplate("highlands/trees/windswept/green/green_skyroot_windswept_7");

		skyroot_moa_nest_tree_1 = getTemplate("moa_nest/skyroot_moa_nest_tree_1");
		skyroot_moa_nest_1 = getTemplate("moa_nest/skyroot_moa_nest_1");
		skyroot_moa_nest_2 = getTemplate("moa_nest/skyroot_moa_nest_2");

		large_green_skyroot_pine_1 = getTemplate("highlands/trees/pine/green/large_green_skyroot_pine_1");
		large_green_skyroot_pine_2 = getTemplate("highlands/trees/pine/green/large_green_skyroot_pine_2");

		green_skyroot_pine_1 = getTemplate("highlands/trees/pine/green/green_skyroot_pine_1");
		green_skyroot_pine_2 = getTemplate("highlands/trees/pine/green/green_skyroot_pine_2");
		green_skyroot_pine_3 = getTemplate("highlands/trees/pine/green/green_skyroot_pine_3");
		green_skyroot_pine_4 = getTemplate("highlands/trees/pine/green/green_skyroot_pine_4");
		green_skyroot_pine_5 = getTemplate("highlands/trees/pine/green/green_skyroot_pine_5");

		dark_blue_skyroot_tree_1 = getTemplate("highlands/trees/normal/dark_blue/dark_blue_skyroot_tree_1");
		dark_blue_skyroot_tree_2 = getTemplate("highlands/trees/normal/dark_blue/dark_blue_skyroot_tree_2");
		dark_blue_skyroot_tree_3 = getTemplate("highlands/trees/normal/dark_blue/dark_blue_skyroot_tree_3");

		dark_blue_skyroot_oak_1 = getTemplate("highlands/trees/oak/dark_blue/dark_blue_skyroot_oak_1");
		dark_blue_skyroot_oak_2 = getTemplate("highlands/trees/oak/dark_blue/dark_blue_skyroot_oak_2");

		aether_portal = getTemplate("aether_portal");
		nether_portal = getTemplate("nether_portal");
		end_portal = getTemplate("end_portal");

		mysterious_henge = getTemplate("mysterious_henge");

		kura_tree_1 = getTemplate("kura/trees/small/kura_tree_1");
		kura_tree_2 = getTemplate("kura/trees/small/kura_tree_2");
		kura_tree_3 = getTemplate("kura/trees/small/kura_tree_3");
		kura_tree_4 = getTemplate("kura/trees/small/kura_tree_4");
		kura_tree_5 = getTemplate("kura/trees/small/kura_tree_5");

		large_kura_tree_1 = getTemplate("kura/trees/large/large_kura_tree_1");

		kura_bush_1 = getTemplate("kura/trees/small/kura_bush_1");
		kura_bush_2 = getTemplate("kura/trees/small/kura_bush_2");
		kura_bush_3 = getTemplate("kura/trees/small/kura_bush_3");
		kura_bush_4 = getTemplate("kura/trees/small/kura_bush_4");
	}

	private static Template getTemplate(String resource)
	{
		return getTemplate(AetherCore.getResource(resource));
	}

	public static Template getTemplate(ResourceLocation resourceLocation)
	{
		return TemplatesAether.MANAGER.getTemplate(null, resourceLocation);
	}

}
