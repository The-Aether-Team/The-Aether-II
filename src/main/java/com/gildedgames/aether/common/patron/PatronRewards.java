package com.gildedgames.aether.common.patron;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.patron.PatronRewardRegistry;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.patron.armor.PatronRewardArmor;
import net.minecraft.util.ResourceLocation;

import static com.gildedgames.aether.api.patron.PatronUnlocks.hasSkin;

public class PatronRewards
{
	public static ResourceLocation armor(String name)
	{
		return AetherCore.getResource("textures/armor/patron/" + name + ".png");
	}

	public static ResourceLocation armorIcon(String name)
	{
		return AetherCore.getResource("textures/patron/armor/" + name + ".png");
	}

	public static void preInit()
	{
		PatronRewardRegistry r = AetherAPI.content().patronRewards();

		r.register(0, new PatronRewardArmor("aether.reward.sun_spirit.name", armorIcon("sun_spirit"), "patron/sun_spirit",
				armor("sun_spirit_gloves"),
				armor("sun_spirit_gloves_slim"),
				hasSkin("sun_spirit")));
		r.register(1, new PatronRewardArmor("aether.reward.slider.name", armorIcon("slider"), "patron/slider",
				armor("slider_gloves"),
				armor("slider_gloves_slim"),
				hasSkin("slider_gloves")));
		r.register(2, new PatronRewardArmor("aether.reward.valkyrie_queen.name", armorIcon("valkyrie_queen"), "patron/valkyrie_queen",
				armor("valkyrie_queen_gloves"),
				armor("valkyrie_queen_gloves_slim"),
				hasSkin("valkyrie_queen")));
	}
}
