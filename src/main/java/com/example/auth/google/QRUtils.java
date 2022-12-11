package com.example.auth.google;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public abstract class QRUtils {

	public static String getAuthenticatorBarCode(String secretKey, String account, String issuer) {
		try {
			String totpString = URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20");
			String secretString = URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20");
			String issuerString = URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");

			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append("otpauth://totp/");
			stringBuilder.append(totpString);
			stringBuilder.append("?secret=");
			stringBuilder.append(secretString);
			stringBuilder.append("&issuer=");
			stringBuilder.append(issuerString);
			return stringBuilder.toString();

		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

	public static void createQRCode(String barCodeData, String filePath, int height, int width)
			throws WriterException, IOException {

		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		BitMatrix matrix = multiFormatWriter.encode(barCodeData, BarcodeFormat.QR_CODE, width, height);
		try (FileOutputStream out = new FileOutputStream(filePath)) {
			MatrixToImageWriter.writeToStream(matrix, "png", out);
		}
	}
}
