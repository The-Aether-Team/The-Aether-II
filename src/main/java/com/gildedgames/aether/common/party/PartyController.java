package com.gildedgames.aether.common.party;

import com.gildedgames.aether.common.player.PlayerAether;

import java.util.HashMap;
import java.util.UUID;

public class PartyController
{
	public static final PartyController INSTANCE = new PartyController();

	private final HashMap<UUID, Party> parties = new HashMap<>();

	public Party getParty(UUID uuid)
	{
		return this.parties.get(uuid);
	}

	public void addParty(Party party)
	{
		this.parties.put(party.getUniqueId(), party);
	}

	public void removeParty(Party party)
	{
		this.parties.remove(party.getUniqueId());
	}

	public Party createParty(PlayerAether player, String name)
	{
		Party party = new Party(UUID.randomUUID());
		party.setName(name);

		PartyMember owner = new PartyMember(player.getUniqueId());
		owner.setMemberRank(MemberRank.OWNER);
		owner.setInfo(PartyMemberInfo.create(player.getEntity()));

		party.addMember(owner);

		return party;
	}
}
