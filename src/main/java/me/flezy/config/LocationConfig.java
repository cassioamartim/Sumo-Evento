package me.flezy.config;

import lombok.Getter;
import lombok.SneakyThrows;
import me.flezy.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@Getter
public class LocationConfig {

    private transient final String LOCATION_KEY = "location.%s";

    private File file;
    private YamlConfiguration yamlConfig;

    public Main main;

    public LocationConfig(Main main) {
        this.main = main;
        load();
    }

    @SneakyThrows
    private void load() {
        file = new File(main.getDataFolder(), "location.yml");

        if (!main.getDataFolder().exists())
            main.getDataFolder().mkdirs();

        if (!file.exists())
            file.createNewFile();

        yamlConfig = YamlConfiguration.loadConfiguration(file);
    }

    @SneakyThrows
    public void save() {
        yamlConfig.save(file);
    }

    public boolean hasLocation(String key) {
        return yamlConfig.contains(String.format(LOCATION_KEY, key));
    }

    public void setLocation(Location location, String key) {
        String formattedKey = String.format(LOCATION_KEY, key);

        yamlConfig.set(formattedKey + ".world", location.getWorld().getName());
        yamlConfig.set(formattedKey + ".x", location.getX());
        yamlConfig.set(formattedKey + ".y", location.getY());
        yamlConfig.set(formattedKey + ".z", location.getZ());
        yamlConfig.set(formattedKey + ".yaw", location.getYaw());
        yamlConfig.set(formattedKey + ".pitch", location.getPitch());

        save();
    }

    public Location getLocation(String key) {
        String formattedKey = String.format(LOCATION_KEY, key);

        String world = yamlConfig.getString(formattedKey + ".world");

        double x = yamlConfig.getDouble(formattedKey + ".x");
        double y = yamlConfig.getDouble(formattedKey + ".y");
        double z = yamlConfig.getDouble(formattedKey + ".z");

        float yaw = yamlConfig.getInt(formattedKey + ".yaw");
        float pitch = yamlConfig.getInt(formattedKey + ".pitch");

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);

    }
}
