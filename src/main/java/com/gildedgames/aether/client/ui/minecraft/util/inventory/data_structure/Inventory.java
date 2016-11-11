package com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.common.util.io.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class Inventory<E extends NBT> implements NBT
{
	
	private final int size;
	
	private Class<? extends NBT> cls;

	private E[] elements;
	
	private List<InventoryListener<E>> listeners = new ArrayList<InventoryListener<E>>();

	private Inventory()
	{
		this(null);
	}
	
	public Inventory(Class<? extends NBT> cls)
	{
		this(cls, 9);
	}
	
	@SuppressWarnings("unchecked")
	public Inventory(Class<? extends NBT> cls, int size)
	{
		this.size = size;
		
		if (cls != null)
		{
			this.cls = cls;
			this.elements = (E[]) Array.newInstance(this.cls, this.size);
		}
	}
	
	public void addListener(InventoryListener<E> listener)
	{
		this.listeners.add(listener);
	}
	
	public void removeListener(InventoryListener<E> listener)
	{
		this.listeners.remove(listener);
	}
	
	public int getMaxSize()
	{
		return this.size;
	}

	public int getElementCount()
	{
		int amount = 0;
		
		for (int i = 0; i < this.getMaxSize(); i++)
		{
			if (!this.isEmpty(i))
			{
				amount++;
			}
		}
		
		return amount;
	}
	
	private void notifyListeners(int slotIndex, E element)
	{
		for (InventoryListener<E> listener : this.listeners)
		{
			listener.onChange(slotIndex, element);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void clear()
	{
		for (int slotIndex = 0; slotIndex < this.getMaxSize(); slotIndex++)
		{
			this.notifyListeners(slotIndex, null);
		}
		
		this.elements = (E[]) Array.newInstance(this.cls, this.getMaxSize());
	}
	
	public boolean isEmpty(int slotIndex)
	{
		return this.elements[slotIndex] == null;
	}

	public E getElement(int slotIndex)
	{
		return (E) this.elements[slotIndex];
	}

	public E setElement(E element, int slotIndex)
	{
		final E oldValue = (E) this.elements[slotIndex];
		this.elements[slotIndex] = element;
		
		this.notifyListeners(slotIndex, element);
		
		return oldValue;
	}
	
	public E setElementAtHeld(E element, EntityPlayer player)
	{
		final int slotIndex = this.getHeldElementIndex(player);

		return this.setElement(element, slotIndex);
	}

	public int addElement(E element)
	{
		for (int i = 0; i < this.size; i++)
		{
			final E checkElement = this.elements[i];

			if (checkElement == null)
			{
				this.setElement(element, i);

				return i;
			}
		}
		
		return -1;
	}

	public List<E> getElements()
	{
		List<E> list = new ArrayList<E>();
		
		for (E element : this.elements)
		{
			list.add(element);
		}
		
		return list;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		output.setString("cls", this.cls.getName());

		NBTHelper.fullySerializeArray("elements", this.elements, output);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void read(NBTTagCompound input)
	{
		try
		{
			Class<E> cls = (Class<E>) Class.forName(input.getString("cls"));
			
			this.cls = cls;

			Class<E[]> clsArray = (Class<E[]>) Array.newInstance(cls.getComponentType(), 0).getClass();;

			this.elements = NBTHelper.fullyDeserializeArray("elements", clsArray, input);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public E getHeldElement(EntityPlayer player)
	{
		return this.elements[this.getHeldElementIndex(player)];
	}

	public int getHeldElementIndex(EntityPlayer player)
	{
		return player.inventory.currentItem;
	}
	
	public boolean hasElement(E element)
	{
		for (int i = 0; i < this.size; i++)
		{
			final E checkElement = this.elements[i];

			if (checkElement != null && checkElement.equals(element))
			{
				return true;
			}
		}
		
		return false;
	}

	public int getElementIndex(E element)
	{
		for (int i = 0; i < this.size; i++)
		{
			final E checkElement = this.elements[i];

			if (checkElement != null && checkElement.equals(element))
			{
				return i;
			}
		}
		
		return -1;
	}
	
}
