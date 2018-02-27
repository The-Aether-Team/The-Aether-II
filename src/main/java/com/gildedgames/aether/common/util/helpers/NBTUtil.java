package com.gildedgames.aether.common.util.helpers;

import com.google.common.collect.AbstractIterator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Iterator;

public class NBTUtil
{

	public static NBTTagList getTagList(final NBTTagCompound tag, final String key)
	{
		return tag.getTagList(key, 10);
	}

	public static Iterable<NBTTagCompound> getIterator(final NBTTagCompound tag, final String tagListKey)
	{
		return getIterator(getTagList(tag, tagListKey));
	}

	/**
	 * Get the iterator for a taglist in an NBTTagCompound.
	 * Simply a nice shortcut method.
	 */
	public static Iterable<NBTTagCompound> getIterator(final NBTTagList tagList)
	{
		return new Iterable<NBTTagCompound>()
		{
			@Override
			public Iterator<NBTTagCompound> iterator()
			{
				return new AbstractIterator<NBTTagCompound>()
				{
					private int i = 0;

					@Override
					protected NBTTagCompound computeNext()
					{
						if (this.i >= tagList.tagCount())
						{
							return this.endOfData();
						}

						final NBTTagCompound tag = tagList.getCompoundTagAt(this.i);
						this.i++;
						return tag;
					}
				};
			}
		};
	}

}
