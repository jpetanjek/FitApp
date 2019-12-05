package info.androidhive.barcode;

import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.barcode.camera.GraphicOverlay;

class BarcodeGraphicTracker extends Tracker<Barcode> {
    private GraphicOverlay<BarcodeGraphic> mOverlay;
    private BarcodeGraphic mGraphic;
    private BarcodeGraphicTrackerListener listener;

    BarcodeGraphicTracker(GraphicOverlay<BarcodeGraphic> overlay, BarcodeGraphic graphic, BarcodeGraphicTrackerListener listener) {
        mOverlay = overlay;
        mGraphic = graphic;
        this.listener = listener;
    }

    /**
     * Start tracking the detected item instance within the item overlay.
     */
    @Override
    public void onNewItem(int id, Barcode item) {
        mGraphic.setId(id);
        Log.e("XX", "barcode detected: " + item.displayValue + ", listener: " + listener);

        if (listener != null) {
            listener.onScanned(item);
        }
    }

    /**
     * Update the position/characteristics of the item within the overlay.
     */
    @Override
    public void onUpdate(Detector.Detections<Barcode> detectionResults, Barcode item) {
        mOverlay.add(mGraphic);
        mGraphic.updateItem(item);

        if (detectionResults != null && detectionResults.getDetectedItems().size() > 1) {
            Log.e("XX", "Multiple items detected");
            Log.e("XX", "onUpdate: " + detectionResults.getDetectedItems().size());

            if (listener != null) {
                List<Barcode> barcodes = asList(detectionResults.getDetectedItems());
                listener.onScannedMultiple(barcodes);
            }
        }
    }

    public static <C> List<C> asList(SparseArray<C> sparseArray) {
        if (sparseArray == null) return null;
        List<C> arrayList = new ArrayList<C>(sparseArray.size());
        for (int i = 0; i < sparseArray.size(); i++)
            arrayList.add(sparseArray.valueAt(i));
        return arrayList;
    }

    /**
     * Hide the graphic when the corresponding object was not detected.  This can happen for
     * intermediate frames temporarily, for example if the object was momentarily blocked from
     * view.
     */
    @Override
    public void onMissing(Detector.Detections<Barcode> detectionResults) {
        mOverlay.remove(mGraphic);
    }

    /**
     * Called when the item is assumed to be gone for good. Remove the graphic annotation from
     * the overlay.
     */
    @Override
    public void onDone() {
        mOverlay.remove(mGraphic);
    }

    public interface BarcodeGraphicTrackerListener {
        void onScanned(Barcode barcode);

        void onScannedMultiple(List<Barcode> barcodes);

        void onBitmapScanned(SparseArray<Barcode> sparseArray);

        void onScanError(String errorMessage);
    }
}
