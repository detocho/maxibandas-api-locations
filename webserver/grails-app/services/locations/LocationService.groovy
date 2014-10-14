package locations


import java.text.MessageFormat
import org.apache.ivy.plugins.conflict.ConflictManager
import grails.converters.*
import locations.exceptions.NotFoundException
import locations.exceptions.ConflictException
import locations.exceptions.BadRequestException

class LocationService {

    static transactional = 'mongo'

    def getLocation(def locationId){

        Map jsonResult              = [:]
        def jsonChildren            = []
        Map resultParentLocation    = [:]

        if (!locationId){

            throw  new NotFoundException("You must provider locationId")
        }

        def location = Location.findById(locationId)

        if (!location){

            throw new NotFoundException("The locationId not Found")
        }

        def childrenLocations = Location.findAllByParentLocationId(locationId)

        childrenLocations.each{

            jsonChildren.add(

                    locationId  : it.id,
                    name        : it.name,
                    level       : it.level
            )
        }

        if (location.parentLocationId){
            def parentLocation = Location.findById(location.parentLocationId)
            resultParentLocation.id = parentLocation.id
            resultParentLocation.name = parentLocation.name
            resultParentLocation.level = parentLocation.level
        }

        jsonResult.id = location.id
        jsonResult.name = location.name
        jsonResult.level = location.level
        jsonResult.parent_location = resultParentLocation
        jsonResult.children_locations = jsonChildren

        jsonResult

    }

    def createLocation (def parentLocationId, def jsonLocation){

        Map jsonResult      = [:]
        def responseMessage = ''

        if(!Location.findById(parentLocationId)){

            throw new NotFoundException("The locationId = "+parentLocationId+" not found")
        }

        def newLocation =  new Location(

                parentLocationId:parentLocationId,
                name: jsonLocation?.name,
                level: jsonLocation?.level
        )

        if (!newLocation.validate()){

            newLocation.errors.allErrors.each {
                responseMessage += MessageFormat.format(it.defaultMessage, it.arguments) + " "
            }

            throw new BadRequestException(responseMessage)
        }

        newLocation.save()

        jsonResult = getLocation(newLocation.id)

        jsonResult
    }

    def createLocation (def jsonLocation){

        Map jsonResult      = [:]
        def responseMessage = ''

        def newLocation = new Location(

                name: jsonLocation?.name,
                level: jsonLocation?.level

        )

        if (!newLocation.validate()){

            newLocation.errors.allErrors.each {
                responseMessage += MessageFormat.format(it.defaultMessage, it.arguments) + " "
            }

            throw new BadRequestException(responseMessage)
        }

        newLocation.save()

        jsonResult = getLocation(newLocation.id)

        jsonResult
    }
    
}
