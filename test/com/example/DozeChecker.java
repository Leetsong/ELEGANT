package com.example;

import com.example.os.*;

public class DozeChecker {

    public boolean isCompatible() {
        return Build.SDK_VERSION > 26;
    }

    public void go() {

    }

    public void goWithChecking() {
        if (isCompatible()) {
            go();
        }
    }

    public void goWithoutChecking() {
        go();
    }

    public static void main(String args[]) {
        DozeChecker d = new DozeChecker();
        d.goWithoutChecking();
        d.goWithChecking();
    }

}
