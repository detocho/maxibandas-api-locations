package locations



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(locations.Location)
class LocationTests {

    def registeredParentLocation
    def registeredChildLocation
    def sampleLocation

    @Before
    void setUp(){

        registeredParentLocation = new Location(
                locationId: "MX",
                name:"México",
                level: "country"
        )

        mockForConstraintsTests(Location , [registeredParentLocation])

        registeredChildLocation = new Location (
                locationId: "EST1",
                parentLocationId: registeredParentLocation.locationId,
                name:"Aguscalientes",
                level:"state"
        )

        mockForConstraintsTests(Location, [registeredChildLocation])

        sampleLocation = new Location(
                locationId: "EST2",
                parentLocationId: registeredParentLocation.locationId,
                name:"Baja California",
                level: "state"
        )
    }

    void test_LocationIsValidate(){

        assertTrue(sampleLocation.validate())
        sampleLocation.save()
        assertEquals(sampleLocation.parentLocationId, registeredChildLocation.parentLocationId)
    }

    void test_LocationIsNotValidWhit_NameIsLong(){

        sampleLocation.name = 'El nombre de la location es demasiado  grande y asi no se puede procesar asi que debes escribir algo mas ligero creo que falto mas longitud para poder entrar a la prueba jojojojojo'
        assertFalse(sampleLocation.validate())
        assertEquals('maxSize', sampleLocation.errors['name'])

    }

    void test_LocationIsNotValidWhit_ParentIdIsLong(){

        sampleLocation.parentLocationId = 'hiduahdq34630284320874y3847184761874610984182940129874bc219984918479182740912847912874'
        assertFalse(sampleLocation.validate())
        assertEquals('maxSize', sampleLocation.errors['parentLocationId'])

    }

    void test_LocationIsNotValidWhit_NameIsBlank(){

        sampleLocation.name = ''
        assertFalse(sampleLocation.validate())
        assertEquals('blank', sampleLocation.errors['name'])

    }
}
