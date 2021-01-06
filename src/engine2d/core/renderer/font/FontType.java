package engine2d.core.renderer.font;

import engine2d.core.renderer.Model;
import engine2d.core.renderer.texture.Texture;
import engine2d.core.renderer.texture.TexParams;
import org.joml.Vector3f;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static engine2d.utils.Utils.*;

public class FontType {

    private final FontFile fontFile;
    private final Texture fontAtlas;
    private final Model textModel = new Model();

    public FontType(String fontFilePath, String fontAtlasPath) {
        this.fontFile = load(fontFilePath);

        TexParams parameters = new TexParams(
                TexParams.LINEAR, TexParams.LINEAR,
                TexParams.CLAMP_TO_BORDER, TexParams.CLAMP_TO_BORDER
        );
        this.fontAtlas = new Texture(fontAtlasPath, false, 4, parameters);
    }

    public Text render(String text, int red, int green, int blue) {
        int[] characters = new int[text.length()];

        for (int i = 0; i < text.length(); i++) {
            char charID = text.charAt(i);
            characters[i] = charID;
        }

        float[] charPositions = new float[12 * text.length()];
        float[] charTextureCoordinates = new float[12 * text.length()];

        int character = 0;
        int caretPosition = 0;

        outerLoop:
        for (int characterID : characters) {
            for (CharacterInfo charInfo : fontFile.characters) {
                if (characterID == charInfo.id) {
                    fillArraysWithData(charPositions, charTextureCoordinates, caretPosition, character, charInfo);
                    // Advance to the next character quad
                    caretPosition += charInfo.xadvance;
                    character += 12;

                    continue outerLoop;
                }
            }
            fillArraysWithData(charPositions, charTextureCoordinates, caretPosition, character, fontFile.unknownCharacter);
            // Advance to the next character quad
            caretPosition += fontFile.unknownCharacter.xadvance;
            character += 12;
        }

        float r = byteToFloat(red);
        float g = byteToFloat(green);
        float b = byteToFloat(blue);

        return new Text(new Vector3f(r, g, b), textModel, fontAtlas, charPositions, charTextureCoordinates);
    }

    public String getName() {
        return fontFile.fontName;
    }

    private void fillArraysWithData(float[] charPositions, float[] charTextureCoordinates, int caretPosition, int character, CharacterInfo charInfo) {
        // Add character's quad's positions
        charPositions[character] = caretPosition + charInfo.xoffset;
        charPositions[1 + character] = 72 - charInfo.yoffset;

        charPositions[2 + character] = caretPosition + charInfo.xoffset;
        charPositions[3 + character] = 72 - charInfo.height - charInfo.yoffset;

        charPositions[4 + character] = caretPosition + charInfo.xoffset + charInfo.width;
        charPositions[5 + character] = 72 - charInfo.height - charInfo.yoffset;

        charPositions[6 + character] = caretPosition + charInfo.xoffset + charInfo.width;
        charPositions[7 + character] = 72 - charInfo.height - charInfo.yoffset;

        charPositions[8 + character] = caretPosition + charInfo.xoffset + charInfo.width;
        charPositions[9 + character] = 72 - charInfo.yoffset;

        charPositions[10 + character] = caretPosition + charInfo.xoffset;
        charPositions[11 + character] = 72 - charInfo.yoffset;
        // I have no idea what I'm doing ^

        // Convert the texture coordinates
        float x = convertTextureCoordinates(charInfo.x);
        float y = convertTextureCoordinates(charInfo.y);
        float width = convertTextureCoordinates(charInfo.width);
        float height = convertTextureCoordinates(charInfo.height);

        // Add texture coordinates
        charTextureCoordinates[character] = x;
        charTextureCoordinates[1 + character] = y;

        charTextureCoordinates[2 + character] = x;
        charTextureCoordinates[3 + character] = y + height;

        charTextureCoordinates[4 + character] = x + width;
        charTextureCoordinates[5 + character] = y + height;

        charTextureCoordinates[6 + character] = x + width;
        charTextureCoordinates[7 + character] = y + height;

        charTextureCoordinates[8 + character] = x + width;
        charTextureCoordinates[9 + character] = y;

        charTextureCoordinates[10 + character] = x;
        charTextureCoordinates[11 + character] = y;
    }

    private float convertTextureCoordinates(int n) {  // This assumes that the font atlas is square
        return n / (float) fontAtlas.getWidth();
    }

    private FontFile load(String fontFile) {
        String fontName = null;
        ArrayList<CharacterInfo> characters = new ArrayList<>();
        CharacterInfo unknownCharacter = null;

        File file = new File(fontFile);

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNext()) {
                String line = sc.nextLine();

                if (line.startsWith("info ")) {
                    String[] info = line.split(" ");
                    String infoName = info[1];
                    fontName = infoName.substring(6, infoName.length() - 1);
                } else if (line.startsWith("char ")) {
                    String[] character = line.split(" {1,5}");  // first time doing regex :O

                    int id = Integer.parseInt(character[1].substring(3));
                    int x = Integer.parseInt(character[2].substring(2));
                    int y = Integer.parseInt(character[3].substring(2));
                    int width = Integer.parseInt(character[4].substring(6));
                    int height = Integer.parseInt(character[5].substring(7));
                    int xoffset = Integer.parseInt(character[6].substring(8));
                    int yoffset = Integer.parseInt(character[7].substring(8));
                    int xadvance = Integer.parseInt(character[8].substring(9));

                    characters.add(new CharacterInfo(id, x, y, width, height, xoffset, yoffset, xadvance));

                    if (id == 127)
                        unknownCharacter = new CharacterInfo(id, x, y, width, height, xoffset, yoffset, xadvance);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return new FontFile(fontName, characters.toArray(new CharacterInfo[0]), unknownCharacter);
    }

}
