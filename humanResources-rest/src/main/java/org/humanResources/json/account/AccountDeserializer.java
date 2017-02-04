package org.humanResources.json.account;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.humanResources.security.entity.AccountImpl;

import java.io.IOException;

public class AccountDeserializer extends JsonDeserializer<AccountImpl> {


	@Override
	public AccountImpl deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
		ObjectCodec oc = jsonParser.getCodec();
		JsonNode node = oc.readTree(jsonParser);

		AccountImpl account = new AccountImpl();

		if(!node.get("id").isNull()) {
			account.setId(Long.valueOf(node.get("id").asText()));
		}

		account.setName(node.get("name").asText());

		return account;

	}

}