package net.minecraft.client.gui;

import java.io.IOException;

import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.litarvan.openauth.microsoft.model.response.MinecraftProfile;
import net.alive.Client;
import net.alive.utils.gui.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {
    public boolean doesGuiPauseGame() {
        return false;
    }

    public void initGui() {
        addButtons();
    }

    private void addButtons() {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, 250, "I have no friends..."));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, 274, "Play Online!"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, 298, "Account Login"));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, 322, 98, 20, "Settings"));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 2, 322, 98, 20, "Rage Quit!"));
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        int id = button.id;
        switch (id) {
            case 0:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            case 1:
                this.mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            case 2:
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 3:
                try {
                    MicrosoftAuthenticator microsoftAuthenticator = new MicrosoftAuthenticator();
                    MicrosoftAuthResult result = microsoftAuthenticator.loginWithWebview();
                    MinecraftProfile profile = result.getProfile();
                    mc.session = new Session(profile.getName(), profile.getId(), result.getAccessToken(), "microsoft");
                } catch (MicrosoftAuthenticationException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                Minecraft.getMinecraft().shutdown();
                break;
            case 5:
                this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
                break;
            case 6:
                this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
                break;
            case 7:
                ISaveFormat isaveformat = this.mc.getSaveLoader();
                WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

                if (worldinfo != null) {
                    GuiYesNo guiyesno = GuiSelectWorld.func_152129_a(this, worldinfo.getWorldName(), 12);
                    this.mc.displayGuiScreen(guiyesno);
                }
                break;
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        RenderingUtils.drawImg(new ResourceLocation("images/horizon.jpg"), -100, -100, width + 200, height + 200);
        RenderingUtils.drawImg(new ResourceLocation("icons/logo3.png"), (double) width / 2 - 50, 75,
                100, 100);
        Client.INSTANCE.getArial17().drawStringWithShadow("Alive v" + Client.INSTANCE.getClientVersion() + " by sleepyfor!", 2, scaledResolution.getScaledHeight() - 10, -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
