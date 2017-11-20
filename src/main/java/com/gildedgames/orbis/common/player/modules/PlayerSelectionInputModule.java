package com.gildedgames.orbis.common.player.modules;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.common.network.packets.PacketChangeSelectionInput;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selection_input.ISelectionInput;
import com.gildedgames.orbis.common.player.godmode.selection_input.SelectionInputBrush;
import com.gildedgames.orbis.common.player.godmode.selection_input.SelectionInputDragged;
import com.gildedgames.orbis.common.player.godmode.selectors.IShapeSelector;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Collection;

public class PlayerSelectionInputModule extends PlayerAetherModule
{
	private final ISelectionInput[] selectionInputs;

	private final SelectionInputDragged non_uniform;

	private final SelectionInputDragged uniform;

	private final SelectionInputDragged centered;

	private final SelectionInputBrush brush;

	private final PlayerOrbisModule module;

	private int currentSelectionTypeIndex;

	public PlayerSelectionInputModule(final PlayerOrbisModule module)
	{
		super(module.getPlayer());

		this.module = module;

		this.non_uniform = new SelectionInputDragged(this.getEntity(), "Non-Uniform",
				AetherCore.getResource("orbis/godmode/selection_mode_icons/non_uniform.png")).createFromCenter(false).uniform(false);
		this.uniform = new SelectionInputDragged(this.getEntity(), "Uniform",
				AetherCore.getResource("orbis/godmode/selection_mode_icons/uniform.png")).createFromCenter(false).uniform(true);
		this.centered = new SelectionInputDragged(this.getEntity(), "Centered",
				AetherCore.getResource("orbis/godmode/selection_mode_icons/centered.png")).createFromCenter(true).uniform(true);
		this.brush = new SelectionInputBrush(module, module.getWorld());

		final Collection<ISelectionInput> selectionInputs = new ArrayList<>();

		selectionInputs.add(this.non_uniform);
		selectionInputs.add(this.uniform);
		selectionInputs.add(this.centered);
		selectionInputs.add(this.brush);

		this.selectionInputs = selectionInputs.toArray(new ISelectionInput[selectionInputs.size()]);
	}

	public SelectionInputDragged getNonUniform()
	{
		return this.non_uniform;
	}

	public SelectionInputDragged getUniform()
	{
		return this.uniform;
	}

	public SelectionInputDragged getCentered()
	{
		return this.centered;
	}

	public SelectionInputBrush getBrush()
	{
		return this.brush;
	}

	public int getSelectionInputIndex(final ISelectionInput input)
	{
		int foundIndex = -1;

		for (int i = 0; i < this.selectionInputs.length; i++)
		{
			final ISelectionInput s = this.selectionInputs[i];

			if (input == s)
			{
				foundIndex = i;
				break;
			}
		}

		return foundIndex;
	}

	public ISelectionInput getCurrentSelectionInput()
	{
		return this.selectionInputs[this.currentSelectionTypeIndex];
	}

	public void setCurrentSelectionInput(final int index)
	{
		this.currentSelectionTypeIndex = index;

		if (this.getWorld().isRemote)
		{
			NetworkingAether.sendPacketToServer(new PacketChangeSelectionInput(this.currentSelectionTypeIndex));
		}
	}

	public boolean isCurrentSelectionInput(final ISelectionInput selectionType)
	{
		return this.getCurrentSelectionInput() == selectionType;
	}

	public ISelectionInput[] array()
	{
		return this.selectionInputs;
	}

	@Override
	public void onUpdate()
	{
		IShapeSelector selector = this.module.powers().getCurrentPower().getShapeSelector();
		final ItemStack held = this.module.powers().getEntity().getHeldItemMainhand();

		if (held.getItem() instanceof IShapeSelector)
		{
			selector = (IShapeSelector) held.getItem();
		}

		for (final ISelectionInput s : this.array())
		{
			s.onUpdate(s == this.getCurrentSelectionInput(), selector);
		}
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTTagCompound modules = new NBTTagCompound();

		for (final ISelectionInput s : this.selectionInputs)
		{
			s.write(modules);
		}

		tag.setTag("selectionInputs", modules);
		tag.setInteger("currentSelectionInputIndex", this.currentSelectionTypeIndex);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTTagCompound modules = tag.getCompoundTag("selectionInputs");

		for (final ISelectionInput s : this.selectionInputs)
		{
			s.read(modules);
		}

		this.currentSelectionTypeIndex = tag.getInteger("currentSelectionInputIndex");
	}
}
