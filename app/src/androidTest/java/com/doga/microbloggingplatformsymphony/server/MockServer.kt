package com.bins.tryz.server

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class MockServer {

    //  valid response dispatcher
    class ResponseDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse().setResponseCode(200).setBody(
                "[" +
                        "{ \"id\": 1," +
                        "\"name\": \"Leigh Kessler\"," +
                        "\"userName\": \"Myrtie_Heller51\"," +
                        "\"email\": \"vance_hansen7@yahoo.com\"," +
                        "\"avatar_url\": \"https://s3.amazonaws.com/uifaces/faces/twitter/nutzumi/128.jpg\"" +
                        "\"address\": {" +
                        "\"latitude\": 73.5451," +
                        "\"longitude\": 155.4534," +
                        "}," +
                        "}," +
                        "{" +
                        "\"id\": 2," +
                        "\"name\": \"Homer Bradtke Jr.\"," +
                        "\"userName\": \"Trinity.Feeney46\"," +
                        "\"email\": \"moriah.zulauf@gmail.com\"," +
                        "\"avatar_url\": \"https://s3.amazonaws.com/uifaces/faces/twitter/nelshd/128.jpg\"" +
                        "\"address\": {\n" +
                        "\"latitude\": 73.5451,\n" +
                        "\"longitude\": 155.4534," +
                        "},\n" +
                        "}" +
                        "]"
            )
        }
    }
    //  error dispatcher
    class ErrorDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse = MockResponse().setResponseCode(400)
    }
}