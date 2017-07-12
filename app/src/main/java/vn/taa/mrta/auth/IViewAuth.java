package vn.taa.mrta.auth;

import vn.taa.mrta.object.Clinic;
import vn.taa.mrta.object.Patient;

/**
 * Created by Putin on 3/4/2017.
 */

public interface IViewAuth {
    void loginSuccess(Patient patient);
    void loginFailed();
}
