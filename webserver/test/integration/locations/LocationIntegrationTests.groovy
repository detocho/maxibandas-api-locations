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
                locationId: "MX",
                name:"México",
                level: "country"
        ).save()

        sampleChildrenLocation = new Location(
                locationId: "EST1",
                name:"Aguscalientes",
                parentLocationId: sampleParentLocation.locationId,
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

        locationController.params.locationId = sampleParentLocation.locationId
        def response = locationController.getLocation()
        print response
    }
}
