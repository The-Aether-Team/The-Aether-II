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
