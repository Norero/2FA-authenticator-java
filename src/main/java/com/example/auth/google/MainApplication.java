package com.example.auth.google;

import java.io.IOException;
import java.util.Scanner;

import com.google.zxing.WriterException;

public class MainApplication {

	// Cabia por cada usuario
	private static final String secretKey = "QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK";

	public static void main(String[] args) throws IOException, WriterException {
		String email = "test@gmail.com";
		String companyName = "Awesome Company";

		String barCodeUrl = QRUtils.getAuthenticatorBarCode(secretKey, email, companyName);
		QRUtils.createQRCode(barCodeUrl, "QRCode.png", 400, 400);

		System.out.print("Please enter 2fA code here -> ");
		Scanner scanner = new Scanner(System.in);
		String code = scanner.nextLine();

		boolean isValid = Utils.isCodeValid(code, secretKey);

		if (isValid) {
			System.out.println("Logged in successfully");
		} else {
			System.out.println("Invalid 2FA Code");
		}

		scanner.close();

	}

}
