package net.alive.api.file;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import net.alive.Client;
import net.alive.api.module.Module;
import net.alive.api.value.Value;
import net.alive.manager.module.ModuleManager;

import java.io.*;

public class Config {

    private Gson gson = new Gson();

    public void createDirectory() {
        Client.INSTANCE.getDIR().mkdirs();
    }

    public void saveConfig() throws IOException {
        File configFile = new File(Client.INSTANCE.getDIR(), "Configuration.json");
        if (!configFile.exists()) configFile.createNewFile();
        JsonObject config = new JsonObject();
        for (Module module : Client.INSTANCE.getModuleManager().getModuleList().values()) {
            JsonObject modules = new JsonObject();
            modules.addProperty("Enabled", module.isEnabled());
            modules.addProperty("Keybind", module.getKeybind());
            for (Value value : module.getValues()) {
                if (value.getValueObject() instanceof Boolean)
                    modules.addProperty(value.getValueName(), (Boolean) value.getValueObject());
                if (value.getValueObject() instanceof String)
                    modules.addProperty(value.getValueName(), (String) value.getValueObject());
                if (value.getValueObject() instanceof Number)
                    modules.addProperty(value.getValueName(), (Number) value.getValueObject());
            }
            config.add(module.getName(), modules);
        }
        PrintWriter printWriter = new PrintWriter(configFile);
        printWriter.println(gson.toJson(config));
        printWriter.flush();
        printWriter.close();
    }

    public void loadConfig() throws IOException {
        File configFile = new File(Client.INSTANCE.getDIR(), "Configuration.json");
        if (!configFile.exists()) {
            configFile.createNewFile();
            saveConfig();
        }
        BufferedReader reader = new BufferedReader(new FileReader(configFile));
        JsonElement json = gson.fromJson(reader, JsonElement.class);
        if (json instanceof JsonNull)
            return;
        JsonObject object = (JsonObject) json;
        for (Module module : Client.INSTANCE.getModuleManager().getModuleList().values()) {
            if (!object.has(module.getName()))
                continue;
            JsonElement modules = object.get(module.getName());
            if (modules instanceof JsonNull)
                return;
            JsonObject moduleObject = (JsonObject) modules;
            if (moduleObject.has("Enabled")) module.setState(moduleObject.get("Enabled").getAsBoolean());
            if (moduleObject.has("Keybind")) module.setKeybind(moduleObject.get("Keybind").getAsInt());
            for (Value value : module.getValues()) {
                if (!moduleObject.has(value.getValueName())) continue;
                if (value.getValueObject() instanceof Boolean)
                    value.setValueObject(moduleObject.get(value.getValueName()).getAsBoolean());
                if (value.getValueObject() instanceof String)
                    value.setValueObject(moduleObject.get(value.getValueName()).getAsString());
                if (value.getValueObject() instanceof Double)
                    value.setValueObject(moduleObject.get(value.getValueName()).getAsDouble());
                if (value.getValueObject() instanceof Integer)
                    value.setValueObject(moduleObject.get(value.getValueName()).getAsInt());
            }
        }
    }
}
