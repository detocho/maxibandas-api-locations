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

        def location = Location.findByLocationId(locationId)

        if (!location){

            throw new NotFoundException("The locationId not Found")
        }

        def childrenLocations = Location.findAllByParentLocationId(locationId)

        childrenLocations.each{

            jsonChildren.add(

                    locationId  : it.locationId,
                    name        : it.name,
                    level       : it.level
            )
        }

        //TODO aqui debemos  obtener toda la info hasta la raiz
        if (location.parentLocationId){

            def parentLocation = Location.findByLocationId(location.parentLocationId)

            resultParentLocation.location_id    = parentLocation.locationId
            resultParentLocation.name           = parentLocation.name
            resultParentLocation.level          = parentLocation.level
        }

        jsonResult.location_id          = location.locationId
        jsonResult.name                 = location.name
        jsonResult.level                = location.level
        jsonResult.parent_location      = resultParentLocation
        jsonResult.children_locations   = jsonChildren

        jsonResult

    }

    def createLocation (def parentLocationId, def jsonLocation){

        Map jsonResult      = [:]
        def responseMessage = ''

        if(!Location.findByLocationId(parentLocationId)){

            throw new NotFoundException("The parent location with location_id = "+parentLocationId+" not found")
        }

        def newLocation =  new Location(

                locationId          : jsonLocation?.location_id,
                parentLocationId    : parentLocationId,
                name                : jsonLocation?.name,
                level               : jsonLocation?.level
        )

        if (!newLocation.validate()){

            newLocation.errors.allErrors.each {
                responseMessage += MessageFormat.format(it.defaultMessage, it.arguments) + " "
            }

            throw new BadRequestException(responseMessage)
        }

        newLocation.save()

        jsonResult.location_id          = newLocation.locationId
        jsonResult.parent_location_id   = newLocation.parentLocationId
        jsonResult.name                 = newLocation.name
        jsonResult.level                = newLocation.level

        jsonResult
    }

    def createLocation (def jsonLocation){

        Map jsonResult      = [:]
        def responseMessage = ''

        def newLocation = new Location(

                locationId  : jsonLocation?.location_id,
                name        : jsonLocation?.name,
                level       : jsonLocation?.level

        )

        if (!newLocation.validate()){

            newLocation.errors.allErrors.each {
                responseMessage += MessageFormat.format(it.defaultMessage, it.arguments) + " "
            }

            throw new BadRequestException(responseMessage)
        }

        newLocation.save()

        jsonResult.location_id  = newLocation.locationId
        jsonResult.name         = newLocation.name
        jsonResult.level        = newLocation.level

        jsonResult
    }

    def modifyLocation(def locationId, def jsonLocation){

        Map jsonResult      = [:]
        def responseMessage = ''

        if (!locationId){

            throw new NotFoundException("You must provider locationId")

        }

        def obteinedLocation  = Location.findByLocationId(locationId)

        if (!obteinedLocation){

            throw new NotFoundException("The Location with locationId="+locationId+" not found")

        }

        obteinedLocation.name               = jsonLocation?.name
        obteinedLocation.parentLocationId   = jsonLocation?.parent_location_id
        obteinedLocation.level              = jsonLocation?.level

        if (!obteinedLocation.validate()){

            obteinedLocation.errors.allErrors.each {

                responseMessage += MessageFormat.format(it.defaultMessage, it.arguments) + " "
            }

            throw  new BadRequestException(responseMessage)
        }

        obteinedLocation.save()

        jsonResult = getLocation(obteinedLocation.id)

        jsonResult

    }
}
