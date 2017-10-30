package com.gildedgames.orbis.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class OrbisKeyBindings
{

	public static KeyBinding keyBindControl = new KeyBinding("keybindings.control", Minecraft.IS_RUNNING_ON_MAC ? Keyboard.KEY_LMETA : Keyboard.KEY_LCONTROL,
			"key.categories.misc");

	public static KeyBinding keyBindFindPower = new KeyBinding("keybindings.findPower", Keyboard.KEY_TAB, "key.categories.misc");

	public static KeyBinding keyBindRotate = new KeyBinding("keybindings.rotate", Keyboard.KEY_R, "key.categories.misc");

	public static KeyBinding keyBindDelete = new KeyBinding("keybindings.delete", Keyboard.KEY_DELETE, "key.categories.misc");

	public static KeyBinding keyBindCopy = new KeyBinding("keybindings.copy", Keyboard.KEY_C, "key.categories.misc");

	public static KeyBinding keyBindIncreaseReach = new KeyBinding("keybindings.increaseReach", Keyboard.KEY_ADD, "key.categories.misc");

	public static KeyBinding keyBindDecreaseReach = new KeyBinding("keybindings.decreaseReach", Keyboard.KEY_SUBTRACT, "key.categories.misc");

	public static void init()
	{
		ClientRegistry.registerKeyBinding(keyBindControl);
		ClientRegistry.registerKeyBinding(keyBindFindPower);
		ClientRegistry.registerKeyBinding(keyBindRotate);
		ClientRegistry.registerKeyBinding(keyBindDelete);
		ClientRegistry.registerKeyBinding(keyBindCopy);
		ClientRegistry.registerKeyBinding(keyBindIncreaseReach);
		ClientRegistry.registerKeyBinding(keyBindDecreaseReach);
	}

}
