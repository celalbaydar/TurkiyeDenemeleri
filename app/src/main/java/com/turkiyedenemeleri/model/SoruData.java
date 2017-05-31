package com.turkiyedenemeleri.model;

import com.turkiyedenemeleri.customviews.CanvasView;


/**
 * Created by celal on 01/05/2017.
 */

public class SoruData {
    CanvasView canvas;
    String cevap;

    public SoruData() {
        this.canvas = null;
        this.cevap = "";
    }

    public CanvasView getCanvas() {
        return canvas;
    }

    public void setCanvas(CanvasView canvas) {
        this.canvas = canvas;
    }

    public String getCevap() {
        return cevap;
    }

    public void setCevap(String cevap) {
        this.cevap = cevap;
    }
}
