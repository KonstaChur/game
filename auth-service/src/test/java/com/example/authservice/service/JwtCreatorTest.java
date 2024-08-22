package com.example.authservice.service;

import com.example.authservice.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtCreatorTest {

    private JwtCreator jwtCreator;

    @BeforeEach
    public void setUp() throws Exception {
        String privateKeyPem = "-----BEGIN PRIVATE KEY-----\n" +
                "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDcCJNiF8uDvZQU\n" +
                "aTCQ0xb4PAnCFsChmLdu63Vcyab1Q91e/L0T27NQ5hzp0v4/zheBLuOxb9073IkW\n" +
                "G1p6TAvYff2XSb3O0tNOPVuUpkorxWtN2JuGk2YSWKLV6KgWW4So8FW0m6B44B5Y\n" +
                "VHypPKSc8HhDcPZAKi5dMki/f3FwAqGhJ7dGu9ol2kCwFyBAFh3vESLK80lnRpWk\n" +
                "u/GdEsgJIli53V+TgIKHavw2+1Rn+27gCulyFKtkxNj51ZUxh2C1fnC8wGSbu6fy\n" +
                "wiIAz8NH397++H8dH+je0I9SNqrWyVW4VwjuYNdo/UJiC3x2DjCEGDjt4Wif6Mg4\n" +
                "g/x86C2TAgMBAAECggEBAIFtcfRjdsz5eN9AuNaXtI00E7FdXNYxVMUkL5by7wia\n" +
                "cN7s3/OMakAN+7f26qNxgRX2FO1MleAea2zbji8eCGHCSqbxM3szPCRrpzEbV8nD\n" +
                "iMjzvxg8H/BuvuoAvs7X6oLNTDy+xngTrQXcPK/4AgRm1vckvVUk+rzLVUt/laSC\n" +
                "cFnCrKpa2cIQnReP6IEHN7XRtESV4AHvbGRMmSqizAsKNPN+G/Q+HkT1rffHiw1y\n" +
                "ftPLjk90X8OCIe/WJnKfCtT7puksawTFsDssFe5Sd0q6jjIBId6GlcprQzXmDBqf\n" +
                "PTjrPpUiHGXN3I5b3TstbFbh1ad1a6+xMdyZ4fB9rTECgYEA9cyjd03v963H2G8d\n" +
                "AE6VEwekKUt1ulQM4iypTviuuyyJAwDTZcT3Ba39dMgYSXiqvAsvWTh5znxAmLSS\n" +
                "PFzzzXtoLyqC4w3j+l9q5u+xmeE9UTuQQTUbpCES2S19HTDVdUO39CvH1frt/HMT\n" +
                "T23qyk4mucOuXmspVkR9MCD6h/0CgYEA5SozpFU9EYTmObblfCZ47DeLFUSBr3us\n" +
                "rSmFC5jnETgX3ghISGQLv7AOdEjuhpC25cOQC1nEoAAUHpNiTd444TO/m9AwfS0z\n" +
                "stRMWIyKK0dpSpGXyk6L5Yh2UBgm90VtaphK062zi4fxiJra7tCGRQSxzwEc2ixE\n" +
                "ttUK6XTOmM8CgYBIy1cqERHdZ0EFTzrmiV9K6Htso2V1TkkWfqWRtsKAulrFYew5\n" +
                "epCxxOqIOG5A7BScZi+am8gvJ8s1rlyUte6aT072kP98YPPAhZuyUxBlAzjWou5A\n" +
                "8YqDHCxp1zfzei/tb6W+EW04aP0sQ6RXZLUhPu3nHBW67r+qRdRReaLYvQKBgQCX\n" +
                "KPApfKQskyo9dZwH+WSYA8c1PxwAAacUDagZz0DThdmQdowfXAa36UyPfbAAiSJr\n" +
                "ikTQh+T/2S1sQbF9RZDlU3oXMAtb9l38nrbBTeqxw4f7TO//3THANPQ9vsCtKFmV\n" +
                "D2GEITamWgSDJAo3rspyajdTIRvD+aFVrE9nUMoZ1wKBgQCMGfDVI8hOcB+yOMw0\n" +
                "rrgC91tYRQAyG72UKFfOggJBCFkJnNvH2w/a/tsSz8muTm8dD1Z6gdBjQL6Xci2a\n" +
                "837bHheNScvOB8SgdYrVFJqOGWT46YqpOReqsX7o9NRek4E+kz0gepCSSmC3EKBQ\n" +
                "b9FDsnpbn2C6/uA4Upt/48lPSg==\n" +
                "-----END PRIVATE KEY-----";

        jwtCreator = new JwtCreator(privateKeyPem);
    }

    @Test
    public void testCreateJwt() {
        User user = new User().setName("testUser").setPlayer(true);
        String token = jwtCreator.createJwt(user);

        // Verify that the token is not null or empty
        assertEquals(true, token != null && !token.isEmpty());
    }

    @Test
    public void testValidateJwt_ValidToken() {
        User user = new User().setName("testUser").setPlayer(true);
        String token = jwtCreator.createJwt(user);

        boolean isValid = jwtCreator.validateJwt(user.getName(), token);

        assertTrue(isValid);
    }

    @Test
    public void testValidateJwt_InvalidToken() {
        User user = new User().setName("testUser").setPlayer(true);
        String token = jwtCreator.createJwt(user);

        // Modify the token to make it invalid
        String invalidToken = token + "tampered";

        boolean isValid = jwtCreator.validateJwt(user.getName(), invalidToken);

        assertFalse(isValid);
    }

    @Test
    public void testValidateJwt_InvalidUser() {
        User user = new User().setName("testUser").setPlayer(true);
        String token = jwtCreator.createJwt(user);

        boolean isValid = jwtCreator.validateJwt("wrongUser", token);

        assertFalse(isValid);
    }
}
