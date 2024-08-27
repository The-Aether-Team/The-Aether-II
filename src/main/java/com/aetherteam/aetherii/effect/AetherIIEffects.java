package com.aetherteam.aetherii.effect;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.effect.harmful.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, AetherII.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> WOUND = EFFECTS.register("wound", WoundEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> STUN = EFFECTS.register("stun", StunEffect::new); //todo
    public static final DeferredHolder<MobEffect, MobEffect> FRACTURE = EFFECTS.register("fracture", FractureEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> AMBROSIUM_POISONING = EFFECTS.register("ambrosium_poisoning", AmbrosiumPoisoningEffect::new); //todo
    public static final DeferredHolder<MobEffect, MobEffect> TOXIN = EFFECTS.register("toxin", ToxinEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> VENOM = EFFECTS.register("venom", VenomEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> CHARGED = EFFECTS.register("charged", ChargedEffect::new); //todo
    public static final DeferredHolder<MobEffect, MobEffect> WEBBED = EFFECTS.register("webbed", WebbedEffect::new); //todo
    public static final DeferredHolder<MobEffect, MobEffect> IMMOLATION = EFFECTS.register("immolation", ImmolationEffect::new); //todo
    public static final DeferredHolder<MobEffect, MobEffect> FROSTBITE = EFFECTS.register("frostbite", FrostbiteEffect::new); //todo
    public static final DeferredHolder<MobEffect, MobEffect> FUNGAL_ROT = EFFECTS.register("fungal_rot", FungalRotEffect::new); //todo
    public static final DeferredHolder<MobEffect, MobEffect> CRYSTALLIZED = EFFECTS.register("crystallized", CrystallizedEffect::new); //todo
}
