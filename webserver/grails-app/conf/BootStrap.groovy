import locations.Location

class BootStrap {

    def init = { servletContext ->

        test{

        }
        development{

            if (Location.count() == 0){

                def location01 = new Location(

                        name:"México",
                        level: "country"
                )
                location01.save()

                def location02 = new Location(
                        name:"Aguscalientes",
                        parentLocationId: location01.id,
                        level: "state"
                )
                location02.save()

                def location03 = new Location(
                        name:"Baja California",
                        parentLocationId: location01.id,
                        level: "state"
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
