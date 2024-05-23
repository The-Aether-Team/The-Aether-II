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
