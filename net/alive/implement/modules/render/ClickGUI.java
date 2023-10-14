package net.alive.implement.modules.render;

import net.alive.Client;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.alive.api.value.Value;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "Click GUI", displayName = "Click GUI", keyBind = Keyboard.KEY_RSHIFT, category = Category.RENDER)
public class ClickGUI extends Module {

    public static Value<String> mode = new Value<>("Mode", "New", "New", "Old");
    public static Value<Boolean> blur = new Value<>("Blur", true);

    @Override
    public void onEnable() {
        switch (getMode("Mode")) {
            case "New":
                mc.displayGuiScreen(Client.INSTANCE.getNewClickGUI());
                break;
            case "Old":
                mc.displayGuiScreen(Client.INSTANCE.getClickGUI());
                break;
        }
        toggle();
        super.onEnable();
    }
}
