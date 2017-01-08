package com.gildedgames.aether.client.gui.menu;

import com.gildedgames.aether.api.capabilites.entity.boss.IBoss;
import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BossBattleOverlay extends GuiFrame
{

	private static final ResourceLocation TEXTURE_BOSS_HP_BAR = AetherCore.getResource("textures/gui/overlay/boss_hp_bar.png");

	private Minecraft mc = Minecraft.getMinecraft();

	private boolean dirty;

	private float currentHealth;

	private float linearDecrement;

	private float healthBeforeAttacked;

	private float healthDifference;

	private int ticksSinceTakenDamage;

	private IBoss<?> boss;

	private final Gui DUMMY_GUI = new Gui();

	public BossBattleOverlay()
	{

	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);
	}

	@Override
	public boolean isEnabled()
	{
		EntityPlayer player = this.mc.thePlayer;

		if (player == null)
		{
			return false;
		}

		if (this.mc.theWorld == null)
		{
			return false;
		}

		PlayerAether playerAether = PlayerAether.getPlayer(player);
		IBoss<?> boss = playerAether.getBossModule().getCurrentBoss();

		return (this.boss = boss) != null;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		if (!this.isEnabled())
		{
			return;
		}

		if (!(this.boss.getBossManager().getEntity() instanceof EntityLivingBase))
		{
			return;
		}

		EntityLivingBase bossMob = (EntityLivingBase) this.boss.getBossManager().getEntity();

		ScaledResolution scaledresolution = new ScaledResolution(this.mc);
		int width = scaledresolution.getScaledWidth();
		String bossTitle = "\247o" + this.boss.getBossManager().getName();
		int nameOffset = this.mc.fontRendererObj.getStringWidth(bossTitle) / 2;
		String bossTypeString = "";
		this.mc.renderEngine.bindTexture(TEXTURE_BOSS_HP_BAR);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float health = (bossMob.getHealth()) / (bossMob.getMaxHealth()) * 256F;
		int offset1 = 35;
		int offset2 = 0;
		int offset4 = 0;
		int x = 0, y = 0;

		/*if (bossMob.getBossType() != null)
		{
			boolean isFinal = bossMob.getBossType() == EnumBossType.BOSS;

			if (isFinal)
			{
				drawTexturedModalRect(width / 2 - 49, 10, 1, 57, 96, 58);
			}

			bossTypeString = "\247o" + (isFinal ? "Final" : "Mini") + " Boss";
			offset1 = isFinal ? 45 : 24;
			offset2 = isFinal ? -10 : 11;
			offset4 = mc.fontRendererObj.getStringWidth(bossTypeString) / 2;
			y = isFinal ? 0 : 14;
		}*/

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		DUMMY_GUI.drawTexturedModalRect(width / 2 - 128, 32 - offset2, x, 28, 256, 14);

		if (bossMob.getHealth() != this.healthBeforeAttacked && !this.dirty)
		{
			this.dirty = true;
			this.currentHealth = bossMob.getHealth();
			this.healthDifference = this.healthBeforeAttacked - this.currentHealth;
			this.linearDecrement = this.healthDifference;
		}

		if (this.dirty)
		{
			float staticHealth = (this.healthBeforeAttacked) / (bossMob.getMaxHealth()) * 256F;

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			DUMMY_GUI.drawTexturedModalRect(width / 2 - 128, 32 - offset2, x, 42, (int) staticHealth, 14);

			if (this.ticksSinceTakenDamage > 25)
			{
				if (this.healthBeforeAttacked > this.currentHealth)
				{
					this.healthBeforeAttacked -= this.linearDecrement / 50.0F;
					this.linearDecrement -= this.healthDifference / 5.0F;
					this.healthDifference /= 5.0F;
				}
				else
				{
					this.dirty = false;
					this.healthDifference = 0;
					this.healthBeforeAttacked = bossMob.getHealth();
					this.ticksSinceTakenDamage = 0;
				}
			}
			else
			{
				this.ticksSinceTakenDamage++;
			}
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		DUMMY_GUI.drawTexturedModalRect(width / 2 - 128, 32 - offset2, x, y, (int) health, 14);
		this.mc.fontRendererObj.drawStringWithShadow(bossTitle, width / 2 - nameOffset, offset1, 0x67ffffff);
		this.mc.fontRendererObj.drawStringWithShadow(bossTypeString, width / 2 - offset4, offset1 + 14, 0x67ffffff);
		GL11.glDisable(GL11.GL_BLEND);
	}

}
