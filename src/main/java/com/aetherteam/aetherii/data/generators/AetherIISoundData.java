package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class AetherIISoundData extends SoundDefinitionsProvider {
    public AetherIISoundData(PackOutput output, ExistingFileHelper helper) {
        super(output, AetherII.MODID, helper);
    }

    @Override
    public void registerSounds() {

        //Portal
        this.add(AetherIISoundEvents.BLOCK_AETHER_PORTAL_AMBIENT,
                definition().with(sound("aether_ii:block/portal/hum").attenuationDistance(10))
                        .subtitle("subtitles.aether_ii.block.aether_portal.ambient")
        );
        this.add(AetherIISoundEvents.BLOCK_AETHER_PORTAL_TRAVEL,
                definition().with(sound("aether_ii:block/portal/travel"))
        );
        this.add(AetherIISoundEvents.BLOCK_AETHER_PORTAL_TRIGGER,
                definition().with(sound("aether_ii:block/portal/trigger"))
                        .subtitle("subtitles.aether_ii.block.aether_portal.trigger")
        );
        this.add(AetherIISoundEvents.BLOCK_BLUE_AERCLOUD_BOUNCE,
                definition().with(sound("aether_ii:block/aercloud/blue_aercloud_bounce"))
                        .subtitle("subtitles.aether_ii.block.aercloud.blue_aercloud_bounce")
        );

        this.add(AetherIISoundEvents.ENTITY_PHYG_AMBIENT,
                definition().with(
                        sound("minecraft:mob/pig/say1"),
                        sound("minecraft:mob/pig/say2"),
                        sound("minecraft:mob/pig/say3")
                ).subtitle("subtitles.aether.entity.phyg.ambient")
        );
        this.add(AetherIISoundEvents.ENTITY_PHYG_DEATH,
                definition().with(sound("minecraft:mob/pig/death"))
                        .subtitle("subtitles.aether.entity.phyg.death")
        );
        this.add(AetherIISoundEvents.ENTITY_PHYG_HURT,
                definition().with(
                        sound("minecraft:mob/pig/say1"),
                        sound("minecraft:mob/pig/say2"),
                        sound("minecraft:mob/pig/say3")
                ).subtitle("subtitles.aether.entity.phyg.hurt")
        );
        this.add(AetherIISoundEvents.ENTITY_PHYG_SADDLE,
                definition().with(sound("minecraft:mob/horse/leather"))
                        .subtitle("subtitles.aether.entity.phyg.saddle")
        );
        this.add(AetherIISoundEvents.ENTITY_PHYG_STEP,
                definition().with(
                        sound("minecraft:mob/pig/step1"),
                        sound("minecraft:mob/pig/step2"),
                        sound("minecraft:mob/pig/step3"),
                        sound("minecraft:mob/pig/step4"),
                        sound("minecraft:mob/pig/step5")
                ).subtitle("subtitles.block.generic.footsteps")
        );


        this.add(AetherIISoundEvents.ENTITY_FLYING_COW_AMBIENT,
                definition().with(
                        sound("minecraft:mob/cow/say1"),
                        sound("minecraft:mob/cow/say2"),
                        sound("minecraft:mob/cow/say3"),
                        sound("minecraft:mob/cow/say4")
                ).subtitle("subtitles.aether.entity.flying_cow.ambient")
        );
        this.add(AetherIISoundEvents.ENTITY_FLYING_COW_DEATH,
                definition().with(
                        sound("minecraft:mob/cow/hurt1"),
                        sound("minecraft:mob/cow/hurt2"),
                        sound("minecraft:mob/cow/hurt3")
                ).subtitle("subtitles.aether.entity.flying_cow.death")
        );
        this.add(AetherIISoundEvents.ENTITY_FLYING_COW_HURT,
                definition().with(
                        sound("minecraft:mob/cow/hurt1"),
                        sound("minecraft:mob/cow/hurt2"),
                        sound("minecraft:mob/cow/hurt3")
                ).subtitle("subtitles.aether.entity.flying_cow.hurt")
        );
        this.add(AetherIISoundEvents.ENTITY_FLYING_COW_SADDLE,
                definition().with(sound("minecraft:mob/horse/leather"))
                        .subtitle("subtitles.aether.entity.flying_cow.saddle")
        );
        this.add(AetherIISoundEvents.ENTITY_FLYING_COW_MILK,
                definition().with(
                        sound("minecraft:entity/cow/milk1"),
                        sound("minecraft:entity/cow/milk2"),
                        sound("minecraft:entity/cow/milk3")
                ).subtitle("subtitles.aether.entity.flying_cow.milk")
        );
        this.add(AetherIISoundEvents.ENTITY_FLYING_COW_STEP,
                definition().with(
                        sound("minecraft:mob/cow/step1"),
                        sound("minecraft:mob/cow/step2"),
                        sound("minecraft:mob/cow/step3"),
                        sound("minecraft:mob/cow/step4")
                ).subtitle("subtitles.block.generic.footsteps")
        );

        this.add(AetherIISoundEvents.ENTITY_AERBUNNY_DEATH,
                definition().with(sound("aether_ii:entity/aerbunny/death"))
                        .subtitle("subtitles.aether_ii.entity.aerbunny.death")
        );
        this.add(AetherIISoundEvents.ENTITY_AERBUNNY_HURT,
                definition().with(sound("aether_ii:entity/aerbunny/hurt"))
                        .subtitle("subtitles.aether_ii.entity.aerbunny.hurt")
        );
        this.add(AetherIISoundEvents.ENTITY_AERBUNNY_LIFT,
                definition().with(sound("aether_ii:entity/aerbunny/lift"))
                        .subtitle("subtitles.aether_ii.entity.aerbunny.lift")
        );

        // Music
//        this.add(AetherIISoundEvents.MUSIC_HIGHFIELDS,
//                definition().with(
//                        sound("aether:music/aether1").stream()
//                )
//        );
//        this.add(AetherIISoundEvents.MUSIC_MAGNETIC,
//                definition().with(
//                        sound("aether:music/aether1").stream()
//                )
//        );
//        this.add(AetherIISoundEvents.MUSIC_ARCTIC,
//                definition().with(
//                        sound("aether:music/aether1").stream()
//                )
//        );
//        this.add(AetherIISoundEvents.MUSIC_IRRADIATED,
//                definition().with(
//                        sound("aether:music/aether1").stream()
//                )
//        );
//        this.add(AetherIISoundEvents.MUSIC_AERCLOUD_SEA,
//                definition().with(
//                        sound("aether:music/aether1").stream()
//                )
//        );
    }
}
