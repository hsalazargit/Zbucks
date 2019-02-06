package com.example.perri.zbucks;

import android.content.Context;

import java.util.ArrayList;

public interface ServerResponseCallback {
    void onSuccessResponse(ArrayList<User> resultList, Context context);
}
