package com.hbpaymentprocessing.hbpaymentprocessing.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AccountLoginRequest implements Serializable {
    private String email;
    private String pwd;
}
