package locations



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Zipcode)
class ZipcodeTests {

    def registeredZipcode
    def sampleZipcode

    @Before
    void setUp(){

        registeredZipcode = new Zipcode(
                zipcode:"54900",
                locationId: "NGD1"
        )

        mockForConstraintsTests(Zipcode, [registeredZipcode])

        sampleZipcode = new Zipcode(
                zipcode: "54900",
                locationId: "NGD2"
        )
    }

    void test_ZipcodeNotValidWithzipcodeIsBlank(){

        sampleZipcode.zipcode=""
        assertFalse(sampleZipcode.validate())
        assertEquals("blank", sampleZipcode.errors['zipcode'])
    }

    void test_ZipcodeNotValidWithZipcodeIsNull(){

        sampleZipcode.zipcode=null
        assertFalse(sampleZipcode.validate())
        assertEquals("nullable",sampleZipcode.errors['zipcode'])
    }

    void test_ZipcodeNotValidWithLocationIdBlank(){

        sampleZipcode.locationId=""
        assertFalse(sampleZipcode.validate())
        assertEquals("blank", sampleZipcode.errors['locationId'])
    }

    void test_ZipcodeNotValidWithLocationIdIsNull(){

        sampleZipcode.locationId=null
        assertFalse(sampleZipcode.validate())
        assertEquals("nullable",sampleZipcode.errors['locationId'])
    }

    void test_ZipcodeNotValidWithLocationIdRepeat(){

        sampleZipcode.locationId='NGD1'
        assertFalse(sampleZipcode.validate())
        assertEquals('unique', sampleZipcode.errors['locationId'])
    }

}
