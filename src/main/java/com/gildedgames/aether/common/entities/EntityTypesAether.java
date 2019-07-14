package com.gildedgames.aether.common.entities;

import com.gildedgames.aether.api.registrar.AbstractRegistrar;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.entities.animals.*;
import com.gildedgames.aether.common.entities.blocks.EntityFloatingBlock;
import com.gildedgames.aether.common.entities.blocks.EntityMovingBlock;
import com.gildedgames.aether.common.entities.characters.*;
import com.gildedgames.aether.common.entities.companions.*;
import com.gildedgames.aether.common.entities.monsters.*;
import com.gildedgames.aether.common.entities.projectiles.EntityBolt;
import com.gildedgames.aether.common.entities.projectiles.EntityDaggerfrostSnowball;
import com.gildedgames.aether.common.entities.projectiles.EntityDart;
import com.gildedgames.aether.common.entities.projectiles.EntityTNTPresent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("aether")
public class EntityTypesAether extends AbstractRegistrar
{
	public static final EntityType<? extends EntityAechorPlant> AECHOR_PLANT = getDefault();

	public static final EntityType<? extends EntityAerbunny> AERBUNNY = getDefault();

	public static final EntityType<? extends EntityAerwhale> AERWHALE = getDefault();

	public static final EntityType<? extends EntityBurrukai> BURRUKAI = getDefault();

	public static final EntityType<? extends EntityCarrionSprout> CARRION_SPROUT = getDefault();

	public static final EntityType<? extends EntityCockatrice> COCKATRICE = getDefault();

	public static final EntityType<? extends EntityEdison> EDISON = getDefault();

	public static final EntityType<? extends EntityGlactrix> GLACTRIX = getDefault();

	public static final EntityType<? extends EntityGlitterwing> GLITTERWING = getDefault();

	public static final EntityType<? extends EntityJosediya> JOSEDIYA = getDefault();

	public static final EntityType<? extends EntityKirrid> KIRRID = getDefault();

	public static final EntityType<? extends EntityMysteriousFigure> MYSTERIOUS_FIGURE = getDefault();

	public static final EntityType<? extends EntityNecromancer> NECROMANCER = getDefault();

	public static final EntityType<? extends EntitySheepuff> SHEEPUFF = getDefault();

	public static final EntityType<? extends EntitySkyrootLizard> SKYROOT_LIZARD = getDefault();

	public static final EntityType<? extends EntitySwet> SWET = getDefault();

	public static final EntityType<? extends EntityTaegore> TAEGORE = getDefault();

	public static final EntityType<? extends EntityTempest> TEMPEST = getDefault();

	public static final EntityType<? extends EntityTivalier> TIVALIER = getDefault();

	public static final EntityType<? extends EntityVaranys> VARANYS = getDefault();

	public static final EntityType<? extends EntityZephyr> ZEPHYR = getDefault();

	public static final EntityType<? extends EntityEtherealWisp> ETHERAL_WISP = getDefault();

	public static final EntityType<? extends EntityFangrin> FANGRIN = getDefault();

	public static final EntityType<? extends EntityFleetingWisp> FLEETING_WISP = getDefault();

	public static final EntityType<? extends EntityFrostpineTotem> FROSTPINE_TOTEM = getDefault();

	public static final EntityType<? extends EntityKraisith> KRAISITH = getDefault();

	public static final EntityType<? extends EntityNexSpirit> NEX_SPIRIT = getDefault();

	public static final EntityType<? extends EntityPinkBabySwet> PINK_BABY_SWET = getDefault();

	public static final EntityType<? extends EntityShadeOfArkenzus> SHADE_OF_ARKENZUS = getDefault();

	public static final EntityType<? extends EntitySoaringWisp> SOARING_WISP = getDefault();

	public static final EntityType<? extends EntityBolt> BOLT = getDefault();

	public static final EntityType<? extends EntityDaggerfrostSnowball> SNOWBALL = getDefault();

	public static final EntityType<? extends EntityDart> DART = getDefault();

	public static final EntityType<? extends EntityFloatingBlock> FLOATING_BLOCK = getDefault();

	public static final EntityType<? extends EntityMoa> MOA = getDefault();

	public static final EntityType<? extends EntityMovingBlock> MOVING_BLOCK = getDefault();

	public static final EntityType<? extends EntityTNTPresent> TNT_PRESENT = getDefault();
}
