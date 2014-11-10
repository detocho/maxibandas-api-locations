import locations.Location

class BootStrap {

    def init = { servletContext ->

        test{

        }
        development{

            if (Location.count() == 0){

                def location01 = new Location(

                        locationId  :"MX",
                        name        :"México",
                        level       :"country"
                )
                location01.save()

                def location02 = new Location(

                        locationId          : "EST1",
                        name                : "Aguscalientes",
                        parentLocationId    : location01.locationId,
                        level               : "state"
                )
                location02.save()

                def location03 = new Location(

                        locationId          : "EST2",
                        name                : "Baja California",
                        parentLocationId    : location01.locationId,
                        level               : "state"
                )
                location03.save()
            }
        }
        production{

        }
    }
    def destroy = {
    }
}
