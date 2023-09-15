package net.alive.manager.module;

import lombok.Getter;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.implement.modules.movement.Sprint;
import net.alive.implement.modules.render.ClickGUI;
import net.alive.implement.modules.render.Hud;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ModuleManager {
    private Map<Class, Module> moduleList = new LinkedHashMap<>();

    public ModuleManager() {
        registerModule(Hud.class, new Hud());
        registerModule(Sprint.class, new Sprint());
        registerModule(ClickGUI.class, new ClickGUI());
    }

    public void registerModule(Class modClass, Module module) {
        moduleList.put(modClass, module);
    }

    public Module getModule(Class module) {
        return moduleList.values().stream().filter(mod -> mod.getClass().getName().equalsIgnoreCase(module.getName())).findFirst().orElse(null);
    }

    public List<Module> getModulesByCategory(Category category) {
        List<Module> modules = new ArrayList<>();
        for (Module module : moduleList.values())
            if (module.getCategory() == category)
                modules.add(module);
        return modules;
    }
}
