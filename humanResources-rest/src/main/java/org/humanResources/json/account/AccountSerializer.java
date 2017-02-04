package org.humanResources.json.account;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.humanResources.security.entity.AccountImpl;

import java.io.IOException;

public class AccountSerializer extends JsonSerializer<AccountImpl> {

	@Override
	public void serialize(AccountImpl account, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {

		
        jgen.writeStartObject();
		if(account.getId()==null){
			jgen.writeNullField("id");
		}else{
			jgen.writeNumberField("id", account.getId());
		}

		jgen.writeStringField("name", account.getName());

        jgen.writeEndObject();

	}
	
}
