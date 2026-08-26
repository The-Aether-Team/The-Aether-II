package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.world.AetherIIEnvironmentAttributes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ARGB;
import net.minecraft.util.EasingType;
import net.minecraft.util.TriState;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.attribute.modifier.BooleanModifier;
import net.minecraft.world.attribute.modifier.ColorModifier;
import net.minecraft.world.attribute.modifier.FloatModifier;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.timeline.Timeline;

public class AetherIITimelines {
    public static final ResourceKey<Timeline> HOLY_ISLES_DAY = createKey("holy_isles_day");

    public static int NIGHT_SKY_LIGHT_COLOR = ARGB.colorFromFloat(1.0F, 0.48F, 0.48F, 1.0F);
    public static int NIGHT_FOG_COLOR_MULTIPLIER = ARGB.colorFromFloat(1.0F, 0.06F, 0.06F, 0.09F);
    public static int NIGHT_CLOUD_COLOR_MULTIPLIER = ARGB.colorFromFloat(1.0F, 0.1F, 0.1F, 0.15F);

    private static ResourceKey<Timeline> createKey(String name) {
        return ResourceKey.create(Registries.TIMELINE, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<Timeline> context) {
        HolderGetter<WorldClock> clocks = context.lookup(Registries.WORLD_CLOCK);
        Holder.Reference<WorldClock> overworldClock = clocks.getOrThrow(WorldClocks.OVERWORLD);
        EasingType skyAngleEase = EasingType.symmetricCubicBezier(0.362F, 0.241F);
        context.register(
                HOLY_ISLES_DAY,
                Timeline.builder(overworldClock)
                        .setPeriodTicks(24000)
                        .addTrack(
                                EnvironmentAttributes.SUN_ANGLE,
                                track -> track.setEasing(skyAngleEase)
                                        .addKeyframe(6000, 360.0F)
                                        .addKeyframe(6000, 0.0F)
                        ).addTrack(
                                EnvironmentAttributes.MOON_ANGLE,
                                track -> track.setEasing(skyAngleEase)
                                        .addKeyframe(6000, 540.0F)
                                        .addKeyframe(6000, 180.0F)
                        ).addTrack(
                                EnvironmentAttributes.STAR_ANGLE,
                                track -> track.setEasing(skyAngleEase)
                                        .addKeyframe(6000, 360.0F)
                                        .addKeyframe(6000, 0.0F)
                        ).addModifierTrack(
                                EnvironmentAttributes.FOG_COLOR,
                                ColorModifier.MULTIPLY_RGB,
                                track -> track
                                        .addKeyframe(133, -1)
                                        .addKeyframe(11867, -1)
                                        .addKeyframe(13670, NIGHT_FOG_COLOR_MULTIPLIER)
                                        .addKeyframe(22330, NIGHT_FOG_COLOR_MULTIPLIER)
                        ).addModifierTrack(
                                EnvironmentAttributes.SKY_COLOR,
                                ColorModifier.MULTIPLY_RGB,
                                track -> track
                                        .addKeyframe(133, -1)
                                        .addKeyframe(11867, -1)
                                        .addKeyframe(13670, -16777216)
                                        .addKeyframe(22330, -16777216)
                        ).addModifierTrack(
                                EnvironmentAttributes.SKY_LIGHT_COLOR,
                                ColorModifier.MULTIPLY_RGB,
                                track -> track
                                        .addKeyframe(730, -1)
                                        .addKeyframe(11270, -1)
                                        .addKeyframe(13140, NIGHT_SKY_LIGHT_COLOR)
                                        .addKeyframe(22860, NIGHT_SKY_LIGHT_COLOR)
                        ).addModifierTrack(
                                EnvironmentAttributes.SKY_LIGHT_FACTOR,
                                FloatModifier.MULTIPLY,
                                track -> track
                                        .addKeyframe(730, 1.0F)
                                        .addKeyframe(11270, 1.0F)
                                        .addKeyframe(13140, 0.24F)
                                        .addKeyframe(22860, 0.24F)
                        ).addModifierTrack(
                                EnvironmentAttributes.SKY_LIGHT_LEVEL,
                                FloatModifier.MULTIPLY,
                                track -> track
                                        .addKeyframe(133, 1.0F)
                                        .addKeyframe(11867, 1.0F)
                                        .addKeyframe(13670, 0.26666668F)
                                        .addKeyframe(22330, 0.26666668F)
                        ).addTrack(
                                EnvironmentAttributes.SUNRISE_SUNSET_COLOR,
                                track -> track
                                        .addKeyframe(71, 0x5FE2AF66)
                                        .addKeyframe(310, 0x29E8C666)
                                        .addKeyframe(565, 0x06EEE066)
                                        .addKeyframe(730, 0x00F2F266)
                                        .addKeyframe(11270, 0x00F2F266)
                                        .addKeyframe(11397, 0x04EFE466)
                                        .addKeyframe(11522, 0x0FECD766)
                                        .addKeyframe(11690, 0x29E8C666)
                                        .addKeyframe(11929, 0x5FE2AF66)
                                        .addKeyframe(12243, 0xB0DA9466)
                                        .addKeyframe(12358, 0xCBD78A66)
                                        .addKeyframe(12512, 0xE9D37F66)
                                        .addKeyframe(12613, 0xF6D07766)
                                        .addKeyframe(12732, 0xFECD6F66)
                                        .addKeyframe(12841, 0xFECA6866)
                                        .addKeyframe(13035, 0xECC55E66)
                                        .addKeyframe(13252, 0xC1BF5366)
                                        .addKeyframe(13775, 0x36B14366)
                                        .addKeyframe(13888, 0x1FAE4166)
                                        .addKeyframe(14039, 0x09AA4066)
                                        .addKeyframe(14192, 0x00A63F66)
                                        .addKeyframe(21807, 0x00A63F66)
                                        .addKeyframe(21961, 0x09AA4066)
                                        .addKeyframe(22112, 0x1FAE4166)
                                        .addKeyframe(22225, 0x36B14366)
                                        .addKeyframe(22748, 0xC0BF5366)
                                        .addKeyframe(22965, 0xECC55E66)
                                        .addKeyframe(23159, 0xFECA6866)
                                        .addKeyframe(23272, 0xFECD6F66)
                                        .addKeyframe(23488, 0xE9D37E66)
                                        .addKeyframe(23642, 0xCCD78A66)
                                        .addKeyframe(23757, 0xB1DA9466)
                        ).addTrack(
                                AetherIIEnvironmentAttributes.CLOUD_COVER_COLOR.get(),
                                track -> track
                                        .addKeyframe(71, 0xF8ECDC)
                                        .addKeyframe(310, 0xFDFBF4)
                                        .addKeyframe(565, 0xFEFEFE)
                                        .addKeyframe(730, 0xFFFFFF)
                                        .addKeyframe(11270, 0xFFFFFF)
                                        .addKeyframe(11397, 0xFEFEFE)
                                        .addKeyframe(11522, 0xFEFEFC)
                                        .addKeyframe(11690, 0xFDFBF4)
                                        .addKeyframe(11929, 0xF8EDDC)
                                        .addKeyframe(12243, 0xE4BEA3)
                                        .addKeyframe(12358, 0xDBB0A3)
                                        .addKeyframe(12512, 0xCFA0A3)
                                        .addKeyframe(12613, 0xC7959F)
                                        .addKeyframe(12732, 0xBE8896)
                                        .addKeyframe(12841, 0xB47D90)
                                        .addKeyframe(13035, 0x9E6C84)
                                        .addKeyframe(13252, 0x7F5570)
                                        .addKeyframe(13775, 0x321E31)
                                        .addKeyframe(13888, 0x2B1C2E)
                                        .addKeyframe(14039, 0x21192A)
                                        .addKeyframe(14192, 0x181726)
                                        .addKeyframe(21807, 0x181726)
                                        .addKeyframe(21961, 0x21192A)
                                        .addKeyframe(22112, 0x2B1C2E)
                                        .addKeyframe(22225, 0x321E31)
                                        .addKeyframe(22748, 0x7F5570)
                                        .addKeyframe(22965, 0x9E6C84)
                                        .addKeyframe(23159, 0xB47D90)
                                        .addKeyframe(23272, 0xBE8897)
                                        .addKeyframe(23488, 0xCF9FA3)
                                        .addKeyframe(23642, 0xDBB0A3)
                                        .addKeyframe(23757, 0xE4BEA3)
                        ).addModifierTrack(
                                EnvironmentAttributes.STAR_BRIGHTNESS,
                                FloatModifier.MAXIMUM,
                                track -> track
                                        .addKeyframe(92, 0.037F)
                                        .addKeyframe(627, 0.0F)
                                        .addKeyframe(11373, 0.0F)
                                        .addKeyframe(11732, 0.016F)
                                        .addKeyframe(11959, 0.044F)
                                        .addKeyframe(12399, 0.143F)
                                        .addKeyframe(12729, 0.258F)
                                        .addKeyframe(13228, 0.5F)
                                        .addKeyframe(22772, 0.5F)
                                        .addKeyframe(23032, 0.364F)
                                        .addKeyframe(23356, 0.225F)
                                        .addKeyframe(23758, 0.101F)
                        ).addModifierTrack(
                                EnvironmentAttributes.CLOUD_COLOR,
                                ColorModifier.MULTIPLY_ARGB,
                                track -> track
                                        .addKeyframe(133, -1)
                                        .addKeyframe(11867, -1)
                                        .addKeyframe(13670, NIGHT_CLOUD_COLOR_MULTIPLIER)
                                        .addKeyframe(22330, NIGHT_CLOUD_COLOR_MULTIPLIER)
                        ).addModifierTrack(
                                EnvironmentAttributes.FIREFLY_BUSH_SOUNDS,
                                BooleanModifier.OR,
                                track -> track
                                        .addKeyframe(12600, true)
                                        .addKeyframe(23401, false)
                        ).addTrack(
                                EnvironmentAttributes.EYEBLOSSOM_OPEN,
                                track -> track
                                        .addKeyframe(12600, TriState.TRUE)
                                        .addKeyframe(23401, TriState.FALSE)
                        ).addModifierTrack(
                                EnvironmentAttributes.CREAKING_ACTIVE,
                                BooleanModifier.OR,
                                track -> track
                                        .addKeyframe(12600, true)
                                        .addKeyframe(23401, false)
                        ).addModifierTrack(
                                EnvironmentAttributes.TURTLE_EGG_HATCH_CHANCE,
                                FloatModifier.MAXIMUM,
                                track -> track
                                        .setEasing(EasingType.CONSTANT)
                                        .addKeyframe(21062, 1.0F)
                                        .addKeyframe(21905, 0.002F)
                        ).addModifierTrack(
                                EnvironmentAttributes.CAT_WAKING_UP_GIFT_CHANCE,
                                FloatModifier.MAXIMUM,
                                track -> track
                                        .setEasing(EasingType.CONSTANT)
                                        .addKeyframe(362, 0.0F)
                                        .addKeyframe(23667, 0.7F)
                        ).addModifierTrack(
                                EnvironmentAttributes.BEES_STAY_IN_HIVE,
                                BooleanModifier.OR,
                                track -> track
                                        .addKeyframe(12542, true)
                                        .addKeyframe(23460, false)
                        ).addModifierTrack(
                                EnvironmentAttributes.MONSTERS_BURN,
                                BooleanModifier.OR, track -> track
                                        .addKeyframe(12542,
                                                false)
                                        .addKeyframe(23460, true)
                        ).build()
        );
    }
}
