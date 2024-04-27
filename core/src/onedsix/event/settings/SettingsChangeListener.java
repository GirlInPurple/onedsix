package onedsix.event.settings;

import onedsix.systems.GameSettings;

import java.util.EventListener;

public interface SettingsChangeListener extends EventListener {
    void settingsChanged(SettingsChangeEvent event, GameSettings.SettingsJson sj);
}
