package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.ScalarResult;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Alexander on 05.07.2016.
 */
public class KolmogorovSmirnovTestAnalyzer implements IAnalyzer<Double>{

    private double getCoeff() {
        final double alpha = 0.05;
        final double epsilon = 0.0001; //alphas from the table don't differ more than that
        if (Math.abs(alpha - 0.05) < epsilon) return 1.36;
        return -1.0;//other cases are unimplemented for the moment
    }

    private class EmpiricalDistributionFunction {
        private List<Double> data;
        EmpiricalDistributionFunction(List<Double> dataIn) {
            Collections.sort(dataIn);//in ascending order.
            data = dataIn;
        }
        public double getValueAt(double x) {
            int i = 0;
            while (i < data.size() && data.get(i) < x) i++;
            return (((double) i) / data.size());//case when i==data.size() is included in this.
        }
    }

    private double calcSmirnovStatistic(List<Double> fstArray, List<Double> sndArray) {
        EmpiricalDistributionFunction fstFun = new EmpiricalDistributionFunction(fstArray);
        //EmpiricalDistributionFunction sndFun = new EmpiricalDistributionFunction(sndArray);
            //can be used for calculations checking.
        double fstLen = fstArray.size();
        double sndLen = sndArray.size();
        Collections.sort(sndArray);

        double statisticPositive = 1.0 / sndLen - fstFun.getValueAt(sndArray.get(0));
        for (int i = 1; i < sndLen; i++) {
            double temp = ((double) (i + 1)) / sndLen - fstFun.getValueAt(sndArray.get(i));
            if (temp > statisticPositive) statisticPositive = temp;
        }

        double statisticNegative = fstFun.getValueAt(sndArray.get(0));
        for (int i = 1; i < sndLen; i++) {
            double temp = fstFun.getValueAt(sndArray.get(i)) - ((double) i) / sndLen;
            if (temp > statisticNegative) statisticNegative = temp;
        }

        double statisticTrue = statisticPositive > statisticNegative ? statisticPositive : statisticNegative;

        statisticTrue *= Math.sqrt((fstLen * sndLen)/ (fstLen + sndLen));
        return statisticTrue;
    }

    public ScalarResult<Boolean> analyze(List<List<Double>> dataSets) throws IllegalArgumentException {
        if (dataSets.size() != 2) throw new IllegalArgumentException("Kolmogorov-Smirnov test can only be applied " +
                "to exactly 2 data sets. Actually got " + dataSets.size());

        double statistic = calcSmirnovStatistic(dataSets.get(0), dataSets.get(1));
        double coeff = getCoeff();
        return new ScalarResult<Boolean>(statistic < coeff);
    }
}
