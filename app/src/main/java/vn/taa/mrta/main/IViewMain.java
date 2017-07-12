package vn.taa.mrta.main;

import vn.taa.mrta.object.Clinic;
import vn.taa.mrta.object.Patient;

/**
 * Created by Putin on 3/27/2017.
 */

public interface IViewMain {
    void showProfile(Patient patient);

    void showClinicInfo(Clinic clinic);
}