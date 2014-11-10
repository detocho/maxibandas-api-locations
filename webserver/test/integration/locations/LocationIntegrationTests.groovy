package locations

import static org.junit.Assert.*
import org.junit.*

class LocationIntegrationTests {

    LocationController locationController
    Location sampleChildrenLocation
    Location sampleParentLocation

    @Before
    void setUp() {

        sampleParentLocation = new Location (

                name:"México",
                level: "country"
        ).save()

        sampleChildrenLocation = new Location(

                name:"Aguscalientes",
                parentLocationId: sampleParentLocation.id,
                level: "state"

        ).save()

        locationController = new LocationController()

    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void test_mustGetLocationWithLocationId(){

        locationController.params.locationId = sampleParentLocation.id
        def response = locationController.getLocation()
        print response
    }
}
