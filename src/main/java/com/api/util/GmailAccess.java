package com.api.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;

public class GmailAccess {

	/** Application name. */
	private static final String APPLICATION_NAME = "POCBazeLabs";
	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	/** Directory to store authorization tokens for this application. */
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	/**
	 * Global instance of the scopes required by this quickstart. If modifying these
	 * scopes, delete your previously saved tokens/ folder.
	 */
	private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_READONLY);
	private static final String CREDENTIALS_FILE_PATH = "/googleCredentials/credentials.json";

	public static String searchMail(String queryStr) throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
				.setApplicationName(APPLICATION_NAME).build();

		String user = "me";
		List<String> labelIds = new ArrayList<>();
		labelIds.add("INBOX");
		ListMessagesResponse listMessagesResponse = service.users().messages().list(user).setLabelIds(labelIds)
				.setQ(queryStr).execute();

		String responseBody = null;
		if (listMessagesResponse.getMessages() != null) {

			Optional<Message> messageInfoOptional = listMessagesResponse.getMessages().stream().findFirst();
			if (messageInfoOptional.isPresent()) {
				Message messageInfo = messageInfoOptional.get();
				Message rawResponse = service.users().messages().get(user, messageInfo.getId()).execute();
				responseBody = getContent(rawResponse);
			}
		}
		return responseBody;
	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @param HTTP_TRANSPORT The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException If the credentials.json file cannot be found.
	 */
	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		// Load client secrets.
		InputStream in = GmailAccess.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
				.setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
		// returns an authorized Credential object.
		return credential;
	}

	private static String getContent(Message message) {
		StringBuilder stringBuilder = new StringBuilder();
		getPlainTextFromMessageParts(message.getPayload().getParts(), stringBuilder);
		byte[] bodyBytes = org.apache.commons.codec.binary.Base64.decodeBase64(stringBuilder.toString());
		String text = new String(bodyBytes, StandardCharsets.UTF_8);
		return text;
	}

	private static void getPlainTextFromMessageParts(List<MessagePart> messageParts, StringBuilder stringBuilder) {
		for (MessagePart messagePart : messageParts) {
			if (messagePart.getMimeType().equals("text/plain")) {
				stringBuilder.append(messagePart.getBody().getData());
			}

			if (messagePart.getParts() != null) {
				getPlainTextFromMessageParts(messagePart.getParts(), stringBuilder);
			}
		}
	}
}
