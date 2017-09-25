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

	public static Template mysterious_henge, outpost_a, outpost_b, outpost_c;

	private TemplatesAether()
	{

	}

	public static void init()
	{
		blue_skyroot_tree_1 = registerTemplate(0, "highlands/trees/normal/blue/blue_skyroot_tree_1");
		blue_skyroot_tree_2 = registerTemplate(1, "highlands/trees/normal/blue/blue_skyroot_tree_2");
		blue_skyroot_tree_3 = registerTemplate(2, "highlands/trees/normal/blue/blue_skyroot_tree_3");

		green_skyroot_tree_1 = registerTemplate(3, "highlands/trees/normal/green/green_skyroot_tree_1");
		green_skyroot_tree_2 = registerTemplate(4, "highlands/trees/normal/green/green_skyroot_tree_2");
		green_skyroot_tree_3 = registerTemplate(5, "highlands/trees/normal/green/green_skyroot_tree_3");

		green_skyroot_oak_1 = registerTemplate(6, "highlands/trees/oak/green/green_skyroot_oak_1");
		green_skyroot_oak_2 = registerTemplate(7, "highlands/trees/oak/green/green_skyroot_oak_2");

		golden_oak_1 = registerTemplate(8, "highlands/trees/oak/golden/golden_oak_1");
		golden_oak_2 = registerTemplate(9, "highlands/trees/oak/golden/golden_oak_2");

		green_skyroot_windswept_1 = registerTemplate(10, "highlands/trees/windswept/green/green_skyroot_windswept_1");
		green_skyroot_windswept_2 = registerTemplate(11, "highlands/trees/windswept/green/green_skyroot_windswept_2");
		green_skyroot_windswept_3 = registerTemplate(12, "highlands/trees/windswept/green/green_skyroot_windswept_3");
		green_skyroot_windswept_4 = registerTemplate(13, "highlands/trees/windswept/green/green_skyroot_windswept_4");
		green_skyroot_windswept_5 = registerTemplate(14, "highlands/trees/windswept/green/green_skyroot_windswept_5");
		green_skyroot_windswept_6 = registerTemplate(15, "highlands/trees/windswept/green/green_skyroot_windswept_6");
		green_skyroot_windswept_7 = registerTemplate(16, "highlands/trees/windswept/green/green_skyroot_windswept_7");

		skyroot_moa_nest_tree_1 = registerTemplate(17, "moa_nest/skyroot_moa_nest_tree_1");
		skyroot_moa_nest_1 = registerTemplate(18, "moa_nest/skyroot_moa_nest_1");
		skyroot_moa_nest_2 = registerTemplate(19, "moa_nest/skyroot_moa_nest_2");

		large_green_skyroot_pine_1 = registerTemplate(20, "highlands/trees/pine/green/large_green_skyroot_pine_1");
		large_green_skyroot_pine_2 = registerTemplate(21, "highlands/trees/pine/green/large_green_skyroot_pine_2");

		green_skyroot_pine_1 = registerTemplate(22, "highlands/trees/pine/green/green_skyroot_pine_1");
		green_skyroot_pine_2 = registerTemplate(23, "highlands/trees/pine/green/green_skyroot_pine_2");
		green_skyroot_pine_3 = registerTemplate(24, "highlands/trees/pine/green/green_skyroot_pine_3");
		green_skyroot_pine_4 = registerTemplate(25, "highlands/trees/pine/green/green_skyroot_pine_4");
		green_skyroot_pine_5 = registerTemplate(26, "highlands/trees/pine/green/green_skyroot_pine_5");

		dark_blue_skyroot_tree_1 = registerTemplate(27, "highlands/trees/normal/dark_blue/dark_blue_skyroot_tree_1");
		dark_blue_skyroot_tree_2 = registerTemplate(28, "highlands/trees/normal/dark_blue/dark_blue_skyroot_tree_2");
		dark_blue_skyroot_tree_3 = registerTemplate(29, "highlands/trees/normal/dark_blue/dark_blue_skyroot_tree_3");

		dark_blue_skyroot_oak_1 = registerTemplate(30, "highlands/trees/oak/dark_blue/dark_blue_skyroot_oak_1");
		dark_blue_skyroot_oak_2 = registerTemplate(31, "highlands/trees/oak/dark_blue/dark_blue_skyroot_oak_2");

		aether_portal = registerTemplate(32, "aether_portal");
		nether_portal = registerTemplate(33, "nether_portal");
		end_portal = registerTemplate(34, "end_portal");

		mysterious_henge = registerTemplate(35, "mysterious_henge");

		outpost_a = registerTemplate(36, "outpost_a");
		outpost_b = registerTemplate(37, "outpost_b");
		outpost_c = registerTemplate(38, "outpost_c");
	}

	private static Template registerTemplate(final int id, final String resource)
	{
		return registerTemplate(id, AetherCore.getResource(resource));
	}

	public static Template registerTemplate(final int id, final ResourceLocation resourceLocation)
	{
		final Template template = TemplatesAether.MANAGER.getTemplate(null, resourceLocation);

		//AetherCore.PROXY.content().templates().register(id, template);

		return template;
	}

}
