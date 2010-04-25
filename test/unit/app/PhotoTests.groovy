package app

import grails.test.*

class PhotoTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testPassId() {
        def photo = new Photo(mimeType: "image/jpeg");
        assert photo.passId.length() == 32
    }
}
