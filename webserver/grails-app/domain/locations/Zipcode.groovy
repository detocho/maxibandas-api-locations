package locations

class Zipcode {

    static constraints = {
        zipcode     nullable:false, blank:false
        locationId  nullable: false, blank:false, unique:true
    }

    String zipcode
    String locationId
}
