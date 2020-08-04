package com.leonov.diplome.assessment;

import com.leonov.diplome.model.Assessment;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

public class AssessmentModule {

    private static final Map<Integer, String> PH_MSG = new LinkedHashMap<>();

    private static final Map<Integer, String> HUM_MSG = new LinkedHashMap<>();

    private static final Map<Integer, String> TROPHY_MSG = new LinkedHashMap<>();

    private static final Map<Integer, String> OXYGEN_MSG = new LinkedHashMap<>();

    private static final Map<Integer, String> RESULT_MSG_COLOR = new LinkedHashMap<>();

    private static final List<Double> FOR_PH;

    private static final List<Double> FOR_HUM;

    private static final List<Double> FOR_TROPHY;

    private static final List<Double> FOR_OXYGEN;

    private static final List<Double> FOR_RESULT;

    static {

        FOR_PH = List.of(4.3, 4.9, 5.5, 6.1, 6.7, 7.3, 7.9);

        FOR_HUM = List.of(225.0, 90.0, 24.0, 10.0, 4.0, 2.0);

        FOR_TROPHY = List.of(300.0, 220.0, 50.0, 20.0, 8.0, 3.0, 2.0);

        FOR_OXYGEN = List.of(20.0, 60.0, 120.0, 140.0); //first class <20 and >140

        FOR_RESULT = List.of(2.6, 3.6, 4.6);

        PH_MSG.put(1, "alkali-free acid");
        PH_MSG.put(2, "alkali-low acid slightly-acid");
        PH_MSG.put(3, "alkali-low acid slightly-acid acid");
        PH_MSG.put(4, "alkali-low slightly-acid");
        PH_MSG.put(5, "alkali-mid slightly-acid neutral");
        PH_MSG.put(6, "alkali-mid neutral alkali-low");
        PH_MSG.put(7, "alkali-high alkali-low");

        HUM_MSG.put(2, "poli humus");
        HUM_MSG.put(3, "mezo-poli humus");
        HUM_MSG.put(4, "mezo humus");
        HUM_MSG.put(5, "oligo humus");
        HUM_MSG.put(6, "ultra-oligo humus");

        TROPHY_MSG.put(1, "hyper trophy");
        TROPHY_MSG.put(2, "high trophy");
        TROPHY_MSG.put(3, "ev trophy");
        TROPHY_MSG.put(4, "mezo trophy");
        TROPHY_MSG.put(5, "oligo trophy");
        TROPHY_MSG.put(6, "ultra-oligo trophy");

        OXYGEN_MSG.put(2, "oxy-oversaturated");
        OXYGEN_MSG.put(3, "oxy-deficient");
        OXYGEN_MSG.put(4, "oxy-permissible saturated");
        OXYGEN_MSG.put(5, "oxy-equilibrium saturated");

        RESULT_MSG_COLOR.put(1, "text-danger");
        RESULT_MSG_COLOR.put(2, "text-warning");
        RESULT_MSG_COLOR.put(3, "text-info");
        RESULT_MSG_COLOR.put(4, "text-success");
    }

    public static Assessment assessmentByZobkov(Assessment assessment) {
        getParamClasses(assessment);

        double rawAssessmentResult = getRawAssessmentResult(assessment);

        assessment.setDateTime(LocalDateTime.now());
        assessment.setAssessmentResult(roundTo3Decimals(rawAssessmentResult));

        return assessment;
    }

    public static Model getDescriptionForParamClasses(Assessment assessment, Model model) {
        int pH_class = (int) Math.floor(assessment.getPhClass());
        int humus_class = (int) Math.floor(assessment.getHumClass());
        int trophy_class = (int) Math.floor(assessment.getTrophyClass());
        int oxygen_class = (int) Math.floor(assessment.getOxygenClass());

        model.addAttribute("phClassMsg", PH_MSG.get(pH_class));
        model.addAttribute("humClassMsg", HUM_MSG.get(humus_class));
        model.addAttribute("trophyClassMsg", TROPHY_MSG.get(trophy_class));
        model.addAttribute("oxygenClassMsg", OXYGEN_MSG.get(oxygen_class));

        double assessmentResult = assessment.getAssessmentResult();
        getColorClassForAssessmentResult(assessmentResult, model);


        return model;
    }

    private static void getColorClassForAssessmentResult(double assessmentResult, Model model) {
        if (assessmentResult < 2.6) {
            model.addAttribute("colorClass", RESULT_MSG_COLOR.get(1));
        } else if (assessmentResult > 4.6) {
            model.addAttribute("colorClass", RESULT_MSG_COLOR.get(4));
        } else {
            for (int i = 0; i < FOR_RESULT.size(); i++) {
                if (assessmentResult >= FOR_RESULT.get(i) && assessmentResult <= FOR_RESULT.get(i + 1)) {
                    model.addAttribute("colorClass", RESULT_MSG_COLOR.get(i + 2));
                    break;
                }
            }
        }
    }

    private static double getRawAssessmentResult(Assessment assessment) {
        double pH_class = assessment.getPhClass();
        double humus_class = assessment.getHumClass();
        double trophy_class = assessment.getTrophyClass();
        double oxygen_class = assessment.getOxygenClass();

        if (pH_class <= 2) {
            return 2.49;
        } else if (humus_class <= 2 && pH_class <= 3) {
            return 2.49;
        } else if (trophy_class <= 2) {
            return 2.49;
        } else if (oxygen_class == 2) {
            return 2.49;
        } else {
            double base = assessment.getPhClass() * assessment.getHumClass() * assessment.getTrophyClass() * assessment.getOxygenClass();
            return Math.pow(base, 0.25);
        }
    }

    private static void getParamClasses(Assessment assessment) {
        double pH = assessment.getPh();
        double hum = assessment.getHum();
        double trophy = assessment.getTrophy();
        double oxygen = assessment.getOxygen();

        double pH_class = 0;
        double humus_class = 0;
        double trophy_class = 0;
        double oxygen_class = 0;

        for (int i = 0; i < FOR_PH.size(); i++) {
            if (pH >= FOR_PH.get(i) && pH <= FOR_PH.get(i + 1)) {

                double fractionalPartOfClass = getFractionalPartForIncreasingSeq(FOR_PH.get(i), FOR_PH.get(i + 1), pH);

                pH_class = i + 1 + fractionalPartOfClass;
                break;
            }
        }

        for (int i = 0; i < FOR_HUM.size(); i++) {
            if (hum <= FOR_HUM.get(i) && hum >= FOR_HUM.get(i + 1)) {

                double fractionalPartOfClass = getFractionalPartForDecreasingSeq(FOR_HUM.get(i), FOR_HUM.get(i + 1), hum);

                humus_class = i + 2 + fractionalPartOfClass;
                break;
            }
        }

        for (int i = 0; i < FOR_TROPHY.size(); i++) {
            if (trophy <= FOR_TROPHY.get(i) && trophy >= FOR_TROPHY.get(i + 1)) {

                double fractionalPartOfClass = getFractionalPartForDecreasingSeq(FOR_TROPHY.get(i), FOR_TROPHY.get(i + 1), trophy);

                trophy_class = i + 1 + fractionalPartOfClass;
                break;
            }
        }

        if (oxygen < 20 || oxygen > 140) {
            oxygen_class = 2;
        } else {
            for (int i = 0; i < FOR_OXYGEN.size(); i++) {
                if (oxygen >= FOR_OXYGEN.get(i) && oxygen <= FOR_OXYGEN.get(i + 1)) {

                    double fractionalPartOfClass = getFractionalPartForIncreasingSeq(FOR_OXYGEN.get(i), FOR_OXYGEN.get(i + 1), oxygen);

                    oxygen_class = i + 3 + fractionalPartOfClass;
                    break;
                }
            }
        }

        assessment.setPhClass(roundTo3Decimals(pH_class));
        assessment.setHumClass(roundTo3Decimals(humus_class));
        assessment.setTrophyClass(roundTo3Decimals(trophy_class));
        assessment.setOxygenClass(roundTo3Decimals(oxygen_class));
    }

    public static double roundTo3Decimals(double value) {
        return BigDecimal.valueOf(value).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }

    private static double getFractionalPartForIncreasingSeq(double leftBound, double rightBound, double param) {
        double intervalLength = rightBound - leftBound;
        double myParamLength = param - leftBound;

        return myParamLength / intervalLength;
    }

    private static double getFractionalPartForDecreasingSeq(double leftBound, double rightBound, double param) {
        double intervalLength = leftBound - rightBound;
        double myParamLength = leftBound - param;

        return myParamLength / intervalLength;
    }
}