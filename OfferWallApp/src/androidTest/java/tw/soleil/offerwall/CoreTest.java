package tw.soleil.offerwall;

import android.test.AndroidTestCase;

import tw.soleil.offerwall.core.OfferWallCore;


/**
 * Created by edward_chiang on 15/2/8.
 */
public class CoreTest extends AndroidTestCase {

    public void testEchoString() {
        OfferWallCore core = new OfferWallCore();
        assertEquals("Echo", core.echoString());
    }

    public void testEchoEchoResString() {
        OfferWallCore core = new OfferWallCore();
        String echoText = core.echoResString(getContext());
        assertNotNull(echoText);
    }
}
