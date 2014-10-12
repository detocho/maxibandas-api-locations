package locations



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Location)
class LocationTests {

    def registeredParentLocation
    def registeredChildLocation
    def sampleLocation

    @Before
    void setUp(){

        registeredParentLocation = new Location(
                name:"México",
                level: "country"
        )

        mockForConstraintsTests(Location , [registeredParentLocation])

        registeredChildLocation = new Location (

                parentLocationId: registeredParentLocation.id,
                name:"Aguscalientes",
                level:"state"
        )

        mockForConstraintsTests(Location, [registeredChildLocation])

        sampleLocation = new Location(
                parentLocationId: registeredParentLocation.id,
                name:"Baja California",
                level: "state"
        )
    }

    void test_LocationIsValidate(){
        assertTrue(sampleLocation.validate())
        sampleLocation.save()
        assertEquals(sampleLocation.parentLocationId, registeredParentLocation.id)
    }
}
