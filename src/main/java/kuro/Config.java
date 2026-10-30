package kuro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;

/** Kalici ayarlar (kuro_settings.txt). Tuslar eskisi gibi kuro_keys.txt icinde. */
public final class Config {
   public static int accent = 0;
   public static boolean watermark = true;
   public static boolean moduleList = true;
   public static boolean keybinds = true;
   public static boolean info = true;
   public static boolean toasts = true;

   public static float flyH = 0.8F;
   public static float flyV = 0.5F;
   public static float freecamSpeed = 0.6F;
   public static float crystalPlace = 1.0F;
   public static float crystalBreak = 0.0F;
   public static float anchorDelay = 2.0F;

   private Config() {
   }

   static Path file() {
      return FabricLoader.getInstance().getGameDir().resolve("kuro_settings.txt");
   }

   public static void load() {
      Path f = file();
      if (!Files.exists(f)) {
         return;
      }

      try {
         for (String line : Files.readAllLines(f)) {
            String[] p = line.trim().split("=");
            if (p.length != 2) {
               continue;
            }

            String k = p[0].trim();
            String v = p[1].trim();
            try {
               switch (k) {
                  case "accent" -> accent = Math.max(0, Math.min(7, Integer.parseInt(v)));
                  case "watermark" -> watermark = Boolean.parseBoolean(v);
                  case "moduleList" -> moduleList = Boolean.parseBoolean(v);
                  case "keybinds" -> keybinds = Boolean.parseBoolean(v);
                  case "info" -> info = Boolean.parseBoolean(v);
                  case "toasts" -> toasts = Boolean.parseBoolean(v);
                  case "flyH" -> flyH = clamp(Float.parseFloat(v), 0.1F, 2.0F);
                  case "flyV" -> flyV = clamp(Float.parseFloat(v), 0.1F, 1.5F);
                  case "freecamSpeed" -> freecamSpeed = clamp(Float.parseFloat(v), 0.1F, 3.0F);
                  case "crystalPlace" -> crystalPlace = clamp(Float.parseFloat(v), 0.0F, 6.0F);
                  case "crystalBreak" -> crystalBreak = clamp(Float.parseFloat(v), 0.0F, 6.0F);
                  case "anchorDelay" -> anchorDelay = clamp(Float.parseFloat(v), 1.0F, 8.0F);
                  default -> {
                  }
               }
            } catch (NumberFormatException ignored) {
            }
         }
      } catch (IOException ignored) {
      }
   }

   public static void save() {
      String s = "accent=" + accent + "\n"
         + "watermark=" + watermark + "\n"
         + "moduleList=" + moduleList + "\n"
         + "keybinds=" + keybinds + "\n"
         + "info=" + info + "\n"
         + "toasts=" + toasts + "\n"
         + "flyH=" + flyH + "\n"
         + "flyV=" + flyV + "\n"
         + "freecamSpeed=" + freecamSpeed + "\n"
         + "crystalPlace=" + crystalPlace + "\n"
         + "crystalBreak=" + crystalBreak + "\n"
         + "anchorDelay=" + anchorDelay + "\n";
      try {
         Files.writeString(file(), s);
      } catch (IOException ignored) {
      }
   }

   static float clamp(float v, float a, float b) {
      return Math.max(a, Math.min(b, v));
   }
}
