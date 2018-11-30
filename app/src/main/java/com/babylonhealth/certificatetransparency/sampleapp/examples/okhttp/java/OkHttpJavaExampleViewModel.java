/*
 * Copyright 2018 Babylon Healthcare Services Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.babylonhealth.certificatetransparency.sampleapp.examples.okhttp.java;

import com.babylonhealth.certificatetransparency.sampleapp.examples.BaseExampleViewModel;
import okhttp3.*;
import org.certificatetransparency.ctlog.InterceptorBuilder;
import org.certificatetransparency.ctlog.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Set;

public class OkHttpJavaExampleViewModel extends BaseExampleViewModel {

    @NotNull
    @Override
    public String getSampleCodeTemplate() {
        return "okhttp-java.txt";
    }

    // A normal client would create this ahead of time and share it between network requests
    // We create it dynamically as we allow the user to set the hosts for certificate transparency
    private OkHttpClient createOkHttpClient(Set<String> hosts, boolean isFailOnError, Logger defaultLogger) {
        // Create a network interceptor
        InterceptorBuilder builder = new InterceptorBuilder()
                .setFailOnError(isFailOnError)
                .setLogger(defaultLogger);

        for (String host : hosts) {
            builder.addHost(host);
        }

        Interceptor networkInterceptor = builder.build();

        // Set the interceptor when creating the OkHttp client
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(networkInterceptor)
                .build();
    }

    @Override
    public void openConnection(@NotNull String connectionHost, @NotNull Set<String> hosts, boolean isFailOnError, @NotNull Logger defaultLogger) {
        OkHttpClient client = createOkHttpClient(hosts, isFailOnError, defaultLogger);

        Request request = new Request.Builder().url("https://" + connectionHost).build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                // Failure. Send message to the UI as logger won't catch generic network exceptions
                sendException(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                // Success. Reason will have been sent to the logger
            }
        });
    }

    /*@NotNull
    @Override
    public String generateSourceCode(@NotNull Set<String> hosts, boolean failOnError) {
        StringBuilder bob = new StringBuilder();

        bob.append("InterceptorBuilder builder = new InterceptorBuilder()");

        for (String host : hosts) {
            bob.append("\n        .addHost(\"");
            bob.append(host);
            bob.append("\")");
        }

        if (!failOnError) {
            bob.append("\n        .setFailOnError(false)");
        }
        bob.append(";\n\n");


        bob.append("OkHttpClient client = new OkHttpClient.Builder()\n");
        bob.append("        .addNetworkInterceptor(networkInterceptor)\n");
        bob.append("        .build();");

        return bob.toString();
    }*/
}
