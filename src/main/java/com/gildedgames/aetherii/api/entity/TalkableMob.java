package com.gildedgames.aetherii.api.entity;

import com.gildedgames.aetherii.api.TalkableController;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;
import java.util.UUID;

public interface TalkableMob {

	ResourceLocation getTalker();

	TalkableController getTalkableController();

	void setTalkingEntity(Player player);

	Optional<UUID> getTalkingUUID();
}
