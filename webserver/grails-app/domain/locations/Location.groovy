package locations

class Location {

    static constraints = {

        parentLocationId  nullable:true, maxSize: 20
        name              blank:false, maxSize: 150
        level             nullable:false, blank:false, inList: ["country", "state", "city","neighborhood"]
    }

    String parentLocationId
    String name
    String dateRegistration
    String level
}
