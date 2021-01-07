package engine2D.core.renderer.font;

class FontFile {

    String fontName;
    CharacterInfo[] characters;
    CharacterInfo unknownCharacter;

    FontFile(String fontName, CharacterInfo[] characters, CharacterInfo unknownCharacter) {
        this.fontName = fontName;
        this.characters = characters;
        this.unknownCharacter = unknownCharacter;
    }

}

class CharacterInfo {

    int id, x, y, width, height;
    int xoffset, yoffset, xadvance;

    CharacterInfo(int id, int x, int y, int width, int height, int xoffset, int yoffset, int xadvance) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.xoffset = xoffset;
        this.yoffset = yoffset;
        this.xadvance = xadvance;
    }

}
