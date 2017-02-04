package org.humanResources.json;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.humanResources.json.account.AccountDeserializer;
import org.humanResources.json.account.AccountSerializer;
import org.humanResources.security.entity.Account;
import org.humanResources.security.entity.AccountImpl;

public class WebServicesObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = 1L;

	public WebServicesObjectMapper() {
		super();

		//configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
		//configure(SerializationConfig.Feature.ORDER_MAP_ENTRIES_BY_KEYS, true);

		SimpleModule module = new SimpleModule();

		/****************** Account  **********************/

		module.addSerializer(AccountImpl.class, new AccountSerializer());
		module.addDeserializer(AccountImpl.class, new AccountDeserializer());

		module.addAbstractTypeMapping(Account.class, AccountImpl.class);


		this.registerModule(module);
	}


}
