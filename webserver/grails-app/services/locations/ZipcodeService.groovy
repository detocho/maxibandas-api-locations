package locations

import java.text.MessageFormat
import org.apache.ivy.plugins.conflict.ConflictManager
import grails.converters.*
import locations.exceptions.NotFoundException
import locations.exceptions.ConflictException
import locations.exceptions.BadRequestException
import java.util.regex.Matcher
import java.util.regex.Pattern

class ZipcodeService {

    static  transactional = 'mongo'

    def getLocationsByZipCode(def params){

        def zipcode = params.zipcode
        def resultParentLocation    = []

        if (!zipcode)
            throw new BadRequestException("zipcode must be provided")

        if (!zipCodeIdValid(zipcode))
            throw new BadRequestException("zipcode is not valid")


        Map jsonResult= [:]

        def locationsObtained = getNeighborhoods(zipcode)


        def neighborhoods = []
        def parentLocationId
        locationsObtained.each{
            neighborhoods.add(
                    locationId : it.locationId,
                    name : it.name
            )

            parentLocationId = it.parentLocationId
        }

        resultParentLocation = getParentLocation(parentLocationId)

        jsonResult.zipcode          = zipcode
        jsonResult.parent_location  = resultParentLocation
        jsonResult.neighborhoods    = neighborhoods

        jsonResult


    }



    def createZipCodeLocation(def zipCodeLocationJson){

        def zipcode
        def locationId
        def neighborhoodResult

        if (!zipCodeLocationJson) {
            throw new BadRequestException("Request JSON must be provided")
        }

        zipcode		= zipCodeLocationJson?.zipcode
        locationId	= zipCodeLocationJson?.location_id

        if (!zipcode) {
            throw new BadRequestException("zipcode must be provided")
        }

        if (!zipCodeIdValid(zipcode)) {
            throw new BadRequestException("zipcode is not valid")
        }

        if (!locationId) {
            throw new BadRequestException("location_id must be provided")
        }

        neighborhoodResult = Location.findByLocationIdAndLevel(locationId, 'neighborhood')

        if (!neighborhoodResult){
            throw new BadRequestException("No results for locationId " + locationId + " at API Locations")
        }

        Map jsonResult	= [:]

        jsonResult.zipcode = zipcode
        jsonResult.location = [:]
        jsonResult.location.location_id = neighborhoodResult.locationId
        jsonResult.location.name = neighborhoodResult.name
        jsonResult.parent_location  = getParentLocation(neighborhoodResult.parentLocationId)

        def existsZipCodeLocation = ExistsZipCodeLocationByNeighborhoodId(locationId)

        if (existsZipCodeLocation){


            throw new ConflictException("The neighborhood "+locationId+" exists for zipcode "+existsZipCodeLocation.zipcode)



        }else{
            def zipCodeLocationNew = new Zipcode(
                    zipcode:zipcode,
                    locationId: locationId
            )

            zipCodeLocationNew.save()
        }

        jsonResult
    }

    def ExistsZipCodeLocationByNeighborhoodId(neighborhoodId){

        Zipcode.countByLocationId(neighborhoodId) > 0 ? Zipcode.findByLocationId(neighborhoodId)  : null

    }

    def getNeighborhoods(def zipcode){
        def relations		= Zipcode.findAllByZipcode(zipcode)

        def neighborhood
        def neighborhoods = []
        def neighborhoodId

        if (!relations){
            throw new BadRequestException("zipCodeId doesn't exists!")
        }

        relations.each {
            neighborhoodId = it.locationId

            neighborhood =  Location.findByLocationId(neighborhoodId)

            if (neighborhood != null){
                neighborhoods.add(neighborhood)
            }

        }

        neighborhoods


    }

    def getParentLocation(def parentLocationId){

        def resultParentsLocations = []

        while (parentLocationId){
            def parent = getParent(parentLocationId)

            resultParentsLocations.add(

                    location_id  : parent.location_id,
                    name         : parent.name,
                    level        : parent.level

            )
            parentLocationId = parent.parent_location_id
        }

        resultParentsLocations


    }

    def getParent(def parentLocationId){

        def jsonParent = [:]

        if (parentLocationId){

            def parentLocation = Location.findByLocationId(parentLocationId)

            jsonParent.location_id          = parentLocation.locationId
            jsonParent.name                 = parentLocation.name
            jsonParent.level                = parentLocation.level
            jsonParent.parent_location_id   = parentLocation.parentLocationId
        }

        jsonParent
    }

    private zipCodeIdValid(zipCodeid){

        def pattern = ~/^[0-9]{5}$/
        pattern.matcher(zipCodeid).matches()
    }



}
