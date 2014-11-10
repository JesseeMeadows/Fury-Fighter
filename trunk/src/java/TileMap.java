import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class TileMap {		
    private final int tileWidth;		   // Width of tile : pixel units
    private final int tileHeight;		   // Height of tile: pixel units
    private final int tileMapWidth;		   // Width of tileMap: Tile units
    private final int tileMapHeight;	   // Height of tileMap: Tile units 
    
    private final int firstGid;    
    private int[] tileMap;				   // tile references of tileset used in compilation of Map

    // private BufferedImage tileset[];               // Tiles parsed

    public TileMap(JSONObject levelFile) {

    	firstGid      = getFirstGid(levelFile);
    	tileWidth     = retrieveAttribute(levelFile, "tilewidth");
    	tileHeight    = retrieveAttribute(levelFile, "tileheight");
    	tileMapWidth  = retrieveAttribute(levelFile, "width");
    	tileMapHeight = retrieveAttribute(levelFile, "height");
    	
    	tileMap = new int[tileMapWidth * tileMapHeight];
    	tileMap = loadTileMap(levelFile);
    	// parseToTiles(getTilesetPath(levelFile), levelFile);

    }

    public int getTile(int gid) {
    	return tileMap[gid];
    }
    
    public int getTileMapWidth() {
    	return tileMapWidth;
    }
    public int getTileMapHeight() {
    	return tileMapHeight;
    }
    
    public int getTileWidth() {
    	return tileWidth;
    }
    
    public int getTileHeight() {
    	return tileHeight;
    }

    private int retrieveAttribute(JSONObject jsonLevel, String attribute) {
    	return ((Number) jsonLevel.get(attribute)).intValue();
    }
    
    private int[] loadTileMap(JSONObject jsonLevel) {
    	
		JSONArray layersArray = (JSONArray)jsonLevel.get("layers");
		JSONObject backgroundLayer = (JSONObject)layersArray.get(0);
		
		JSONArray mapData = (JSONArray)backgroundLayer.get("data");    // gets data array(line:4 in testlevel)
								   // gets data array's size
		int[] tileRefContainer = new int[mapData.size()];
		
		for(int i = 0; i < mapData.size(); i++){                    // Loads the data array into memory
			tileRefContainer[i] = ((Number)mapData.get(i)).intValue();
		}
		
		return tileRefContainer;
	}

//    private void parseToTiles(String location, JSONObject jsonLevel) {    	
//    	BufferedImage tilesetImage;     	
//		
//		try {
//			tilesetImage     = ImageIO.read(new File(location));
//			int totalRows    = tilesetImage.getHeight() / tileHeight;
//			int totalColumns = tilesetImage.getWidth() / tileWidth;
//			
//			tileset = new BufferedImage[totalRows * totalColumns];
//			
//			for (int row = 0; row < totalRows; row++) {
//				for (int column = 0; column < totalColumns; column++) {
//					tileset[row * totalColumns + column] 
//						= tilesetImage.getSubimage(column * tileWidth, row * tileHeight, tileWidth, tileHeight);
//				}
//			}
//		} 
//		catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//    	
//   	}    

//    private String getTilesetPath(JSONObject jsonLevel) {
//    	JSONArray tilesetProperties = (JSONArray) jsonLevel.get("tilesets");
//		JSONObject tilesetsInfo = (JSONObject) tilesetProperties.get(0);  
//		String tileSetLocation = (String) tilesetsInfo.get("image");
//		return "assets/" + tileSetLocation;
//    }

    private int getFirstGid(JSONObject jsonLevel) {
    	JSONArray tilesetProperties = (JSONArray) jsonLevel.get("tilesets");
		JSONObject tilesetsInfo = (JSONObject) tilesetProperties.get(0); 
		return ((Number) tilesetsInfo.get("firstgid")).intValue();
    }

}
