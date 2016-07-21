package org.mat.eduvation;

import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    String[] fields;
    String email;
    @Test
    public void test() {
        email = "ahmed.salama456@gmail.com";
        fields = email.split("\\.");
        int i = fields.length;
        System.out.println(keyGenerator(fields));
    }

    private String keyGenerator(String[] s) {
        String key = "edu";
        for (int i = 0; i < s.length; i++) {
            key += s[i];
        }
        return key;
    }
}