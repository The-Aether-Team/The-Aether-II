package com.gildedgames.aether.common.entities.util;

import com.gildedgames.aether.api.capabilites.entity.boss.BossStage;
import com.gildedgames.aether.api.capabilites.entity.boss.BossStageAction;
import com.gildedgames.aether.api.capabilites.entity.boss.IBossManager;
import com.gildedgames.util.core.nbt.NBTHelper;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class SimpleBossManager<T extends Entity> implements IBossManager<T>
{

	private List<BossStage<T>> stages = Lists.newArrayList();

	private List<BossStageAction<T>> actions = Lists.newArrayList();

	private T entity;

	private String name;

	private SimpleBossManager()
	{

	}

	public SimpleBossManager(T entity, String name, BossStage<T>... stages)
	{
		this.entity = entity;
		this.name = name;
		this.stages = Lists.newArrayList(stages);
	}

	public void setEntity(T entity)
	{
		this.entity = entity;
	}

	@Override public String getName() { return this.name; }

	@Override public List<BossStage<T>> getStages()
	{
		return this.stages;
	}

	@Override public List<BossStageAction<T>> getActions()
	{
		return this.actions;
	}

	@Override public void addStageAction(BossStageAction<T> action)
	{
		this.actions.add(action);
	}

	@Override public void write(NBTTagCompound output)
	{
		output.setString("name", this.name);
		NBTHelper.fullySerializeList("stages", this.stages, output);
		NBTHelper.fullySerializeList("actions", this.actions, output);
	}

	@Override public void read(NBTTagCompound input)
	{
		this.name = input.getString("name");
		this.stages = NBTHelper.fullyDeserializeList("stages", input);
		this.actions = NBTHelper.fullyDeserializeList("actions", input);
	}

	@Override public void updateStagesAndActions()
	{
		final List<BossStageAction<T>> toRemove = Lists.newArrayList();

		for (BossStageAction<T> action : this.actions)
		{
			action.update(this.entity);

			if (action.shouldRemove())
			{
				toRemove.add(action);
			}
		}

		this.actions.removeAll(toRemove);

		for (BossStage<T> stage : this.stages)
		{
			stage.update(this.entity, this);
		}
	}

	@Override public boolean hasBegun(BossStage stage)
	{
		boolean hasBegun = false;

		for (BossStage<T> itStage : this.stages)
		{
			if (itStage.hasBegun())
			{
				hasBegun = true;
				break;
			}
		}

		return hasBegun;
	}

	@Override public boolean hasBegun(Class<? extends BossStage> stage)
	{
		boolean hasBegun = false;

		for (BossStage<T> itStage : this.stages)
		{
			if (itStage.hasBegun() && itStage.getClass().isAssignableFrom(stage))
			{
				hasBegun = true;
				break;
			}
		}

		return hasBegun;
	}

}
