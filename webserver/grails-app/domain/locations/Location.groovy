package locations

class Location {

    static constraints = {

        locationId        nullable: false, unique:true
        parentLocationId  nullable:true, maxSize: 20
        name              blank:false, maxSize: 150
        level             nullable:false, blank:false, inList: ["country", "state", "city","neighborhood"]
    }

    String locationId
    String parentLocationId
    String name
    String dateRegistration = new Date()
    String level
}
