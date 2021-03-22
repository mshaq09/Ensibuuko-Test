package com.ensibuuko.test;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.ensibuuko.test.ui.main.AddPostActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.realm.Realm;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class EnsibuukoUnitTest {

    private static final boolean FAKE_STRING = false;

    @Mock
    Context mMockContext;


    @Test
    public void verifyUser() {

        AddPostActivity myObjectUnderTest = new AddPostActivity(mMockContext);
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Realm.init(context);
        boolean result = myObjectUnderTest.verifyUser("bret");

        // ...then the result should be the expected one.
        assertThat(result, is(FAKE_STRING));
    }
}