package com.deloitte.fi.estore.utility;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.deloitte.fi.estore.constants.Constants;
import com.deloitte.fi.estore.exception.InvalidArgumentException;
import com.deloitte.fi.estore.exception.NotFoundException;

public class UserUtilities {

	private static final Logger LOG = LoggerFactory.getLogger(UserUtilities.class);

	public String encodeToken(String secret, String issuer) {
		LOG.info("~~~~~ Inside encodeToken method of " + this.getClass().getSimpleName() + " ~~~~~");
		try {
			if (null == issuer || issuer.length() == 0)
				throw new NotFoundException(Constants.NO_ISSUER);
			Algorithm alg = Algorithm.HMAC256(secret);
			return JWT.create().withIssuer(issuer).sign(alg);
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			LOG.error("~~~~~ Exception in encodeToken Method of " + this.getClass().getSimpleName() + " "
					+ e.getLocalizedMessage() + " ~~~~~");
		}
		return null;
	}

	public String decodeToken(String token) {
		LOG.info("~~~~~ Inside the method decodeToken of " + this.getClass().getSimpleName() + " ~~~~~");
		try {
			if (null == token)
				throw new InvalidArgumentException(Constants.NO_TOKEN);
			return JWT.decode(token).getIssuer();
		} catch (JWTDecodeException exception) {
			LOG.error("~~~~~ Invalid token " + exception.getLocalizedMessage() + " ~~~~~");
		}
		return null;
	}

}
