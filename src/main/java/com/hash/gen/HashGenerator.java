package com.hash.gen;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashGenerator {

	public static void main(String[] args) {
		try {
			// Input data
			String apiClientId = "47";
			String refNo = "WEKZZL";
			String amount = "";
			String secret = "1WQTJOAO5JOme8cQNbT7VVtps0pTw9UA";
			String key = "elEQqN6m0O+lskUp";
			String currency = "KES";
			String transactionId = "ABC1234";
			String transactionDate = "2024-01-01 08:30:08";
			String customerName = "Customer A";
			String accountNumber = "123456789";
//					data_string =
//					"$api_client_id"."$ref_no"."$amount"."$currency","$gateway_transaction_id".
//					"$gateway_transaction_date"."$customer_name"."$customer_account_number"."$secret"

			// Concatenate data_string
			String dataString = apiClientId + refNo + amount + secret;
			String dataString1 =apiClientId + refNo +amount+currency+transactionId+transactionDate+customerName+accountNumber+secret;
			System.out.println("Validation:::"+dataString);
			System.out.println("Confirmation:::"+dataString1);

			// Calculate HMAC using SHA-256
			String hmac = calculateHmacSHA256(dataString, key);
			String hmac1 = calculateHmacSHA256(dataString1, key);

			// Encode the HMAC using Base64
			String encodedHmac = base64Encode(hmac);
			String encodedHmac1 = base64Encode(hmac1);

			// Print the result
			System.out.println("Validate HMAC: " + encodedHmac);
			System.out.println("Confirm HMAC: " + encodedHmac1);

		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
		}
	}

	private static String calculateHmacSHA256(String data, String key)
			throws NoSuchAlgorithmException, InvalidKeyException {
		Mac sha256Hmac = Mac.getInstance("HmacSHA256");
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
		sha256Hmac.init(secretKey);

		byte[] hashBytes = sha256Hmac.doFinal(data.getBytes());
		StringBuilder result = new StringBuilder();
		for (byte b : hashBytes) {
			result.append(String.format("%02x", b));
		}
		return result.toString();
	}

	private static String base64Encode(String input) {
		return Base64.getEncoder().encodeToString(input.getBytes());
	}
}
