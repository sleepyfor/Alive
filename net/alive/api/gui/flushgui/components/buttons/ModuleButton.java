package net.alive.api.gui.flushgui.components.buttons;

import net.alive.api.gui.flushgui.FlushGUI;
import net.alive.api.gui.flushgui.FlushUtils;
import net.alive.api.gui.flushgui.components.Component;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.implement.modules.render.Hud;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class ModuleButton extends Component {

    public boolean hovered, binding;
    public FlushUtils animation;
    public FlushGUI parent;
    public Module module;

    public ModuleButton(Module module, FlushGUI parent) {
        animation = new FlushUtils();
        this.module = module;
        this.parent = parent;
    }

    @Override
    public void drawComponent() {
        int color = new Color(Hud.red.getValueObject().intValue(), Hud.green.getValueObject().intValue(), Hud.blue.getValueObject().intValue(), 185).getRGB();
        animation.drawAnimatedButton(module.getName() + " [" + (binding ? "..." : Keyboard.getKeyName(module.getKeybind())) + "]" , x, y, width,
                height, new Color(255,255,255,185).getRGB(), color, module.isEnabled(), hovered);
    }
}
