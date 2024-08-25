package com.hbpaymentprocessing.hbpaymentprocessing.utilities;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class Utility {

    public Instant getCurrentUTCDateTime() {
        return Instant.now();
    }

    // format date time to follow by zone id
    public String formatDateTime(String zoneName, Instant currentUTC, String patternStr)
            throws DateTimeException, NullPointerException, IllegalArgumentException {
        zoneName = zoneName == null ? ConstantVariables.DEFAULT_ZONE_NAME.trim()
                : zoneName.isEmpty() ? ConstantVariables.DEFAULT_ZONE_NAME.trim() : zoneName;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(patternStr.trim());
        ZonedDateTime zdt = ZonedDateTime.ofInstant(currentUTC, ZoneId.of(zoneName.trim()));
        return zdt.format(dateTimeFormatter);
    }

    public Timestamp getCurrentDateTime() {
        String currentDateTime = this.formatDateTime(null,
                this.getCurrentUTCDateTime(), ConstantVariables.TIME_FORMATTING);
        return Timestamp.valueOf(currentDateTime.trim());
    }

    public String increaseCode(String codeStr) throws NumberFormatException, IndexOutOfBoundsException {
        if (codeStr != null) {
            if (codeStr.isEmpty()) {
                codeStr = "12345671";
            }
        } else {
            codeStr = "12345671";
        }
        String numberStr = codeStr.substring(6);
        long number = Long.parseLong(numberStr.trim());
        number = number + 1;
        // set code string after increasing
        codeStr = codeStr.substring(0, 6).trim()
                .concat(String.valueOf(number).trim());
        return codeStr;
    }

    public String bytesToHexString(byte[] hashingString) {
        StringBuilder hexString = new StringBuilder(2 * hashingString.length);
        for (int i = 0; i < hashingString.length; i++) {
            String hex = Integer.toHexString(0xff & hashingString[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString().toUpperCase();
    }

    public String encryptPassword(String password, String algorithmName) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithmName.trim());
        byte[] hashing = digest.digest(password.getBytes(ConstantVariables.DEFAULT_CHARSET_ENCODER));
        return this.bytesToHexString(hashing);
    }

    public boolean validateInputString(String inputString, String regexString) {
        return Pattern.matches(regexString.trim(), inputString.trim());
    }


    public Map<String, String> checkIntegerNumberError(String inputStr, String regexStr, Map<String, String> errors,
                                                       boolean allowNull, String errorTitle, String formatErrorMessage,
                                                       String valueErrorMessage) throws NumberFormatException {

        if (!allowNull) {
            // not allow null, not allow empty
            if (inputStr == null) {
                errors.put(errorTitle.trim(), formatErrorMessage.trim());
            } else if (!this.validateInputString(inputStr.trim(), regexStr.trim())) {
                errors.put(errorTitle.trim(), formatErrorMessage.trim());
            } else if (Integer.parseInt(inputStr.trim()) == 0) {
                errors.put(errorTitle.trim(), valueErrorMessage.trim());
            }
        } else {
            // allow null, and allow empty
            if (inputStr != null) {
                if (!inputStr.isEmpty()) {
                    if (!this.validateInputString(inputStr.trim(), regexStr.trim())) {
                        errors.put(errorTitle.trim(), formatErrorMessage.trim());
                    } else if (Integer.parseInt(inputStr.trim()) == 0) {
                        errors.put(errorTitle.trim(), valueErrorMessage.trim());
                    }
                }
            }
        }
        return errors;
    }

    public Map<String, String> checkDecimalNumberError(String inputStr, String regexStr, Map<String, String> errors,
                                                       boolean allowNull, String errorTitle, String formatErrorMessage,
                                                       String valueErrorMessage) throws NumberFormatException {

        if (!allowNull) {
            // not allow null, not allow empty
            if (inputStr == null) {
                errors.put(errorTitle.trim(), formatErrorMessage.trim());
            } else if (!this.validateInputString(inputStr.trim(), regexStr.trim())) {
                errors.put(errorTitle.trim(), formatErrorMessage.trim());
            } else if (Double.parseDouble(inputStr.trim()) <= 0) {
                errors.put(errorTitle.trim(), valueErrorMessage.trim());
            }
        } else {
            // allow null, and allow empty
            if (inputStr != null) {
                if (!inputStr.isEmpty()) {
                    if (!this.validateInputString(inputStr.trim(), regexStr.trim())) {
                        errors.put(errorTitle.trim(), formatErrorMessage.trim());
                    } else if (Double.parseDouble(inputStr.trim()) <= 0) {
                        errors.put(errorTitle.trim(), valueErrorMessage.trim());
                    }
                }
            }
        }
        return errors;
    }

    public Map<String, String> checkStringError(String inputStr, String regexStr, Map<String, String> errors,
                                                boolean allowNull, String errorTitle, String formatErrorMessage) {
        if (!allowNull) {
            // not allow null, not allow empty
            if (inputStr == null) {
                errors.put(errorTitle.trim(), formatErrorMessage.trim());
            } else if (!this.validateInputString(inputStr.trim(), regexStr.trim())) {
                errors.put(errorTitle.trim(), formatErrorMessage.trim());
            }
        } else {
            // allow null, and allow empty
            if (inputStr != null) {
                if (!inputStr.isEmpty()) {
                    if (!this.validateInputString(inputStr.trim(), regexStr.trim())) {
                        errors.put(errorTitle.trim(), formatErrorMessage.trim());
                    }
                }
            }
        }
        return errors;
    }

}
