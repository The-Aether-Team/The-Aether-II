package com.aetherteam.aetherii.data.resources.maps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

public record AmberHourglassFuel(int powerTime)  {
    public static final Codec<AmberHourglassFuel> POWER_TIME_CODEC = ExtraCodecs.POSITIVE_INT.xmap(AmberHourglassFuel::new, AmberHourglassFuel::powerTime);
    public static final Codec<AmberHourglassFuel> CODEC = Codec.withAlternative(RecordCodecBuilder.create((instance) -> instance.group(ExtraCodecs.POSITIVE_INT.fieldOf("power_time").forGetter(AmberHourglassFuel::powerTime)).apply(instance, AmberHourglassFuel::new)), POWER_TIME_CODEC);
}
