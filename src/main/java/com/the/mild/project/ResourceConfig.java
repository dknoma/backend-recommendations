package com.the.mild.project;

import static com.the.mild.project.ResourceConfig.PathParams.PATH_PARAM_EXAMPLE_ID;
import static com.the.mild.project.ResourceConfig.PathParams.PATH_PARAM_ID;

public final class ResourceConfig {
    public static final String SERVICE_NAME = "test";

    public static final String PATH_UPDATE_BY_ID_PARAM = "/{" + PATH_PARAM_ID + "}/update";
    public static final String PATH_CREATE = "/create";

    // Resource path names
    public static final String PATH_TODO_RESOURCE = "todo";
    public static final String PATH_TODO_RESOURCE_CREATE = PATH_TODO_RESOURCE + PATH_CREATE;
    public static final String PATH_TODO_RESOURCE_UPDATE = PATH_TODO_RESOURCE + PATH_UPDATE_BY_ID_PARAM;
    public static final String PATH_TODO_RESOURCE_UPDATE_FORMAT = "todo/%s/update";

    public static final String PATH_TEST_RESOURCE = "testresource";
    public static final String PATH_TEST_RESOURCE_WITH_PARAM = "{" + PATH_PARAM_ID + "}";
    public static final String PATH_TEST_RESOURCE_WITH_MULTIPLE_PARAMS = "/{" +
                                                                             PATH_PARAM_ID + "}/example/{" +
                                                                             PATH_PARAM_EXAMPLE_ID + "}";

    public static final String PATH_USER_AUTH = "/auth";
    public static final String PATH_USER_RESOURCE = "/user";
    public static final String PATH_LOGIN = "/login";
    public static final String PATH_LOGOUT = "/logout";
    public static final String PATH_GET_ALL = "/all";
    public static final String PATH_DELETE_USER = "/all/users/{username}";

    // System wide vars
    public static final String MONGO_ID_FIELD = "_id";
    public static final String GOOGLE_ID = "googleId";
    public static final String SESSION_COLLECTION = "session";
    public static final String USER_COLLECTION = "user";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String GIVEN_NAME = "given_name";
    public static final String FAMILY_NAME = "family_name";
    public static final String EXPIRATION_DATE = "exp";

    // public enum Resource {
    //     PATH_TEST_RESOURCE("testresource");
    //
    //     private final String pathname;
    //
    //     Resource(String pathname) {
    //         this.pathname = pathname;
    //     }
    //
    //     public String getPathname() {
    //         return pathname;
    //     }
    // }

    public static final class PathFormats {
        public static final String PATH_TEST_RESOURCE_WITH_PARAM_FORMAT = "/%s";
        public static final String PATH_TEST_RESOURCE_WITH_MULTIPLE_PARAMS_FORMAT = "/%s/example/%s";

        private PathFormats() {
            // Utility
        }
    }

    public static final class PathParams {
        public static final String PATH_PARAM_ID = "id";
        public static final String PATH_PARAM_EXAMPLE_ID = "exampleId";

        private PathParams() {
            // Utility
        }
    }

    private ResourceConfig() {
        // Utility
    }
}
