package vn.taa.mrta.general;

/**
 * Created by Putin on 3/4/2017.
 */

public class CommonField {
    public static final String URL_HOST = "http://ws.trolyso.vn/Login/userview.php";
    public static final String URL_SERVICES = "http://192.168.201.1:3000/www/skb/services.php";
//    public static final String URL_SERVICES = "http://mrta.000webhostapp.com/services.php";
    public static final String SHARED_PREFERENCES = "SKB";
    public static final String LOGIN_STATUS = "login_status";
    public static final String PATIENT_BRIEF_ID = "patient_brief_id";
    public static final String PATIENT_INFO_DETAIL = "patient_info_detail";
    public static final String LAST_TREATMENT_DETAIL = "last_treatment_detail";

    public static final String TREATMENT_TIME = "time";
    public static final String TREATMENT_DATE = "date";
    public static final String TREATMENT_DOCTOR_NAME = "doctorName";
    public static final String TREATMENT_DOCTOR_OFFICE = "doctorOffice";
    public static final String TREATMENT_SYMPTON = "sympton";
    public static final String TREATMENT_CONTENT = "content";
    public static final String TREATMENT_COST = "cost";
    public static final String TREATMENT_NOTE = "note";
    public static final String TREATMENT_IS_CANCEL = "isCancel";
    public static final String TREATMENT_CARDIOVASCULAR = "cardiovascular";
    public static final String TREATMENT_TEMPERATURE = "temperature";
    public static final String TREATMENT_BLOOD_PRESSURE = "bloodPressure";
    public static final String TREATMENT_BREATHING_RATE = "breathingRate";
    public static final String TREATMENT_WEIGHT = "weight";
    public static final String TREATMENT_HEIGHT = "height";
    public static final String TREATMENT_BMI = "bmi";
    public static final String TREATMENT_STATUS = "status";
    public static final String TREATMENT_CLINICAL_APPROACH = "clinicalApproach";

    public static final int CLINIC_ID_SIZE = 5;

    public static final int FRAGMENT_COUNT = 4;
    public static final int FRAGMENT_DASHBOARD = 0;
    public static final int FRAGMENT_TREATMENT = 1;
    public static final int FRAGMENT_PRESCRIPTION = 2;
    public static final int FRAGMENT_MEETING = 3;

    public static final String FRAGMENT_DASHBOARD_DRUG_LIST = "fragment_dashboard_drug_list";
    public static final String FRAGMENT_PRESCRIPTION_LAST_PRESCRIPTION = "fragment_prescription_last_prescription";

    public static final String IS_FIRST_TIME_LAUNCH = "is_first_time_launch";
    public static final String IS_CONTINUE_REMIND_DRINK_DRUG = "is_continue_remind_drink_drug";

    public static final String DRUG_UNIT_PILL = "Viên";
    public static final String DRUG_UNIT_ROLL = "Cuộn";
    public static final String DRUG_UNIT_BOTTLE = "Lọ";
    public static final String DRUG_UNIT_BOTTLE_2 = "Chai";
    public static final String DRUG_UNIT_BOX = "Hộp";
    public static final String DRUG_UNIT_TUBE = "Tube";
    public static final String DRUG_UNIT_TUBE_2 = "Ống";
    public static final String DRUG_UNIT_UNKNOW = "(Bấm để xem chi tiết)";
    public static final String HAS_MRTA = "has_mrta";

    public static final String ROUTE_FROM = "route_frome";
    public static final String CLINIC_ID = "clinic_id";

    public static final int FRAGMENT_COUNT_MANUAL = 3;
    public static final String USER_INFO = "user_info";

    public static final int FRAGMENT_MANUAL_DASHBOARD = 0;
    public static final int FRAGMENT_MANUAL_PROFILE = 1;
    public static final int FRAGMENT_MANUAL_PRESCRIPTION = 2;
    public static final String SQL_IP = "SQL_IP";
    public static final String SQL_name = "SQL_name";
    public static final String View_user = "View_user";
    public static final String View_pass = "View_pass";
}