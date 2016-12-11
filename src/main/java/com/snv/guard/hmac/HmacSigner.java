package com.snv.guard.hmac;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Hmac signer
 * Created by Michael DESIGAUD on 15/02/2016.
 */
public class HmacSigner {

    private static final Log LOG = LogFactory.getLog(HmacSigner.class);
    private static final String ENCODING = "UTF-8";

    public static final String ENCODING_CLAIM_PROPERTY = "l-lev";
    
    /**
     * private constructor to hide the implicit public one.
     */
    private HmacSigner(){}

    /**
     * Get a signed JWT
     * The issuer (user id) and the custom properties are stored in the JWT
     * to be retrieve later
     * @param secret
     * @param iss issuer (user identifier)
     * @param ttl time to live (in seconds
     * @param claims Custom properties to store in the JWT
     * @return HmacToken instance
     * @throws HmacException
     */
    public static HmacToken getSignedToken(String secret, String iss, Integer ttl,Map<String,String> claims) throws HmacException{

                LOG.info("getSignedToken");
        //Generate a random token
        String jwtID = generateToken();

        //Generate a signed JWT
        String jsonWebToken = generateJWT(secret,jwtID, iss, ttl, claims);

        return new HmacToken(jwtID,secret, jsonWebToken);
    }

    /**
     * Generate a random token (based on uuid)
     * @return a random token
     */
    private static String generateToken(){
                LOG.info("generateToken");
        return UUID.randomUUID().toString();
    }

    /**
     * Generate a random secret (base on uuid) and encoded in base 64
     * @throws HmacException
     * @return a random secret
     */
    public static String generateSecret() throws HmacException {
                LOG.info("generateSecret");
        try {
            return Base64.encodeBase64String(generateToken().getBytes(ENCODING)).replace("\n","").replace("\r","");
        } catch (UnsupportedEncodingException e) {
            LOG.error("Cannot encode base64", e);
            throw new HmacException("Cannot encode base64",e);
        }
    }

    /**
     * Generate a new signed JWT
     * @param secret hmac secret
     * @param jwtID hmac jwtID
     * @param iss issuer
     * @param ttl time to live (in seconds
     * @param claims List of custom claims
     * @return Signed JWT
     */
    private static String generateJWT(String secret, String jwtID,String iss, Integer ttl,Map<String,String> claims) throws HmacException{
                LOG.info("generateJWT");
        try {
            return signJWT(secret,jwtID,ttl,iss,claims);
        } catch (JOSEException e) {
            LOG.error("Cannot generate JWT",e);
            throw new HmacException("Cannot generate JWT",e);
        }
    }

    /**
     * Sign a Json Web Token
     * @param secret Random secret in base 64
     * @param jwtID random jwtID
     * @param ttl time to live (in minutes)
     * @param iss issuer
     * @param claims List of custom claims
     * @return A signed json web token
     * @throws JOSEException
     */
    public static String signJWT(String secret, String jwtID, Integer ttl,String iss, Map<String,String> claims) throws JOSEException {
                LOG.info("signJWT");
        JWSSigner jwsSigner = new MACSigner(secret.getBytes());

        //Create a new claim
        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
        builder
                .jwtID(jwtID)
                .expirationTime(DateTime.now().plusMinutes(ttl).toDate())
                .issuer(iss);

        if(claims != null && !claims.isEmpty()) {
            claims.entrySet().forEach(entry -> 
                builder.claim(entry.getKey(), entry.getValue())
            );
        }

        JWTClaimsSet claimsSet = builder.build();

        //Sign the jwt
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        signedJWT.sign(jwsSigner);

        //return a string jwt
        return signedJWT.serialize();
    }

    /**
     * Check JWT is expired
     * @param jwtString JWT string representation
     * @return true if expired, false otherwise
     * @throws ParseException
     */
    public static Boolean isJwtExpired(String jwtString) throws ParseException {
                LOG.info("isJwtExpired");
        JWT jwt = JWTParser.parse(jwtString);
        if(jwt.getJWTClaimsSet() != null && jwt.getJWTClaimsSet().getExpirationTime() != null) {
            DateTime expirationDate = new DateTime(jwt.getJWTClaimsSet().getExpirationTime());
            return expirationDate.isBefore(DateTime.now());
        }
        return false;
    }

    /**
     * Verify Json Web Token
     * @param jwt jwt
     * @param secret shared secret
     * @return true if the JWT is valid, false otherwise
     * @throws HmacException
     */
    public static Boolean verifyJWT(final String jwt, final String secret) throws HmacException {
                LOG.info("verifyJWT");
        try {
            SignedJWT signedJWT = SignedJWT.parse(jwt);
            JWSVerifier jwsVerifier = new MACVerifier(secret);
            return signedJWT.verify(jwsVerifier);
        } catch (ParseException | JOSEException ex) {
            throw new HmacException("Cannot verify JWT", ex);
        }
    }

    /**
     * To retrieve a jwt claim property from a json web token
     *
     * @param jwt      json web token
     * @param claimKey claim key of the property to retrieve
     * @return property value
     * @throws HmacException
     */
    public static String getJwtClaim(final String jwt, final String claimKey) throws HmacException {
                LOG.info("getJwtClaim");
        try {
            //Parse jwt string
            SignedJWT signedJWT = SignedJWT.parse(jwt);

            Object customClaim = signedJWT.getJWTClaimsSet().getClaim(claimKey);
            return customClaim != null ? String.valueOf(customClaim) : null;
        } catch (ParseException ex) {
            throw new HmacException("The claim property: " + claimKey + " is missing", ex);
        }
    }

    /**
     * To retrieve the "Issuer" (iss) property from a json web token
     *
     * @param jwt json web token
     * @return iss property from claim
     * @throws HmacException
     */
    public static String getJwtIss(final String jwt) throws HmacException {
                LOG.info("getJwtIss");
        try {
            //Parse jwt string
            SignedJWT signedJWT = SignedJWT.parse(jwt);
            //Return the claim property value
            return String.valueOf(signedJWT.getJWTClaimsSet().getIssuer());
        } catch (ParseException ex) {
            throw new HmacException("The iss property is missing", ex);
        }
    }

    /**
     * Encodes a message with HMAC and a given algorithm
     *
     * @param secret    secret used to sign the message
     * @param message   message to sign
     * @param algorithm algorithm ued to sign
     * @return an HMAC encoded string
     * @throws HmacException
     */
    public static String encodeMac(final String secret, final String message, final String algorithm) throws HmacException {
                LOG.info("encodeMac");
        String digest;
        try {
            SecretKeySpec key = new SecretKeySpec(secret.getBytes(ENCODING), algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(key);

            byte[] bytes = mac.doFinal(message.getBytes(ENCODING));

            StringBuilder hash = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (UnsupportedEncodingException | 
                IllegalStateException | 
                InvalidKeyException | 
                NoSuchAlgorithmException ex) {
            throw new HmacException("Error while encoding request with hmac", ex);
        }
        return digest;
    }
}
