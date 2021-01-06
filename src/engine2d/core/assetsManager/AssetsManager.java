package engine2d.core.assetsManager;

import java.util.ArrayList;

public class AssetsManager {

    private static final ArrayList<Disposable> assets = new ArrayList<>();

    public static void addAsset(Disposable asset) {
        assets.add(asset);
    }

    public static void collect() {
        for (Disposable asset : assets) {
            asset.dispose();
        }
        assets.clear();
    }

}
