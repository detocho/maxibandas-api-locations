class UrlMappings {

	static mappings = {

        "/$locationId?"{
            controller = "Location"
            action = [GET:'getLocation',POST:'notAllowed', PUT:'notAllowed',DELETE:'notAllowed']
        }
	}
}
