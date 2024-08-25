package com.hbpaymentprocessing.hbpaymentprocessing.ouputs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedAccountResponse implements Serializable {
    private String publicKey;
    private String token;
}
