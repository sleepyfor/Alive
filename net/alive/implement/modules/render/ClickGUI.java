package net.alive.implement.modules.render;

import net.alive.Client;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "Click GUI", displayName = "Click GUI", keyBind = Keyboard.KEY_RSHIFT, category = Category.RENDER)
public class ClickGUI extends Module {

    @Override
    public void onEnable() {
        mc.displayGuiScreen(Client.INSTANCE.getClickGUI());
        toggle();
        super.onEnable();
    }
}
