package com.hbpaymentprocessing.hbpaymentprocessing.utilities;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ConstantVariables {
    // This class contains all constant variables in the project

    // All constant variables are used to validate a string
    public static final String EMAIL_FORMAT = "^[\\w-\\.]+@gmail.com$";
    public static final String PASSWORD_FORMAT = "^" + // start checking
            "(?=.*?[\\p{Lu}])" + // check at least one upper case letter
            "(?=.*?[\\p{Ll}])" + // check at least one lower case letter
            "(?=.*?[\\d])" + // check at least one number letter
            "(?=.*?[\\p{S}\\p{Po}\\p{Pc}\\p{Ps}\\p{Pe}\\p{Pd}])" + // check at least one special character letter
            "[\\p{Lu}\\p{Ll}\\d\\p{S}\\p{Po}\\p{Pc}\\p{Ps}\\p{Pe}\\p{Pd}]{8,250}" + // total character at least 8 character
            "$"; // end checking
    public static final String FULL_NAME_FORMAT = "[\\p{L}\\s]{2,250}";
    public static final String ROLE_NAME_FORMAT = "[\\p{L}]{2,20}";
    public static final String PHONE_NUMBER_FORMAT = "\\d{10}";
    public static final String ADDRESS_FORMAT = "[\\p{L}\\d\\s\\-\\/,]{1,75}";
    public static final String ID_CARD_FORMAT = "\\d{12}";
    public static final String INVOICE_CODE_FORMAT = "[\\p{L}\\d]{5,20}";
    public static final String ACCOUNT_NUMBER_CODE_FORMAT = "[\\p{L}\\d]{10,12}";
    public static final String DESCRIPTION_FORMAT = "[\\p{L}\\d\\s\\.\\/]{1,250}";
    public static final String DECIMAL_NUMBER_FORMAT = "[\\d\\.]{1,}";
    public static final String INTEGER_NUMBER_FORMAT = "[\\d]{1,}";
    public static final String TAG_NAME_FORMAT = "[\\p{L}\\s]{1,13}";

    // Database
    public static final String ACTIVE_STATUS = "Active";
    public static final String PAID_TRANSACTION_STATUS = "Paid";

    // All constant variables are used to format a string
    public static final String TIME_FORMATTING = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String PASSWORD_HASHING_ALGORITHM_NAME = "SHA-256";
    public static final Charset DEFAULT_CHARSET_ENCODER = StandardCharsets.UTF_8;

    public static final String DEFAULT_ZONE_NAME = "UTC";

}
