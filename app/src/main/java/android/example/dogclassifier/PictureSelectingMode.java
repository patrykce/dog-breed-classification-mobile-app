package android.example.dogclassifier;

public enum PictureSelectingMode {
    CAMERA("camera"),
    GALLERY("gallery");

    public final String mode;

    PictureSelectingMode(String mode) {
        this.mode = mode;
    }
}
