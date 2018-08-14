package com.hoangloc.flipperplugins;

import android.app.Application;
import android.content.Context;

import com.facebook.litho.sonar.LithoSonarDescriptors;
import com.facebook.soloader.SoLoader;
import com.facebook.sonar.android.AndroidSonarClient;
import com.facebook.sonar.core.SonarClient;
import com.facebook.sonar.core.SonarConnection;
import com.facebook.sonar.core.SonarPlugin;
import com.facebook.sonar.plugins.inspector.DescriptorMapping;
import com.facebook.sonar.plugins.inspector.InspectorSonarPlugin;
import com.facebook.sonar.plugins.leakcanary.RecordLeakService;
import com.facebook.sonar.plugins.network.NetworkSonarPlugin;
import com.facebook.sonar.plugins.network.SonarOkhttpInterceptor;
import com.facebook.sonar.plugins.leakcanary.LeakCanarySonarPlugin;
import com.facebook.sonar.plugins.sharedpreferences.SharedPreferencesSonarPlugin;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {
    public static OkHttpClient okhttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(getApplicationContext(), false);

        final SonarClient client = AndroidSonarClient.getInstance(getApplicationContext());
        final DescriptorMapping descriptorMapping = DescriptorMapping.withDefaults();

        NetworkSonarPlugin networkPlugin = new NetworkSonarPlugin();
        SonarOkhttpInterceptor interceptor = new SonarOkhttpInterceptor(networkPlugin);

        okhttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.MINUTES)
                .build();

        LithoSonarDescriptors.add(descriptorMapping);
        client.addPlugin(new InspectorSonarPlugin(getApplicationContext(), descriptorMapping));
        client.addPlugin(networkPlugin);
        client.addPlugin(new SharedPreferencesSonarPlugin(this, "sample"));
        client.addPlugin(new LeakCanarySonarPlugin());

        RefWatcher refWatcher = LeakCanary.refWatcher(this)
                .listenerServiceClass(RecordLeakService.class).buildAndInstall();

        client.start();

        getSharedPreferences("sample", Context.MODE_PRIVATE).edit().putString("0", "world").apply();
        getSharedPreferences("sample", Context.MODE_PRIVATE).edit().putString("1", "world").apply();
        getSharedPreferences("sample", Context.MODE_PRIVATE).edit().putString("2", "world").apply();
        getSharedPreferences("sample", Context.MODE_PRIVATE).edit().putString("3", "world").apply();
    }
}