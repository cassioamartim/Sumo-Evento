package me.flezy.configuration;

import me.flezy.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class Config {

	private File file;
	private YamlConfiguration configfile;

	public Main main;

	public Config(Main main) {
		this.main = main;
		load();
	}

	private void load() {
		file = new File(main.getDataFolder(), "location.yml");
		if (!main.getDataFolder().exists())
			main.getDataFolder().mkdirs();
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (Exception ignored) {
		}
		configfile = YamlConfiguration.loadConfiguration(file);
	}

	public void save() {
		try {
			configfile.save(file);
		} catch (Exception ignored) {
		}
	}
	public YamlConfiguration config() {
		return configfile;
	}
	public boolean hasLocation(String loc) {
		return configfile.contains("Locations." + loc);
	}

	public void setLocation(Location loc, String locname) {
		configfile.set("Locations." + locname + ".world", loc.getWorld().getName());
		configfile.set("Locations." + locname + ".x", loc.getX());
		configfile.set("Locations." + locname + ".y", loc.getY());
		configfile.set("Locations." + locname + ".z", loc.getZ());
		configfile.set("Locations." + locname + ".yaw", loc.getYaw());
		configfile.set("Locations." + locname + ".pitch", loc.getPitch());
		save();
	}

	public Location getLocation(String locname) {
		String world = configfile.getString("Locations." + locname + ".world");
		double x = configfile.getDouble("Locations." + locname + ".x");
		double y = configfile.getDouble("Locations." + locname + ".y");
		double z = configfile.getDouble("Locations." + locname + ".z");
		float yaw = configfile.getInt("Locations." + locname + ".yaw");
		float pitch = configfile.getInt("Locations." + locname + ".pitch");
		return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);

	}

			//TODO - Sem utilidade.
	public  void TeleportLocation(Player player, String location){
		if(!hasLocation(location)){
			player.sendMessage(new String[]{
					"§cLocalizacao §4" + location + "§c nao foi encontrado.",
					"§aUse /set [loc] para adapitar uma warp."
			});
		}else{
			player.teleport(getLocation(location));
			player.sendMessage("§aVoce foi teleportado para " + location);
		}
	}

}
