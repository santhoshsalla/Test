package com.test.automation.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkUtils {

    private final ChromeDriver chrome;

    public NetworkUtils(WebDriver driver) {
        if (!(driver instanceof ChromeDriver)) {
            throw new IllegalArgumentException("Network simulation is only supported for ChromeDriver in this framework.");
        }
        this.chrome = (ChromeDriver) driver;
    }

    public void enableNetwork() {
        chrome.executeCdpCommand("Network.enable", Collections.emptyMap());
    }

    public void setOffline(boolean offline) {
        enableNetwork();
        Map<String, Object> params = new HashMap<>();
        params.put("offline", offline);
        params.put("latency", 0);
        params.put("downloadThroughput", offline ? 0 : -1);
        params.put("uploadThroughput", offline ? 0 : -1);
        chrome.executeCdpCommand("Network.emulateNetworkConditions", params);
    }

    public void blockUrls(List<String> urlPatterns) {
        enableNetwork();
        Map<String, Object> params = new HashMap<>();
        params.put("urls", urlPatterns);
        chrome.executeCdpCommand("Network.setBlockedURLs", params);
    }

    public void unblockAll() {
        blockUrls(List.of());
    }
}
