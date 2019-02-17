package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.capabilities.entity.player.TravelersGuidebook.TGEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;

/**
 * Event's System for the Travellers Guidebook
 */

public class TGEventsModule extends PlayerAetherModule
{
	ArrayList<TGEvent> eventsLangKeys;

	public TGEventsModule(PlayerAether playerAether)
	{
		super(playerAether);

		this.eventsLangKeys = new ArrayList<>();
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void tickEnd(TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void onUpdate()
	{

	}

	/**
	 * Add an event into list of achieved events; handles duplicate events.
	 *
	 * @param key The lang file key for the title
	 * @param descriptionKey The lang file key for the description
	 * @param imageLocation Image texture resource location
	 * @param position Position to add event to, negative number if event should be appended.
	 * @return boolean statement on whether insertion was successful or not.
	 */
	public boolean insertEventIntoList(String key, String descriptionKey, ResourceLocation imageLocation, int position)
	{
		TGEvent newEvent = new TGEvent(key, descriptionKey, imageLocation);

		// Ensure event does not already exist.
		for (TGEvent event : this.eventsLangKeys)
		{
			if (event.compareTo(newEvent) == 0)
			{
				AetherCore.LOGGER.warn("Attempted to add event with title key: " +  key + " but it already exists in list!");
				return false;
			}
		}

		if (position < 0)
		{
			this.eventsLangKeys.add(newEvent);
			return true;
		}

		this.eventsLangKeys.add(position, newEvent);
		return true;
	}

	public TGEvent getEventWithTitleKey(String titleKey)
	{
		for (TGEvent event : this.eventsLangKeys)
		{
			if (event.getTitleKey().equalsIgnoreCase(titleKey))
			{
				return event;
			}
		}

		return null;
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		int i = 0;
		NBTTagCompound root = new NBTTagCompound();
		tag.setTag("TravelersGuidebook_events", root);

		tag.setInteger("TravelersGuidebook_event_size", this.eventsLangKeys.size());

		for (TGEvent event : this.eventsLangKeys)
		{
			tag.setString("TravelersGuidebook_event_title" + i, event.getTitleKey());
			tag.setString("TravelersGuidebook_event_desc" + i, event.getDescriptionKey());
			tag.setString("TravelersGuidebook_event_imagePath" + i, event.getImagePath());

			i++;
		}
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		NBTTagCompound root = tag.getCompoundTag("TravelersGuidebook_events");

		int size = root.getInteger("TravelersGuidebook_event_size");

		for (int i = 0; i < size; i++)
		{
			String titleKey = root.getString("TravelersGuidebook_event_title" + i);
			String descKey = root.getString("TravelersGuidebook_event_desc" + i);
			ResourceLocation image = new ResourceLocation("TravelersGuidebook_event_imagePath" + i);

			this.eventsLangKeys.add(new TGEvent(titleKey,descKey,image));
		}
	}
}
