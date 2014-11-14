import locations.Location
import locations.Zipcode

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

                def municipio = new Location(

                        locationId          : "CTY1",
                        name                : "AguascalientesMun",
                        parentLocationId    : location02.locationId,
                        level               : "city"

                )
                municipio.save()

                def colonia01 = new Location(

                        locationId          : "NGD1",
                        name                : "Colonia",
                        parentLocationId    : municipio.locationId,
                        level               : "neighborhood"

                )
                colonia01.save()

                def colonia02 = new Location(

                        locationId          : "NGD2",
                        name                : "Colonia",
                        parentLocationId    : municipio.locationId,
                        level               : "neighborhood"

                )
                colonia02.save()
            }

            if (Zipcode.count() == 0) {

                def zipCodeLocations01 = new Zipcode(
                        zipcode: '54900',
                        locationId: 'NGD1'
                )

                zipCodeLocations01.save()


                def zipCodeLocation02 = new Zipcode(
                        zipcode: '54900',
                        locationId: 'NGD2'
                )

                zipCodeLocation02.save()
            }
        }
        production{

        }
    }
    def destroy = {
    }
}
