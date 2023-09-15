package net.alive.implement.modules.render;

import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "Click GUI", displayName = "Click GUI", keyBind = Keyboard.KEY_G, category = Category.RENDER)
public class ClickGUI extends Module {

    /*@Override
    public void onEnable() {
        mc.displayGuiScreen(new ClickGui());
        setEnabled(false);
        this.onEnable();
    }*/

    @Override
    public void onEnable() {
        mc.displayGuiScreen(new net.alive.api.gui.click.ClickGUI());
        toggle();
        super.onEnable();
    }
}
