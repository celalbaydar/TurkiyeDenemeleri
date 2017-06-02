package com.turkiyedenemeleri.util;

import java.text.DecimalFormat;

/**
 * Created by safakesberk on 02/07/16.
 */
public class YGSCalculate {
    private double ygsTrN, ygsSosyalN, ygsMatN, ygsFenN;

    public YGSCalculate(double ygsTrN, double ygsSosyalN, double ygsMatN, double ygsFenN) {
        this.ygsTrN = ygsTrN;
        this.ygsSosyalN = ygsSosyalN;
        this.ygsMatN = ygsMatN;
        this.ygsFenN = ygsFenN;
    }

    public double getYgs1() {
        return format(100.160 + (ygsTrN * 1.999) + (ygsSosyalN * 1) + (ygsMatN * 3.998) + (ygsFenN * 2.999));
    }

    public double getYgs2() {
        return format(100.160 + (ygsTrN * 1.999) + (ygsSosyalN * 1) + (ygsMatN * 2.999) + (ygsFenN * 3.998));
    }

    public double getYgs3() {
        return format(100.160 + (ygsTrN * 3.998) + (ygsSosyalN * 2.999) + (ygsMatN * 1.999) + (ygsFenN * 1));
    }

    public double getYgs4() {
        return format(100.160 + (ygsTrN * 2.999) + (ygsSosyalN * 3.998) + (ygsMatN * 1.999) + (ygsFenN * 1));
    }

    public double getYgs5() {
        return format(100.120 + (ygsTrN * 3.699) + (ygsSosyalN * 1.999) + (ygsMatN * 3.299) + (ygsFenN * 1));
    }

    public double getYgs6() {
        return format(100.120 + (ygsTrN * 3.299) + (ygsSosyalN * 1) + (ygsMatN * 3.699) + (ygsFenN * 1.999));
    }

    private double format(double param){
        return Double.parseDouble(new DecimalFormat("##.####").format(param).replace(',','.'));
    }
}
