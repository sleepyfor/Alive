package net.alive.manager.module;

import lombok.Getter;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.implement.modules.combat.AntiKnockback;
import net.alive.implement.modules.combat.KillAura;
import net.alive.implement.modules.movement.*;
import net.alive.implement.modules.player.NoFall;
import net.alive.implement.modules.player.TimerModifier;
import net.alive.implement.modules.render.ClickGui;
import net.alive.implement.modules.render.Hud;
import net.alive.implement.modules.world.ChestStealer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ModuleManager {
    private final Map<Class, Module> moduleList = new LinkedHashMap<>();

    public ModuleManager() {
        registerModules();
    }

    public void registerModules() {
        registerCombat();
        registerMovement();
        registerWorld();
        registerPlayer();
        registerRender();
    }

    public void registerCombat() {
        registerModule(new AntiKnockback());
        registerModule(new KillAura());
    }

    public void registerMovement() {
        registerModule(new JumpModifier());
        registerModule(new NoSlowDown());
        registerModule(new Sprint());
        registerModule(new Flight());
        registerModule(new Speed());
    }

    public void registerWorld() {
        registerModule(new ChestStealer());
    }

    public void registerPlayer() {
        registerModule(new TimerModifier());
        registerModule(new NoFall());
    }

    public void registerRender() {
        registerModule(new ClickGui());
        registerModule(new Hud());
    }

    public void registerModule(Module module) {
        moduleList.put(module.getClass(), module);
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
