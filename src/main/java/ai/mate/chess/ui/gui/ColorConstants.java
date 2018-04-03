package ai.mate.chess.ui.gui;


import ai.mate.chess.model.board.Tile;

import java.awt.*;


/**
 * Useful color constants.
 */
public class ColorConstants {
    private static final Color lightBlueFill =  new Color(187, 222, 251);
    private static final Color darkBlueOuter = new Color(150, 219, 239);
    private static final Color lightRedFill = new Color(255, 171, 145);
    private static final Color darkRedOuter = new Color(255, 140, 130);
    private static final Color lightOrangeFill = new Color(255, 208, 109);
    private static final Color darkOrangeOuter = new Color(255, 182, 44);
    private static final Color lightGreenFill = new Color(128,203,196);
    private static final Color darkGreenOuter = new Color(102, 162, 156);
    private static final Color lightYellowFill = new Color(255, 241, 118);
    private static final Color darkYellowOuter = new Color(240, 220, 100);

    private static final Color darkTileColor = new Color(145, 103, 69);
    private static final Color lightTileColor = new Color(230, 179, 136);

    static final Color[] normalColorsLightTile = {
            getSimulatedAlphaColor(Tile.TILE_TYPE.LIGHT, lightBlueFill),
            getSimulatedAlphaColor(Tile.TILE_TYPE.LIGHT, darkBlueOuter)
    };

    static final Color[] normalColorsDarkTile = {
            getSimulatedAlphaColor(Tile.TILE_TYPE.DARK, lightBlueFill),
            getSimulatedAlphaColor(Tile.TILE_TYPE.DARK, darkBlueOuter)
    };

    static final Color[] inCheckColorsLightTile = {
            getSimulatedAlphaColor(Tile.TILE_TYPE.LIGHT, lightOrangeFill),
            getSimulatedAlphaColor(Tile.TILE_TYPE.LIGHT, darkOrangeOuter)
    };

    static final Color[] inCheckColorsDarkTile = {
            getSimulatedAlphaColor(Tile.TILE_TYPE.DARK, lightOrangeFill),
            getSimulatedAlphaColor(Tile.TILE_TYPE.DARK, darkOrangeOuter)
    };

    static final Color[] specialColorsLightTile = {
            getSimulatedAlphaColor(Tile.TILE_TYPE.LIGHT, lightYellowFill),
            getSimulatedAlphaColor(Tile.TILE_TYPE.LIGHT, darkYellowOuter)
    };

    static final Color[] specialColorsDarkTile = {
            getSimulatedAlphaColor(Tile.TILE_TYPE.DARK, lightYellowFill),
            getSimulatedAlphaColor(Tile.TILE_TYPE.DARK, darkYellowOuter)};

    static final Color[] attackColorsLightTile = {
            getSimulatedAlphaColor(Tile.TILE_TYPE.LIGHT, lightRedFill),
            getSimulatedAlphaColor(Tile.TILE_TYPE.LIGHT, darkRedOuter)
    };

    static final Color[] attackColorsDarkTile = {
            getSimulatedAlphaColor(Tile.TILE_TYPE.DARK, lightRedFill),
            getSimulatedAlphaColor(Tile.TILE_TYPE.DARK, darkRedOuter)
    };

    static final Color[] selectedColorsLightTile = {
            getSimulatedAlphaColor(Tile.TILE_TYPE.LIGHT, lightGreenFill),
            getSimulatedAlphaColor(Tile.TILE_TYPE.LIGHT, darkGreenOuter)
    };

    static final Color[] selectedColorsDarkTile = {
            getSimulatedAlphaColor(Tile.TILE_TYPE.DARK, lightGreenFill),
            getSimulatedAlphaColor(Tile.TILE_TYPE.DARK, darkGreenOuter)
    };

    /**
     * Helper method to get the simulated alpha color.
     * It adds the colors of the tile and the desired color together based on a factor, in order to simulate alpha.
     * @param tileType          Tile type, either LIGHT or DARK.
     * @param desiredColor      Desired color.
     * @return                  New simulated alpha color.
     */
    private static Color getSimulatedAlphaColor(Tile.TILE_TYPE tileType, Color desiredColor) {
        float factor = 160 / 255f;
        Color tileColor = tileType == Tile.TILE_TYPE.LIGHT ? lightTileColor : darkTileColor;
        int red = (int) (tileColor.getRed() * (1 - factor) + desiredColor.getRed() * factor);
        int green = (int) (tileColor.getGreen() * (1 - factor) + desiredColor.getGreen() * factor);
        int blue = (int) (tileColor.getBlue() * (1 - factor) + desiredColor.getBlue() * factor);
        return new Color(red, green, blue);
    }
}