package com.gildedgames.orbis.common.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.common.network.packets.PacketChangeSelectionType;
import com.gildedgames.orbis.common.player.godmode.selection_types.*;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Collection;

public class PlayerSelectionTypesModule extends PlayerAetherModule
{
	private final ISelectionType[] selectionTypes;

	private final SelectionTypeCuboid cuboid;

	private final SelectionTypeSphere sphere;

	private final SelectionTypeLine line;

	private final SelectionTypePyramid pyramid;

	private final SelectionTypeCone cone;

	private final SelectionTypeCylinder cylinder;

	private final SelectionTypeDome dome;

	private int currentSelectionTypeIndex;

	public PlayerSelectionTypesModule(final PlayerAether playerAether)
	{
		super(playerAether);

		this.cuboid = new SelectionTypeCuboid();
		this.sphere = new SelectionTypeSphere();
		this.line = new SelectionTypeLine();
		this.pyramid = new SelectionTypePyramid();
		this.cone = new SelectionTypeCone();
		this.cylinder = new SelectionTypeCylinder();
		this.dome = new SelectionTypeDome();

		final Collection<ISelectionType> selectionTypes = new ArrayList<>();

		selectionTypes.add(this.cuboid);
		selectionTypes.add(this.sphere);
		selectionTypes.add(this.line);
		selectionTypes.add(this.pyramid);
		selectionTypes.add(this.cylinder);
		selectionTypes.add(this.cone);
		selectionTypes.add(this.dome);

		this.selectionTypes = selectionTypes.toArray(new ISelectionType[selectionTypes.size()]);
	}

	public SelectionTypeDome getDome()
	{
		return this.dome;
	}

	public SelectionTypeCylinder getCylinder()
	{
		return this.cylinder;
	}

	public SelectionTypeCone getCone()
	{
		return this.cone;
	}

	public SelectionTypePyramid getPyramid()
	{
		return this.pyramid;
	}

	public SelectionTypeCuboid getCuboid()
	{
		return this.cuboid;
	}

	public SelectionTypeSphere getSphere()
	{
		return this.sphere;
	}

	public SelectionTypeLine getLine()
	{
		return this.line;
	}

	public ISelectionType getCurrentSelectionType()
	{
		return this.selectionTypes[this.currentSelectionTypeIndex];
	}

	public void setCurrentSelectionType(final Class<? extends ISelectionType> clazz)
	{
		int foundIndex = -1;

		for (int i = 0; i < this.selectionTypes.length; i++)
		{
			final ISelectionType s = this.selectionTypes[i];

			if (clazz.isAssignableFrom(s.getClass()))
			{
				foundIndex = i;
				break;
			}
		}

		if (foundIndex != -1)
		{
			this.currentSelectionTypeIndex = foundIndex;

			if (this.getWorld().isRemote)
			{
				NetworkingAether.sendPacketToServer(new PacketChangeSelectionType(this.currentSelectionTypeIndex));
			}
		}
	}

	public void setCurrentSelectionType(int powerIndex)
	{
		this.currentSelectionTypeIndex = powerIndex;

		if (this.getWorld().isRemote)
		{
			NetworkingAether.sendPacketToServer(new PacketChangeSelectionType(this.currentSelectionTypeIndex));
		}
	}

	public int getSelectionTypeIndex(final Class<? extends ISelectionType> clazz)
	{
		int foundIndex = -1;

		for (int i = 0; i < this.selectionTypes.length; i++)
		{
			final ISelectionType s = this.selectionTypes[i];

			if (clazz.isAssignableFrom(s.getClass()))
			{
				foundIndex = i;
				break;
			}
		}

		return foundIndex;
	}

	public boolean isCurrentSelectionType(final ISelectionType selectionType)
	{
		return this.getCurrentSelectionType() == selectionType;
	}

	public ISelectionType[] array()
	{
		return this.selectionTypes;
	}

	@Override
	public void onUpdate()
	{

	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTTagCompound modules = new NBTTagCompound();

		for (final ISelectionType s : this.selectionTypes)
		{
			s.write(modules);
		}

		tag.setTag("selectionTypes", modules);
		tag.setInteger("currentSelectionTypeIndex", this.currentSelectionTypeIndex);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTTagCompound modules = tag.getCompoundTag("selectionTypes");

		for (final ISelectionType s : this.selectionTypes)
		{
			s.read(modules);
		}

		this.currentSelectionTypeIndex = tag.getInteger("currentSelectionTypeIndex");
	}
}
