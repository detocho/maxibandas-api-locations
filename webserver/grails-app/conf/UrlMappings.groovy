class UrlMappings {

	static mappings = {

        "/$locationId?"{
            controller = "Location"
            action = [GET:'getLocation',POST:'addLocation', PUT:'notAllowed',DELETE:'notAllowed']
        }
	}
}
