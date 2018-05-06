package com.gildedgames.aether.common.patron;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.patron.PatronDetails;
import com.gildedgames.aether.api.patron.PatronRewardRegistry;
import com.gildedgames.aether.api.patron.PatronTier;
import com.gildedgames.aether.api.util.FunctionBoolean;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.patron.armor.PatronRewardArmor;
import net.minecraft.util.ResourceLocation;

import static com.gildedgames.aether.api.patron.PatronUnlocks.hasHadTier;

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

		FunctionBoolean<PatronDetails> gilded = hasHadTier(PatronTier.GILDED);

		r.register(0, new PatronRewardArmor("Sun Spirit", armorIcon("sun_spirit"), () -> "patron/sun_spirit",
				armor("sun_spirit_gloves"),
				armor("sun_spirit_gloves_slim"),
				hasHadTier(PatronTier.ANGEL).or(gilded)));
		r.register(1, new PatronRewardArmor("Slider", armorIcon("slider"), () -> "patron/slider",
				armor("slider_gloves"),
				armor("slider_gloves_slim"),
				hasHadTier(PatronTier.ANGEL).or(gilded)));
		r.register(2, new PatronRewardArmor("Valkyrie Queen", armorIcon("valkyrie_queen"), () -> "patron/valkyrie_queen",
				armor("valkyrie_queen_gloves"),
				armor("valkyrie_queen_gloves_slim"),
				hasHadTier(PatronTier.ANGEL).or(gilded)));
	}
}
