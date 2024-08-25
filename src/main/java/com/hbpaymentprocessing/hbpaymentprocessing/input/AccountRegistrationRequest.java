package com.hbpaymentprocessing.hbpaymentprocessing.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class AccountRegistrationRequest implements Serializable {
        private String email;
        private String pwd;
        private String fullName;
        private String phoneNumber;
        private String address;
        private String idCard;
        private String roleName;
}
