package com.gildedgames.aether.common.structure;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.HashMap;

public class StructurePalette
{
	private HashMap<IBlockState, Integer> mappings = new HashMap<>();

	private ArrayList<IBlockState> states = new ArrayList<>();

	public int add(IBlockState state)
	{
		if (!this.mappings.containsKey(state))
		{
			int index = this.mappings.size();

			this.mappings.put(state, index);
			this.states.add(state);

			return index;
		}

		return this.mappings.get(state);
	}

	public IBlockState get(int index)
	{
		return this.states.get(index);
	}

	public NBTBase save()
	{
		NBTTagList list = new NBTTagList();

		for (IBlockState state : this.states)
		{
			list.appendTag(NBTUtil.writeBlockState(new NBTTagCompound(), state));
		}

		return list;
	}

	public void load(NBTBase tag)
	{
		Validate.isTrue(tag instanceof NBTTagList && ((NBTTagList) tag).getTagType() == 10, "Expected NBTTagList<NBTTagCompound>");

		NBTTagList list = (NBTTagList) tag;

		for (int i = 0; i < list.tagCount(); i++)
		{
			this.add(NBTUtil.readBlockState(list.getCompoundTagAt(i)));
		}
	}
}
