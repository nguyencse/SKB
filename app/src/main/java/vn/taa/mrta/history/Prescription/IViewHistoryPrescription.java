package vn.taa.mrta.history.Prescription;

import java.util.List;

import vn.taa.mrta.object.PrescriptionInfo;

/**
 * Created by Putin on 3/11/2017.
 */

public interface IViewHistoryPrescription {
    void showListHistoryPrescription(List<PrescriptionInfo> prescriptionInfoList);
}
