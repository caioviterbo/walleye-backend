package com.walleye.walleye_backend.util;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class PairCodeGenerator {
    
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();

    public static String generatePairCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);

         for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }
    
}
