/*
 * MIT License
 *
 * Copyright (c) 2021 Kyle Hicks
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.untouchedodin0.privatemines.utils.placeholderapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.untouchedodin0.privatemines.utils.mine.Mine;
import me.untouchedodin0.privatemines.utils.storage.MineStorage;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PrivateMinesExpansion extends PlaceholderExpansion {

    private final MineStorage mineStorage;
    private Mine mine;
    Map<String, String> placeholders = new HashMap<>();


    public PrivateMinesExpansion(MineStorage mineStorage) {
        this.mineStorage = mineStorage;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "privatemines";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public @NotNull String getAuthor() {
        return "UntouchedOdin0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String placeholder) {
        if (mineStorage.hasMine(player.getPlayer())) {
            mine = mineStorage.getMine(player.getPlayer());
        }
        boolean hasMine = mineStorage.hasMine(player.getPlayer());

        placeholders.putIfAbsent("owner", String.valueOf(hasMine));
        if (hasMine) {
            placeholders.put("location", mine.getMineLocation().toString());
            placeholders.put("spawnlocation", mine.getSpawnLocation().toString());
            placeholders.put("npclocation", mine.getNpcLocation().toString());
            placeholders.put("weightedrandom", mine.getWeightedRandom().toString());
            placeholders.put("cornerlocations", Arrays.toString(mine.getCornerLocations()));
            placeholders.put("owneruuid", mine.getMineOwner().toString());
            placeholders.put("structure", mine.getStructure().getType().getName());
            placeholders.put("cuboidregion", mine.getCuboidRegion().toString());
            placeholders.put("npcrelative", Arrays.toString(mine.getNPCLocationRelative()));
        }
        return placeholders.get(placeholder);
    }
}
