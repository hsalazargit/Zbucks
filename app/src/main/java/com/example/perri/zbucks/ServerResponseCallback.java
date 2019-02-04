package com.example.perri.zbucks;

import java.util.ArrayList;

public interface ServerResponseCallback {
    void onSuccessResponse(ArrayList<User> resultList);
}
