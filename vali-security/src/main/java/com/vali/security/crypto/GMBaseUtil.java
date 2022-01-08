package com.vali.security.crypto;

import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class GMBaseUtil {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
}
