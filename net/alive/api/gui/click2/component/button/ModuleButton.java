package net.alive.api.gui.click2.component.button;

import net.alive.api.gui.click2.component.Component;
import net.alive.api.module.Module;

public class ModuleButton extends Component {
    public Module module;
    public boolean binding;

    public ModuleButton(Module module){
        this.module = module;
    }
}
