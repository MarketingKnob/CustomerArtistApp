package com.samyotech.fabcustomer.interfacess;

/**
 * Created by shubham on 19/7/17.
 */

public interface Consts {
    public static String APP_NAME = "FabCustomer";

//    OLD Server
//    public String BASE_URL = "http://phpstack-132936-677440.cloudwaysapps.com/Webservice/";

//    New MKnob
    public String BASE_URL = "http://planetory.agency/artist/Webservice/";

    public String PAYMENT_FAIL = "http://phpstack-132936-677440.cloudwaysapps.com/Stripe/Payment/fail";
    public String PAYMENT_SUCCESS = "http://phpstack-132936-677440.cloudwaysapps.com/Stripe/Payment/success";
    public String MAKE_PAYMENT = "http://phpstack-132936-677440.cloudwaysapps.com/Stripe/Payment/make_payment/";

    public String PAYMENT_FAIL_Paypal = "http://phpstack-132936-677440.cloudwaysapps.com/Webservice/payufailure";
    public String PAYMENT_SUCCESS_paypal = "http://phpstack-132936-677440.cloudwaysapps.com/Webservice/payusuccess";
    public String MAKE_PAYMENT_paypal = "http://phpstack-132936-677440.cloudwaysapps.com/Webservice/paypalWallent?";

    public String INVOICE_PAYMENT_FAIL_Stripe = "http://phpstack-132936-677440.cloudwaysapps.com/Stripe/BookingPayement/fail";
    public String INVOICE_PAYMENT_SUCCESS_Stripe = "http://phpstack-132936-677440.cloudwaysapps.com/Stripe/BookingPayement/success";
    public String INVOICE_PAYMENT_Stripe = "http://phpstack-132936-677440.cloudwaysapps.com/Stripe/BookingPayement/make_payment/1/100";

    public String INVOICE__PAYMENT_paypal = " http://phpstack-132936-677440.cloudwaysapps.com/Webservice/paypal?amount=10&userId=20?invoice_id=KJLKJHH";



    /*Api Details*/
    public String GET_ALL_ARTISTS_API = "getAllArtists";
    public String GET_ARTIST_BY_ID_API = "getArtistByid";
    public String GET_NOTIFICATION_API = "getNotifications";
    public String GET_INVOICE_API = "getMyInvoice";
    public String GET_REFERRAL_CODE_API = "getMyReferralCode";
    public String GET_CHAT_HISTORY_API = "getChatHistoryForUser";
    public String GET_CHAT_API = "getChat";
    public String SEND_CHAT_API = "sendmsg";
    public String LOGIN_API = "signIn";
    public String REGISTER_API = "SignUp";
    public String UPDATE_PROFILE_API = "editPersonalInfo";
    public String CURRENT_BOOKING_API = "getMyCurrentBookingUser";
    public String BOOK_ARTIST_API = "book_artist";
    public String BOOK_APPOINTMENT_API = "book_appointment";
    public String DECLINE_BOOKING_API = "decline_booking";
    public String UPDATE_LOCATION_API = "updateLocation";
    public String MAKE_PAYMENT_API = "makePayment";
    public String CHECK_COUPON_API = "checkCoupon";
    public String GET_MY_TICKET_API = "getMyTicket";
    public String GENERATE_TICKET_API = "generateTicket";
    public String GET_TICKET_COMMENTS_API = "getTicketComments";
    public String ADD_TICKET_COMMENTS_API = "addTicketComments";
    public String FORGET_PASSWORD_API = "forgotPassword";
    public String GET_APPOINTMENT_API = "getAppointment";
    public String EDIT_APPOINTMENT_API = "edit_appointment";
    public String APPOINTMENT_OPERATION_API = "appointment_operation";
    public String GET_ALL_CATEGORY_API = "getAllCaegory";
    public String GET_ALL_JOB_USER_API = "get_all_job_user";
    public String POST_JOB_API = "post_job";
    public String GET_APPLIED_JOB_BY_ID_API = "get_applied_job_by_id";
    public String JOB_STATUS_USER_API = "job_status_user";
    public String EDIT_POST_JOB_API = "edit_post_job";
    public String DELETE_JOB_API = "deletejob";
    public String ADD_FAVORITES_API = "add_favorites";
    public String REMOVE_FAVORITES_API = "remove_favorites";
    public String GET_LOCATION_ARTIST_API = "getLocationArtist";
    public String ADD_RATING_API = "addRating";
    public String BOOKING_OPERATION_API = "booking_operation";
    public String JOB_COMPLETE_API = "jobComplete";
    public String DELETE_PROFILE_IMAGE_API = "deleteProfileImage";
    public String ADD_MONEY_API = "addMoney";
    public String GET_WALLET_HISTORY_API = "getWalletHistory";
    public String GET_WALLET_API = "getWallet";
    public String GET_All_JOBS_API = "get_all_jobs";



    /*app data*/
    public static String INTROAPP = "INTROAPP";
    String CAMERA_ACCEPTED = "camera_accepted";
    String STORAGE_ACCEPTED = "storage_accepted";
    String MODIFY_AUDIO_ACCEPTED = "modify_audio_accepted";
    String CALL_PRIVILAGE = "call_privilage";
    String FINE_LOC = "fine_loc";
    String CORAS_LOC = "coras_loc";
    String CALL_PHONE = "call_phone";
    String PAYMENT_URL = "payment_url";
    String SURL = "surl";
    String FURL = "furl";

    String SURL_BOOKING = "surl_booking";
    String FURL_BOOKING = "furl_booking";
    public static final String mBroadcastShowAdd = "FabCustomer.showAdd";
    /*app data*/

    /*Project Parameter*/
    String ARTIST_ID = "artist_id";
    String CHAT_LIST_DTO = "chat_list_dto";
    String USER_DTO = "user_dto";
    String POST_JOB_DTO = "post_job_dto";
    String IS_REGISTERED = "is_registered";
    String IMAGE_URI_CAMERA = "image_uri_camera";
    String DATE_FORMATE_SERVER = "EEE, MMM dd, yyyy hh:mm a"; //Wed, JUL 06, 2018 04:30 pm
    String DATE_FORMATE_TIMEZONE = "z";
    String HISTORY_DTO = "history_dto";
    String BROADCAST = "broadcast";

    /*Parameter Get Artist and Search*/
    String USER_ID = "user_id";
    String LATITUDE = "latitude";
    String LONGITUDE = "longitude";
    String CATEGORY_ID = "category_id";

    /*Get All History*/
    String ROLE = "role";

    /*Send Message*/
    String MESSAGE = "message";
    String SEND_BY = "send_by";
    String SENDER_NAME = "sender_name";


    /*Login Parameter*/
    String NAME = "name";
    String EMAIL_ID = "email_id";
    String PASSWORD = "password";
    String DEVICE_TYPE = "device_type";
    String DEVICE_TOKEN = "device_token";
    String DEVICE_ID = "device_id";
    String REFERRAL_CODE = "referral_code";


    /*Update Profile*/
    String NEW_PASSWORD = "new_password";
    String GENDER = "gender";
    String MOBILE = "mobile";
    String OFFICE_ADDRESS = "office_address";
    String ADDRESS = "address";
    String IMAGE = "image";
    String CITY = "city";
    String COUNTRY = "country";

    /*Book Artist*/

    String DATE_STRING = "date_string";
    String TIMEZONE = "timezone";
    String PRICE = "price";

    /*Decline*/
    String BOOKING_ID = "booking_id";
    String DECLINE_BY = "decline_by";
    String DECLINE_REASON = "decline_reason";

    /*Make Payment*/
    String INVOICE_ID = "invoice_id";
    // String USER_ID = "user_id";
    String COUPON_CODE = "coupon_code";
    String FINAL_AMOUNT = "final_amount";
    String PAYMENT_STATUS = "payment_status";


    /*Chat intent*/
    String ARTIST_NAME = "artist_name";

    /*Add Ticket*/
    String REASON = "reason";


    /*Get Ticket*/
    String TICKET_ID = "ticket_id";
    String COMMENT = "comment";


    /*Edit Appointment*/
    String APPOINTMENT_ID = "appointment_id";

    /*Decline Appointment*/
    String REQUEST = "request";


    /*Post Job*/
    String AVTAR = "avtar";
    String TITLE = "title";
    String DESCRIPTION = "description";
    String LATI = "lati";
    String LONGI = "longi";

    /*Get Applied Job*/
    String JOB_ID = "job_id";
    String aj_id = "aj_id";

    /*Job Status*/
    String AJ_ID = "aj_id";
    String STATUS = "status";

    // Google Console APIs developer key
    // Replace this key with your's
    public static final String DEVELOPER_KEY = "AIzaSyBlLIsCaCw8ylCTPR0XhaKp-vkeD4S-5_0";

    /*Payment*/
    String PAYMENT_TYPE = "payment_type";
    /*Chat*/
    String CHAT_TYPE = "chat_type";

    /*Paypal Client Id*/
    /*Add Review*/
    String RATING = "rating";

    /*Add Money*/
    String TXN_ID = "txn_id";
    String ORDER_ID = "order_id";
    String AMOUNT = "amount";
    String CURRENCY = "currency";

}

