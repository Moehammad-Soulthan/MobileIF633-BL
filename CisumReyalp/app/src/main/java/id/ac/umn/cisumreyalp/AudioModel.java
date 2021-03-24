package id.ac.umn.cisumreyalp;

import java.io.Serializable;

public class AudioModel implements Serializable {

    String aId;
    String aTitle;
    String aArtist;
    String aAlbum;
    String aPath;
    int aDuration;
    int aSize;

    //    Setter
    public void setaId(String aId) { this.aId = aId; }
    public void setaTitle(String aTitle) { this.aTitle = aTitle; }
    public void setaArtist(String aArtist) { this.aArtist = aArtist; }
    public void setaAlbum(String aAlbum) { this.aAlbum = aAlbum; }
    public void setaPath(String aPath) { this.aPath = aPath; }
    public void setaDuration(int aDuration) { this.aDuration = aDuration; }
    public void setaSize(int aSize) { this.aSize = aSize; }

    //    Getter
    public String getaId() {
        return aId;
    }
    public String getaTitle() { return aTitle; }
    public String getaArtist() { return aArtist; }
    public String getaAlbum() { return aAlbum; }
    public String getaPath() { return aPath; }
    public int getaDuration() {
        return aDuration;
    }
    public int getaSize() { return aSize; }
}
