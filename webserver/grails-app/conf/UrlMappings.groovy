class UrlMappings {

	static mappings = {

        "/$locationId?"{
            controller = "Location"
            action = [GET:'getLocation',POST:'addLocation', PUT:'putLocation',DELETE:'notAllowed']
        }
        "/zipcodes/$zipcode?"{
            controller = "Zipcode"
            action = [GET:'getLocationsByZipcode', POST:'addZipcode', PUT:'notAllowed',DELETE:'notAllowed']
        }
	}
}
